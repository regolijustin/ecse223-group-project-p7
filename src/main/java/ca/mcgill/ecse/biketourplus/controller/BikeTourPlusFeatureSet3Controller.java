/**
 *
 * @author Romain Teyssier (Roms)
 *
 */

package ca.mcgill.ecse.biketourplus.controller;

import ca.mcgill.ecse.biketourplus.model.Participant;
import ca.mcgill.ecse.biketourplus.model.BikeTourPlus;
import ca.mcgill.ecse.biketourplus.model.BookableItem;
import ca.mcgill.ecse.biketourplus.model.BookedItem;
import ca.mcgill.ecse.biketourplus.model.Combo;
import ca.mcgill.ecse.biketourplus.model.Gear;
import ca.mcgill.ecse.biketourplus.model.Guide;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse.biketourplus.application.BikeTourPlusApplication;
import ca.mcgill.ecse.biketourplus.persistence.BTPPersistence;

public class BikeTourPlusFeatureSet3Controller {

    private static BikeTourPlus currentBTP = BikeTourPlusApplication.getBikeTourPlus();

    /**
     * <p>This method registers a participant to the Bike Tour Plus application.
     * @param email email address used to identify the participant
     * @param password account password
     * @param name name of the participant
     * @param emergencyContact emergency contact
     * @param nrWeeks number of week that the participant would like to participate in the tour
     * @param weekAvailableFrom the participant is available to participate from week number x
     * @param weekAvailableUntil the participant is available to participate until week number y
     * @param lodgeRequired if the participant needs to be lodged before/during/after the biketour
     * @return errors, if any
     */
    public static String registerParticipant(String email, String password, String name, String emergencyContact, int nrWeeks, int weekAvailableFrom, int weekAvailableUntil, boolean lodgeRequired) {
        // TODO Implement the method, return error message (if any)
        if (email.equals("")) return "Email cannot be empty";
        if (email.contains(" ")) return "Email must not contain any spaces";
        if (email.equals("manager@btp.com")) return "Email cannot be manager@btp.com";
        if (email.replace("@","").length()!=email.length()-1) return "Invalid email";
        if (!((email.endsWith("@mail.ca") && email.replace("@mail.ca","")!="") || (email.endsWith("@email.com") && email.replace("@email.com","")!="") || (email.endsWith("@email.ca") && email.replace("@email.ca","")!="") || (email.endsWith("@mail.com") && email.replace("@mail.com","")!="") || (email.endsWith("@hotmail.com") && email.replace("@hotmail.com","")!="") || (email.endsWith("@hotmail.ca") && email.replace("@hotmail.ca","")!="") || (email.endsWith("@yahoo.com") && email.replace("@yahoo.com","")!="") || (email.endsWith("@yahoo.ca") && email.replace("@yahoo.ca","")!=""))) return "Invalid email";
        List<Guide> existingGuides = currentBTP.getGuides();
        for (Guide g : existingGuides) {
            if (g.getEmail().equals(email)) return "Email already linked to a guide account";
        }
        List<Participant> existingParticipants= currentBTP.getParticipants();
        for (Participant p : existingParticipants) {
            if (p.getEmail().equals(email)) return "Email already linked to a participant account";
        }
        if (password.equals("")) return "Password cannot be empty";
        if (name.equals("")) return "Name cannot be empty";
        if (emergencyContact.equals("")) return "Emergency contact cannot be empty";
        if (nrWeeks <= 0) return "Number of weeks must be greater than zero";
        if (nrWeeks > currentBTP.getNrWeeks()) return "Number of weeks must be less than or equal to the number of biking weeks in the biking season";
        if (weekAvailableFrom > currentBTP.getNrWeeks() || weekAvailableFrom <= 0 || weekAvailableUntil > currentBTP.getNrWeeks() || weekAvailableUntil <= 0) return "Available weeks must be within weeks of biking season";
        if (weekAvailableFrom > weekAvailableUntil) return "Week from which one is available must be less than or equal to the week until which one is available";
        if (nrWeeks > weekAvailableUntil-weekAvailableFrom+1) return "Number of weeks must be less than or equal to the number of available weeks";
        Participant newParticipant = new Participant(email, password, name, emergencyContact, nrWeeks, weekAvailableFrom, weekAvailableUntil, lodgeRequired, "", 0, currentBTP);
        try {
            currentBTP.addParticipant(newParticipant);
            BTPPersistence.save();
        } catch (RuntimeException e) {
            return e.getMessage();
        }
        return "";
    }

    /**
     * <p>This method updates the information of a participant registered to the Bike Tour Plus application
     * @param email email address used to identify the participant
     * @param newPassword new password
     * @param newName new name of the participant
     * @param newEmergencyContact new emergency contact
     * @param newNrWeeks new number of week that the participant would like to participate in the tour
     * @param newWeekAvailableFrom the participant is available to participate from week number x
     * @param newWeekAvailableUntil the participant is available to participate until week number y
     * @param newLodgeRequired if the participant needs to be lodged before/during/after the biketour
     * @return errors, if any
     */
    public static String updateParticipant(String email, String newPassword, String newName, String newEmergencyContact, int newNrWeeks, int newWeekAvailableFrom, int newWeekAvailableUntil, boolean newLodgeRequired) {
        // TODO Implement the method, return error message (if any)
        List<Participant> existingParticipants= currentBTP.getParticipants();
        Participant participant = null;
        for (Participant p : existingParticipants) {
            if (p.getEmail().equals(email)) {
                participant=p;
                break;
            }
        }
        if (participant == null) return "The participant account does not exist";
        if (newPassword.equals("")) return "Password cannot be empty";
        if (newName.equals("")) return "Name cannot be empty";
        if (newEmergencyContact.equals("")) return "Emergency contact cannot be empty";
        if (newNrWeeks <= 0) return "Number of weeks must be greater than zero";
        if (newNrWeeks > currentBTP.getNrWeeks()) return "Number of weeks must be less than or equal to the number of biking weeks in the biking season";
        if (newWeekAvailableFrom > currentBTP.getNrWeeks() || newWeekAvailableFrom <= 0 || newWeekAvailableUntil > currentBTP.getNrWeeks() || newWeekAvailableUntil <= 0) return "Available weeks must be within weeks of biking season";
        if (newWeekAvailableFrom > newWeekAvailableUntil) return "Week from which one is available must be less than or equal to the week until which one is available";
        if (newNrWeeks > newWeekAvailableUntil-newWeekAvailableFrom+1) return "Number of weeks must be less than or equal to the number of available weeks";
        participant.setPassword(newPassword);
        participant.setName(newName);
        participant.setEmergencyContact(newEmergencyContact);
        participant.setNrWeeks(newNrWeeks);
        participant.setWeekAvailableFrom(newWeekAvailableFrom);
        participant.setWeekAvailableUntil(newWeekAvailableUntil);
        participant.setLodgeRequired(newLodgeRequired);
        try {
            BTPPersistence.save();
        } catch (RuntimeException e) {
            return e.getMessage();
        }
        return "";
    }

    /**
     * <p>This method adds a gear or a combo to a registered Participant
     * @param email email address used to identify the participant
     * @param bookableItemName the name of the gear/combo that the participant wants to register
     * @return errors, if any
     */
    public static String addBookableItemToParticipant(String email, String bookableItemName) {
        // TODO Implement the method, return error message (if any)
        List<Participant> existingParticipants = currentBTP.getParticipants();
        Participant participant = null;
        for (Participant p : existingParticipants) {
            if (p.getEmail().equals(email)) {
                participant = p;
                break;
            }
        }
        if (participant == null) return "The participant does not exist";
        List<Gear> gears = currentBTP.getGear();
        List<Combo> combos = currentBTP.getCombos();
        BookableItem item = null;
        for (Gear g : gears) {
            if (g.getName().equals(bookableItemName)) {
                item = g;
                break;
            }
        }
        if (item == null) {
            for (Combo combo : combos) {
                if (combo.getName().equals(bookableItemName)) {
                    item = combo;
                    break;
                }
            }
        }
        if (item == null) {
            return ("The piece of gear or combo does not exist");
        }
        List<BookedItem> participantBookedItems = participant.getBookedItems();
        for (BookedItem b : participantBookedItems) {
            if (b.getItem().equals(item)) {
                b.setQuantity(b.getQuantity()+1);
                try {
                    BTPPersistence.save();
                } catch (RuntimeException e) {
                    return e.getMessage();
                }
                return "";
            }
        }
        try {
            currentBTP.addBookedItem(1, participant, item);
            BTPPersistence.save();
        } catch (RuntimeException e) {
            return e.getMessage();
        }
        return "";
    }

    /**
     * <p>This method removes a gear or combo from a registered Participant
     * @param email email address used to identify the participant
     * @param bookableItemName the name of the gear/combo that the participant wants to unregister
     */
    public static String removeBookableItemFromParticipant(String email, String bookableItemName) {
        List<Participant> existingParticipants= currentBTP.getParticipants();
        Participant participant = null;
        List<BookedItem> items = null;
        for (Participant p : existingParticipants) {
            if (p.getEmail().equals(email)) {
                participant=p;
                items = p.getBookedItems();
                break;
            }
        }
        if (participant == null) return "The participant does not exist";
        if(items!=null) {
            for(BookedItem i: items) {
                if(i.getItem().getName().equals(bookableItemName)) {
                    if (i.getQuantity() == 1) {
                        i.delete();
                        try {
                            BTPPersistence.save();
                        } catch (RuntimeException e) {
                            return e.getMessage();
                        }
                    } else {
                        i.setQuantity(i.getQuantity()-1);
                    }
                    BTPPersistence.save();
                    return "";
                }
            }
        }
        BTPPersistence.save();
        return "";
    }
    
    public static List<String> getBookedItemNamesForParticipant(String email){
    	Participant participant = null;
    	List<Participant> existingParticipants = currentBTP.getParticipants();
    	for (Participant b : existingParticipants) {
    		if (b.getEmail().equals(email)) participant=b;
    	}
		List<BookedItem> bookedItems = participant.getBookedItems();
		List<String> bookedItemsNames = new ArrayList<>();
		for (BookedItem p : bookedItems) {
			bookedItemsNames.add(p.getItem().getName());
		}
		return bookedItemsNames;
	}
    
    public static List<String> getBookedItemsQuantities(String email){
    	List<String> itemQuantities = new ArrayList<>();
    	Participant participant = null;
    	List<Participant> existingParticipants = currentBTP.getParticipants();
    	for (Participant b : existingParticipants) {
    		if (b.getEmail().equals(email)) participant=b;
    	}
		List<BookedItem> bookedItems = participant.getBookedItems();
    	for (BookedItem b : bookedItems) {
    		itemQuantities.add(Integer.toString(b.getQuantity()));
    	}
		return itemQuantities;
	}
    
    public static String getBookedItemQuantity(String email, String name) {
    	String quantity="";
    	Participant participant = null;
    	List<Participant> existingParticipants = currentBTP.getParticipants();
    	for (Participant b : existingParticipants) {
    		if (b.getEmail().equals(email)) participant=b;
    	}
		List<BookedItem> bookedItems = participant.getBookedItems();
		for (BookedItem b : bookedItems) {
			if (b.getItem().getName().equals(name)) {
				quantity=Integer.toString(b.getQuantity());
				break;
			}
		}
    	return quantity;
    }
    public static List<TOBookedItem> getTOBookedItems(String email) {
		BikeTourPlus b1 = BikeTourPlusApplication.getBikeTourPlus();
		TOBookedItem tbi;
		List<TOBookedItem> items = new ArrayList<TOBookedItem>();
		Participant realParticipant = null;
		for (Participant p : b1.getParticipants()) {
			if (p.getEmail().equals(email)) {
				realParticipant = p;
				break;
			}
		}
		if (realParticipant==null){
			return items;
		}
		for(BookedItem bi: realParticipant.getBookedItems()){
			tbi = new TOBookedItem(bi.getItem().getName(),bi.getQuantity());
			items.add(tbi);
		}
		return items;
	}
}