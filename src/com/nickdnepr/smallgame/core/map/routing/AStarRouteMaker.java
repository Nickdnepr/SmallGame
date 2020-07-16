package com.nickdnepr.smallgame.core.map.routing;

import com.nickdnepr.smallgame.core.map.Coordinates;
import com.nickdnepr.smallgame.core.map.GameMap;

public class AStarRouteMaker extends AbstractRouteMaker{


    public AStarRouteMaker(GameMap gameMap) {
        super(gameMap);
    }

    @Override
    public Route getRoute(Coordinates source, Coordinates destination, RoutingPredicate routingPredicate) {
        return null;
    }
}
