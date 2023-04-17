package model.gamelogic.events;

import model.gamelogic.Game;
import model.units.Empty;
import model.units.Snake;
import model.units.SnakeBody;

import java.util.LinkedList;

public class SnakeDeath extends SnakeEvent implements Runnable {
    public SnakeDeath(Snake snake, Game game) {
        super(snake, game);
    }

    @Override
    public void run() {
        snake.setControllable(false);
        LinkedList<SnakeBody> snakeBodies = new LinkedList<>(snake.getBody());
        snakeBodies.add(snake.getHead());
        snakeBodies.forEach((snakeBody -> {
            game.setGameUnit(new Empty(snakeBody));
        }));

    }
}
