/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.biketourplus.controller;

// line 16 "../../../../../BikeTourPlusTransferObjects.ump"
public class TOParticipantCost
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOParticipantCost Attributes
  private String participantEmail;
  private String participantName;
  private String participantPassword;
  private String participantEmergencyContact;
  private String status;
  private String participantAuthorizationCode;
  private int participantRefund;
  private int availabilityFrom;
  private int availabilityTo;
  private int nrWeeks;
  private int totalCostForBookableItems;
  private int totalCostForBikingTour;
  private boolean lodgeRequired;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOParticipantCost(String aParticipantEmail, String aParticipantName, String aParticipantPassword, String aParticipantEmergencyContact, String aStatus, String aParticipantAuthorizationCode, int aParticipantRefund, int aAvailabilityFrom, int aAvailabilityTo, int aNrWeeks, int aTotalCostForBookableItems, int aTotalCostForBikingTour, boolean aLodgeRequired)
  {
    participantEmail = aParticipantEmail;
    participantName = aParticipantName;
    participantPassword = aParticipantPassword;
    participantEmergencyContact = aParticipantEmergencyContact;
    status = aStatus;
    participantAuthorizationCode = aParticipantAuthorizationCode;
    participantRefund = aParticipantRefund;
    availabilityFrom = aAvailabilityFrom;
    availabilityTo = aAvailabilityTo;
    nrWeeks = aNrWeeks;
    totalCostForBookableItems = aTotalCostForBookableItems;
    totalCostForBikingTour = aTotalCostForBikingTour;
    lodgeRequired = aLodgeRequired;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public String getParticipantEmail()
  {
    return participantEmail;
  }

  public String getParticipantName()
  {
    return participantName;
  }

  public String getParticipantPassword()
  {
    return participantPassword;
  }

  public String getParticipantEmergencyContact()
  {
    return participantEmergencyContact;
  }

  public String getStatus()
  {
    return status;
  }

  public String getParticipantAuthorizationCode()
  {
    return participantAuthorizationCode;
  }

  public int getParticipantRefund()
  {
    return participantRefund;
  }

  public int getAvailabilityFrom()
  {
    return availabilityFrom;
  }

  public int getAvailabilityTo()
  {
    return availabilityTo;
  }

  public int getNrWeeks()
  {
    return nrWeeks;
  }

  public int getTotalCostForBookableItems()
  {
    return totalCostForBookableItems;
  }

  public int getTotalCostForBikingTour()
  {
    return totalCostForBikingTour;
  }

  public boolean getLodgeRequired()
  {
    return lodgeRequired;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isLodgeRequired()
  {
    return lodgeRequired;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "participantEmail" + ":" + getParticipantEmail()+ "," +
            "participantName" + ":" + getParticipantName()+ "," +
            "participantPassword" + ":" + getParticipantPassword()+ "," +
            "participantEmergencyContact" + ":" + getParticipantEmergencyContact()+ "," +
            "status" + ":" + getStatus()+ "," +
            "participantAuthorizationCode" + ":" + getParticipantAuthorizationCode()+ "," +
            "participantRefund" + ":" + getParticipantRefund()+ "," +
            "availabilityFrom" + ":" + getAvailabilityFrom()+ "," +
            "availabilityTo" + ":" + getAvailabilityTo()+ "," +
            "nrWeeks" + ":" + getNrWeeks()+ "," +
            "totalCostForBookableItems" + ":" + getTotalCostForBookableItems()+ "," +
            "totalCostForBikingTour" + ":" + getTotalCostForBikingTour()+ "," +
            "lodgeRequired" + ":" + getLodgeRequired()+ "]";
  }
}