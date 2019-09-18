package simulation;

public class GameOfLifeCell extends Cell {


//    private double width;
//    private double height;
//    private Rectangle rectangle; // Don't think we need to have visualization objs in cell class itself

    public GameOfLifeCell(int r, int c, CellType state) {
        super(r, c, state);
    }

    @Override
    public void check() {
        int countAlive = 0;
        for (Cell cell : getCornerNeighbor()) {
            if (cell.getState() == CellType.ALIVE) {
                countAlive++;
            }
        }
        for (Cell cell : getEdgeNeighbor()) {
            if (cell.getState() == CellType.ALIVE) {
                countAlive++;
            }
        }
        if (isAlive()) {
            if (countAlive < 2 || countAlive > 3) {
                setNextState(CellType.DEAD);
            }
        } else {
            if (countAlive == 3 || countAlive == 2) {
                setNextState(CellType.ALIVE);
            }
        }
    }

    @Override
    public void changeState() {
        setState(getNextState());
    }

    private boolean isAlive() {
        return this.getState() == CellType.ALIVE;
    }


}