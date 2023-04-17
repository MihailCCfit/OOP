package model.gameField;

import model.units.Empty;
import model.units.GameUnit;

import java.util.ArrayList;
import java.util.List;

public class GameField {
    private final List<List<GameUnit>> field;

    public GameField(int width, int height) {
        field = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            ArrayList<GameUnit> list = new ArrayList<>();
            for (int j = 0; j < height; j++) {
                list.add(new Empty(i, j));
            }
            field.add(list);
        }
    }

    public int width() {
        return field.size();
    }

    public int height() {
        return field.get(0).size();
    }

    public GameUnit get(int x, int y) {
        return field.get(x).get(y);
    }

    public void set(int x, int y, GameUnit unit) {
        field.get(x).set(y, unit);
    }

    public void set(GameUnit unit) {
        field.get(unit.getX()).set(unit.getY(), unit);
    }
}
