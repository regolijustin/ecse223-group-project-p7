package ca.mcgill.ecse.biketourplus.javafx.fxml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ca.mcgill.ecse.biketourplus.application.BikeTourPlusApplication;

public class BtpFxmlView extends Application {// code from tutorial 9
  public static final EventType<Event> REFRESH_EVENT = new EventType<>("REFRESH");
  private static BtpFxmlView instance;
  private List<Node> refreshableNodes = new ArrayList<>();
  @FXML
  private Pane display;
  @FXML
  private Button home;
  @FXML
  private Button scheduledTour;
  @FXML
  private Button gAndpManager;
  @FXML
  private Button settings;
  @FXML
  private Button gAndcManager;

  /**
   * <p>
   * switching pages on button clicks
   * </p>
   * 
   * @param
   * @return void
   */
  // Event Listener on Button[#home].onMouseClicked
  @FXML
  public void home(MouseEvent event) {
    for (Node n : display.getChildren()) {
      if (n.getId().equals("mainPage")) {
        n.setDisable(false);
        n.setVisible(true);
        n.setUserData(event);
      } else {
        n.setDisable(true);
        n.setVisible(false);
      }
    }
  }

  // Event Listener on Button[#scheduledTour].onMouseClicked
  @FXML
  public void scheduledTour(MouseEvent event) {
    for (Node n : display.getChildren()) {
      if (n.getId().equals("viewBikeTours")) {
        n.setDisable(false);
        n.setVisible(true);
      } else {
        n.setDisable(true);
        n.setVisible(false);
      }
    }
  }

  // Event Listener on Button[#gAndpManager].onMouseClicked
  @FXML
  public void gAndpManager(MouseEvent event) {
    for (Node n : display.getChildren()) {
      if (n.getId().equals("viewUsers")) {
        n.setDisable(false);
        n.setVisible(true);
        for (Node n1 : ((Pane)n).getChildren()) {
  	      if (n1.getId().equals("mainControllerPage")) {
  	        n1.setDisable(false);
  	        n1.setVisible(true);
  	      } else {
  	        n1.setDisable(true);
  	        n1.setVisible(false);
  	      }
  	    }
      } else {
        n.setDisable(true);
        n.setVisible(false);
      }
    }
  }

  // Event Listener on Button[#settings].onMouseClicked
  @FXML
  public void settings(MouseEvent event) {
    for (Node n : display.getChildren()) {
      if (n.getId().equals("setting")) {
        n.setDisable(false);
        n.setVisible(true);
      } else {
        n.setDisable(true);
        n.setVisible(false);
      }
    }
  }

  // Event Listener on Button[#gAndcManager].onMouseClicked
  @FXML
  public void gAndcManager(MouseEvent event) {
    for (Node n : display.getChildren()) {
      if (n.getId().equals("viewGears")) {
        n.setDisable(false);
        n.setVisible(true);
      } else {
        n.setDisable(true);
        n.setVisible(false);
      }
    }
  }


  @Override
  public void start(Stage primaryStage) {
    instance = this;
    try {
      var root = (Pane) FXMLLoader.load(getClass().getResource("MainPage.fxml"));
      root.setStyle(BikeTourPlusApplication.DARK_MODE ? "-fx-base: rgba(20, 20, 20, 255);" : "");
      var scene = new Scene(root);
      primaryStage.setScene(scene);
      primaryStage.setMinWidth(100);
      primaryStage.setMinHeight(100);
      primaryStage.setTitle("BTP");
      primaryStage.show();
      refresh();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * <p>
   * initiate everything and setup keyboard events
   * </p>
   * 
   * @param
   * @return void
   */
  public void initialize() throws IOException {
	int i=0;
    for (Node n : display.getChildren()) {
    	if (i!=2) {
    		n.setVisible(false);
    		n.setDisable(true);
    	}
    	i++;
    }
    // FXMLLoader loader = new FXMLLoader(getClass().getResource("pages/createBiketour.fxml"));
    // var root1 = loader.load();
    // this.cbc = (createBiketourController) loader.getController();
    // System.out.println(cbc.toString());
    // FXMLLoader loader2 = new FXMLLoader(getClass().getResource("pages/ViewGuides.fxml"));
    // var root2 = loader2.load();
    // this.vu = (ViewUsers) loader2.getController();
    // System.out.println(vu.toString());
    // Stage stage = new Stage();
    // var s1 = new Scene((Parent) root1);
    // var s2 = new Scene((Parent) root2);
    // stage.setScene(s1);
    // stage.show();
  }

  // Register the node for receiving refresh events
  public void registerRefreshEvent(Node node) {
    refreshableNodes.add(node);
  }

  // Register multiple nodes for receiving refresh events
  public void registerRefreshEvent(Node... nodes) {
    for (var node : nodes) {
      refreshableNodes.add(node);
    }
  }

  // remove the node from receiving refresh events
  public void removeRefreshableNode(Node node) {
    refreshableNodes.remove(node);
  }

  // fire the refresh event to all registered nodes
  public void refresh() {
    for (Node node : refreshableNodes) {
      node.fireEvent(new Event(REFRESH_EVENT));
    }
  }

  public static BtpFxmlView getInstance() {
    return instance;
  }
}
