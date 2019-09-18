package simulation;

import javafx.scene.Node;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;

public abstract class Cell {

    private CellType state;
    private CellType nextState;
    private Point coordinate;

    private ArrayList<Cell> neighbors;

    public Cell(CellType state, int r, int c) {
        this.state = state;
        this.coordinate = new Point(r,c); //Joey: added coordinate for each cell
    }

    public void setNextState(CellType nextState) {this.nextState = nextState;}
    public CellType getNextState() {return nextState;}
    public abstract void check();
    public abstract void changeState();
    public CellType getState() {return state;}
    public void setState(CellType state) {this.state = state;}
    public void addNeighbor(Cell neighbor) {neighbors.add(neighbor);}
    public ArrayList<Cell> getNeighbors() {return neighbors;}

    //For the flexibility of the shapes that the cell can be
//    public abstract Node getNode();
}
