package userInterface;

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
    private static final Font TITLE_FONT = Font.font("Arial", FontWeight.BOLD, 30);
    private static final int ERROR_MSG_TIME_LIMIT = 5;
    private static final int SPACING = 20;
    private static final double PADDING_TOP = 20;
    private static final double PADDING_OTHER = 50;
    private AbstractGridView myGridView;
    private VBox titleAndGridCol;
    private HBox hBox;
    private ControlsManager myControlsManager;
    private int numOfCols;
    private int numOfRows;
    private Text simulationTitle;
    private int errorMsgTimer = -1;
    private Text errorMsg;
    private Text simulationFilePath;
    private PauseButton pauseButton;


    public UserInterface(String simulationName) {
        this.simulationTitle = new Text(simulationName);
        simulationTitle.setFont(TITLE_FONT);
        myGridView = new RectangleGridView(numOfCols, numOfRows);
        myControlsManager = new ControlsManager();
    }

    public Group setScene() {
        var root = new Group();
        titleAndGridCol = new VBox(SPACING);
        hBox = new HBox(SPACING);
        hBox.setPadding(new Insets(PADDING_TOP, PADDING_OTHER, PADDING_OTHER, PADDING_OTHER));
        titleAndGridCol.getChildren().addAll(simulationTitle, myGridView.getMyGridPane());
        hBox.getChildren().add(titleAndGridCol);
        hBox.getChildren().add(myControlsManager.getMyPane());

        root.getChildren().add(hBox);
        return root;
    }

    public void update() {
        myGridView.updateGrid();
        if (this.errorMsgTimer != -1) errorMsgTimer++;
        if (this.errorMsgTimer > ERROR_MSG_TIME_LIMIT) {
            this.getMyControlsManager().getMyConstantCol().getChildren().remove(this.errorMsg);
            this.errorMsgTimer = -1;
        }
    }

    public void addSimulationControls() {
        this.getMyGridView().getGridManager().getCellGrid().initializeControlPanel();
        this.getMyControlsManager().getMySimulationCol().getChildren().clear();
        this.getMyControlsManager().getMySimulationCol().getChildren().add(this.getMyGridView().getGridManager().getCellGrid().getControlPanel().getMyColPane());
    }

    public AbstractGridView getMyGridView() {
        return myGridView;
    }


    public ControlsManager getMyControlsManager() {
        return myControlsManager;
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
            this.getMyControlsManager().getMyConstantCol().getChildren().remove(this.errorMsg);
        }
        this.errorMsg = new Text(errorMessage);
        this.errorMsg.setFont(Font.font("Arial", FontWeight.BOLD, ERROR_MSG_FONT_SIZE));
        this.errorMsg.setFill(Color.ORANGE);
        this.getMyControlsManager().getMyConstantCol().getChildren().add(this.errorMsg);
        this.errorMsgTimer = 0;
    }

    public void displaySimulationFilePath(String filePath) {
        this.simulationFilePath = new Text(filePath);
        this.simulationFilePath.setFont(Font.font("Arial", FontWeight.BOLD, FILE_PATH_FONT_SIZE));
        this.simulationFilePath.setFill(Color.CORNFLOWERBLUE);
        this.titleAndGridCol.getChildren().add(this.simulationFilePath);
    }

    public void setCellShape(CellShapeType type) {
        if (type == CellShapeType.RECTANGLE) {

            myGridView = new RectangleGridView(numOfRows, numOfCols);
        }
        if (type == CellShapeType.TRIANGLE) {
            myGridView = new TriangleGridView(numOfRows, numOfCols);
        }
        if (type == CellShapeType.HEXAGON) {
            myGridView = new HexagonGridView(numOfRows, numOfCols);
        }
        myGridView.generateBlankGrid();
        titleAndGridCol.getChildren().clear();
        titleAndGridCol.getChildren().addAll(simulationTitle, myGridView.getMyGridPane());
    }

    public PauseButton getPauseButton() {
        return pauseButton;
    }

    public void setPauseButton(PauseButton button) {
        pauseButton = button;
    }
}
