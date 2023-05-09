package ru.nsu.tsukanov.snakegame;


import ru.nsu.tsukanov.snakegame.console.ConsoleSnakePresenter;
import ru.nsu.tsukanov.snakegame.fx.SnakeFXApp;

import java.io.IOException;

public class HelloApplication {
    public static void main(String[] args) {
        if (args.length == 0 || args[0].equals("--javafx")) {
            SnakeFXApp app = new SnakeFXApp();
            app.run();
        } else {
            ConsoleSnakePresenter presenter;
            try {
                presenter = new ConsoleSnakePresenter();
                presenter.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


    }
}