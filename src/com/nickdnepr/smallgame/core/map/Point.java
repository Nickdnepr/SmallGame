package com.nickdnepr.smallgame.core.map;

import com.nickdnepr.smallgame.core.objects.BaseEntity;

import java.util.ArrayList;

public class Point {

    private Coordinates coordinates;
    private Relief relief;
    private ArrayList<BaseEntity> objects;

    public Point(Coordinates coordinates, Relief relief, ArrayList<BaseEntity> objects) {
        this.coordinates = coordinates;
        this.relief = relief;
        this.objects = objects;
    }

    public Point(Coordinates coordinates, Relief relief) {
        this.coordinates = coordinates;
        this.relief = relief;
        this.objects = new ArrayList<>();
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Relief getRelief() {
        return relief;
    }

    public ArrayList<BaseEntity> getObjects() {
        return objects;
    }

    public void setRelief(Relief relief) {
        this.relief = relief;
    }

    @Override
    public String toString() {
        return "Point{" +
                "coordinates=" + coordinates +
                ", relief=" + relief +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (coordinates != null ? !coordinates.equals(point.coordinates) : point.coordinates != null) return false;
        return relief == point.relief;
    }

    @Override
    public int hashCode() {
        int result = coordinates != null ? coordinates.hashCode() : 0;
        result = 31 * result + (relief != null ? relief.hashCode() : 0);
        return result;
    }
}
