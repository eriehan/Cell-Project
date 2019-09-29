package userInterface;


import static userInterface.VisualizationConstants.CENTER_COLOR;
import static userInterface.VisualizationConstants.NEIGHBOR_STYLE;

public class NeighborButton extends SimulationButton {
    public static final int CENTER = 4;
    private int idx;
    private boolean chosen = false;

    public NeighborButton(int idx) {
        super("");
        this.idx = idx;
        this.setPrefSize(40,40);
        this.setStyle(NEIGHBOR_STYLE);
        if (idx==CENTER){
            this.setStyle(CENTER_COLOR);
        }
    }

    public int getIdx() {
        return idx;
    }

    public boolean isChosen() {
        return chosen;
    }

    public void flipChosen(){
        chosen = !chosen;
    }
}
