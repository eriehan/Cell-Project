package userInterface;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import simulation.*;
import utils.Point;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static userInterface.VisualizationConstants.GRID_HEIGHT;
import static userInterface.VisualizationConstants.GRID_WIDTH;

public class GridView {
    private GridPane myGridPane;
    private final int numOfRows;
    private final int numOfCols;
    private final Color[] paints = {Color.LAVENDER, Color.HONEYDEW, Color.CORNFLOWERBLUE, Color.PLUM};
    private CellGrid myCellGrid;

    public GridView(int numOfRows, int numOfCols) {
        myGridPane = new GridPane();
        this.numOfCols = numOfCols;
        this.numOfRows = numOfRows;

        initializeMyCellGrid();
    }

    // TODO: set with config
    public void createGrid() {
        for (int r = 0; r < numOfRows; r++) {
            for (int c = 0; c < numOfCols; c++) {
                Rectangle shape = new Rectangle(GRID_WIDTH / numOfCols, GRID_HEIGHT / numOfRows);
                shape.setFill(myCellGrid.stateOfCellAtPoint(r, c).getMyColor());
                myGridPane.add(shape, c, r);
            }
        }
        /*Random ran = new Random();
        for (int r = 0; r < numOfRows; r++) {
            for (int c = 0; c < numOfCols; c++) {
                Rectangle shape = new Rectangle(GRID_WIDTH / numOfCols, GRID_HEIGHT / numOfRows);
                shape.setFill(paints[ran.nextInt(4)]);
                myGridPane.add(shape, r, c);
            }
        }*/
    }

    private void initializeMyCellGrid() {
        Map configMap = new HashMap<Point, CellState>();
        //hardcode to see if game of life works
        /*configMap.put(new Point(4, 4), CellState.ALIVE);
        configMap.put(new Point(5, 4), CellState.ALIVE);
        configMap.put(new Point(6, 4 ), CellState.ALIVE);
        configMap.put(new Point(6, 3), CellState.ALIVE);
        configMap.put(new Point(5, 2), CellState.ALIVE);
        myCellGrid = new GameOfLifeCellGrid( numOfRows,  numOfCols);*/

        //hardcode to see if fire works
        /*configMap.put(new Point(3, 4), CellState.BURNING);
        configMap.put(new Point(3, 5), CellState.BURNING);
        configMap.put(new Point(6, 7), CellState.BURNING);
        myCellGrid = new FireCellGrid(numOfRows, numOfCols, 30);*/


        //hardcode to see if segregation works

        configMap.put(new Point(0, 1), CellState.FIRSTAGENT);
        configMap.put(new Point(0, 2), CellState.FIRSTAGENT);
        configMap.put(new Point(0, 4), CellState.SECONDAGENT);
        configMap.put(new Point(0, 5), CellState.SECONDAGENT);
        configMap.put(new Point(1, 0), CellState.FIRSTAGENT);
        configMap.put(new Point(1, 1), CellState.SECONDAGENT);
        configMap.put(new Point(1, 3), CellState.SECONDAGENT);
        configMap.put(new Point(1, 4), CellState.FIRSTAGENT);
        configMap.put(new Point(1, 5), CellState.FIRSTAGENT);
        configMap.put(new Point(2, 1), CellState.FIRSTAGENT);
        configMap.put(new Point(2, 3), CellState.FIRSTAGENT);
        configMap.put(new Point(2, 4), CellState.SECONDAGENT);
        configMap.put(new Point(2, 5), CellState.SECONDAGENT);
        configMap.put(new Point(3, 0), CellState.SECONDAGENT);
        configMap.put(new Point(3, 1), CellState.FIRSTAGENT);
        configMap.put(new Point(3, 2), CellState.FIRSTAGENT);
        configMap.put(new Point(3, 3), CellState.SECONDAGENT);
        configMap.put(new Point(3, 4), CellState.SECONDAGENT);
        configMap.put(new Point(3, 5), CellState.FIRSTAGENT);
        configMap.put(new Point(4, 0), CellState.FIRSTAGENT);
        configMap.put(new Point(4, 2), CellState.SECONDAGENT);
        configMap.put(new Point(4, 3), CellState.FIRSTAGENT);
        configMap.put(new Point(4, 5), CellState.SECONDAGENT);
        configMap.put(new Point(5, 1), CellState.FIRSTAGENT);
        configMap.put(new Point(5, 3), CellState.FIRSTAGENT);
        configMap.put(new Point(5, 4), CellState.SECONDAGENT);
        configMap.put(new Point(5, 5), CellState.FIRSTAGENT);
        myCellGrid = new SegregationCellGrid(numOfRows, numOfCols, 50, 12);


        //hardcode to see if percolation works
        /*configMap.put(new Point(0, 1), CellState.BLOCKED);
        configMap.put(new Point(0, 4), CellState.BLOCKED);
        configMap.put(new Point(0, 5), CellState.BLOCKED);
        configMap.put(new Point(0, 7), CellState.BLOCKED);
        configMap.put(new Point(1, 2), CellState.BLOCKED);
        configMap.put(new Point(1, 5), CellState.BLOCKED);
        configMap.put(new Point(2, 0), CellState.BLOCKED);
        configMap.put(new Point(2, 1), CellState.BLOCKED);
        configMap.put(new Point(2, 3), CellState.BLOCKED);
        configMap.put(new Point(2, 6), CellState.BLOCKED);
        configMap.put(new Point(2, 7), CellState.BLOCKED);
        configMap.put(new Point(3, 0), CellState.BLOCKED);
        configMap.put(new Point(3, 3), CellState.BLOCKED);
        configMap.put(new Point(3, 6), CellState.BLOCKED);
        configMap.put(new Point(4, 0), CellState.PERCOLATED);
        configMap.put(new Point(4, 4), CellState.BLOCKED);
        configMap.put(new Point(4, 7), CellState.BLOCKED);
        configMap.put(new Point(5, 0), CellState.BLOCKED);
        configMap.put(new Point(5, 2), CellState.BLOCKED);
        configMap.put(new Point(5, 4), CellState.BLOCKED);
        configMap.put(new Point(5, 5), CellState.BLOCKED);
        configMap.put(new Point(6, 0), CellState.BLOCKED);
        configMap.put(new Point(6, 4), CellState.BLOCKED);
        configMap.put(new Point(6, 5), CellState.BLOCKED);
        configMap.put(new Point(6, 6), CellState.BLOCKED);
        configMap.put(new Point(7, 2), CellState.BLOCKED);
        configMap.put(new Point(7, 3), CellState.BLOCKED);
        configMap.put(new Point(7, 4), CellState.BLOCKED);
        myCellGrid = new PercolationCellGrid(numOfRows, numOfCols); */


        myCellGrid.initializeGrids(configMap);
        myCellGrid.assignNeighborsToEachCell();

    }

    public void generateBlankGrid() {
        Rectangle shape = new Rectangle(GRID_WIDTH, GRID_HEIGHT);
        shape.setFill(Color.LIGHTBLUE);
        myGridPane.add(shape, 0,0);
    }

    // TODO: set with config
    public void updateGrid() {
        myGridPane.getChildren().clear();
        myCellGrid.checkAllCells();
        myCellGrid.changeAllCells();
        createGrid();
    }

    public GridPane getMyGridPane() {
        return myGridPane;
    }
}

