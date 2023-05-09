package ru.nsu.tsukanov.snakegame;


import ru.nsu.tsukanov.snakegame.fx.SnakeFXApp;

public class HelloApplication {
    public static void main(String[] args) {
        System.out.println("Hi");
        SnakeFXApp app = new SnakeFXApp();
        app.run();
//        ConsoleSnakePresenter presenter;
//        try {
//            presenter = new ConsoleSnakePresenter();
//            presenter.start();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

    }
}