package ru.nsu.tsukanov.snakegame.fx.settings;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import ru.nsu.tsukanov.snakegame.console.GameSettings;
import ru.nsu.tsukanov.snakegame.console.settings.UserMode;
import ru.nsu.tsukanov.snakegame.fx.GlobalGameSettings;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {
    private final GameSettings gameSettings = GlobalGameSettings.gameSettings;


    @FXML
    private CheckBox observerBox;
    @FXML
    private Slider levelSlider;

    public void switchToMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/menu-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        observerBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(true)) {
                gameSettings.setUserMode(UserMode.Observer);
            } else {
                gameSettings.setUserMode(UserMode.Player);
            }
            System.out.println(gameSettings.getUserMode());
        });
        levelSlider.setMin(1);
        levelSlider.setMax(5);
        levelSlider.setValue(gameSettings.getGameSpeed());
        levelSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            gameSettings.setGameSpeed(newValue.intValue());
        });


    }


}
