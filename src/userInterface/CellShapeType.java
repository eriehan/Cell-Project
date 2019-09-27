package userInterface;

import utils.Point;

import java.util.List;

public enum CellShapeType {
    RECTANGLE {
        @Override
        public List<Point> neighborDirections() {
            return List.of(new Point(-1, -1), new Point(-1, 0), new Point(-1, 1),
                    new Point(0, -1), new Point(0, 1), new Point(1, -1),
                    new Point(1, 0), new Point(1, 1));
        }

        @Override
        public List<Point> edgeNeighborDirections() {
            System.out.println("sdfs");
            return List.of(new Point(-1, 0), new Point(-1, 0),
                    new Point(0, -1), new Point(0, 1));
        }
    },
    TRIANGLE {
        @Override
        public List<Point> neighborDirections() {
            return null;
        }

        @Override
        public List<Point> edgeNeighborDirections() {
            return null;
        }
    },
    HEXAGON {
        @Override
        public List<Point> neighborDirections() {
            return null;
        }

        @Override
        public List<Point> edgeNeighborDirections() {
            return null;
        }
    };

    public abstract List<Point> neighborDirections();

    public abstract List<Point> edgeNeighborDirections();

}
