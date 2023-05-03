package console;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import console.constructor.ConstructorScene;
import console.game.GamePresenter;
import console.menu.ConsoleMenuScene;
import console.menu.states.MenuPage;
import console.settings.ConsoleSettingsScene;
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

        ConsoleMenuScene menuScene = new ConsoleMenuScene(screen);
        boolean flag = true;
        while (flag) {
            MenuPage menuPage = menuScene.start();
            switch (menuPage) {
                case Game -> {
                    terminal.setCursorVisible(false);
                    screen.setCursorPosition(new TerminalPosition(1000, 1000));
                    Game game = settings.getGame().getCopy();
                    GamePresenter gamePresenter = new GamePresenter(game, settings, screen);
                    gamePresenter.start();
                }
                case Settings -> {
                    ConsoleSettingsScene settingsPresenter = new ConsoleSettingsScene(screen, settings);
                    settingsPresenter.start();
                }
                case Exit -> {
                    flag = false;
                }
                case FieldConstructor -> {
                    ConstructorScene constructorScene = new ConstructorScene(settings.getFile(), screen);
                    settings.setGame(constructorScene.start());
                }
            }
        }
        menuScene.close();
    }


}
