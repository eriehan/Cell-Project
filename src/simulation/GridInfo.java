package simulation;

import simulation.AntForaging.GridAttribute;
import utils.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class GridInfo {
    private int row;
    private int col;
    private Map<Point, GridInfo> neighborGrids = new HashMap<>();
    private Map<GridAttribute, Double> gridAttributes = new HashMap<>();
    private Map<GridAttribute, Boolean> gridBooleans = new HashMap<>();
    private List<Individual> individuals = new ArrayList<>();
    private List<Individual> removedIndividuals = new ArrayList<>();
    private List<Point> possibleOrderedDirections = new ArrayList<>();

    public GridInfo(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public abstract void createIndividual();

    public abstract void moveIndividuals();

    public abstract void update();

    /**
     * This method needs to be called. Otherwise, the gridInfo won't know which neighbor is
     * in which direction.
     *
     * @param  orderedDirections is an ordered list of directions of neighbors. Can be obtained by getOrderedNeighborDirections() in
     *                           NeighborManager class.
     */
    public void setPossibleOrderedDirections(List<Point> orderedDirections) {
        List<Point> list = new ArrayList<>();
        list.addAll(orderedDirections);
        possibleOrderedDirections = list;
    }

    public List<Point> getPossibleOrderedDirections() {
        List<Point> list = new ArrayList<>();
        list.addAll(possibleOrderedDirections);
        return list;
    }

    /**
     * Method to put the value of specific parameters of each individual GridInfo class. This method was made
     * to prevent casting
     *
     * @param gridAttribute is the GridAttribute object that represents the name of the parameter
     * @param num is the value to be stored.
     */
    public void putNumberAttributes(GridAttribute gridAttribute, double num) {
        gridAttributes.put(gridAttribute, num);
    }

    public void putBooleanAttributes(GridAttribute gridAttribute, boolean bool) {
        gridBooleans.put(gridAttribute, bool);
    }

    /**
     * Method to multiply to the value of specific parameters of each individual GridInfo class. This method was made
     * to prevent casting
     *
     * @param gridAttribute is the GridAttribute object that represents the name of the parameter
     * @param num is the value to be multiplied by.
     */
    public void multiplyNumberAttributes(GridAttribute gridAttribute, double num) {
        if (gridAttributes.containsKey(gridAttribute)) {
            gridAttributes.put(gridAttribute, num * getNumberAttribute(gridAttribute));
        } else {
            gridAttributes.put(gridAttribute, 0.0);
        }
    }

    /**
     * Method to add to the value of specific parameters of each individual GridInfo class. This method was made
     * to prevent casting
     *
     * @param gridAttribute is the GridAttribute object that represents the name of the parameter
     * @param num is the value to be added by.
     */
    public void addToNumberAttributes(GridAttribute gridAttribute, double num) {
        if (!gridAttributes.containsKey(gridAttribute)) {
            putNumberAttributes(gridAttribute, num);
        } else {
            gridAttributes.put(gridAttribute, num + getNumberAttribute(gridAttribute));
        }
    }

    public double getNumberAttribute(GridAttribute gridAttribute) {
        if(!gridAttributes.containsKey(gridAttribute)) {
            return 0;
        }
        return gridAttributes.get(gridAttribute);
    }

    public boolean getBooleanAttribute(GridAttribute gridAttribute) {
        if(!gridBooleans.containsKey(gridAttribute)) {
            return false;
        }
        return gridBooleans.get(gridAttribute);
    }

    /**
     * Method to add a neighbor gridInfo with the corresponding direction.
     *
     * @param dir is the direction of the neighbor
     * @param gridInfo is the gridInfo that is the neighbor
     */
    public void addNeighbor(Point dir, GridInfo gridInfo) {
        neighborGrids.put(dir, gridInfo);
    }

    public GridInfo getOneNeighborGrid(Point direction) {
        return neighborGrids.get(direction);
    }

    public List<GridInfo> getNeighborGrids() {
        List<GridInfo> grids = new ArrayList<>();
        grids.addAll(neighborGrids.values());
        return grids;
    }

    public int numOfIndividuals() {
        return individuals.size();
    }

    public List<Individual> getIndividuals() {
        List<Individual> list = new ArrayList<>();
        list.addAll(individuals);
        return list;
    }

    /**
     * removes all the individuals that will not exist in the next round.
     */
    public void removeAllRemovedIndividuals() {
        individuals.removeAll(removedIndividuals);
        removedIndividuals.clear();
    }

    /**
     * Individual object in the parameter will be removed when removeAllRemovedIndividuals() is called.
     */
    public void addRemovedIndividual(Individual individual) {
        removedIndividuals.add(individual);
    }

    /**
     * Store the individual object in the parameter
     */
    public void addIndividual(Individual individual) {
        individuals.add(individual);
    }

    /**
     * False if neighbor has not been assigned yet
     */
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
