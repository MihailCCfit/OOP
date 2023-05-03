package console.game.scenes;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.screen.Screen;
import console.common.FieldView;
import console.game.units.GameStateDTO;
import console.utils.ConsoleUtils;

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
