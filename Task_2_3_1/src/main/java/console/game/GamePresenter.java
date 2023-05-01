package console.game;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import console.game.scenes.GameScene;
import console.game.units.*;
import model.gamelogic.Game;
import model.units.Food;
import model.units.SnakeBody;
import model.units.Wall;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GamePresenter {

    private Game game;
    private Screen screen;
    private boolean escapeFlag = false;

    public GamePresenter(Game game, Screen screen) {
        this.game = game;
        this.screen = screen;
    }

    public int start() {
        GameScene gameScene = new GameScene(screen);
        try {
            screen.readInput();
            screen.clear();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        while (loopCondition()) {
            try {
                KeyStroke keyStroke = screen.pollInput();
                if (keyStroke != null) {
                    switch (keyStroke.getKeyType()) {
                        case Escape -> {
                            escapeFlag = true;
                        }

                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            List<FoodDTO> foods = new ArrayList<>();
            List<WallDTO> walls = new ArrayList<>();
            game.getField().getAll().forEach((unit -> {
                if (unit instanceof Food food) {
                    foods.add(new FoodDTO(new PointDTO(food.getX(), food.getY()), food.getValue()));
                }
                if (unit instanceof Wall wall) {
                    walls.add(new WallDTO(new PointDTO(wall.getX(), wall.getY())));
                }
            }));
            List<SnakeDTO> snakeDTOS = new ArrayList<>();
            game.getSnakeList().forEach(snake -> {
                List<SnakeBodyDTO> bodyDTOS = new ArrayList<>();
                snake.getBody().forEach(snakeBody -> {
                    bodyDTOS.add(new SnakeBodyDTO(new PointDTO(snakeBody.getX(), snakeBody.getY()),
                            DirectionDTO.valueOf(snakeBody.getDirection().toString())));
                });
                SnakeBody head = snake.getHead();
                snakeDTOS.add(new SnakeDTO(bodyDTOS, new SnakeHeadDTO(new PointDTO(head.getX(), head.getY()),
                        DirectionDTO.valueOf(head.getDirection().name()))));
            });
            gameScene.update(new GameStateDTO(snakeDTOS, walls, foods));
            game.tick();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        return 1;
    }

    public boolean loopCondition() {

        return !escapeFlag;
    }

}
