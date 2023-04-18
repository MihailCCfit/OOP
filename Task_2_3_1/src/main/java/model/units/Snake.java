package model.units;

import model.units.snake.Direction;

import java.util.LinkedList;

public final class Snake {
    private LinkedList<SnakeBody> body;
    private SnakeBody head;
    private boolean isControllable = true;

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

    public LinkedList<SnakeBody> getBody() {
        return body;
    }

    public boolean isControllable() {
        return isControllable;
    }

    public void setControllable(boolean controllable) {
        isControllable = controllable;
    }

    public void move(int x, int y) {
        body.add(new SnakeBody(head.getX(), head.getY(), head.getDirection()));
        body.removeLast();
        head.move(x, y);
    }
}
