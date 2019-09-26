package simulation;

import userInterface.SimulationSlider;
import utils.Point;

import java.util.Map;

public class FireCellGrid extends GameOfLifeCellGrid {
    private double probCatch;

    public FireCellGrid(int numRows, int numCols, double probCatch) {
        super(numRows, numCols);
        this.probCatch = probCatch;
    }

    @Override
    public void initializeControlPannel() {
        String[] sliderArrays = getControlPanel().getResourceBundle().getString("FireControls").split(";");
        for (String sliderArray : sliderArrays){
            String[] sliderValues = sliderArray.split(",");
            SimulationSlider probCatchSlider = new SimulationSlider(Integer.parseInt(sliderValues[0]),Integer.parseInt(sliderValues[1]),Integer.parseInt(sliderValues[2]),sliderValues[3]);
            probCatchSlider.getMySlider().valueProperty().addListener(e -> updateProbCatch((double) Math.round(probCatchSlider.getMySlider().getValue())));
            getControlPanel().getMyColPanel().getChildren().add(probCatchSlider.getvBox());
        }
    }

    private void updateProbCatch(double newProbCatch){
        //TODO
    }

    @Override
    public void initializeGrids(Map<Point, CellState> configMap) {
        createMapFullOfTrees();
        for (Map.Entry<Point, CellState> entry : configMap.entrySet()) {
            getGridOfCells().get(entry.getKey()).setState(entry.getValue());
        }
    }

    @Override
    public void addEmptyStateToCell(int row, int col) {
        Point point = new Point(row, col);
        addToGridOfCells(point, new FireCell(row, col, CellState.TREE, probCatch));
    }

    private void createMapFullOfTrees() {
        getGridOfCells().clear();
        for (int row = 0; row < getNumOfRows(); row++) {
            for (int col = 0; col < getNumOfCols(); col++) {
                addEmptyStateToCell(row, col);
            }
        }
    }
}