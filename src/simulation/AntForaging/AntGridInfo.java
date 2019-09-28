package simulation.AntForaging;

import simulation.GridInfo;
import simulation.Individual;
import utils.Point;

import java.util.ArrayList;
import java.util.List;

public class AntGridInfo extends GridInfo {

    private static final double HUNDRED = 100;

    private List<Point> possibleDirections = new ArrayList<>();
    private List<Individual> movedIndividuals = new ArrayList<>();


    public AntGridInfo(int row, int col,  List<Point> orderedDirections) {
        super(row, col);
        possibleDirections.addAll(orderedDirections);
    }

    @Override
    public void createIndividual() {
        addIndividual(new Ant(this, possibleDirections));
    }

    @Override
    public void moveIndividuals() {
    }

    @Override
    public void update() {
        calcReduction(GridAttribute.FOODPHEROMONE, GridAttribute.DIFFUSION);
        calcReduction(GridAttribute.HOMEPHEROMONE, GridAttribute.EVAPORATION);
    }

    private void calcReduction(GridAttribute toBeReduced, GridAttribute reduction) {
        multiplyNumberAttributes(toBeReduced, (HUNDRED - getNumberAttribute(reduction)) / HUNDRED);
    }
}
