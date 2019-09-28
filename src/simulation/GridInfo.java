package simulation;

import simulation.AntForaging.GridAttribute;
import utils.Point;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class GridInfo {
    private int row;
    private int col;
    private Map<Point, GridInfo> neighborGrids;
    private Map<GridAttribute, Double> gridAttributes = new HashMap<>();
    private Map<GridAttribute, Boolean> gridBooleans = new HashMap<>();
    private List<Individual> individuals = new ArrayList<>();
    private List<Individual> removedIndividuals = new ArrayList<>();

    public GridInfo(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public abstract void createIndividual();

    public abstract void moveIndividuals();

    public abstract void update();

    public void putNumberAttributes(GridAttribute gridAttribute, double num) {
        gridAttributes.put(gridAttribute, num);
    }

    public void putBooleanAttributes(GridAttribute gridAttribute, boolean bool) {
        gridBooleans.put(gridAttribute, bool);
    }

    public void multiplyNumberAttributes(GridAttribute gridAttribute, double num) {
        if (gridAttributes.containsKey(gridAttribute)) {
            gridAttributes.put(gridAttribute, num * gridAttributes.get(gridAttribute));
        }
    }

    public void addToNumberAttributes(GridAttribute gridAttribute, double num) {
        if (!gridAttributes.containsKey(gridAttribute)) {
            putNumberAttributes(gridAttribute, num);
        } else {
            gridAttributes.put(gridAttribute, num + gridAttributes.get(gridAttribute));
        }
    }

    public double getNumberAttribute(GridAttribute gridAttribute) {
        return gridAttributes.get(gridAttribute);
    }

    public boolean getBooleanAttribute(GridAttribute gridAttribute) {
        return gridBooleans.get(gridAttribute);
    }

    public void addNeigbor(Point dir, GridInfo gridInfo) {
        neighborGrids.put(dir, gridInfo);
    }

    public boolean hasNeigborGrid(Point direction) {
        return neighborGrids.containsKey(direction);
    }

    public GridInfo getOneNeighborGrid(Point direction) {
        return neighborGrids.get(direction);
    }

    public List<GridInfo> getNeighborGrids() {
        List<GridInfo> grids = new ArrayList<>();
        grids.addAll(neighborGrids.values());
        return grids;
    }

    public int numOfIndividuals() {return individuals.size();}

    public List<Individual> getIndividuals() {
        List<Individual> list = new ArrayList<>();
        list.addAll(individuals);
        return list;
    }

    public void removeAllRemovedIndividuals() {
        individuals.removeAll(removedIndividuals);
        removedIndividuals.clear();
    }

    public void addRemovedIndividual(Individual individual) {
        removedIndividuals.add(individual);
    }

    public void addIndividual(Individual individual) {
        individuals.add(individual);
    }

    public boolean neighborAssigned() {
        return !individuals.isEmpty();
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    @Override
    public boolean equals(Object other) {
        return getClass().equals(other.getClass()) && hashCode() == other.hashCode();
    }

    @Override
    public int hashCode() {
        return (new Point(row, col).toString() + "gridInfo").hashCode();
    }
}
