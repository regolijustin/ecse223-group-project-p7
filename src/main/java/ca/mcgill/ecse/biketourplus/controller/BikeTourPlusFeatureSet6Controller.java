/**
 *
 * @author Vincent Théberge (ThebergeV)
 *
 */

package ca.mcgill.ecse.biketourplus.controller;

import ca.mcgill.ecse.biketourplus.application.BikeTourPlusApplication;
import ca.mcgill.ecse.biketourplus.model.BikeTourPlus;
import ca.mcgill.ecse.biketourplus.model.Combo;
import ca.mcgill.ecse.biketourplus.model.ComboItem;
import ca.mcgill.ecse.biketourplus.model.Gear;
import ca.mcgill.ecse.biketourplus.persistence.BTPPersistence;

import java.util.List;

public class BikeTourPlusFeatureSet6Controller {

    /**
     * The BikeTourPlus where combos will be added, removed or updated
     */

    private static BikeTourPlus currentBTP = BikeTourPlusApplication.getBikeTourPlus();

    /**
     * <p> The method adds a combo to BikeTourPlus
     * </p>
     * @param name name of the new combo
     * @param discount discount to apply on combo
     * @return a string stating whether the combo was added successfully or if there was an error
     */
    public static String addCombo(String name, int discount) {


        //starts by verifying conditions to add the combo
        if(name.equals("")) return "The name must not be empty";
        if (discount < 0){
            return "Discount must be at least 0";
        }
        if (discount > 100){
            return "Discount must be no more than 100";
        }

        //verifies if there is a combo with the same name already existing
        if (currentBTP.hasCombos()) {
            List<Combo> currentCombos = currentBTP.getCombos();
            for (Combo c : currentCombos) {
                if (c.getName().equalsIgnoreCase(name)){
                    //put ignore case to ignore capital letters
                    return "A combo with the same name already exists";
                }
            }
            List<Gear> gears = currentBTP.getGear();
            for (Gear g : gears) {
                if (g.getName().equalsIgnoreCase(name)){
                    //put ignore case to ignore capital letters
                    return "A piece of gear with the same name already exists";
                }
            }

        }
        //creates a new combo and adds it to BikeTourPlus
        Combo newCombo = new Combo(name, discount, currentBTP);

        try {
            currentBTP.addCombo(newCombo);
            BTPPersistence.save();
        } catch (RuntimeException e) {
            return e.getMessage();
        }
        return "";
    }

    /**
     * <p> The method updates a combo in BikeTourPlus
     * </p>
     * @param oldName name of the combo to modify
     * @param newName name of the combo after modification
     * @param newDiscount discount of the combo after modification
     * @return a string stating whether the combo was updated successfully or if there was an error
     */
    public static String updateCombo(String oldName, String newName, int newDiscount) {
        // starts by verifying some conditions

        if(newName.equals("")||oldName.equals("")) return "The name must not be empty";
        if (newDiscount < 0){
            return "Discount must be at least 0";
        }
        if (newDiscount > 100){
            return "Discount must be no more than 100";
        }
        //verifies if there is a combo with the same name already existing
        if (currentBTP.hasCombos()) {

            List<Gear> gears = currentBTP.getGear();
            for (Gear g : gears) {
                if (g.getName().equalsIgnoreCase(newName)){
                    //put ignore case to ignore capital letters
                    return "A piece of gear with the same name already exists";
                }
            }
            List<Combo> currentCombos = currentBTP.getCombos();
            Combo comboToModify = null;
            for (Combo c : currentCombos) {
                if (c.getName().equalsIgnoreCase(oldName)){
                    comboToModify = c;
                }
                if (c.getName().equalsIgnoreCase(newName)){ //verifies that the new name is not a name already used by another combo
                    if(oldName.equals(newName)) break;
                    return "A combo with the same name already exists";
                }
            }
            if(comboToModify == null) return "The combo does not exist";
            comboToModify.setName(newName);
            comboToModify.setDiscount(newDiscount);
            try {
                BTPPersistence.save();
            } catch (RuntimeException e) {
                return e.getMessage();
            }
            return "";
        }
        return "";
    }

    /**
     * <p> The method adds gear to a combo in BikeTourPlus
     * </p>
     * @param gearName name of the gear to add
     * @param comboName name of the combo in which the gear is added
     * @return a string stating whether the gear was added to a combo successfully or if there was an error
     */
    public static String addGearToCombo(String gearName, String comboName) {
        // starts by looking at conditions for the addition of gear to a combo
        Combo comboToModify = null;
        Gear gearToAdd = null;
        if (currentBTP.hasCombos()) { // Looks for an existing combo with the right name
            List<Combo> currentCombos = currentBTP.getCombos();
            for (Combo c : currentCombos) {
                if (c.getName().equalsIgnoreCase(comboName)){
                    comboToModify = currentBTP.getCombo(currentBTP.indexOfCombo(c));
                    break;
                }
            }
            if (comboToModify == null) {
                return "The combo does not exist";
            }
        }

        if (!currentBTP.hasCombos()) {
            return "The combo does not exist";
        }

        if (currentBTP.hasGear()) { //looks for the gear to add and if it exists in the system
            List<Gear> availableGear = currentBTP.getGear();
            for (Gear g : availableGear) {
                if (g.getName().equalsIgnoreCase(gearName)) {
                    gearToAdd = currentBTP.getGear(currentBTP.indexOfGear(g));
                    break;
                }
            }
            if (gearToAdd == null) {
                return "The piece of gear does not exist";
            }
        }

        if (!currentBTP.hasGear()) {
            return "The piece of gear does not exist";
        }
        if(comboToModify.hasComboItems()){
            for(ComboItem ci: comboToModify.getComboItems()) {
                if(ci.getGear().getName().equals(gearName)) {
                    ci.setQuantity(ci.getQuantity()+1);
                    try {
                        BTPPersistence.save();
                    } catch (RuntimeException e) {
                        return e.getMessage();
                    }
                    return "";
                }
            }
        }
        ComboItem ci = new ComboItem(1, currentBTP, comboToModify, gearToAdd);
        try {
            BTPPersistence.save();
        } catch (RuntimeException e) {
            return e.getMessage();
        }
        return "";
    }

    /**
     * <p> The method removes gear to a combo in BikeTourPlus
     * </p>
     * @param gearName name of the gear to remove
     * @param comboName name of the combo in which the gear is removed
     * @return a string stating whether the gear was removed to a combo successfully or if there was an error
     */
    public static String removeGearFromCombo(String gearName, String comboName) {
        // verifies for the conditions to remove gear from a combo
        Combo comboToModify = null;
        if (currentBTP.hasCombos()) { //looks for the right combo
            List<Combo> currentCombos = currentBTP.getCombos();
            for (Combo c : currentCombos) {
                if (c.getName().equalsIgnoreCase(comboName)){
                    comboToModify = currentBTP.getCombo(currentBTP.indexOfCombo(c));
                    break;
                }
            }

            if (comboToModify == null) {
                return "The combo does not exist";
            }
        }

        if (!currentBTP.hasCombos()) {
            return "The combo does not exist";
        }

        if (!currentBTP.hasGear()) { //if no gear exists, doesn't execute rest of code
            return "The piece of gear does not exist";
        }

        if (comboToModify != null && !comboToModify.hasComboItems()){
            return "The piece of gear does not exist";
        }

        if (comboToModify != null && comboToModify.hasComboItems()){
            List<ComboItem> addedComboItems = comboToModify.getComboItems();
            for (ComboItem j : addedComboItems) {
                if (j.getGear().getName().equalsIgnoreCase(gearName)) {
                    if (j.getQuantity() == 1 && addedComboItems.size() <= comboToModify.minimumNumberOfComboItems()) return "A combo must have at least two pieces of gear";
                    if (j.getQuantity() == 1) j.delete();//comboToModify.removeComboItem(j); //removes the right combo item
                    else j.setQuantity(j.getQuantity()-1);
                    if (!comboToModify.isNumberOfComboItemsValid()){ //if there is less comboItems in the combo than the minimum allowed, it deletes the combo
                        comboToModify.delete();
                        try {
                            BTPPersistence.save();
                        } catch (RuntimeException e) {
                            return e.getMessage();
                        }
                        return "";
                    }
                    try {
                        BTPPersistence.save();
                    } catch (RuntimeException e) {
                        return e.getMessage();
                    }
                    return "";
                }
            }
            return "The piece of gear does not exist";
        }


        if (!currentBTP.hasComboItems()) { //cannot remove gear if there are no combo items
            return "The piece of gear does not exist";
        }
        try {
            BTPPersistence.save();
        } catch (RuntimeException e) {
            return e.getMessage();
        }
        return "";
    }
    public static BikeTourPlus getBTP() {
		return currentBTP;
    	
    }

}
