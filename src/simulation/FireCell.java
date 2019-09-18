package simulation;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class FireCell extends Cell {

    private double probCatch;

    public FireCell(int row, int col, int state, double probCatch) {
        super(row, col, state);

        this.probCatch = probCatch;
    }
    @Override
    public void check() {

    }

    @Override
    public void changeState() {

    }

    private Paint stateToPaint() {
        return null;
    }
}
