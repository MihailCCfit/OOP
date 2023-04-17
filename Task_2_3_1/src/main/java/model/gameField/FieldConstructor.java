package model.gameField;

import model.units.Empty;
import model.units.GameUnit;
import model.units.Snake;
import model.units.SnakeBody;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FieldConstructor {
    private final List<List<GameUnit>> field;
    private final Set<SnakeBody> snakeHeads = new HashSet<>();

    public FieldConstructor(int width, int height) {
        field = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            ArrayList<GameUnit> list = new ArrayList<>();
            for (int j = 0; j < height; j++) {
                list.add(new Empty(i, j));
            }
            field.add(list);
        }
    }

    public void setUnit(GameUnit unit) {
        int x = unit.getX();
        int y = unit.getY();
        if (field.get(x).get(y) instanceof SnakeBody) {
            snakeHeads.remove(field.get(x).get(y));
        }
        if (field.get(x).get(y) instanceof Empty) {
            field.get(x).set(y, unit);
        }
        if (unit instanceof SnakeBody) {
            snakeHeads.add((SnakeBody) unit);
        }
    }

    public GameUnit getUnit(int x, int y) {
        return field.get(x).get(y);
    }

    public GameField getField() {
        List<Snake> snakes = snakeHeads.stream().map(Snake::new).toList();
        return new GameField(field, snakes);
    }
}
