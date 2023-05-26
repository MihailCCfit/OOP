package ru.nsu.tsukanov.snakegame.console.menu;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;
import ru.nsu.tsukanov.snakegame.console.game.sprites.utils.ConsoleUtils;

import java.io.IOException;

public class MenuView {
    private Screen screen;
    private TerminalPosition playPosition;
    private TerminalPosition settingsPosition;
    private TerminalPosition editorPosition;

    public MenuView(Screen screen) throws IOException {
        this.screen = screen;
        screen.clear();
        TerminalSize size = screen.getTerminalSize();
        initialisePositions();
        ConsoleUtils.printLine(screen, "Snake game",
                new TerminalPosition(Math.min(3, size.getColumns() / 2 - 3), 1), TextColor.ANSI.CYAN);

        ConsoleUtils.printLine(screen, "Play", playPosition);
        ConsoleUtils.printLine(screen, "Settings", settingsPosition);
        ConsoleUtils.printLine(screen, "Editor", editorPosition);
        setCursorPosition(0);
        screen.refresh();
    }

    private void initialisePositions() {
        TerminalSize size = screen.getTerminalSize();
        playPosition = new TerminalPosition(Math.min(3, size.getColumns() / 2 - 3),
                Math.min(5, size.getRows() / 4));

        settingsPosition = new TerminalPosition(Math.min(3, size.getColumns() / 2 - 3),
                Math.min(playPosition.getRow() + 3, size.getRows() / 2));

        editorPosition = new TerminalPosition(Math.min(3, size.getColumns() / 2 - 3),
                Math.min(settingsPosition.getRow() + 3,
                        size.getRows() * 3 / 4));

    }
    public void clear() {
        screen.clear();
    }

    public void close() {
        screen.clear();
        try {
            screen.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setCursorPosition(int index) throws IOException {
        if (index < 0 || index > 2) {
            throw new RuntimeException();
        }
        switch (index) {
            case 0 -> screen.setCursorPosition(playPosition.withColumn(playPosition.getColumn() - 1));
            case 1 -> screen.setCursorPosition(settingsPosition.withColumn(settingsPosition.getColumn() - 1));
            case 2 -> screen.setCursorPosition(editorPosition.withColumn(editorPosition.getColumn() - 1));
        }
        screen.refresh();
    }
}
