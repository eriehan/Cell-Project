package userInterface;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;


public class InfoButton extends SimulationButton {
    private Alert alert;


    public InfoButton(String name, String title, String header, String content) {
        super(name);
        this.setStyle("-fx-background-radius: 5em; " +
                "-fx-min-width: 40px; " +
                "-fx-min-height: 40px; " +
                "-fx-max-width: 40px; " +
                "-fx-max-height: 40px;" +
                "-fx-background-color: MediumSeaGreen");
        this.setText(name);
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        this.setOnAction(value -> buttonAction());
    }

    private void buttonAction() {
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                System.out.println("Pressed OK to close.");
            }
        });
    }

}
