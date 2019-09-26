package userInterface;

import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import static userInterface.VisualizationConstants.BUTTON_FONT_SIZE;

public class SimulationChoice {
    private ChoiceBox choiceBox;
    private HBox myHBox;

    public SimulationChoice(String[] choices, String label) {
        choiceBox = new ChoiceBox(FXCollections.observableArrayList(choices));
        Label myLabel = new Label();
        myLabel.setText(label);
        myLabel.setFont(new Font(BUTTON_FONT_SIZE));
        myHBox = new HBox();
        myHBox.setSpacing(10);
        this.myHBox.getChildren().add(myLabel);
        this.myHBox.getChildren().add(choiceBox);
    }

    public HBox getMyHBox() {
        return myHBox;
    }

    public ChoiceBox getChoiceBox() {
        return choiceBox;
    }
}
