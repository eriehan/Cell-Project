package userInterface;

import javafx.animation.Animation;

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

    public void resetPauseButton() {
        paused = false;
        this.setText(pauseName);
    }
}
