package ru.nsu.tsukanov.snakegame.console.game.scenes;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.screen.Screen;
import ru.nsu.tsukanov.snakegame.console.common.FieldView;
import ru.nsu.tsukanov.snakegame.console.utils.ConsoleUtils;
import ru.nsu.tsukanov.snakegame.units.GameStateDTO;

import java.io.IOException;

public class GameScene extends FieldView {
    public GameScene(Screen screen) {
        super(screen);

    }

    public void drawStartScreen(GameStateDTO gameStateDTO) {
        var size = screen.getTerminalSize();
        update(gameStateDTO);
        ConsoleUtils.printLine(screen, "Press any key to start", new TerminalPosition(size.getColumns() / 2 - 10,
                size.getRows() / 2 - 10));
        try {

            screen.refresh(Screen.RefreshType.COMPLETE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
