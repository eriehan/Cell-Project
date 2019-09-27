package userInterface;

import javafx.scene.paint.Color;
import simulation.*;
import utils.Point;
import xml.AbstractXml;

import java.util.HashMap;
import java.util.Map;

public class GridManager {
    private AbstractXml myXml;
    private CellGrid cellGrid;
    private Map<Point, CellState> initialConfigMap;

    public  GridManager(){
    }

    public void initializeMyCellGrid(AbstractXml myXml){
        this.myXml = myXml;
        Map configMap = new HashMap<Point, CellState>();

        switch (myXml.getMyTitle()) {
            case "Segregation":
                for (int i = 0; i < myXml.getMyColArray().size(); i++) {
                    int size = Math.min(myXml.getMyColArray().get(i).size(), myXml.getMyRowArray().get(i).size());
                    if (i == 0) {
                        for (int j = 0; j < size; j++) {
                            configMap.put(new Point(myXml.getMyRowArray().get(i).get(j), myXml.getMyColArray().get(i).get(j)), CellState.FIRSTAGENT);
                        }
                    } else {
                        for (int j = 0; j < size; j++) {
                            configMap.put(new Point(myXml.getMyRowArray().get(i).get(j), myXml.getMyColArray().get(i).get(j)), CellState.SECONDAGENT);
                        }
                    }
                }
                this.initialConfigMap = configMap;
                cellGrid = new SegregationCellGrid(myXml.getCellGridRowNum(), myXml.getCellGridColNum(), (int) myXml.getRate(), (int) myXml.getRate());
            case "Game Of Life":
                for (int i = 0; i < myXml.getMyColArray().size(); i++) {
                    int size = Math.min(myXml.getMyColArray().get(i).size(), myXml.getMyRowArray().get(i).size());
                    for (int j = 0; j < size; j++) {
                        configMap.put(new Point(myXml.getMyRowArray().get(i).get(j), myXml.getMyColArray().get(i).get(j)), CellState.ALIVE);
                    }
                }
                this.initialConfigMap = configMap;
                cellGrid =  new GameOfLifeCellGrid(myXml.getCellGridRowNum(), myXml.getCellGridColNum());
            case "Wa-Tor":

                for (int i = 0; i < myXml.getMyColArray().size(); i++) {
                    int size = Math.min(myXml.getMyColArray().get(i).size(), myXml.getMyRowArray().get(i).size());
                    if (i == 0) {
                        for (int j = 0; j < size; j++) {
                            configMap.put(new Point(myXml.getMyRowArray().get(i).get(j), myXml.getMyColArray().get(i).get(j)), CellState.SHARK);
                        }
                    } else {
                        for (int j = 0; j < size; j++) {
                            configMap.put(new Point(myXml.getMyRowArray().get(i).get(j), myXml.getMyColArray().get(i).get(j)), CellState.FISH);
                        }
                    }
                }
                this.initialConfigMap = configMap;
                cellGrid = new WaTorCellGrid(myXml.getCellGridRowNum(), myXml.getCellGridColNum(), myXml.getMaturityArray(), myXml.getEnergyArray());
            case "Percolation":
                for (int i = 0; i < myXml.getMyColArray().size(); i++) {
                    int size = Math.min(myXml.getMyColArray().get(i).size(), myXml.getMyRowArray().get(i).size());
                    if (i == 0) {
                        for (int j = 0; j < size; j++) {
                            configMap.put(new Point(myXml.getMyRowArray().get(i).get(j), myXml.getMyColArray().get(i).get(j)), CellState.PERCOLATED);
                        }
                    } else {
                        for (int j = 0; j < size; j++) {
                            configMap.put(new Point(myXml.getMyRowArray().get(i).get(j), myXml.getMyColArray().get(i).get(j)), CellState.BLOCKED);
                        }
                    }

                }
                this.initialConfigMap = configMap;
                cellGrid = new PercolationCellGrid(myXml.getCellGridRowNum(), myXml.getCellGridColNum());

            case "Fire":


                for (int i = 0; i < myXml.getMyColArray().size(); i++) {
                    int size = Math.min(myXml.getMyColArray().get(i).size(), myXml.getMyRowArray().get(i).size());
                    if (i == 0) {
                        for (int j = 0; j < size; j++) {
                            configMap.put(new Point(myXml.getMyRowArray().get(i).get(j), myXml.getMyColArray().get(i).get(j)), CellState.BURNING);
                        }
                    } else {
                        for (int j = 0; j < size; j++) {
                            configMap.put(new Point(myXml.getMyRowArray().get(i).get(j), myXml.getMyColArray().get(i).get(j)), CellState.FIREEMPTY);
                        }
                    }

                }
                this.initialConfigMap = configMap;
                cellGrid = new FireCellGrid(myXml.getCellGridRowNum(), myXml.getCellGridColNum(), myXml.getRate());
        }

        cellGrid.initializeGrids(initialConfigMap);
        cellGrid.assignNeighborsToEachCell();
    }

    public CellGrid getCellGrid() {
        return cellGrid;
    }

    public Color getColorOfCellAtPoint(int r, int c){
        return this.getCellGrid().stateOfCellAtPoint(r,c).getMyColor();
    }

    public void setStateOfCellAtPointOnClick(int r, int c){
        getCellGrid().setStateOfCellAtPointOnClick(r, c);
    }

    public void resetCellGrid() {
        if (initialConfigMap == null) {
            return;
        }
        getCellGrid().initializeGrids(initialConfigMap);
        getCellGrid().assignNeighborsToEachCell();
    }
}