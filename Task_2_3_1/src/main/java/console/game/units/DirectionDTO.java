package console.game.units;

import model.units.snake.Direction;

import java.util.Random;

public enum DirectionDTO {
    LEFT, UP, RIGHT, DOWN;



    public int getAngle() {
        return switch (this) {
            case LEFT -> -90;
            case UP -> 0;
            case RIGHT -> 90;
            case DOWN -> 180;
        };
    }

}
