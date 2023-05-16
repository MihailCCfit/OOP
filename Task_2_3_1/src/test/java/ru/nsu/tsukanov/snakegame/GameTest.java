package ru.nsu.tsukanov.snakegame;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.nsu.tsukanov.snakegame.model.game.field.FieldConstructor;
import ru.nsu.tsukanov.snakegame.model.game.logic.Game;
import ru.nsu.tsukanov.snakegame.model.players.CommonBotPlayer;
import ru.nsu.tsukanov.snakegame.model.players.HumanPlayer;
import ru.nsu.tsukanov.snakegame.model.units.Food;
import ru.nsu.tsukanov.snakegame.model.units.Snake;
import ru.nsu.tsukanov.snakegame.model.units.SnakeBody;
import ru.nsu.tsukanov.snakegame.model.units.Wall;
import ru.nsu.tsukanov.snakegame.model.units.snake.Direction;

public class GameTest {
    static private Game gameForCopy;

    @BeforeAll
    static void initEmptyGame() {
        FieldConstructor fieldConstructor = new FieldConstructor(50, 50);
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                if (i == 0 || j == 0 || i == 49 || j == 49) {
                    fieldConstructor.setUnit(new Wall(i, j));
                }

            }
        }
        fieldConstructor.setUnit(new SnakeBody(25, 25));
        fieldConstructor.setUnit(new Food(24, 25, 1));
        fieldConstructor.setUnit(new Food(25, 26, 1));
        fieldConstructor.setUnit(new Food(26, 25, 1));
        fieldConstructor.setUnit(new Food(25, 24, 1));
        gameForCopy = fieldConstructor.getGameField();
    }

    @Test
    void testFood() {
        Game game = gameForCopy.getCopy();
        Snake snake = game.getSnakeMap().get(0);
        Assertions.assertEquals(1, snake.length());
        game.moveSnakes();
        Assertions.assertEquals(2, snake.length());
        for (int i = 0; i < 25; i++) {
            game.moveSnakes();
        }
        Assertions.assertFalse(snake.isControllable());

    }

    @Test
    void testPlayingBot() {
        Game game = gameForCopy.getCopy();
        Snake snake = game.getSnakeMap().get(0);
        CommonBotPlayer commonBotPlayer = new CommonBotPlayer(game, 0);
        game.addPlayer(0, commonBotPlayer);
        for (int i = 0; i < 100; i++) {
            game.moveSnakes();
        }
        Assertions.assertTrue(snake.isControllable());
        Assertions.assertTrue(snake.length() > 1);
    }

    @Test
    void testPlayingPerson() {
        Game game = gameForCopy.getCopy();
        Snake snake = game.getSnakeMap().get(0);
        HumanPlayer player = new HumanPlayer(game, 0);
        game.addPlayer(0, player);
        for (int i = 0; i < 23; i++) {
            game.moveSnakes();
            player.setDirection(Direction.UP);
            game.moveSnakes();
            player.setDirection(Direction.RIGHT);
            game.moveSnakes();
            player.setDirection(Direction.DOWN);
            game.moveSnakes();
            player.setDirection(Direction.LEFT);
            Assertions.assertTrue(snake.isControllable());
        }
        Assertions.assertTrue(snake.isControllable());
    }
}
