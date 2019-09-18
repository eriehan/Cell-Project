package simulation;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import utils.Point;

import java.util.ArrayList;

public abstract class Cell {

    private CellType state;
    private CellType nextState;
    private Point coord; // x is row, y is col


    private ArrayList<Cell> cornerNeighbors;
    private ArrayList<Cell> edgeNeighbors;


    public Cell(int row, int col, CellType state) {
        this.state = state;
        this.nextState = state;
        this.coord = new Point(row,col);

        cornerNeighbors = new ArrayList<>();
        edgeNeighbors = new ArrayList<>();
    }

    public void setNextState(CellType nextState) {
        this.nextState = nextState;
    }

    public CellType getNextState() {
        return nextState;
    }


    public abstract void check();

    public abstract void changeState();

    public CellType getState() {
        return state;
    }

    public void setState(CellType state) {
        this.state = state;
    }


    public void addCornerNeighbor(Cell neighbor) {
        cornerNeighbors.add(neighbor);
    }

    public void addEdgeNeighbor(Cell neighbor) {
        edgeNeighbors.add(neighbor);
    }

    public ArrayList<Cell> getCornerNeighbor() {
        return cornerNeighbors;
    }

    public ArrayList<Cell> getEdgeNeighbor() {
        return edgeNeighbors;
    }

    public void setCornerNeighbors(ArrayList<Cell> cornerNeighbors) {
        this.cornerNeighbors = cornerNeighbors;
    }

    public void setEdgeNeighbors(ArrayList<Cell> edgeNeighbors) {
        this.edgeNeighbors = edgeNeighbors;
    }

    //called when the cell needs to move. Changes state with the cell that the cell should move to.
    public void moveToDifferentCell(Cell other) {
        CellType tempState = getState();
        setState(other.getState());
        other.setState(tempState);
    }

    public int getRow() {
        return coord.getRow();
    }

    public void setRow(int row) {
        coord.setRow(row);
    }

    public int getCol() {
        return coord.getCol();
    }

    public void setCol(int col) {
        coord.setCol(col);
    }
}
