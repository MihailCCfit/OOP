
import console.ConsoleSnakePresenter;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        try {
            ConsoleSnakePresenter snakePresenter = new ConsoleSnakePresenter();
            snakePresenter.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
