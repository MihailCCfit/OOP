package ru.nsu.tsukanov.snakegame.fx.game;

import javafx.scene.SnapshotParameters;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import ru.nsu.tsukanov.snakegame.units.DirectionDTO;

import java.util.HashMap;
import java.util.Map;

public class SnakeDrawer {
    private final SnapshotParameters parameters;
    private final int playersAmount;
    private final Map<Integer, HeadBody> imageViewMap = new HashMap<>();

    private GameController gameController;


    public SnakeDrawer(int playersAmount, GameController gameController) {
        this.gameController = gameController;
        this.playersAmount = playersAmount;
        parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        initSkinMap();
    }

    private void initSkinMap() {
        for (int id = 0; id < playersAmount; id++) {
            ImageView headView = new ImageView(ImageCollector.snakeHead);
            ImageView bodyView = new ImageView(ImageCollector.snakeBody);
            ColorAdjust color = new ColorAdjust((double) id / playersAmount * 1.1, 0, 0, 0);
            headView.setEffect(color);
            bodyView.setEffect(color);
            imageViewMap.put(id, new HeadBody(headView, bodyView));
        }
    }

    private record HeadBody(ImageView headView, ImageView bodyView) {
    }


    public Image getHeadImage(DirectionDTO headDirection, int id) {
        HeadBody headBody = imageViewMap.get(id);
        ImageView headView = headBody.headView;
        int headRotation = headDirection.getAngle();
        headView.setRotate(headRotation);
        Image headImage = headView.snapshot(parameters, null);
        headView.setRotate(-headRotation);
        return headImage;
    }

    public Image getHeadImage(int id) {
        HeadBody headBody = imageViewMap.get(id);
        ImageView headView = headBody.headView;
        return headView.snapshot(parameters, null);
    }

    public Image getBodyImage(DirectionDTO bodyDirection, int id) {
        HeadBody headBody = imageViewMap.get(id);
        ImageView bodyView = headBody.bodyView;
        int angle = bodyDirection.getAngle();
        bodyView.setRotate(angle);
        Image bodyImage = bodyView.snapshot(parameters, null);
        bodyView.setRotate(-angle);
        return bodyImage;
    }


}