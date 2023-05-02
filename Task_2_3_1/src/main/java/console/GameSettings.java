package console;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import console.settings.UserMode;
import model.game.field.FieldDAO;
import model.game.logic.Game;
import model.units.snake.Direction;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class GameSettings {
    private int gameSpeed = 1;
    private UserMode userMode;
    private Game game;
    private File file;

    private final Map<Character, Direction> keyCharacterDirectionMap;
    private final Map<KeyType, Direction> keyTypeDirectionMap;

    public GameSettings(File folder) {
        this.file = new File(folder, "1");
        setGame(new FieldDAO(file).getField());
        keyCharacterDirectionMap = new HashMap<>();
        keyTypeDirectionMap = new HashMap<>();
        initializeKeyMap();

    }

    private void initializeKeyMap() {
        keyTypeDirectionMap.put(KeyType.ArrowDown, Direction.UP);
        keyTypeDirectionMap.put(KeyType.ArrowUp, Direction.DOWN);
        keyTypeDirectionMap.put(KeyType.ArrowLeft, Direction.LEFT);
        keyTypeDirectionMap.put(KeyType.ArrowRight, Direction.RIGHT);

        keyCharacterDirectionMap.put('s', Direction.UP);
        keyCharacterDirectionMap.put('w', Direction.DOWN);
        keyCharacterDirectionMap.put('l', Direction.LEFT);
        keyCharacterDirectionMap.put('r', Direction.RIGHT);
        keyCharacterDirectionMap.put('S', Direction.UP);
        keyCharacterDirectionMap.put('W', Direction.DOWN);
        keyCharacterDirectionMap.put('L', Direction.LEFT);
        keyCharacterDirectionMap.put('R', Direction.RIGHT);
    }

    public Direction keyDirection(KeyStroke keyStroke) {
        if (keyStroke==null){
            return null;
        }
        if (keyStroke.getKeyType().equals(KeyType.Character)) {
            return keyCharacterDirectionMap.get(keyStroke.getCharacter());
        } else {
            return keyTypeDirectionMap.get(keyStroke.getKeyType());
        }
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
