package simulation.AntForaging;

import simulation.GridInfo;
import simulation.Individual;
import utils.Point;

import java.util.ArrayList;
import java.util.List;

public class Ant extends Individual{
    private int curDirection;
    private List<Point> directions = new ArrayList<>();
    private int lifeTime;
    private int survivedTime;

    public Ant(GridInfo gridInfo, List<Point> orderedDirections) {
        super(gridInfo);
        directions.addAll(orderedDirections);
    }

    @Override
    public void move() {
        //to be added.
    }

    private boolean canMove() {
        return true;
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
        List<GridInfo> list = new ArrayList<>();
        list.add(getGridInfo(curDirection-1));
        list.add(getGridInfo(curDirection));
        list.add(getGridInfo(curDirection+1));
        return list;
    }

    private List<GridInfo> getRestOfDirections() {
        List<GridInfo> list = new ArrayList<>();
        for(int i=curDirection+2; i<curDirection+directions.size()-1; i++) {
            list.add(getGridInfo(i));
        }
        return list;
    }
}
