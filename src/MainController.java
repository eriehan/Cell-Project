import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import userInterface.*;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static userInterface.VisualizationConstants.*;

public class MainController extends Application {
    private static final String RESOURCE_FILE_PATH = "resources/MainResources";

    private int framesPerSecond;
    private int millisecondDelay;
    private double secondDelay;


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
    private int cellGridRowNum;
    private int cellGridColNum;
    private ArrayList<Integer> EnergyArray = new ArrayList<>();
    private ArrayList<Integer> MaturityArray = new ArrayList<>();
    private double rate;
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
        millisecondDelay = 1000 / framesPerSecond;
        secondDelay = 1.0 / framesPerSecond;
        normalUpdateFreq = Integer.parseInt(resourceBundle.getString("InitialUpdateFreq"));
        updateFreq = normalUpdateFreq;
        myTitle = resourceBundle.getString("InitialTitle");
    }

    private void initVisualizations(Stage stage) throws Exception {
        updateTimer = 0;
        var animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        myAnimation = animation;
        myUserInterface = new UserInterface(10, 10, myTitle);
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
        if (updateTimer > updateFreq || isStep) {
            updateTimer = 0;
            myUserInterface.update();
        } else {
            updateTimer++;
        }
    }

    private Scene initScene() throws IOException, SAXException, ParserConfigurationException {
        Group root = myUserInterface.setScene();
        var scene = new Scene(root, Integer.parseInt(resourceBundle.getString("WindowWidth")), Integer.parseInt(resourceBundle.getString("WindowHeight")), BACKGROUND_COLOR);
        return scene;
    }


    private void parseXML(String file) throws IOException, ParserConfigurationException, SAXException {
        ArrayList<ArrayList<Integer>> myColArray = new ArrayList<>();
        ArrayList<ArrayList<Integer>> myRowArray = new ArrayList<>();
        int numAgents = 0;
        File xmlFile = new File(String.valueOf(file));
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = documentBuilderFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(xmlFile);
        determineCellShape(doc);
        setUpSimulationParameters(myColArray, myRowArray, numAgents, doc);
        myUserInterface.getMyGridView().initializeMyCellGrid(myRowArray, myColArray, myTitle,
                cellGridRowNum, cellGridColNum, EnergyArray, MaturityArray, rate);

    }

    private void determineCellShape(Document doc) {
        Node shapeNode = doc.getElementsByTagName("Shape").item(0);
        Element shapeElement = (Element) shapeNode;
        String shape = shapeElement.getAttribute("name");
        switch (shape) {
            case "triangle":
                myUserInterface.setCellShape(CellShapeType.TRIANGLE);
                break;

            case "rectangle":
                myUserInterface.setCellShape(CellShapeType.RECTANGLE);
                break;
            case "hexagon":
                myUserInterface.setCellShape(CellShapeType.HEXAGON);
                break;
        }
    }

    private void setUpSimulationParameters(ArrayList<ArrayList<Integer>> myColArray, ArrayList<ArrayList<Integer>> myRowArray, int numAgents, Document doc) {
        cellGridColNum = Integer.parseInt(doc.getElementsByTagName("Col").item(0).getTextContent());
        myUserInterface.setNumOfCols(cellGridColNum);
        cellGridRowNum = Integer.parseInt(doc.getElementsByTagName("Row").item(0).getTextContent());
        myUserInterface.setNumOfRows(cellGridRowNum);
        this.rate = Integer.parseInt(doc.getElementsByTagName("Rate").item(0).getTextContent());
        NodeList typeOfSimulation = doc.getElementsByTagName("Type");
        //goes through NodeList to get information about the different simulation
        for (int i = 0; i < typeOfSimulation.getLength(); i++) {
            //gets the i-th simulation type and casts it as a node
            Node currentSimulationType = typeOfSimulation.item(i);
            Element currentSimulationElement = (Element) currentSimulationType;
            this.myTitle = currentSimulationElement.getAttribute("name");
            myUserInterface.changeTitle(this.myTitle);
            numAgents = Integer.parseInt(currentSimulationElement.getTextContent());
        }
        for (int i = 0; i < numAgents; i++) {
            NodeList agent = doc.getElementsByTagName("Agent" + i);
            Element n = (Element) agent.item(0);
            if (myTitle.equals("Wa-Tor")) {
                EnergyArray.add(Integer.parseInt(n.getElementsByTagName("Energy").item(0).getTextContent()));
                MaturityArray.add(Integer.parseInt(n.getElementsByTagName("Mature").item(0).getTextContent()));
            }
            String row = n.getElementsByTagName("Row").item(0).getTextContent();
            String col = n.getElementsByTagName("Column").item(0).getTextContent();
            myRowArray.add(stringToIntArray(row));
            myColArray.add(stringToIntArray(col));
        }
    }

    private ArrayList<Integer> stringToIntArray(String s) {
        StringBuilder myStringBuilder = new StringBuilder(s);
        int startIndex = 0;
        int endIndex = 1;

        ArrayList<Integer> myInts = new ArrayList<>();
        while (endIndex < myStringBuilder.length()) {
            if (myStringBuilder.charAt(endIndex) == ' ') {
                myInts.add(Integer.parseInt(myStringBuilder.substring(startIndex, endIndex)));
                startIndex = endIndex + 1;
                endIndex = startIndex + 1;
            } else {
                endIndex++;
            }
        }
        return myInts;
    }

    private void initButtons() throws IOException, SAXException, ParserConfigurationException {

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
        saveButton.setOnAction(value -> save());
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

    private void save() {
        if (!checkFileSelected()) return;
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
        parseXML(this.userFile);
        this.myAnimation.pause();
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
        this.step(secondDelay);
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
