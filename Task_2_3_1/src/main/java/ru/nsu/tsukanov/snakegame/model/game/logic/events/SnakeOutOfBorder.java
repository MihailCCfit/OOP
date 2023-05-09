package ru.nsu.tsukanov.snakegame.model.game.logic.events;

import ru.nsu.tsukanov.snakegame.model.game.logic.Game;
import ru.nsu.tsukanov.snakegame.model.units.Snake;

public class SnakeOutOfBorder extends SnakeEvent {
    public SnakeOutOfBorder(Snake snake, Game game) {
        super(snake, game);
    }

    @Override
    public void run() {
        new SnakeDeath(snake, game).run();
    }
}
