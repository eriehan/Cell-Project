package simulation;

public class GameOfLifeCell extends Cell {

    private static final CellState ALIVE = CellState.ALIVE;
    private static final CellState DEAD = CellState.DEAD;

    public GameOfLifeCell(int r, int c, CellState state) {
        super(r, c, state);
    }

    @Override
    public void check() {
        int countAlive = 0;
        for (Cell cell : getCornerNeighbor()) {
            if (cell.getState() == ALIVE) {
                countAlive++;
            }
        }
        for (Cell cell : getEdgeNeighbor()) {
            if (cell.getState() == ALIVE) {
                countAlive++;
            }
        }
        if (isAlive()) {
            if (countAlive < 2 || countAlive > 3) {
                setNextState(DEAD);
            }
            else {setNextState(ALIVE);}
        } else {
            if (countAlive == 3) {
                setNextState(ALIVE);
            } else {setNextState(DEAD);}
        }
    }

    @Override
    public void changeState() {
        setState(getNextState());
    }

    private boolean isAlive() {
        return this.getState() == ALIVE;
    }
}