package userInterface;

import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class Sliders {
    private ArrayList<SimulationSlider> sliderList;
    private VBox myCol;
    public Sliders(){
        sliderList = new ArrayList<>();
        myCol = new VBox();
    }


    public VBox getMyCol() {
        return myCol;
    }

    public void addSlider(SimulationSlider slider){
        myCol.getChildren().add(slider.getMyHBox());
        sliderList.add(slider);
    }
}
