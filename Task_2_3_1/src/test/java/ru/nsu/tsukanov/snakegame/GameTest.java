package ru.nsu.tsukanov.snakegame;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.nsu.tsukanov.snakegame.model.game.field.FieldConstructor;
import ru.nsu.tsukanov.snakegame.model.game.logic.Game;
import ru.nsu.tsukanov.snakegame.model.units.Food;
import ru.nsu.tsukanov.snakegame.model.units.Snake;
import ru.nsu.tsukanov.snakegame.model.units.SnakeBody;
import ru.nsu.tsukanov.snakegame.model.units.Wall;

public class GameTest {
    private Game gameForCopy;

    @BeforeAll
    void initEmptyGame() {
        FieldConstructor fieldConstructor = new FieldConstructor(50, 50);
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                fieldConstructor.setUnit(new Wall(i, j));
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
}
