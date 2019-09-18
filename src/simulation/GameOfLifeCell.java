package simulation;

public class GameOfLifeCell extends Cell {

    public GameOfLifeCell(int row, int col, int state) {
        super(row, col, state);
    }

    @Override
    public void check() {
        int countAlive = 0;
        for(Cell cell : getCornerNeighbor()) {
            if(cell.getState() > 0) {countAlive++;}
        }
        for(Cell cell : getEdgeNeighbor()) {
            if(cell.getState() > 0) {countAlive++;}
        }
        if(isAlive()) {
            if (countAlive < 2 || countAlive > 3) {
                setNextState(0);
            }
        } else {
            if(countAlive == 3) {setNextState(1);}
        }
    }

    @Override
    public void changeState() {
        setState(getNextState());
    }

    private boolean isAlive() {return getState() > 0;}
}
