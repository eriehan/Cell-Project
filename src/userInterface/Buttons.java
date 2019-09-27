package userInterface;

import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;

public class Buttons {
    private List<Button> buttonList;

    public Buttons() {
        buttonList = new ArrayList<>();
    }

    public List<Button> getButtonList() {
        return buttonList;
    }
}
