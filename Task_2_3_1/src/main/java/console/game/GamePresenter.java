package console.game;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import console.GameSettings;
import console.game.scenes.GameScene;
import console.game.scenes.WinnerScene;
import console.game.units.*;
import model.game.logic.Game;
import model.game.logic.PlayerListener;
import model.players.CommonBotPlayer;
import model.players.EuristickBot;
import model.players.HumanPlayer;
import model.units.Food;
import model.units.SnakeBody;
import model.units.Wall;
import model.units.snake.Direction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GamePresenter {

    private Game game;
    private Screen screen;
    private boolean escapeFlag = false;
    private GameSettings gameSettings;

    public GamePresenter(Game game, GameSettings gameSettings, Screen screen) {
        this.game = game;
        this.screen = screen;
        this.gameSettings = gameSettings;
    }

    public int start() {
        GameScene gameScene = new GameScene(screen);
        HumanPlayer humanPlayer = new HumanPlayer(game, 0);
        game.addPlayer(0, humanPlayer);
        game.getSnakeMap().forEach(((id, snake) -> {
            if (id != 0) {
                game.addPlayer(id, getBot(id));
            }
        }));
        try {
            screen.readInput();
            screen.clear();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Direction direction = null;

        while (loopCondition()) {
            try {
                KeyStroke keyStroke = null;
                KeyStroke tmpStroke;
                while ((tmpStroke = screen.pollInput()) != null) {
                    keyStroke = tmpStroke;
                }
                if (keyStroke != null) {
                    switch (keyStroke.getKeyType()) {
                        case Escape -> {
                            escapeFlag = true;
                        }
                        default -> direction = gameSettings.keyDirection(keyStroke);

                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            List<FoodDTO> foods = new ArrayList<>();
            List<WallDTO> walls = new ArrayList<>();
            game.getField().getAll().forEach((unit -> {
                if (unit instanceof Food food) {
                    foods.add(new FoodDTO(new PointDTO(food.getX(), food.getY()), food.getValue()));
                }
                if (unit instanceof Wall wall) {
                    walls.add(new WallDTO(new PointDTO(wall.getX(), wall.getY())));
                }
            }));
            List<SnakeDTO> snakeDTOS = new ArrayList<>();
            game.getSnakeMap().forEach((id, snake) -> {
                List<SnakeBodyDTO> bodyDTOS = new ArrayList<>();
                snake.getBody().forEach(snakeBody -> {
                    bodyDTOS.add(new SnakeBodyDTO(new PointDTO(snakeBody.getX(), snakeBody.getY()),
                            DirectionDTO.valueOf(snakeBody.getDirection().toString())));
                });
                SnakeBody head = snake.getHead();
                snakeDTOS.add(new SnakeDTO
                        (bodyDTOS, new SnakeHeadDTO(
                                new PointDTO(head.getX(), head.getY()),
                                DirectionDTO.valueOf(head.getDirection().name())),
                                snake.isControllable(), id)
                );
            });
            gameScene.update(new GameStateDTO(snakeDTOS, walls, foods));
            try {
                screen.refresh();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (direction != null) {
                humanPlayer.setDirection(direction);
            }
            if (game.tick()) {
                try {
                    Thread.sleep(10 * ((long) (8 - gameSettings.getGameSpeed())));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                //TODO: Список победителей
                WinnerScene winnerScene = new WinnerScene(screen, game.getResults());
                winnerScene.showWinners();
                while (true) {
                    KeyStroke key = null;
                    try {
                        key = screen.readInput();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    if (key.getKeyType().equals(KeyType.Escape)) {
                        break;
                    }
                }
                break;
            }

        }

        return 1;
    }

    public boolean loopCondition() {

        return !escapeFlag;
    }

    private PlayerListener getBot(int id) {
        return new EuristickBot(game, id);
    }


}
