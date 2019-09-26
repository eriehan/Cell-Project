package userInterface;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import static userInterface.VisualizationConstants.BUTTON_FONT_SIZE;
import static userInterface.VisualizationConstants.SLIDER_WIDTH;

public class SimulationSlider {
    private Slider mySlider;
    private HBox myHBox;

    public SimulationSlider(double min, double max, double defaultValue, String label) {
        mySlider = new Slider(min, max, defaultValue);
        mySlider.setShowTickLabels(true);
        mySlider.setShowTickMarks(true);
        mySlider.setMajorTickUnit(0.5);
        mySlider.setPrefWidth(SLIDER_WIDTH);
        Label myLabel = new Label();
        myLabel.setText(label);
        myLabel.setFont(new Font(BUTTON_FONT_SIZE));
        myHBox = new HBox();
        myHBox.setSpacing(15);
        this.myHBox.getChildren().add(myLabel);
        this.myHBox.getChildren().add(mySlider);
    }

    public HBox getMyHBox() {
        return myHBox;
    }

    public Slider getMySlider() {
        return mySlider;
    }
}