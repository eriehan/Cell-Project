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

    OPEN(Color.WHITE),
    PERCOLATED(Color.BLUE),
    BLOCKED(Color.BLACK),

    WATER(Color.BLUE),
    FISH(Color.CYAN),
    SHARK(Color.GOLD),

    ROCK(Color.BLUE),
    PAPER(Color.RED),
    SCISSOR(Color.GREEN);


    private Color myColor;

    CellState(Color color) {
        this.myColor = color;
    }

    public Color getMyColor() {
        return this.myColor;
    }

}
