package console.constructor;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import console.game.scenes.GameScene;
import console.game.units.*;
import model.game.field.FieldConstructor;
import model.game.field.FieldDAO;
import model.game.logic.Game;
import model.units.Empty;
import model.units.Food;
import model.units.SnakeBody;
import model.units.Wall;
import model.units.snake.Direction;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConstructorPresenter {
    private File file;
    private FieldConstructor fieldConstructor;
    private Screen screen;
    private TerminalPosition cursorPosition = new TerminalPosition(0, 0);
    private FieldDAO fieldDAO;
    private int width;
    private int height;

    public ConstructorPresenter(File file, Screen screen) {
        this.file = file;
        this.screen = screen;
        fieldDAO = new FieldDAO(file);

        var field = fieldDAO.getField().getField();
        fieldConstructor = new FieldConstructor(field.width(), field.height());
        field.getAll().forEach((gameUnit) -> fieldConstructor.setUnit(gameUnit));
        width = field.width();
        height = field.height();

    }

    public Game start() {
        GameScene gameScene = new GameScene(screen);
        boolean escapeFlag = true;
        while (escapeFlag) {
            Direction direction = null;
            try {
                KeyStroke keyStroke = screen.readInput();
                if (keyStroke != null) {
                    switch (keyStroke.getKeyType()) {
                        case Escape -> {
                            escapeFlag = false;
                        }
                        case ArrowDown -> cursorPosition = cursorPosition.withRow(Math.min(height-1,
                                cursorPosition.getRow() + 1));
                        case ArrowUp -> cursorPosition = cursorPosition.withRow(Math.max(0,
                                cursorPosition.getRow() - 1));
                        case ArrowLeft -> cursorPosition = cursorPosition.withColumn(Math.max(0,
                                cursorPosition.getColumn() - 1));
                        case ArrowRight -> cursorPosition = cursorPosition.withColumn(Math.min(width-1,
                                cursorPosition.getColumn() + 1));
                        case Delete -> {
                            fieldConstructor.setUnit(new Empty(cursorPosition.getColumn(),
                                    cursorPosition.getRow()));
                        }
                        case Character -> {
                            switch (keyStroke.getCharacter()) {
                                case 's', 'S' -> fieldConstructor.setUnit(new SnakeBody(cursorPosition.getColumn(),
                                        cursorPosition.getRow()));
                                case 'w', 'W' -> {
                                    fieldConstructor.setUnit(new Wall(cursorPosition.getColumn(),
                                            cursorPosition.getRow()));
                                }
                                case 'f', 'F' -> {
                                    fieldConstructor.setUnit(new Food(cursorPosition.getColumn(),
                                            cursorPosition.getRow(), 1));
                                }
                                case 'E', 'e' -> {
                                    fieldConstructor.setUnit(new Empty(cursorPosition.getColumn(),
                                            cursorPosition.getRow()));
                                }
                            }
                        }

                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            List<FoodDTO> foods = new ArrayList<>();
            List<WallDTO> walls = new ArrayList<>();
            fieldConstructor.getAll().forEach((unit -> {
                if (unit instanceof Food food) {
                    foods.add(new FoodDTO(new PointDTO(food.getX(), food.getY()), food.getValue()));
                }
                if (unit instanceof Wall wall) {
                    walls.add(new WallDTO(new PointDTO(wall.getX(), wall.getY())));
                }
            }));
            List<SnakeDTO> snakeDTOS = new ArrayList<>();
            fieldConstructor.getSnakes().forEach(snake -> {
                List<SnakeBodyDTO> bodyDTOS = new ArrayList<>();
                SnakeBody head = snake;
                bodyDTOS.add(new SnakeBodyDTO(new PointDTO(head.getX(),head.getY()), DirectionDTO.UP));
                snakeDTOS.add(new SnakeDTO
                        (bodyDTOS, new SnakeHeadDTO(
                                new PointDTO(head.getX(), head.getY()),
                                DirectionDTO.valueOf(head.getDirection().name())),
                                true)
                );
            });
            gameScene.update(new GameStateDTO(snakeDTOS, walls, foods));

            screen.setCursorPosition(cursorPosition);
            try {
                screen.refresh();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        Game game = fieldConstructor.getGameField();
        fieldDAO.saveField(game);
        return game;
    }
}
