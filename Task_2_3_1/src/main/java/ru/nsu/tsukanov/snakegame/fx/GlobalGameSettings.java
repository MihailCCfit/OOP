package ru.nsu.tsukanov.snakegame.fx;

import ru.nsu.tsukanov.snakegame.console.GameSettings;

import java.io.File;

public class GlobalGameSettings {
    final public static GameSettings gameSettings = new GameSettings(new File("resources/maps"));
}
