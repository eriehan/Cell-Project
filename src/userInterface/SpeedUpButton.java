package userInterface;

import javafx.scene.control.Button;
import javafx.scene.text.Font;

import static userInterface.VisualizationConstants.BUTTON_FONT_SIZE;

public class SpeedUpButton extends Button {
    public SpeedUpButton(){
        super();
        this.setFont(new Font(BUTTON_FONT_SIZE));
        this.setText("SPEED UP");
        this.setOnAction(value -> buttonAction());
    }

    public void buttonAction(){

    }
}
