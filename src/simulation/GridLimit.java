package simulation;

import utils.Point;

import java.util.Map;

public enum GridLimit {
    FINITE {
        @Override
        public void assignCornerNeighborsSquare(Map<Point, Cell> gridOfCells, int numOfRows, int numOfCols) {
            for(Map.Entry<Point, Cell> cellEntry : gridOfCells.entrySet()) {
                Cell cell = cellEntry.getValue();
                cell.clearCornerNeighbors();
                int row = cellEntry.getKey().getRow();
                int col = cellEntry.getKey().getCol();

                if(!(row == 0 || col == 0)) {addNeighbor(cell, gridOfCells, row-1, col-1, numOfRows, numOfCols, true);}
                if(!(row == 0 || col == numOfCols - 1)) { addNeighbor(cell, gridOfCells, row-1, col+1, numOfRows, numOfCols, true);}
                if(!(row == numOfRows - 1 || col == 0)) {addNeighbor(cell, gridOfCells, row+1, col-1, numOfRows, numOfCols, true);}
                if(!(row == numOfRows - 1 || col == numOfCols - 1)) {addNeighbor(cell, gridOfCells, row+1, col+1, numOfRows, numOfCols, true);}
            }
        }

        public void assignEdgeNeighborsSquare(Map<Point, Cell> gridOfCells, int numOfRows, int numOfCols) {
            for(Map.Entry<Point, Cell> cellEntry : gridOfCells.entrySet()) {
                Cell cell = cellEntry.getValue();
                cell.clearEdgeNeighbors();
                int row = cellEntry.getKey().getRow();
                int col = cellEntry.getKey().getCol();

                if (!(row == 0)) { addNeighbor(cell, gridOfCells, row - 1, col, numOfRows, numOfCols, false); }
                if (!(row == numOfRows - 1)) { addNeighbor(cell, gridOfCells, row + 1, col, numOfRows, numOfCols, false); }
                if (!(col == 0)) { addNeighbor(cell, gridOfCells, row, col - 1, numOfRows, numOfCols, false); }
                if (!(col == numOfCols - 1)) { addNeighbor(cell, gridOfCells, row, col + 1, numOfRows, numOfCols, false); }
            }
        }

        @Override
        public void assignNeighborsTriangular(Cell cell, int row, int col) {
            return;
        }

        @Override
        public void assignNeighborsHexagonal(Cell cell, int row, int col) {
            return;
        }

    },
    TOROIDAL {
        @Override
        public void assignCornerNeighborsSquare(Map<Point, Cell> gridOfCells, int numOfRows, int numOfCols) {
            for(Map.Entry<Point, Cell> cellEntry : gridOfCells.entrySet()) {
                Cell cell = cellEntry.getValue();
                int row = cellEntry.getKey().getRow();
                int col = cellEntry.getKey().getCol();

                addNeighbor(cell, gridOfCells, row-1, col-1, numOfRows, numOfCols, true);
                addNeighbor(cell, gridOfCells, row-1, col+1, numOfRows, numOfCols, true);
                addNeighbor(cell, gridOfCells, row+1, col-1, numOfRows, numOfCols, true);
                addNeighbor(cell, gridOfCells, row+1, col+1, numOfRows, numOfCols, true);
            }
        }

        @Override
        public void assignEdgeNeighborsSquare(Map<Point, Cell> gridOfCells, int numOfRows, int numOfCols) {
            for(Map.Entry<Point, Cell> cellEntry : gridOfCells.entrySet()) {
                Cell cell = cellEntry.getValue();
                int row = cellEntry.getKey().getRow();
                int col = cellEntry.getKey().getCol();

                addNeighbor(cell, gridOfCells, row, col-1, numOfRows, numOfCols, false);
                addNeighbor(cell, gridOfCells, row, col+1, numOfRows, numOfCols, false);
                addNeighbor(cell, gridOfCells, row+1, col, numOfRows, numOfCols, false);
                addNeighbor(cell, gridOfCells, row-1, col, numOfRows, numOfCols, false);
            }
        }

        @Override
        public void assignNeighborsTriangular(Cell cell, int row, int col) {
            return;
        }

        @Override
        public void assignNeighborsHexagonal(Cell cell, int row, int col) {
            return;
        }
    },
    INFINITE {
        @Override
        public void assignCornerNeighborsSquare(Map<Point, Cell> gridOfCells, int numOfRows, int numOfCols) {
            FINITE.assignCornerNeighborsSquare(gridOfCells, numOfRows, numOfCols);
        }

        @Override
        public void assignEdgeNeighborsSquare(Map<Point, Cell> gridOfCells, int numOfRows, int numOfCols) {
            FINITE.assignEdgeNeighborsSquare(gridOfCells, numOfRows, numOfCols);
        }

        @Override
        public void assignNeighborsTriangular(Cell cell, int row, int col) {
            FINITE.assignNeighborsTriangular(cell, row, col);
        }

        @Override
        public void assignNeighborsHexagonal(Cell cell, int row, int col) {
            FINITE.assignNeighborsHexagonal(cell, row, col);
        }
    };

    public abstract void assignCornerNeighborsSquare(Map<Point, Cell> gridOfCells, int numOfRows, int numOfCols);
    public abstract void assignEdgeNeighborsSquare(Map<Point, Cell> gridOfCells, int numOfRows, int numOfCols);
    public abstract void assignNeighborsTriangular(Cell cell, int row, int col);
    public abstract void assignNeighborsHexagonal(Cell cell, int row, int col);

    protected Cell cellFromPoint(Map<Point, Cell> gridOfCells, int row, int col, int numOfRows, int numOfCols) {
        if(row<0) {row = numOfRows-1;}
        else if(row>=numOfRows) {row = 0;}

        if(col<0) {col = numOfCols-1;}
        else if(col>=numOfCols) {col = 0;}

        return gridOfCells.get(new Point(row, col));
    }

    protected void addNeighbor(Cell cell, Map<Point, Cell> gridOfCells, int row, int col, int numOfRows, int numOfCols, boolean corner) {
        if(corner) {
            cell.addCornerNeighbor(cellFromPoint(gridOfCells, row, col, numOfRows, numOfCols));
        } else {
            cell.addEdgeNeighbor(cellFromPoint(gridOfCells, row, col, numOfRows, numOfCols));
        }
    }
}
