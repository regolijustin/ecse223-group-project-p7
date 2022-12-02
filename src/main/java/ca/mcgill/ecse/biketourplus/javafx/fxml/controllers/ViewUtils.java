package ca.mcgill.ecse.biketourplus.javafx.fxml.controllers;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse.biketourplus.application.BikeTourPlusApplication;
import ca.mcgill.ecse.biketourplus.controller.*;
import ca.mcgill.ecse.biketourplus.javafx.fxml.BtpFxmlView;
import ca.mcgill.ecse.biketourplus.model.BikeTourPlus;
import ca.mcgill.ecse.biketourplus.model.Combo;
import ca.mcgill.ecse.biketourplus.model.ComboItem;
import ca.mcgill.ecse.biketourplus.model.Gear;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ViewUtils {//Code from tutorial 9

  /** Calls the controller and shows an error, if applicable. */
  public static boolean callController(String result) {
    if (result.isEmpty()) {
      BtpFxmlView.getInstance().refresh();
      return true;
    }
    showError(result);
    return false;
  }

  /** Calls the controller and returns true on success. This method is included for readability. */
  public static boolean successful(String controllerResult) {
    return callController(controllerResult);
  }

  /**
   * Creates a popup window.
   *
   * @param title: title of the popup window
   * @param message: message to display
   */
  public static void makePopupWindow(String title, String message) {
    Stage dialog = new Stage();
    dialog.initModality(Modality.APPLICATION_MODAL);
    VBox dialogPane = new VBox();

    // create UI elements
    Text text = new Text(message);
    Button okButton = new Button("OK");
    okButton.setOnAction(a -> dialog.close());

    // display the popup window
    int innerPadding = 10; // inner padding/spacing
    int outerPadding = 100; // outer padding
    dialogPane.setSpacing(innerPadding);
    dialogPane.setAlignment(Pos.CENTER);
    dialogPane.setPadding(new Insets(innerPadding, innerPadding, innerPadding, innerPadding));
    dialogPane.getChildren().addAll(text, okButton);
    Scene dialogScene = new Scene(dialogPane, outerPadding + 5 * message.length(), outerPadding);
    dialog.setScene(dialogScene);
    dialog.setTitle(title);
    dialog.show();
  }

  public static void showError(String message) {
    makePopupWindow("Error", message);
  }

  public static ObservableList<Integer> getIds() {
    return FXCollections.observableList(BikeTourPlusFeatureSet1Controller.getIds());
  }
  public static ObservableList<String> getParticiantNames(TOBikeTour bt) {
    List<String> names = new ArrayList<String>();
    for (TOParticipantCost c : bt.getParticipantCosts()) {
      names.add(c.getParticipantName());
    }
    return FXCollections.observableList(names);
  }
  public static ObservableList<String> getParticipantEmails() {
    return FXCollections.observableList(BikeTourPlusFeatureSet4Controller.getParticipants());
  }
  public static ObservableList<String> getGuideEmails() {
    return FXCollections.observableList(BikeTourPlusFeatureSet4Controller.getGuides());
  }
  public static ObservableList<String> getParticipantBookedItems(String email) {
	  return FXCollections.observableList(BikeTourPlusFeatureSet3Controller.getBookedItemNamesForParticipant(email));
  }
  public static ObservableList<String> getBookedItemsQuantities(String email) {
	  return FXCollections.observableList(BikeTourPlusFeatureSet3Controller.getBookedItemsQuantities(email));
  }
  
  public static ObservableList<Combo> getCombos() {
    if (BikeTourPlusFeatureSet6Controller.getBTP().hasCombos()) {
      return FXCollections.observableList(BikeTourPlusFeatureSet6Controller.getBTP().getCombos());
    } else {
      return null;
    }
  }
  public static Combo getCombo(String name) {
    Combo returnedCombo = null;
    if (BikeTourPlusFeatureSet6Controller.getBTP().hasCombos()) {
      List<Combo> combos = BikeTourPlusFeatureSet6Controller.getBTP().getCombos();
      for (Combo c : combos) {
        if (c.getName().equalsIgnoreCase(name)) {
          returnedCombo = c;
          return returnedCombo;
        }
      }
    }
    return returnedCombo;
  }
  public static ObservableList<ComboItem> getComboGear(Combo combo) {
    if (BikeTourPlusFeatureSet6Controller.getBTP().hasComboItems()) {
      return FXCollections.observableList(combo.getComboItems());
    }
    return null;
  }
  public static ObservableList<String> getComboGearNames(Combo combo) {
    if (BikeTourPlusFeatureSet6Controller.getBTP().hasComboItems()) {
      ObservableList<ComboItem> listComboItems = getComboGear(combo);
      ArrayList<String> list = new ArrayList<>();
      for (ComboItem ci : listComboItems) {
        list.add(ci.getGear().getName());
      }
      ObservableList<String> listComboGearName = FXCollections.observableArrayList(list);
      return listComboGearName;
    }
    return null;
  }

  public static ObservableList<String> getCombosNames() {
    if (BikeTourPlusFeatureSet6Controller.getBTP().hasCombos()) {
      ObservableList<Combo> listCombo = getCombos();
      ArrayList<String> list = new ArrayList<>();
      for (Combo c : listCombo) {
        list.add(c.getName());
      }
      ObservableList<String> listComboName = FXCollections.observableArrayList(list);
      return listComboName;
    }
    else {
      ArrayList<String> list = new ArrayList<>();
      return FXCollections.observableArrayList(list);
    }
  }
  public static ObservableList<Gear> getAvailableGear() {

    if (BikeTourPlusFeatureSet6Controller.getBTP().hasGear()) {
      ObservableList<Gear> listGear = FXCollections.observableArrayList(BikeTourPlusFeatureSet6Controller.getBTP().getGear());

      return listGear;
    }
    return null;
  }
  public static ObservableList<String> getGearNames() {
    if (BikeTourPlusFeatureSet6Controller.getBTP().hasGear()) {
      ObservableList<Gear> listGear = getAvailableGear();
      ArrayList<String> list = new ArrayList<>();
      for (Gear g : listGear) {
        list.add(g.getName());
      }
      ObservableList<String> listAvailableGear = FXCollections.observableArrayList(list);
      return listAvailableGear;
    }
    return null;
  }
  public static Gear getGear(String name) {
    Gear returnedGear = null;
    if (BikeTourPlusFeatureSet6Controller.getBTP().hasGear()) {
      List<Gear> gear = BikeTourPlusFeatureSet6Controller.getBTP().getGear();
      for (Gear g : gear) {
        if (g.getName().equalsIgnoreCase(name)) {
          returnedGear = g;
          return returnedGear;
        }
      }
    }
    return returnedGear;
  }
  
  public static ObservableList<String> getExistingGears() {
    BikeTourPlus btp = BikeTourPlusApplication.getBikeTourPlus();
    List<Gear> gearList = btp.getGear();
    List<String> gearNameList = new ArrayList<String>();
    for (Gear g : gearList) {
      if (g.equals(null)) {
        gearList.remove(g);
      }
      else {
        gearNameList.add(g.getName()); 
      }
    }
    return FXCollections.observableList(gearNameList);
  }
  
  public static ObservableList<String> getWeeks() {
  	return FXCollections.observableList(BikeTourPlusFeatureSet1Controller.getNrWeeks());
  }
}