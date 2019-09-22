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
    private boolean isStep = false;
    private String userFile;
    private int cellGridRowNum;
    private int cellGridColNum;
    private ArrayList<Integer> EnergyArray = new ArrayList<>();
    private ArrayList<Integer> MaturityArray = new ArrayList<>();
//    private ArrayList<ArrayList<Integer>> myColArray = new ArrayList<>();
//    private ArrayList<ArrayList<Integer>> myRowArray = new ArrayList<>();
    private double rate;
    private ResourceBundle resourceBundle;
    private AbstractGridView initialGridView;


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
        updateFreq = Integer.parseInt(resourceBundle.getString("InitialUpdateFreq"));
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
        //System.out.println("ENTERED");
        int numAgents = 0;
        File xmlFile = new File(String.valueOf(file));
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = documentBuilderFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(xmlFile);


        cellGridColNum = Integer.parseInt(doc.getElementsByTagName("Col").item(0).getTextContent());
        myUserInterface.setNumOfCols(cellGridColNum);
        cellGridRowNum = Integer.parseInt(doc.getElementsByTagName("Row").item(0).getTextContent());
        myUserInterface.setNumOfRows(cellGridRowNum);

        this.rate = Integer.parseInt(doc.getElementsByTagName("Rate").item(0).getTextContent());

        // System.out.println(rate);
        //returns nodeList of elements named "Type"
        NodeList typeOfSimulation = doc.getElementsByTagName("Type");


        //goes through NodeList to get information about the different simulation
        for (int i = 0; i < typeOfSimulation.getLength(); i++) {

            //gets the i-th simulation type and casts it as a node
            Node currentSimulationType = typeOfSimulation.item(i);
            Element currentSimulationElement = (Element) currentSimulationType;
            this.myTitle = currentSimulationElement.getAttribute("name");
            System.out.println(this.myTitle);
            myUserInterface.changeTitle(this.myTitle);
            numAgents = Integer.parseInt(currentSimulationElement.getTextContent());
        }
        // System.out.println(numAgents);
        //return row and col arrays
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

        // TODO: can be initialized to TRIANGLE or RECTANGLE
        myUserInterface.setCellShape(CellShapeType.RECTANGLE);
        //myUserInterface.setCellShape(CellShapeType.TRIANGLE);
        System.out.println(myRowArray);
        System.out.println(myTitle);
        System.out.println(myRowArray);
        myUserInterface.getMyGridView().initializeMyCellGrid(myRowArray, myColArray, myTitle,
                cellGridRowNum, cellGridColNum, EnergyArray, MaturityArray, rate);

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

    private void initButtons() throws IOException, SAXException, ParserConfigurationException{
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

        this.myUserInterface.getMyButtons().getButtonList().add(new PauseButton(myAnimation, resourceBundle.getString("Pause")));

        SimulationButton resetButton = new SimulationButton(resourceBundle.getString("Reset"));
        resetButton.setOnAction(value -> resetGrid());
        this.myUserInterface.getMyButtons().getButtonList().add(resetButton);

        SimulationButton stepButton = new SimulationButton(resourceBundle.getString("Step"));
        stepButton.setOnAction(value -> stepProcess());
        this.myUserInterface.getMyButtons().getButtonList().add(stepButton);

        SimulationButton speedUpButton = new SimulationButton(resourceBundle.getString("SpeedUp"));
        speedUpButton.setOnAction(value -> speedUp());
        this.myUserInterface.getMyButtons().getButtonList().add(speedUpButton);

        SimulationButton slowDownButton = new SimulationButton(resourceBundle.getString("SlowDown"));
        slowDownButton.setOnAction(value -> slowDown());
        this.myUserInterface.getMyButtons().getButtonList().add(slowDownButton);
    }

    private void selectFilePrompt() throws IOException, ParserConfigurationException, SAXException {
        FileChooser fileChooser = new FileChooser();
        myConfigFile = fileChooser.showOpenDialog(myStage);
        if (myConfigFile==null){
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
        if (myUserInterface.getMyGridView().getMyCellGrid() == null) {
            this.myUserInterface.displayErrorMsg(resourceBundle.getString("ErrorMsg_selectFile"));
            return;
        }
        this.myAnimation.play();
    }

    private void slowDown() {
        if (myUserInterface.getMyGridView().getMyCellGrid() == null) {
            this.myUserInterface.displayErrorMsg(resourceBundle.getString("ErrorMsg_selectFile"));
            return;
        }
        this.updateFreq *= 1.5;
    }

    private void speedUp() {
        if (myUserInterface.getMyGridView().getMyCellGrid() == null) {
            this.myUserInterface.displayErrorMsg(resourceBundle.getString("ErrorMsg_selectFile"));
            return;
        }
        this.updateFreq /= 1.5;
    }

    private void stepProcess() {
        if (myUserInterface.getMyGridView().getMyCellGrid() == null) {
            this.myUserInterface.displayErrorMsg(resourceBundle.getString("ErrorMsg_selectFile"));
            return;
        }
        this.isStep = true;
        this.myAnimation.pause();
        this.step(secondDelay);
    }

    private void resetGrid() {
        this.myUserInterface.getMyGridView().getMyGridPane().getChildren().clear();
        this.myUserInterface.getMyGridView().generateBlankGrid();
        this.myUserInterface.getMyGridView().resetCellGrid();
        myAnimation.pause();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
