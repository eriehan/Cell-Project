package simulation;

import utils.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SegregationCellGrid extends GameOfLifeCellGrid {

    private double agentPercent;
    private double prob = 0.5;
    private int emptyCellNumber;
    private List<Cell> emptyCells = new ArrayList<>();
    private List<Cell> dissatisfiedCells = new ArrayList<>();

    //more parameters to be added :
    public SegregationCellGrid(int numRows, int numCols, double agentPercent, double emptyPercent) {
        super(numRows, numCols);
        this.agentPercent = agentPercent;
        emptyCellNumber = (int) (numRows * numCols * emptyPercent/100);
    }

    @Override
    public void initializeGrids(Map<Point, CellType> configMap) {
        createEmptyMap();
        //If xml already has the whole configuration, grid will be filled accordingly
        for(Map.Entry<Point, CellType> entry : configMap.entrySet()) {
            Cell notEmptyCell = getGridOfCells().get(entry.getKey());
            emptyCells.remove(notEmptyCell);
            notEmptyCell.setState(entry.getValue());
        }

        //If xml does not have the configuration so that all configuration will be randomly(while loop will be in an if statement),
        //according to the percentage of empty cells(parameter of constructor), randomly fills grid.
        while(emptyCells.size() > emptyCellNumber) {
            int index = (int) (emptyCells.size() * Math.random());
            Cell tempCell = emptyCells.get(index);
            emptyCells.remove(tempCell);

            //Will be changed later to be more flexible. Right now, 50% for state1, 50% for state2.
//            tempCell.setState((int) (1.5 + Math.random()));
            tempCell.setState((Math.random()>this.prob)?CellType.FIRSTAGENT:CellType.SECONDAGENT);
        }
    }

    @Override
    public void checkAllCells() {
        for(Cell cell : getGridOfCells().values()) {
            cell.check();
            //if dissatisfied
            if(cell.getNextState() == CellType.DISATISFIED) {
                dissatisfiedCells.add(cell);
            }
        }
    }

    @Override
    public void changeAllCells() {
        //swapping dissatisfied ones with a random empty one
        for(Cell dissatisfiedCell : dissatisfiedCells) {
            Cell emptyCell = emptyCells.get((int) (Math.random() * emptyCellNumber));
            emptyCells.remove(emptyCell);
            dissatisfiedCells.remove(dissatisfiedCell);

            dissatisfiedCell.moveToDifferentCell(emptyCell);
            emptyCells.add(dissatisfiedCell);
        }

        super.changeAllCells();
    }

    private void createEmptyMap() {
        getGridOfCells().clear();
        for(int row = 0; row < getNumOfRows(); row++) {
            for (int col = 0; col < getNumOfCols(); col++) {
                Point point = new Point(row, col);
                Cell segregationCell = new SegregationCell(row, col, CellType.EMPTY, agentPercent);
                addToGridOfCells(point, segregationCell);
                emptyCells.add(segregationCell);
            }
        }
    }
}
