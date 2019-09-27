package simulation;

import utils.Point;

public class RockPaperScissorGrid extends GameOfLifeCellGrid {
    public RockPaperScissorGrid(int numRows, int numCols, int paperPercent, int scissorPercent) {
        super(numRows, numCols);
    }

    @Override
    public void addEmptyStateToCell(int row, int col) {
        Point point = new Point(row, col);
        addToGridOfCells(point, new GameOfLifeCell(row, col, CellState.ROCK));
    }
}
