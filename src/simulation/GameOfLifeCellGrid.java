package simulation;

import utils.Point;

import java.util.Map;

public class GameOfLifeCellGrid extends CellGrid {
    private int numOfRows;
    private int numOfCols;

    public GameOfLifeCellGrid(int numRows, int numCols) {
        this.numOfRows = numRows;
        this.numOfCols = numCols;
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
    }

    @Override
    public void assignNeighborsToEachCell() {
        for (int i = 0; i < numOfRows; i++) {
            for (int j = 0; j < numOfCols; j++) {
                assignNeighborsToOneCell(i, j);
            }
        }
    }

    protected void assignNeighborsToOneCell(int row, int col) {
        Cell cell = cellFromPoint(row, col);
        cell.clearNeighbors();

        if (!(row == 0 || col == 0)) {
            cell.addCornerNeighbor(cellFromPoint(row - 1, col - 1));
        }
        if (!(row == 0 || col == numOfCols - 1)) {
            cell.addCornerNeighbor(cellFromPoint(row - 1, col + 1));
        }
        if (!(row == numOfRows - 1 || col == 0)) {
            cell.addCornerNeighbor(cellFromPoint(row + 1, col - 1));
        }
        if (!(row == numOfRows - 1 || col == numOfCols - 1)) {
            cell.addCornerNeighbor(cellFromPoint(row + 1, col + 1));
        }

        if (row != 0) {
            cell.addEdgeNeighbor(cellFromPoint(row - 1, col));
        }
        if (row != numOfRows - 1) {
            cell.addEdgeNeighbor(cellFromPoint(row + 1, col));
        }
        if (col != 0) {
            cell.addEdgeNeighbor(cellFromPoint(row, col - 1));
        }
        if (col != numOfCols - 1) {
            cell.addEdgeNeighbor(cellFromPoint(row, col + 1));
        }

    }

    private Cell cellFromPoint(int row, int col) {
        return getGridOfCells().get(new Point(row, col));
    }

    //creates a map of cells with state==dead
    private void createEmptyMap() {
        getGridOfCells().clear();
        for (int row = 0; row < numOfRows; row++) {
            for (int col = 0; col < numOfCols; col++) {
                Point point = new Point(row, col);
                addToGridOfCells(point, new GameOfLifeCell(row, col, CellState.DEAD));
            }
        }
    }

    public int getNumOfRows() {
        return numOfRows;
    }

    public int getNumOfCols() {
        return numOfCols;
    }
}
