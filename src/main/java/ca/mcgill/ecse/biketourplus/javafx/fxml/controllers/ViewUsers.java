/**
 * @author Simon Li(slsecrets357)
 */
package ca.mcgill.ecse.biketourplus.javafx.fxml.controllers;

import static ca.mcgill.ecse.biketourplus.javafx.fxml.controllers.ViewUtils.successful;

import java.util.List;

import org.controlsfx.control.textfield.TextFields;

import ca.mcgill.ecse.biketourplus.controller.*;
import ca.mcgill.ecse.biketourplus.javafx.fxml.BtpFxmlView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ViewUsers {
  private String code;
  @FXML
  private Button searchButton;
  @FXML
  private TextField emailSearchField;
  @FXML
  private Label message;
  @FXML
  private TextField guideEmailTextField;
  @FXML
  private TextField guideNameTextField;
  @FXML
  private TextField guideEmergencyContactTextField;
  @FXML
  private PasswordField guidePasswordTextField;
  @FXML
  private ListView<String> guideList;
  @FXML
  private ListView<String> participantList;
  @FXML
  private TextField participantEmailTextField;
  @FXML
  private TextField participantNameTextField;
  @FXML
  private TextField participantEmergencyContactTextField;
  @FXML
  private PasswordField participantPasswordTextField;
  @FXML
  private TextField participantNrWeeksTextField;
  @FXML
  private TextField participantAvailableFromTextField;
  @FXML
  private TextField participantAvailableToTextField;
  @FXML
  private CheckBox lodgeCheckBox;
  @FXML
  private Button registerGuideButton;
  @FXML
  private Button updateGuideButton;
  @FXML
  private Button deleteGuideButton;
  @FXML
  private Button switchButton;
  @FXML
  private Button refreshButton;
  @FXML
  private Button payButton;
  @FXML
  private Button cancelButton;
  @FXML
  private Button finishButton;
  @FXML
  private Button gearButton;
  @FXML
  private Label participantStatusLabel;
  @FXML
  private Label participantCostLabel;
  @FXML
  private Pane viewUsers;
  @FXML
  private Pane mainControllerPage;
  /**
   * <p>
   * initiate everything and setup keyboard events
   * </p>
   * 
   * @param
   * @return void
   */
  @FXML
  public void initialize() {
    // Lighting l = new Lighting();
    // Light.Distant light = new Light.Distant();
    // light.setColor(Color.web("0x00b6b6",1.0));
    // l.setLight(light);
    // message.getParent().setEffect(l);
//	  for (Node n : viewUsers.getChildren()) {
//		  System.out.println(n.getId());
//	      if (n.getId().equals("mainControllerPage")) {
//	        n.setDisable(false);
//	        n.setVisible(true);
//	      } else {
//	        n.setDisable(true);
//	        n.setVisible(false);
//	      }
//	    }
    if (ViewUtils.getGuideEmails() != null) {
      guideList.setItems(ViewUtils.getGuideEmails());
    }
    guideList.setOnMouseClicked(new EventHandler<MouseEvent>() {
      public void handle(MouseEvent event) {
        viewGuideClicked();
      }
    });
    if (ViewUtils.getParticipantEmails() != null) {
      participantList.setItems(ViewUtils.getParticipantEmails());
    }
    participantList.setOnMouseClicked(new EventHandler<MouseEvent>() {
      public void handle(MouseEvent event) {
        viewParticipantClicked();
      }
    });
    guideList.setVisible(false);
    guideList.setDisable(true);
    participantList.setVisible(false);
    participantList.setDisable(true);
    participantEmailTextField.getParent().getParent().setVisible(false);
    participantEmailTextField.getParent().getParent().setDisable(true);
    guideEmailTextField.getParent().setVisible(false);
    guideEmailTextField.getParent().setDisable(true);
    switchButton.setText("Manage Guide");
    List<String> list = BikeTourPlusFeatureSet4Controller.getGuides();
    list.addAll(BikeTourPlusFeatureSet4Controller.getParticipants());
    TextFields.bindAutoCompletion(emailSearchField, list);
    emailSearchField.setOnKeyPressed(new EventHandler<KeyEvent>() {
      public void handle(KeyEvent ke) {
        if (ke.getCode().equals(KeyCode.ENTER)) {
          searchButtonClicked();
        }
      }
    });
    message.setText("");
    guideList.getParent().getParent().setOnKeyPressed(new EventHandler<KeyEvent>() {
      public void handle(KeyEvent ke) {
        if (ke.getCode().equals(KeyCode.ESCAPE)) {
          if (guideList.isVisible()) {
            guideEmailTextField.setText("");
            guidePasswordTextField.setText("");
            guideEmergencyContactTextField.setText("");
            guideNameTextField.setText("");
            message.setText("");
          } else {
            participantEmailTextField.setText("");
            participantPasswordTextField.setText("");
            participantNameTextField.setText("");
            participantEmergencyContactTextField.setText("");
            participantAvailableFromTextField.setText("");
            participantAvailableToTextField.setText("");
            participantNrWeeksTextField.setText("");
            lodgeCheckBox.setSelected(false);
            participantCostLabel.setText("");
            message.setText("");
          }
        }
      }
    });
    guideList.setOnKeyPressed(new EventHandler<KeyEvent>() {
      public void handle(KeyEvent ke) {
        if (ke.getCode().equals(KeyCode.ESCAPE)) {
          if (guideList.isVisible()) {
            guideEmailTextField.setText("");
            guidePasswordTextField.setText("");
            guideEmergencyContactTextField.setText("");
            guideNameTextField.setText("");
            message.setText("");
          } else {
            participantEmailTextField.setText("");
            participantPasswordTextField.setText("");
            participantNameTextField.setText("");
            participantEmergencyContactTextField.setText("");
            participantAvailableFromTextField.setText("");
            participantAvailableToTextField.setText("");
            participantNrWeeksTextField.setText("");
            lodgeCheckBox.setSelected(false);
            participantCostLabel.setText("");
            message.setText("");
          }
        }
        if (ke.getCode().equals(KeyCode.UP)) {
          int i = guideList.getSelectionModel().getSelectedIndex();
          guideList.getSelectionModel().select(i--);
          viewGuideClicked();
        }
        if (ke.getCode().equals(KeyCode.DOWN)) {
          int i = guideList.getSelectionModel().getSelectedIndex();
          guideList.getSelectionModel().select(i++);
          viewGuideClicked();
        }
      }
    });
    participantList.setOnKeyPressed(new EventHandler<KeyEvent>() {
      public void handle(KeyEvent ke) {
        if (ke.getCode().equals(KeyCode.ESCAPE)) {
          if (guideList.isVisible()) {
            guideEmailTextField.setText("");
            guidePasswordTextField.setText("");
            guideEmergencyContactTextField.setText("");
            guideNameTextField.setText("");
          } else {
            participantEmailTextField.setText("");
            participantPasswordTextField.setText("");
            participantNameTextField.setText("");
            participantEmergencyContactTextField.setText("");
            participantAvailableFromTextField.setText("");
            participantAvailableToTextField.setText("");
            participantNrWeeksTextField.setText("");
            lodgeCheckBox.setSelected(false);
            participantCostLabel.setText("");
          }
        }
        if (ke.getCode().equals(KeyCode.UP)) {
          int i = participantList.getSelectionModel().getSelectedIndex();
          participantList.getSelectionModel().select(i--);
          viewParticipantClicked();
        }
        if (ke.getCode().equals(KeyCode.DOWN)) {
          int i = participantList.getSelectionModel().getSelectedIndex();
          participantList.getSelectionModel().select(i++);
          viewParticipantClicked();
        }
      }
    });
  }

  /**
   * <p>
   * search a user by email
   * </p>
   * 
   * @param
   * @return void
   */
  @FXML
  public void searchButtonClicked() {
    String email = emailSearchField.getText();
    if (email == null || email.trim().isEmpty()) {
      ViewUtils.showError("Please input a valid email");
    } else {
      emailSearchField.setText("");
      participantList.getItems().clear();
      guideList.getItems().clear();
      // search guides
      for (String g : BikeTourPlusFeatureSet4Controller.getGuides()) {
        if (g.equals(email)) {
          switchPage("Manage Guide");
          guideList.getItems().add(email);
        }
      }
      for (String p : BikeTourPlusFeatureSet4Controller.getParticipants()) {
        if (p.equals(email)) {
          switchPage("Manage Participant");
          participantList.getItems().add(email);
        }
      }
    }
  }

  /**
   * <p>
   * view guide information
   * </p>
   * 
   * @param
   * @return void
   */
  public void viewGuideClicked() {
    if (guideList.getSelectionModel().getSelectedItem() == null)
      return;
    String email = guideList.getSelectionModel().getSelectedItem();
    TOGuide guide = BikeTourPlusFeatureSet4Controller.getTOGuide(email);
    guideEmailTextField.setText(email);
    guidePasswordTextField.setText(guide.getGuidePassword());
    guideEmergencyContactTextField.setText(guide.getGuideEmergencyContact());
    guideNameTextField.setText(guide.getGuideName());
  }

  /**
   * <p>
   * view participant information
   * </p>
   * 
   * @param
   * @return void
   */
  public void viewParticipantClicked() {
    if (participantList.getSelectionModel().getSelectedItem() == null)
      return;
    String email = participantList.getSelectionModel().getSelectedItem();
    TOParticipantCost participant = BikeTourPlusFeatureSet4Controller.getTOParticipantCost(email);
    participantEmailTextField.setText(email);
    participantPasswordTextField.setText(participant.getParticipantPassword());
    participantEmergencyContactTextField.setText(participant.getParticipantEmergencyContact());
    participantNameTextField.setText(participant.getParticipantName());
    participantAvailableFromTextField.setText(Integer.toString(participant.getAvailabilityFrom()));
    participantAvailableToTextField.setText(Integer.toString(participant.getAvailabilityTo()));
    participantNrWeeksTextField.setText(Integer.toString(participant.getNrWeeks()));
    lodgeCheckBox.setSelected(participant.getLodgeRequired());
    participantCostLabel.setText(Integer.toString(participant.getTotalCostForBikingTour()));
    participantStatusLabel.setText(participant.getStatus());
  }

  /**
   * <p>
   * register a user
   * </p>
   * 
   * @param ActionEvent event
   * @return void
   */
  // Event Listener on Button[#registerGuideButton].onAction
  @FXML
  public void registerGuideClicked(ActionEvent event) {
    if (guideList.isVisible()) {
      String email = guideEmailTextField.getText();
      String password = guidePasswordTextField.getText();
      String name = guideNameTextField.getText();
      String emergencyContact = guideEmergencyContactTextField.getText();
      if (email == null || email.trim().isEmpty()) {
        ViewUtils.showError("Please input a valid guide email");
      } else if (password == null || password.trim().isEmpty()) {
        ViewUtils.showError("Please input a valid guide password");
      } else if (name == null || name.trim().isEmpty()) {
        ViewUtils.showError("Please input a valid guide name");
      } else if (emergencyContact == null || emergencyContact.trim().isEmpty()) {
        ViewUtils.showError("Please input a valid guide emergency contact");
      } else {
        // reset the driver text field if success
        if (successful(BikeTourPlusFeatureSet4Controller.registerGuide(email, password, name,
            emergencyContact))) {
          guideEmailTextField.setText("");
          guidePasswordTextField.setText("");
          guideNameTextField.setText("");
          guideEmergencyContactTextField.setText("");
          message.setText("Registration Successful");
          message.setText("Registration for " + email + " Successful");
        }
      }
      guideList.setItems(ViewUtils.getGuideEmails());
    } else {
      String email = participantEmailTextField.getText();
      String password = participantPasswordTextField.getText();
      String name = participantNameTextField.getText();
      String emergencyContact = participantEmergencyContactTextField.getText();
      String availabilityFrom = participantAvailableFromTextField.getText();
      String availabilityTo = participantAvailableToTextField.getText();
      String nrWeeks = participantNrWeeksTextField.getText();
      boolean lodgeSelected = lodgeCheckBox.isSelected();
      if (email == null || email.trim().isEmpty()) {
        ViewUtils.showError("Please input a valid participant email");
      } else if (password == null || password.trim().isEmpty()) {
        ViewUtils.showError("Please input a valid participant password");
      } else if (name == null || name.trim().isEmpty()) {
        ViewUtils.showError("Please input a valid participant name");
      } else if (emergencyContact == null || emergencyContact.trim().isEmpty()) {
        ViewUtils.showError("Please input a valid participant emergency contact");
      } else if (availabilityFrom == null || availabilityFrom.trim().isEmpty()) {
        ViewUtils.showError("Please input a valid participant availability");
      } else if (availabilityTo == null || availabilityTo.trim().isEmpty()) {
        ViewUtils.showError("Please input a valid participant availability");
      } else if (nrWeeks == null || nrWeeks.trim().isEmpty()) {
        ViewUtils.showError("Please input a valid number of weeks");
      } else {
        // reset the driver text field if success
        if (successful(BikeTourPlusFeatureSet3Controller.registerParticipant(email, password, name,
            emergencyContact, Integer.parseInt(nrWeeks), Integer.parseInt(availabilityFrom),
            Integer.parseInt(availabilityTo), lodgeSelected))) {
          participantEmailTextField.setText("");
          participantPasswordTextField.setText("");
          participantNameTextField.setText("");
          participantEmergencyContactTextField.setText("");
          participantAvailableFromTextField.setText("");
          participantAvailableToTextField.setText("");
          participantNrWeeksTextField.setText("");
          lodgeCheckBox.setSelected(false);
          participantCostLabel.setText("");
          participantStatusLabel.setText("");
          message.setText("Registration for " + email + " Successful");
        }
      }
      participantList.setItems(ViewUtils.getParticipantEmails());
    }
    BtpFxmlView.getInstance().refresh();
  }

  /**
   * <p>
   * update a user
   * </p>
   * 
   * @param ActinoEvent event
   * @return void
   */
  @FXML
  public void updateGuideClicked(ActionEvent event) {
    if (guideList.isVisible()) {
      String email = guideEmailTextField.getText();
      String password = guidePasswordTextField.getText();
      String name = guideNameTextField.getText();
      String emergencyContact = guideEmergencyContactTextField.getText();
      if (email == null || email.trim().isEmpty()) {
        ViewUtils.showError("Please input a valid guide email");
      } else if (password == null || password.trim().isEmpty()) {
        ViewUtils.showError("Please input a valid guide password");
      } else if (name == null || name.trim().isEmpty()) {
        ViewUtils.showError("Please input a valid guide name");
      } else if (emergencyContact == null || emergencyContact.trim().isEmpty()) {
        ViewUtils.showError("Please input a valid guide emergency contact");
      } else {
        // reset the driver text field if success
        if (successful(BikeTourPlusFeatureSet4Controller.updateGuide(email, password, name,
            emergencyContact))) {
          guideEmailTextField.setText("");
          guidePasswordTextField.setText("");
          guideNameTextField.setText("");
          guideEmergencyContactTextField.setText("");
          message.setText("Update " + email + " Successful");
        }
      }
      guideList.setItems(ViewUtils.getGuideEmails());
    } else {
      String email = participantEmailTextField.getText();
      String password = participantPasswordTextField.getText();
      String name = participantNameTextField.getText();
      String emergencyContact = participantEmergencyContactTextField.getText();
      String availabilityFrom = participantAvailableFromTextField.getText();
      String availabilityTo = participantAvailableToTextField.getText();
      String nrWeeks = participantNrWeeksTextField.getText();
      boolean lodgeSelected = lodgeCheckBox.isSelected();
      if (email == null || email.trim().isEmpty()) {
        ViewUtils.showError("Please input a valid participant email");
      } else if (password == null || password.trim().isEmpty()) {
        ViewUtils.showError("Please input a valid participant password");
      } else if (name == null || name.trim().isEmpty()) {
        ViewUtils.showError("Please input a valid participant name");
      } else if (emergencyContact == null || emergencyContact.trim().isEmpty()) {
        ViewUtils.showError("Please input a valid participant emergency contact");
      } else if (availabilityFrom == null || availabilityFrom.trim().isEmpty()) {
        ViewUtils.showError("Please input a valid participant availability");
      } else if (availabilityTo == null || availabilityTo.trim().isEmpty()) {
        ViewUtils.showError("Please input a valid participant availability");
      } else if (nrWeeks == null || nrWeeks.trim().isEmpty()) {
        ViewUtils.showError("Please input a valid number of weeks");
      } else {
        // reset the driver text field if success
        if (successful(BikeTourPlusFeatureSet3Controller.updateParticipant(email, password, name,
            emergencyContact, Integer.parseInt(nrWeeks), Integer.parseInt(availabilityFrom),
            Integer.parseInt(availabilityTo), lodgeSelected))) {
          participantEmailTextField.setText("");
          participantPasswordTextField.setText("");
          participantNameTextField.setText("");
          participantEmergencyContactTextField.setText("");
          participantAvailableFromTextField.setText("");
          participantAvailableToTextField.setText("");
          participantNrWeeksTextField.setText("");
          lodgeCheckBox.setSelected(false);
          participantCostLabel.setText("");
          participantStatusLabel.setText("");
          message.setText("Update " + email + " Successful");
        }
      }
      participantList.setItems(ViewUtils.getParticipantEmails());
    }
    BtpFxmlView.getInstance().refresh();
  }

  /**
   * <p>
   * delete a user
   * </p>
   * 
   * @param ActionEvent event
   * @return void
   */
  public void deleteGuideClicked(ActionEvent event) {
    if (guideList.isVisible()) {
      String email = guideEmailTextField.getText();
      if (email == null || email.trim().isEmpty()) {
        ViewUtils.showError("Please input a valid guide email");
      } else {
        int s = ViewUtils.getGuideEmails().size();
        BikeTourPlusFeatureSet4Controller.deleteGuide(email);
        if (s > ViewUtils.getGuideEmails().size()) {
          message.setText(email + " Deleted");
          guideEmailTextField.setText("");
          guidePasswordTextField.setText("");
          guideNameTextField.setText("");
          guideEmergencyContactTextField.setText("");
        } else
          message.setText("Email not found");
      }
      guideList.setItems(ViewUtils.getGuideEmails());
    } else {
      String email = participantEmailTextField.getText();
      if (email == null || email.trim().isEmpty()) {
        ViewUtils.showError("Please input a valid participant email");
      } else {
        int s = ViewUtils.getParticipantEmails().size();
        BikeTourPlusFeatureSet2Controller.deleteParticipant(email);
        if (s > ViewUtils.getParticipantEmails().size()) {
          message.setText(email + " Deleted");
          participantEmailTextField.setText("");
          participantPasswordTextField.setText("");
          participantNameTextField.setText("");
          participantEmergencyContactTextField.setText("");
          participantAvailableFromTextField.setText("");
          participantAvailableToTextField.setText("");
          participantNrWeeksTextField.setText("");
          lodgeCheckBox.setSelected(false);
          participantCostLabel.setText("");
          participantStatusLabel.setText("");
        } else
          message.setText("Email not found");
      }
      participantList.setItems(ViewUtils.getParticipantEmails());
    }
    BtpFxmlView.getInstance().refresh();
  }

  public void gearButtonClicked(ActionEvent event) {
	  for (Node n : viewUsers.getChildren()) {
	      if (n.getId().equals("viewParticipantGear")) {
	    	n.setUserData(participantEmailTextField.getText());
	        n.setDisable(false);
	        n.setVisible(true);
	      } else {
	        n.setDisable(true);
	        n.setVisible(false);
	      }
	    }
  }
  
  /**
   * <p>
   * refresh
   * </p>
   * 
   * @param ActionEvent event
   * @return void
   */
  public void refreshButtonClicked(ActionEvent event) {
    initialize();
  }

  /**
   * <p>
   * switch between guide and participant display
   * </p>
   * 
   * @param ActionEvent event
   * @return void
   */
  // Event Listener on Button[#switchButton].onAction
  @FXML
  public void switchClicked(ActionEvent event) {
    switchPage(switchButton.getText());
  }

  /**
   * <p>
   * helper to switch page
   * </p>
   * 
   * @param
   * @return void
   */
  public void switchPage(String s) {
    if (s.equals("Manage Participant")) {
      participantList.setVisible(true);
      participantList.setDisable(false);
      guideList.setVisible(false);
      guideList.setDisable(true);
      guideEmailTextField.getParent().setVisible(false);
      guideEmailTextField.getParent().setDisable(true);
      participantEmailTextField.getParent().getParent().setVisible(true);
      participantEmailTextField.getParent().getParent().setDisable(false);
      switchButton.setText("Manage Guide");
      registerGuideButton.setText("Register Participant");
      updateGuideButton.setText("update Participant");
      deleteGuideButton.setText("delete Participant");
    } else {
      participantList.setVisible(!true);
      participantList.setDisable(!false);
      guideList.setVisible(!false);
      guideList.setDisable(!true);
      guideEmailTextField.getParent().setVisible(!false);
      guideEmailTextField.getParent().setDisable(!true);
      participantEmailTextField.getParent().getParent().setVisible(!true);
      participantEmailTextField.getParent().getParent().setDisable(!false);
      switchButton.setText("Manage Participant");
      registerGuideButton.setText("Register Guide");
      updateGuideButton.setText("update Guide");
      deleteGuideButton.setText("delete Guide");
    }
  }

  /**
   * <p>
   * pay for a participant with code prompt
   * </p>
   * 
   * @param ActionEvent event
   * @return void
   */
  @FXML
  public void payButtonClicked(ActionEvent event) {
    String email = participantEmailTextField.getText();
    if (email == null || email.trim().isEmpty()) {
      ViewUtils.showError("Please input a valid participant email");
      return;
    }
    makePayWindow();
  }

  /**
   * <p>
   * cancel tour for a participant
   * </p>
   * 
   * @param ActionEvent event
   * @return void
   */
  @FXML
  public void cancelButtonClicked(ActionEvent event) {
    String email = participantEmailTextField.getText();
    if (email == null || email.trim().isEmpty()) {
      ViewUtils.showError("Please input a valid participant email");
    } else if (successful(ExtraFeatures.cancel(email))) {
      participantEmailTextField.setText("");
      participantPasswordTextField.setText("");
      participantNameTextField.setText("");
      participantEmergencyContactTextField.setText("");
      participantAvailableFromTextField.setText("");
      participantAvailableToTextField.setText("");
      participantNrWeeksTextField.setText("");
      lodgeCheckBox.setSelected(false);
      participantCostLabel.setText("");
      participantStatusLabel.setText("");
      message.setText("Cancel trip for " + email + " Successfully");
    }
  }

  /**
   * <p>
   * finish biketour for a participant
   * </p>
   * 
   * @param ActionEvent event
   * @return void
   */
  @FXML
  public void finishButtonClicked(ActionEvent event) {
    String email = participantEmailTextField.getText();
    if (email == null || email.trim().isEmpty()) {
      ViewUtils.showError("Please input a valid participant email");
    } else if (successful(ExtraFeatures.finish(email))) {
      participantEmailTextField.setText("");
      participantPasswordTextField.setText("");
      participantNameTextField.setText("");
      participantEmergencyContactTextField.setText("");
      participantAvailableFromTextField.setText("");
      participantAvailableToTextField.setText("");
      participantNrWeeksTextField.setText("");
      lodgeCheckBox.setSelected(false);
      participantCostLabel.setText("");
      participantStatusLabel.setText("");
      message.setText("Finish trip for " + email + " Successfully");
    }
  }

  /**
   * <p>
   * code prompt for paying
   * </p>
   * 
   * @param
   * @return void
   */
  public void makePayWindow() {
    Stage dialog = new Stage();
    dialog.initModality(Modality.APPLICATION_MODAL);
    VBox dialogPane = new VBox();

    // create UI elements
    Text text = new Text("Please enter autorization code");
    TextField codeField = new TextField();
    Button okButton = new Button("OK");

    okButton.setOnAction(a -> {
      code = codeField.getText();
      dialog.close();
      String email = participantEmailTextField.getText();
      if (successful(ExtraFeatures.pay(email, code))) {
        participantEmailTextField.setText("");
        participantPasswordTextField.setText("");
        participantNameTextField.setText("");
        participantEmergencyContactTextField.setText("");
        participantAvailableFromTextField.setText("");
        participantAvailableToTextField.setText("");
        participantNrWeeksTextField.setText("");
        lodgeCheckBox.setSelected(false);
        participantCostLabel.setText("");
        participantStatusLabel.setText("");
        message.setText("Pay trip for " + email + " Successfully");
      } else {
        ViewUtils.showError(ExtraFeatures.pay(email, code));
      }
      code = "";
    });

    // display the popup window
    int innerPadding = 10; // inner padding/spacing
    int outerPadding = 100; // outer padding
    dialogPane.setSpacing(innerPadding);
    dialogPane.setAlignment(Pos.CENTER);
    dialogPane.setPadding(new Insets(innerPadding, innerPadding, innerPadding, innerPadding));
    dialogPane.getChildren().addAll(text, codeField, okButton);
    Scene dialogScene = new Scene(dialogPane,
        outerPadding + 5 * "Please enter autorization code".length(), outerPadding);
    dialog.setScene(dialogScene);
    dialog.setTitle("Payment");
    dialog.show();
  }
}
