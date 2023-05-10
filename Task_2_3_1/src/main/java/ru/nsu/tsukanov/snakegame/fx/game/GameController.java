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
import java.util.HashMap;
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
    public Text scorePlayer1;
    public Text namePlayer2;
    public Text namePlayer1;
    public ImageView imagePlayer1;
    public ImageView imagePlayer2;
    public Text scorePlayer2;
    public ImageView imagePlayer3;
    public Text namePlayer3;
    public Text scorePlayer3;
    public ImageView personImage;
    public Text personName;
    public ImageView winnerImage;
    public Text winnerText;
    private int gameWidth;
    private int gameHeight;

    private double cellWidth;

    private double cellHeight;
    private GameSettings gameSettings;
    private Timeline timeline = new Timeline();
    private HumanPlayer humanPlayer;
    private KeyResolver keyResolver = new KeyResolver();
    private SnakeDrawer snakeDrawer;
    private LeaderBoard leaderBoard;

    int i = 0;


    public void click(MouseEvent event) {
        score.setText("" + i++);
    }

    public void type(Event event) {
        score.setText("" + i++);
    }


    public void init() {
        gameSettings = GlobalGameSettings.gameSettings;
        canvas.getScene().setOnKeyPressed((event -> {
            keyResolver.resolveKeyCode(event.getCode());
        }));
        gc = canvas.getGraphicsContext2D();
        game = gameSettings.getGame().getCopy();
        gameWidth = game.width();
        gameHeight = game.height();
        cellWidth = canvas.getWidth() / gameWidth;
        cellHeight = canvas.getHeight() / gameHeight;
        snakeDrawer = new SnakeDrawer(game.getSnakeMap().size());

        winnerImage.setOpacity(0);
        winnerImage.setImage(ImageCollector.winner);
        winnerText.setOpacity(0);

        initLeaderBoard();
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
    private void initLeaderBoard() {
        leaderBoard = new LeaderBoard(List.of(imagePlayer1, imagePlayer2, imagePlayer3),
                List.of(scorePlayer1, scorePlayer2, scorePlayer3),
                List.of(namePlayer1, namePlayer2, namePlayer3));
    }

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
        System.out.println(gameSettings.getGameSpeed());
        timeline = new Timeline(new KeyFrame(Duration.millis((double) 20 / gameSettings.getGameSpeed()), ev -> {
            timeline.stop();
            if (!game.tick()) {
                showWinner();
                return;
            }
            update();
            timeline.play();
        }));
//        timeline.setCycleCount(1);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void initPlayers() {
        humanPlayer = new HumanPlayer(game, 0);
        if (gameSettings.getUserMode() == UserMode.Player) {
            game.addPlayer(0, humanPlayer);
            personImage.setImage(ImageCollector.snakeBody);
            personName.setText("0");
            //TODO: name for player
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
                drawCell(x, y);
            }
        }
    }

    private void drawCell(int x, int y) {
        Image image;
        if (((x + y) & 1) == 0) {
            image = ImageCollector.light_grass;
        } else {
            image = ImageCollector.dark_grass;
        }
        gc.drawImage(image, x * cellWidth, y * cellHeight,
                cellWidth, cellHeight);
    }

    public void update() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        drawField();
        drawGameObjects(GameStateDTO.getGameState(game));
        updateStatistics();
        snakeDrawer.updateLeaders();
    }

    private void updateStatistics() {
        if (humanPlayer != null) {
            score.setText(humanPlayer.getScore() + "");
        }
        personImage.setImage(ImageCollector.snakeHead);

    }

    private void drawGameObjects(GameStateDTO gameStateDTO) {
        gameStateDTO.foodDTOS().forEach(this::drawFood);
        gameStateDTO.walls().forEach(this::drawWall);
        gameStateDTO.snakes().forEach(snakeDrawer::drawSnake);
    }

    private void drawFood(FoodDTO foodDTO) {
        PointDTO point = foodDTO.foodPoint();
        gc.drawImage(ImageCollector.getFood(foodDTO.value()), point.x() * cellWidth, point.y() * cellHeight,
                cellWidth, cellHeight);
        point = null;
    }


    private void drawWall(WallDTO wallDTO) {
        PointDTO point = wallDTO.point();
        gc.drawImage(ImageCollector.wall, point.x() * cellWidth, point.y() * cellHeight,
                cellWidth, cellHeight);
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

    private void showWinner() {
        winnerImage.setOpacity(0.8);
        winnerText.setOpacity(1);
    }


    private class SnakeDrawer {
        private final SnapshotParameters parameters;
        private final int playersAmount;
        private final Map<Integer, HeadBody> imageViewMap = new HashMap<>();

        public SnakeDrawer(int playersAmount) {
            this.playersAmount = playersAmount;
            parameters = new SnapshotParameters();
            parameters.setFill(Color.TRANSPARENT);
            initSkinMap();
        }

        private void initSkinMap() {
            for (int id = 0; id < playersAmount; id++) {
                ImageView headView = new ImageView(ImageCollector.snakeHead);
                ImageView bodyView = new ImageView(ImageCollector.snakeBody);
                ColorAdjust color = new ColorAdjust((double) id / playersAmount * 1.1, 0, 0, 0);
                headView.setEffect(color);
                bodyView.setEffect(color);
                imageViewMap.put(id, new HeadBody(headView, bodyView));
            }
        }

        private record HeadBody(ImageView headView, ImageView bodyView) {
        }

        private void drawSnake(SnakeDTO snakeDTO) {
            if (!snakeDTO.isAlive()) {
                return;
            }
            SnakeHeadDTO head = snakeDTO.head();
            List<SnakeBodyDTO> bodyList = snakeDTO.body();
            HeadBody headBody = imageViewMap.get(snakeDTO.id());
            ImageView headView = headBody.headView;
            int headRotation = head.directionDTO().getAngle();
            headView.setRotate(headRotation);
            Image image = headView.snapshot(parameters, null);
            ImageView bodyView = headBody.bodyView;
            for (SnakeBodyDTO snakeBodyDTO : bodyList) {
                int angle = snakeBodyDTO.directionDTO().getAngle();
                bodyView.setRotate(angle);
                gc.drawImage(bodyView.snapshot(parameters, null),
                        snakeBodyDTO.pointDTO().x() * cellWidth, snakeBodyDTO.pointDTO().y() * cellHeight,
                        cellWidth, cellHeight);
                bodyView.setRotate(-angle);
            }
            gc.drawImage(image, head.pointDTO().x() * cellWidth, head.pointDTO().y() * cellHeight,
                    cellWidth, cellHeight);
            headView.setRotate(-headRotation);
        }

        public void updateLeaders() {
            Map<Integer, Integer> results = game.getResults();
            List<Map.Entry<Integer, Integer>> listOfWinners = results.entrySet().stream()
                    .sorted((entry1, entry2) -> -Long.compare(entry1.getValue(), entry2.getValue()))
                    .limit(3).toList();

            int maxIndex = 0;
            for (int i = 0; i < listOfWinners.size(); i++) {
                Map.Entry<Integer, Integer> player = listOfWinners.get(i);
                leaderBoard.update(i,
                        imageViewMap.get(player.getKey()).headView.snapshot(parameters, null),
                        player.getValue(),
                        player.getKey() + "");

            }
            maxIndex += 1;
//            ConsoleUtils.printLine(screen, "Press Esc for exit      ",
//                    new TerminalPosition(size.getColumns() / 2 - 4,
//                            2 + maxIndex));
//            try {
//                screen.refresh();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
        }
    }

    private record LeaderBoard(List<ImageView> leaderViews, List<Text> boardScores, List<Text> boardNames) {
        public void update(int index, Image playerImage, int newScore, String playerName) {
            leaderViews.get(index).setImage(playerImage);
            boardScores.get(index).setText(newScore + "");
            boardNames.get(index).setText(playerName);
        }
    }


    public static class ImageCollector {
        public static Image light_grass = loadImage("light_grass.png");
        public static Image dark_grass = loadImage("dark_grass.png");
        public static Image wall = loadImage("wall.png");
        public static Image snakeBody = loadImage("snakeBody.png");
        public static Image snakeHead = loadImage("snakeHead.png");
        public static Image pointer = loadImage("pointer.png");
        public static Image winner = loadImage("win.png");
        public static int maxFood = 2;
        private static final Random random = new Random();

        public static Image getFood(int foodValue) {
            if (foodValue > maxFood) {
                foodValue = maxFood;
            }
            foodValue -= 1;
            return loadImage("food_" + foodValue + ".png");
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
        timeline.stop();
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
