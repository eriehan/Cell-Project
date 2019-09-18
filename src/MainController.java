import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import userInterface.UserInterface;
import userInterface.VisualizationConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.File;
import java.io.IOException;

import static userInterface.VisualizationConstants.*;

public class MainController extends Application {
    private UserInterface myUserInterface;
    private Scene myScene;
    private Stage myStage;
    private Animation myAnimation;
    private String myTitle = "Change Me";
    private int updateTimer; // TODO: backend can use this to control speed of update


    @Override
    public void start(Stage stage) throws Exception {
        updateTimer =0;

        myUserInterface = new UserInterface(100, 100, myTitle);
        myUserInterface.getMyGridView().createGrid();
        myScene = initScene();
        stage.setScene(myScene);
        stage.setTitle("Cell Society");
        stage.show();
        myStage = stage;
        var frame = new KeyFrame(Duration.millis(GameConstants.MILLISECOND_DELAY), e -> step(GameConstants.SECOND_DELAY));
        var animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        myAnimation = animation;
        animation.play();
    }

    private void step(double elapsedTime) {
        if (updateTimer<=100){
            updateTimer++;
        }
        else{
            myUserInterface.update();
            updateTimer=0;
        }
        // listen to UI
        // update grid
        // update view
    }

    private Scene initScene() throws IOException, SAXException, ParserConfigurationException {
        Group root = myUserInterface.setScene();
        parseXML();
        var scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT, BACKGROUND_COLOR);
        return scene;
    }

    private void parseXML() throws ParserConfigurationException, IOException, SAXException {
        //TODO: parseXML code
        File xmlFile = new File("xml_files/simulation1.xml");
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = documentBuilderFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(xmlFile);

        NodeList typeOfSimulation = doc.getElementsByTagName("Game");

        for(int i = 0; i < typeOfSimulation.getLength(); i++){
            Node currentGameType = typeOfSimulation.item(i);

            if(currentGameType.getAttributes().item(i).getNodeValue().equals("Game Of Life")) {
                if(currentGameType.getNodeType() == Node.ELEMENT_NODE){
                    Element currElement = (Element)currentGameType;
                    GRID_WIDTH = Integer.parseInt(currElement.getElementsByTagName("Width").item(0).getTextContent());
                    GRID_HEIGHT = Integer.parseInt(currElement.getElementsByTagName("Height").item(0).getTextContent());
                    NodeList listOfCoord = currElement.getElementsByTagName("Cells");

                    //returns the x and y
                    System.out.println(listOfCoord.item(0));




                }



            }
        }

    }


    public static void main(String[] args) {
        launch(args);
    }

}
