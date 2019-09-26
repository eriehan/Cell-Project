package simulation;

import userInterface.CellShapeType;
import utils.Point;

import java.util.HashMap;
import java.util.Map;

public abstract class CellGrid {

    //Can change to hashmap later. using 2D arrayList here just to show the idea.
    private Map<Point, Cell> gridOfCells = new HashMap<>();
    //true when cellgrid is fully stabilized, and nothing will change indefinitely.
    private boolean finished = false;
    private GridLimit gridLimit = GridLimit.TOROIDAL;
    private CellShapeType cellShapeType = CellShapeType.RECTANGLE;
    private int numOfRows;
    private int numOfCols;

    public CellGrid(int numRows, int numCols) {
        this.numOfRows = numRows;
        this.numOfCols = numCols;
    }

    public abstract void initializeGrids(Map<Point, CellState> configMap);

    //Iterates through all cells and change nextState
    public abstract void checkAllCells();

    //Iterates through all cells and change state.
    public abstract void changeAllCells();

    public void assignNeighborsToEachCell() {
        if(cellShapeType==CellShapeType.RECTANGLE) {
            gridLimit.assignEdgeNeighborsSquare(gridOfCells, numOfRows, numOfCols);
            gridLimit.assignCornerNeighborsSquare(gridOfCells, numOfRows, numOfCols);
        } else if(cellShapeType == CellShapeType.TRIANGLE) {
            //gridLimit.assignNeighborsTriangular(gridOfCells, numOfRows, numOfCols);
        }
    }


    public Map<Point, Cell> getGridOfCells() {
        return gridOfCells;
    }

    public void addToGridOfCells(Point point, Cell cell) {
        gridOfCells.put(point, cell);
    }

    public CellState stateOfCellAtPoint(int row, int col) {
        return gridOfCells.get(new Point(row, col)).getState();
    }

    public void setStateOfCellAtPointOnClick(int row, int col) {
        gridOfCells.get(new Point(row, col)).setNextStateOnClick();
    }

    public boolean isFinished() {
        return finished;
    }

    protected void setFinished(boolean finished) {
        this.finished = finished;
    }

    public CellShapeType getCellShapeType() {
        return cellShapeType;
    }

    public void setCellShapeType(CellShapeType cellShapeType) {
        this.cellShapeType = cellShapeType;
    }

    public GridLimit getGridLimit() {
        return gridLimit;
    }

    public void setGridLimit(GridLimit gridLimit) {
        this.gridLimit = gridLimit;
        assignNeighborsToEachCell();
    }

    private Cell cellFromPoint(int row, int col) {
        return getGridOfCells().get(new Point(row, col));
    }

    public int getNumOfRows() {
        return numOfRows;
    }

    public void setNumOfRows(int numOfRows) {
        this.numOfRows = numOfRows;
    }

    public int getNumOfCols() {
        return numOfCols;
    }

    public void setNumOfCols(int numOfCols) {
        this.numOfCols = numOfCols;
    }
}
