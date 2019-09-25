package userInterface;

import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;


public class HexagonGridView extends AbstractGridView {
    private Group myHexagonGroup;

    public HexagonGridView(int numOfRows, int numOfCols) {
        super(numOfRows, numOfCols);
        this.setGridHeight(this.getGridHeight()/2);
        myHexagonGroup = new Group();
        getMyGridPane().getChildren().add(myHexagonGroup);
    }

    @Override
    public void generateBlankGrid() {
        Rectangle rectangle = new Rectangle(this.getGridWidth(), this.getGridHeight());
        rectangle.setFill(Color.LIGHTBLUE);
        myHexagonGroup.getChildren().add(rectangle);
    }

    @Override
    public void updateGrid() {
        if (getMyCellGrid() == null) return;
        myHexagonGroup.getChildren().clear();
        getMyCellGrid().checkAllCells();
        getMyCellGrid().changeAllCells();
        createGrid();
    }

    @Override
    public void createGrid() {
        boolean rowFlag = true;
        double width = this.getGridWidth() / (getNumOfCols()*1.5 + 0.25) ;
        double height = this.getGridHeight() / (getNumOfRows()*0.5 + 0.5);
        for (int r = 0; r < getNumOfRows(); r++) {
            for (int c = 0; c < getNumOfCols(); c++) {
                Polygon hexagon = new Polygon();
                hexagon.getPoints().addAll(
                        0.0, height/2,
                        0.25*width, 0.0,
                        0.75*width, 0.0,
                        width, height/2,
                        0.75*width, height,
                        0.25*width, height);
                hexagon.setLayoutY(r * height / 2);
                hexagon.setLayoutX(rowFlag? 0.75*width + c*1.5*width : c*1.5*width);
                myHexagonGroup.getChildren().add(hexagon);
                hexagon.setFill(getMyCellGrid().stateOfCellAtPoint(r, c ).getMyColor());
                final int row = r;
                final int col = c;
                hexagon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> changeState(row,col));
            }
            rowFlag = !rowFlag;
        }
    }

    private void changeState(int r, int c) {
        this.getMyCellGrid().setStateOfCellAtPointOnClick(r, c);
        createGrid();
    }


}

