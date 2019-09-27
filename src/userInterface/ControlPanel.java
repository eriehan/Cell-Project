package userInterface;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControlPanel {
    private static final double PADDING_TOP = 100;
    private static final double PADDING_OTHER = 0;
    private static final String RESOURCE_FILE_PATH = "resources/ControlResources";
    private static final int SPACING = 15;
    private ResourceBundle resourceBundle;
    private ArrayList<SimulationSlider> sliderList;
    private VBox myColPane;

    public ControlPanel() {
        sliderList = new ArrayList<>();
        myColPane = new VBox();
        myColPane.setSpacing(SPACING);
        myColPane.setPadding(new Insets(PADDING_TOP,PADDING_OTHER,PADDING_OTHER,PADDING_OTHER));
        this.resourceBundle = ResourceBundle.getBundle(RESOURCE_FILE_PATH);
    }


    public VBox getMyColPane() {
        return myColPane;
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

}
