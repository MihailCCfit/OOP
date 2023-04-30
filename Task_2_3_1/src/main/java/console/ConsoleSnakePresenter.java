package console;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import console.menu.ConsoleMenuPresenter;
import console.menu.states.MenuPage;
import console.settings.ConsoleSettingsPresenter;
import model.gamelogic.Game;

import java.io.File;
import java.io.IOException;

public class ConsoleSnakePresenter {
    private Game game;
    private Terminal terminal;
    private GameSettings settings = new GameSettings();

    public ConsoleSnakePresenter(){

    }

    public void start() throws IOException {
        terminal = new DefaultTerminalFactory()
                .setInitialTerminalSize(new TerminalSize(game.width(), game.height()))
                .createTerminal();
        ConsoleMenuPresenter menuPresenter = new ConsoleMenuPresenter(terminal);
        boolean flag = true;
        while (flag) {
            MenuPage menuPage = menuPresenter.start();

            switch (menuPage) {
                case Game -> {
                    System.out.println("Game");

                }
                case Settings -> {
                    File mapDir = new File("resources/maps");
                    System.out.println(mapDir.getAbsolutePath());
                    ConsoleSettingsPresenter settingsPresenter = new ConsoleSettingsPresenter(terminal, settings, mapDir);
                    settingsPresenter.runSettings();
                }
                case Exit -> {
                    flag = false;
                }
                case FieldConstructor -> {
                    System.out.println("Field constructor");

                }
            }
        }
        menuPresenter.close();
    }


}
