package userInterface;

import utils.Point;

import java.util.List;

public enum CellShapeType {
    /*protected final Point W = new Point(0, -1);
    protected final Point E = new Point(0, 1);
    protected final Point S = new Point(1, 0);
    protected final Point N = new Point(-1, 0);
    protected final Point SW = new Point(1, -1);
    protected final Point SE = new Point(1, 1);
    protected final Point NW = new Point(1, -1);
    protected final Point NE = new Point(1, 1);
    protected final Point WW = new Point(0, -2);
    protected final Point EE = new Point(0, 2);
    protected final Point SWW = new Point(1, -2);
    protected final Point SEE = new Point(1, 2);
    protected final Point NWW= new Point(-1, -2);
    protected final Point NEE= new Point(-1, 2);*/

    RECTANGLE {
        @Override
        public List<Point> neighborDirections(int row, int col) {
            return List.of(new Point(1, 1), new Point(1, -1), new Point(1, 0),
                    new Point(0, -1), new Point(0, 1), new Point(-1, 1),
                    new Point(-1, 0), new Point(-1, -1));
        }

        @Override
        public List<Point> edgeNeighborDirections(int row, int col) {
            return List.of(new Point(-1, 0), new Point(1, 0),
                    new Point(0, -1), new Point(0, 1));
        }
    },
    TRIANGLE {
        @Override
        public List<Point> neighborDirections(int row, int col) {
            if(row+col % 2  == 0) {
                return List.of(new Point(1, 0), new Point(1, -1), new Point(1, 1),
                        new Point(0, -2), new Point(0, -1), new Point(0, 1),
                        new Point(0, 2), new Point(-1, -2), new Point(-1, -1),
                        new Point(-1, 0), new Point(-1, 1), new Point(-1, 2));
            } else {
                return List.of(new Point(-1, 0), new Point(-1, -1), new Point(-1, 1),
                        new Point(0, -2), new Point(0, -1), new Point(0, 1),
                        new Point(0, 2), new Point(1, -2), new Point(1, -1),
                        new Point(1, 0), new Point(1, 1), new Point(1, 2));
            }
        }

        @Override
        public List<Point> edgeNeighborDirections(int row, int col) {
            if(row + col % 2 == 0) {
                return List.of(new Point(0, -1), new Point(0, 1), new Point(-1, 0));
            } else {
                return List.of(new Point(0, -1), new Point(0, 1), new Point(1, 0));
            }
        }
    },
    HEXAGON {
        @Override
        public List<Point> neighborDirections(int row, int col) {
            return null;
        }

        @Override
        public List<Point> edgeNeighborDirections(int row, int col) {
            return null;
        }
    };

    public abstract List<Point> neighborDirections(int row, int col);

    public abstract List<Point> edgeNeighborDirections(int row, int col);

}
