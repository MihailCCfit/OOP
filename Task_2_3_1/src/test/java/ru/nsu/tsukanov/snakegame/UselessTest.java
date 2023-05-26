package ru.nsu.tsukanov.snakegame;

import org.junit.jupiter.api.Test;
import ru.nsu.tsukanov.snakegame.model.game.field.GameField;
import ru.nsu.tsukanov.snakegame.model.game.logic.Game;

import java.util.HashMap;

public class UselessTest {
    @Test
    void test() {
        Game game = new Game(new GameField(500, 500), new HashMap<>());
        game.tick();
    }
}
