package simulation;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class GameOfLifeCell extends Cell {

    private static final Paint DEAD_COLOR = Color.WHITE;
    private static final Paint ALIVE_COLOR = Color.BLACK;

//    private double width;
//    private double height;
//    private Rectangle rectangle; // Don't think we need to have visualization objs in cell class itself

    public GameOfLifeCell(CellType state,int r, int c) {
        super(state,r,c);
//        this.width = width;
//        this.height = height;
//
//        rectangle = new Rectangle(width, height, stateToPaint());
    }

    @Override
    public void check() {
        int countAlive = 0;
        for(Cell cell : getNeighbors()) {
            if(cell.getState()==CellType.ALIVE) {countAlive++;}
        }
        if(isAlive()) {
            if (countAlive < 2 || countAlive > 3) {
                setNextState(CellType.DEAD);
            }
        } else {
            if(countAlive == 3 || countAlive ==2) {setNextState(CellType.ALIVE);}
        }

    }

    @Override
    public void changeState() {
        setState(getNextState());
//        rectangle.setFill(stateToPaint());
    }

//    @Override
//    public Node getNode() {
//        return rectangle;
//    }

    private Paint stateToPaint() {
        if(isAlive()) {return ALIVE_COLOR;}
        return DEAD_COLOR;
    }

    private boolean isAlive() {return getState() == CellType.ALIVE;}
}
