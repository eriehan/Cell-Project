package simulation;

import java.util.List;

public class PercolationCell extends Cell{
    private static final CellState PERCOLATED = CellState.PERCOLATED;
    private static final CellState OPEN = CellState.OPEN;

    public PercolationCell(int row, int col, CellState state) {
        super(row, col, state);
    }
    @Override
    public void check() {
        if(getState() == OPEN && percolatedCellNearby()) {setNextState(PERCOLATED);}
        else {setNextState(getState());}
    }

    @Override
    public void changeState() {
        setState(getNextState());
    }

    private List<Cell> allNeighbors() {
        List<Cell> list = getCornerNeighbor();
        list.addAll(getEdgeNeighbor());
        return list;
    }

    private boolean percolatedCellNearby() {
        for (Cell neighbor : allNeighbors()) {
            if (neighbor.getState() == PERCOLATED) {
                return true;
            }
        }
        return false;
    }
}