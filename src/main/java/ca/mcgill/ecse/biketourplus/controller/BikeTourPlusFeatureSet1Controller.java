/**
 * @author Antoine (AntoineDeng)
 */
package ca.mcgill.ecse.biketourplus.controller;

import ca.mcgill.ecse.biketourplus.application.BikeTourPlusApplication;
import ca.mcgill.ecse.biketourplus.model.BikeTour;
import ca.mcgill.ecse.biketourplus.model.BikeTourPlus;
import ca.mcgill.ecse.biketourplus.model.Participant;
import ca.mcgill.ecse.biketourplus.persistence.BTPPersistence;

import java.util.ArrayList;
import java.util.List;

public class BikeTourPlusFeatureSet1Controller {
  private static BikeTourPlus btp = BikeTourPlusApplication.getBikeTourPlus();

  /**
   * <p>
   * updates a manager
   * </p>
   * 
   * @param password new password
   * @return error message, if any
   */
  public static String updateManager(String password) {
    // TODO Implement the method, return error message (if any)
    if (btp == null)
      return "no BTP";
    if (btp.hasManager() == false)
      return "no manager in BTP";
    if (password.equals(""))
      return "Password cannot be empty";
    if (password.length() < 4)
      return "Password must be at least four characters long";
    if (!password.contains("!") && !password.contains("#") && !password.contains("$"))
      return "Password must contain one character out of !#$";
    boolean lower = false;
    boolean upper = false;
    for (int i = 0; i < password.length(); i++) {
      if (Character.isLowerCase(password.charAt(i))) {
        lower = true;
      }
      if (Character.isUpperCase(password.charAt(i))) {
        upper = true;
      }
      if (lower && upper)
        break;
    }
    if (!lower)
      return "Password must contain one lower-case character";
    if (!upper)
      return "Password must contain one upper-case character";
    btp.getManager().setPassword(password);
    try {
      BTPPersistence.save();
    } catch (RuntimeException e) {
      return e.getMessage();
    }
    return "";
  }

  /**
   * <p>
   * get a bike tour plus application
   * </p>
   * 
   * @param id
   * @return TOBikeTour
   */
  public static TOBikeTour getBikeTour(int id) {
    // TODO Implement the method
    TOBikeTour t1 = null;
    if (btp.hasBikeTours() == false)
      return t1;
    List<BikeTour> bikeTourList = btp.getBikeTours();
    for (BikeTour b : bikeTourList) {
      if (b.getId() == id) {
        TOParticipantCost aParticipantCosts = null;
        TOParticipantCost[] allParticipantCosts = new TOParticipantCost[b.getParticipants().size()];
        int i = 0;
        int aTotalCostForGuide =
            btp.getPriceOfGuidePerWeek() * (b.getEndWeek() - b.getStartWeek() + 1);
        // int aTotalCostForBikingTour =
        // b1.getPriceOfGuidePerWeek()*(b.getEndWeek()-b.getStartWeek()+1);
        for (Participant p : b.getParticipants()) {
          String status;
          if (p.getStatus() == null) {
            status = "NotAssigned";
          } else {
            status = p.getStatusFullName();
          }
          int aTotalCostForBookableItems = Utility.getTotalCostItem(p);
          aParticipantCosts = new TOParticipantCost(p.getEmail(), p.getName(), p.getPassword(),
              p.getEmergencyContact(), status, p.getAuthorizationCode(),
              p.getRefundedPercentageAmount(), p.getWeekAvailableFrom(), p.getWeekAvailableUntil(),
              p.getNrWeeks(), aTotalCostForBookableItems,
              aTotalCostForGuide + aTotalCostForBookableItems, p.getLodgeRequired());
          allParticipantCosts[i] = aParticipantCosts;
          i++;
        }
        TOGuide g = new TOGuide(b.getGuide().getEmail(), b.getGuide().getName(),
            b.getGuide().getPassword(), b.getGuide().getEmergencyContact());
        // int aTotalCostForGuide =
        // b1.getPriceOfGuidePerWeek()*(b.getEndWeek()-b.getStartWeek()+1)*b.getParticipants().size();
        t1 = new TOBikeTour(id, b.getStartWeek(), b.getEndWeek(), b.getGuide().getEmail(),
            b.getGuide().getName(), aTotalCostForGuide, b.getParticipants().size(), g,
            allParticipantCosts);
      }
    }
    return t1;
  }

  public static List<Integer> getIds() {
    List<Integer> ids = new ArrayList<Integer>();
    for (BikeTour b : btp.getBikeTours()) {
      ids.add(b.getId());
    }
    return ids;
  }

  public static List<String> getNrWeeks() {
    List<String> weeks = new ArrayList<String>();
    for (int i = 1; i <= btp.getNrWeeks(); i++) {
      weeks.add(Integer.toString(i));
    }
    return weeks;
  }
}
