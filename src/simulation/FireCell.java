package simulation;

public class FireCell extends Cell {

    private double probCatch;
    private boolean fireNearby;

    public FireCell(int row, int col, CellState state, double probCatch) {
        super(row, col, state);
        this.probCatch = probCatch;
    }

    @Override
    public void check() {
        //if already burnt or in fire, will be empty at next round.
        if(getState()== CellState.FIREEMPTY) {setNextState(CellState.FIREEMPTY);}
        else if(getState()== CellState.BURNING) {setNextState(CellState.FIREEMPTY);}

        else {
            for (Cell neighbor : getEdgeNeighbor()) {
                if (neighbor.getState()== CellState.BURNING) {
                    fireNearby = true; break;
                }
            }
            if(fireNearby) {
                int num = (int) (Math.random() * 100);
                System.out.println(num);
                if(num <= probCatch) {setNextState(CellState.BURNING);}
            } else {
                setNextState(CellState.TREE);
            }
        }
    }

    @Override
    public void changeState() {
        fireNearby = false;
        setState(getNextState());
    }
}
