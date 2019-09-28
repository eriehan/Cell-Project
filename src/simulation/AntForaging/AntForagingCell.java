package simulation.AntForaging;

import simulation.Cell;
import simulation.CellState;

public class AntForagingCell extends Cell {
    private int numOfAnts;
    private int maxAnt;

    public AntForagingCell(int row, int col, CellState state) {
        super(row, col, state);
    }

    @Override
    public void check() {
        //to be added. Have not figured out how to implement.
    }

    @Override
    public void changeState() {
        //to be added. Have not figured out how to implement.
    }
}
