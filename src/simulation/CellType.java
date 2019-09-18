package simulation;

import javafx.scene.paint.Color;

public enum CellType {
    ALIVE(Color.BLACK),
    DEAD(Color.WHITE),
    EMPTY(Color.WHITE),
    DISATISFIED(Color.RED),
    SATISFIED(Color.BLUE);

    private Color myColor;

    public Color getMyColor() {
        return this.myColor;
    }

    CellType(Color color) {
        this.myColor = color;
    }

}
