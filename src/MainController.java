import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.xml.sax.SAXException;
import userInterface.*;
import xml.*;


import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;


import static userInterface.VisualizationConstants.BACKGROUND_COLOR;

public class MainController extends Application {
    private static final String RESOURCE_FILE_PATH = "resources/MainResources";
    private static final int MILLIS_IN_SEC = 1000;
    private static final double UNIT_SEC = 1.0;

    private int framesPerSecond;
    private int millisecondDelay;
    private double secondDelay;

    private AbstractXml myXml;
    private UserInterface myUserInterface;
    private Scene myScene;
    private Stage myStage;
    private Animation myAnimation;
    private String myTitle;
    private File myConfigFile;
    private int updateTimer;
    private int updateFreq;
    private int normalUpdateFreq;
    private boolean isStep = false;
    private String userFile;

    private ResourceBundle resourceBundle;
    private boolean setState = false;


    @Override
    public void start(Stage stage) throws Exception {
        initializeResources();
        initVisualizations(stage);
    }

    private void initializeResources() {
        resourceBundle = ResourceBundle.getBundle(RESOURCE_FILE_PATH);
        framesPerSecond = Integer.parseInt(resourceBundle.getString("FPS"));
        millisecondDelay = MILLIS_IN_SEC / framesPerSecond;
        secondDelay = UNIT_SEC / framesPerSecond;
        normalUpdateFreq = Integer.parseInt(resourceBundle.getString("InitialUpdateFreq"));
        updateFreq = normalUpdateFreq;
        myTitle = resourceBundle.getString("InitialTitle");
    }

    private void initVisualizations(Stage stage) throws Exception {
        updateTimer = 0;
        var animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        myAnimation = animation;
        myUserInterface = new UserInterface( myTitle);
        myUserInterface.getMyGridView().generateBlankGrid();
        initButtons();
        myScene = initScene();
        stage.setScene(myScene);
        stage.setTitle(myTitle);
        stage.show();
        myStage = stage;
        var frame = new KeyFrame(Duration.millis(millisecondDelay), e -> step(secondDelay));
        animation.getKeyFrames().add(frame);
    }

    private void step(double elapsedTime) {
        if (setState) return;
        else if (updateTimer > updateFreq) {
            updateTimer = 0;
            myUserInterface.update();
        } else {
            updateTimer++;
        }
    }

    private Scene initScene(){
        Group root = myUserInterface.setScene();
        var scene = new Scene(root, Integer.parseInt(resourceBundle.getString("WindowWidth")), Integer.parseInt(resourceBundle.getString("WindowHeight")), BACKGROUND_COLOR);
        return scene;
    }

    private void initButtons() {

        this.myUserInterface.getMyButtons().getButtonList().add(new InfoButton(resourceBundle.getString("Help"),resourceBundle.getString("HelpWindowTitle"), resourceBundle.getString("HelpHeaderText"), resourceBundle.getString("HelpContentText")));

        SimulationButton selectFileButton = new SimulationButton(resourceBundle.getString("SelectFile"));
        selectFileButton.setOnAction(value -> {
            try {
                selectFilePrompt();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
        });

        this.myUserInterface.getMyButtons().getButtonList().add(selectFileButton);
        SimulationButton startButton = new SimulationButton(resourceBundle.getString("Start"));
        startButton.setOnAction(value -> startSimulation());

        this.myUserInterface.getMyButtons().getButtonList().add(startButton);

        this.myUserInterface.getMyButtons().getButtonList().add(new PauseButton(myAnimation, resourceBundle.getString("Pause"), resourceBundle.getString("Resume")));

        SimulationButton resetButton = new SimulationButton(resourceBundle.getString("Reset"));
        resetButton.setOnAction(value -> resetGrid());
        this.myUserInterface.getMyButtons().getButtonList().add(resetButton);

        SimulationButton stepButton = new SimulationButton(resourceBundle.getString("Step"));
        stepButton.setOnAction(value -> stepProcess());
        this.myUserInterface.getMyButtons().getButtonList().add(stepButton);

        SimulationButton setStateButton = new SimulationButton(resourceBundle.getString("SetState"));
        setStateButton.setOnAction(value -> setState(setStateButton));
        this.myUserInterface.getMyButtons().getButtonList().add(setStateButton);

        SimulationButton saveButton = new SimulationButton(resourceBundle.getString("Save"));
        saveButton.setOnAction(value -> {
            try {
                save();
            } catch (TransformerException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
        });
        this.myUserInterface.getMyButtons().getButtonList().add(saveButton);

        SimulationSlider speedSlider = new SimulationSlider(0, 2, 1, resourceBundle.getString("Speed"));
        speedSlider.getMySlider().valueProperty().addListener(e -> updateSpeed((double) Math.round(speedSlider.getMySlider().getValue())));
        this.myUserInterface.getMySlidersAndControls().addSlider(speedSlider);

        SimulationChoice edgeTypeChoice = new SimulationChoice(resourceBundle.getString("EdgeTypes").split(","), resourceBundle.getString("EdgeTypeChoiceBox"));
        edgeTypeChoice.getChoiceBox().setValue(resourceBundle.getString("EdgeTypes").split(",")[0]);
        edgeTypeChoice.getChoiceBox().valueProperty().addListener(e -> setEdgeType((String) edgeTypeChoice.getChoiceBox().getValue()));
        this.myUserInterface.getMySlidersAndControls().addChoiceBox(edgeTypeChoice);
    }

    private void setEdgeType(String edgeType) {
        System.out.println("for debug: " + edgeType);
        //TODO: set edge type @Eric
    }

    private void save() throws TransformerException, ParserConfigurationException {
        if (!checkFileSelected()) return;
        myXml.saveCurrentSimulation(this.myUserInterface.getMyGridView(), myConfigFile);
        System.out.println("configFile: " + myConfigFile);
        //TODO: save... @Dianne
    }

    private void setState(SimulationButton simulationButton) {
        if (!checkFileSelected()) return;
        this.setState = !setState;
        if (setState) simulationButton.setText(resourceBundle.getString("Resume"));
        else simulationButton.setText(resourceBundle.getString("SetState"));
    }

    private void selectFilePrompt() throws IOException, ParserConfigurationException, SAXException {
        FileChooser fileChooser = new FileChooser();
        myConfigFile = fileChooser.showOpenDialog(myStage);
        if (myConfigFile == null) {
            this.myUserInterface.displayErrorMsg(resourceBundle.getString("ErrorMsg_noFileSelected"));
            return;
        }
        StringBuilder myFile = new StringBuilder(myConfigFile.toString());
        for (int i = 0; i < myFile.length(); i++) {
            if (myFile.substring(i, i + 3).equals("xml")) {
                this.userFile = myFile.substring(i);
                break;
            }
        }
        this.myUserInterface.displaySimulationFilePath("Configuration File: " + myConfigFile.getName());

        //check which xml to make
        whichXml();
        myXml.parse(this.userFile);
        //System.out.println("In Main: " +myXml.getMaturityArray());
        myUserInterface.getMyGridView().initializeMyCellGrid(myXml);
        this.myUserInterface.addSimulationControls();
        this.myAnimation.pause();
    }

    private void whichXml(){
        StringBuilder myFile = new StringBuilder(myConfigFile.toString());
        String s = "";
        for (int i = 0; i < myFile.length(); i++) {
            if (myFile.substring(i, i + 10).equals("/xml_files")) {
                s = myFile.substring(i+11);
                System.out.println(s);
                break;
            }
        }

        if(s.charAt(0) == 'W'){
            myXml = new WaTorXml(this.myUserInterface);
        }
        else if(s.charAt(0) == 'F'){
            myXml = new FireXml((this.myUserInterface));
        }
        else if(s.charAt(0) == 'P'){
            myXml = new PercolationXml(this.myUserInterface);
        }
        else if(s.charAt(0) == 'S'){
            myXml = new SegregationXml(this.myUserInterface);
        }
        else if(s.charAt(0) == 'G'){
            myXml = new GameOfLifeXml(this.myUserInterface);
        }

    }

    private void startSimulation() {
        if (!checkFileSelected()) return;
        this.myAnimation.play();
    }

    private void updateSpeed(Double sliderInput) {
        updateFreq = Math.round(normalUpdateFreq / sliderInput.floatValue());
    }

    private void stepProcess() {
        if (!checkFileSelected()) return;
        this.isStep = true;
        this.myAnimation.pause();
        this.myUserInterface.update();
    }

    private void resetGrid() {
        this.myUserInterface.getMyGridView().resetCellGrid();
        this.setState = false;
        myAnimation.pause();
    }

    private boolean checkFileSelected() {
        if (myUserInterface.getMyGridView().getMyCellGrid() == null) {
            this.myUserInterface.displayErrorMsg(resourceBundle.getString("ErrorMsg_selectFile"));
        }
        return myUserInterface.getMyGridView().getMyCellGrid() != null;
    }


    public static void main(String[] args) {
        launch(args);
    }

}
