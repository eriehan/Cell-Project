package simulation;

import utils.Point;

import java.util.Map;

public class GameOfLifeCellGrid extends CellGrid {

    public GameOfLifeCellGrid(int numRows, int numCols) {
        super(numRows, numCols);
    }

    @Override
    public void initializeControlPannel() {
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
    }

    //creates a map of cells with state==dead
    private void createEmptyMap() {
        getGridOfCells().clear();
        for (int row = 0; row < getNumOfRows(); row++) {
            for (int col = 0; col < getNumOfCols(); col++) {
                addEmptyStateToCell(row, col);
            }
        }
    }
}
