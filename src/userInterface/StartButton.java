package userInterface;

import javafx.animation.Animation;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

import static userInterface.VisualizationConstants.BUTTON_FONT_SIZE;

public class StartButton extends Button {
    private boolean started = false;
    private Animation myAnimation;


    public StartButton(Animation animation){
        super();
        this.myAnimation = animation;
        this.setFont(new Font(BUTTON_FONT_SIZE));
        this.setText("START");
        this.setOnAction(value -> buttonAction());
    }

    private void buttonAction(){
        if (!started) myAnimation.play();
    }
}
