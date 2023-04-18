package model.gamelogic;

import model.units.*;

import java.util.Random;

public class GameLogic {
    //Todo: check premove, collision, death, outOfBounds
    private Game game;

    public GameLogic(Game game) {
        this.game = game;
    }

    public void moveSnake(Snake snake) {
        SnakeBody head = snake.getHead();
        int nextX = head.getX(), nextY = head.getY();
        switch (head.getDirection()) {
            case LEFT -> nextX--;
            case UP -> nextY++;
            case RIGHT -> nextX++;
            case DOWN -> nextY--;
        }
        if (!interaction(snake, game.getUnitAt(nextX, nextY))) return;
        snake.move(nextX, nextY);
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

    public void SnakeWithFood(Snake snake, Food food) {
        //TODO: Eat
    }

    public void SnakeWithWall(Snake snake, Wall wall) {
        //TODO: удариться
    }

    public boolean SnakeToSnake(Snake movingSnake, SnakeBody stayingSnake) {


        return true;
    }

    public boolean spawnFood() {
        Random random = new Random();
        int x = random.nextInt(game.width());
        int y = random.nextInt(game.height());
        if (game.getUnitAt(x, y) instanceof Empty) {
            game.setGameUnit(new Food(x, y, 1));
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
            return true;
        }
        return false;
    }
}
