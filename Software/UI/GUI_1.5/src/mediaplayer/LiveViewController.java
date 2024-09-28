package mediaplayer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javax.swing.SwingUtilities;
import org.freedesktop.gstreamer.Gst;
import org.freedesktop.gstreamer.Pipeline;
import org.freedesktop.gstreamer.Version;
import org.freedesktop.gstreamer.elements.AppSink;
import org.freedesktop.gstreamer.swing.GstVideoComponent;


// 
import javafx.embed.swing.SwingNode;


/**
 * FXML Controller class
 *
 * @author asem
 */
public class LiveViewController implements Initializable {

    @FXML
    private BorderPane borderPane;  // The BorderPane from FXML where video will be shown
    @FXML
    private Button returnMain;

    private Pipeline pipeline;
    @FXML
    private ImageView recordLable;
    @FXML
    private Button btnStartRec;
    @FXML
    private ImageView startRecImg;
    @FXML
    private Button btnStopRec;
    @FXML
    private ImageView StopRecImg;
    
    private boolean  isRecord = false ; 

    @Override
    public void initialize(URL url, ResourceBundle rb) {
      /*   recordLable.setVisible(false );
        // Initialize GStreamer
        Gst.init(Version.BASELINE, "BasicPipeline");

        // Create GStreamer pipeline
        pipeline = (Pipeline) Gst.parseLaunch("libcamerasrc ! videoconvert ! appsink name=sink");

        AppSink sink = (AppSink) pipeline.getElementByName("sink");

        // Create the GStreamer video component
        GstVideoComponent gstVideoComponent = new GstVideoComponent(sink);
        gstVideoComponent.setSize(600, 560);
        gstVideoComponent.setVisible(true);

        // Create a SwingNode to display the GStreamer video component in JavaFX
        SwingNode swingNode = new SwingNode();
        SwingUtilities.invokeLater(() -> {
            swingNode.setContent(gstVideoComponent);
        });

        // Add the SwingNode (which contains the GstVideoComponent) to the BorderPane
        borderPane.setCenter(swingNode);

        // Play the pipeline
        pipeline.play();*/
    }

    @FXML
    private void returnMainAction(ActionEvent event) {
        try {
            BorderPane liveView = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));

            // Get the stage from the event source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Create a new scene with the liveView BorderPane
            Scene scene = new Scene(liveView);

            // Set the scene to the stage
            stage.setScene(scene);

            // Set the stage to full screen
            stage.setFullScreen(true);
        } catch (IOException ex) {
            Logger.getLogger(LiveViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void startRecordAction(ActionEvent event) {
        
        recordLable.setVisible(true);
       
        
    }

    @FXML
    private void stopRecordAction(ActionEvent event) {
        
        recordLable.setVisible(false);

        
    }
}
