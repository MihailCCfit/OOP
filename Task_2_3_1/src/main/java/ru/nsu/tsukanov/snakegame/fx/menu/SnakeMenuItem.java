package ru.nsu.tsukanov.snakegame.fx.menu;

import javafx.beans.binding.Bindings;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public class SnakeMenuItem extends Pane {
    private Text text;

    private Effect shadow = new DropShadow(5, Color.BLACK);
    private Effect blur = new BoxBlur(1, 1, 1);
    private Effect q = new Bloom();

    public SnakeMenuItem(String name) {
        Polygon bg = new Polygon(
                0, 0,
                300, 0,
                330, 30,
                300, 60,
                0, 60
        );

        text = new Text(name);
        text.setFont(new Font(28));
        text.setTranslateX(15);
        text.setTranslateY(40);
        // text.setFont(Font.loadFont(SnakeFXApp.class.getResource("res/Penumbra-HalfSerif-Std_35114.ttf").toExternalForm(), 14));
        text.setFill(Color.WHITE);

        text.effectProperty().bind(
                Bindings.when(hoverProperty())
                        .then(q)
                        .otherwise(shadow)
        );

        getChildren().addAll(bg, text);
    }

    public void setOnAction(Runnable action) {
        setOnMouseClicked(e -> action.run());
    }
}