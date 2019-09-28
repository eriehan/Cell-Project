package simulation.AntForaging;

import simulation.GridInfo;
import simulation.Individual;
import utils.Point;

import java.util.ArrayList;
import java.util.List;

public class Ant extends Individual{

    private static final int LIFETIME = 500;

    private int curDirection = -1;
    private List<Point> directions = new ArrayList<>();
    private int lifeTime = LIFETIME;
    private int survivedTime = 0;
    private boolean hasFoodItem;

    public Ant(GridInfo gridInfo, List<Point> orderedDirections) {
        super(gridInfo);
        directions.addAll(orderedDirections);
        //if this is initialized as false, it will change this state to true inside
        //its home.
        hasFoodItem = false;
    }

    @Override
    public void move() {
        //to be added.

        //algorithm for moving
        dropFood();
        survivedTime++;
        if(lifeTime < survivedTime) {
            setDead(true);
        }
    }

    private GridInfo getGridInfo(int index) {
        if(index < 0) {
            index += directions.size();
        } else if(index >= directions.size()) {
            index -= directions.size();
        }
        return getMyGridInfo().getOneNeighborGrid(directions.get(index));
    }

    private List<GridInfo> getInitialThree() {
        return getNeighborGridInfos(curDirection-1, curDirection+2);
    }

    private List<GridInfo> getRestOfDirections() {
        return getNeighborGridInfos(curDirection+2, curDirection + directions.size()-1);
    }

    private List<GridInfo> getAllNeighbors() {
        return getNeighborGridInfos(0, directions.size());
    }

    private List<GridInfo> getNeighborGridInfos(int startIndex, int lastIndex) {
        List<GridInfo> list = new ArrayList<>();
        for(int i=startIndex; i<lastIndex; i++) {
            list.add(getGridInfo(i));
        }
        return list;
    }

    private void dropFood() {
        if(getMyGridInfo().getBooleanAttribute(GridAttribute.ISFOOD)) {
            hasFoodItem = true;
        } else if(getMyGridInfo().getBooleanAttribute(GridAttribute.ISHOME)) {
            hasFoodItem = false;
        }
    }

    private List<GridInfo> possibleGridsToMove(List<GridInfo> gridInfos) {
        List<GridInfo> list = new ArrayList<>();
        for(GridInfo gridInfo : gridInfos) {
            if(!(gridInfo.getBooleanAttribute(GridAttribute.ISPACKED) || gridInfo.getBooleanAttribute(GridAttribute.ISOBSTACLE))) {
                list.add(gridInfo);
            }
        }
        return list;
    }

    private boolean isPacked(GridInfo gridInfo) {
        return gridInfo.getBooleanAttribute(GridAttribute.ISPACKED);
    }

    private double maxPheromone(GridAttribute gridAttribute) {
        double max = 0;
        for(GridInfo gridInfo : getAllNeighbors()) {
            max = Math.max(max, gridInfo.getNumberAttribute(gridAttribute));
        }
        return max;
    }
}
