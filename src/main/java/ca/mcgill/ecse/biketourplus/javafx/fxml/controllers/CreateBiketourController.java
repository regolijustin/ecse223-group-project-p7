/**
 * @author Antoine (AntoineDeng)
 */
package ca.mcgill.ecse.biketourplus.javafx.fxml.controllers;

import ca.mcgill.ecse.biketourplus.controller.ExtraFeatures;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class CreateBiketourController {
  @FXML
  private ProgressBar progress;
  @FXML
  private Button createBiketour;
  @FXML
  private ImageView logo;

  private double prog; // progress of the progress bar

  /**
   * <p>
   * initialize progress bar to 0
   * </p>
   * 
   * @param
   * @return void
   */
  @FXML
  public void initialize() {
    setProg(0);
    //logo = new ImageView();
    //logo.setVisible(true);
  }

  /**
   * <p>
   * initiate biketour creation
   * </p>
   * 
   * @param MouseEvent event
   * @return void
   */
  // Event Listener on Button[#createBiketour].onMouseClicked
  @FXML
  public void createBiketour(MouseEvent event) {
    ExtraFeatures.initiateBikeTour();
    setProgress(1);
  }

  /**
   * <p>
   * set progress bar
   * </p>
   * 
   * @param double p
   * @return void
   */
  public void setProgress(double p) {
    this.setProg(p);
    progress.setProgress(prog);
  }

  /**
   * <p>
   * get the progress
   * </p>
   * 
   * @param
   * @return double prog
   */
  public double getProg() {
    return prog;
  }

  /**
   * <p>
   * set progress bar
   * </p>
   * 
   * @param double prog
   * @return
   */
  public void setProg(double prog) {
    this.prog = prog;
  }

  /**
   * <p>
   * reset progress bar
   * </p>
   * 
   * @param MouseEvent event
   * @return void
   */
  @FXML
  public void moveReset(MouseEvent event) {
    if (progress.getParent().getUserData() != null) {
      setProgress(0);
    }
    progress.getParent().setUserData(null);
  }
}
