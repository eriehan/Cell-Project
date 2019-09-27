package simulation;

import userInterface.CellShapeType;
import utils.Point;

import java.util.List;
import java.util.Map;

public enum GridLimit {
    FINITE {
        @Override
        public void assignNeighbors(Map<Point, Cell> gridOfCells, CellShapeType cellShapeType, int numOfRows, int numOfCols) {
            assignNeighborsToCell(gridOfCells, cellShapeType, numOfRows, numOfCols, false);
        }

        @Override
        public void assignEdgeNeighbors(Map<Point, Cell> gridOfCells, CellShapeType cellShapeType, int numOfRows, int numOfCols) {
            assignEdgeNeighborsToCell(gridOfCells, cellShapeType, numOfRows, numOfCols, false);
        }
    },
    TOROIDAL {
        @Override
        public void assignNeighbors(Map<Point, Cell> gridOfCells, CellShapeType cellShapeType, int numOfRows, int numOfCols) {
            assignNeighborsToCell(gridOfCells, cellShapeType, numOfRows, numOfCols, true);
        }
        @Override
        public void assignEdgeNeighbors(Map<Point, Cell> gridOfCells, CellShapeType cellShapeType, int numOfRows, int numOfCols) {
            assignEdgeNeighborsToCell(gridOfCells, cellShapeType, numOfRows, numOfCols, true);
        }
    },
    INFINITE {
        @Override
        public void assignNeighbors(Map<Point, Cell> gridOfCells, CellShapeType cellShapeType, int numOfRows, int numOfCols) {
            assignNeighborsToCell(gridOfCells, cellShapeType, numOfRows, numOfCols, false);
        }
        @Override
        public void assignEdgeNeighbors(Map<Point, Cell> gridOfCells, CellShapeType cellShapeType, int numOfRows, int numOfCols) {
            assignEdgeNeighborsToCell(gridOfCells, cellShapeType, numOfRows, numOfCols, false);
        }
    };

    public abstract void assignNeighbors(Map<Point, Cell> gridOfCells, CellShapeType cellShapeType, int numOfRows, int numOfCols);
    public abstract void assignEdgeNeighbors(Map<Point, Cell> gridOfCells, CellShapeType cellShapeType, int numOfRows, int numOfCols);

    protected void assignNeighborsToCell(Map<Point, Cell> gridOfCells, CellShapeType cellShapeType, int numOfRows, int numOfCols, boolean toroidal) {
        for(Map.Entry<Point, Cell> cellEntry : gridOfCells.entrySet()) {
            Cell cell = cellEntry.getValue();
            cell.clearNeighbors();
            addAllNeighbors(cell, gridOfCells, numOfRows, numOfCols, cellShapeType.neighborDirections(), toroidal);
        }
    }

    protected void assignEdgeNeighborsToCell(Map<Point, Cell> gridOfCells, CellShapeType cellShapeType, int numOfRows, int numOfCols, boolean toroidal) {
        for(Map.Entry<Point, Cell> cellEntry : gridOfCells.entrySet()) {
            Cell cell = cellEntry.getValue();
            cell.clearNeighbors();
            addAllNeighbors(cell, gridOfCells, numOfRows, numOfCols, cellShapeType.edgeNeighborDirections(), toroidal);
        }
    }

    private void addAllNeighbors(Cell cell, Map<Point, Cell> gridOfCells, int numOfRows, int numOfCols, List<Point> directions, boolean toroidal) {
        for(Point direction : directions) {
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
    }

    private Cell cellFromPoint(Map<Point, Cell> gridOfCells, int row, int col, int numOfRows, int numOfCols) {
        if(row<0) {
            row = numOfRows-1;
        }
        else if(row>=numOfRows) {
            row = 0;
        }

        if(col<0) {col = numOfCols-1;}
        else if(col>=numOfCols) {col = 0;}

        return gridOfCells.get(new Point(row, col));
    }
}
