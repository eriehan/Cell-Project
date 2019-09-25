package simulation;

import utils.Point;

import java.util.HashMap;
import java.util.Map;

public abstract class CellGrid {

    //Can change to hashmap later. using 2D arrayList here just to show the idea.
    private Map<Point, Cell> gridOfCells = new HashMap<>();
    //true when cellgrid is fully stabilized, and nothing will change indefinitely.
    private boolean finished = false;

    public abstract void initializeGrids(Map<Point, CellState> configMap);
    //Iterates through all cells and change nextState
    public abstract void checkAllCells();
    //Iterates through all cells and change state.
    public abstract void changeAllCells();

    public abstract void assignNeighborsToEachCell();

    //will be implemented if user can click one grid and change its state
    //public abstract void changeOneCell(Point point);

    public Map<Point, Cell> getGridOfCells() { return gridOfCells; }

    public void addToGridOfCells(Point point, Cell cell) {
        gridOfCells.put(point, cell);
    }

    public CellState stateOfCellAtPoint(int row, int col) {return gridOfCells.get(new Point(row, col)).getState();}

    public void setStateOfCellAtPointOnClick(int row, int col) {gridOfCells.get(new Point(row, col)).setNextStateOnClick();}

    public boolean isFinished() {return finished;}

    protected void setFinished(boolean finished) {this.finished = finished;}

}
