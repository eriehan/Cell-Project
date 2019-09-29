package simulation.AntForaging;

import simulation.CellState;
import simulation.CellWithInfo;
import simulation.GridInfo;
import utils.Point;
import java.util.List;

public class AntForagingCell extends CellWithInfo {

    private static final int BIRTHRATE = 2;

    //also a parameter
    private int birthrate = BIRTHRATE;
    private GridInfo myGridInfo;

    public AntForagingCell(int row, int col, CellState state, int maxAnt, double evaporation, double diffusion) {
        super(row, col, state);
        myGridInfo.putNumberAttributes(GridAttribute.EVAPORATION, evaporation);
        myGridInfo.putNumberAttributes(GridAttribute.DIFFUSION, diffusion);
        myGridInfo.putNumberAttributes(GridAttribute.MAXANT, maxAnt);
        assignBooleanValues();
    }

    @Override
    public void check() {
        //to be added. Have not figured out how to implement.
       myGridInfo.moveIndividuals();
       if(getState() == CellState.HOME) {
           createAnts();
       }
    }

    @Override
    public void changeState() {
        //to be added. Have not figured out how to implement.
        setState(getNextState());

        myGridInfo.update();
    }

    @Override
    public void createMyGridInfo(List<Point> directions) {
        myGridInfo = new AntGridInfo(getRow(), getCol(), directions);
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
        myGridInfo.putBooleanAttributes(GridAttribute.ISOBSTACLE, obstacle);
        myGridInfo.putBooleanAttributes(GridAttribute.ISHOME, home);
        myGridInfo.putBooleanAttributes(GridAttribute.ISFOOD, food);
        myGridInfo.putBooleanAttributes(GridAttribute.ISPACKED, packed);
    }
}
