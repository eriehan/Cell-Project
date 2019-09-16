package userInterface;

import javafx.scene.control.Button;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class Buttons {
    private ArrayList<Button> buttonList;

    public Buttons() {
        buttonList = new ArrayList<>();
        initializeButtons();
    }

    private void initializeButtons() {
        createButton(20, "select file");
        createButton(20, "Start");
        createButton(20, "Pause");
        createButton(20, "speed up");
        createButton(20, "slow down");

        /*Button selectFile = new Button();
        selectFile.setFont(new Font(20));
        selectFile.setText("select file");
        selectFile.setOnAction(value -> {
            //TODO: actions on backend
        });
        buttonList.add(selectFile);

        Button start = new Button();
        start.setFont(new Font(20));
        start.setText("Start");
        start.setOnAction(value -> {
            //TODO: actions on backend
        });
        buttonList.add(start);


        Button pause = new Button();
        pause.setFont(new Font(20));
        pause.setText("Pause");
        pause.setOnAction(value -> {
            //TODO: actions on backend
        });
        buttonList.add(pause);

        Button speedUp = new Button();
        speedUp.setFont(new Font(20));
        speedUp.setText("speed up");
        speedUp.setOnAction(value -> {
            //TODO: actions on backend
        });
        buttonList.add(speedUp);

        Button slowDown = new Button();
        slowDown.setFont(new Font(20));
        slowDown.setText("slow down");
        slowDown.setOnAction(value -> {
            //TODO: actions on backend
        });
        buttonList.add(slowDown);*/

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
    }

    public ArrayList<Button> getButtonList() {
        return buttonList;
    }
}
