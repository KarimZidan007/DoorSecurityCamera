# 


# Introduction and Goals
The following dcoumentation was written to follow arc42 documentation way.  
We have developed a door security camera aimed at enhancing home and office security by monitoring and capturing footage of activities around entry points. 

##   underlying business goals

- Increasing the security of homes and businesses with an affordable, scalable solution.
- Providing users with a reliable, easy-to-use interface for monitoring their surroundings.
- Ensuring footage can be securely stored and reviewed easily.


## Requirements Overview

### System Requirements

- Hardware: The system operates on a Raspberry Pi 4, using its processing power to manage video recording, playback, and file management.
- Camera: The Raspberry Pi camera module is used for recording, capturing video when the ring button is pressed.
- Storage: Recordings are saved on local storage in a specific directory using a timestamp naming convention to easily differentiate and organize files.
- Trigger Mechanism: A physical doorbell (ring button) serves as the trigger for the camera to start recording.
- Also the owner can record a video when needed from the live view screen


### Functional Requirements

- Automatic Recording: Recording automatically starts when the ring button is pressed. This action also triggers the system to:

    - Open a live view screen, allowing the owner to monitor the door in real time.
    - Display a recording indicator on the screen.

- Manual Stop: The recording continues until the owner manually presses the stop button.
- Recording Management: Files are saved in a specific directory. The file names follow a timestamp naming convention (e.g., YYYYMMDD_HHMMSS.mp4) to ensure each recording is unique and easy to locate.
- Playback Features: The built-in media player offers:

    - Ability to play recorded videos.
    - Options to adjust playback speed (e.g., 0.5x, 1x, 2x), giving the owner flexibility in reviewing footage.


### Performance Requirements

- Low Latency: The live view feed must display with minimal delay (<2 seconds) to ensure real-time monitoring when the ring button is pressed.
- Recording Duration: There is no fixed recording duration; it is controlled by the owner's manual action (pressing stop). The system must handle long recordings efficiently without crashes or file corruption.
- Storage: The system must have enough storage space to handle multiple high-definition recordings without performance degradation.
- Playback: The media player should play video smoothly, with no stuttering, even when the playback speed is changed.



###Software Requirements
- Operating System: a custom Linux image that will be developed by yocto project.
- Video Encoding: Recordings are saved in MP4 format using the H.264 codec to ensure efficient storage and compatibility with playback systems.
- Timestamping: The file system should automatically generate a timestamp for each recording based on the current system time.
- Directory Management: The system should allow the user to configure the directory path for storing recordings, or default to a designated directory (e.g., /home/pi/records/).

### User Requirements


- Simple UI: Users must have a clear and intuitive interface to:

    - View the live feed when the ring button is pressed.
    - Start and stop recordings easily.
    - Review recorded footage through the media player with options to adjust playback speed.

- Notifications: The system should notify the user when recording starts and stops (e.g., via visual indicators or audio alerts).
- Playback Control: Users should be able to navigate and control playback speed from the media player interface.


### Security and Privacy Requirements

- File Protection: Recorded videos should be stored securely, with restricted access to avoid unauthorized viewing or tampering.
- Data Integrity: Ensure that recordings are not corrupted or lost during the saving process, especially for long durations.














# System Scope and Context

## Contents

The **System Scope and Context** section outlines the boundaries of the door security camera system, distinguishing it from other related systems or components. It also identifies the external interfaces that enable communication with neighboring systems and users. This section delves into both the business context (domain-specific inputs and outputs) and the technical context (channels, protocols, and hardware).

### Business Context

The door security camera system primarily serves as a home or office security solution, interacting directly with users and delivering key features:

- **User Inputs:**
  - **Ring Button Press**: When the doorbell is pressed, the system is triggered to start recording and display the live view.
  - **Start Recording Button**: Users have the flexibility to manually start recording at any time through a "Start Recording" button on the live view screen.
  - **Stop Recording**: Users can manually stop the recording using the designated button within the UI.

- **User Outputs:**
  - **Live View Monitoring**: Users can monitor real-time footage through the live view interface, which is displayed using a JavaFX program with GStreamer as the backend.
  - **Recordings**: The system uses ffmpeg to record the live view and saves the videos in MP4 format. Files are stored in a directory using a timestamp naming convention. These recordings can be played back later via the media player, which offers playback speed control.
  - **Playback Features**: The user can interact with recorded footage, including adjusting the playback speed for review.

These interactions represent the core business processes, where the system interfaces with users, enhancing the safety and security of homes or offices.

### Technical Context

The technical context defines the communication protocols, hardware, and software components that allow the door security camera system to function as intended.

- **Hardware**:
  - The system is built on a Raspberry Pi 4, which provides the necessary computing power and I/O capabilities.
  - A Raspberry Pi camera module captures video footage.
  - The system includes a doorbell (ring button) that serves as a hardware trigger for recording.

- **Software**:
  - **Custom Linux Image (Yocto Project)**: The system runs on a custom Linux image generated using the Yocto Project. This image includes only the necessary components and libraries to optimize performance on the Raspberry Pi 4.
  - **JavaFX and GStreamer**: The live view of the camera is managed through a JavaFX application that uses GStreamer as the backend for smooth video display.
  - **Video Processing (FFmpeg)**: The system uses ffmpeg to record the camera's live view. Users can trigger the recording either when the ring button is pressed or manually through the "Start Recording" button on the live view screen. The recordings are saved in MP4 format using the H.264 codec to ensure efficient storage and compatibility.
  - **Media Playback**: The media player allows users to play and control recorded footage, including features like playback speed adjustment.

- **Protocols and Interfaces**:
  - **GPIO**: The ring button is connected via GPIO, which triggers the camera to start recording when pressed.
  - **File System**: Recordings are saved to the local file system in a user-defined directory, using a timestamp for each file.
  - **Display Interface**: The live view interface is implemented in JavaFX and rendered via GStreamer, interfacing directly with the Raspberry Pi’s display output.

### Differentiation of Business and Technical Context

- **Business Context**: Focuses on user interactions with the system, such as triggering recordings, accessing live views, and playing back recordings. Users have the flexibility to start or stop recording at any time, enhancing control over the security system.

- **Technical Context**: Involves the detailed configuration of hardware (Raspberry Pi 4, camera module), software (custom Yocto-generated Linux image, JavaFX, GStreamer, ffmpeg), and protocols (GPIO, file system) that enable the system to function. This includes the technical workflows that support user inputs and outputs, such as capturing and saving videos, managing directories, and providing live feed access.





# Solution Strategy

## Contents

The **Solution Strategy** outlines the key decisions that shape the architecture of the door security camera system. These decisions provide the foundation for the design and development of the system, addressing both technical and organizational aspects.

### Technology Decisions
- **Yocto Project for Custom Linux Image**: We selected the Yocto Project to generate a lightweight and optimized Linux image tailored specifically for the Raspberry Pi 4 hardware. This decision ensures that only essential packages and libraries are included, improving performance and reducing system overhead.
- **JavaFX and GStreamer**: JavaFX was chosen as the front-end framework for building the user interface, providing flexibility in creating dynamic and intuitive views. GStreamer was selected as the backend for streaming and displaying the live camera view, ensuring smooth video rendering and compatibility with various media formats.
- **FFmpeg for Recording**: FFmpeg is used for recording the live view and saving the footage in MP4 format. This tool provides robust video processing capabilities and ensures compatibility with common video players.
- **GPIO for Ring Button**: The system utilizes Raspberry Pi’s GPIO to connect the ring button, which acts as a trigger for the camera to start recording.

### Top-Level Decomposition and Design Patterns
- **Modular Architecture**: The system follows a modular design, where key components such as the live view, recording feature, and playback functionality are separated into distinct modules. This ensures easier maintenance, scalability, and future upgrades.
- **Observer Pattern**: The "Start Recording" button and ring button triggers follow an event-driven approach using the Observer pattern. This pattern allows the system to react to user inputs or external triggers (e.g., the ring button press) efficiently.

### Key Quality Goals
- **Performance and Efficiency**: By using a custom Yocto-generated Linux image, we ensure that the system is lightweight and optimized, running only necessary services on the Raspberry Pi 4. GStreamer and FFmpeg also provide efficient handling of video streams and recording, maintaining smooth performance even in extended operations.
- **Scalability**: The modular architecture allows for easy scalability. Additional features such as cloud integration or enhanced security measures can be added in the future without disrupting existing functionality.
- **Reliability and User Control**: The system is designed to provide reliable video streaming and recording. Users have full control over recording, with the ability to start and stop recordings manually or trigger them via the ring button.

### Organizational Decisions
- **Agile Development Process**: We adopted an agile development process to facilitate quick iterations, improvements, and better collaboration among team members. This method also ensures that feedback from stakeholders can be incorporated promptly during the development cycle.
- **Delegation of Tasks**: Certain tasks, such as testing and optimization of the video processing components, may be delegated to specialists or third-party developers with expertise in GStreamer or FFmpeg, ensuring the highest quality and performance.

## Motivation

These decisions form the foundation of the system architecture. The use of Yocto for custom Linux builds aligns with performance and optimization goals, while the combination of JavaFX, GStreamer, and FFmpeg provides an efficient and flexible way to manage video streaming and recording. The modular and event-driven design ensures scalability, maintainability, and responsiveness.

## Form

The decisions were made based on the problem statement, which required a lightweight, efficient, and reliable security system. Performance and flexibility were key quality goals, leading to the selection of technologies like Yocto, JavaFX, and FFmpeg. The architectural patterns and design strategies ensure that the system can be extended and maintained with ease.

Refer to the following sections for more details on the implementation of each component and feature.















# Building Block View

## Contents

The **Building Block View** presents the static decomposition of the door security camera system into its core building blocks. This view highlights the structure and interrelationships of the system's components, providing a clear floor plan of the system architecture.

## Whitebox Overall System

### Overview Diagram

*Insert an overview diagram here that illustrates the top-level decomposition of the system into its major building blocks.*

### Motivation

The overall system is divided into distinct building blocks to clearly delineate responsibilities and interactions. This decomposition simplifies the management of complexity, enables better communication with stakeholders, and supports a modular approach to system development and maintenance.

### Contained Building Blocks

#### Black Box Descriptions

The following table provides a high-level overview of the main building blocks in the system, describing their responsibilities and interfaces.

| **Name**                | **Responsibility**                                       |
|-------------------------|----------------------------------------------------------|
| **Main menu Module**    | Show the welcome page and gives the user the ablity to navigate through the system |
| **Live View Module**    | Manages the display of real-time video feed using GStreamer and JavaFX. |
| **Playback Module**     | Facilitates playback of recorded videos, including features like playback speed adjustment. |
| **Recording Module**    | Handles the recording of live view video using FFmpeg. Manages start/stop recording operations. |
| **User Interface**      | Provides the graphical interface for users to interact with the live view and recording functionalities. |
| **GPIO Interface**      | Interfaces with the Raspberry Pi's GPIO for handling the ring button and other hardware interactions. |
| **File Management**     | Manages file storage, including saving recordings with timestamp naming conventions. |

![alt text](<Screenshot from 2024-09-09 20-20-53.png>)

#### Important Interfaces

- **Live View Module to Recording Module**: The Live View Module sends video data to the Recording Module for recording. Interfaces include data streaming protocols and control commands.
- **User Interface to Recording Module**: Commands from the User Interface (e.g., start/stop recording) are sent to the Recording Module. This interface includes user-triggered events and status updates.
- **GPIO Interface to User Interface**: GPIO events (e.g., ring button press) are communicated to the User Interface to trigger recording and update the live view.

## Level 2

### White Box Live View Module

Describes the internal structure of the Live View Module.

- **Components**:
  - **GStreamer Pipeline**: Handles video stream processing and rendering.
  - **JavaFX Renderer**: Displays the video feed within the JavaFX application.

- **Interfaces**:
  - **Input**: Receives video data from the camera module.
  - **Output**: Sends video data to the Recording Module.

### White Box Recording Module

Describes the internal structure of the Recording Module.

- **Components**:
  - **FFmpeg Recorder**: Manages video recording and file saving.
  - **Recording Controller**: Handles user commands to start and stop recording.

- **Interfaces**:
  - **Input**: Receives video data from the Live View Module.
  - **Output**: Saves recorded video to the file system.

## Level 3

### White Box GStreamer Pipeline

Specifies the internal structure of the GStreamer Pipeline.

- **Components**:
  - **Source Element**: Captures video from the camera.
  - **Filter Elements**: Processes the video stream (e.g., scaling, color correction).
  - **Sink Element**: Renders the video to the display.

- **Interfaces**:
  - **Input**: Receives video data from the camera.
  - **Output**: Sends rendered video to the JavaFX Renderer.

### White Box FFmpeg Recorder

Specifies the internal structure of the FFmpeg Recorder.

- **Components**:
  - **Encoder**: Encodes the video data into MP4 format.
  - **File Writer**: Saves the encoded video to disk.

- **Interfaces**:
  - **Input**: Receives video data from the Live View (x11 server) Module.
  - **Output**: Writes video files to the specified directory.

---

This section provides a hierarchical breakdown of the system's architecture, starting from the overall system view down to the detailed internal structure of key components. Each building block is described in terms of its purpose, responsibilities, interfaces, and internal structure, providing a comprehensive overview of the system's design.


























# Runtime View

## Contents

The runtime view describes the concrete behavior and interactions of the system's building blocks through various scenarios. This section covers:

- Important use cases and features
- Interactions at critical external interfaces
- Operation and administration

## Motivation

Understanding the runtime behavior of building blocks helps stakeholders visualize how the system operates and how components interact. This view is crucial for those who prefer not to delve into static models.

## Form

The following scenario is illustrated using a sequence diagram, which depicts interactions over time among system components.

## Scenario: Start and Stop Recording

- *Sequence Diagram:*

    ![alt text](<Door security camera Sequence diagram (1).png>)


- *Description:*

    This sequence diagram illustrates the process of starting and stopping a recording from the live view screen. The scenario includes:

    1. **Start Recording:**
        - The user clicks the "Start Recording" button or the door ring button is pressed.
        - The system initiates the recording process.
        - The live view screen updates to show the recording status.
        - Recording continues until stopped by the user.

    2. **Stop Recording:**
        - The user clicks the "Stop Recording" button.
        - The system halts the recording process.
        - The recording is finalized and saved with a timestamp-based filename in the specified directory.

    Notable aspects include:
    - Interaction between the user interface and recording component.
    - Real-time updates to the live view and recording indicator.
    - Proper handling of start and stop commands to ensure accurate recording and storage.

## Additional Notes

- Ensure that the sequence diagram is clear and includes all relevant interactions.
- The description should provide context for the diagram, highlighting key interactions and their significance.
















# Architecture Decisions

## Contents

This section documents the key architecture decisions made during the development of the door security camera system. These decisions include the rationale behind selecting specific technologies, design patterns, and other architectural choices. Each decision is important due to its potential impact on the system's performance, scalability, and maintainability.

## Motivation

Documenting architecture decisions helps stakeholders understand the reasoning behind critical choices made in the system's design. This transparency aids in evaluating the system's suitability, troubleshooting issues, and guiding future enhancements.

## Decision Summary

### 1. Technology Stack

- **Decision**: Use JavaFX with GStreamer for the user interface and video streaming, and FFmpeg for recording.
- **Rationale**: JavaFX provides a robust framework for building the graphical user interface, while GStreamer offers extensive support for video processing and streaming. FFmpeg is a powerful tool for recording and encoding video, making it suitable for high-quality video recording in MP4 format.
- **Implications**: This combination ensures a high-quality video experience and effective recording capabilities but requires careful integration and testing.

### 2. Recording Control Mechanism

- **Decision**: Implement a manual recording control system with a "Start Recording" button on the live view screen.
- **Rationale**: Allows users to initiate recording at any time, providing flexibility and control over what is captured. This approach caters to various user needs and scenarios, such as unexpected events.
- **Implications**: Requires careful handling of user input and state management to ensure that recordings start and stop correctly.

### 3. File Storage and Naming Convention

- **Decision**: Save recordings in a specific directory with timestamp-based filenames.
- **Rationale**: Timestamp naming ensures that each recording is uniquely identifiable and sortable. This approach simplifies file management and retrieval.
- **Implications**: Requires implementation of file management logic to handle naming, storage, and potential conflicts.

### 4. System Platform

- **Decision**: Use Yocto Project to generate a custom Linux image for deployment on Raspberry Pi 4.
- **Rationale**: Yocto Project allows for the creation of a tailored Linux environment optimized for the hardware, ensuring efficient resource usage and compatibility.
- **Implications**: Involves additional setup and configuration work to build and maintain the custom Linux image.

### 5. Playback Speed Adjustment

- **Decision**: Include playback speed control in the media player.
- **Rationale**: Provides users with the ability to adjust playback speed, which can be useful for reviewing recorded footage more efficiently.
- **Implications**: Requires integration with the media player and careful handling of playback controls to ensure smooth performance.

## Decision Tracking

For more detailed documentation of each decision, including the criteria used and alternatives considered, refer to the specific sections within the white box templates or the associated design documents.

---

This section should help in understanding and tracing the major architectural decisions that shape the system. Adjust the details based on any additional decisions or specific contexts relevant to your project.























# Quality Requirements

## Content

This section outlines the quality requirements for the system, structured as a quality tree with associated scenarios. The most critical quality goals have been described in the previous section. Here, we include additional quality requirements with lesser priority, which, if not fully achieved, do not pose significant risks.

## Motivation

Understanding quality requirements is crucial for making informed architectural decisions. This section helps stakeholders know what is important to them in concrete and measurable terms.

## Quality Tree

The quality tree provides a high-level overview of quality goals and requirements, structured hierarchically to show their priorities and relationships.

- **Root Node**: Quality or Usefulness
  - **Branch 1**: Performance
    - **Leaf 1**: Response Time
    - **Leaf 2**: Throughput
  - **Branch 2**: Reliability
    - **Leaf 1**: System Availability
    - **Leaf 2**: Fault Tolerance
  - **Branch 3**: Usability
    - **Leaf 1**: User Interface Design
    - **Leaf 2**: Accessibility
  - **Branch 4**: Security
    - **Leaf 1**: Data Protection
    - **Leaf 2**: Access Control

For each branch and leaf, link to the relevant quality scenarios that provide concrete examples of how these quality attributes should be met.

## Quality Scenarios

Quality scenarios define how the system should respond to specific stimuli and describe various aspects of the system’s behavior and performance.

### Usage Scenarios

- **Scenario 1: Response Time**
  - **Description**: The system must respond to a user’s request within 1 second.
  - **Stimulus**: User initiates a search query.
  - **Response**: System provides search results in less than 1 second.
  - **Measurement**: Time from user input to display of search results.

- **Scenario 2: System Availability**
  - **Description**: The system must be available 99.9% of the time.
  - **Stimulus**: System uptime monitoring.
  - **Response**: System should log uptime and downtime.
  - **Measurement**: Percentage of uptime over a given period.

### Change Scenarios

- **Scenario 1: Adding New Functionality**
  - **Description**: Adding a new feature should not degrade existing performance.
  - **Stimulus**: Introduction of a new feature.
  - **Response**: System performance metrics should remain within acceptable limits.
  - **Measurement**: Performance benchmarks before and after the feature addition.

- **Scenario 2: Changing Quality Attribute Requirements**
  - **Description**: Changing security requirements should not compromise existing functionalities.
  - **Stimulus**: Update to security requirements.
  - **Response**: System must adapt to new security requirements without impacting functionality.
  - **Measurement**: Compliance with updated security standards without loss of functionality.


<br/>
<br/>
<br/>
<br/>
<br/>




# the following sections are about techincal parts and chanllenges that we want to concentrate on: 



  
<br/>
<br/>
<br/>
<br/>









# Camera live view parsing using opencv 
For working with media on a Raspberry Pi 4 and accessing a USB camera through a JavaFX application, OpenCV is one of the best frameworks for the following reasons:
Why OpenCV is Recommended
    Cross-Platform Support: OpenCV works well on Raspberry Pi 4 and is compatible with Java, making it easier to access USB cameras.
    Efficient Video Processing: OpenCV is optimized for real-time video capture and processing, which is crucial for resource-constrained devices like Raspberry Pi.
    USB Camera Support: It provides easy access to USB cameras through the VideoCapture class, with support for various video formats and frame sizes.
    Integration with JavaFX: OpenCV integrates smoothly with JavaFX for displaying video streams in real-time. You can use OpenCV for camera capture and convert the frames into JavaFX Image objects for display.

## Overview

This JavaFX application captures video from the USB camera using OpenCV and displays the live feed in a GUI window. The application consists of three main parts:
1. Initializing and capturing video from the camera.
2. Converting the captured frames to a format compatible with JavaFX.
3. Displaying the live video feed using an `ImageView` in JavaFX.

## Key Components

### OpenCV Setup
```java
static { System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }
```
- This line loads the OpenCV native library required to access the computer vision functionality. Make sure OpenCV is correctly installed and its library files are available to the Java application.

### JavaFX Setup
- The application extends the `Application` class to set up the JavaFX graphical user interface (GUI).
- `ImageView` is used to display the live camera feed, and it is added to a `StackPane`, which is set as the root of the scene.

```java
imageView = new ImageView();
StackPane root = new StackPane();
root.getChildren().add(imageView);
Scene scene = new Scene(root, 640, 480);
primaryStage.setTitle("Camera Feed");
primaryStage.setScene(scene);
primaryStage.show();
```

### Camera Initialization
```java
camera = new VideoCapture(0);
```
- The `VideoCapture` object is used to access the default camera (`0` refers to the default USB camera). If the camera cannot be opened, the application will print an error message.

### Capturing Video Frames
- The `startCameraFeed()` method runs in a separate thread to continuously capture frames from the camera without blocking the JavaFX UI thread.
- It uses OpenCV’s `camera.read(frame)` to capture each frame.
- The frames are updated in the `ImageView` using `Platform.runLater()`, ensuring the updates happen on the JavaFX Application thread.

```java
new Thread(() -> {
    Mat frame = new Mat();
    while (true) {
        if (camera.read(frame)) {
            Image image = matToImage(frame);
            Platform.runLater(() -> imageView.setImage(image));
        }
        Thread.sleep(33); // Approximately 30 FPS
    }
}).start();
```

### Converting OpenCV Mat to JavaFX Image
- OpenCV stores frames as `Mat` objects, which need to be converted into JavaFX `Image` objects for display.
- The `matToImage()` method performs this conversion by first converting the image from BGR (used by OpenCV) to RGB (used by JavaFX), and then converting the `Mat` object to a `BufferedImage` before converting it to a JavaFX `Image` using `SwingFXUtils.toFXImage()`.

```java
private Image matToImage(Mat mat) {
    Mat convertedMat = new Mat();
    Imgproc.cvtColor(mat, convertedMat, Imgproc.COLOR_BGR2RGB); // Convert BGR to RGB
    BufferedImage bufferedImage = new BufferedImage(convertedMat.width(), convertedMat.height(), BufferedImage.TYPE_3BYTE_BGR);
    byte[] data = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
    convertedMat.get(0, 0, data);
    return SwingFXUtils.toFXImage(bufferedImage, null);
}
```

### Releasing the Camera
- The `stop()` method ensures that the camera is released when the application is closed.

```java
@Override
public void stop() {
    if (camera != null && camera.isOpened()) {
        camera.release(); // Release the camera on application exit
    }
}
```

## Running the Application

### Dependencies
- OpenCV must be installed, and its native library (e.g., `opencv_java*.so`) should be correctly referenced in your project.
- JavaFX must be included in your build environment (e.g., as a module or via Maven/Gradle).

### Compile and Execute
- Compile the application and make sure the native OpenCV library is loaded. Run the application, and a window will display the live video feed from the USB camera.

## Conclusion

This JavaFX application provides a simple interface for displaying live camera feed using OpenCV. The camera frames are captured in a separate thread, converted to JavaFX `Image` format, and displayed on the screen using an `ImageView`. This approach can be extended for applications like surveillance systems, video processing, or interactive installations.













<br/>
<br/>
<br/>
<br/>











# challenges and how we overcame it
## making javafx work on on the yocto image 


## how to use the camera 
we can use a lot of cli tools available to show a live view from the camera or record such as : 
- rpicam-apps 
- libcamer-apps 
- gstreamer  

the problem of all of this cli tools is that despite being able to access the camera, view and record effectively, I can't use that input inside my javafx program.  
each tool has a pop up screen that displays the video for the raspberry pi user, we wanted to be able to view the video on a javafx component inside our application so we found out that in order to do this we have to use opencv library (which is written in cpp) and java binding for opencv so that we can interface with the opencv library easily from the java application.  
<br/>
the other option we found was to use libcamer-src which is the tool that is able to access the raspberrypi camera and provide gstremer with the camera data in a pipeline which we found a way to view in the javafx program



## the problem of recording the camera view
if we opened the camera using any tool in The javafx app, we also will want to record it's view. This is a problem because the camera now became a shared resource and java doesn't help much with accessing the hardware.  
this problem can be solved by duplicating the data stream returned from gstreamer pipeline using arg -tee, but we faced problems which took too much time so we decided to access the camera hardware when we want to view and record the screen if we want to record the view using another tool.

## ffmpeg as a screen recorder 
we decided to use ffmpeg which is a cli tool that can be invoced using ProcessBuilder class in java, we choose to implement this functionality using singleton design pattern so that only one recording operation can happen at the same time. This way we can preserve the resources and prevent any error that may happen in the implementation which may cause multiple recording operation at the same time.  
the code was implemented, tested on host successfully and then deployed on the target.

## ffmpeg not working on the wayland display server
Once the code was deployed on the target we found out that there is a compatibility issue with th display server used in the server ( wayland server).
we tried several tools to record the screen and we decided to go with another cli tool that was reported to support wayland display server.

## trying to use wf-recorder instead 
We tried the new tool (wf-recorder) and it seemed to be working fine at first but upon taking a close look we found that it starts recording three seconds after executing the command and some times it results in broken video files, once we tried it with the ProcessBuilder class in java which enable us to execute command line tools we found out that the call .destroy() which destroys the process so that it stops recording mimics the behavior of sending SIGINT to the process but doesn't do that exactly which results in that the wf-recorder exits with an exit status error and causes a corrupted video file each time.  
in order to overcome this problem and we needed to change the way of stopping the process to sending the actual SIGINT to the process which required using jna which caused a new layer of dependencies while building the image.

## switching to x11 display server 
finally we decided to switch the display server from wayland to x11 server so that we can use ffmpeg, we did that and finally the recording process wroked fine.





