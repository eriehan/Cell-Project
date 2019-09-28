package simulation;

import userInterface.CellShapeType;
import utils.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NeighborManager {

    private static final Point W = new Point(0, -1);
    private static final Point E = new Point(0, 1);
    private static final Point S = new Point(1, 0);
    private static final Point N = new Point(-1, 0);
    private static final Point SW = new Point(1, -1);
    private static final Point SE = new Point(1, 1);
    private static final Point NW = new Point(-1, -1);
    private static final Point NE = new Point(-1, 1);
    private static final Point WW = new Point(0, -2);
    private static final Point EE = new Point(0, 2);
    private static final Point SWW = new Point(1, -2);
    private static final Point SEE = new Point(1, 2);
    private static final Point NWW= new Point(-1, -2);
    private static final Point NEE= new Point(-1, 2);
    private static final Point NN = new Point(-2, 0);
    private static final Point SS = new Point(2, 0);

    private static final List<Point> squareAllNeighbors = List.of(NW, N, NE, E, SE, S, SW, W);
    private static final List<Point> squareEdgeNeighbors = List.of(N, E, S, W);
    private static final List<Point> downwardTriangleNeighbors = List.of(W, E, S, N, SW, SE, NW, NE, WW, EE, NWW, NEE);
    private static final List<Point> upwardTriangleNeighbors = List.of(W, E, S, N, SW, SE, NW, NE, WW, EE, SWW, SEE);
    private static final List<Point> downwardTriangleEdgeNeighbors = List.of(W, E, S);
    private static final List<Point> upwardTriangleEdgeNeighbors = List.of(W, E, N);
    private static final List<Point> leftSidedRowHexagonNeighbors = List.of(S, SE, N, NE, NN, SS);
    private static final List<Point> rightSidedRowHexagonNeighbors = List.of(S, SW, N, NW, NN, SS);

    private static final String defaultConfig = "11111111";

    private List<Point> allowedNeighbor;
    private List<Point> edgeNeighbors;
    private boolean toroidal;
    private boolean upRowExtended = false;
    private CellShapeType cellShapeType;

    //default -> put "11111111" for eightBit.
    public NeighborManager(String eightBit, CellShapeType cellShapeType, boolean toroidal) {
        this.cellShapeType = cellShapeType;
        allowedNeighbor = calcAllowedAllNeighbors(eightBit);
        edgeNeighbors = calcAllowedEdgeNeighbors(eightBit);
        this.toroidal = toroidal;
    }

    public void assignAllNeighbors(Map<Point, Cell> gridOfCells, int numOfRows, int numOfCols) {
        for (Map.Entry<Point, Cell> cellEntry : gridOfCells.entrySet()) {
            Cell cell = cellEntry.getValue();
            cell.clearNeighbors();
            addNeighborsToCell(cell, gridOfCells, numOfRows, numOfCols, calcActualNeighbors(cell.getRow(), cell.getCol()));
        }
    }

    public void assignEdgeNeighbors(Map<Point, Cell> gridOfCells, int numOfRows, int numOfCols) {
        for (Map.Entry<Point, Cell> cellEntry : gridOfCells.entrySet()) {
            Cell cell = cellEntry.getValue();
            cell.clearNeighbors();
            addNeighborsToCell(cell, gridOfCells, numOfRows, numOfCols, calcActualEdgeNeighbors(cell.getRow(), cell.getCol()));
        }
    }

    private void addNeighborsToCell(Cell cell, Map<Point, Cell> gridOfCells, int numOfRows, int numOfCols, List<Point> directions) {
        for (Point direction : directions) {
            int row = cell.getRow();
            int col = cell.getCol();
            row += direction.getRow();
            col += direction.getCol();
            if (!toroidal) {
                if (row < 0 || row >= numOfRows) {
                    continue;
                }
                if (col < 0 || col >= numOfCols) {
                    continue;
                }
            }
            cell.addNeighbor(direction, cellFromPoint(gridOfCells, row, col, numOfRows, numOfCols));
        }
        System.out.println(cell.getNeighbors().size());
    }

    private Cell cellFromPoint(Map<Point, Cell> gridOfCells, int row, int col, int numOfRows, int numOfCols) {
        if (row < 0) {
            row += numOfRows;
        } else if (row >= numOfRows) {
            row -= numOfRows;
        }

        if (col < 0) {
            col += numOfCols;
        } else if (col >= numOfCols) {
            col -= numOfCols;
        }

        return gridOfCells.get(new Point(row, col));
    }

    private List<Point> shapeNeighbor() {
        if(cellShapeType == CellShapeType.RECTANGLE) {
            return squareAllNeighbors;
        }
        else if(cellShapeType == CellShapeType.TRIANGLE) {
            return upwardTriangleNeighbors;
        }
        else {
            return rightSidedRowHexagonNeighbors;
        }
    }

    private List<Point> shapeEdgeNeighbor() {
        if(cellShapeType == CellShapeType.RECTANGLE) {
            return squareEdgeNeighbors;
        }
        else if(cellShapeType == CellShapeType.TRIANGLE) {
            return upwardTriangleEdgeNeighbors;
        }
        else {
            return rightSidedRowHexagonNeighbors;
        }
    }

    private List<Point> calcAllowedAllNeighbors(String str) {
        if(str.equals(defaultConfig)) {
            return shapeNeighbor();
        }
        else {
            List<Point> list = new ArrayList<>();
            for(int i=0; i<str.length(); i++) {
                if(str.charAt(i) == '1' && shapeNeighbor().contains(squareEdgeNeighbors.get(i))) {
                    list.add(squareAllNeighbors.get(i));
                }
            }
            return list;
        }
    }

    private List<Point> calcAllowedEdgeNeighbors(String str) {
        if(str.equals(defaultConfig)) {
            return shapeEdgeNeighbor();
        }
        else {
            List<Point> list = new ArrayList<>();
            for(int i=1; i<str.length(); i+=2) {
                if(str.charAt(i) == '1' && shapeEdgeNeighbor().contains(squareEdgeNeighbors.get(i))) {
                    list.add(squareAllNeighbors.get(i));
                }
            }
            return list;
        }
    }

    private List<Point> calcActualNeighbors(int row, int col) {
        if(downWardTriangle(row, col)) {
            return downwardTriangleNeighbors;
        }
        else if(leftSidedRowHexagon(row)) {
            return leftSidedRowHexagonNeighbors;
        }
        return allowedNeighbor;
    }

    private List<Point> calcActualEdgeNeighbors(int row, int col) {
        if(downWardTriangle(row, col)) {
            return downwardTriangleEdgeNeighbors;
        }
        else if(leftSidedRowHexagon(row)) {
            return leftSidedRowHexagonNeighbors;
        }
        return edgeNeighbors;
    }

    private boolean downWardTriangle(int row, int col) {
        boolean case1 = upRowExtended && (row + col)%2 != 0;
        boolean case2 = !upRowExtended && (row + col)%2 == 0;
        return cellShapeType == CellShapeType.TRIANGLE && (case1 || case2);
    }


    private boolean leftSidedRowHexagon(int row) {
        boolean case1 = upRowExtended && row%2==0;
        boolean case2 = !upRowExtended && row%2 != 0;
        return cellShapeType == CellShapeType.HEXAGON && (case1 || case2);
    }
}
