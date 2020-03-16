package com.nickdnepr.smallgame.core.map;

import java.util.ArrayList;

public class GameMap {

    private int width;
    private int height;
    private Point[][] body;
    private ArrayList<Point> points;

    public GameMap(int width, int height) {
        this.width = width;
        this.height = height;
        this.body = new Point[height][width];
        this.points = new ArrayList<>();
    }

    public boolean addPoint(Point point) {
        if (getPoint(point.getCoordinates()) == null) {
            body[point.getCoordinates().getY()][point.getCoordinates().getX()] = point;
            points.add(point);
            return true;
        }
        return false;
    }

    public void deletePoint(Point point) {
        if (point!=null){
            if (points.contains(point)){
                body[point.getCoordinates().getY()][point.getCoordinates().getX()] = null;
                points.remove(point);
            }
        }
    }

    public void deletePoint(Coordinates coordinates){
        Point point = getPoint(coordinates);
        points.remove(point);
    }

    public Point getPoint(Coordinates coordinates) {
        return body[coordinates.getY()][coordinates.getX()];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }
}
