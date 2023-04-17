package model.units;

import model.units.snake.Direction;

import java.util.ArrayList;

public final class Snake {
    private ArrayList<SnakeBody> body;
    SnakeBody head;

    public Snake(SnakeBody head) {
        this.head = head;
        head.setSnake(this);
    }

    public Snake(int x, int y) {
        head = new SnakeBody(x, y, Direction.getRandomDirection());
    }

    public Snake(int x, int y, Direction direction) {
        head = new SnakeBody(x, y, direction);
    }

    public void setDirection(Direction direction) {
        head.setDirection(direction);
    }

    public boolean changeDirection(Direction direction) {
        if (direction.isOpposite(head.getDirection())) {
            return false;
        }
        head.setDirection(direction);
        return true;
    }

    public SnakeBody getHead() {
        return head;
    }

    public ArrayList<SnakeBody> getBody() {
        return body;
    }
}
