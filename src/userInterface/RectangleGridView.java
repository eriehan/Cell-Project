package userInterface;

import javafx.animation.Animation;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import simulation.CellState;


public class RectangleGridView extends AbstractGridView {

    public RectangleGridView(int numOfRows, int numOfCols) {
        super(numOfRows, numOfCols);
    }

    // TODO: set with config
    public void createGrid() {
        for (int r = 0; r < getNumOfRows(); r++) {
            for (int c = 0; c < getNumOfCols(); c++) {
                Rectangle shape = new Rectangle(this.getGridWidth() / getNumOfCols(), this.getGridHeight() / getNumOfRows());
                shape.setFill(getMyCellGrid().stateOfCellAtPoint(r, c).getMyColor());
                getMyGridPane().add(shape, c, r);
                final int row = r;
                final int col = c;
                shape.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> changeState(row, col));
            }
        }
    }

    @Override
    public void displayGrid(){
        getMyGridPane().getChildren().clear();
        createGrid();
    }

    private void changeState(int r, int c) {
        this.getMyCellGrid().setStateOfCellAtPointOnClick(r, c);
        createGrid();
    }
}

