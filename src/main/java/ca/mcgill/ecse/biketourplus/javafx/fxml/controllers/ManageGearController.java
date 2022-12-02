package ca.mcgill.ecse.biketourplus.javafx.fxml.controllers;

import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import javafx.scene.control.ListView;

import javafx.scene.control.TextField;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;


import static ca.mcgill.ecse.biketourplus.javafx.fxml.controllers.ViewUtils.successful;
import org.controlsfx.control.textfield.TextFields;
import ca.mcgill.ecse.biketourplus.controller.*;
import ca.mcgill.ecse.biketourplus.javafx.fxml.BtpFxmlView;

public class ManageGearController {
  @FXML private TextField gearNameField;
  @FXML private TextField gearPriceField;
  @FXML private ListView<String> currentGearList;
  @FXML private Button goUpdateGearButton;
  @FXML private Button goAddGearButton;
  @FXML private Button goDeleteGearButton;
  @FXML private Button refreshButton;
  @FXML private Button searchGearButton;
  @FXML private TextField gearSearchField;
  @FXML private Text actionMessage;
  
  @FXML private GridPane gearPane;
  @FXML private GridPane comboPane;
  @FXML private Button manageGearButton;
  @FXML private Button manageComboButton;
  
  public void initialize() {
    
    comboPane.setVisible(false);
    comboPane.setDisable(true);
    
    //gearPane initialization
    
   
    if (!ViewUtils.getExistingGears().isEmpty()) {
      currentGearList.setItems(ViewUtils.getExistingGears());
      currentGearList.getSelectionModel().select(0);
      gearInlistClicked();

    }
    EventHandler<MouseEvent> chooseGear = new EventHandler<MouseEvent>() {
      public void handle(MouseEvent event) {gearInlistClicked();}
    };
    currentGearList.setOnMouseClicked (chooseGear);
    TextFields.bindAutoCompletion(gearSearchField, ViewUtils.getExistingGears());
    EventHandler<KeyEvent> enter = new EventHandler<KeyEvent> () {
      public void handle(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
          searchGearButtonClicked();
        }
      }
    };
    gearSearchField.setOnKeyPressed(enter);
    
    EventHandler<KeyEvent> down = new EventHandler<KeyEvent>() {
      public void handle(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.DOWN)) {
          //currentGearList.getSelectionModel().selectNext();
          int index = currentGearList.getSelectionModel().getSelectedIndex();
          currentGearList.getSelectionModel().select(index++);
          gearInlistClicked();
        }
      }
    };
    
    EventHandler<KeyEvent> up = new EventHandler<KeyEvent>() {
      public void handle(KeyEvent upEvent) {
        if (upEvent.getCode().equals(KeyCode.UP)) {
          //currentGearList.getSelectionModel().selectPrevious();
          int index = currentGearList.getSelectionModel().getSelectedIndex();
          currentGearList.getSelectionModel().select(index--);
          gearInlistClicked();
        }
      }
    };
   
    currentGearList.setOnKeyPressed(up);    
    currentGearList.setOnKeyReleased(down);
    
  }

  // Event Listener on Button[#goUpdateGearButton].onAction
  @FXML
  public void goUpdateGearButtonClicked(ActionEvent event) {
     
    String oldGearName = currentGearList.getSelectionModel().getSelectedItem();
    if (oldGearName == null) {
      ViewUtils.showError("Please select a gear in list");
      return;
    }
    String newGearName = gearNameField.getText();
    int newPrice = Integer.parseInt(gearPriceField.getText());
    String result = BikeTourPlusFeatureSet5Controller.updateGear(oldGearName, newGearName, newPrice);
    if (result.isBlank()) {
      actionMessage.setText("Updated successfully");
      currentGearList.setItems(ViewUtils.getExistingGears());
      BtpFxmlView.getInstance().refresh();
      currentGearList.getSelectionModel().select(newGearName);
      gearInlistClicked();
    }
    else {
      ViewUtils.showError(result);
      currentGearList.setItems(ViewUtils.getExistingGears());
      BtpFxmlView.getInstance().refresh();
      currentGearList.getSelectionModel().select(oldGearName);
      gearInlistClicked();
      
    }
    
    
    
  }
  // Event Listener on Button[#goDeleteGearButton].onAction
  @FXML
  public void goDeleteGearButtonClicked(ActionEvent event) {
      
    String gearName = gearNameField.getText();
    int listIndex = ViewUtils.getExistingGears().indexOf(gearName);
    String result = BikeTourPlusFeatureSet5Controller.deleteGear(gearName);
    if (result.isBlank()) {
      actionMessage.setText("Deleted successfully");
    }
    else  {
      ViewUtils.showError(result);
    }
    currentGearList.setItems(ViewUtils.getExistingGears());
    currentGearList.getSelectionModel().select(listIndex+1);
    BtpFxmlView.getInstance().refresh();
  }
  
  // Event Listener on Button[#goAddGearButton].onAction
  @FXML
  public void goAddGearButtonClicked(ActionEvent event) {
    
    String newGearName = gearNameField.getText();
    Integer newGearPrice = Integer.parseInt(gearPriceField.getText());
    
    String result = BikeTourPlusFeatureSet5Controller.addGear(newGearName, newGearPrice);
    if (result.isBlank()) {
      
      actionMessage.setText("Added successfully");
      
      currentGearList.setItems(ViewUtils.getExistingGears());
      currentGearList.getSelectionModel().select(newGearName);
      gearInlistClicked();
    }
    else {
          
          ViewUtils.showError(BikeTourPlusFeatureSet5Controller.addGear(newGearName, newGearPrice));
        }
    
    
    
    BtpFxmlView.getInstance().refresh();
  }
  
  public void manageGearButtonClicked(ActionEvent event) {
    gearPane.setVisible(true);
    gearPane.setDisable(false);
    comboPane.setVisible(false);
    comboPane.setDisable(true);
  }
  
  public void manageComboButtonClicked(ActionEvent event) {
    comboPane.setVisible(true);
    comboPane.setDisable(false);
    gearPane.setVisible(false);
    gearPane.setDisable(true);
  }
  /*
  public void goAddGearButtonClicked (ActionEvent event) {
    addingGearButton.setVisible(true);
    currentGearList.getSelectionModel().clearSelection();
    gearNameField.setText("");
    gearPriceField.setText("");
    BtpFxmlView.getInstance().refresh();
  }
 */
  public void searchGearButtonClicked () {
    String gearSearch = gearSearchField.getText();
    for (String s:ViewUtils.getExistingGears()) {
      if (s.equals(gearSearch)) {
        currentGearList.getSelectionModel().select(gearSearch);
        gearInlistClicked();
      }
    }
  }
  
  public void gearInlistClicked () {
    if (currentGearList.getSelectionModel().getSelectedItem().equals(null)) {
      return;
    }
    
    String gearName = currentGearList.getSelectionModel().getSelectedItem();
    String gearPrice = Integer.toString(AccessGear.getPricePerWeek(gearName));
    
    gearNameField.setText(gearName);
    gearPriceField.setText(gearPrice);
  }
  
  public void RefreshButtonClicked() {
    BtpFxmlView.getInstance().refresh();
    currentGearList.setItems(ViewUtils.getExistingGears());
  }
}
