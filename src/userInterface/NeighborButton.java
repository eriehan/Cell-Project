package userInterface;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.*;


public class NeighborButton extends SimulationButton {
    public static final int CENTER = 4;
    private static final String EMPTYSTYLE = "-fx-border-color: black;"+"-fx-background-color: White";
    private static final String CLICKEDSTYLE = "-fx-border-color: black;"+"-fx-background-color: LightBlue";
    private int idx;
    private boolean chosen = false;

    public NeighborButton(int idx) {
        super("");
        this.idx = idx;
        this.setPrefSize(40,40);
        this.setStyle("-fx-border-color: black;"+"-fx-background-color: White");
        if (idx==CENTER){
            this.setText("*");
            this.setStyle("-fx-background-color: Yellow");
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
        if(chosen) {
            this.setStyle(CLICKEDSTYLE);
        } else {
            this.setStyle(EMPTYSTYLE);
        }
    }
}
