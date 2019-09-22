package userInterface;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;



public class TriangleGridView extends AbstractGridView {
    private Group myTriangleGroup;

    public TriangleGridView(int numOfRows, int numOfCols) {
        super(numOfRows, numOfCols);
        myTriangleGroup = new Group();
        getMyGridPane().getChildren().add(myTriangleGroup);
    }

    @Override
    public void generateBlankGrid() {
        Rectangle rectangle = new Rectangle(this.getGridWidth(), this.getGridHeight());
        rectangle.setFill(Color.LIGHTBLUE);
        myTriangleGroup.getChildren().add(rectangle);
    }

    @Override
    public void updateGrid() {
        if (getMyCellGrid() == null) {
            return;
        }
        myTriangleGroup.getChildren().clear();
        getMyCellGrid().checkAllCells();
        getMyCellGrid().changeAllCells();
        createGrid();
    }

    @Override
    public void createGrid() {
        boolean rowFlag = true;
        double width = this.getGridWidth() / getNumOfCols() * 2;
        double height = this.getGridHeight() / getNumOfRows();
        for (int r = 0; r < getNumOfRows(); r++) {
            for (int c = 0; c < getNumOfCols() / 2; c++) {
                Polygon upTriangle = new Polygon();
                upTriangle.getPoints().addAll(
                        0.0, 0.0,
                        width, 0.0,
                        width / 2, height);
                upTriangle.setLayoutY(r * height);
                myTriangleGroup.getChildren().add(upTriangle);

                Polygon downTriangle = new Polygon();
                downTriangle.getPoints().addAll(
                        width / 2, 0.0,
                        0.0, height,
                        width, height);
                downTriangle.setLayoutY(r * height);
                myTriangleGroup.getChildren().add(downTriangle);

                Color firstColor = getMyCellGrid().stateOfCellAtPoint(r, c * 2).getMyColor();
                Color secondColor = getMyCellGrid().stateOfCellAtPoint(r, c * 2 + 1).getMyColor();
                upTriangle.setLayoutX(rowFlag ? c * width : (c + 0.5) * width);
                upTriangle.setFill(rowFlag ? firstColor : secondColor);
                downTriangle.setLayoutX(rowFlag ? (c + 0.5) * width : c * width);
                downTriangle.setFill(rowFlag ? secondColor : firstColor);
            }
            rowFlag = !rowFlag;
        }
    }


}

