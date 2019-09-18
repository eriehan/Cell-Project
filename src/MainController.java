import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import userInterface.UserInterface;

import static userInterface.VisualizationConstants.*;

public class MainController extends Application {
    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

    private UserInterface myUserInterface;
    private Scene myScene;
    private Stage myStage;
    private Animation myAnimation;
    private int updateTimer;
    private Integer updateFreq = 100;



    @Override
    public void start(Stage stage) throws Exception {
        updateTimer =0;
        var animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        myAnimation = animation;
        myUserInterface = new UserInterface(100, 100, "Some Simulation", myAnimation);
        myUserInterface.getMyGridView().createGrid();
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
        if (updateTimer<=updateFreq){
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

    public void setUpdateFreq(int newFreq){
        this.updateFreq = newFreq;
    }

    public static void main(String[] args) {
        launch(args);
    }


}
