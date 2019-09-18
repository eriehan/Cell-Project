package simulation;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class FireCell extends Cell {

    private double probCatch;
    private boolean fireNearby;

    public FireCell(int row, int col, int state, double probCatch) {
        super(row, col, state);
        this.probCatch = probCatch;
    }

    @Override
    public void check() {
        //if already burnt or in fire, will be empty at next round.
        if(getState()==0) {setNextState(0);}
        else if(getState()==1) {setNextState(0);}

        else {
            for (Cell neighbor : getEdgeNeighbor()) {
                if (neighbor.getState()==1) {
                    fireNearby = true;
                    return;
                }
            }
            if(fireNearby) {
                if(Math.random() < probCatch) {setNextState(1);}
            } else {
                setNextState(2);
            }
        }
    }

    @Override
    public void changeState() {
        fireNearby = false;
        setState(getNextState());
    }
}
