package ru.nsu.tsukanov.snakegame.fx.constructor;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ru.nsu.tsukanov.snakegame.console.GameSettings;
import ru.nsu.tsukanov.snakegame.fx.GlobalGameSettings;
import ru.nsu.tsukanov.snakegame.fx.game.ImageCollector;
import ru.nsu.tsukanov.snakegame.model.game.field.FieldConstructor;
import ru.nsu.tsukanov.snakegame.model.game.field.FieldDAO;
import ru.nsu.tsukanov.snakegame.model.game.field.GameField;
import ru.nsu.tsukanov.snakegame.model.game.logic.Game;
import ru.nsu.tsukanov.snakegame.model.units.*;
import ru.nsu.tsukanov.snakegame.units.*;

import java.io.IOException;
import java.util.List;

public class ConstructorController {
    public Canvas canvas;
    public RadioButton wallRadioButton;
    public RadioButton foodRadioButton;
    public RadioButton snakeRadioButton;
    public RadioButton emptyRadioButton;
    private GraphicsContext gc;
    private double cellWidth;
    private double cellHeight;
    private double gameWidth;
    private double gameHeight;
    public ChoiceBox<GameUnitEnum> unitChoiceBox;
    private Game game;
    private FieldConstructor fieldConstructor;
    private GameUnit currentGameUnit = new Empty(0, 0);
    private FieldDAO fieldDAO;

    public void init() {

        GameSettings gameSettings = GlobalGameSettings.gameSettings;
        gc = canvas.getGraphicsContext2D();

        fieldDAO = new FieldDAO(gameSettings.getFile());
        GameField field = fieldDAO.getField().getField();
        fieldConstructor = new FieldConstructor(field.width(), field.height());
        field.getAll().forEach((gameUnit) -> fieldConstructor.setUnit(gameUnit));
        game = fieldConstructor.getGameField();

        cellWidth = canvas.getWidth() / (gameWidth = game.width());
        cellHeight = canvas.getHeight() / (gameHeight = game.height());
        update();
        ToggleGroup group = new ToggleGroup();
        emptyRadioButton.setToggleGroup(group);
        snakeRadioButton.setToggleGroup(group);
        wallRadioButton.setToggleGroup(group);
        foodRadioButton.setToggleGroup(group);


        wallRadioButton.setOnAction((event -> {
            currentGameUnit = new Wall(0, 0);
        }));
        foodRadioButton.setOnAction((event -> {
            currentGameUnit = new Food(0, 0, 1);
        }));
        snakeRadioButton.setOnAction((event -> {
            currentGameUnit = new SnakeBody(0, 0);
        }));
        emptyRadioButton.setOnAction((event -> {
            currentGameUnit = new Empty(0, 0);
        }));

    }

    private void drawField() {
        for (int x = 0; x < gameWidth; x++) {
            for (int y = 0; y < gameHeight; y++) {
                ImageView imageView;
                if (((x + y) & 1) == 0) {
                    imageView = new ImageView(ImageCollector.light_grass);
                } else {
                    imageView = new ImageView(ImageCollector.dark_grass);
                }
                gc.drawImage(imageView.getImage(), x * cellWidth, y * cellHeight,
                        cellWidth, cellHeight);

            }
        }
    }

    public void update() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        drawField();
        drawGameObjects(GameStateDTO.getGameState(game));
    }

    private void drawGameObjects(GameStateDTO gameStateDTO) {
        gameStateDTO.foodDTOS().forEach(this::drawFood);
        gameStateDTO.walls().forEach(this::drawWall);
        gameStateDTO.snakes().forEach(this::drawSnake);
    }

    private void drawFood(FoodDTO foodDTO) {
        PointDTO point = foodDTO.foodPoint();
        gc.drawImage(ImageCollector.getFood(foodDTO.value()), point.x() * cellWidth, point.y() * cellHeight,
                cellWidth, cellHeight);
    }

    private void drawSnake(SnakeDTO snakeDTO) {
        if (!snakeDTO.isAlive()) {
            return;
        }
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);

        int id = snakeDTO.id();
        SnakeHeadDTO head = snakeDTO.head();
        List<SnakeBodyDTO> bodyList = snakeDTO.body();


        Image image = ImageCollector.snakeHead;


        bodyList.forEach((snakeBodyDTO -> {

            gc.drawImage(ImageCollector.snakeBody,
                    snakeBodyDTO.pointDTO().x() * cellWidth, snakeBodyDTO.pointDTO().y() * cellHeight,
                    cellWidth, cellHeight);
        }));
        gc.drawImage(image, head.pointDTO().x() * cellWidth, head.pointDTO().y() * cellHeight,
                cellWidth, cellHeight);
    }

    private void drawWall(WallDTO wallDTO) {
        PointDTO point = wallDTO.point();
        gc.drawImage(ImageCollector.wall, point.x() * cellWidth, point.y() * cellHeight,
                cellWidth, cellHeight);
    }

    public void mouseClick(MouseEvent event) {
        System.out.println(event);
        double x = event.getX();
        double y = event.getY();
//        canvas.getLayoutX();
        System.out.println(canvas.getLayoutX() + " " + canvas.getLayoutY());
        if (x > canvas.getWidth() || y > canvas.getHeight()) {
            return;
        }
        int xCell = (int) (x / cellWidth);
        int yCell = (int) (y / cellHeight);
        fieldConstructor.setUnit(currentGameUnit.getCopy(xCell, yCell));
        game = fieldConstructor.getGameField();
        update();
    }

    public void switchToMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/menu-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void saveField(ActionEvent event) {
        fieldDAO.saveField(game);
        GlobalGameSettings.gameSettings.setGame(game);
    }

    private enum GameUnitEnum {
        Snake,
        Food,
        Empty,
        Wall
    }
}
