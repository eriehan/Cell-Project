package simulation;

import java.util.List;

public class PercolationCell extends Cell{

    public PercolationCell(int row, int col, CellState state) {
        super(row, col, state);
    }
    @Override
    public void check() {
        if(toBePercolated()) {setNextState(CellState.PERCOLATED);}
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

    private boolean toBePercolated() {
        if(getState()!= CellState.EMPTY) {return false;}
        for(Cell neighbor : allNeighbors()) {
            if(neighbor.getState() == CellState.PERCOLATED) {
                return true;
            }
        }
        return false;
    }
}
