package userInterface;

import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ControlsManager {
    private static final int SPACING = 15;
    private static final Insets PADDING = new Insets(70,50,50,50);
//    private ArrayList<SimulationSlider> sliderList;
    private HBox myPane;
    private VBox myConstantCol;
    private VBox mySimulationCol;

    public ControlsManager() {
//        sliderList = new ArrayList<>();
        myPane = new HBox(SPACING);
        myPane.setPadding(PADDING);
        myConstantCol = new VBox(SPACING);
        mySimulationCol = new VBox(SPACING);
        myPane.getChildren().add(myConstantCol);
        myPane.getChildren().add(mySimulationCol);
    }


    public VBox getMyConstantCol() {
        return myConstantCol;
    }

    public VBox getMySimulationCol() {
        return mySimulationCol;
    }

    public HBox getMyPane() {
        return myPane;
    }

    public void addSlider(SimulationSlider slider) {
        myConstantCol.getChildren().add(slider.getvBox());
    }

    public void addChoiceBox(SimulationChoice choice) {
        myConstantCol.getChildren().add(choice.getMyHBox());
    }


    public void addButton(SimulationButton button){
        myConstantCol.getChildren().add(button);
    }
//
//    public void addSimulationSlider(SimulationSlider simulationSlider){
//        mySimulationCol.getChildren().add(simulationSlider.getvBox());
//    }
//
//    public void addControlPanel(ControlPanel controlPanel){
//        mySimulationCol.getChildren().add(controlPanel.getMyColPane());
//    }
}
