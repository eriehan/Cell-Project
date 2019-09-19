package userInterface;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

import static userInterface.VisualizationConstants.GRID_HEIGHT;
import static userInterface.VisualizationConstants.GRID_WIDTH;

public class GridView {
    private GridPane myGridPane;
    private final int numOfCols;
    private final int numOfRows;
    private final Color[] paints = {Color.LAVENDER, Color.HONEYDEW, Color.CORNFLOWERBLUE, Color.PLUM};

    public GridView(int numOfCols, int numOfRows) {
        myGridPane = new GridPane();
        this.numOfCols = numOfCols;
        this.numOfRows = numOfRows;
    }

    // TODO: set with config
    public void createGrid() {
        Random ran = new Random();
        for (int r = 0; r < numOfRows; r++) {
            for (int c = 0; c < numOfCols; c++) {
                Rectangle shape = new Rectangle(GRID_WIDTH / numOfCols, GRID_HEIGHT / numOfRows);
                shape.setFill(paints[ran.nextInt(4)]);
                myGridPane.add(shape, r, c);
            }
        }
    }

    public void generateBlankGrid() {
        Rectangle shape = new Rectangle(GRID_WIDTH, GRID_HEIGHT);
        shape.setFill(Color.LIGHTBLUE);
        myGridPane.add(shape, 0,0);
    }

    // TODO: set with config
    public void updateGrid() {
        myGridPane.getChildren().clear();
        createGrid();
    }

    public GridPane getMyGridPane() {
        return myGridPane;
    }
}

