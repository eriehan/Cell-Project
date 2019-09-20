package userInterface;

import javafx.animation.Animation;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

import static userInterface.VisualizationConstants.BUTTON_FONT_SIZE;

public class StartButton extends Button {

    public StartButton() {
        super();
        this.setFont(new Font(BUTTON_FONT_SIZE));
        this.setText("START");

    }

}
