package simulation;

import userInterface.SimulationSlider;
import utils.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PercolationCellGrid extends GameOfLifeCellGrid {

    private List<Cell> openCells = new ArrayList<>();
    private int initPercolated = 0;
    private int initBlocked = 0;

    public PercolationCellGrid(int numRows, int numCols) {
        super(numRows, numCols);
    }

    @Override
    public void initializeControlPanel() {
        this.getControlPanel().getMyColPane().getChildren().clear();
        String[] controlsList = getControlPanel().getResourceBundle().getString("PercolationControls").split(",");
        for (String controlType : controlsList) {
            SimulationSlider segregationSlider = createSliderFromResourceFile(controlType);
            segregationSlider.getMySlider().valueProperty().addListener(e -> sliderAction(controlType, (double) Math.round(segregationSlider.getMySlider().getValue())));
        }
    }

    protected void sliderAction(String type, double inputPercentage) {
        //TODO: added slider actions @Eric
        // type: "PercentBlocked", "NumberPercolated"
        if(type.equals("PercentBlocked")) {
           this.initBlocked = (int) (inputPercentage * getGridOfCells().size() / 100);
        }
        else {
            this.initPercolated = (int)inputPercentage;
        }
        openCells.clear();
        createEmptyMap();
        randomlyInitializeGrids();
        assignNeighborsToEachCell();
        System.out.println(type);
    }

    @Override
    public void initializeGrids(Map<Point, CellState> configMap) {
        createEmptyMap();
        for (Map.Entry<Point, CellState> entry : configMap.entrySet()) {
            getGridOfCells().get(entry.getKey()).setState(entry.getValue());
        }
    }

    @Override
    public void addEmptyStateToCell(int row, int col) {
        Point point = new Point(row, col);
        addToGridOfCells(point, new PercolationCell(row, col, CellState.OPEN));
        openCells.add(getGridOfCells().get(point));
    }

    private void randomlyInitializeGrids() {
        createEmptyMap();
        for(int i=0; i<initBlocked; i++) {
            addToRandomCell(CellState.BLOCKED);
        }
        for(int i=0; i<initPercolated; i++) {
            addToRandomCell(CellState.PERCOLATED);
        }
    }

    private void addToRandomCell(CellState state) {
        Cell openCell = openCells.get((int) (Math.random() * openCells.size()));
        openCell.setState(state);
        openCells.remove(openCell);
    }

    private void createEmptyMap() {
        clearMap();
        openCells.clear();
        for (int row = 0; row < getNumOfRows(); row++) {
            for (int col = 0; col < getNumOfCols(); col++) {
                addEmptyStateToCell(row, col);
            }
        }
    }
}
