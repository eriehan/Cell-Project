package simulation.AntForaging;

import simulation.CellState;
import simulation.CellWithInfo;
import utils.Point;

import java.util.Arrays;
import java.util.List;

public class AntForagingCell extends CellWithInfo {

    public static final List<CellState> STATES_LIST = Arrays.asList(CellState.EMPTY, CellState.HOME, CellState.FOOD,
            CellState.ANT, CellState.ANTFULL);
    private static final int BIRTHRATE = 2;

    //also a parameter
    private int birthrate = BIRTHRATE;

    public AntForagingCell(int row, int col, CellState state, int maxAnt, double evaporation, double diffusion) {
        super(row, col, state);
        setMyGridInfo(new AntGridInfo(row, col));
        getMyGridInfo().putNumberAttributes(GridAttribute.EVAPORATION, evaporation);
        getMyGridInfo().putNumberAttributes(GridAttribute.DIFFUSION, diffusion);
        getMyGridInfo().putNumberAttributes(GridAttribute.MAXANT, maxAnt);
        assignBooleanValues();
    }

    @Override
    public void check() {
        if(getMyGridInfo().getNeighborGrids().isEmpty()) {
            System.out.println("sdf");
            addNeighborsToMyGridInfo();
        }
        //to be added. Have not figured out how to implement.
       getMyGridInfo().moveIndividuals();
       if(getState() == CellState.HOME) {
           createAnts();
       }
    }

    @Override
    public void changeState() {
        //to be added. Have not figured out how to implement.

        if(getState() == CellState.HOME || getState() == CellState.FOOD) {
            setNextState(getState());
        }
        else if(getMyGridInfo().numOfIndividuals() <= 0) {
            setNextState(CellState.EMPTY);
        }
        else if(getMyGridInfo().numOfIndividuals() >= getMyGridInfo().getNumberAttribute(GridAttribute.MAXANT)) {
            setNextState(CellState.ANTFULL);
        } else {
            setNextState(CellState.ANT);
        }
        super.changeState();
    }

    @Override
    public void addOrderedNeighborDirections(List<Point> directions) {
        getMyGridInfo().setPossibleOrderedDirections(directions);
    }

    private void createAnts() {
        for(int i=0; i<birthrate; i++) {
            getMyGridInfo().createIndividual();
        }
    }

    private void assignBooleanValues() {
        CellState state = getState();
        putBooleanValues(state==CellState.OBSTACLE, state == CellState.HOME,
                state == CellState.FOOD, false);
    }

    private void putBooleanValues(boolean obstacle, boolean home, boolean food, boolean packed) {
        getMyGridInfo().putBooleanAttributes(GridAttribute.ISOBSTACLE, obstacle);
        getMyGridInfo().putBooleanAttributes(GridAttribute.ISHOME, home);
        getMyGridInfo().putBooleanAttributes(GridAttribute.ISFOOD, food);
        getMyGridInfo().putBooleanAttributes(GridAttribute.ISPACKED, packed);
    }
}
