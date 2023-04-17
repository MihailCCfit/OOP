package model.gamelogic.events;

import model.gamelogic.Game;
import model.units.Snake;

public abstract class SnakeEvent  implements Runnable{
    protected Snake snake;
    protected Game game;

    public SnakeEvent(Snake snake, Game game) {
        this.snake = snake;
        this.game = game;
    }
}
