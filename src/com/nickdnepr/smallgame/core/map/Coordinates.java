package com.nickdnepr.smallgame.core.map;

public class Coordinates {

    private int x;
    private int y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isSame(Coordinates coordinates) {
        return x == coordinates.x && y == coordinates.y;
    }

    public boolean isNear(Coordinates coordinates) {
        return Math.abs(x - coordinates.x) < 2 && Math.abs(y - coordinates.y) < 2 && !isSame(coordinates);
    }

    public boolean isCorner(Coordinates coordinates) {
        return isNear(coordinates) && x != coordinates.x && y != coordinates.y;
    }
}
