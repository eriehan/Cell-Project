package simulation;

import utils.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Cell {

    private CellState state;
    private CellState nextState;
    private Point coord; // x is row, y is col


    private List<Cell> cornerNeighbors = new ArrayList<>();
    private List<Cell> edgeNeighbors = new ArrayList<>();
    private Map<CellAttribute, Integer> attributes = new HashMap<>();

    private List<CellState> possibleStates = new ArrayList<>();

    public Cell(int row, int col, CellState state) {
        this.state = state;
        this.nextState = state;
        this.coord = new Point(row, col);

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

    public List<Cell> getCornerNeighbor() {
        List<Cell> tempCornerNeighbors = cornerNeighbors;
        return tempCornerNeighbors;
    }

    public List<Cell> getEdgeNeighbor() {
        return edgeNeighbors;
    }

    public void addCornerNeighbors(List<Cell> cornerNeighbors) {
        this.cornerNeighbors = cornerNeighbors;
    }

    public void addEdgeNeighbors(List<Cell> edgeNeighbors) {
        this.edgeNeighbors = edgeNeighbors;
    }

    //called when the cell needs to move. Changes state with the cell that the cell should move to.
    public void moveToDifferentCell(Cell other) {
        nextState = other.getState();
        other.setNextState(state);
    }

    public Point getCoord() {return coord;}

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

    public void clearNeighbors() {
        clearEdgeNeighbors();
        clearCornerNeighbors();
    }

    public void clearEdgeNeighbors() {
        edgeNeighbors.clear();
    }

    public void clearCornerNeighbors() {
        cornerNeighbors.clear();
    }

    public void putAttribute(CellAttribute cellAttribute, int value) {
        attributes.put(cellAttribute, value);
    }

    public int getAttribute(CellAttribute cellAttribute) {
        return attributes.get(cellAttribute);
    }

    public List<CellState> getPossibleStates() {
        List<CellState> states = possibleStates;
        return states;
    }

    protected void setPossibleStates(List<CellState> possibleStates) {
        this.possibleStates = possibleStates;
    }

    protected void setNextStateOnClick() {
        System.out.println(possibleStates.size());
        if(!possibleStates.contains(state)) {
            System.out.println("Fix code"); return;
        }
        int index = possibleStates.indexOf(getState());
        if (possibleStates.size()==index+1) {
            index = -1;
        }
        setState(possibleStates.get(index+1));
        //setNextState(getState());
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Cell && other.hashCode() == hashCode();
    }

    @Override
    public int hashCode() {
        return coord.toString().hashCode() + "Cell".hashCode();
    }

    protected int countNeighborsWithState(CellState state) {
        int count = 0;
        for (Cell cell : getCornerNeighbor()) {
            if (cell.getState() == state) { count++; }
        }
        for (Cell cell : getEdgeNeighbor()) {
            if (cell.getState() == state) { count++; }
        }
        return count;
    }
}
