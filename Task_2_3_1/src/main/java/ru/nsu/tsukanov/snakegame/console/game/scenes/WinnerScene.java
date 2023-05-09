package ru.nsu.tsukanov.snakegame.console.game.scenes;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;
import ru.nsu.tsukanov.snakegame.console.game.sprites.utils.ConsoleUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class WinnerScene {
    private Screen screen;
    private Map<Integer, Long> results;

    public WinnerScene(Screen screen, Map<Integer, Long> results) {
        this.screen = screen;

        this.results = results;
    }

    public void showWinners() {
        List<Map.Entry<Integer, Long>> listOfWinners = results.entrySet().stream()
                .sorted((entry1, entry2) -> -Long.compare(entry1.getValue(), entry2.getValue()))
                .limit(3).toList();
        TerminalSize size = screen.getTerminalSize();
        ConsoleUtils.printLine(screen, "Winners !!!      ", new TerminalPosition(size.getColumns() / 2 - 4,
                1), TextColor.ANSI.CYAN);
        int maxIndex = 0;
        for (int i = 0; i < listOfWinners.size(); i++) {
            Map.Entry<Integer, Long> player = listOfWinners.get(i);
            ConsoleUtils.printLine(screen, "Player " + player.getKey() + ": " + player.getValue() + "       ",
                    new TerminalPosition(size.getColumns() / 2 - 4,
                            2 + i));
            maxIndex = i;
        }
        maxIndex += 1;
        ConsoleUtils.printLine(screen, "Press Esc for exit      ",
                new TerminalPosition(size.getColumns() / 2 - 4,
                        2 + maxIndex));
        try {
            screen.refresh();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
