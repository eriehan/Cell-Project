package utils;

public class Point {
    private int row;
    private int col;

    public Point(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Point && ((Point) other).getRow()==row && ((Point) other).getCol()==col;
    }
}
