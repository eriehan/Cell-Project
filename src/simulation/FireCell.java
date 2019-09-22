package simulation;

public class FireCell extends Cell {

    private static final CellAttribute PROBCATCH = CellAttribute.PROBCATCH;
    private static final CellState TREE = CellState.TREE;
    private static final CellState FIREEMPTY = CellState.FIREEMPTY;
    private static final CellState BURNING = CellState.BURNING;

    private double probCatch;

    public FireCell(int row, int col, CellState state, double probCatch) {
        super(row, col, state);
        this.probCatch = probCatch;
        putAttribute(PROBCATCH, (int) probCatch);
    }

    @Override
    public void check() {
        if(getState() == CellState.TREE) {
            if(isFireNearby() && Math.random() * 100 <= probCatch) { setNextState(BURNING);}
            else { setNextState(CellState.TREE); }
        }
        else {setNextState(FIREEMPTY);}
    }

    @Override
    public void changeState() { setState(getNextState()); }

    private boolean isFireNearby() {
        for (Cell neighbor : getEdgeNeighbor()) {
            if (neighbor.getState()== CellState.BURNING) { return true; }
        }
        return false;
    }
}
