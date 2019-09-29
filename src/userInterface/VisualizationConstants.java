package userInterface;


import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class VisualizationConstants {

    public static final Paint BACKGROUND_COLOR = Color.AZURE;
    public static final Paint BLANK_GRID_COLOR = Color.LIGHTBLUE;
    public static final int BUTTON_FONT_SIZE = 20;
    public static final int SLIDER_FONT_SIZE = 15;
    public static final int ERROR_MSG_FONT_SIZE = 15;
    public static final int FILE_PATH_FONT_SIZE = 12;
    public static final int SLIDER_WIDTH = 200;
    public static final String NEIGHBOR_STYLE = "-fx-border-color: lightgrey; -fx-background-color: LightYellow";
    public static final String CENTER_COLOR = "-fx-border-color: lightgrey; -fx-background-color: Yellow";
    public static final String NEIGHBOR_CHOSEN = "-fx-border-color: lightgrey; -fx-background-color: LightBlue";

    private VisualizationConstants() {
        // private constructor for static public class.
    }
}
