package simulation;

import javafx.scene.Node;
import java.util.ArrayList;

public abstract class Cell {

    private int row;
    private int col;
    private int state;
    private int nextState;

    private ArrayList<Cell> cornerNeighbors;
    private ArrayList<Cell> edgeNeighbors;

    public Cell(int row, int col, int state) {
        this.state = state;
        this.nextState = state;
        this.row = row;
        this.col = col;

        cornerNeighbors = new ArrayList<>();
        edgeNeighbors = new ArrayList<>();
    }

    public void setNextState(int nextState) {this.nextState = nextState;}

    public int getNextState() {return nextState;}

    public abstract void check();

    public abstract void changeState();

    public int getState() {return state;}

    public void setState(int state) {this.state = state;}

    public void addCornerNeighbor(Cell neighbor) {cornerNeighbors.add(neighbor);}

    public void addEdgeNeighbor(Cell neighbor) {edgeNeighbors.add(neighbor);}

    public ArrayList<Cell> getCornerNeighbor() {return cornerNeighbors;}

    public ArrayList<Cell> getEdgeNeighbor() {return edgeNeighbors;}

    public void setCornerNeighbors(ArrayList<Cell> cornerNeighbors) {this.cornerNeighbors = cornerNeighbors;}

    public void setEdgeNeighbors(ArrayList<Cell> edgeNeighbors) {this.edgeNeighbors = edgeNeighbors;}

    //called when the cell needs to move. Changes state with the cell that the cell should move to.
    public void moveToDifferentCell(Cell other) {
        int tempState = getState();
        setState(other.getState());
        other.setState(tempState);
    }

    public int getRow() { return row; }

    public void setRow(int row) { this.row = row; }

    public int getCol() { return col; }

    public void setCol(int col) { this.col = col; }
}
