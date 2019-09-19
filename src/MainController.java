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

import static userInterface.VisualizationConstants.*;

public class MainController extends Application {
    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

    private UserInterface myUserInterface;
    private Scene myScene;
    private Stage myStage;
    private Animation myAnimation;
    private String myTitle = "change me"; //TODO: put into new popup for simulation
    private File myConfigFile;
    private int updateTimer;
    private int updateFreq = 30;
    private boolean isStep = false;
    private String userInputSimulation = "Game Of Life"; //TODO: make dynamic
    private String userFile;
    private int cellGridRowNum;
    private int cellGridColNum;

    //TODO: subject to change
    private ArrayList<ArrayList<Integer>> myColArray = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> myRowArray = new ArrayList<>();


    @Override
    public void start(Stage stage) throws Exception {
        initVisualizations(stage);
    }

    private void initVisualizations(Stage stage) throws Exception{
        updateTimer = 0;
        var animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        myAnimation = animation;
        myUserInterface = new UserInterface(6, 6, myTitle);
        myUserInterface.getMyGridView().generateBlankGrid();
        initButtons();
        myScene = initScene();
        stage.setScene(myScene);
        stage.setTitle(myTitle.toString());
        stage.show();
        myStage = stage;
        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        animation.getKeyFrames().add(frame);
    }

    private void step(double elapsedTime) {
        if (updateTimer>updateFreq || isStep){
            updateTimer = 0;

            myUserInterface.update();
        }
        else{
            updateTimer++;
        }
        // listen to UI
        // update grid
        // update view
    }

    private Scene initScene() throws IOException, SAXException, ParserConfigurationException {
        Group root = myUserInterface.setScene();
        var scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT, BACKGROUND_COLOR);
        return scene;
    }

    private void parseXML(String file) throws IOException, ParserConfigurationException, SAXException {
        //TODO: parseXML code
        int numAgents = 0;
        File xmlFile = new File(String.valueOf(file));
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = documentBuilderFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(xmlFile);


         cellGridColNum = Integer.parseInt(doc.getElementsByTagName("Col").item(0).getTextContent());
         cellGridRowNum = Integer.parseInt(doc.getElementsByTagName("Row").item(0).getTextContent());


        //returns nodeList of elements named "Type"
        NodeList typeOfSimulation = doc.getElementsByTagName("Type");


        //goes through NodeList to get information about the different simulation
        for(int i = 0; i < typeOfSimulation.getLength(); i++) {

            //gets the i-th simulation type and casts it as a node
            Node currentSimulationType = typeOfSimulation.item(i);
            Element currentSimulationElement = (Element) currentSimulationType;
            if (currentSimulationElement.getAttribute("name").equals(userInputSimulation)) {
                this.myTitle = currentSimulationElement.getAttribute("name");
                numAgents = Integer.parseInt(currentSimulationElement.getTextContent());
            }
        }
        //return row and col arrays
        for(int i = 0; i < numAgents; i++){
            NodeList agent = doc.getElementsByTagName("Agent" + i);
                Element n = (Element)agent.item(0);
                String row = n.getElementsByTagName("Row").item(0).getTextContent();
                String col = n.getElementsByTagName("Column").item(0).getTextContent();
                myRowArray.add(stringToIntArray(row));
                myColArray.add(stringToIntArray(col));
        }

        myUserInterface.getMyGridView().initializeMyCellGrid(myRowArray, myColArray, userInputSimulation);

    }

    private ArrayList<Integer> stringToIntArray(String s){
        StringBuilder myStringBuilder = new StringBuilder(s);
        int startIndex = 0;
        int endIndex = 1;

        ArrayList<Integer> myInts = new ArrayList<>();
        while(endIndex < myStringBuilder.length()){
            if(myStringBuilder.charAt(endIndex) == ' '){
                myInts.add(Integer.parseInt(myStringBuilder.substring(startIndex, endIndex)));
                startIndex = endIndex + 1;
                endIndex = startIndex + 1;
            }
            else{
                endIndex++;
            }
        }
        return myInts;
    }

    private void initButtons() {
        SelectFileButton selectFileButton = new SelectFileButton();
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
        this.myUserInterface.getMyButtons().getButtonList().add(new StartButton(myAnimation));
        this.myUserInterface.getMyButtons().getButtonList().add(new PauseButton(myAnimation));
        ResetButton resetButton = new ResetButton(myAnimation);
        resetButton.setOnAction(value -> resetGrid());
        this.myUserInterface.getMyButtons().getButtonList().add(resetButton);
        StepButton stepButton = new StepButton();
        stepButton.setOnAction(value -> stepProcess());
        this.myUserInterface.getMyButtons().getButtonList().add(stepButton);
        SpeedUpButton speedUpButton = new SpeedUpButton();
        speedUpButton.setOnAction(value -> speedUp());
        this.myUserInterface.getMyButtons().getButtonList().add(speedUpButton);
        SlowDownButton slowDownButton = new SlowDownButton();
        slowDownButton.setOnAction(value -> slowDown());
        this.myUserInterface.getMyButtons().getButtonList().add(slowDownButton);
    }

    private void selectFilePrompt() throws IOException, ParserConfigurationException, SAXException {
        FileChooser fileChooser = new FileChooser();
        myConfigFile = fileChooser.showOpenDialog(myStage);

        StringBuilder myFile = new StringBuilder(myConfigFile.toString());
        for(int i = 0; i < myFile.length(); i++){
            if(myFile.substring(i, i + 3).equals("xml")){
                this.userFile = myFile.substring(i);
                System.out.println(myFile.substring(i));
                break;
            }

        }

        parseXML(this.userFile);

        System.out.println("for debug"+ myConfigFile.getAbsolutePath());
    }

    private void slowDown() {
        this.updateFreq *= 2;
    }

    private void speedUp() {
        this.updateFreq /= 2;
    }

    private void stepProcess(){
        this.isStep = true;
        this.myAnimation.pause();
        this.step(SECOND_DELAY);
    }

    private void resetGrid(){
        this.myUserInterface.getMyGridView().getMyGridPane().getChildren().clear();
        this.myUserInterface.getMyGridView().generateBlankGrid();
        myAnimation.pause();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
