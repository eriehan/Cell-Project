package simulation;

import utils.Point;

import java.util.Map;

public class FireCellGrid extends GameOfLifeCellGrid{
    private double probCatch;

    public FireCellGrid(int numRows, int numCols, int probCatch) {
        super(numRows, numCols);
        this.probCatch = probCatch;
    }

    @Override
    public void initializeGrids(Map<Point, CellType> configMap) {
        createMapFullOfTrees();
        for(Map.Entry<Point, CellType> entry : configMap.entrySet()) {
            getGridOfCells().get(entry.getKey()).setState(entry.getValue());
        }
    }

    private void createMapFullOfTrees() {
        getGridOfCells().clear();
        for(int row = 0; row < getNumOfRows(); row++) {
            for (int col = 0; col < getNumOfCols(); col++) {
                Point point = new Point(row, col);
                addToGridOfCells(point, new FireCell(row, col, CellType.TREE, probCatch));
            }
        }
    }
}