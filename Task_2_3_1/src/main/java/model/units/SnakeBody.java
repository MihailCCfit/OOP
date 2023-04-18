package model.units;

import model.units.snake.Direction;

public final class SnakeBody extends GameUnit {
    private Direction direction;
    private Snake snake;

    public SnakeBody(int x, int y, Direction direction) {
        super(x, y);
        this.direction = direction;
    }

    public SnakeBody(int x, int y) {
        this(x, y, Direction.getRandomDirection());
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public boolean isHead() {
        return snake.getHead().equals(this);
    }

    public Snake getSnake() {
        return snake;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

}
