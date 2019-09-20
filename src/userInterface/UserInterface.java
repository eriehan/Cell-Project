package userInterface;

import javafx.animation.Animation;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import static userInterface.VisualizationConstants.ERROR_MSG_FONT_SIZE;
import static userInterface.VisualizationConstants.FILE_PATH_FONT_SIZE;

public class UserInterface {
    private AbstractGridView myGridView;
    private Group myGroup;
    private VBox colTwo;
    private VBox colOne;
    private Buttons myButtons;
    private int numOfCols;
    private int numOfRows;
    private Text simulationTitle;
    private int errorMsgTimeLimit = 5;
    private int errorMsgTimer = -1;
    private Text errorMsg;
    private Text simulationFilePath;

    public UserInterface(int numofRows, int numofCols, String simulationName) {
        this.numOfRows = numofRows;
        this.numOfCols = numofCols;
        this.simulationTitle = new Text(simulationName);
        simulationTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        myGridView = new GridView(numOfCols, numOfRows);
        myButtons = new Buttons();
    }

    public Group setScene() {
        var root = new Group();
        colOne = new VBox(20);
        colTwo = new VBox(20);
        HBox hBox = new HBox(20);
        hBox.setPadding(new Insets(10, 50, 50, 50));
        colOne.getChildren().addAll(simulationTitle, myGridView.getMyGridPane());
        colTwo.getChildren().addAll(myButtons.getButtonList());
        colTwo.setPadding(new Insets(30, 0, 0, 0));
        hBox.getChildren().addAll(colOne, colTwo);
        root.getChildren().add(hBox);
        return root;
    }

    public void update() {
        myGridView.updateGrid();
        if (this.errorMsgTimer != -1) errorMsgTimer++;
        if (this.errorMsgTimer > this.errorMsgTimeLimit) {
            this.colTwo.getChildren().remove(this.errorMsg);
            this.errorMsgTimer = -1;
        }
    }

    public AbstractGridView getMyGridView() {
        return myGridView;
    }

    public Buttons getMyButtons() {
        return myButtons;
    }

    public void changeTitle(String s) {
        simulationTitle.setText(s);
    }

    public void setNumOfRows(int n) {
        numOfRows = n;
        this.getMyGridView().setNumOfRows(n);
    }

    public void setNumOfCols(int n) {
        numOfCols = n;
        this.getMyGridView().setNumOfCols(n);
    }

    public void displayErrorMsg(String errorMessage) {
        if (errorMsgTimer != -1) {
            this.colTwo.getChildren().remove(this.errorMsg);
        }
        this.errorMsg = new Text(errorMessage);
        this.errorMsg.setFont(Font.font("Arial", FontWeight.BOLD, ERROR_MSG_FONT_SIZE));
        this.errorMsg.setFill(Color.ORANGE);
        this.colTwo.getChildren().add(this.errorMsg);
        this.errorMsgTimer = 0;
    }

    public void displaySimulationFilePath(String filePath) {
        this.simulationFilePath = new Text(filePath);
        this.simulationFilePath.setFont(Font.font("Arial", FontWeight.BOLD, FILE_PATH_FONT_SIZE));
        this.simulationFilePath.setFill(Color.CORNFLOWERBLUE);
        this.colOne.getChildren().add(this.simulationFilePath);
    }

    public void setCellShape(CellShapeType type){
        if (type == CellShapeType.RECTANGLE) myGridView = new GridView(numOfRows,numOfCols);
        if (type == CellShapeType.TRIANGLE) myGridView = new TriangleGridView(numOfRows,numOfCols);
        myGridView.generateBlankGrid();
        colOne.getChildren().clear();
        colOne.getChildren().addAll(simulationTitle, myGridView.getMyGridPane());
    }



}
