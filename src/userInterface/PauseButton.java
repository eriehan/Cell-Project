package userInterface;

import javafx.animation.Animation;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

import static userInterface.VisualizationConstants.BUTTON_FONT_SIZE;

public class PauseButton extends SimulationButton {
    private boolean paused = false;
    private Animation myAnimation;
    private String pauseName;
    private String resumeName;

    public PauseButton(Animation animation, String pause, String resume) {
        super(pause);
        this.pauseName = pause;
        this.resumeName = resume;
        this.myAnimation = animation;
        this.setOnAction(value -> buttonAction());
    }

    private void buttonAction() {
        paused = !paused;
        if (paused) {
            myAnimation.pause();
            this.setText(resumeName);
        } else {
            myAnimation.play();
            this.setText(pauseName);
        }
    }
}
