package simulation;

public class GameOfLifeCell extends Cell {

    private static final CellState ALIVE = CellState.ALIVE;
    private static final CellState DEAD = CellState.DEAD;

    public GameOfLifeCell(int r, int c, CellState state) {
        super(r, c, state);
    }

    @Override
    public void check() {
        int aliveNeighbors = aliveNeighbors();
        if(willLive(aliveNeighbors)) {setNextState(ALIVE);}
        else {setNextState(DEAD);}
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