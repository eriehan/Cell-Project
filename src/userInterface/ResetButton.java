package userInterface;

import javafx.animation.Animation;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

import static userInterface.VisualizationConstants.BUTTON_FONT_SIZE;

public class ResetButton extends Button {
    private Animation myAnimation;


    public ResetButton(Animation animation){
        super();
        this.myAnimation = animation;
        this.setFont(new Font(BUTTON_FONT_SIZE));
        this.setText("RESET");
    }
}
