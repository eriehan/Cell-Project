package simulation;

import java.util.ArrayList;
import java.util.List;

public class SegregationCell extends Cell {

    private static final CellState EMPTY = CellState.EMPTY;
    private static final CellState DISATISFIED = CellState.DISATISFIED;

    private int agentPercent;

    public SegregationCell(int row, int col, CellState state, int agentPercent) {
        super(row, col, state);

        putAttribute(CellAttribute.AGENTPERCENT, agentPercent);
        this.agentPercent = agentPercent;
    }

    @Override
    public void check() {
        //if empty, next state is empty.
        if(getState() == EMPTY) {setNextState(CellState.EMPTY);}
        else {
            int cellsWithSameState = 0;
            int notEmptyNeighbors = 0;
            for (Cell other : allNeighbors()) {
                if(other.getState() != EMPTY) {
                    if (other.getState() == getState()) { cellsWithSameState++; }
                    notEmptyNeighbors++;
                }
            }
            if (cellsWithSameState < notEmptyNeighbors * agentPercent / 100.0) { setNextState(DISATISFIED); }
            else { setNextState(getState()); }
        }
    }

    @Override
    public void changeState() {
        setState(getNextState());
    }

    private List<Cell> allNeighbors() {
        List<Cell> list = new ArrayList<>();
        list.addAll(getCornerNeighbor());
        list.addAll(getEdgeNeighbor());
        return list;
    }
}
