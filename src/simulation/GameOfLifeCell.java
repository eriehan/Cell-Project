package simulation;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GameOfLifeCell extends Cell {

    private static final CellState ALIVE = CellState.ALIVE;
    private static final CellState DEAD = CellState.DEAD;
    private static final List<CellState> STATES_LIST =
            Collections.unmodifiableList(Arrays.asList(ALIVE, DEAD));

    public GameOfLifeCell(int r, int c, CellState state) {
        super(r, c, state);
    }

    @Override
    public void check() {
        int aliveNeighbors = aliveNeighbors();
        if(willLive(aliveNeighbors)) {setNextState(ALIVE);}
        else {setNextState(DEAD);}
        setPossibleStates(STATES_LIST);
    }

    @Override
    public void changeState() {
        setState(getNextState());
    }

    private int aliveNeighbors() {
        int countAlive = 0;
        for (Cell cell : getCornerNeighbor()) {
            if (cell.getState() == ALIVE) { countAlive++; }
        }
        for (Cell cell : getEdgeNeighbor()) {
            if (cell.getState() == ALIVE) { countAlive++; }
        }
        return countAlive;
    }

    private boolean willLive(int aliveNeighbors) {
        return (getState() == DEAD && aliveNeighbors == 3) || (getState() == ALIVE && (aliveNeighbors == 2 || aliveNeighbors == 3));
    }
}