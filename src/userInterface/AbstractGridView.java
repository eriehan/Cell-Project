package userInterface;

import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import simulation.CellState;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import static userInterface.VisualizationConstants.BLANK_GRID_COLOR;

public abstract class AbstractGridView {
    private static final int SPACING = 10;
    private static final String RESOURCE_FILE_PATH = "resources/MainResources";
    private GridPane myGridPane;
    private SimulationGraph simulationGraph;
    private VBox myView;
    private int round = 0;
    private int numOfRows;
    private int numOfCols;
    private int gridWidth;
    private int gridHeight;
    private GridManager gridManager;
    private ResourceBundle resourceBundle;


    public AbstractGridView(int numOfRows, int numOfCols) {
        myGridPane = new GridPane();
        addSimulationGraph();
        this.numOfCols = numOfCols;
        this.numOfRows = numOfRows;
        this.resourceBundle = ResourceBundle.getBundle(RESOURCE_FILE_PATH);
        this.gridWidth = Integer.parseInt(resourceBundle.getString("GridWidth"));
        this.gridHeight = Integer.parseInt(resourceBundle.getString("GridHeight"));
        this.gridManager = new GridManager();
        this.myView = new VBox(SPACING);
        this.myView.getChildren().addAll(this.myGridPane, this.simulationGraph);

    }

    public abstract void createGrid();

    protected void addSimulationGraph() {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        this.simulationGraph = new SimulationGraph(xAxis, yAxis);
    }

    public void generateBlankGrid() {
        Rectangle shape = new Rectangle(this.gridWidth, this.gridHeight);
        shape.setFill(BLANK_GRID_COLOR);
        myGridPane.add(shape, 0, 0);
    }

    public void updateGrid() {
        if (gridManager == null || gridManager.getCellGrid() == null) {
            return;
        }
        gridManager.getCellGrid().checkAllCells();
        gridManager.getCellGrid().changeAllCells();
        updateSeries();
        displayGrid();
    }

    private void updateSeries() {
        round += 1;
        List<CellState> stateList = this.gridManager.getStateList();
        for (int i = 0; i < this.simulationGraph.seriesList.size(); i++) {
            CellState state = stateList.get(i);
            updatePercentageForState(state, round, this.simulationGraph.seriesList.get(i));
        }
    }

    public void addSeriesToChart() {
        if (this.getGridManager().getCellGrid() == null) return;
        List<CellState> stateList = this.gridManager.getStateList();
        for (int i = 0; i < stateList.size(); i++) {
            XYChart.Series line = new XYChart.Series();
            line.setName(stateList.get(i).toString());
            simulationGraph.seriesList.add(line);
            simulationGraph.getData().add(line);
            updatePercentageForState(stateList.get(i), round, this.simulationGraph.seriesList.get(i));
        }
    }

    private void updatePercentageForState(CellState state, int round, XYChart.Series line) {
        Map<CellState, Integer> statesCount = this.gridManager.getCellGrid().countStates();
        if (statesCount.get(state) == null) {
            statesCount.put(state, 0);
        }
        double percent = ((double) statesCount.get(state) / (getNumOfCols() * getNumOfRows())) * 100;
        line.getData().add(new XYChart.Data<>(round, percent));
    }


    public abstract void displayGrid();

    public void resetGridView() {
        this.getGridManager().resetCellGrid();
        simulationGraph.getData().clear();
        simulationGraph.seriesList.clear();
        round = 0;
        addSeriesToChart();
        displayGrid();
    }

    public GridPane getMyGridPane() {
        return myGridPane;
    }

    protected int getNumOfRows() {
        return numOfRows;
    }

    public void setNumOfRows(int n) {
        numOfRows = n;
    }

    protected int getNumOfCols() {
        return numOfCols;
    }

    public void setNumOfCols(int n) {
        numOfCols = n;
    }

    public GridManager getGridManager() {
        return gridManager;
    }

    protected int getGridWidth() {
        return this.gridWidth;
    }

    protected int getGridHeight() {
        return this.gridHeight;
    }

    protected void setGridHeight(int gridHeight) {
        this.gridHeight = gridHeight;
    }

    public VBox getMyView() {
        return myView;
    }

    public SimulationGraph getSimulationGraph() {
        return simulationGraph;
    }
}

