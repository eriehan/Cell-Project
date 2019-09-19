package userInterface;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import simulation.CellGrid;
import simulation.CellState;
import simulation.SegregationCellGrid;
import utils.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static userInterface.VisualizationConstants.GRID_HEIGHT;
import static userInterface.VisualizationConstants.GRID_WIDTH;

public abstract class AbstractGridView {
    private GridPane myGridPane;
    private int numOfRows;
    private int numOfCols;
    private CellGrid myCellGrid;

    public AbstractGridView(int numOfRows, int numOfCols) {
        myGridPane = new GridPane();
        this.numOfCols = numOfCols;
        this.numOfRows = numOfRows;
    }

    public abstract void createGrid();

    public void generateBlankGrid() {
        Rectangle shape = new Rectangle(GRID_WIDTH, GRID_HEIGHT);
        shape.setFill(Color.LIGHTBLUE);
        myGridPane.add(shape, 0, 0);
    }

    public void updateGrid() {
        myGridPane.getChildren().clear();
        myCellGrid.checkAllCells();
        myCellGrid.changeAllCells();
        createGrid();
    }

    public abstract void initializeMyCellGrid(ArrayList<ArrayList<Integer>> row, ArrayList<ArrayList<Integer>> col, String s, int rowSize, int colSize);

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
}

