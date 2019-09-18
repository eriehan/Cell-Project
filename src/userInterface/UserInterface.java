package userInterface;

import javafx.animation.Animation;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class UserInterface {
    private GridView myGridView;
    private Buttons myButtons;
    private final int numOfCols;
    private final int numOfRows;
    private final String mySimulationName;
    private Animation myAnimation;

    public UserInterface(int numOfCols, int numOfRows, String simulationName, Animation animation) {
        this.numOfCols = numOfCols;
        this.numOfRows = numOfRows;
        this.mySimulationName = simulationName;
        myGridView = new GridView(numOfCols, numOfRows);
        myAnimation = animation;
        myButtons = new Buttons(myAnimation);
    }

    public Group setScene() {
        var root = new Group();

        VBox colOne = new VBox(20);
        VBox colTwo = new VBox(20);

        HBox hBox = new HBox(20);

        Text simulationTitle = new Text(mySimulationName);
        simulationTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));


        hBox.setPadding(new Insets(10, 50, 50, 50));
        colOne.getChildren().addAll(simulationTitle, myGridView.getMyGridPane());
        colTwo.getChildren().addAll(myButtons.getButtonList());
        colTwo.setPadding(new Insets(30, 0, 0, 0));
        hBox.getChildren().addAll(colOne, colTwo);
        root.getChildren().add(hBox);
        return root;
    }

    public void update(){
        myGridView.updateGrid();
    }

    public GridView getMyGridView() {
        return myGridView;
    }

    public Buttons getMyButtons() {
        return myButtons;
    }
}
