package ru.nsu.tsukanov.snakegame.fx.game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import ru.nsu.tsukanov.snakegame.console.GameSettings;
import ru.nsu.tsukanov.snakegame.console.settings.UserMode;
import ru.nsu.tsukanov.snakegame.fx.GlobalGameSettings;
import ru.nsu.tsukanov.snakegame.model.game.logic.Game;
import ru.nsu.tsukanov.snakegame.model.game.logic.PlayerListener;
import ru.nsu.tsukanov.snakegame.model.players.EuristickBot;
import ru.nsu.tsukanov.snakegame.model.players.HumanPlayer;
import ru.nsu.tsukanov.snakegame.model.units.snake.Direction;
import ru.nsu.tsukanov.snakegame.units.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameController {
    @FXML
    public Text score;
    @FXML
    public Canvas canvas;
    public GraphicsContext gc;
    public Game game;
    private int speed;
    private UserMode userMode;
    private int gameWidth;
    private int gameHeight;

    private double cellWidth;

    private double cellHeight;
    private GameSettings gameSettings;
    private Timeline timeline = new Timeline();
    private int playersAmount;
    private HumanPlayer humanPlayer;
    private KeyResolver keyResolver = new KeyResolver();

    int i = 0;


    public void click(MouseEvent event) {
        score.setText("" + i++);
    }

    public void type(Event event) {
        score.setText("" + i++);
    }


    public void init() {
        gameSettings = GlobalGameSettings.gameSettings;
        speed = gameSettings.getGameSpeed();
        userMode = gameSettings.getUserMode();
        canvas.getScene().setOnKeyPressed((event -> {
            keyResolver.resolveKeyCode(event.getCode());
        }));
        gc = canvas.getGraphicsContext2D();
        game = gameSettings.getGame().getCopy();
        gameWidth = game.width();
        gameHeight = game.height();
        cellWidth = canvas.getWidth() / gameWidth;
        cellHeight = canvas.getHeight() / gameHeight;
        update();
        initPlayers();
//        beforeStart();
        startGame();
    }

//    private Timeline beforeStart() {
//        Timeline timeline1 = null;
//
//        if (humanPlayer != null) {
//            var point = humanPlayer.getCoordinates();
//            gc.drawImage(ImageCollector.pointer, (point.getX() - 1) * cellWidth, (point.getY() - 1) * cellHeight,
//                    cellWidth * 3, cellHeight * 3);
//            timeline1 = new Timeline(new KeyFrame(Duration.millis(1000), (event -> {
//            })));
//            timeline1.play();
//        }
//        return timeline1;
//
//    }

    private void startGame(Timeline beforeStart) {
        beforeStart.setOnFinished((actionEvent) -> {
            beforeStart.stop();
            timeline = new Timeline(new KeyFrame(Duration.millis((double) 200 / gameSettings.getGameSpeed()), ev -> {
                game.tick();
                update();
            }));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        });
    }

    private void startGame() {
        timeline = new Timeline(new KeyFrame(Duration.millis((double) 100 / gameSettings.getGameSpeed()), ev -> {
            timeline.stop();
            game.tick();
            update();
            timeline.play();
        }));
//        timeline.setCycleCount(1);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();


    }

    private void initPlayers() {
        playersAmount = game.getSnakeMap().size();
        humanPlayer = new HumanPlayer(game, 0);
        if (gameSettings.getUserMode() == UserMode.Player) {
            game.addPlayer(0, humanPlayer);
        } else {
            game.addPlayer(0, getBot(0));
        }
        game.getSnakeMap().forEach(((id, snake) -> {
            if (id != 0) {
                game.addPlayer(id, getBot(id));
            }
        }));
    }

    private PlayerListener getBot(int id) {
        return new EuristickBot(game, id);
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
                imageView.setRotate(randomRotation());
                gc.drawImage(imageView.getImage(), x * cellWidth, y * cellHeight,
                        cellWidth, cellHeight);
                imageView = null;

            }
        }
    }

    public void update() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        drawField();
        drawGameObjects(GameStateDTO.getGameState(game));
        updateStatistics();
    }

    private void updateStatistics() {
        if (humanPlayer != null) {
            score.setText(humanPlayer.getScore() + "");
        }

    }

    private void drawGameObjects(GameStateDTO gameStateDTO) {
        gameStateDTO.foodDTOS().forEach(this::drawFood);
        gameStateDTO.walls().forEach(this::drawWall);
        gameStateDTO.snakes().forEach(this::drawSnake);
    }

    private void drawFood(FoodDTO foodDTO) {
        PointDTO point = foodDTO.foodPoint();
        gc.drawImage(ImageCollector.randomFood(), point.x() * cellWidth, point.y() * cellHeight,
                cellWidth, cellHeight);
        point = null;
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


        ImageView headView = new ImageView(ImageCollector.snakeHead);
        headView.setRotate(head.directionDTO().getAngle());
        ColorAdjust color = new ColorAdjust((double) id / playersAmount * 1.1, 0, 0, 0);
        headView.setEffect(color);

        Image image = headView.snapshot(parameters, null);


        bodyList.forEach((snakeBodyDTO -> {
            ImageView bodyView = new ImageView(ImageCollector.snakeBody);
            bodyView.setRotate(snakeBodyDTO.directionDTO().getAngle());
            bodyView.setEffect(color);
            gc.drawImage(bodyView.snapshot(parameters, null),
                    snakeBodyDTO.pointDTO().x() * cellWidth, snakeBodyDTO.pointDTO().y() * cellHeight,
                    cellWidth, cellHeight);
        }));
        gc.drawImage(image, head.pointDTO().x() * cellWidth, head.pointDTO().y() * cellHeight,
                cellWidth, cellHeight);
        bodyList = null;
        headView = null;
        image = null;


    }

    private void drawWall(WallDTO wallDTO) {
        PointDTO point = wallDTO.point();
        gc.drawImage(ImageCollector.wall, point.x() * cellWidth, point.y() * cellHeight,
                cellWidth, cellHeight);
    }


    private double randomRotation() {
        Random random = new Random();
        return switch (random.nextInt(4)) {
            case 0 -> 0.0;
            case 1 -> 90.0;
            case 2 -> 180.0;
            case 3 -> 270.0;
            default -> 0;
        };
    }

    private void restart() {
        timeline.stop();
        game = GlobalGameSettings.gameSettings.getGame().getCopy();
        initPlayers();
        update();
        timeline.play();
//        beforeStart().setOnFinished(
//                (event) -> timeline.play()
//        );
//        init();
    }


    public static class ImageCollector {
        public static Image light_grass = loadImage("light_grass.png");
        public static Image dark_grass = loadImage("dark_grass.png");
        public static Image wall = loadImage("wall.png");
        public static Image snakeBody = loadImage("snakeBody.png");
        public static Image snakeHead = loadImage("snakeHead.png");
        public static Image pointer = loadImage("pointer.png");
        public static int maxFood = 1;
        private static final Random random = new Random();

        public static Image randomFood() {

            return loadImage("food_" + random.nextInt(maxFood) + ".png");
        }


        private static Image loadImage(String filename) {

            try {
                return new Image(new FileInputStream("resources/textures/" + filename));
            } catch (FileNotFoundException e) {
                WritableImage constr = new WritableImage(5, 5);
                constr.getPixelWriter().setColor(2, 2, new Color(1, 0.2, 1, 1));
                return constr;
            }
        }
    }

    private void exitToMenu() {

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/menu-view.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Stage stage = (Stage) (canvas.getScene().getWindow());
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }


    private class KeyResolver {
        private void resolveKeyCode(KeyCode keyCode) {
            if (keyCode == null) return;
            Runnable action = keyMap.get(keyCode);
            if (action != null) {
                action.run();
            }
        }

        private void changeDirection(Direction direction) {
            if (humanPlayer != null) {
                humanPlayer.setDirection(direction);
            }
        }

        private final Map<KeyCode, Runnable> keyMap
                = Map.of(
                KeyCode.R, () -> {
                    restart();
                },
                KeyCode.Q, () -> {
                    exitToMenu();
                },

                KeyCode.UP, () -> {
                    changeDirection(Direction.DOWN);
                },

                KeyCode.DOWN, () -> {
                    changeDirection(Direction.UP);
                },

                KeyCode.LEFT, () -> {
                    changeDirection(Direction.LEFT);
                },

                KeyCode.RIGHT, () -> {
                    changeDirection(Direction.RIGHT);
                }
        );
    }

}
