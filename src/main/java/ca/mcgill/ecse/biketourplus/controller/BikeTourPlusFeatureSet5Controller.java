

package ca.mcgill.ecse.biketourplus.controller;


import ca.mcgill.ecse.biketourplus.application.BikeTourPlusApplication;
import ca.mcgill.ecse.biketourplus.model.*;
import ca.mcgill.ecse.biketourplus.persistence.BTPPersistence;

import java.util.List;

/**
 * This class will focus on the implementation of gear operations in the BikeTourPlus system.
 *
 * @author Xinya Lin (lin-xinya)
 */

public class BikeTourPlusFeatureSet5Controller {

  /**
   * <p>
   * This method allows to add a bookable gear in the biketourplus system.
   * </p>
   *
   * @param name The name for the gear
   * @param pricePerWeek The renting price of the gear for a week
   * @return String： Whether the gear has been added or else.
   */

  private static BikeTourPlus b1 = BikeTourPlusApplication.getBikeTourPlus();
  public static String addGear(String name, int pricePerWeek) {
    // TODO Implement the method, return error message (if any)
    if (name.isBlank() == true) {
      return "The name must not be empty";
    }
    if (pricePerWeek < 0) {
      return "The price per week must be greater than or equal to 0";
    }


    List<Gear> existingGearList = b1.getGear();
    List<Combo> existingComboList = b1.getCombos();
    for (Gear gearX : existingGearList) {


      if (gearX.getName().equalsIgnoreCase(name)) {

        return "A piece of gear with the same name already exists";
      }

    }
    for (Combo comboX : existingComboList) {


      if (comboX.getName().equalsIgnoreCase(name)) {

        return "A combo with the same name already exists";
      }

    }
    Gear newGear = new Gear(name, pricePerWeek, b1);
    try {
      b1.addGear(newGear);
      BTPPersistence.save();
    } catch (RuntimeException e) {
      return e.getMessage();
    }


    return "";
  }

  /**
   * <p>
   * This method allows to update a bookable gear's information
   * </p>
   *
   * @param oldName The current name for the gear
   * @param newName The new name for the gear
   * @param newPricePerWeek The new renting price of the gear for a week
   * @return String: Whether the gear's info has been updated or else.
   */
  public static String updateGear(String oldName, String newName, int newPricePerWeek) {
    // TODO Implement the method, return error message (if any)
    if (oldName.isBlank() == true) {
      return "The name must not be empty";
    }
    if (newName.isBlank() == true) {
      return "The name must not be empty";
    }
    if (newPricePerWeek < 0) {
      return "The price per week must be greater than or equal to 0";
    }

    //BikeTourPlus b1 = BikeTourPlusApplication.getBikeTourPlus();
    List<Gear> existingGearList = b1.getGear();
    List<Combo> existingComboList = b1.getCombos();
    Gear toUpGear = null;
    for (Combo comboX : existingComboList) {


      if (comboX.getName().equalsIgnoreCase(newName)) {

        return "A combo with the same name already exists";
      }

    }
    for (Gear gearX : existingGearList) {

      if (gearX.getName().equalsIgnoreCase(oldName)) {

        toUpGear = gearX;
        
      }
      if (!oldName.equalsIgnoreCase(newName)) {
        if (gearX.getName().equalsIgnoreCase(newName)) {
          return "A piece of gear with the same name already exists";
        }
      }

    }
    if (toUpGear == null) {
      return "The piece of gear does not exist";
    }
    if (!oldName.equalsIgnoreCase(newName)) {
      toUpGear.setName(newName);
    }
    if (toUpGear.getPricePerWeek()!=newPricePerWeek){
      toUpGear.setPricePerWeek(newPricePerWeek);
    }

    BTPPersistence.save();
    return "";
  }

  /**
   * <p>
   * This method allows to delete a bookable gear in the biketourplus system.
   * </p>
   *
   * @param name The name for the gear
   * @return String: Whether the gear has been deleted or else.
   */
  public static String deleteGear(String name) {
    // TODO Implement the method, return error message (if any)
    if (name.isBlank() == true) {
      return "The name must not be empty";
    }
    //BikeTourPlus b1 = BikeTourPlusApplication.getBikeTourPlus();
    List<Gear> existingGearList = b1.getGear();
    List<ComboItem> existingGearinCombo = b1.getComboItems();
    Gear toDelGear = null;
    for (ComboItem itemX : existingGearinCombo) {
      if (itemX.getGear().getName().equalsIgnoreCase(name)) {
        return "The piece of gear is in a combo and cannot be deleted";
      }
    }

    for (Gear gearX : existingGearList) {

      if (gearX.getName().equalsIgnoreCase(name)) {

        toDelGear = gearX;
        toDelGear.delete();
        try {
          BTPPersistence.save();
        } catch (RuntimeException e) {
          return e.getMessage();
        }

        break;
      }
    }



    return "";
  }

}
