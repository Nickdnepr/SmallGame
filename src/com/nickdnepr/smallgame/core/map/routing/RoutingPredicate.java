package com.nickdnepr.smallgame.core.map.routing;

import com.nickdnepr.smallgame.core.map.Point;
import com.nickdnepr.smallgame.core.map.Relief;

public interface RoutingPredicate {

    boolean validatePoint(Point point);

    double getMovementPrice(Point source, Point destination);

    default RoutingPredicate standardPredicate() {
        return new RoutingPredicate() {
            @Override
            public boolean validatePoint(Point point) {
                return point.getRelief() != Relief.WATER && point.getRelief() != Relief.MOUNTAIN;
            }

            @Override
            public double getMovementPrice(Point source, Point destination) {
                double price = 1.0;
                price *= destination.getRelief().getMovementCoefficient();
                price *= destination.getCoordinates().isCorner(source.getCoordinates()) ? 1.41 : 1.0;
                return price;
            }
        };
    }
}
