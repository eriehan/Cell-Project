package userInterface;

import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class SlidersAndControls {
    private ArrayList<SimulationSlider> sliderList;
    private VBox myCol;

    public SlidersAndControls() {
        sliderList = new ArrayList<>();
        myCol = new VBox();
        myCol.setSpacing(15);
    }


    public VBox getMyCol() {
        return myCol;
    }

    public void addSlider(SimulationSlider slider) {
        myCol.getChildren().add(slider.getMyHBox());
        sliderList.add(slider);
    }

    public void addChoiceBox(SimulationChoice choice) {
        myCol.getChildren().add(choice.getMyHBox());
    }
}
