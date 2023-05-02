package model.units;

import model.units.snake.Direction;

import java.util.LinkedList;

public final class Snake {
    private LinkedList<SnakeBody> body = new LinkedList<>();
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
        if (isControllable) {
            var newBody = new SnakeBody(head.getX(), head.getY(), head.getDirection());
            newBody.setSnake(this);
            body.addFirst(newBody);
            body.removeLast();
            head.move(x, y);
        }

    }

    public void eat(Food food) {
        if (isControllable) {


            SnakeBody bod;
            if (body.isEmpty()) {
                bod = new SnakeBody(head.getX(), head.getY(), head.getDirection());
                bod.setSnake(this);
            } else {
                bod = body.getLast();
            }

            for (int i = 0; i < food.getValue(); i++) {
                var newBody = new SnakeBody(bod.getX(), bod.getY(), bod.getDirection());
                newBody.setSnake(this);
                body.addLast(newBody);
            }
        }

    }

    public long length() {
        return body.size() + 1;
    }
}
