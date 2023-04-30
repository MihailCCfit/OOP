package console.menu;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.Terminal;
import console.menu.states.MenuPage;

import java.io.IOException;

public class ConsoleMenuPresenter {

    private Terminal terminal;
    private MenuView menuView;

    private Screen screen;
    private int state = 0;

    public ConsoleMenuPresenter(Terminal terminal) {
        this.terminal = terminal;
    }

    public MenuPage start() throws IOException {
        screen = new TerminalScreen(terminal);
        screen.startScreen();
        menuView = new MenuView(screen);
        state = 0;
        while (true) {
            int newState = state;
            KeyStroke keyStroke = terminal.readInput();
            KeyType keyType = keyStroke.getKeyType();
            if (keyType.equals(KeyType.Enter)) {
                break;
            }
            if (keyType.equals(KeyType.Escape)) {
                state = 3;
                break;
            }
            if (keyType.equals(KeyType.ArrowDown)) {
                newState++;

            }
            if (keyType.equals(KeyType.ArrowUp)) {
                newState--;
            }

            if (newState < 0) {
                newState = 0;
            }
            if (newState > 2) {
                newState = 2;
            }
            if (newState != state) {
                state = newState;
                menuView.setCursorPosition(state);
            }
        }
        menuView.clear();

        System.out.println(MenuPage.getMenuPage(state));
        return MenuPage.getMenuPage(state);
    }

    public void close() {
        menuView.close();
    }


}