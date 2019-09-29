package simulation.AntForaging;

import simulation.GridInfo;
import simulation.Individual;

public class AntGridInfo extends GridInfo {

    private static final double HUNDRED = 100;
    private static final double MAXPHEROMONE = 1000;
    private static final GridAttribute HOMEPHEROMONE = GridAttribute.HOMEPHEROMONE;
    private static final GridAttribute FOODPHEROMONE = GridAttribute.FOODPHEROMONE;
    private static final GridAttribute EVAPORATION = GridAttribute.EVAPORATION;
    private static final GridAttribute MAXANT = GridAttribute.MAXANT;

    public AntGridInfo(int row, int col) {
        super(row, col);

    }

    @Override
    public void createIndividual() {
        System.out.println(getPossibleOrderedDirections().toString());
        addIndividual(new Ant(this, getPossibleOrderedDirections()));
    }

    @Override
    public void moveIndividuals() {
        for(Individual ant : getIndividuals()) {
            if(!ant.hasMoved()) {
                ant.move();
                ant.setMoved(true);
            }
            if(ant.isDead()) {
               addRemovedIndividual(ant);
            }
        }
        removeAllRemovedIndividuals();
        //If initially full but ant moved, ISPACKED will be false
        checkIfGridFull();
    }

    @Override
    public void update() {
        for(Individual ant : getIndividuals()) {
            ant.setMoved(false);
        }
        manageDiffusion();
        manageEvaporation();

        if(!getIndividuals().isEmpty()) {
            if(getBooleanAttribute(GridAttribute.ISFOOD)) {
                putNumberAttributes(FOODPHEROMONE, MAXPHEROMONE);
            } else {
                putNumberAttributes(HOMEPHEROMONE, MAXPHEROMONE);
            }
        }
    }

    //Checking if the grid is full of ants done every time an ant is added.
    @Override
    public void addIndividual(Individual individual) {
        super.addIndividual(individual);
        checkIfGridFull();
    }


    private void manageEvaporation() {
        multiplyNumberAttributes(HOMEPHEROMONE, (HUNDRED - getNumberAttribute(EVAPORATION)) / HUNDRED);
        multiplyNumberAttributes(FOODPHEROMONE, (HUNDRED - getNumberAttribute(EVAPORATION)) / HUNDRED);
    }

    private void checkIfGridFull() {
        putBooleanAttributes(GridAttribute.ISPACKED, numOfIndividuals() > getNumberAttribute(MAXANT));
    }

    private void manageDiffusion() {
        double diffusionRate = getNumberAttribute(GridAttribute.DIFFUSION);
        if(getBooleanAttribute(GridAttribute.ISFOOD)) {System.out.println(diffusionRate * getNumberAttribute(FOODPHEROMONE) / getNeighborGrids().size() + "sdfsfsdf");}
        int neighborNum = getNeighborGrids().size();
        for(GridInfo neighbor : getNeighborGrids()) {
            neighbor.addToNumberAttributes(HOMEPHEROMONE, diffusionRate * getNumberAttribute(HOMEPHEROMONE) / neighborNum);
            neighbor.addToNumberAttributes(FOODPHEROMONE, diffusionRate * getNumberAttribute(FOODPHEROMONE) / neighborNum);
        }
    }

}
