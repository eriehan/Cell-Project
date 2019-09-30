package userInterface;

import static userInterface.VisualizationConstants.CENTER_COLOR;
import static userInterface.VisualizationConstants.NEIGHBOR_STYLE;

public class NeighborButton extends SimulationButton {
    private static final int CENTER = 4;
    private static final int BUTTON_WIDTH = 40;

    private static final String EMPTYSTYLE = "-fx-border-color: black;" + "-fx-background-color: White";
    private static final String CLICKEDSTYLE = "-fx-border-color: black;" + "-fx-background-color: LightBlue";
    private int idx;
    private boolean chosen = false;

    public NeighborButton(int idx) {
        super("");
        this.idx = idx;
        this.setPrefSize(BUTTON_WIDTH, BUTTON_WIDTH);
        this.setStyle(NEIGHBOR_STYLE);
        if (idx == CENTER) {
            this.setStyle(CENTER_COLOR);
        }
    }

    public int getIdx() {
        return idx;
    }

    public boolean isChosen() {
        return chosen;
    }

    public void flipChosen() {
        if (idx == CENTER) {
            return;
        }
        chosen = !chosen;
        if (chosen) {
            this.setStyle(CLICKEDSTYLE);
        } else {
            this.setStyle(EMPTYSTYLE);
        }
    }
}
