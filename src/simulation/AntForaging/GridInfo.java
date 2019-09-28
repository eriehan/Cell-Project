package simulation.AntForaging;

import java.util.List;

public class GridInfo {
    private double foodPheromone = 0;
    private double homePheromone = 0;
    private int antNum;
    private int maxAnt;
    private int row;
    private int col;
    //private List<Ant> ants;

    public GridInfo(int row, int col, int maxAnt) {
        this.row = row;
        this.col = col;
        antNum = 0;
        this.maxAnt = maxAnt;
    }

    public void addFoodPheromone(double foorPheromone) {
        this.foodPheromone += foodPheromone;
    }

    public void addHomePheromone(double foodPheromone) {
        this.homePheromone += homePheromone;
    }
}
