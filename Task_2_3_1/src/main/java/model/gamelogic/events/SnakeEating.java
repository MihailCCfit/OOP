package model.gamelogic.events;

import model.gamelogic.Game;
import model.units.Food;
import model.units.Snake;

public class SnakeEating extends SnakeEvent {
    private final Food food;

    public SnakeEating(Snake snake, Game game, Food food) {
        super(snake, game);
        this.food = food;
    }

    @Override
    public void run() {
        snake.eat(food);
    }
}
