/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gstreamer;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author karim
 */
 class MainScreen {
    // Static reference to the JFrame instance
    public static JFrame frame;
    
    // Static reference to the JPanel where video will be displayed
    public static JPanel panel_video;

    // Static initializer to set up the frame and panel
    static {
        // Initialize the main JFrame
        frame = new JFrame("Main Screen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());
        
        // Initialize the video panel
        panel_video = new JPanel();
        panel_video.setLayout(new BorderLayout()); // Allows components to stretch and fit
        
        // Add the video panel to the main frame
        frame.add(panel_video, BorderLayout.CENTER);
        
        // Display the frame
        frame.setVisible(true);
    }
 }
