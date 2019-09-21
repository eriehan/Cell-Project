package userInterface;

import javafx.scene.shape.Rectangle;

import static userInterface.VisualizationConstants.GRID_HEIGHT;
import static userInterface.VisualizationConstants.GRID_WIDTH;

public class GridView extends AbstractGridView {

    public GridView(int numOfRows, int numOfCols) {

        super(numOfRows, numOfCols);

    }

    // TODO: set with config
    public void createGrid() {
        for (int r = 0; r < getNumOfRows(); r++) {
            for (int c = 0; c < getNumOfCols(); c++) {
                Rectangle shape = new Rectangle(GRID_WIDTH / getNumOfCols(), GRID_HEIGHT / getNumOfRows());
                shape.setFill(getMyCellGrid().stateOfCellAtPoint(r, c).getMyColor());
                getMyGridPane().add(shape, c, r);
            }
        }
    }
}

