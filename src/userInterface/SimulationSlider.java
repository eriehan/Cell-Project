package userInterface;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import static userInterface.VisualizationConstants.SLIDER_FONT_SIZE;
import static userInterface.VisualizationConstants.SLIDER_WIDTH;

public class SimulationSlider {
    private static final int NUM_OF_TICKS = 5;
    private static final int SPACING = 5;
    private Slider mySlider;
    private VBox vBox;

    public SimulationSlider(double min, double max, double defaultValue, String label) {
        mySlider = new Slider(min, max, defaultValue);
        mySlider.setShowTickLabels(true);
        mySlider.setShowTickMarks(true);
        mySlider.setMajorTickUnit((max - min) / NUM_OF_TICKS);
        mySlider.setPrefWidth(SLIDER_WIDTH);
        Label myLabel = new Label();
        myLabel.setText(label);
        myLabel.setFont(new Font(SLIDER_FONT_SIZE));
        vBox = new VBox();
        vBox.setSpacing(SPACING);
        this.vBox.getChildren().add(myLabel);
        this.vBox.getChildren().add(mySlider);
    }

    public VBox getvBox() {
        return vBox;
    }

    public Slider getMySlider() {
        return mySlider;
    }
}
