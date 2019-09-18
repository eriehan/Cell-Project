package simulation;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class FireCell extends Cell {

    private double probCatch;
    private boolean fireNearby;

    public FireCell(int row, int col, CellType state, double probCatch) {
        super(row, col, state);
        this.probCatch = probCatch;
    }

    @Override
    public void check() {
        //if already burnt or in fire, will be empty at next round.
        if(getState()==CellType.FIREEMPTY) {setNextState(CellType.FIREEMPTY);}
        else if(getState()==CellType.BURNING) {setNextState(CellType.FIREEMPTY);}

        else {
            for (Cell neighbor : getEdgeNeighbor()) {
                if (neighbor.getState()==CellType.BURNING) {
                    fireNearby = true;
                    return;
                }
            }
            if(fireNearby) {
                if(Math.random() < probCatch) {setNextState(CellType.BURNING);}
            } else {
                setNextState(CellType.TREE);
            }
        }
    }

    @Override
    public void changeState() {
        fireNearby = false;
        setState(getNextState());
    }
}
