package ru.nsu.tsukanov.snakegame.model.players;

import ru.nsu.tsukanov.snakegame.model.game.logic.Game;
import ru.nsu.tsukanov.snakegame.model.units.snake.Direction;

/**
 * Используется в логике игры. Где с каждого игрока спрашивается его следующее направление.
 * Позволяет реализовывать множество возможных вариантов для управления змейкой, хоть через http-запросы.
 */
abstract public class PlayerListener {
    protected final Game game;
    protected final Integer mySnakeId;

    public PlayerListener(Game game, Integer snakeId) {
        this.game = game;
        mySnakeId = snakeId;
    }

    /**
     * Следующее направление для змейки, которая передастся внутрь игры. Но например змейка не может
     * повернуться за раз на 180 градусов. Так что порой указания не будут работать.
     *
     * @return куда игрок хочет повернут змейку.
     */
    abstract public Direction nextDirection();
}
