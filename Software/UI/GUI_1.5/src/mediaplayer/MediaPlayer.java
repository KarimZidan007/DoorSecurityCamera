/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediaplayer;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.freedesktop.gstreamer.Gst;
import org.freedesktop.gstreamer.Pipeline;
import org.freedesktop.gstreamer.Version;
import org.freedesktop.gstreamer.elements.AppSink;
import org.freedesktop.gstreamer.swing.GstVideoComponent;



/**
 *
 * @author asem
 */
public class MediaPlayer extends Application {
    
      
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
        
        Scene  scene = new Scene(root);
        stage.setTitle("Eldo3fa media player");
        
        scene.setOnMouseClicked((MouseEvent e) -> {
            
            if(e.getClickCount() == 2){
                stage.setFullScreen(true);
            }
        });

        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
