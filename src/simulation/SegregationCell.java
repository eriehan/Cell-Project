package simulation;

import java.util.ArrayList;
import java.util.List;

public class SegregationCell extends Cell {

    private double agentPercent;

    public SegregationCell(int row, int col, CellState state, double agentPercent) {
        super(row, col, state);

        this.agentPercent = agentPercent;
    }

    @Override
    public void check() {
        //if empty, next state is empty.
        if(getState() == CellState.EMPTY) {setNextState(CellState.EMPTY);}

        else {
            int cellsWithSameState = 0;
            for (Cell other : allNeighbors()) {
                if (other.getState() == getState()) {
                    cellsWithSameState++;
                }
            }
            if (cellsWithSameState < allNeighbors().size() * agentPercent / 100) {
                //dissatisfied
                setNextState(CellState.DISATISFIED);
            }
            else {
                setNextState(getState());
            }
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

    @Override
    public void moveToDifferentCell(Cell other) {
        super.moveToDifferentCell(other);

        setNextState(getState());
        other.setNextState(other.getState());
    }
}