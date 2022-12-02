package ca.mcgill.ecse.biketourplus.javafx.fxml.controllers;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import java.time.LocalDate;
import ca.mcgill.ecse.biketourplus.application.BikeTourPlusApplication;
import ca.mcgill.ecse.biketourplus.controller.BikeTourPlusFeatureSet1Controller;
import ca.mcgill.ecse.biketourplus.controller.BikeTourPlusFeatureSet2Controller;
import ca.mcgill.ecse.biketourplus.controller.BikeTourPlusFeatureSet6Controller;
import ca.mcgill.ecse.biketourplus.controller.ExtraFeatures;
import ca.mcgill.ecse.biketourplus.javafx.fxml.controllers.ViewUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.DatePicker;

public class SettingsController {
	@FXML
	private Button refreshButton;
	@FXML
	private TextField managerEmailTextField;
	@FXML
	private PasswordField managerPasswordTextField;
	@FXML
	private Button changePasswordButton;
	@FXML
	private Button createBiketour;
	@FXML
	private DatePicker startDatePicker;
	@FXML
	private DatePicker endDatePicker;
	@FXML
	private Button updateSeasonDatesButton;
	@FXML
	private TextField nrWeeksTextField;
	@FXML
	private TextField priceOfGuideTextField;
	@FXML
	private Button updatePriceOf;
	
	//private static BikeTourPlus BikeTourPlusFeatureSet6Controller.getBTP() = BikeTourPlusFeatureSet6Controller.getBikeTourPlusFeatureSet6Controller.getBTP()P();;
	
	public void initialize() {
		
		managerEmailTextField.setEditable(false);
		startDatePicker.setEditable(false);
		endDatePicker.setDisable(true);
		
		managerEmailTextField.setText(BikeTourPlusFeatureSet6Controller.getBTP().getManager().getEmail());
		startDatePicker.setValue(BikeTourPlusFeatureSet6Controller.getBTP().getStartDate().toLocalDate());
		endDatePicker.setValue(startDatePicker.getValue().plusWeeks(BikeTourPlusFeatureSet6Controller.getBTP().getNrWeeks()));
		nrWeeksTextField.setText(String.valueOf(BikeTourPlusFeatureSet6Controller.getBTP().getNrWeeks()));
		priceOfGuideTextField.setText(String.valueOf(BikeTourPlusFeatureSet6Controller.getBTP().getPriceOfGuidePerWeek()));
		
	}

	// Event Listener on Button[#refreshButton].onAction
	@FXML
	public void refreshButtonClicked(ActionEvent event) {
		initialize();
	}
	// Event Listener on Button[#changePasswordButton].onAction
	@FXML
	public void changeManagerPassword(ActionEvent event) {
		String enteredPassword = managerPasswordTextField.getText();
		var error = "";
		
		if (enteredPassword == "") error += "Invalid password! ";
		if (enteredPassword == BikeTourPlusFeatureSet6Controller.getBTP().getManager().getPassword()) {
			
		}
		error += BikeTourPlusFeatureSet1Controller.updateManager(enteredPassword);
		
		if (!error.isEmpty()) {
			ViewUtils.showError(error);
		}else ViewUtils.showError("Manager password updated successfully");
	}
	
	// Event Listener on Button[#createBiketour].onMouseClicked
	@FXML
	public void createBiketour(MouseEvent event) {
	  ExtraFeatures.initiateBikeTour();
	  ViewUtils.showError("BikeTourPlus Process initiated");
	}
	
	
	// Event Listener on Button[#updateSeasonDatesButton].onAction
	@FXML
	public void updateSeasonDates(ActionEvent event) {
		LocalDate enteredStartDate  = startDatePicker.getValue();
		int enteredNrWeeks = Integer.valueOf(nrWeeksTextField.getText());
		var error = "";
		
		error += BikeTourPlusFeatureSet2Controller.updateBikeTourPlus(java.sql.Date.valueOf(enteredStartDate), enteredNrWeeks, BikeTourPlusFeatureSet6Controller.getBTP().getPriceOfGuidePerWeek());
		
		if (!error.isEmpty()) {
			ViewUtils.showError(error);
		} else {
			startDatePicker.setValue(enteredStartDate);
			endDatePicker.setValue(enteredStartDate.plusWeeks(BikeTourPlusFeatureSet6Controller.getBTP().getNrWeeks()));
			ViewUtils.showError("Season Dates updated successfully");
		}

		
	}
	
	// Event Listener on Button[#updatePriceOf].onAction
	@FXML
	public void updatePriceOfGuide(ActionEvent event) {
		String priceEntered = priceOfGuideTextField.getText();
		var error = "";
		
		if (priceEntered == "") error += "Invalid price! ";
		else{
			error += BikeTourPlusFeatureSet2Controller.updateBikeTourPlus(BikeTourPlusFeatureSet6Controller.getBTP().getStartDate(), BikeTourPlusFeatureSet6Controller.getBTP().getNrWeeks(), Integer.valueOf(priceEntered));
		}
		
		if (!error.isEmpty()) {
			ViewUtils.showError(error);
		} else {
			priceOfGuideTextField.setText(priceEntered);
			ViewUtils.showError("Price of guide updated successfully");
		}
		
	}
}
