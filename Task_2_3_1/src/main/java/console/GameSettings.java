package console;

import console.settings.UserMode;
import model.game.field.FieldDAO;
import model.game.logic.Game;

import java.io.File;

public class GameSettings {
    private int gameSpeed;
    private UserMode userMode;
    private Game game;
    private File file;

    public GameSettings(File folder) {
        this.file = new File(folder, "1");
        setGame(new FieldDAO(file).getField());
    }

    public File getFile() {
        return file;
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
