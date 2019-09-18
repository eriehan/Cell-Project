package simulation;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


public class GameOfLifeCell extends Cell {

    private static final Paint DEAD_COLOR = Color.WHITE;
    private static final Paint ALIVE_COLOR = Color.BLACK;

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

    private Paint stateToPaint() {
        if(isAlive()) {return ALIVE_COLOR;}
        return DEAD_COLOR;
    }

    private boolean isAlive() {return getState() > 0;}
}
