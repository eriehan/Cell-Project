package userInterface;

import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import simulation.*;
import utils.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static userInterface.VisualizationConstants.BLANK_GRID_COLOR;

public abstract class AbstractGridView {
    private static final String RESOURCE_FILE_PATH = "resources/MainResources";
    private GridPane myGridPane;
    private int numOfRows;
    private int numOfCols;
    private int gridWidth;
    private int gridHeight;
    private CellGrid myCellGrid;
    private Map<Point, CellState> initialConfigMap;
    private ResourceBundle resourceBundle;


    public AbstractGridView(int numOfRows, int numOfCols) {
        myGridPane = new GridPane();
        this.numOfCols = numOfCols;
        this.numOfRows = numOfRows;
        this.resourceBundle = ResourceBundle.getBundle(RESOURCE_FILE_PATH);
        this.gridWidth = Integer.parseInt(resourceBundle.getString("GridWidth"));
        this.gridHeight = Integer.parseInt(resourceBundle.getString("GridHeight"));
    }

    public abstract void createGrid();

    public void generateBlankGrid() {
        Rectangle shape = new Rectangle(this.gridWidth, this.gridHeight);
        shape.setFill(BLANK_GRID_COLOR);
        myGridPane.add(shape, 0, 0);
    }

    public void updateGrid() {
        if (myCellGrid==null){
            return;
        }
        myGridPane.getChildren().clear();
        createGrid();
        myCellGrid.checkAllCells();
        myCellGrid.changeAllCells();
    }

//    public abstract void initializeMyCellGrid(ArrayList<ArrayList<Integer>> row, ArrayList<ArrayList<Integer>> col, String s, int rowSize, int colSize);
    public void initializeMyCellGrid(ArrayList<ArrayList<Integer>> row, ArrayList<ArrayList<Integer>> col, String s, int rowSize, int colSize,
                                     ArrayList<Integer> energyArray, ArrayList<Integer> maturityArray, double rate) {
        Map configMap = new HashMap<Point, CellState>();

        System.out.println(s);

        switch (s) {
            case "Segregation":
                for (int i = 0; i < row.size(); i++) {
                    if (i == 0) {
                        for (int j = 0; j < row.get(i).size(); j++) {
                            configMap.put(new Point(row.get(i).get(j), col.get(i).get(j)), CellState.FIRSTAGENT);
                        }
                    } else {
                        for (int j = 0; j < row.get(i).size(); j++) {
                            configMap.put(new Point(row.get(i).get(j), col.get(i).get(j)), CellState.SECONDAGENT);
                        }
                    }
                }
                //TODO: make this work
                setMyCellGrid(new SegregationCellGrid(rowSize, colSize, (int)rate, (int)rate));
                break;
            case "Game Of Life":
                for (int i = 0; i < row.size(); i++) {
                    for (int j = 0; j < row.get(i).size(); j++) {
                        configMap.put(new Point(row.get(i).get(j), col.get(i).get(j)), CellState.ALIVE);
                    }
                }
                setMyCellGrid(new GameOfLifeCellGrid(rowSize, colSize));
                break;
            case "Wa-Tor":
                for (int i = 0; i < row.size(); i++) {
                    if (i == 0) {
                        for (int j = 0; j < row.get(i).size(); j++) {
                            configMap.put(new Point(row.get(i).get(j), col.get(i).get(j)), CellState.SHARK);
                        }
                    } else {
                        for (int j = 0; j < row.get(i).size(); j++) {
                            configMap.put(new Point(row.get(i).get(j), col.get(i).get(j)), CellState.FISH);
                        }
                    }
                }

                setMyCellGrid(new WaTorCellGrid(rowSize, colSize, maturityArray, energyArray));
                break;

            case "Percolation":
                for (int i = 0; i < row.size(); i++) {
                    if(i == 0) {
                        for (int j = 0; j < row.get(i).size(); j++) {
                            configMap.put(new Point(row.get(i).get(j), col.get(i).get(j)), CellState.PERCOLATED);
                        }
                    }
                    else{
                        for(int j = 0; j < row.get(i).size(); j++){
                            configMap.put(new Point(row.get(i).get(j), col.get(i).get(j)), CellState.BLOCKED);
                        }
                }

            }
                setMyCellGrid(new PercolationCellGrid(rowSize, colSize));
                break;

            case "Fire":
               // System.out.println("entered Fire");
                for (int i = 0; i < row.size(); i++) {
                    for (int j = 0; j < row.get(i).size(); j++) {
                        configMap.put(new Point(row.get(i).get(j), col.get(i).get(j)), CellState.BURNING);
                    }
                }
                setMyCellGrid(new FireCellGrid(rowSize, colSize, rate));
                break;

        }
        this.initialConfigMap = configMap;
        getMyCellGrid().initializeGrids(configMap);
        getMyCellGrid().assignNeighborsToEachCell();
    }

    public void resetCellGrid(){
        if (initialConfigMap == null){
            return;
        }
        getMyCellGrid().initializeGrids(this.initialConfigMap);
        getMyCellGrid().assignNeighborsToEachCell();
    }

    public GridPane getMyGridPane() {
        return myGridPane;
    }

    public int getNumOfRows() {
        return numOfRows;
    }

    public void setNumOfRows(int n){
        numOfRows = n;
    }

    public void setNumOfCols(int n){
        numOfCols = n;
    }

    public int getNumOfCols() {
        return numOfCols;
    }

    public CellGrid getMyCellGrid() {
        return myCellGrid;
    }

    public void setMyCellGrid(CellGrid myCellGrid) {
        this.myCellGrid = myCellGrid;
    }

    public int getGridWidth(){
        return this.gridWidth;
    }
    public int getGridHeight(){
        return this.gridHeight;
    }




}

