package simulation.AntForaging;

import simulation.Cell;
import simulation.CellState;
import simulation.CellWithInfo;
import simulation.GridInfo;
import utils.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AntForagingCell extends CellWithInfo {

    private static final int BIRTHRATE = 2;

    //also a parameter
    private int birthrate = BIRTHRATE;
    private GridInfo myGridInfo;
    private double evaporation;
    private double diffusion;
    private int maxAnt;

    public AntForagingCell(int row, int col, CellState state, int maxAnt, double evaporation, double diffusion, List<Point> directions) {
        super(row, col, state);
        createMyGridInfo(directions);
        myGridInfo.putNumberAttributes(GridAttribute.EVAPORATION, evaporation);
        myGridInfo.putNumberAttributes(GridAttribute.DIFFUSION, diffusion);
        myGridInfo.putNumberAttributes(GridAttribute.MAXANT, maxAnt);
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

    private void createMyGridInfo(List<Point> directions) {
        myGridInfo = new AntGridInfo(getRow(), getCol(), directions);
    }

    private void createAnts() {
        for(int i=0; i<birthrate; i++) {
            getMyGridInfo().createIndividual();
        }
    }
}
