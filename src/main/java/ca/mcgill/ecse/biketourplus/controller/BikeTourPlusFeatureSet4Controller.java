/**
 * @author Simon Li(slsecrets357)
 */
package ca.mcgill.ecse.biketourplus.controller;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse.biketourplus.application.BikeTourPlusApplication;
import ca.mcgill.ecse.biketourplus.model.BikeTour;
import ca.mcgill.ecse.biketourplus.model.BikeTourPlus;
import ca.mcgill.ecse.biketourplus.model.Guide;
import ca.mcgill.ecse.biketourplus.model.Participant;
import ca.mcgill.ecse.biketourplus.persistence.BTPPersistence;

/**
 * This class will focus on the implementation of guide operations in the BikeTourPlus system.
 */
public class BikeTourPlusFeatureSet4Controller {
  /**
   * <p>
   * registers a guide
   * </p>
   * 
   * @param email email address used to identify the guide
   * @param password password of the account
   * @param name name of the guide
   * @param emergencyContact emergency contact
   * @return String: error message, if any
   */
  public static String registerGuide(String email, String password, String name,
      String emergencyContact) {
    // TODO Implement the method, return error message (if any)
    if (password.isEmpty())
      return "Password cannot be empty";
    if (name.isEmpty())
      return "Name cannot be empty";
    if (emergencyContact.isEmpty())
      return "Emergency contact cannot be empty";
    String emailCheck = Utility.validateEmail(email);
    if (!emailCheck.equals("ok"))
      return emailCheck;

    BikeTourPlus b1 = BikeTourPlusApplication.getBikeTourPlus();
    List<Guide> existingGuides = b1.getGuides();
    for (Guide g : existingGuides) {
      if (g.getEmail().equals(email))
        return "Email already linked to a guide account";
    }
    List<Participant> existingParticipants = b1.getParticipants();
    for (Participant p : existingParticipants) {
      if (p.getEmail().equalsIgnoreCase(email))
        return "Email already linked to a participant account";
    }
    try {
      Guide newGuide = new Guide(email, password, name, emergencyContact, b1);
      BTPPersistence.save();
    } catch (RuntimeException e) {
      return e.getMessage();
    }
    return "";
  }

  /**
   * <p>
   * updates a guide
   * </p>
   * 
   * @param email email address used to identify the guide
   * @param newPassword new password
   * @param newName new name
   * @param newEmergencyContact new emergency contact
   * @return String: error message, if any
   */
  public static String updateGuide(String email, String newPassword, String newName,
      String newEmergencyContact) {
    // TODO Implement the method, return error message (if any)
    if (newPassword.isEmpty())
      return "Password cannot be empty";
    if (newName.isEmpty())
      return "Name cannot be empty";
    if (newEmergencyContact.isEmpty())
      return "Emergency contact cannot be empty";
    String emailCheck = Utility.validateEmail(email);
    if (!emailCheck.equals("ok"))
      return emailCheck;
    BikeTourPlus b1 = BikeTourPlusApplication.getBikeTourPlus();
    // Guide guide = b1.User.getWithEmail(email);
    List<Guide> existingGuides = b1.getGuides();
    Guide guide = null;
    for (Guide g : existingGuides) {
      if (g.getEmail().equals(email)) {
        guide = g;
        break;
      }
    }
    if (guide == null)
      return "The guide account does not exist";
    guide.setPassword(newPassword);
    guide.setName(newName);
    guide.setEmergencyContact(newEmergencyContact);
    BTPPersistence.save();
    return "";
  }

  /**
   * <p>
   * deletes a guide
   * </p>
   * 
   * @param email email address used to identify the guide
   * @return nothing
   */
  public static void deleteGuide(String email) {
    // String emailCheck = Utility.validateEmail(email);
    // if(!emailCheck.equals("ok")) return emailCheck;
    BikeTourPlus b1 = BikeTourPlusApplication.getBikeTourPlus();
    List<Guide> existingGuides = b1.getGuides();
    // Guide guide = null;
    for (Guide g : existingGuides) {
      if (g.getEmail().equals(email)) {
        g.delete();
        BTPPersistence.save();
        return;
      }
    }
  }

  public static List<String> getGuides() {
    BikeTourPlus b1 = BikeTourPlusApplication.getBikeTourPlus();
    List<Guide> existingGuides = b1.getGuides();
    List<String> emails = new ArrayList<>();
    for (Guide g : existingGuides) {
      emails.add(g.getEmail());
    }
    return emails;
  }

  public static TOGuide getTOGuide(String email) {
    BikeTourPlus b1 = BikeTourPlusApplication.getBikeTourPlus();
    List<Guide> existingGuides = b1.getGuides();
    for (Guide g : existingGuides) {
      if (g.getEmail().equals(email)) {
        TOGuide g1 =
            new TOGuide(g.getEmail(), g.getName(), g.getPassword(), g.getEmergencyContact());
        return g1;
      }
    }
    return null;
  }

  public static List<String> getParticipants() {
    BikeTourPlus b1 = BikeTourPlusApplication.getBikeTourPlus();
    List<Participant> existingParticipants = b1.getParticipants();
    List<String> emails = new ArrayList<>();
    for (Participant p : existingParticipants) {
      emails.add(p.getEmail());
    }
    return emails;
  }

  public static TOParticipantCost getTOParticipantCost(String email) {
    BikeTourPlus b1 = BikeTourPlusApplication.getBikeTourPlus();
    List<Participant> existingParticipants = b1.getParticipants();
    for (Participant p : existingParticipants) {
      if (p.getEmail().equals(email)) {
        int aTotalCostForGuide = 0;
        BikeTour b = p.getBikeTour();
        if (b != null)
          aTotalCostForGuide =
              b1.getPriceOfGuidePerWeek() * (b.getEndWeek() - b.getStartWeek() + 1);
        int aTotalCostForBookableItems = Utility.getTotalCostItem(p);
        String status;
        if (p.getStatus() == null) {
          status = "NotAssigned";
        } else {
          status = p.getStatusFullName();
        }
        TOParticipantCost g1 = new TOParticipantCost(p.getEmail(), p.getName(), p.getPassword(),
            p.getEmergencyContact(), status, p.getAuthorizationCode(),
            p.getRefundedPercentageAmount(), p.getWeekAvailableFrom(), p.getWeekAvailableUntil(),
            p.getNrWeeks(), aTotalCostForBookableItems,
            aTotalCostForGuide + aTotalCostForBookableItems, p.getLodgeRequired());
        return g1;
      }
    }
    return null;
  }

}
