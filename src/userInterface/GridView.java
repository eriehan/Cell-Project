package userInterface;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import simulation.*;
import utils.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static userInterface.VisualizationConstants.GRID_HEIGHT;
import static userInterface.VisualizationConstants.GRID_WIDTH;

public class GridView extends AbstractGridView {

    public GridView(int numOfRows, int numOfCols) {

        super(numOfRows, numOfCols);

    }

    // TODO: set with config
    public void createGrid() {
        for (int r = 0; r < getNumOfRows(); r++) {
            for (int c = 0; c < getNumOfCols(); c++) {
                Rectangle shape = new Rectangle(GRID_WIDTH / getNumOfCols(), GRID_HEIGHT / getNumOfRows());
                shape.setFill(getMyCellGrid().stateOfCellAtPoint(r, c).getMyColor());
                getMyGridPane().add(shape, c, r);
            }
        }
    }

    public void initializeMyCellGrid(ArrayList<ArrayList<Integer>> row, ArrayList<ArrayList<Integer>> col) {
        Map configMap = new HashMap<Point, CellState>();

        for(int i = 0 ; i < row.size(); i++){
            if(i == 0){
                for(int j = 0; j < row.get(i).size(); j++){
                    configMap.put(new Point(row.get(i).get(j), col.get(i).get(j)), CellState.FIRSTAGENT);
                }
            }
            else{
                for(int j = 0; j < row.get(i).size(); j++){
                    configMap.put(new Point(row.get(i).get(j), col.get(i).get(j)), CellState.SECONDAGENT);
                }
            }


        }



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

        //pass the arraylist of row and col to here and put in configMap


//        //hardcode to see if segregation works
//        configMap.put(new Point(0, 1), CellState.FIRSTAGENT);
//        configMap.put(new Point(0, 2), CellState.FIRSTAGENT);
//        configMap.put(new Point(0, 4), CellState.SECONDAGENT);
//        configMap.put(new Point(0, 5), CellState.SECONDAGENT);
//        configMap.put(new Point(1, 0), CellState.FIRSTAGENT);
//        configMap.put(new Point(1, 1), CellState.SECONDAGENT);
//        configMap.put(new Point(1, 3), CellState.SECONDAGENT);
//        configMap.put(new Point(1, 4), CellState.FIRSTAGENT);
//        configMap.put(new Point(1, 5), CellState.FIRSTAGENT);
//        configMap.put(new Point(2, 1), CellState.FIRSTAGENT);
//        configMap.put(new Point(2, 3), CellState.FIRSTAGENT);
//        configMap.put(new Point(2, 4), CellState.SECONDAGENT);
//        configMap.put(new Point(2, 5), CellState.SECONDAGENT);
//        configMap.put(new Point(3, 0), CellState.SECONDAGENT);
//        configMap.put(new Point(3, 1), CellState.FIRSTAGENT);
//        configMap.put(new Point(3, 2), CellState.FIRSTAGENT);
//        configMap.put(new Point(3, 3), CellState.SECONDAGENT);
//        configMap.put(new Point(3, 4), CellState.SECONDAGENT);
//        configMap.put(new Point(3, 5), CellState.FIRSTAGENT);
//        configMap.put(new Point(4, 0), CellState.FIRSTAGENT);
//        configMap.put(new Point(4, 2), CellState.SECONDAGENT);
//        configMap.put(new Point(4, 3), CellState.FIRSTAGENT);
//        configMap.put(new Point(4, 5), CellState.SECONDAGENT);
//        configMap.put(new Point(5, 1), CellState.FIRSTAGENT);
//        configMap.put(new Point(5, 3), CellState.FIRSTAGENT);
//        configMap.put(new Point(5, 4), CellState.SECONDAGENT);
//        configMap.put(new Point(5, 5), CellState.FIRSTAGENT);


        setMyCellGrid(new SegregationCellGrid(getNumOfRows(), getNumOfCols(), 33, 30));



        getMyCellGrid().initializeGrids(configMap);
        getMyCellGrid().assignNeighborsToEachCell();

    }

}

