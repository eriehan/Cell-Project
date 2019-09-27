package userInterface;

import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;


public class RectangleGridView extends AbstractGridView {

    public RectangleGridView(int numOfRows, int numOfCols) {
        super(numOfRows, numOfCols);
    }

    public void createGrid() {
        double regWidth = this.getGridWidth() / getGridManager().getCellGrid().getNumOfCols();
        double regHeight = this.getGridHeight() / getGridManager().getCellGrid().getNumOfRows();
        for (int r = 0; r < getGridManager().getCellGrid().getNumOfRows(); r++) {
            for (int c = 0; c < getGridManager().getCellGrid().getNumOfCols(); c++) {
                Rectangle shape = new Rectangle(regWidth, regHeight);
                shape.setFill(this.getGridManager().getColorOfCellAtPoint(r, c));
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
        getGridManager().setStateOfCellAtPointOnClick(r, c);
        createGrid();
    }
}

