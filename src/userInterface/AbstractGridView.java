package userInterface;

import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import simulation.*;
import utils.Point;
import xml.Xml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static userInterface.VisualizationConstants.BLANK_GRID_COLOR;

public abstract class AbstractGridView {
    private static final String RESOURCE_FILE_PATH = "resources/MainResources";
    private GridPane myGridPane;
    private int numOfRows;
    private int numOfCols;
    private int gridWidth;
    private int gridHeight;
    private CellGrid myCellGrid;
    private Map<Point, CellState> initialConfigMap;
    private ResourceBundle resourceBundle;
    private Map configMap = new HashMap<Point, CellState>();


    public AbstractGridView(int numOfRows, int numOfCols) {
        myGridPane = new GridPane();
        this.numOfCols = numOfCols;
        this.numOfRows = numOfRows;
        this.resourceBundle = ResourceBundle.getBundle(RESOURCE_FILE_PATH);
        this.gridWidth = Integer.parseInt(resourceBundle.getString("GridWidth"));
        this.gridHeight = Integer.parseInt(resourceBundle.getString("GridHeight"));
    }

    public abstract void createGrid();

    public void generateBlankGrid() {
        Rectangle shape = new Rectangle(this.gridWidth, this.gridHeight);
        shape.setFill(BLANK_GRID_COLOR);
        myGridPane.add(shape, 0, 0);
    }

    public void updateGrid() {
        if (myCellGrid == null) {
            return;
        }
        myCellGrid.checkAllCells();
        myCellGrid.changeAllCells();
        displayGrid();
    }

    public void initializeMyCellGrid(Xml myXml){

        //Map configMap = new HashMap<Point, CellState>();

        switch (myXml.getMyTitle()) {
            case "Segregation":
                for (int i = 0; i < myXml.getMyColArray().size(); i++) {
                    if (i == 0) {
                        for (int j = 0; j < myXml.getMyColArray().get(i).size(); j++) {
                            configMap.put(new Point(myXml.getMyRowArray().get(i).get(j), myXml.getMyColArray().get(i).get(j)), CellState.FIRSTAGENT);
                        }
                    } else {
                        for (int j = 0; j < myXml.getMyColArray().get(i).size(); j++) {
                            configMap.put(new Point(myXml.getMyRowArray().get(i).get(j), myXml.getMyColArray().get(i).get(j)), CellState.SECONDAGENT);
                        }
                    }
                }

                setMyCellGrid(new SegregationCellGrid(myXml.getCellGridRowNum(), myXml.getCellGridColNum(), (int) myXml.getRate(), (int) myXml.getRate()));
                break;
            case "Game Of Life":
                for (int i = 0; i < myXml.getMyColArray().size(); i++) {
                    for (int j = 0; j < myXml.getMyColArray().get(i).size(); j++) {
                        configMap.put(new Point(myXml.getMyRowArray().get(i).get(j), myXml.getMyColArray().get(i).get(j)), CellState.ALIVE);
                    }
                }
                setMyCellGrid(new GameOfLifeCellGrid(myXml.getCellGridRowNum(), myXml.getCellGridColNum()));
                break;
            case "Wa-Tor":
                for (int i = 0; i < myXml.getMyColArray().size(); i++) {
                    if (i == 0) {
                        for (int j = 0; j < myXml.getMyColArray().get(i).size(); j++) {
                            configMap.put(new Point(myXml.getMyRowArray().get(i).get(j), myXml.getMyColArray().get(i).get(j)), CellState.SHARK);
                        }
                    } else {
                        for (int j = 0; j < myXml.getMyColArray().get(i).size(); j++) {
                            configMap.put(new Point(myXml.getMyRowArray().get(i).get(j), myXml.getMyColArray().get(i).get(j)), CellState.FISH);
                        }
                    }
                }

                setMyCellGrid(new WaTorCellGrid(myXml.getCellGridRowNum(), myXml.getCellGridColNum(), myXml.getMaturityArray(), myXml.getEnergyArray()));
                break;

            case "Percolation":
                for (int i = 0; i < myXml.getMyColArray().size(); i++) {
                    if (i == 0) {
                        for (int j = 0; j < myXml.getMyColArray().get(i).size(); j++) {
                            configMap.put(new Point(myXml.getMyRowArray().get(i).get(j), myXml.getMyColArray().get(i).get(j)), CellState.PERCOLATED);
                        }
                    } else {
                        for (int j = 0; j < myXml.getMyColArray().get(i).size(); j++) {
                            configMap.put(new Point(myXml.getMyRowArray().get(i).get(j), myXml.getMyColArray().get(i).get(j)), CellState.BLOCKED);
                        }
                    }

                }
                setMyCellGrid(new PercolationCellGrid(myXml.getCellGridRowNum(), myXml.getCellGridColNum()));
                break;

            case "Fire":
                // System.out.println("entered Fire");
                for (int i = 0; i < myXml.getMyColArray().size(); i++) {
                    for (int j = 0; j < myXml.getMyColArray().get(i).size(); j++) {
                        configMap.put(new Point(myXml.getMyRowArray().get(i).get(j), myXml.getMyColArray().get(i).get(j)), CellState.BURNING);
                    }
                }
                setMyCellGrid(new FireCellGrid(myXml.getCellGridRowNum(), myXml.getCellGridColNum(), myXml.getRate()));
                break;

        }

        this.initialConfigMap = configMap;
        getMyCellGrid().initializeGrids(configMap);
        getMyCellGrid().assignNeighborsToEachCell();
        displayGrid();
    }


    public abstract void displayGrid();

    public void resetCellGrid() {
        if (initialConfigMap == null) {
            return;
        }
        getMyCellGrid().initializeGrids(initialConfigMap);
        getMyCellGrid().assignNeighborsToEachCell();
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

    public void setNumOfCols(int n) {
        numOfCols = n;
    }

    public int getNumOfCols() {
        return numOfCols;
    }

    public CellGrid getMyCellGrid() {
        return myCellGrid;
    }

    public void setMyCellGrid(CellGrid myCellGrid) {
        this.myCellGrid = myCellGrid;
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

