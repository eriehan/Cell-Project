package simulation;

import utils.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WaTorCellGrid extends GameOfLifeCellGrid {
    private List<Integer> reproductions;
    private List<Integer> energies;
    private List<Cell> fishes = new ArrayList<>();
    private List<Cell> sharks = new ArrayList<>();

    //reproductions is a list of time needed for fish and shark to reproduce
    //energies.get(0) = shark's initial energy, energies.get(1) = energy that shark gets by eating fish.
    public WaTorCellGrid(int numRows, int numCols, List<Integer> reproductions, List<Integer> energies) {
        super(numRows, numCols);
        this.reproductions = reproductions;
        this.energies = energies;
    }

    @Override
    public void initializeGrids(Map<Point, CellState> configMap) {
        createEmptyMap();
        for(Map.Entry<Point, CellState> entry : configMap.entrySet()) {
            Cell cell =  getGridOfCells().get(entry.getKey());
            CellState state = entry.getValue();
            cell.setState(state);

            if(cell instanceof WaTorCell) {
                WaTorCell waTorCell = (WaTorCell) cell;
                if (state == CellState.FISH) {
                    waTorCell.setReproduce(reproductions.get(0));
                    waTorCell.setEnergy(energies.get(0));
                } else if (state == CellState.SHARK) {
                    waTorCell.setReproduce(reproductions.get(1));
                    waTorCell.setEnergy(energies.get(1));
                }
            }
        }
    }

    @Override
    public void checkAllCells() {
        findFishesAndSharks();
        for(Cell fish : fishes) {
            fish.check();
        } for(Cell shark : sharks) {
            shark.check();
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
                addToGridOfCells(point, new WaTorCell(row, col, CellState.EMPTY, 0,0));
            }
        }
    }

    private void findFishesAndSharks() {
        sharks.clear();
        fishes.clear();
        for(Cell cell : getGridOfCells().values()) {
            if (cell.getState() == CellState.SHARK) {
                sharks.add(cell);
            } else if(cell.getState() == CellState.FISH) {
                fishes.add(cell);
            }
        }
    }
}
