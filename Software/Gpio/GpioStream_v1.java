package mediaplayer;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GpioStream_v1 {

    public static boolean ringStatus = false;
    private static final Logger LOGGER = Logger.getLogger(GpioStream_v1.class.getName());
    private static final int PIN_NUM = 22;
    private static final File EXPORT_FILE_PATH = new File("/sys/class/gpio/export");
    private static final File DIRECTION_PATH = new File("/sys/class/gpio/gpio" + PIN_NUM + "/direction");
    private static final File VALUE_FILE_PATH = new File("/sys/class/gpio/gpio" + PIN_NUM + "/value");
    private static final char[] VALUE_BUFFER = new char[1];
    char previousValue = '0';
    void initializeGpio() throws IOException {
        exportGpioPin();
        setGpioDirection("in");
        System.out.println("hi");
    }

    void exportGpioPin() throws IOException {
        if (!DIRECTION_PATH.exists()) {
            try (FileWriter exportWriter = new FileWriter(EXPORT_FILE_PATH)) {
                exportWriter.write(String.valueOf(PIN_NUM));
            }
        }
    }

    void setGpioDirection(String direction) throws IOException {
        try (FileWriter directionWriter = new FileWriter(DIRECTION_PATH)) {
            directionWriter.write(direction);
        }
    }

    void startReadingGpioValue(Stage stage, String fxmlfile) {
        new Thread(() -> {
            while (true) {
                try (FileReader valueReader = new FileReader(VALUE_FILE_PATH)) {
                    if (valueReader.read(VALUE_BUFFER) != -1) {
                        // Only respond if the value has changed
                        if (VALUE_BUFFER[0] != previousValue) {
                            previousValue = VALUE_BUFFER[0];  // Update the previous value

                            if ((VALUE_BUFFER[0] - 48) == 1) {
                                ringStatus = true;
                                Platform.runLater(() -> {
                                    loadNewSceneFromFXML(stage, fxmlfile);
                                    stage.setFullScreen(true);
                                });
                            }
                        }
                    }
                    // Sleep for a short time to reduce resource usage
                    Thread.sleep(100);  // Adjust the sleep time to balance responsiveness and resource consumption
                } catch (IOException e) {
                    LOGGER.log(Level.SEVERE, "Error reading GPIO value", e);
                    break; // Break out of the loop if an error occurs
                } catch (InterruptedException e) {
                    LOGGER.log(Level.SEVERE, "Thread interrupted", e);
                    Thread.currentThread().interrupt();  // Handle thread interruption
                    break; // Exit the loop if the thread is interrupted
                }
            }
        }).start();
    }

    private void loadNewSceneFromFXML(Stage stage, String fxmlFilePath) {
        try {
            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFilePath));
            Parent newRoot = loader.load();

            // Set the new FXML file to the current scene
            Scene currentScene = stage.getScene();
            currentScene.setRoot(newRoot);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
