package model.game.logic.events;

import model.game.logic.Game;
import model.units.Empty;
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
        game.setGameUnit(new Empty(food.getX(), food.getY()));
    }
}
