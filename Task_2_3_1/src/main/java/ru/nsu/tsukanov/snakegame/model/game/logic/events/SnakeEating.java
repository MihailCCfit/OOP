package ru.nsu.tsukanov.snakegame.model.game.logic.events;

import ru.nsu.tsukanov.snakegame.model.game.logic.Game;
import ru.nsu.tsukanov.snakegame.model.units.Empty;
import ru.nsu.tsukanov.snakegame.model.units.Food;
import ru.nsu.tsukanov.snakegame.model.units.Snake;

public class SnakeEating extends SnakeEvent {
    private final Food food;

    public SnakeEating(Snake snake, Game game, Food food) {
        super(snake, game);
        this.food = food;
    }

    @Override
    public void run() {
        snake.eat(food);
        game.setGameUnit(new Empty(food.getX(), food.getY()));
    }
}
