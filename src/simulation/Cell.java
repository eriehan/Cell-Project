package simulation;

import javafx.scene.Node;
import java.util.ArrayList;

public abstract class Cell {

    private int state;
    private int nextState;

    private ArrayList<Cell> neighbors;

    public Cell(int state) {
        this.state = state;
    }

    public void setNextState(int nextState) {this.nextState = nextState;}
    public int getNextState() {return nextState;}
    public abstract void check();
    public abstract void changeState();
    public int getState() {return state;}
    public void setState(int state) {this.state = state;}
    public void addNeighbor(Cell neighbor) {neighbors.add(neighbor);}
    public ArrayList<Cell> getNeighbors() {return neighbors;}

    //For the flexibility of the shapes that the cell can be.
    public abstract Node getNode();
}
