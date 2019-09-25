package simulation;

import utils.Point;

import java.util.Map;

public class PercolationCellGrid extends GameOfLifeCellGrid {
    public PercolationCellGrid(int numRows, int numCols) {
        super(numRows, numCols);
    }

    @Override
    public void initializeGrids(Map<Point, CellState> configMap) {
        createEmptyMap();
        for (Map.Entry<Point, CellState> entry : configMap.entrySet()) {
            getGridOfCells().get(entry.getKey()).setState(entry.getValue());
        }
    }

    private void createEmptyMap() {
        getGridOfCells().clear();
        for (int row = 0; row < getNumOfRows(); row++) {
            for (int col = 0; col < getNumOfCols(); col++) {
                Point point = new Point(row, col);
                addToGridOfCells(point, new PercolationCell(row, col, CellState.OPEN));
            }
        }
    }
}
