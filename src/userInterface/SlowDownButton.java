package userInterface;

import javafx.scene.control.Button;
import javafx.scene.text.Font;

import static userInterface.VisualizationConstants.BUTTON_FONT_SIZE;

public class SlowDownButton extends Button {
    public SlowDownButton(){
        super();
        this.setFont(new Font(BUTTON_FONT_SIZE));
        this.setText("SLOW DOWN");
    }
}
