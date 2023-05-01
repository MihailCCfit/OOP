package console.game.scenes;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.screen.Screen;
import console.game.sprites.UnitsCharacters;
import console.game.units.*;
import console.utils.ConsoleUtils;

import java.io.IOException;

public class GameScene {
    private Screen screen;

    public GameScene(Screen screen) {
        this.screen = screen;
        screen.clear();
        var size = screen.getTerminalSize();

        ConsoleUtils.printLine(this.screen, "Press any key to start", new TerminalPosition(size.getColumns() / 2 - 10,
                size.getRows() / 2 - 10));
        try {

            screen.refresh(Screen.RefreshType.COMPLETE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(GameStateDTO gameStateDTO) {
        screen.clear();
        gameStateDTO.walls().forEach(this::drawWall);
        gameStateDTO.foodDTOS().forEach(this::drawFood);
        gameStateDTO.snakes().forEach(snakeDTO -> {
            if (snakeDTO.isAlive()) {
                drawSnake(snakeDTO);
            }
        });
        try {
            screen.refresh();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void drawFood(FoodDTO foodDTO) {
        setCharacterAtPoint(foodDTO.foodPoint(), UnitsCharacters.FOOD1);
    }

    private void drawSnake(SnakeDTO snakeDTO) {
        var snakeHead = snakeDTO.head();
        Character headCharacter = UnitsCharacters.HEADDOWN;
        switch (snakeHead.directionDTO()) {
            case UP -> headCharacter = UnitsCharacters.HEADUP;
            case DOWN -> headCharacter = UnitsCharacters.HEADDOWN;
            case LEFT -> headCharacter = UnitsCharacters.HEADLEFT;
            case RIGHT -> headCharacter = UnitsCharacters.HEADRIGHT;
        }
        setCharacterAtPoint(new PointDTO(snakeHead.pointDTO().x(), snakeHead.pointDTO().y()), headCharacter);
        snakeDTO.body().forEach(snakeBodyDTO -> {
            var snakeBody = snakeBodyDTO.pointDTO();
            setCharacterAtPoint(snakeBody, UnitsCharacters.BODY);
        });

    }

    public void drawWall(WallDTO wall) {
        setCharacterAtPoint(wall.point(), UnitsCharacters.WALL);
    }

    private void setCharacterAtPoint(PointDTO point, Character character) {
        screen.setCharacter(point.x(), point.y(), new TextCharacter(character));
    }
}
