
import console.ConsoleSnakePresenter;
import model.gameField.FieldConstructor;
import model.gamelogic.Game;
import model.units.Food;
import model.units.SnakeBody;
import model.units.Wall;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        FieldConstructor fieldConstructor = new FieldConstructor(50, 20);
        fieldConstructor.setUnit(new Food(0, 0, 1));
        fieldConstructor.setUnit(new Wall(1, 1));
        fieldConstructor.setUnit(new SnakeBody(2, 2));
        Game game = fieldConstructor.getGameField();
        ConsoleSnakePresenter snakePresenter = null;
        try {
            snakePresenter = new ConsoleSnakePresenter();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            snakePresenter.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
