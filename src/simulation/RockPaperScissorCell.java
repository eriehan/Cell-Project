package simulation;

import java.util.Arrays;
import java.util.List;

public class RockPaperScissorCell extends Cell {
    private static final CellState ROCK = CellState.ROCK;
    private static final CellState PAPER = CellState.PAPER;
    private static final CellState SCISSOR = CellState.SCISSOR;
    private static final CellAttribute THRESHOLD = CellAttribute.THRESHOLD;

    private static final List<CellState> STATES_LIST = Arrays.asList(ROCK, PAPER, SCISSOR);
    private int threshold;

    public RockPaperScissorCell(int row, int col, CellState state, int threshhold) {
        super(row, col, state);
        this.threshold = threshhold;
        putAttribute(THRESHOLD, threshhold);
    }

    @Override
    public void check() {
        if (changeToWinner()) {
            setNextState(winningCellState());
        }
    }

    @Override
    public void changeState() {
        setState(getNextState());
    }

    public void changeThreshold(int threshhold) {
        putAttribute(THRESHOLD, threshhold);
    }

    private CellState winningCellState() {
        int index = STATES_LIST.indexOf(getState());
        index++;
        index %= STATES_LIST.size();
        return STATES_LIST.get(index);
    }

    private boolean changeToWinner() {
        return countNeighborsWithState(winningCellState()) >= threshold;
    }
}
