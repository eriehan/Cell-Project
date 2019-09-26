package userInterface;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControlPanel {
    private static final String RESOURCE_FILE_PATH = "resources/ControlResources";
    private ResourceBundle resourceBundle;
    private ArrayList<SimulationSlider> sliderList;
    private VBox myColPanel;

    public ControlPanel() {
        sliderList = new ArrayList<>();
        myColPanel = new VBox();
        myColPanel.setSpacing(15);
        myColPanel.setPadding(new Insets(100, 0, 0, 0));
        this.resourceBundle = ResourceBundle.getBundle(RESOURCE_FILE_PATH);
    }


    public VBox getMyColPanel() {
        return myColPanel;
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

}
