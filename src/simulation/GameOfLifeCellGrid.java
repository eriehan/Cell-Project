package simulation;

import userInterface.SimulationSlider;
import utils.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameOfLifeCellGrid extends CellGrid {

    private int initialAliveNum = 5;
    private int HUNDRED = 100;
    private List<Cell> deadCells = new ArrayList<>();

    public GameOfLifeCellGrid(int numRows, int numCols) {
        super(numRows, numCols);
    }

    @Override
    public void initializeControlPanel() {
        this.getControlPanel().getMyColPane().getChildren().clear();
        String[] controlsList = getControlPanel().getResourceBundle().getString("GameofLifeControls").split(",");
        for (String controlType : controlsList) {
            SimulationSlider segregationSlider = createSliderFromResourceFile(controlType);
            segregationSlider.getMySlider().valueProperty().addListener(e -> sliderAction(controlType, (double) Math.round(segregationSlider.getMySlider().getValue())));
        }
    }

    protected void sliderAction(String type, double inputPercentage) {
        //TODO: added slider actions @Eric
        this.initialAliveNum = (int) (getGridOfCells().size() * inputPercentage / 100);
        deadCells.clear();
        createEmptyMap();
        randomlyInitialize();
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
    public void checkAllCells() {
        for (Cell cell : getGridOfCells().values()) {
            cell.check();
        }
    }

    @Override
    public void changeAllCells() {
        for (Cell cell : getGridOfCells().values()) {
            cell.changeState();
        }
        cellGridExpand();
    }

    @Override
    public void addEmptyStateToCell(int row, int col) {
        Point point = new Point(row, col);
        addToGridOfCells(point, new GameOfLifeCell(row, col, CellState.DEAD));
        deadCells.add(getGridOfCells().get(point));
    }

    //creates a map of cells with state==dead
    private void createEmptyMap() {
        clearMap();
        for (int row = 0; row < getNumOfRows(); row++) {
            for (int col = 0; col < getNumOfCols(); col++) {
                addEmptyStateToCell(row, col);
            }
        }
    }

    private void randomlyInitialize() {
        createEmptyMap();
        for(int i=0; i<initialAliveNum; i++) {
            Cell cell = deadCells.get((int) (Math.random() * deadCells.size()));
            deadCells.remove(cell);
            cell.setState(CellState.ALIVE);
        }
    }
}
