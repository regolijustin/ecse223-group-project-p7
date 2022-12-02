package ca.mcgill.ecse.biketourplus.javafx.fxml.controllers;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import ca.mcgill.ecse.biketourplus.model.Combo;
import ca.mcgill.ecse.biketourplus.model.ComboItem;
import ca.mcgill.ecse.biketourplus.model.Gear;
import org.controlsfx.control.textfield.TextFields;
import ca.mcgill.ecse.biketourplus.controller.BikeTourPlusFeatureSet2Controller;
import ca.mcgill.ecse.biketourplus.controller.BikeTourPlusFeatureSet6Controller;
import ca.mcgill.ecse.biketourplus.javafx.fxml.BtpFxmlView;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;

public class ViewGearandCombosController {

    @FXML
    private Button addComboButton;
    @FXML
    private Button updateComboButton;
    @FXML
    private Button deleteComboButton;
    @FXML
    private ListView<String> comboList;
    @FXML
    private TextField comboTextField;
    @FXML
    private ListView<String> gearIncludedList;
    @FXML
    private Button addOneButton;
    @FXML
    private Button removeOneButton;
    @FXML
    private TextField quantityTextField;
    @FXML
    private TextField discountTextField;
    @FXML
    private ListView<String> gearToAddList;
    @FXML
    private Button addGearButton;
    @FXML
    private Button removeGearButton;
    @FXML
    private Button refreshComboButton;
    @FXML
    private Button searchComboButton;
    @FXML
    private TextField searchComboField;


    @FXML
    public void initialize() {

        comboList.setItems(ViewUtils.getCombosNames());
        comboList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {viewComboClicked();}
        });
        gearToAddList.setItems(ViewUtils.getGearNames());
        addOneButton.setDisable(true);
        removeOneButton.setDisable(true);
        updateComboButton.setDisable(true);
        deleteComboButton.setDisable(true);
        removeGearButton.setDisable(true);
        quantityTextField.setDisable(true);
        addGearButton.setDisable(true);
        gearToAddList.setDisable(true);
        gearIncludedList.setDisable(true);
        

        //key responses
        EventHandler<MouseEvent> chooseCombo = new EventHandler<MouseEvent>() {
          public void handle(MouseEvent event) {viewComboClicked();}
        };
        comboList.setOnMouseClicked (chooseCombo);
        TextFields.bindAutoCompletion(searchComboField, ViewUtils.getCombosNames());
        EventHandler<KeyEvent> enter = new EventHandler<KeyEvent> () {
          public void handle(KeyEvent keyEvent) {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
              searchComboClicked();
            }
          }
        };
        searchComboField.setOnKeyPressed(enter);

        EventHandler<KeyEvent> down = new EventHandler<KeyEvent>() {
          public void handle(KeyEvent keyEvent) {
            if (keyEvent.getCode().equals(KeyCode.DOWN)) {
              //currentGearList.getSelectionModel().selectNext();
              int index = comboList.getSelectionModel().getSelectedIndex();
              comboList.getSelectionModel().select(index++);
              viewComboClicked();
            }
          }
        };

        EventHandler<KeyEvent> up = new EventHandler<KeyEvent>() {
          public void handle(KeyEvent upEvent) {
            if (upEvent.getCode().equals(KeyCode.UP)) {
              int index = comboList.getSelectionModel().getSelectedIndex();
              comboList.getSelectionModel().select(index--);
              viewComboClicked();
            }
          }
        };

        comboList.setOnKeyPressed(up);    
        comboList.setOnKeyReleased(down);
       // gearIncludedList.setOnKeyPressed();
    }

    public void viewComboClicked() {
        if (comboList.getSelectionModel().getSelectedItem() == null)return;
        String comboName = comboList.getSelectionModel().getSelectedItem();
        Combo combo = ViewUtils.getCombo(comboName);
        String comboDiscount = Integer.toString(combo.getDiscount());
        gearToAddList.setItems(ViewUtils.getGearNames());
        comboTextField.setText(comboName);
        gearIncludedList.setItems(ViewUtils.getComboGearNames(combo));
        discountTextField.setText(comboDiscount);
        gearIncludedList.setVisible(!false);
        gearIncludedList.setDisable(!true);
        updateComboButton.setDisable(!true);
        deleteComboButton.setDisable(!true);
        gearToAddList.setDisable(!true);
        gearIncludedList.setDisable(!true);
        gearIncludedList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {viewGearClicked();}
        });
        gearToAddList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {viewGearToAddClicked();}
        });
    }
    public void viewGearClicked() {
        if (gearIncludedList.getSelectionModel().getSelectedItem() == null) return;
        String comboName = comboList.getSelectionModel().getSelectedItem();
        Combo combo = ViewUtils.getCombo(comboName);
        ObservableList<ComboItem> gearList = ViewUtils.getComboGear(combo);
        String gearName = gearIncludedList.getSelectionModel().getSelectedItem();
        int gearQTY = 0;
        if (gearList != null) {
            for (ComboItem gl : gearList) {
                if (gl.getGear().getName().equalsIgnoreCase(gearName)) {
                    gearQTY = gl.getQuantity();
                }
            }
        }
        gearToAddList.setItems(ViewUtils.getGearNames());
        gearIncludedList.setItems(ViewUtils.getComboGearNames(combo));
        quantityTextField.setText(Integer.toString(gearQTY));
        addOneButton.setDisable(!true);
        removeOneButton.setDisable(!true);
        removeGearButton.setDisable(!true);
    }
    //Event Listener on Button[#addComboButton].onAction
    @FXML
        public void addComboButtonClicked(ActionEvent event) {
        var error = "";
        if (discountTextField.getText().equalsIgnoreCase("")) {
            ViewUtils.showError("A discount is required");
        }
        else {
            error += BikeTourPlusFeatureSet6Controller.addCombo(comboTextField.getText(), Integer.parseInt(discountTextField.getText()));
        
            if(!error.isEmpty()) {
                ViewUtils.showError(error);
            }
            else {
                comboList.setItems(ViewUtils.getCombosNames());
            }
        }
    }
    // Event Listener on Button[#updateComboButton].onAction
    @FXML
    public void updateComboButtonClicked(ActionEvent event) {
        if (comboList.getSelectionModel().getSelectedItem() == null)return;
        String comboName = comboList.getSelectionModel().getSelectedItem();
        var error = "";
        error += BikeTourPlusFeatureSet6Controller.updateCombo(comboName, comboTextField.getText(), Integer.parseInt(discountTextField.getText()));
        
        if(!error.isEmpty()) {
            ViewUtils.showError(error);
        }
        else {
            comboList.setItems(ViewUtils.getCombosNames());
        }
    }
    // Event Listener on Button[#deleteComboButton].onAction
    @FXML
    public void deleteComboButtonClicked(ActionEvent event) {
        if (comboList.getSelectionModel().getSelectedItem() == null)return;
        String comboName = comboList.getSelectionModel().getSelectedItem();
        BikeTourPlusFeatureSet2Controller.deleteCombo(comboName);
        comboList.setItems(ViewUtils.getCombosNames());
        viewGearClicked();
    }
    // Event Listener on Button[#addOneButton].onAction
    @FXML
    public void addOneButtonClicked(ActionEvent event) {
        if (comboList.getSelectionModel().getSelectedItem() == null)return;
        String comboName = comboList.getSelectionModel().getSelectedItem();
        if (gearIncludedList.getSelectionModel().getSelectedItem() == null) return;
        String gearName = gearIncludedList.getSelectionModel().getSelectedItem();
        var error = "";
        error += BikeTourPlusFeatureSet6Controller.addGearToCombo(gearName, comboName);
        
        if(!error.isEmpty()) {
            ViewUtils.showError(error);
        }
        else {
            viewGearClicked();
        }
    }
    // Event Listener on Button[#removeOneButton].onAction
    @FXML
    public void removeOneButtonClicked(ActionEvent event) {
        if (comboList.getSelectionModel().getSelectedItem() == null)return;
        String comboName = comboList.getSelectionModel().getSelectedItem();
        if (gearIncludedList.getSelectionModel().getSelectedItem() == null) return;
        String gearName = gearIncludedList.getSelectionModel().getSelectedItem();
        var error = "";
        error += BikeTourPlusFeatureSet6Controller.removeGearFromCombo(gearName, comboName);
        
        if(!error.isEmpty()) {
            ViewUtils.showError(error);
        }
        else {
            viewGearClicked();
        }
    }
    // Event Listener on Button[#removeGearButton].onAction
    @FXML
    public void removeGearButtonClicked(ActionEvent event) {
        for (int i = Integer.parseInt(quantityTextField.getText()); i >= 0; i--) {
            removeOneButtonClicked(event);
        }
    }
    // Event Listener on Button[#addGearButton].onAction
    @FXML
    public void addGearButtonClicked(ActionEvent event) {
        if (comboList.getSelectionModel().getSelectedItem() == null)return;
        String comboName = comboList.getSelectionModel().getSelectedItem();
        Combo combo = ViewUtils.getCombo(comboName);
        if (gearToAddList.getSelectionModel().getSelectedItem() == null)return;
        String gearName = gearToAddList.getSelectionModel().getSelectedItem();
        Gear gear = ViewUtils.getGear(gearName);
        var error = "";
        ObservableList<ComboItem> existingGear = ViewUtils.getComboGear(combo);
        boolean match = false;
        if (existingGear != null) {
            for (ComboItem ci : existingGear) {
                if (ci.getGear().equals(gear)) {
                    match = true;
                }
            }
        }
        
        if (match) {
            error += BikeTourPlusFeatureSet6Controller.addGearToCombo(gearName, comboName);
            
            if(!error.isEmpty()) {
                ViewUtils.showError(error);
            }
            else {
                viewGearClicked();
            }
        }
        if (!match) {
            combo.addComboItem(1, BikeTourPlusFeatureSet6Controller.getBTP(), gear);
            viewGearClicked();
        }
    }
    public void viewGearToAddClicked() {
        if (gearToAddList.getSelectionModel().getSelectedItem() == null) return;
        addGearButton.setDisable(false);
    }
    
    //added search function
    public void searchComboClicked () {
      String comboSearch = searchComboField.getText();
      for (String s:ViewUtils.getCombosNames()) {
        if (s.equals(comboSearch)) {
          comboList.getSelectionModel().select(comboSearch);
          viewComboClicked();
        }
      }
    }



    
    public void RefreshButtonClicked() {
      BtpFxmlView.getInstance().refresh();
      comboList.setItems(ViewUtils.getCombosNames());
      viewGearClicked();
    }
}
