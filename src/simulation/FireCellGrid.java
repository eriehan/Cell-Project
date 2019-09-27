package simulation;

import userInterface.SimulationSlider;
import utils.Point;

import java.util.HashMap;
import java.util.Map;

public class FireCellGrid extends GameOfLifeCellGrid {
    private double probCatch;
    private Map myConfigMap = new HashMap<Point, CellState>();

    public FireCellGrid(int numRows, int numCols, double probCatch) {
        super(numRows, numCols);

        this.probCatch = probCatch;

    }


    @Override
    public void initializeControlPanel() {
        this.getControlPanel().getMyColPane().getChildren().clear();
        String[] controlsList = getControlPanel().getResourceBundle().getString("FireControls").split(",");
        for (String controlType : controlsList) {
            SimulationSlider segregationSlider = createSliderFromResourceFile(controlType);
            segregationSlider.getMySlider().valueProperty().addListener(e -> sliderAction(controlType, (double) Math.round(segregationSlider.getMySlider().getValue())));
        }
    }

    private void sliderAction(String type, double inputPercentage) {
        //TODO: added slider actions @Eric
        // type: "PropCatch"
        this.probCatch = inputPercentage;
        System.out.println("probCatch in SliderAction: " + probCatch);
        createMapFullOfTrees();
        initializeGrids(myConfigMap);
        assignNeighborsToEachCell();

    }

    @Override
    public void initializeGrids(Map<Point, CellState> configMap) {
        createMapFullOfTrees();
        for (Map.Entry<Point, CellState> entry : configMap.entrySet()) {
            getGridOfCells().get(entry.getKey()).setState(entry.getValue());
            myConfigMap.put(entry.getKey(), entry.getValue());
        }

    }

    @Override
    public void assignNeighborsToEachCell() {
        getGridLimit().assignEdgeNeighbors(getGridOfCells(), getCellShapeType(), getNumOfRows(), getNumOfCols());
    }

    @Override
    public void addEmptyStateToCell(int row, int col) {
        Point point = new Point(row, col);
        System.out.println("probCatch in addEmptyStateToCel;: " + probCatch);
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