package simulation;

import javafx.scene.shape.Polygon;

import java.util.ArrayList;

public class CellGrid {

    private int numOfRows;
    private int numOfCols;
    private double gridWidth;
    private double gridHeight;
    private ArrayList<String[]> cellConfig;

    //Can change to hashmap later. using 2D arrayList here just to show the idea.
    private ArrayList<ArrayList<Cell>> gridOfCells = new ArrayList<>();

    public CellGrid(int numOfRows, int numOfCols, double gridWidth, double gridHeight, ArrayList<String[]> cellConfig) {
        this.numOfRows = numOfRows;
        this.numOfCols = numOfCols;
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;

        //CellConfig is going to be the configuration that is read from the file.
        //The method for reading the file will be in the class that calls this constructor of CellGrid.
        this.cellConfig = cellConfig;
    }

    public void fillGridWithCells(String type, ArrayList<String[]> cellConfig) {
        for (int i = 0; i < cellConfig.size(); i++) {
            for (int j = 0; j < cellConfig.get(i).length; j++) {
                if (j == 0) {
                    gridOfCells.add(new ArrayList<>());
                }
                gridOfCells.get(i).add(createCell(type, i, j, Integer.parseInt(cellConfig.get(i)[j])));
            }
        }

        assignNeighborsToEachCell();
    }

    private Cell createCell(String type, int row, int col, int state) {
        // switch-case to be added later
        return new GameOfLifeCell(state, gridWidth / numOfCols, gridHeight / numOfRows);
    }

    private void assignNeighborsToEachCell() {
        for (int i = 0; i < gridOfCells.size(); i++) {
            for (int j = 0; j < gridOfCells.get(i).size(); j++) {
                assignNeighborsToOneCell(gridOfCells.get(i).get(j), i, j);
            }
        }
    }

    private void assignNeighborsToOneCell(Cell cell, int row, int col) {
        if (!(row == 0 || col == 0)) { cell.addNeighbor(gridOfCells.get(row - 1).get(col - 1)); }
        if (!(row == 0 || col == numOfCols - 1)) { cell.addNeighbor(gridOfCells.get(row - 1).get(col - 1)); }
        if (!(row == numOfRows - 1 || col == 0)) { cell.addNeighbor(gridOfCells.get(row + 1).get(col - 1)); }
        if (!(row == numOfRows - 1 || col == numOfCols - 1)) { cell.addNeighbor(gridOfCells.get(row + 1).get(col + 1)); }

        if (row != 0) { cell.addNeighbor(gridOfCells.get(row - 1).get(col)); }
        if (row != numOfRows - 1) { cell.addNeighbor(gridOfCells.get(row + 1).get(col)); }
        if (col != 0) { cell.addNeighbor(gridOfCells.get(row).get(col - 1)); }
        if (col != numOfCols - 1) { cell.addNeighbor(gridOfCells.get(row).get(col + 1)); }
    }


    //if user wants to change the state of a cell
    public void changeOneCell (int row, int col, int state){
        Cell cell = gridOfCells.get(row).get(col);
        cell.setState(state);
        cell.changeState();
    }
}
