package userInterface;

import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

import java.util.ResourceBundle;

import static userInterface.VisualizationConstants.BLANK_GRID_COLOR;

public abstract class AbstractGridView {
    private static final String RESOURCE_FILE_PATH = "resources/MainResources";
    private GridPane myGridPane;
    private int numOfRows;
    private int numOfCols;
    private int gridWidth;
    private int gridHeight;
    private GridManager gridManager;
    private ResourceBundle resourceBundle;


    public AbstractGridView(int numOfRows, int numOfCols) {
        myGridPane = new GridPane();
        this.numOfCols = numOfCols;
        this.numOfRows = numOfRows;
        this.resourceBundle = ResourceBundle.getBundle(RESOURCE_FILE_PATH);
        this.gridWidth = Integer.parseInt(resourceBundle.getString("GridWidth"));
        this.gridHeight = Integer.parseInt(resourceBundle.getString("GridHeight"));
        this.gridManager = new GridManager();
    }

    public abstract void createGrid();

    public void generateBlankGrid() {
        Rectangle shape = new Rectangle(this.gridWidth, this.gridHeight);
        shape.setFill(BLANK_GRID_COLOR);
        myGridPane.add(shape, 0, 0);
    }

    public void updateGrid() {
        if (gridManager == null || gridManager.getCellGrid() == null) {
            return;
        }
        gridManager.getCellGrid().checkAllCells();
        gridManager.getCellGrid().changeAllCells();
        displayGrid();
    }


    public abstract void displayGrid();

    public void resetGridView() {
        this.getGridManager().resetCellGrid();
        displayGrid();
    }

    public GridPane getMyGridPane() {
        return myGridPane;
    }

    public int getNumOfRows() {
        return numOfRows;
    }

    public void setNumOfRows(int n) {
        numOfRows = n;
    }

    public int getNumOfCols() {
        return numOfCols;
    }

    public void setNumOfCols(int n) {
        numOfCols = n;
    }

    public GridManager getGridManager() {
        return gridManager;
    }

    public int getGridWidth() {
        return this.gridWidth;
    }

    public int getGridHeight() {
        return this.gridHeight;
    }

    public void setGridHeight(int gridHeight) {
        this.gridHeight = gridHeight;
    }


}

