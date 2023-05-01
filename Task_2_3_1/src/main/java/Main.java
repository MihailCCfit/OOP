
import console.ConsoleSnakePresenter;
import model.gameField.FieldConstructor;
import model.gamelogic.Game;
import model.units.Food;
import model.units.SnakeBody;
import model.units.Wall;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        FieldConstructor fieldConstructor = new FieldConstructor(100, 40);
        fieldConstructor.setUnit(new Food(0, 0, 1));
        fieldConstructor.setUnit(new Wall(1, 1));
        fieldConstructor.setUnit(new SnakeBody(2, 2));
        Game game = fieldConstructor.getGameField();

        try {
            ConsoleSnakePresenter snakePresenter = new ConsoleSnakePresenter();
            snakePresenter.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
