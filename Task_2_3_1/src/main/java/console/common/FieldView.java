package console.common;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;
import console.game.sprites.UnitsCharacters;
import console.game.units.*;

import java.io.IOException;

public class FieldView {
    private Screen screen;

    public FieldView(Screen screen) {
        this.screen = screen;
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
        TextColor textColor1 = TextColor.ANSI.DEFAULT;
        if (snakeDTO.id() == 0) {
            textColor1 = TextColor.ANSI.YELLOW;
        }
        final TextColor textColor = textColor1;
        setCharacterAtPoint(new PointDTO(snakeHead.pointDTO().x(), snakeHead.pointDTO().y()),
                new TextCharacter(headCharacter, textColor, TextColor.ANSI.BLACK));
        snakeDTO.body().forEach(snakeBodyDTO -> {
            var snakeBody = snakeBodyDTO.pointDTO();
            setCharacterAtPoint(snakeBody, new TextCharacter(UnitsCharacters.BODY, textColor, TextColor.ANSI.BLACK));
        });

    }

    public void drawWall(WallDTO wall) {
        setCharacterAtPoint(wall.point(), UnitsCharacters.WALL);
    }

    private void setCharacterAtPoint(PointDTO point, Character character) {
        screen.setCharacter(point.x(), point.y(), new TextCharacter(character));
    }

    private void setCharacterAtPoint(PointDTO point, TextCharacter textCharacter) {
        screen.setCharacter(point.x(), point.y(), textCharacter);
    }
}
