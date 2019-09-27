package userInterface;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import static userInterface.VisualizationConstants.SLIDER_FONT_SIZE;

public class SimulationTextInput {
    private static final int SPACING = 5;
    private TextField textField;
    private VBox myVBox;

    public SimulationTextInput(String label) {
        textField = new TextField();
        Label myLabel = new Label();
        myLabel.setText(label);
        myLabel.setFont(new Font(SLIDER_FONT_SIZE));
        myVBox = new VBox(SPACING);
        myVBox.getChildren().add(myLabel);
        myVBox.getChildren().add(textField);
    }

    public VBox getMyVBox() {
        return myVBox;
    }

    public TextField getTextField() {
        return textField;
    }
}
