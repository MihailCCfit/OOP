package model.basic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Point2 {
    int x;
    int y;

    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }
}
