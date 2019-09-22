package userInterface;

import javafx.scene.shape.Rectangle;


public class GridView extends AbstractGridView {

    public GridView(int numOfRows, int numOfCols) {

        super(numOfRows, numOfCols);
    }

    // TODO: set with config
    public void createGrid() {
        for (int r = 0; r < getNumOfRows(); r++) {
            for (int c = 0; c < getNumOfCols(); c++) {
                Rectangle shape = new Rectangle(this.getGridWidth() / getNumOfCols(), this.getGridHeight() / getNumOfRows());
                shape.setFill(getMyCellGrid().stateOfCellAtPoint(r, c).getMyColor());
                getMyGridPane().add(shape, c, r);
            }
        }
    }
}

