package simulation;

import userInterface.ControlPanel;
import userInterface.CellShapeType;
import utils.Point;

import java.util.HashMap;
import java.util.Map;

public abstract class CellGrid {

    //Can change to hashmap later. using 2D arrayList here just to show the idea.
    private Map<Point, Cell> gridOfCells = new HashMap<>();
    //true when cellgrid is fully stabilized, and nothing will change indefinitely.
    private boolean finished = false;
    private GridLimit gridLimit = GridLimit.FINITE;
    private CellShapeType cellShapeType = CellShapeType.RECTANGLE;
    private int numOfRows;
    private int numOfCols;
    private ControlPanel controlPanel;

    public CellGrid(int numRows, int numCols) {
        this.numOfRows = numRows;
        this.numOfCols = numCols;
        this.controlPanel = new ControlPanel();
    }

    public void initializeControlPanel(){
        this.controlPanel.getMyColPane().getChildren().clear();
    };

    public abstract void initializeGrids(Map<Point, CellState> configMap);

    //Iterates through all cells and change nextState
    public abstract void checkAllCells();

    //Iterates through all cells and change state.
    public abstract void changeAllCells();

    protected void createEmptyRow(int row) {
        for(int col=0; col<getNumOfCols(); col++) {
            addEmptyStateToCell(row, col);
        }
    }

    protected void createEmptyCol(int col) {
        for(int row=0; row<getNumOfRows(); row++) {
            addEmptyStateToCell(row, col);
        }
    }

    public abstract void addEmptyStateToCell(int row, int col);

    //Must be called for initializing by gridView
    public void assignNeighborsToEachCell() {
        gridLimit.assignNeighbors(getGridOfCells(), cellShapeType, numOfRows, numOfCols);
    }

    protected void cellGridExpand() {
        //only executed when gridLimit is Infinite
        if(gridLimit != GridLimit.INFINITE) {
            return;
        }
        boolean expand = false;
        if(!isColEmpty(0)) {
            expand = true;
            addColOnLeft();
        }
        if(!isColEmpty(getNumOfCols()-1)) {
            expand = true;
            addColOnRight();
        }
        if(!isRowEmpty(0)) {
            expand = true;
            addRowOnTop();
        }
        if(!isRowEmpty(getNumOfRows()-1)) {
            expand = true;
            addRowOnBottom();
        }

        if(expand) {
            assignNeighborsToEachCell();
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

    public ControlPanel getControlPanel() {
        return controlPanel;
    }

    public void setControlPanel (ControlPanel newControlPanel){
        controlPanel = newControlPanel;
    }

    private boolean isRowEmpty(int rowNum) {
        for(int col=0; col<numOfCols; col++) {
            Cell cell = cellFromPoint(rowNum, col);
            if(cell.getState() != cell.getPossibleStates().get(0)) {
                return false;
            }
        }
        return true;
    }

    private boolean isColEmpty(int colNum) {
        for(int row=0; row<numOfRows; row++) {
            Cell cell = cellFromPoint(row, colNum);
            if(cell.getState() != cell.getPossibleStates().get(0)) {
                return false;
            }
        }
        return true;
    }

    private void addRowOnTop() {
        numOfRows++;
        createEmptyRow(numOfRows-1);
        for(int i=numOfRows-1; i>=1; i--) {
            for(int j=0; j<numOfCols; j++) {
                gridOfCells.put(new Point(i, j), cellFromPoint(i-1, j));
            }
        }
        createEmptyRow(0);
    }

    private void addRowOnBottom() {
        numOfRows++;
        createEmptyRow(numOfRows-1);
    }

    private void addColOnLeft() {
        numOfCols++;
        createEmptyCol(numOfCols-1);
        System.out.println(getGridOfCells().size());
        for(int col=numOfCols-1; col>=1; col--) {
            for(int row=0; row<numOfRows; row++) {
                gridOfCells.put(new Point(row, col), cellFromPoint(row, col-1));
            }
        }
        createEmptyCol(0);
    }

    private void addColOnRight() {
        numOfCols++;
        createEmptyCol(numOfCols-1);
    }
}
