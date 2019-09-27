package simulation.AntForaging;

import simulation.CellGrid;
import simulation.CellState;
import utils.Point;

import java.util.Map;

public class AntForagingCellGrid extends CellGrid {
    private int maxAnt;
    private double evaporation;

    public AntForagingCellGrid(int numRows, int numCols) {
        super(numRows, numCols);
    }

    @Override
    public void initializeGrids(Map<Point, CellState> configMap) {

    }

    @Override
    public void checkAllCells() {

    }

    @Override
    public void changeAllCells() {

    }

    @Override
    public void addEmptyStateToCell(int row, int col) {

    }
}
