package simulation;

import java.util.List;

public class WaTorCell extends Cell {

    private static final CellAttribute ENERGY = CellAttribute.ENERGY;
    private static final CellAttribute INI_ENERGY = CellAttribute.INITIAL_ENERGY;
    private static final CellAttribute SURVIVE  = CellAttribute.SURVIVEDTIME;
    private static final CellAttribute REPRODUCE = CellAttribute.REPRODUCTION;
    private static final CellState FISH = CellState.FISH;
    private static final CellState EMPTY = CellState.EMPTY;
    private static final CellState SHARK = CellState.SHARK;

    private boolean moved;

    public WaTorCell(int row, int col, CellState state, int reproduce, int energy) {
        super(row, col, state);

        putAttribute(ENERGY, energy);
        putAttribute(SURVIVE, 0);
        putAttribute(INI_ENERGY, energy);
        putAttribute(REPRODUCE, reproduce);
    }

    //highly important for the fish cells to call this method before the shark cells do.
    //Code is designed so that all the fish move first, and sharks move after that.
    @Override
    public void check() {
        if((getState()==FISH && getNextState()==SHARK) || getState() == EMPTY) {return;}

        if(getState() == FISH) {
            checkAndMoveToNeighbor(EMPTY);
        }
        else if(getState() == SHARK) {
            checkAndMoveToNeighbor(FISH);
            checkAndMoveToNeighbor(EMPTY);
        }

        if(!moved) {
            putAttribute(SURVIVE, getAttribute(SURVIVE)+1);
            if(getState()==SHARK) {
                int energy = getAttribute(ENERGY);
                putAttribute(ENERGY, energy--);
                if(energy==0) {setNextState(EMPTY);}
            }
        }
    }

    private void checkAndMoveToNeighbor(CellState state) {
        int index = (int) (Math.random() * getEdgeNeighbor().size());
        List<Cell> neighbors = getEdgeNeighbor();
        for (int i = index; i < index + neighbors.size(); i++) {
            Cell other = neighbors.get(i % neighbors.size());
            if (other.getNextState() == state) {
                moveToDifferentCell(other);
                moved = true;
            }
        }
    }

    @Override
    public void changeState() {
        setState(getNextState());
        moved = false;
    }

    @Override
    public void moveToDifferentCell(Cell other) {
        if (getState()==CellState.EMPTY) {return;}
        int energy = getAttribute(ENERGY);

        setNextState(other.getNextState());
        other.setNextState(getState());

        if (getState() == CellState.SHARK) {
            if (other.getNextState() == CellState.FISH) { energy += other.getAttribute(ENERGY); setNextState(EMPTY);}
            energy--;
            if(energy==0) {
                other.setNextState(EMPTY);
            }
        }

        other.putAttribute(ENERGY, energy);
        other.putAttribute(REPRODUCE, getAttribute(REPRODUCE));
        other.putAttribute(INI_ENERGY, getAttribute(INI_ENERGY));
        other.putAttribute(SURVIVE, getAttribute(SURVIVE)+1);

        if(other.getAttribute(SURVIVE) > getAttribute(REPRODUCE)) {
            setNextState(getState());
            putAttribute(ENERGY, getAttribute(INI_ENERGY));
            other.putAttribute(SURVIVE, 0);
        } else {setNextState(CellState.EMPTY);}

        putAttribute(SURVIVE, 0);
    }
}
