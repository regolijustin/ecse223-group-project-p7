package ca.mcgill.ecse.biketourplus.application;

import ca.mcgill.ecse.biketourplus.javafx.fxml.BtpFxmlView;
import ca.mcgill.ecse.biketourplus.model.BikeTourPlus;
import ca.mcgill.ecse.biketourplus.persistence.BTPPersistence;
import javafx.application.Application;

public class BikeTourPlusApplication {

  private static BikeTourPlus bikeTourPlus;
  public static final boolean DARK_MODE = true;
  
  public static void main(String[] args) {
	  Application.launch(BtpFxmlView.class, args);
  }
  
  public static BikeTourPlus getBikeTourPlus() {
    if (bikeTourPlus == null) {
      // these attributes are default, you should set them later with the setters
      bikeTourPlus = BTPPersistence.load();
    }
    return bikeTourPlus;
  }
}