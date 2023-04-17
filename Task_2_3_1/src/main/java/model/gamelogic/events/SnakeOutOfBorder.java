package model.gamelogic.events;

import model.gamelogic.Game;
import model.units.Snake;

public class SnakeOutOfBorder extends SnakeEvent {
    public SnakeOutOfBorder(Snake snake, Game game) {
        super(snake, game);
    }

    @Override
    public void run() {
        new SnakeDeath(snake, game).run();
    }
}
