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
        this.getControlPanel().getMyColPanel().getChildren().clear();
        String[] controlsList = getControlPanel().getResourceBundle().getString("FireControls").split(",");
        for (String controlType : controlsList) {
            int minVal = Integer.parseInt(getControlPanel().getResourceBundle().getString(controlType + "." + "min"));
            int maxVal = Integer.parseInt(getControlPanel().getResourceBundle().getString(controlType + "." + "max"));
            int defaultVal = Integer.parseInt(getControlPanel().getResourceBundle().getString(controlType + "." + "default"));
            String title = getControlPanel().getResourceBundle().getString(controlType + "." + "title");
            SimulationSlider probCatchSlider = new SimulationSlider(minVal, maxVal, defaultVal, title);
            probCatchSlider.getMySlider().valueProperty().addListener(e -> updateProbCatch((double) Math.round(probCatchSlider.getMySlider().getValue())));
            getControlPanel().getMyColPanel().getChildren().add(probCatchSlider.getvBox());

        }
    }

    private void updateProbCatch(double newProbCatch) {
        //TODO
        this.probCatch = newProbCatch;
    }

    @Override
    public void initializeGrids(Map<Point, CellState> configMap) {
        createMapFullOfTrees();
        for (Map.Entry<Point, CellState> entry : configMap.entrySet()) {
            getGridOfCells().get(entry.getKey()).setState(entry.getValue());
        }
    }

    @Override
    public void assignNeighborsToEachCell() {
        getGridLimit().assignEdgeNeighbors(getGridOfCells(), getCellShapeType(), getNumOfRows(), getNumOfCols());
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