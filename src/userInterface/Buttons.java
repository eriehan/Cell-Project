package userInterface;

import javafx.animation.Animation;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class Buttons {
    private ArrayList<Button> buttonList;
    private Animation myAnimation;

    public Buttons(Animation animation) {
        buttonList = new ArrayList<>();
        myAnimation = animation;
        initializeButtons();
    }

    private void initializeButtons() {
        buttonList.add(new SelectFileButton());
        buttonList.add(new StartButton(myAnimation));
        buttonList.add(new PauseButton(myAnimation));
        buttonList.add(new SpeedUpButton());
        buttonList.add(new SlowDownButton());
        buttonList.add(new StepButton());


    }

    private void createButton(int textSize, String text) {
        Button button = new Button();
        button.setFont(new Font(textSize));
        button.setText(text);
        button.setOnAction(value -> buttonAction(text));
        buttonList.add(button);
    }

    private void buttonAction(String type) {
        //to be added
        // TODO: update buttons methods (pause, start, end, speed up and down, step forward)
    }

    public ArrayList<Button> getButtonList() {
        return buttonList;
    }
}
