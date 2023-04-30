package console.settings;

import com.googlecode.lanterna.terminal.Terminal;
import console.GameSettings;

import java.io.File;

public class ConsoleSettingsPresenter {

    private GameSettings settings;
    private Terminal terminal;
    private File folder;

    public ConsoleSettingsPresenter(Terminal terminal, GameSettings settings, File folder) {
        this.folder = folder;
        this.terminal = terminal;
        this.settings = settings;
    }

    public void runSettings() {
        System.out.println("Was something interesting");

        return;
    }


}
