package ru.nsu.tsukanov.snakegame.model.players;

import ru.nsu.tsukanov.snakegame.model.game.logic.Game;
import ru.nsu.tsukanov.snakegame.model.game.logic.GameLogic;
import ru.nsu.tsukanov.snakegame.model.units.*;
import ru.nsu.tsukanov.snakegame.model.units.snake.Direction;

import java.util.Map;
import java.util.Random;

public class CustomizableEuristickBot extends PlayerListener {
    /**
     * Should be from 0 to 1.
     */
    private double correct = 1;
    private int range = 3;
    private double distanceSquare = 1;
    private double distanceLinear = 2;
    private double distanceConstant = 0.2;


    private double tailPenalty = 10;
    private double anotherSnakePenalty = 40;
    private Map<Class<? extends GameUnit>, Double> penalties;
    private final Random random = new Random();

    public CustomizableEuristickBot(Game game, Integer snakeId) {
        super(game, snakeId);
        penalties = getInitPenalties();
    }

    public Map<Class<? extends GameUnit>, Double> getInitPenalties() {
        Map<Class<? extends GameUnit>, Double> map = Map.of(
                Empty.class, -5.0,
                Wall.class, 80.0,
                Food.class, -120.0,
                SnakeBody.class, 50.0
        );
        return map;
    }

    public Direction nextDirection() {
        SnakeBody snakeHead = game.getSnakeMap().get(mySnakeId).getHead();
        double continuePenalty = calculateTotalPenalty(getNextUnit(nextPoint(snakeHead)));
        GameUnit nextUnit;
        nextUnit = getNextUnit(
                nextPoint(snakeHead.getDirection().changeDirection(true),
                        snakeHead.getX(), snakeHead.getY()));
        double rightPenalty = calculateTotalPenalty(nextUnit);
        rightPenalty *= Math.max(random.nextDouble(), correct);
        nextUnit = getNextUnit(
                nextPoint(snakeHead.getDirection().changeDirection(false),
                        snakeHead.getX(), snakeHead.getY()));
        double leftPenalty = calculateTotalPenalty(nextUnit);
        leftPenalty *= Math.max(random.nextDouble(), correct);
        if (continuePenalty <= rightPenalty && continuePenalty <= leftPenalty) {
            return null;
        }
        if (rightPenalty < leftPenalty) {
            return snakeHead.getDirection().changeDirection(true);
        } else {
            return snakeHead.getDirection().changeDirection(false);
        }
    }

    private double getPenalty(GameUnit gameUnit) {
        if (gameUnit == null) {
            return 40;
        }
        if (gameUnit instanceof Food food) {
            return -((double) food.getValue() + 1) / 2 * penalties.get(Food.class);
        }
        if (gameUnit instanceof SnakeBody snakeBody) {
            double res = penalties.get(SnakeBody.class);
            if (!snakeBody.getSnake().getBody().isEmpty() && snakeBody.getSnake().getBody().getLast() == snakeBody) {
                res = tailPenalty;
            }
            if (snakeBody.getSnake() == game.getSnakeMap().get(mySnakeId)) {
                if (snakeBody.isHead()) {
                    res = 0;
                }
            } else {
                res += anotherSnakePenalty;
            }
            return res;
        } else {
            return penalties.get(gameUnit.getClass());
        }
    }

    private double distanceScale(double distance) {
        return 1 / (distance * distance + 2 * distance + 0.2);
    }


    public void addPenalty(Class<? extends GameUnit> gameUnitClass, double penalty) {
        penalties.put(gameUnitClass, penalty);
    }

    private Map.Entry<Integer, Integer> nextPoint(Direction direction, int x, int y) {
        return GameLogic.nextStep(direction, x, y);
    }

    private Map.Entry<Integer, Integer> nextPoint(SnakeBody head) {
        return nextPoint(head.getDirection(), head.getX(), head.getY());
    }


    private GameUnit getNextUnit(Map.Entry<Integer, Integer> pair) {
        int nextX = pair.getKey();
        int nextY = pair.getValue();
        return game.getUnitAt(nextX, nextY);
    }

    private double calculateTotalPenalty(GameUnit gameUnit) {
        if (gameUnit == null) {
            return -10000;
        }
        int x = gameUnit.getX();
        int y = gameUnit.getY();
        double res = 0;
        for (int i = x - range; i <= x + range; i++) {
            for (int j = y - range; j <= y + range; j++) {
                res += getPenalty(game.getUnitAt(i, j)) * distanceScale(Math.abs(i - x) + Math.abs(j - y));
            }
        }
        return res;
    }


    static public class BotBuilder {
        private final CustomizableEuristickBot customizableEuristickBot;

        public BotBuilder(Game game, Integer snakeId) {
            customizableEuristickBot = new CustomizableEuristickBot(game, snakeId);
        }

        public BotBuilder addBasicPenalties(Class<? extends GameUnit> gameUnitClass,
                                            Double penalty) {
            customizableEuristickBot.penalties.put(gameUnitClass, penalty);
            return this;
        }

        public BotBuilder setCorrect(Double correct) {
            if (correct >= 0 && correct <= 1) {
                customizableEuristickBot.correct = correct;
            }
            return this;
        }

        public CustomizableEuristickBot build() {
            return customizableEuristickBot;
        }
    }
}
