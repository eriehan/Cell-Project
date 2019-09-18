package simulation;

import javafx.scene.paint.Color;

public enum CellType {
    ALIVE(Color.BLACK),
    DEAD(Color.WHITE);

    private Color myColor;

    public Color getMyColor() {
        return this.myColor;
    }

    CellType(Color color) {
        this.myColor = color;
    }

}
