package ru.nsu.tsukanov.snakegame.console.game;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import ru.nsu.tsukanov.snakegame.console.GameSettings;
import ru.nsu.tsukanov.snakegame.console.game.scenes.GameScene;
import ru.nsu.tsukanov.snakegame.console.game.scenes.WinnerScene;
import ru.nsu.tsukanov.snakegame.console.settings.UserMode;
import ru.nsu.tsukanov.snakegame.model.game.logic.Game;
import ru.nsu.tsukanov.snakegame.model.players.CommonBotPlayer;
import ru.nsu.tsukanov.snakegame.model.players.EuristickBot;
import ru.nsu.tsukanov.snakegame.model.players.HumanPlayer;
import ru.nsu.tsukanov.snakegame.model.players.PlayerListener;
import ru.nsu.tsukanov.snakegame.model.units.snake.Direction;
import ru.nsu.tsukanov.snakegame.units.GameStateDTO;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class GamePresenter {

    private Game game;
    private Screen screen;
    private boolean escapeFlag = false;
    private GameSettings gameSettings;

    public GamePresenter(Game game, GameSettings gameSettings, Screen screen) {
        this.game = game;
        this.screen = screen;
        this.gameSettings = gameSettings;
    }

    public int start() {
        GameScene gameScene = new GameScene(screen);
        gameScene.drawStartScreen(GameStateDTO.getGameState(game));
        HumanPlayer humanPlayer = new HumanPlayer(game, 0);
        if (gameSettings.getUserMode() == UserMode.Player) {
            game.addPlayer(0, humanPlayer);
        } else {
            game.addPlayer(0, getBot(0));
        }
        game.getSnakeMap().forEach(((id, snake) -> {
            if (id != 0) {
                game.addPlayer(id, getBot(id));
            }
        }));
        try {
            screen.readInput();
            screen.clear();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        DirectionWrapper directionWrapper = new DirectionWrapper(null);

        Timer timer = new Timer();
        BooleanWrapper booleanWrapper = new BooleanWrapper(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!booleanWrapper.flag) {
                    this.cancel();
                }
                gameScene.update(GameStateDTO.getGameState(game));
                try {
                    screen.refresh();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (directionWrapper.direction != null) {
                    humanPlayer.setDirection(directionWrapper.direction);
                }
                if (game.tick()) {
                    try {
                        Thread.sleep(10 * ((long) (8 - gameSettings.getGameSpeed())));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    //TODO: Список победителей
                    booleanWrapper.flag = false;
                    this.cancel();
                }
            }
        }, 100, 10 * ((long) (8 - gameSettings.getGameSpeed())));

        while (loopCondition() && booleanWrapper.flag) {
            try {
                KeyStroke keyStroke = null;
                KeyStroke tmpStroke;
                while ((tmpStroke = screen.pollInput()) != null) {
                    keyStroke = tmpStroke;
                }
                if (keyStroke != null) {
                    switch (keyStroke.getKeyType()) {
                        case Escape -> {
                            escapeFlag = true;
                        }
                        default -> directionWrapper.direction = gameSettings.keyDirection(keyStroke);

                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        WinnerScene winnerScene = new WinnerScene(screen, game.getResults());
        winnerScene.showWinners();
        timer.cancel();
        while (true) {
            KeyStroke key = null;
            try {
                key = screen.readInput();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (key.getKeyType().equals(KeyType.Escape)) {
                break;
            }
        }

        return 1;
    }

    public boolean loopCondition() {

        return !escapeFlag;
    }

    private PlayerListener getBot(int id) {
        if (true) {
            return new EuristickBot(game, id);
        } else {
            return new CommonBotPlayer(game, id);
        }

    }


    private static class DirectionWrapper {
        Direction direction;

        public DirectionWrapper(Direction direction) {
            this.direction = direction;
        }
    }

    private static class BooleanWrapper {
        boolean flag;

        public BooleanWrapper(boolean flag) {
            this.flag = flag;
        }
    }

}
