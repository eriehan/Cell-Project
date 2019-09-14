package userInterface;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import static userInterface.VisualizationConstants.*;

public class UserInterface {
    private GridView myGridView;
    private Buttons myButtons;
    private final int numOfCols;
    private final int numOfRows;
    private final String mySimulationName;

    public UserInterface(int numOfCols, int numOfRows, String simulationName) {
        this.numOfCols = numOfCols;
        this.numOfRows = numOfRows;
        this.mySimulationName = simulationName;
        myGridView = new GridView(numOfCols, numOfRows);
        myButtons = new Buttons();
    }

    public Group setScene() {
        var root = new Group();

        VBox colOne = new VBox(20);
        VBox colTwo = new VBox(20);

        HBox hBox = new HBox(20);

        Text simulatioTitle = new Text(mySimulationName);
        simulatioTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));


        hBox.setPadding(new Insets(10, 50, 50, 50));
        colOne.getChildren().addAll(simulatioTitle, myGridView.getMyGridPane());
        colTwo.getChildren().addAll(myButtons.getButtonList());
        hBox.getChildren().addAll(colOne, colTwo);
        root.getChildren().add(hBox);
        return root;
    }

    public GridView getMyGridView() {
        return myGridView;
    }

    public Buttons getMyButtons() {
        return myButtons;
    }
}
