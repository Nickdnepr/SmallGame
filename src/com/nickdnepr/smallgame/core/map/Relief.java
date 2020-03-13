package com.nickdnepr.smallgame.core.map;

public enum  Relief {

    EARTH(1),
    SAND(1.5),
    SWAMP(2),
    WATER(1),
    HILL(1.25),
    MOUNTAIN(1.75);

    private double movementCoefficient;

    Relief(double movementCoefficient) {
        this.movementCoefficient = movementCoefficient;
    }

    public double getMovementCoefficient() {
        return movementCoefficient;
    }
}
