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
    private List<Cell> newEmptyCells = new ArrayList<>();

    //more parameters to be added :
    public SegregationCellGrid(int numRows, int numCols, double agentPercent, double emptyPercent) {
        super(numRows, numCols);
        this.agentPercent = agentPercent;
        emptyCellNumber = (int) (numRows * numCols * emptyPercent/100);
    }

    @Override
    public void initializeGrids(Map<Point, CellState> configMap) {
        createEmptyMap();
        //If xml already has the whole configuration, grid will be filled accordingly
        for(Map.Entry<Point, CellState> entry : configMap.entrySet()) {
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
            //tempCell.setState((int) (1.5 + Math.random()));
            tempCell.setState((Math.random()>this.prob)? CellState.FIRSTAGENT: CellState.SECONDAGENT);
        }
    }

    @Override
    public void checkAllCells() {
        for(Cell cell : getGridOfCells().values()) {
            cell.check();
            //if dissatisfied
            if(cell.getNextState() == CellState.DISATISFIED) {
                System.out.println(cell.getRow() + ", " + cell.getCol());
                dissatisfiedCells.add(cell);
            }
        }
        System.out.println(dissatisfiedCells.size());
    }

    @Override
    public void changeAllCells() {
        //swapping dissatisfied ones with a random empty one
        while(!dissatisfiedCells.isEmpty()) {
            Cell dissatisfiedCell = dissatisfiedCells.get(0);
            Cell emptyCell = (!emptyCells.isEmpty())? emptyCells.get((int) (Math.random() * emptyCells.size()))
                    : newEmptyCells.get((int) (Math.random() * newEmptyCells.size()));

            if(!emptyCells.isEmpty()) {emptyCells.remove(emptyCell);}
            else {newEmptyCells.remove(emptyCell);}
            dissatisfiedCells.remove(dissatisfiedCell);
            newEmptyCells.add(dissatisfiedCell);

            dissatisfiedCell.moveToDifferentCell(emptyCell);
        }

        super.changeAllCells();

        dissatisfiedCells.clear();
        emptyCells.addAll(newEmptyCells);
        newEmptyCells.clear();
    }

    private void createEmptyMap() {
        getGridOfCells().clear();
        for(int row = 0; row < getNumOfRows(); row++) {
            for (int col = 0; col < getNumOfCols(); col++) {
                Point point = new Point(row, col);
                Cell segregationCell = new SegregationCell(row, col, CellState.EMPTY, agentPercent);
                addToGridOfCells(point, segregationCell);
                emptyCells.add(segregationCell);
            }
        }
    }
}