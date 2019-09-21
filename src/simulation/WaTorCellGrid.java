package simulation;

import utils.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class WaTorCellGrid extends GameOfLifeCellGrid {

    private final static CellAttribute ENERGY = CellAttribute.ENERGY;
    private final static CellAttribute INI_ENERGY = CellAttribute.INITIAL_ENERGY;
    private final static CellAttribute SURVIVE  = CellAttribute.SURVIVEDTIME;
    private final static CellAttribute REPRODUCE = CellAttribute.REPRODUCTION;

    private List<Integer> reproductions;
    private List<Integer> energies;
    private List<Cell> fishesAndSharks = new ArrayList<>();

    //reproductions is a list of time needed for fish and shark to reproduce
    //energies.get(0) = energy that shark gets by eating fish, energies.get(1) = shark's initial energy
    public WaTorCellGrid(int numRows, int numCols, List<Integer> reproductions, List<Integer> energies) {
        super(numRows, numCols);
        this.reproductions = reproductions;
        this.energies = energies;
    }

    @Override
    public void initializeGrids(Map<Point, CellState> configMap) {
        createEmptyMap();
        System.out.println(configMap.size());
        for(Map.Entry<Point, CellState> entry : configMap.entrySet()) {
            Cell cell =  getGridOfCells().get(entry.getKey());
            CellState state = entry.getValue();
            cell.setState(state);
            cell.setNextState(state);

            if(state == CellState.FISH) { assignAttributes(cell, 0); }
            else if(state == CellState.SHARK) { assignAttributes(cell, 1); }
        }
    }

    @Override
    public void checkAllCells() {
        findFishesAndSharks();
        for(Cell fishOrShark : fishesAndSharks) {
            fishOrShark.check();
        }
    }

    @Override
    public void changeAllCells() {
        for(Cell cell : getGridOfCells().values()) {
            cell.changeState();
        }
    }

    private void createEmptyMap() {
        getGridOfCells().clear();
        for(int row = 0; row < getNumOfRows(); row++) {
            for (int col = 0; col < getNumOfCols(); col++) {
                Point point = new Point(row, col);
                addToGridOfCells(point, new WaTorCell(row, col, CellState.WATER, 0,0));
            }
        }
    }

    private void findFishesAndSharks() {
        fishesAndSharks.clear();
        for(Cell cell : getGridOfCells().values()) {
            if (cell.getState() == CellState.SHARK || cell.getState() == CellState.FISH) {
                fishesAndSharks.add(cell);
            }
        }
        //shuffle to make more natural effect. Would be unnatural if the sharks move after all the fishes move, or vice versa.
        Collections.shuffle(fishesAndSharks);
    }

    private void assignAttributes(Cell cell, int index) {
        System.out.println(cell.getRow() + ", " + cell.getCol() + ", " + cell.getState());
        cell.putAttribute(REPRODUCE, reproductions.get(index));
        cell.putAttribute(INI_ENERGY, energies.get(index));
        cell.putAttribute(ENERGY, energies.get(index));
    }
}
