package simulation;

import utils.Point;

import java.util.ArrayList;

public abstract class Cell {

    private CellState state;
    private CellState nextState;
    private Point coord; // x is row, y is col


    private ArrayList<Cell> cornerNeighbors;
    private ArrayList<Cell> edgeNeighbors;


    public Cell(int row, int col, CellState state) {
        this.state = state;
        this.nextState = state;
        this.coord = new Point(row,col);

        cornerNeighbors = new ArrayList<>();
        edgeNeighbors = new ArrayList<>();
    }

    public void setNextState(CellState nextState) {
        this.nextState = nextState;
    }

    public CellState getNextState() {
        return nextState;
    }


    public abstract void check();

    public abstract void changeState();

    public CellState getState() {
        return state;
    }

    public void setState(CellState state) {
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
        CellState tempState = getState();
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

    public void setCol(int col) { coord.setCol(col); }

    public void clearNeighbors() {
        edgeNeighbors.clear();
        cornerNeighbors.clear();
    }
}
