package userInterface;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import simulation.CellGrid;
import simulation.CellState;
import simulation.SegregationCellGrid;
import utils.Point;

import java.util.HashMap;
import java.util.Map;

import static userInterface.VisualizationConstants.GRID_HEIGHT;
import static userInterface.VisualizationConstants.GRID_WIDTH;

public abstract class AbstractGridView {
    private GridPane myGridPane;
    private final int numOfRows;
    private final int numOfCols;
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

    public GridPane getMyGridPane() {
        return myGridPane;
    }

    public int getNumOfRows() {
        return numOfRows;
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

