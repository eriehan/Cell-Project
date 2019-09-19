package simulation;

public class WaTorCell extends Cell {

    private int reproduce;
    private int energy;

    public WaTorCell(int row, int col, CellState state, int reproduce, int energy) {
        super(row, col, state);
        this.reproduce = reproduce;
        this.energy = energy;
    }

    @Override
    public void check() {

    }

    @Override
    public void changeState() {

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
}
