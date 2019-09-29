package userInterface;

import javafx.scene.paint.Color;
import simulation.*;
import simulation.AntForaging.AntForagingCellGrid;
import utils.Point;
import xml.AbstractXml;
import xml.AntForagingXml;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GridManager {
    private AbstractXml myXml;
    private CellGrid cellGrid;
    private Map<Point, CellState> initialConfigMap;
    private List<CellState> stateList;

    public void initializeMyCellGrid(AbstractXml myXml) {
        this.myXml = myXml;
        Map configMap = new HashMap<Point, CellState>();

        switch (myXml.getMyTitle()) {
            case "Segregation":
                setSegregation(configMap);
                break;
            case "Game Of Life":
                setGameOfLife(configMap);
                break;
            case "Wa-Tor":
                setWaTor(configMap);
                break;
            case "Percolation":
                setPercolation(configMap);
                break;
            case "Fire":
                setFire(configMap);
                break;
            case "Ant Foraging":
                cellGrid = new AntForagingCellGrid(myXml.getCellGridRowNum(), myXml.getCellGridColNum(),
                        myXml.getMaxAnts(), myXml.getEvaporation(), myXml.getDiffusion());
                System.out.println(myXml.getMaxAnts());
                System.out.println(myXml.getEvaporation());
                System.out.println(myXml.getDiffusion());
                break;
            case "Rock Paper Scissors":
                cellGrid = new RockPaperScissorGrid(myXml.getCellGridRowNum(), myXml.getCellGridColNum(), myXml.getRate(), myXml.getPercentage());
                this.stateList = RockPaperScissorCell.STATES_LIST;
        }
        cellGrid.initializeGrids(initialConfigMap);
        cellGrid.assignNeighborsToEachCell();
        System.out.println(myXml.getCellGridColNum());
    }

    private void setSegregation(Map configMap) {
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
        this.stateList = SegregationCell.STATES_LIST;
        cellGrid = new SegregationCellGrid(myXml.getCellGridRowNum(), myXml.getCellGridColNum(), myXml.getRate(), myXml.getRate());

    }

    private void setGameOfLife(Map configMap) {
        for (int i = 0; i < myXml.getMyColArray().size(); i++) {
            int size = Math.min(myXml.getMyColArray().get(i).size(), myXml.getMyRowArray().get(i).size());
            for (int j = 0; j < size; j++) {
                configMap.put(new Point(myXml.getMyRowArray().get(i).get(j), myXml.getMyColArray().get(i).get(j)), CellState.ALIVE);
            }
        }
        this.initialConfigMap = configMap;
        this.stateList = GameOfLifeCell.STATES_LIST;
        cellGrid = new GameOfLifeCellGrid(myXml.getCellGridRowNum(), myXml.getCellGridColNum());
    }

    private void setWaTor(Map configMap) {
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
        this.stateList = WaTorCell.STATES_LIST;
        cellGrid = new WaTorCellGrid(myXml.getCellGridRowNum(), myXml.getCellGridColNum(), myXml.getMaturityArray(), myXml.getEnergyArray());
    }

    private void setPercolation(Map configMap) {
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
        this.stateList = PercolationCell.STATES_LIST;
        cellGrid = new PercolationCellGrid(myXml.getCellGridRowNum(), myXml.getCellGridColNum());

    }

    private void setFire(Map configMap) {
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
        this.stateList = FireCell.STATES_LIST;
        cellGrid = new FireCellGrid(myXml.getCellGridRowNum(), myXml.getCellGridColNum(), myXml.getRate());

    }


    public CellGrid getCellGrid() {
        return cellGrid;
    }

    public Color getColorOfCellAtPoint(int r, int c) {
        return this.getCellGrid().stateOfCellAtPoint(r, c).getMyColor();
    }

    public void setStateOfCellAtPointOnClick(int r, int c) {
        getCellGrid().setStateOfCellAtPointOnClick(r, c);
    }

    public void resetCellGrid() {
        if (initialConfigMap == null) {
            return;
        }
        getCellGrid().initializeGrids(initialConfigMap);
        getCellGrid().assignNeighborsToEachCell();
    }

    public void changeEdgeTypeOfGrid(String type) {
        if(type.equals("toroidal")) {
            getCellGrid().setGridLimit(GridLimit.TOROIDAL);
        } else if(type.equals("finite")) {
            getCellGrid().setGridLimit(GridLimit.FINITE);
        } else if(type.equals("infinite")) {
            getCellGrid().setGridLimit(GridLimit.INFINITE);
        }
    }

    public List<CellState> getStateList() {
        return stateList;
    }


}
