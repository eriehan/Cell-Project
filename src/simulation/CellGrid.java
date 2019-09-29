package simulation;

import userInterface.*;
import utils.Point;

import java.util.HashMap;
import java.util.Map;

import static userInterface.NeighborButtonGrid.NUM_COL;
import static userInterface.NeighborButtonGrid.NUM_ROW;

public abstract class CellGrid {

    //Can change to hashmap later. using 2D arrayList here just to show the idea.
    private Map<Point, Cell> gridOfCells = new HashMap<>();
    //true when cellgrid is fully stabilized, and nothing will change indefinitely.
    private int[] neighbor = new int[NUM_COL*NUM_ROW];
    private boolean finished = false;
    private GridLimit gridLimit = GridLimit.FINITE;
    private CellShapeType cellShapeType = CellShapeType.RECTANGLE;
    private int numOfRows;
    private int numOfCols;
    private ControlPanel controlPanel;

    private NeighborManager neighborManager;
    private String neighborConfig = "11111111";

    public CellGrid(int numRows, int numCols) {
        this.numOfRows = numRows;
        this.numOfCols = numCols;
        this.controlPanel = new ControlPanel();
        createNeighborManager();
    }

    public void initializeControlPanel() {
        this.controlPanel.getMyColPane().getChildren().clear();
    }

    protected void initializeControlPanel(String controlsType) {
        this.getControlPanel().getMyColPane().getChildren().clear();
        if (this.cellShapeType ==CellShapeType.RECTANGLE){
            addNeighorButton();
        }
        String[] controlsList = getControlPanel().getResourceBundle().getString(controlsType).split(",");
        for (String controlType : controlsList) {
            SimulationSlider segregationSlider = createSliderFromResourceFile(controlType);
            segregationSlider.getMySlider().valueProperty().addListener(e ->
                    sliderAction(controlType, (double) Math.round(segregationSlider.getMySlider().getValue())));
        }
    }

    private void addNeighorButton(){
        NeighborButtonGrid neighborButtonGrid = new NeighborButtonGrid();
        this.getControlPanel().getMyColPane().getChildren().add(neighborButtonGrid.getMyView());
        for (NeighborButton button : neighborButtonGrid.getButtonList()){
            button.setOnAction(e -> changeNeighbor(button));
        }
    }

    private void changeNeighbor(NeighborButton button){
        if (button.getIdx() != NeighborButton.CENTER){
            button.flipChosen();
            if (button.isChosen()){
                button.setStyle("-fx-background-color: LightBlue");
                neighbor[button.getIdx()] = 1;
            }
            else {
                button.setStyle("-fx-background-color: White");
                neighbor[button.getIdx()] = 0;
            }
        }
        setNeighborConfig(makeNeighborString());
    }

    private String makeNeighborString(){
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<NUM_COL;i++){
            sb.append(neighbor[i]);
        }
        sb.append(neighbor[5]);
        for (int i=NUM_COL*NUM_ROW -1;i>NUM_COL*NUM_ROW -NUM_COL;i++){
            sb.append(neighbor[i]);
        }
        sb.append(neighbor[3]);
        return sb.toString();
    }

    protected abstract void sliderAction(String type, double inputPercentage);

    public SimulationSlider createSliderFromResourceFile(String controlType) {
        int minVal = Integer.parseInt(getControlPanel().getResourceBundle().getString(controlType + "." + "min"));
        int maxVal = Integer.parseInt(getControlPanel().getResourceBundle().getString(controlType + "." + "max"));
        int defaultVal = Integer.parseInt(getControlPanel().getResourceBundle().getString(controlType + "." + "default"));
        String title = getControlPanel().getResourceBundle().getString(controlType + "." + "title");
        SimulationSlider slider = new SimulationSlider(minVal, maxVal, defaultVal, title);
        controlPanel.getMyColPane().getChildren().add(slider.getvBox());
        return slider;
    }

    public abstract void initializeGrids(Map<Point, CellState> configMap);

    //Iterates through all cells and change nextState
    public abstract void checkAllCells();

    //Iterates through all cells and change state.
    public abstract void changeAllCells();

    protected void createEmptyRow(int row) {
        for (int col = 0; col < getNumOfCols(); col++) {
            addEmptyStateToCell(row, col);
        }
    }

    protected void createEmptyCol(int col) {
        for (int row = 0; row < getNumOfRows(); row++) {
            addEmptyStateToCell(row, col);
        }
    }

    public abstract void addEmptyStateToCell(int row, int col);

    //Must be called for initializing by gridView
    public void assignNeighborsToEachCell() {
        //gridLimit.assignNeighbors(getGridOfCells(), cellShapeType, numOfRows, numOfCols);
        createNeighborManager();
        neighborManager.assignAllNeighbors(gridOfCells, numOfRows, numOfCols);
    }

    //This can be used to set different types of neighbor configuration.
    public void setNeighborConfig(String neighborConfig) {
        this.neighborConfig = neighborConfig;
        createNeighborManager();
        neighborManager.assignAllNeighbors(gridOfCells, numOfRows, numOfCols);
    }

    protected void cellGridExpand() {
        //only executed when gridLimit is Infinite
        if (gridLimit != GridLimit.INFINITE) {
            return;
        }
        boolean expand = false;
        if (!isColEmpty(0)) {
            expand = true;
            addColOnLeft();
        }
        if (!isColEmpty(getNumOfCols() - 1)) {
            expand = true;
            addColOnRight();
        }
        if (!isRowEmpty(0)) {
            expand = true;
            addRowOnTop();
        }
        if (!isRowEmpty(getNumOfRows() - 1)) {
            expand = true;
            addRowOnBottom();
        }

        if (expand) {
            assignNeighborsToEachCell();
        }
    }

    public Map<Point, Cell> getGridOfCells() {
        return gridOfCells;
    }

    public void clearMap() {
        gridOfCells.clear();
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

    public NeighborManager getNeighborManager() {
        return neighborManager;
    }

    protected void setFinished(boolean finished) {
        this.finished = finished;
    }

    public CellShapeType getCellShapeType() {
        return cellShapeType;
    }

    public void setCellShapeType(CellShapeType cellShapeType) {
        this.cellShapeType = cellShapeType;
        createNeighborManager();
        assignNeighborsToEachCell();
    }

    public void setGridLimit(GridLimit gridLimit) {
        this.gridLimit = gridLimit;
        assignNeighborsToEachCell();
    }

    public void setGridLimit(String str) {
        if (str.equals("toroidal")) {
            setGridLimit(GridLimit.TOROIDAL);
        } else if (str.equals("infinite")) {
            setGridLimit(GridLimit.INFINITE);
        } else {
            setGridLimit(GridLimit.FINITE);
        }
    }

    public int getNumOfRows() {
        return numOfRows;
    }

    public int getNumOfCols() {
        return numOfCols;
    }

    public ControlPanel getControlPanel() {
        return controlPanel;
    }

    public void setControlPanel(ControlPanel newControlPanel) {
        controlPanel = newControlPanel;
    }

    private boolean isRowEmpty(int rowNum) {
        for (int col = 0; col < numOfCols; col++) {
            Cell cell = cellFromPoint(rowNum, col);
            if (cell.getState() != cell.getPossibleStates().get(0)) {
                return false;
            }
        }
        return true;
    }

    private boolean isColEmpty(int colNum) {
        for (int row = 0; row < numOfRows; row++) {
            Cell cell = cellFromPoint(row, colNum);
            if (cell.getState() != cell.getPossibleStates().get(0)) {
                return false;
            }
        }
        return true;
    }

    private void addRowOnTop() {
        numOfRows++;
        createEmptyRow(numOfRows - 1);
        for (int i = numOfRows - 1; i >= 1; i--) {
            for (int j = 0; j < numOfCols; j++) {
                gridOfCells.put(new Point(i, j), cellFromPoint(i - 1, j));
            }
        }
        createEmptyRow(0);
    }

    private void addRowOnBottom() {
        numOfRows++;
        createEmptyRow(numOfRows - 1);
    }

    private void addColOnLeft() {
        numOfCols++;
        createEmptyCol(numOfCols - 1);
        System.out.println(getGridOfCells().size());
        for (int col = numOfCols - 1; col >= 1; col--) {
            for (int row = 0; row < numOfRows; row++) {
                gridOfCells.put(new Point(row, col), cellFromPoint(row, col - 1));
            }
        }
        createEmptyCol(0);
    }

    private void addColOnRight() {
        numOfCols++;
        createEmptyCol(numOfCols - 1);
    }

    private Cell cellFromPoint(int row, int col) {
        return getGridOfCells().get(new Point(row, col));
    }

    private boolean isToroidal() {
        return gridLimit == GridLimit.TOROIDAL;
    }

    private void createNeighborManager() {
        neighborManager = new NeighborManager(neighborConfig, cellShapeType, isToroidal());
    }
}
