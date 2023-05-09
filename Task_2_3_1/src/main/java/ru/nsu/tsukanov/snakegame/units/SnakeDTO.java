package ru.nsu.tsukanov.snakegame.units;

import java.util.List;

public record SnakeDTO(List<SnakeBodyDTO> body, SnakeHeadDTO head, boolean isAlive, Integer id) {

}
