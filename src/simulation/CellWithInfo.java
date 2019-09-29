package simulation;

import utils.Point;

import java.util.List;
import java.util.Map;

public abstract class CellWithInfo extends Cell {
    private GridInfo myGridInfo;

    public CellWithInfo(int row, int col, CellState state) {
        super(row, col, state);
    }

    @Override
    public void check() {
        if (!myGridInfo.neighborAssigned()) {
            addNeighborsToMyGridInfo();
        }
    }

    @Override
    public void changeState() {
        setState(getNextState());
        myGridInfo.update();
    }

    public abstract void addOrderedNeighborDirections(List<Point> neighbors);

    public GridInfo getMyGridInfo() {return myGridInfo;}

    public void setMyGridInfo(GridInfo gridInfo) {
        myGridInfo = gridInfo;
    }

    protected void addNeighborsToMyGridInfo() {
        System.out.println(getNeighbors().size());
        Map<Point, Cell> myNeighbors = getNeighbors();

        for (Point direction : myNeighbors.keySet()) {
            Cell cell = myNeighbors.get(direction);

            //will remove this casting part later.
            if (!(cell instanceof CellWithInfo)) {
                return;
            }
            CellWithInfo cellWithInfo = (CellWithInfo) cell;
            myGridInfo.addNeighbor(direction, cellWithInfo.getMyGridInfo());
        }
    }
}
