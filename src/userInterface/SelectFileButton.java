package userInterface;

import javafx.scene.control.Button;
import javafx.scene.text.Font;

import static userInterface.VisualizationConstants.BUTTON_FONT_SIZE;

public class SelectFileButton extends Button {
    public SelectFileButton() {
        super();
        this.setFont(new Font(BUTTON_FONT_SIZE));
        this.setText("SELECT FILE");
    }
}
