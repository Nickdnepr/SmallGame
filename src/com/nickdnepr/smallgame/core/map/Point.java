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
}
