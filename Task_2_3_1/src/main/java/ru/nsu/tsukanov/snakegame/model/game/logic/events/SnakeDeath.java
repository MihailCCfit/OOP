package ru.nsu.tsukanov.snakegame.model.game.logic.events;

import ru.nsu.tsukanov.snakegame.model.game.logic.Game;
import ru.nsu.tsukanov.snakegame.model.units.Empty;
import ru.nsu.tsukanov.snakegame.model.units.Food;
import ru.nsu.tsukanov.snakegame.model.units.Snake;
import ru.nsu.tsukanov.snakegame.model.units.SnakeBody;

import java.util.LinkedList;
import java.util.List;

public class SnakeDeath extends SnakeEvent implements Runnable {
    public SnakeDeath(Snake snake, Game game) {
        super(snake, game);
    }

    @Override
    public void run() {
        snake.setControllable(false);
        List<SnakeBody> snakeBodies = new LinkedList<>(snake.getBody());
        snakeBodies.forEach((snakeBody -> {
            if (game.getUnitAt(snakeBody.getX(), snakeBody.getY()) instanceof SnakeBody) {
                if (((snakeBody.getX() ^ snakeBody.getY() % 2) & 1) == 0) {
                    game.setGameUnit(new Food(snakeBody.getX(), snakeBody.getY(), 1));
                } else {
                    game.setGameUnit(new Empty(snakeBody));
                }
            }
        }));
        game.setGameUnit(new Empty(snake.getHead()));

    }
}
