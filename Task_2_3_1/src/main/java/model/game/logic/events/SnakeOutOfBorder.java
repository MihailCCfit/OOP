package model.game.logic.events;

import console.game.units.SnakeBodyDTO;
import model.game.logic.Game;
import model.units.Snake;
import model.units.SnakeBody;

public class SnakeOutOfBorder extends SnakeEvent {
    public SnakeOutOfBorder(Snake snake, Game game) {
        super(snake, game);
    }

    @Override
    public void run() {
        new SnakeDeath(snake, game).run();
    }
}
