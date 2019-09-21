package userInterface;

import javafx.animation.Animation;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

import static userInterface.VisualizationConstants.BUTTON_FONT_SIZE;

public class PauseButton extends SimulationButton {
    private boolean paused = false;
    private Animation myAnimation;

    public PauseButton(Animation animation, String name){
        super(name);
        this.myAnimation = animation;
        this.setOnAction(value -> buttonAction());
    }

    private void buttonAction(){
        paused = !paused;
        if (paused) myAnimation.pause();
        else myAnimation.play();
    }
}
