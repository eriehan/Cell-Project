package simulation;

import utils.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class RockPaperScissorGrid extends GameOfLifeCellGrid {

    private static final double HUNDRED = 100.0;
    private final List<CellState> STATES_LIST = Arrays.asList(CellState.ROCK, CellState.PAPER, CellState.SCISSOR);
    //first index -> # of
    private List<Integer> possibilities;
    private int threshold;
    private List<Point> rockList = new ArrayList<>();


    //possibilities -> possibility of rock, paper, scissor
    public RockPaperScissorGrid(int numRows, int numCols, int threshold, List<Integer> possibilities) {
        super(numRows, numCols);

        this.threshold = threshold;
        this.possibilities = new ArrayList<>();
        this.possibilities.addAll(possibilities);
    }

    @Override
    public void initializeGrids(Map<Point, CellState> configMap) {
        createRockMap();
        //If xml already has the whole configuration, grid will be filled accordingly

        fillUnfilledGrids();
    }

    @Override
    public void changeAllCells() {
        for (Cell cell : getGridOfCells().values()) {
            cell.changeState();
        }
    }

    @Override
    public void addEmptyStateToCell(int row, int col) {
        Point point = new Point(row, col);
        addToGridOfCells(point, new RockPaperScissorCell(row, col, CellState.ROCK, threshold));
        rockList.add(point);
    }

    private void createRockMap() {
        getGridOfCells().clear();
        for (int row = 0; row < getNumOfRows(); row++) {
            for (int col = 0; col < getNumOfCols(); col++) {
                addEmptyStateToCell(row, col);
            }
        }
    }

    private void fillUnfilledGrids() {
        int wholePossibility = sumOfList(possibilities);
        for (Point point : rockList) {
            double ran = Math.random() * wholePossibility;
            int index = 0;
            int num = 0;
            for (int i = 0; i < possibilities.size(); i++) {
                num += possibilities.get(i);
                if (ran < num) {
                    break;
                }
                index++;
            }
            getGridOfCells().get(point).setState(STATES_LIST.get(index));
        }
    }

    private int sumOfList(List<Integer> list) {
        int count = 0;
        for (int num : list) {
            count += num;
        }
        return count;
    }

}
