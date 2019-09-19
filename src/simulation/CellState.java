package simulation;

import javafx.scene.paint.Color;

public enum CellState {
    ALIVE(Color.BLACK),
    DEAD(Color.WHITE),
    EMPTY(Color.WHITE),

    DISATISFIED(Color.RED),
    FIRSTAGENT(Color.RED),
    SECONDAGENT(Color.BLUE),

    FIREEMPTY(Color.YELLOW),
    BURNING(Color.DARKRED),
    TREE(Color.GREEN),

    PERCOLATED(Color.BLUE),
    BLOCKED(Color.BLACK);



    private Color myColor;

    CellState(Color color) {
        this.myColor = color;
    }

    public Color getMyColor() {
        return this.myColor;
    }

}
