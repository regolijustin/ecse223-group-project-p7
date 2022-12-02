/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.biketourplus.controller;

// line 33 "../../../../../BikeTourPlusTransferObjects.ump"
public class TOGuide
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOGuide Attributes
  private String guideEmail;
  private String guideName;
  private String guidePassword;
  private String guideEmergencyContact;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOGuide(String aGuideEmail, String aGuideName, String aGuidePassword, String aGuideEmergencyContact)
  {
    guideEmail = aGuideEmail;
    guideName = aGuideName;
    guidePassword = aGuidePassword;
    guideEmergencyContact = aGuideEmergencyContact;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public String getGuideEmail()
  {
    return guideEmail;
  }

  public String getGuideName()
  {
    return guideName;
  }

  public String getGuidePassword()
  {
    return guidePassword;
  }

  public String getGuideEmergencyContact()
  {
    return guideEmergencyContact;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "guideEmail" + ":" + getGuideEmail()+ "," +
            "guideName" + ":" + getGuideName()+ "," +
            "guidePassword" + ":" + getGuidePassword()+ "," +
            "guideEmergencyContact" + ":" + getGuideEmergencyContact()+ "]";
  }
}