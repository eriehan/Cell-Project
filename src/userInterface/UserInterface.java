package userInterface;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ResourceBundle;

import static userInterface.VisualizationConstants.ERROR_MSG_FONT_SIZE;
import static userInterface.VisualizationConstants.FILE_PATH_FONT_SIZE;

public class UserInterface {
    private static final String RESOURCE_FILE_PATH = "resources/MainResources";

    private AbstractGridView myGridView;
    private VBox colTwo;
    private VBox colOne;
    private Buttons myButtons;
    private SlidersAndControls mySlidersAndControls;
    private int numOfCols;
    private int numOfRows;
    private Text simulationTitle;
    private int errorMsgTimeLimit = 5;
    private int errorMsgTimer = -1;
    private Text errorMsg;
    private Text simulationFilePath;
    private ResourceBundle resourceBundle;

    public UserInterface(int numofRows, int numofCols, String simulationName) {
        this.numOfRows = numofRows;
        this.numOfCols = numofCols;
        this.simulationTitle = new Text(simulationName);
        this.resourceBundle = ResourceBundle.getBundle(RESOURCE_FILE_PATH);
        simulationTitle.setFont(Font.font("Arial", FontWeight.BOLD, Integer.parseInt(resourceBundle.getString("TitleFont"))));
        myGridView = new RectangleGridView(numOfCols, numOfRows);
        myButtons = new Buttons();
        mySlidersAndControls = new SlidersAndControls();
    }

    public Group setScene() {
        var root = new Group();
        colOne = new VBox(20);
        colTwo = new VBox(20);
        HBox hBox = new HBox(20);
        hBox.setPadding(new Insets(20, 50, 50, 50));
        colOne.getChildren().addAll(simulationTitle, myGridView.getMyGridPane());
        colTwo.getChildren().addAll(myButtons.getButtonList());
        colTwo.getChildren().addAll(mySlidersAndControls.getMyCol());
        colTwo.setPadding(new Insets(70, 0, 0, 0));
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

    public SlidersAndControls getMySlidersAndControls() {
        return mySlidersAndControls;
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

    public void setCellShape(CellShapeType type) {
        if (type == CellShapeType.RECTANGLE) myGridView = new RectangleGridView(numOfRows, numOfCols);
        if (type == CellShapeType.TRIANGLE) myGridView = new TriangleGridView(numOfRows, numOfCols);
        if (type == CellShapeType.HEXAGON) myGridView = new HexagonGridView(numOfRows, numOfCols);
        myGridView.generateBlankGrid();
        colOne.getChildren().clear();
        colOne.getChildren().addAll(simulationTitle, myGridView.getMyGridPane());
    }

}
