package com.nickdnepr.smallgame.core.map.routing;

import com.nickdnepr.smallgame.core.map.Coordinates;
import com.nickdnepr.smallgame.core.map.GameMap;

public class RouteMaker {

    private GameMap gameMap;

    public RouteMaker(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public Route getRoute(Coordinates source, Coordinates destination, RoutingPredicate routingPredicate){
        
        return null;
    }
}
