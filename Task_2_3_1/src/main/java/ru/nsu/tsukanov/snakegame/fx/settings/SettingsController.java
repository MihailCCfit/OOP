package ru.nsu.tsukanov.snakegame.fx.settings;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.nsu.tsukanov.snakegame.console.GameSettings;
import ru.nsu.tsukanov.snakegame.fx.GlobalGameSettings;

import java.io.IOException;

public class SettingsController {
    private final GameSettings gameSettings = GlobalGameSettings.gameSettings;

    public void switchToMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/menu-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
