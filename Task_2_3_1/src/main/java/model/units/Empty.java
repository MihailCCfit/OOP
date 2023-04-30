package model.units;

public final class Empty extends GameUnit {
    public Empty(int x, int y) {
        super(x, y);
    }

    public Empty(GameUnit unit) {
        super(unit.getX(), unit.getY());
    }
}