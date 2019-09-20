package simulation;

import java.util.List;

public class WaTorCell extends Cell {

    private int reproduce;
    private int initialEnergy;

    private int energy;
    private int survivedTime = 0;

    public WaTorCell(int row, int col, CellState state, int reproduce, int energy) {
        super(row, col, state);
        this.reproduce = reproduce;
        this.energy = energy;
        this.initialEnergy = energy;
    }

    //highly important for the fish cells to call this method before the shark cells do.
    //Code is designed so that all the fish move first, and sharks move after that.
    @Override
    public void check() {
        List<Cell> neighbors = getEdgeNeighbor();
        int index = (int) Math.random() * neighbors.size();
        if(getState() == CellState.FISH) {
            //move to one of the adjacent empty grid.
            for(int i = index; i<index+neighbors.size(); i++) {
                Cell other = neighbors.get(i % neighbors.size());
                if(other.getState() == CellState.EMPTY) {
                    moveToDifferentCell(other);
                }
            }
        } else if(getState() == CellState.SHARK) {
            //if find a fish, move to the fish
            for(int i  = index; i<index+neighbors.size(); i++) {
                Cell other = neighbors.get(i % neighbors.size());
                if(other.getState() == CellState.FISH) {
                    moveToDifferentCell(other);
                }
            }
            //if no fish found, move to empty grid
            for(int i  = index; i<index+neighbors.size(); i++) {
                Cell other = neighbors.get(i % neighbors.size());
                if(other.getState() == CellState.FISH) {
                    moveToDifferentCell(other);
                }
            }
        }
    }

    @Override
    public void changeState() {
        if(getState() == CellState.SHARK && energy == 0) {setNextState(CellState.EMPTY);}
        setState(getNextState());
    }

    public int getReproduce() {
        return reproduce;
    }

    public void setReproduce(int reproduce) {
        this.reproduce = reproduce;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    @Override
    public void moveToDifferentCell(Cell other) {
        if (!(other instanceof WaTorCell)) { return; }
        super.moveToDifferentCell(other);

        WaTorCell waTorCell = (WaTorCell) other;
        if (waTorCell.getState() == CellState.SHARK) {
            if (getState() == CellState.FISH) {
                energy += waTorCell.getEnergy();
                setState(CellState.EMPTY);
            }
            energy--;
        }

        waTorCell.setEnergy(energy);
        waTorCell.setNextState(waTorCell.getState());
        waTorCell.setReproduce(reproduce);
        waTorCell.setInitialEnergy(initialEnergy);
        waTorCell.setSurvivedTime(survivedTime++);
        survivedTime = 0;

        if(survivedTime > reproduce) {
            setNextState(waTorCell.getState());
            energy = initialEnergy;
            waTorCell.setSurvivedTime(0);
        } else {
            setNextState(CellState.EMPTY);
        }
    }

    public int getSurvivedTime() {
        return survivedTime;
    }

    public void setSurvivedTime(int survivedTime) {
        this.survivedTime = survivedTime;
    }

    public int getInitialEnergy() {
        return initialEnergy;
    }

    public void setInitialEnergy(int initialEnergy) {
        this.initialEnergy = initialEnergy;
    }
}
