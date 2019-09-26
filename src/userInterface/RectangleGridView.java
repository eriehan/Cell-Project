package userInterface;

import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;


public class RectangleGridView extends AbstractGridView {

    public RectangleGridView(int numOfRows, int numOfCols) {
        super(numOfRows, numOfCols);
    }

    // TODO: set with config
    public void createGrid() {
        double regWidth = this.getGridWidth() / getMyCellGrid().getNumOfCols();
        double regHeight = this.getGridHeight() / getMyCellGrid().getNumOfRows();
        for (int r = 0; r < getMyCellGrid().getNumOfRows(); r++) {
            for (int c = 0; c < getMyCellGrid().getNumOfCols(); c++) {
                Rectangle shape = new Rectangle(regWidth,regHeight);
                shape.setFill(getMyCellGrid().stateOfCellAtPoint(r, c).getMyColor());
                getMyGridPane().add(shape, c, r);
                final int row = r;
                final int col = c;
                shape.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> changeState(row, col));
            }
        }
    }

    @Override
    public void displayGrid() {
        getMyGridPane().getChildren().clear();
        createGrid();
    }

    private void changeState(int r, int c) {
        this.getMyCellGrid().setStateOfCellAtPointOnClick(r, c);
        createGrid();
    }
}

