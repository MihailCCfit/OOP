package ru.nsu.tsukanov.snakegame.fx.game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import ru.nsu.tsukanov.snakegame.console.GameSettings;
import ru.nsu.tsukanov.snakegame.console.settings.UserMode;
import ru.nsu.tsukanov.snakegame.fx.GlobalGameSettings;
import ru.nsu.tsukanov.snakegame.model.game.logic.Game;
import ru.nsu.tsukanov.snakegame.model.players.EuristickBot;
import ru.nsu.tsukanov.snakegame.model.players.HumanPlayer;
import ru.nsu.tsukanov.snakegame.model.players.PlayerListener;
import ru.nsu.tsukanov.snakegame.model.units.snake.Direction;
import ru.nsu.tsukanov.snakegame.units.*;

import java.util.List;
import java.util.Map;

public class GamePresenter {
    private final GameController gameController;
    private Timeline timeline = new Timeline();
    private HumanPlayer humanPlayer;
    private final KeyResolver keyResolver = new KeyResolver();
    private final int gameWidth;
    private final int gameHeight;

    private final double cellWidth;

    private final double cellHeight;
    private final GameSettings gameSettings = GlobalGameSettings.gameSettings;

    private Game game;
    private final SnakeDrawer snakeDrawer;

    public GamePresenter(GameController gameController) {
        this.gameController = gameController;
        game = gameSettings.getGame().getCopy();
        gameWidth = game.width();
        gameHeight = game.height();
        cellWidth = gameController.getCanvasWidth() / gameWidth;
        cellHeight = gameController.getCanvasHeight() / gameHeight;
        snakeDrawer = new SnakeDrawer(game.getSnakeMap().size(), gameController);//TODO:
        gameController.setSize(cellWidth, cellHeight);
    }

    public void init() {
        initPlayers();
    }

    public KeyResolver getKeyResolver() {
        return keyResolver;
    }

    private void initPlayers() {
        humanPlayer = new HumanPlayer(game, 0);
        if (gameSettings.getUserMode() == UserMode.Player) {
            game.addPlayer(0, humanPlayer);
            gameController.initPersonStatistic("0", ImageCollector.snakeBody);
        } else {
            game.addPlayer(0, getBot(0));
        }
        game.getSnakeMap().forEach(((id, snake) -> {
            if (id != 0) {
                game.addPlayer(id, getBot(id));
            }
        }));
    }

    public void update() {
        gameController.clear();
        drawField();
        drawGameObjects(GameStateDTO.getGameState(game));
        updateStatistics();
        updateLeaders();
    }

    private void updateStatistics() {
        if (humanPlayer != null) {
            gameController.updatePersonStatistic(humanPlayer.getScore() + "",
                    ImageCollector.snakeHead);
        }

    }

    private void drawGameObjects(GameStateDTO gameStateDTO) {
        gameStateDTO.foodDTOS().forEach(this::drawFood);
        gameStateDTO.walls().forEach(this::drawWall);
        gameStateDTO.snakes().forEach(this::drawSnakes);
    }

    private void drawField() {
        for (int x = 0; x < gameWidth; x++) {
            for (int y = 0; y < gameHeight; y++) {
                drawCell(x, y);
            }
        }
    }

    private void drawWall(WallDTO wallDTO) {
        PointDTO point = wallDTO.point();
        gameController.drawImage(ImageCollector.wall, point.x(), point.y());
    }

    private void drawCell(int x, int y) {
        Image image;
        if (((x + y) & 1) == 0) {
            image = ImageCollector.light_grass;
        } else {
            image = ImageCollector.dark_grass;
        }
        gameController.drawImage(image, x, y);
    }

    private void drawFood(FoodDTO foodDTO) {
        PointDTO point = foodDTO.foodPoint();
        gameController.drawImage(ImageCollector.getFood(foodDTO.value()),
                point.x(), point.y());
    }

    private void restart() {
        timeline.stop();
        game = GlobalGameSettings.gameSettings.getGame().getCopy();
        update();
        initPlayers();

        timeline.play();
    }

    public void drawSnakes(SnakeDTO snakeDTO) {
        if (snakeDTO.isAlive()) {
            int id = snakeDTO.id();

            for (SnakeBodyDTO snakeBodyDTO : snakeDTO.body()) {
                PointDTO point = snakeBodyDTO.pointDTO();
                Image bodyImage = snakeDrawer.getBodyImage(snakeBodyDTO.directionDTO(), id);
                gameController.drawImage(bodyImage, point.x() * cellWidth, point.y() * cellWidth,
                        cellWidth, cellHeight);
            }
            PointDTO point = snakeDTO.head().pointDTO();
            Image headImage = snakeDrawer.getHeadImage(snakeDTO.head().directionDTO(), id);
            gameController.drawImage(headImage, point.x() * cellWidth, point.y() * cellWidth,
                    cellWidth, cellHeight);
        }

    }

    void startGame() {
        timeline = new Timeline(new KeyFrame(Duration.millis((double) 20 / gameSettings.getGameSpeed()), ev -> {
            timeline.stop();
            if (!game.tick()) {
                gameController.showWinner();
                return;
            }
            update();
            timeline.play();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private PlayerListener getBot(int id) {
//        CustomizableEuristickBot.BotBuilder botBuilder = new CustomizableEuristickBot.BotBuilder(game, id);
//        botBuilder.setCorrect(gameSettings.getDifficult());
        return new EuristickBot(game, id);
    }

    public void stop() {
        timeline.stop();
    }

    public void updateLeaders() {
        Map<Integer, Integer> results = game.getResults();
        List<Map.Entry<Integer, Integer>> listOfWinners = results.entrySet().stream()
                .sorted((entry1, entry2) -> -Long.compare(entry1.getValue(), entry2.getValue()))
                .limit(3).toList();
        for (int i = 0; i < listOfWinners.size(); i++) {
            Map.Entry<Integer, Integer> player = listOfWinners.get(i);
            gameController.updateLeaderBoard(i,
                    snakeDrawer.getHeadImage(player.getKey()),
                    player.getValue(),
                    player.getKey() + "");

        }
    }


    public class KeyResolver {
        public void resolveKeyCode(KeyCode keyCode) {
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
                KeyCode.R, GamePresenter.this::restart,
                KeyCode.Q, () -> {
                    gameController.exitToMenu();
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
