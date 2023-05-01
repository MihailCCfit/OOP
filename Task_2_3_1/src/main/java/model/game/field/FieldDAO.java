package model.game.field;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.basic.Point;
import model.game.field.data.FoodData;
import model.game.field.data.PointData;
import model.game.logic.Game;
import model.units.Food;
import model.units.SnakeBody;
import model.units.Wall;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FieldDAO {
    private File file;


    public FieldDAO(File inputStream) {
        this.file = inputStream;
    }

    public void setInputStream(File inputStream) {
        this.file = inputStream;
    }

    public Game getField() {
        FieldData fieldData = null;
        try {
            fieldData = new ObjectMapper().readValue(new FileInputStream(file), FieldData.class);
        } catch (IOException e) {
            System.err.println("There is no field");
            throw new RuntimeException(e);
        }
        FieldConstructor fieldConstructor = new FieldConstructor(fieldData.width(), fieldData.height());
        fieldData.foods().forEach((foodData) -> fieldConstructor.setUnit(new Food(foodData.point().x(),
                foodData.point().y(), foodData.value())));
        fieldData.walls().forEach((pointData) ->
                fieldConstructor.setUnit(
                        new Wall(pointData.x(), pointData.y())
                ));
        fieldData.snakeHeads().forEach((snakeHead) ->
                fieldConstructor.setUnit(
                        new SnakeBody(snakeHead.x(), snakeHead.y())
                ));
        return fieldConstructor.getGameField();
    }

    public void saveField(Game game) {
        List<FoodData> foods = new ArrayList<>();
        List<PointData> walls = new ArrayList<>();
        List<PointData> snakeHeads = new ArrayList<>();
        game.getField().getAll().forEach((gameUnit) -> {
            if (gameUnit instanceof Food food) {
                foods.add(new FoodData(new PointData(food.getX(), food.getY()), food.getValue()));
            }
            if (gameUnit instanceof Wall wall) {
                walls.add(new PointData(wall.getX(), wall.getY()));
            }
            if (gameUnit instanceof SnakeBody) {
                snakeHeads.add(new PointData(gameUnit.getX(), gameUnit.getY()));
            }
        });
        FieldData fieldData = new FieldData(foods, walls, snakeHeads, game.width(), game.height());
        try {
            new ObjectMapper().writeValue(file, fieldData);
        } catch (IOException e) {
            System.err.println("LOX");
            throw new RuntimeException(e);
        }
    }
}

record FieldData(List<FoodData> foods, List<PointData> walls, List<PointData> snakeHeads, int width, int height) {

}
