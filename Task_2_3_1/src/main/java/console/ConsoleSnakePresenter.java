package console;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import console.constructor.ConstructorPresenter;
import console.game.GamePresenter;
import console.menu.ConsoleMenuPresenter;
import console.menu.states.MenuPage;
import console.settings.ConsoleSettingsPresenter;
import console.utils.MenuConfig;
import model.game.logic.Game;

import java.io.File;
import java.io.IOException;

public class ConsoleSnakePresenter {
    private Terminal terminal;
    private Screen screen;
    private GameSettings settings;
    private final File mapDir;
    private TerminalSize defaultSize;

    public ConsoleSnakePresenter() throws IOException {

        terminal = new DefaultTerminalFactory()
                .setInitialTerminalSize(new TerminalSize(MenuConfig.WIDTH, MenuConfig.HEIGHT))
                .createTerminal();
        screen = new TerminalScreen(terminal);
        mapDir = new File("resources/maps");
        settings = new GameSettings(mapDir);
        defaultSize = terminal.getTerminalSize();
    }

    public void start() throws IOException {

        ConsoleMenuPresenter menuPresenter = new ConsoleMenuPresenter(screen);
        boolean flag = true;
        while (flag) {
            MenuPage menuPage = menuPresenter.start();
            switch (menuPage) {
                case Game -> {
                    terminal.setCursorVisible(false);
                    screen.setCursorPosition(new TerminalPosition(1000, 1000));
                    Game game = settings.getGame().getCopy();
                    TerminalSize oldSize = screen.getTerminalSize();
                    GamePresenter gamePresenter = new GamePresenter(game, screen);
                    gamePresenter.start();
                }
                case Settings -> {

                    System.out.println(mapDir.getAbsolutePath());
                    ConsoleSettingsPresenter settingsPresenter = new ConsoleSettingsPresenter(terminal, settings, mapDir);
                    settingsPresenter.runSettings();
                }
                case Exit -> {
                    flag = false;
                }
                case FieldConstructor -> {
                    ConstructorPresenter constructorPresenter = new ConstructorPresenter(settings.getFile(), screen);
                    settings.setGame(constructorPresenter.start());
                }
            }
        }
        menuPresenter.close();
    }


}
