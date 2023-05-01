package console;

import console.settings.UserMode;
import model.gameField.FieldConstructor;
import model.gameField.FieldDAO;
import model.gamelogic.Game;
import model.units.Food;
import model.units.SnakeBody;
import model.units.Wall;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GameSettings {
    private int gameSpeed;
    private UserMode userMode;
    private Game game;

    public GameSettings()  {
        FieldConstructor constructor = new FieldConstructor(40, 40);
        constructor.setUnit(new Food(1,1,1));
        constructor.setUnit(new Wall(0,0));
        constructor.setUnit(new Wall(0,1));
        constructor.setUnit(new Wall(0,2));
        constructor.setUnit(new Wall(0,3));
        constructor.setUnit(new Wall(0,4));
        constructor.setUnit(new Wall(1,1));
        constructor.setUnit(new Wall(1,0));
        constructor.setUnit(new Wall(2,0));
        constructor.setUnit(new Wall(3,0));
        constructor.setUnit(new Wall(4,0));
        constructor.setUnit(new SnakeBody(5,5));
        game = constructor.getGameField();
    }

    public int getGameSpeed() {
        return gameSpeed;
    }

    public void setGameSpeed(int gameSpeed) {
        this.gameSpeed = gameSpeed;
    }

    public UserMode getUserMode() {
        return userMode;
    }

    public void setUserMode(UserMode userMode) {
        this.userMode = userMode;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
