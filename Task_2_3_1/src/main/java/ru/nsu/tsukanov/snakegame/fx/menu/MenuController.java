package ru.nsu.tsukanov.snakegame.fx.menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.nsu.tsukanov.snakegame.console.GameSettings;
import ru.nsu.tsukanov.snakegame.fx.GlobalGameSettings;
import ru.nsu.tsukanov.snakegame.fx.game.GameController;

import java.io.IOException;

public class MenuController {
    private final GameSettings gameSettings = GlobalGameSettings.gameSettings;


    public void switchToPlay(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/game-view.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        GameController gameController = loader.getController();
        gameController.init();
        stage.setScene(scene);
        stage.show();

    }

    public void switchToSettings(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/settings.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.show();
    }

    public void switchToConstructor(ActionEvent event) throws IOException {

    }


    public void exit(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}