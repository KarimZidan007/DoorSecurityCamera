package mediaplayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ScreenRecorder {

    private static ScreenRecorder instance; // Singleton instance
    private static boolean isRecording = false, verbose = true; // Static variable to track recording status
    private Process recordingProcess;

    // Private constructor to prevent instantiation
    private ScreenRecorder() {
    }

    // Public method to provide access to the Singleton instance
    public static synchronized ScreenRecorder getInstance() {
        if (instance == null) {
            instance = new ScreenRecorder();
        }
        return instance;
    }

    // Starts screen recording
    public synchronized void startRecording(String outputFile, int x, int y, int width, int height) throws IOException {
        if (isRecording) {
            System.out.println("Recording is already in progress.");
            return;
        }
        else
        {
            
            
        

        // Set recording status to true
        isRecording = true;
        
        // Use the ProcessBuilder constructor with arguments directly
        ProcessBuilder processBuilder = new ProcessBuilder(
                "ffmpeg",
                "-video_size", width + "x" + height,
                "-framerate", "30",
                "-f", "x11grab",
                "-i", ":0.0+" + x + "," + y,
                outputFile
        );
        processBuilder.redirectErrorStream(true);  // Merge stderr with stdout
        recordingProcess = processBuilder.start();
        System.out.println("Asem oki");
}
        // Optional: Print ffmpeg output for debugging
        if (verbose) {
            new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(recordingProcess.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);  // Output ffmpeg logs
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    // Stops screen recording
    public synchronized void stopRecording() {
        if (recordingProcess != null) {
            recordingProcess.destroy();
            recordingProcess = null; // Clear the process reference
            isRecording = false; // Update recording status
            System.out.println("Recording stopped.");
        } else {
            System.out.println("No recording process found.");
        }
    }

    public boolean getRecordingStatus() {
        return isRecording;
    }
}
