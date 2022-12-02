package ca.mcgill.ecse.biketourplus.javafx.fxml.controllers;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.List;

import org.controlsfx.control.textfield.TextFields;

import ca.mcgill.ecse.biketourplus.controller.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ListView;

public class ViewParticipantGearController {
	@FXML
	private TextField quantityField;
	@FXML
	private TextField nameField;
	@FXML
	private Button decrementQuantity;
	@FXML
	private Button incrementQuantity;
	@FXML
	private Button addNewGear;
	@FXML
	private Button addNewCombo;
	@FXML
	private ListView<String> gearsToAddList;
	@FXML
	private ListView<String> combosToAddList;
	@FXML
	private TableView<TOBookedItem> bookedItemTable;
	@FXML
	private Button refreshButton;
	@FXML
	private TextField itemSearchField;
	@FXML
	private Button searchButton;
	@FXML
	private Text message;
	@FXML
	private GridPane viewParticipantGear;
	
	private String email;
	
	public void initialize() {
		bookedItemTable.getColumns().add(createTableColumn("Name","bookedItemName"));
		bookedItemTable.getColumns().add(createTableColumn("Quantity","bookedItemQuantity"));
		nameField.setDisable(true);
		if (ViewUtils.getGearNames() != null) {
		      gearsToAddList.setItems(ViewUtils.getGearNames());
		    }
		gearsToAddList.setOnMouseClicked(new EventHandler<MouseEvent>() {
		      public void handle(MouseEvent event) {
		        viewBookableGearClicked();
		      }
		    });
		if (ViewUtils.getCombosNames() != null) {
		      combosToAddList.setItems(ViewUtils.getCombosNames());
		    }
		combosToAddList.setOnMouseClicked(new EventHandler<MouseEvent>() {
		      public void handle(MouseEvent event) {
		        viewBookableComboClicked();
		      }
		    });
	}
//	@FXML
	
	public void whenOpened() {
	    gearsToAddList.setItems(ViewUtils.getGearNames());
	    combosToAddList.setItems(ViewUtils.getCombosNames());
		bookedItemTable.getItems().clear();
		email = refreshButton.getParent().getParent().getUserData().toString();
		bookedItemTable.getItems().addAll(BikeTourPlusFeatureSet3Controller.getTOBookedItems(email));
	    quantityField.setOnKeyPressed(new EventHandler<KeyEvent>() {
	        public void handle(KeyEvent ke) {
	            if (ke.getCode().equals(KeyCode.ENTER)) {
	            	quantityFieldChanged();
	            }
	        }
	  	 });
	    List<String> itemNameList = BikeTourPlusFeatureSet3Controller.getBookedItemNamesForParticipant(email);
		TextFields.bindAutoCompletion(itemSearchField, itemNameList);
	    itemSearchField.setOnKeyPressed(new EventHandler<KeyEvent>() {
	        public void handle(KeyEvent ke) {
	            if (ke.getCode().equals(KeyCode.ENTER)) {
	            	searchButtonClicked();
	            }
	        }
	  	 });
	    bookedItemTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
	        public void handle(MouseEvent event) {
	         viewBookedItemClicked();
	        }
	    });
	    message.setText("");
	}
	
	public void viewBookedItemClicked() {
		String quantity="";
		if (bookedItemTable.getSelectionModel().getSelectedItem() == null)return;
		String name = bookedItemTable.getSelectionModel().getSelectedItem().getBookedItemName();
		nameField.setText(name);
		quantity = BikeTourPlusFeatureSet3Controller.getBookedItemQuantity(email, name);
		quantityField.setText(quantity);
	}
	
	public void viewBookableGearClicked() {
		if (gearsToAddList.getSelectionModel().getSelectedItem()== null) return;
		String name=gearsToAddList.getSelectionModel().getSelectedItem();
		String quantity;
		if (BikeTourPlusFeatureSet3Controller.getBookedItemQuantity(email, name).equals("")) {
			quantity="0";
		} else {
		quantity=BikeTourPlusFeatureSet3Controller.getBookedItemQuantity(email, name);
		}
		nameField.setText(name);
		quantityField.setText(quantity);
	}
	
	public void viewBookableComboClicked() {
		if (combosToAddList.getSelectionModel().getSelectedItem()== null) return;
		String name=combosToAddList.getSelectionModel().getSelectedItem();
		String quantity;
		if (BikeTourPlusFeatureSet3Controller.getBookedItemQuantity(email, name).equals("")) {
			quantity="0";
		} else {
		quantity=BikeTourPlusFeatureSet3Controller.getBookedItemQuantity(email, name);
		}
		nameField.setText(name);
		quantityField.setText(quantity);
	}
	
	public void quantityFieldChanged() {
		String name = nameField.getText();
		String oldQuantity;
		if (BikeTourPlusFeatureSet3Controller.getBookedItemQuantity(email, name).equals("")) {
			oldQuantity="0";
		} else {
		oldQuantity=BikeTourPlusFeatureSet3Controller.getBookedItemQuantity(email, name);
		}
		String newQuantity = quantityField.getText();
		if (Integer.parseInt(newQuantity)<0) {
			message.setText("Quantity has to be positive");
			return;
		}
		if (newQuantity.equals("0")) {
			message.setText("Item removed from participant");
		}
		if (Integer.parseInt(oldQuantity)-Integer.parseInt(newQuantity)==0) {
			return;
		} else if (Integer.parseInt(oldQuantity)-Integer.parseInt(newQuantity)>0) {
			for (int i=0;i<Integer.parseInt(oldQuantity)-Integer.parseInt(newQuantity);i++) {
				BikeTourPlusFeatureSet3Controller.removeBookableItemFromParticipant(email, name);
			}
		} else {
			for (int i=0;i<Integer.parseInt(newQuantity)-Integer.parseInt(oldQuantity);i++) {
				BikeTourPlusFeatureSet3Controller.addBookableItemToParticipant(email, name);
			}
		}
		whenOpened();
		message.setText("Quantity updated!");
	}
	
	public void addNewComboClicked(ActionEvent event) {
		String name = nameField.getText();
		BikeTourPlusFeatureSet3Controller.addBookableItemToParticipant(email, name);
		whenOpened();
		String quantity = BikeTourPlusFeatureSet3Controller.getBookedItemQuantity(email, name);
		quantityField.setText(quantity);
	}
	
	public void addNewGearClicked(ActionEvent event) {
		String name = nameField.getText();
		BikeTourPlusFeatureSet3Controller.addBookableItemToParticipant(email, name);
		whenOpened();
		String quantity = BikeTourPlusFeatureSet3Controller.getBookedItemQuantity(email, name);
		quantityField.setText(quantity);
	}
	
	// Event Listener on Button[#decrementQuantity].onAction
	@FXML
	public void decrementButtonClicked(ActionEvent event) {
		String name = nameField.getText();
		String quantity;
		if (BikeTourPlusFeatureSet3Controller.getBookedItemQuantity(email, name).equals("")) {
			quantity="0";
		} else {
		quantity=BikeTourPlusFeatureSet3Controller.getBookedItemQuantity(email, name);
		}
		if (quantity.equals("0")) {
			message.setText("Item already unassigned");
			return;
		}
		if (quantity.equals("1")) {
			message.setText("Item removed from participant");
		} else {
			message.setText("Quantity decremented!");
		}
		BikeTourPlusFeatureSet3Controller.removeBookableItemFromParticipant(email, name);
		whenOpened();
		if (BikeTourPlusFeatureSet3Controller.getBookedItemQuantity(email, name).equals("")) {
			quantity="0";
		} else {
		quantity=BikeTourPlusFeatureSet3Controller.getBookedItemQuantity(email, name);
		}
		quantityField.setText(quantity);
		message.setText("Quantity decremented!");
	}
	// Event Listener on Button[#incrementQuantity].onAction
	@FXML
	public void incrementButtonClicked(ActionEvent event) {
		String name = nameField.getText();
		BikeTourPlusFeatureSet3Controller.addBookableItemToParticipant(email, name);
		whenOpened();
		String quantity;
		if (BikeTourPlusFeatureSet3Controller.getBookedItemQuantity(email, name).equals("")) {
			quantity="0";
		} else {
		quantity=BikeTourPlusFeatureSet3Controller.getBookedItemQuantity(email, name);
		}
		quantityField.setText(quantity);
	}
	// Event Listener on Button[#refreshButton].onAction
	@FXML
	public void refreshButtonClicked(ActionEvent event) {
		whenOpened();
	}
	// Event Listener on Button[#searchButton].onAction
	@FXML
	public void searchButtonClicked() {
		String name = itemSearchField.getText();
	    if (name == null || name.trim().isEmpty()) {
	      ViewUtils.showError("Please input a valid name");
	    } else {
	      itemSearchField.setText("");
	      bookedItemTable.getItems().clear();
	      // search guides
	      for (String g : BikeTourPlusFeatureSet3Controller.getBookedItemNamesForParticipant(email)) {
	        if (g.equals(name)) {
	        	List<TOBookedItem> itemList = BikeTourPlusFeatureSet3Controller.getTOBookedItems(email);
	        	for (TOBookedItem b : itemList) {
	        		if (b.getBookedItemName().equals(g)) {
	        			bookedItemTable.getItems().addAll(b);
	        			break;
	        		}
	        	}
	        }
	      }
	    }
	}
	
	public static TableColumn<TOBookedItem, String> createTableColumn(String header,
		      String propertyName) {
		    TableColumn<TOBookedItem, String> column = new TableColumn<>(header);
		    column.setCellValueFactory(new PropertyValueFactory<>(propertyName));
		    return column;
		  }
}
