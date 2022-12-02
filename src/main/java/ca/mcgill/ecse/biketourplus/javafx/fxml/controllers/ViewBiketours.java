/**
 * @author Antoine (AntoineDeng)
 */
package ca.mcgill.ecse.biketourplus.javafx.fxml.controllers;

import java.util.ArrayList;
import java.util.List;

import org.controlsfx.control.textfield.*;

import ca.mcgill.ecse.biketourplus.controller.TOBikeTour;
import ca.mcgill.ecse.biketourplus.controller.TOGuide;
import ca.mcgill.ecse.biketourplus.controller.TOParticipantCost;
import ca.mcgill.ecse.biketourplus.controller.BikeTourPlusFeatureSet1Controller;
import ca.mcgill.ecse.biketourplus.controller.BikeTourPlusFeatureSet4Controller;
import ca.mcgill.ecse.biketourplus.controller.ExtraFeatures;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class ViewBiketours {
  @FXML
  private ListView<Integer> biketoursIdField;
  @FXML
  private TableView<TOParticipantCost> participantViewTable;
  @FXML
  private TableView<TOGuide> guideViewTable;
  @FXML
  private TableView<TOBikeTour> biketourViewTable;
  @FXML
  private Button refreshButton;
  @FXML
  private Button searchButton;
  @FXML
  private Button startButton;
  @FXML
  private Button searchButtonW;
  @FXML
  private ChoiceBox<String> startChoiceBox;
  @FXML
  private TextField emailSearchField;

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
    if (ViewUtils.getIds() != null) {
      biketoursIdField.setItems(ViewUtils.getIds());
    }
    biketoursIdField.setOnMouseClicked(new EventHandler<MouseEvent>() {
      public void handle(MouseEvent event) {
        viewBiketourClicked(null);
      }
    });
    guideViewTable.getColumns().add(createTableColumnG("Name", "guideName"));
    guideViewTable.getColumns().add(createTableColumnG("Email", "guideEmail"));
    guideViewTable.getColumns()
        .add(createTableColumnG("Emergency Contact", "guideEmergencyContact"));
    participantViewTable.getColumns().add(createTableColumnP("Name", "participantName"));
    participantViewTable.getColumns().add(createTableColumnP("Email", "participantEmail"));
    participantViewTable.getColumns()
        .add(createTableColumnP("Emergency Contact", "participantEmergencyContact"));
    participantViewTable.getColumns().add(createTableColumnP("Status", "status"));
    participantViewTable.getColumns()
        .add(createTableColumnP("Item cost", "totalCostForBookableItems"));
    participantViewTable.getColumns()
        .add(createTableColumnP("Total cost", "totalCostForBikingTour"));
    participantViewTable.getColumns().add(createTableColumnP("Lodge", "lodgeRequired"));
    participantViewTable.getColumns().add(createTableColumnP("Aut.code", "participantAuthorizationCode"));
    participantViewTable.getColumns().add(createTableColumnP("Refund (%)", "participantRefund"));
    biketourViewTable.getColumns().add(createTableColumnB("Start Week", "startWeek"));
    biketourViewTable.getColumns().add(createTableColumnB("End Week", "endWeek"));
    biketourViewTable.getColumns().add(createTableColumnB("Guide cost", "totalCostForGuide"));
    biketourViewTable.getColumns()
        .add(createTableColumnB("Number of participants", "nrParticipants"));
    guideViewTable.setPlaceholder(new Label("no guide in the biketour"));
    participantViewTable.setPlaceholder(new Label("no participant in the biketour"));
    biketourViewTable.setPlaceholder(new Label(""));
    emailSearchField.setOnKeyPressed(new EventHandler<KeyEvent>() {
      public void handle(KeyEvent ke) {
        if (ke.getCode().equals(KeyCode.ENTER)) {
          searchButtonClicked(null);
        }
      }
    });
    refreshButton.getParent().getParent().setOnKeyPressed(new EventHandler<KeyEvent>() {
      public void handle(KeyEvent ke) {
        if (ke.getCode().equals(KeyCode.ESCAPE)) {
          refreshButtonClicked(null);
        }
      }
    });
    biketoursIdField.setOnKeyPressed(new EventHandler<KeyEvent>() {
      public void handle(KeyEvent ke) {
        if (ke.getCode().equals(KeyCode.ESCAPE)) {
          refreshButtonClicked(null);
        }
        if (ke.getCode().equals(KeyCode.DOWN)) {
          int i = biketoursIdField.getSelectionModel().getSelectedIndex();
          biketoursIdField.getSelectionModel().select(i++);
          viewBiketourClicked(null);
        }
        if (ke.getCode().equals(KeyCode.UP)) {
          int i = biketoursIdField.getSelectionModel().getSelectedIndex();
          biketoursIdField.getSelectionModel().select(i--);
          viewBiketourClicked(null);
        }
      }
    });
    List<String> list = BikeTourPlusFeatureSet4Controller.getGuides();
    list.addAll(BikeTourPlusFeatureSet4Controller.getParticipants());
    TextFields.bindAutoCompletion(emailSearchField, list);
    startChoiceBox.setItems(ViewUtils.getWeeks());
    startChoiceBox.setValue("Chose a start date");
  }

  /**
   * <p>
   * start biketour of selected week
   * </p>
   * 
   * @param MouseEvent event
   * @return void
   */
  @FXML
  public void startButtonClicked(ActionEvent event) {
    String week = startChoiceBox.getValue();
    if (week == null || week.trim().isEmpty() || week.equals("Chose a start date")) {
      ViewUtils.showError("Please select a valid week");
    } else {
      ExtraFeatures.start(Integer.parseInt(week));
    }
  }

  /**
   * <p>
   * refresh
   * </p>
   * 
   * @param MouseEvent event
   * @return void
   */
  @FXML
  public void refreshButtonClicked(ActionEvent event) {
    biketoursIdField.setItems(ViewUtils.getIds());
    participantViewTable.getItems().clear();
    guideViewTable.getItems().clear();
    biketourViewTable.getItems().clear();
  }

  /**
   * <p>
   * display biketour information
   * </p>
   * 
   * @param MouseEvent event
   * @return void
   */
  @FXML
  public void viewBiketourClicked(ActionEvent event) {
    participantViewTable.getItems().clear();
    guideViewTable.getItems().clear();
    biketourViewTable.getItems().clear();
    if (biketoursIdField.getSelectionModel().getSelectedItem() == null)
      return;
    int id = biketoursIdField.getSelectionModel().getSelectedItem();
    TOBikeTour bt = BikeTourPlusFeatureSet1Controller.getBikeTour(id);
    guideViewTable.getItems().addAll(bt.getGuide());
    participantViewTable.getItems().addAll(bt.getParticipantCosts());
    biketourViewTable.getItems().addAll(bt);
    autoResizeColumns(participantViewTable);
    autoResizeColumns(guideViewTable);
    autoResizeColumns(biketourViewTable);
  }

  /**
   * <p>
   * search for biketour by participant or guide
   * </p>
   * 
   * @param MouseEvent event
   * @return void
   */
  @FXML
  public void searchButtonClicked(ActionEvent event) {
    String email = emailSearchField.getText();
    List<Integer> idl = new ArrayList<Integer>();
    TOBikeTour bt;
    boolean isGuide = false;
    if (email == null || email.trim().isEmpty()) {
      ViewUtils.showError("Please input a valid email");
    } else {
      emailSearchField.setText("");
      participantViewTable.getItems().clear();
      guideViewTable.getItems().clear();
      for (int i : ViewUtils.getIds()) {
        bt = BikeTourPlusFeatureSet1Controller.getBikeTour(i);
        if (bt.getGuideEmail().equals(email)) {
          idl.add(i);
          isGuide = true;
        }
        if (!isGuide) {
          for (TOParticipantCost p : bt.getParticipantCosts()) {
            if (p.getParticipantEmail().equals(email)) {
              idl.add(i);
              biketoursIdField.setItems(FXCollections.observableList(idl));
              return;
            }
          }
        }
      }
      biketoursIdField.setItems(FXCollections.observableList(idl));
    }
  }

  /**
   * <p>
   * search for biketour by starting week
   * </p>
   * 
   * @param MouseEvent event
   * @return void
   */
  @FXML
  public void searchButtonWClicked(ActionEvent event) {
    String week = startChoiceBox.getValue();
    List<Integer> idl = new ArrayList<Integer>();
    TOBikeTour bt;
    if (week == null || week.trim().isEmpty() || week.equals("Chose a start date")) {
      ViewUtils.showError("Please select a valid week");
    } else {
      participantViewTable.getItems().clear();
      guideViewTable.getItems().clear();
      for (int i : ViewUtils.getIds()) {
        bt = BikeTourPlusFeatureSet1Controller.getBikeTour(i);
        if (bt.getStartWeek() == Integer.parseInt(week)) {
          idl.add(i);
        }
      }
      biketoursIdField.setItems(FXCollections.observableList(idl));
    }
  }

  // Code from tutorial 9
  public static TableColumn<TOParticipantCost, String> createTableColumnP(String header,
      String propertyName) {
    TableColumn<TOParticipantCost, String> column = new TableColumn<>(header);
    column.setCellValueFactory(new PropertyValueFactory<>(propertyName));
    return column;
  }

  public static TableColumn<TOGuide, String> createTableColumnG(String header,
      String propertyName) {
    TableColumn<TOGuide, String> column = new TableColumn<>(header);
    column.setCellValueFactory(new PropertyValueFactory<>(propertyName));
    return column;
  }

  public static TableColumn<TOBikeTour, String> createTableColumnB(String header,
      String propertyName) {
    TableColumn<TOBikeTour, String> column = new TableColumn<>(header);
    column.setCellValueFactory(new PropertyValueFactory<>(propertyName));
    return column;
  }

  // Code from https://stackoverflow.com/questions/14650787/javafx-column-in-tableview-auto-fit-size
  // By: HarleyDavidson
  public static void autoResizeColumns(TableView<?> table) {
    // Set the right policy
    table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
    table.getColumns().stream().forEach((column) -> {
      // Minimal width = columnheader
      Text t = new Text(column.getText());
      double max = t.getLayoutBounds().getWidth();
      for (int i = 0; i < table.getItems().size(); i++) {
        // cell must not be empty
        if (column.getCellData(i) != null) {
          t = new Text(column.getCellData(i).toString());
          double calcwidth = t.getLayoutBounds().getWidth();
          // remember new max-width
          if (calcwidth > max) {
            max = calcwidth;
          }
        }
      }
      // set the new max-widht with some extra space
      column.setPrefWidth(max + 10.0d);
    });
  }
}
