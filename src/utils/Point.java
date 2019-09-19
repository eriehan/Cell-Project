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

    public void setRow(int r){
        this.row = r;
    }

    public void setCol(int c){
        this.col = c;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Point && ((Point) other).getRow()==row && ((Point) other).getCol()==col;
    }

    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
