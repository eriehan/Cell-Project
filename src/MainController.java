import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import userInterface.UserInterface;
import userInterface.VisualizationConstants;

import static userInterface.VisualizationConstants.*;

public class MainController extends Application {
    private UserInterface myUserInterface;
    private Scene myScene;
    private Stage myStage;
    private Animation myAnimation;
    private int updateTimer; // TODO: backend can use this to control speed of update


    @Override
    public void start(Stage stage) throws Exception {
        updateTimer =0;

        myUserInterface = new UserInterface(100, 100, "Some Simulation");
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

    private Scene initScene() {
        Group root = myUserInterface.setScene();
        var scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT, BACKGROUND_COLOR);
        return scene;
    }

    private void parseXML() {
        //TODO: parseXML code


    }

    public static void main(String[] args) {
        launch(args);
    }


}
