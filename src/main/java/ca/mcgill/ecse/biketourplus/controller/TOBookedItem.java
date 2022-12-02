/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.biketourplus.controller;

// line 41 "../../../../../BikeTourPlusTransferObjects.ump"
public class TOBookedItem
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOBookedItem Attributes
  private String bookedItemName;
  private int bookedItemQuantity;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOBookedItem(String aBookedItemName, int aBookedItemQuantity)
  {
    bookedItemName = aBookedItemName;
    bookedItemQuantity = aBookedItemQuantity;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public String getBookedItemName()
  {
    return bookedItemName;
  }

  public int getBookedItemQuantity()
  {
    return bookedItemQuantity;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "bookedItemName" + ":" + getBookedItemName()+ "," +
            "bookedItemQuantity" + ":" + getBookedItemQuantity()+ "]";
  }
}