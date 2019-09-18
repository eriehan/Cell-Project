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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import userInterface.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.File;
import java.io.IOException;

import static userInterface.VisualizationConstants.*;

public class MainController extends Application {
    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

    private UserInterface myUserInterface;
    private Scene myScene;
    private Stage myStage;
    private Animation myAnimation;
    private File myConfigFile;
    private int updateTimer;
    private int updateFreq = 100;
    private boolean isStep = false;


    @Override
    public void start(Stage stage) throws Exception {
        updateTimer = 0;
        var animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        myAnimation = animation;
        myUserInterface = new UserInterface(100, 100, "Some Simulation");
        myUserInterface.getMyGridView().createGrid();
        initButtons();
        myScene = initScene();
        stage.setScene(myScene);
        stage.setTitle("Cell Society");
        stage.show();
        myStage = stage;
        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        animation.getKeyFrames().add(frame);
//        animation.play();
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
        // parseXML();
        var scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT, BACKGROUND_COLOR);
        return scene;
    }

    private void parseXML() throws ParserConfigurationException, IOException, SAXException {
//        //TODO: parseXML code
//        File xmlFile = new File("simulation1.xml");
//        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
//        DocumentBuilder docBuilder = documentBuilderFactory.newDocumentBuilder();
//        Document doc = docBuilder.parse(xmlFile);
//        NodeList typeOfSimulation = doc.getElementsByTagName("Game");
//        for(int i = 0; i < typeOfSimulation.getLength(); i++){
//            if(typeOfSimulation.item(i).getAttributes().equals("Game Of Life")){
//                System.out.println("game of Life");
//            }
//        }

    }

    private void initButtons() {
        SelectFileButton selectFileButton = new SelectFileButton();
        selectFileButton.setOnAction(value -> selectFilePrompt());
        this.myUserInterface.getMyButtons().getButtonList().add(selectFileButton);
        this.myUserInterface.getMyButtons().getButtonList().add(new StartButton(myAnimation));
        this.myUserInterface.getMyButtons().getButtonList().add(new PauseButton(myAnimation));
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

    private void selectFilePrompt(){
        FileChooser fileChooser = new FileChooser();
        myConfigFile = fileChooser.showOpenDialog(myStage);
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

    public static void main(String[] args) {
        launch(args);
    }

}
