package userInterface;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;


public class NeighborButtonGrid {
    private static final int SPACING = 5;
    private static final Label LABEL = new Label("Select cell neighbors");
    public static final int NUM_ROW = 3;
    public static final int NUM_COL = 3;
    private GridPane buttonGrid;
    private VBox myView;
    private List<NeighborButton> buttonList;

    public NeighborButtonGrid() {
        buttonGrid = new GridPane();
        myView = new VBox(SPACING);
        myView.getChildren().add(LABEL);
        myView.getChildren().add(buttonGrid);
        buttonList = new ArrayList<>();
        for (int r=0;r<NUM_ROW;r++){
            for (int c=0;c<NUM_COL;c++){
                NeighborButton neighborButton = new NeighborButton(r*NUM_COL + c);
                System.out.println(r*NUM_COL + c);
                buttonList.add(neighborButton);
                buttonGrid.add(neighborButton,c,r);
            }
        }
    }

    public VBox getMyView() {
        return myView;
    }

    public List<NeighborButton> getButtonList() {
        List<NeighborButton> list = new ArrayList<>();
        list.addAll(buttonList);
        return list;
    }
}
