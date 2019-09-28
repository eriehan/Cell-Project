package simulation.AntForaging;

import simulation.CellGrid;
import simulation.CellState;
import simulation.GameOfLifeCell;
import utils.Point;

import java.util.List;
import java.util.Map;

public class AntForagingCellGrid extends CellGrid {
    private final static int MAXANT = 10;

    private Map<Point, GridInfo> gridInfos;
    private int maxAnt = MAXANT;
    private double evaporation;

    public AntForagingCellGrid(int numRows, int numCols) {
        super(numRows, numCols);
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
        maxAnt = 0;
    }

    @Override
    public void changeAllCells() {
        maxAnt = 0;
    }

    @Override
    public void addEmptyStateToCell(int row, int col) {
        Point point = new Point(row, col);
        addToGridOfCells(point, new AntForagingCell(row, col, CellState.EMPTY));
        gridInfos.put(point, new GridInfo(row, col, maxAnt));
    }

    private void createEmptyMap() {
        getGridOfCells().clear();
        gridInfos.clear();
        for (int row = 0; row < getNumOfRows(); row++) {
            for (int col = 0; col < getNumOfCols(); col++) {
                addEmptyStateToCell(row, col);
            }
        }
    }
}
