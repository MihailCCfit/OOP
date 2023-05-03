package model.game.logic;

import model.game.logic.events.SnakeDeath;
import model.game.logic.events.SnakeEating;
import model.game.logic.events.SnakeOutOfBorder;
import model.units.*;
import model.units.snake.Direction;

import java.util.Map;
import java.util.Random;

public class GameLogic {
    private Game game;
    private long amountOfFood;

    public GameLogic(Game game) {
        this.game = game;
        amountOfFood = game.getField().getAll().stream().filter((unit -> unit instanceof Food)).count();
    }

    public boolean moveSnake(Snake snake) {
        SnakeBody head = snake.getHead();
        int nextX = head.getX(), nextY = head.getY();
        switch (head.getDirection()) {
            case LEFT -> nextX--;
            case UP -> nextY++;
            case RIGHT -> nextX++;
            case DOWN -> nextY--;
        }
        if (nextX < 0 || nextX >= game.width()
                || nextY < 0 || nextY >= game.height()) {
            SnakeOutOfBorder event = new SnakeOutOfBorder(snake, game);
            event.run();
//            if (nextX < 0) nextX = game.width() - 1;
//            if (nextX >= game.width()) nextX = 0;
//            if (nextY < 0) nextY = game.width() - 1;
//            if (nextY >= game.width()) nextY = 0;
            return false;
        }
        if (!interaction(snake, game.getUnitAt(nextX, nextY))) return false;
        snake.move(nextX, nextY);
        return true;
    }

    public boolean interaction(Snake snake, GameUnit unit) {
        SnakeBody head = snake.getHead();
        if (unit instanceof Food) {
            SnakeWithFood(snake, (Food) unit);
            return true;
        }
        if (unit instanceof Empty) {
            return true;
        }
        if (unit instanceof Wall) {
            SnakeWithWall(snake, (Wall) unit);
            return false;
        }
        if (unit instanceof SnakeBody) {
            return SnakeToSnake(snake, (SnakeBody) unit);
        }
        return true;
    }

    public boolean SnakeWithFood(Snake snake, Food food) {
        new SnakeEating(snake, game, food).run();
        amountOfFood--;
        return true;
    }

    public boolean SnakeWithWall(Snake snake, Wall wall) {
        new SnakeDeath(snake, game).run();
        return false;
    }

    public boolean SnakeToSnake(Snake movingSnake, SnakeBody stayingSnake) {
        new SnakeDeath(movingSnake, game).run();
        return false;
    }

    public boolean spawnFood() {
        if (amountOfFood >
//                ((long) game.height() * game.width() -
//                game.getSnakeMap().values().stream().map(Snake::length).reduce(0L, Long::sum)) / 10
                10

        ) {
            return false;
        }
        Random random = new Random();
        int x = random.nextInt(game.width());
        int y = random.nextInt(game.height());
        if (game.getUnitAt(x, y) instanceof Empty) {
            game.setGameUnit(new Food(x, y, 1));
            amountOfFood++;
            return true;
        } else {
            return trySpawnFood(x - 1, y) || trySpawnFood(x, y - 1)
                    || trySpawnFood(x + 1, y) || trySpawnFood(x - 1, y);
        }
    }

    private boolean trySpawnFood(int x, int y) {
        if (x > 0 && y > 0
                && x < game.width() && y < game.height()
                && game.getUnitAt(x, y) instanceof Empty) {
            game.setGameUnit(new Food(x, y, 1));
            amountOfFood++;
            return true;
        }
        return false;
    }

    public static Map.Entry<Integer, Integer> nextStep(Direction direction, int x, int y) {
        switch (direction) {
            case LEFT -> x--;
            case UP -> y++;
            case RIGHT -> x++;
            case DOWN -> y--;
        }
        int finalX = x;
        int finalY = y;
        return new Map.Entry<Integer, Integer>() {
            @Override
            public Integer getKey() {
                return finalX;
            }

            @Override
            public Integer getValue() {
                return finalY;
            }

            @Override
            public Integer setValue(Integer integer) {
                throw new UnsupportedOperationException();
            }
        };
    }
}
