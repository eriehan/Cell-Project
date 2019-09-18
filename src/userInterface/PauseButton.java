package userInterface;

import javafx.animation.Animation;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

import static userInterface.VisualizationConstants.BUTTON_FONT_SIZE;

public class PauseButton extends Button {
    private boolean paused = false;
    private Animation myAnimation;

    public PauseButton(Animation animation){
        super();
        this.myAnimation = animation;
        this.setFont(new Font(BUTTON_FONT_SIZE));
        this.setText("PAUSE/RESUME");
        this.setOnAction(value -> buttonAction());
    }

    private void buttonAction(){
        paused = !paused;
        if (paused) myAnimation.pause();
        else myAnimation.play();
    }

    public boolean getPaused(){
        return paused;
    }
}
