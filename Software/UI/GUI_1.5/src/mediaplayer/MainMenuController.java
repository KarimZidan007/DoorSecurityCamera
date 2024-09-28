/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediaplayer;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author asem
 */
public class MainMenuController implements Initializable {

    @FXML
    private Button btnReplay;

    @FXML
    private Button btnLive;
    private StackPane mainStackPane;
    @FXML
    private VBox sideBottonContainer;
    private AnchorPane topImagepane;
    private AnchorPane bottomImagepane;
    private ImageView menuImageView;
    private ImageView menuImageView1;
    @FXML
    private BorderPane mainBroderPane;
   
    @FXML
    private Label timeLabel;
    @FXML
    private Label dateLable;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // Update the date and time every second
         // Create a Timeline to update every second
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateDateTime()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        
        // Initial update
        updateDateTime();
        /*sideBottonContainer.getStyleClass().add("sideBottonContainer");
        btnReplay.getStyleClass().add("sideButton");
        btnLive.getStyleClass().add("sideButton");*/

    }

    @FXML
    private void liveButtonAction(ActionEvent event) {
        try {
            BorderPane liveView = FXMLLoader.load(getClass().getResource("LiveView.fxml"));

            // Get the stage from the event source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Create a new scene with the liveView BorderPane
            Scene scene = new Scene(liveView);

            // Set the scene to the stage
            stage.setScene(scene);

            // Set the stage to full screen
            stage.setFullScreen(true);

        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

 private void updateDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        
        // Update the labels
        dateLable.setText( now.format(dateFormatter));
        timeLabel.setText( now.format(timeFormatter));
    }

    @FXML
    
    
    private void RplayButtonAction(ActionEvent event) {
        try {
            // Hide the showReplay element
            btnReplay.setVisible(false);

            // Load the new view
            BorderPane newView = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

            // Create a new stage (instead of modifying the current one)
            Stage newStage = new Stage();

            // Create a new scene with the new view
            Scene scene = new Scene(newView);

            // Set the new scene to the new stage
            newStage.setScene(scene);

            // Set the new stage style to UNDECORATED
            newStage.initStyle(StageStyle.UNDECORATED);

            // Set the stage to full screen
            newStage.setFullScreen(true);

            // Show the new stage
            newStage.show();

            // Optionally, hide the current window
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
