package model.gameField;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.units.Food;
import model.units.SnakeBody;
import model.units.Wall;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class FieldDTO {
    private InputStream inputStream;


    public FieldDTO(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public GameField getField() {
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
        fieldData.walls().forEach(fieldConstructor::setUnit);
        fieldData.snakeHeads().forEach(fieldConstructor::setUnit);

        return null;
    }
}

record FieldData(List<Food> foods, List<Wall> walls, List<SnakeBody> snakeHeads, int width, int height) {

}
