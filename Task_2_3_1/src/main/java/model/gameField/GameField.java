package model.gameField;

import model.units.GameUnit;
import model.units.Snake;

import java.util.ArrayList;
import java.util.List;

public class GameField {
    private List<List<GameUnit>> field;
    private List<Snake> snakes;

    public GameField(List<List<GameUnit>> field, List<Snake> snakes) {
        this.field = field;
        this.snakes = snakes;
    }
}
