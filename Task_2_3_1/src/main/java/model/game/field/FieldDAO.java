package model.game.field;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.game.logic.Game;
import model.units.Food;
import model.units.SnakeBody;
import model.units.Wall;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class FieldDAO {
    private InputStream inputStream;


    public FieldDAO(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public Game getField() {
        FieldData fieldData = null;
        try {
            fieldData = new ObjectMapper().readValue(inputStream, FieldData.class);
        } catch (IOException e) {
            System.err.println("There is no field");
            throw new RuntimeException(e);
        }
        FieldConstructor fieldConstructor = new FieldConstructor(fieldData.width(), fieldData.height());
        fieldData.foods().forEach(fieldConstructor::setUnit);
        fieldData.walls().forEach(fieldConstructor::setUnit);
        fieldData.snakeHeads().forEach(fieldConstructor::setUnit);
        return fieldConstructor.getGameField();
    }
}

record FieldData(List<Food> foods, List<Wall> walls, List<SnakeBody> snakeHeads, int width, int height) {

}
