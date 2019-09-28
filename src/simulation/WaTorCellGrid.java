package simulation;

import userInterface.SimulationSlider;
import utils.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class WaTorCellGrid extends GameOfLifeCellGrid {

    private final static CellAttribute ENERGY = CellAttribute.ENERGY;
    private final static CellAttribute INI_ENERGY = CellAttribute.INITIAL_ENERGY;
    private final static CellAttribute SURVIVE = CellAttribute.SURVIVEDTIME;
    private final static CellAttribute REPRODUCE = CellAttribute.REPRODUCTION;

    private List<Integer> reproductions;
    private List<Integer> energies;
    private List<Cell> fishesAndSharks = new ArrayList<>();
    private List<Cell> waterCells = new ArrayList<>();
    private double fishRatio = 80;
    private int waterCellNum = 40;

    //reproductions is a list of time needed for fish and shark to reproduce
    //energies.get(0) = energy that shark gets by eating fish, energies.get(1) = shark's initial energy
    public WaTorCellGrid(int numRows, int numCols, List<Integer> reproductions, List<Integer> energies) {
        super(numRows, numCols);
        this.reproductions = new ArrayList<>(reproductions);
        this.energies = new ArrayList<>(energies);
    }

    @Override
    public void initializeControlPanel() {
        this.getControlPanel().getMyColPane().getChildren().clear();
        String[] controlsList = getControlPanel().getResourceBundle().getString("WaTorControls").split(",");
        for (String controlType : controlsList) {
            SimulationSlider watorSlider = createSliderFromResourceFile(controlType);
            watorSlider.getMySlider().valueProperty().addListener(e -> sliderAction(controlType, (double) Math.round(watorSlider.getMySlider().getValue())));
        }
    }

    private void sliderAction(String type, double inputPercentage) {
        //TODO: added slider actions @Eric
        // type: SharkEnergy,FishEnergy,SharkMaturityDate,FishMaturityDate,WaterPercent,FishSharkRatio
        if(type.equals("SharkEnergy")) {
            energies.set(1, (int) inputPercentage);
        }
        else if(type.equals("FishEnergy")) {
            energies.set(0, (int) inputPercentage);
        }
        else if(type.equals("SharkMaturityDate")) {
            reproductions.set(1, (int) inputPercentage);
        }
        else if(type.equals("FishMaturityDate")) {
            reproductions.set(0, (int) inputPercentage);
        }
        else if(type.equals("WaterPercent")) {
            waterCellNum = (int) (getGridOfCells().size() * inputPercentage / 100);
        } else {
            fishRatio = inputPercentage;
        }

        System.out.println(type);
        waterCells.clear();
        createEmptyMap();
        initializeGrids();
        assignNeighborsToEachCell();
        System.out.println(type);
    }

    @Override
    public void initializeGrids(Map<Point, CellState> configMap) {
        createEmptyMap();
        System.out.println(configMap.size());
        for (Map.Entry<Point, CellState> entry : configMap.entrySet()) {
            Cell cell = getGridOfCells().get(entry.getKey());
            CellState state = entry.getValue();
            changeOneCell(cell, state);
        }
    }

    private void initializeGrids() {
        System.out.println(waterCells.size());
        while (waterCells.size() > waterCellNum) {
            int index = (int) (waterCells.size() * Math.random());
            Cell tempCell = waterCells.get(index);
            waterCells.remove(tempCell);

            //Will be changed later to be more flexible. Right now, 50% for state1, 50% for state2.
            //tempCell.setState((int) (1.5 + Math.random()));
            CellState state = (Math.random() * 100 > fishRatio) ? CellState.SHARK : CellState.FISH;
            changeOneCell(tempCell, state);
        }
        System.out.println(waterCells.size());
    }

    @Override
    public void checkAllCells() {
        findFishesAndSharks();
        for (Cell fishOrShark : fishesAndSharks) {
            fishOrShark.check();
        }
    }

    @Override
    public void changeAllCells() {
        for (Cell cell : getGridOfCells().values()) {
            cell.changeState();
        }
        cellGridExpand();
    }

    @Override
    public void assignNeighborsToEachCell() {
        getGridLimit().assignEdgeNeighbors(getGridOfCells(), getCellShapeType(), getNumOfRows(), getNumOfCols());
        for(Cell cell : getGridOfCells().values()) {
            System.out.println(cell.getNeighbors().size());
        }
    }

    @Override
    public void setStateOfCellAtPointOnClick(int row, int col) {
        Cell cell = getGridOfCells().get(new Point(row, col));
        cell.setNextStateOnClick();
        changeOneCell(cell, cell.getState());
    }

    @Override
    public void addEmptyStateToCell(int row, int col) {
        Point point = new Point(row, col);
        addToGridOfCells(point, new WaTorCell(row, col, CellState.WATER, 0, 0));
        waterCells.add(getGridOfCells().get(point));
    }

    private void createEmptyMap() {
        getGridOfCells().clear();
        for (int row = 0; row < getNumOfRows(); row++) {
            for (int col = 0; col < getNumOfCols(); col++) {
                addEmptyStateToCell(row, col);
            }
        }
    }

    private void findFishesAndSharks() {
        fishesAndSharks.clear();
        for (Cell cell : getGridOfCells().values()) {
            if (cell.getState() == CellState.SHARK || cell.getState() == CellState.FISH) {
                fishesAndSharks.add(cell);
            }
        }
        //shuffle to make more natural effect. Would be unnatural if the sharks move after all the fishes move, or vice versa.
        Collections.shuffle(fishesAndSharks);
    }

    private void assignAttributes(Cell cell, int index) {
        System.out.println(cell.getRow() + ", " + cell.getCol() + ", " + cell.getState());
        cell.putAttribute(SURVIVE, 0);
        cell.putAttribute(REPRODUCE, reproductions.get(index));
        cell.putAttribute(INI_ENERGY, energies.get(index));
        cell.putAttribute(ENERGY, energies.get(index));
    }

    private void changeOneCell(Cell cell, CellState state) {
        cell.setState(state);
        cell.setNextState(state);
        if (state == CellState.FISH) {
            assignAttributes(cell, 0);
        } else if (state == CellState.SHARK) {
            assignAttributes(cell, 1);
        }
    }
}
