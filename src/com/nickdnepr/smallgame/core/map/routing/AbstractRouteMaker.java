package com.nickdnepr.smallgame.core.map.routing;

import com.nickdnepr.smallgame.core.map.Coordinates;
import com.nickdnepr.smallgame.core.map.GameMap;

public abstract class AbstractRouteMaker {

    protected GameMap gameMap;

    public AbstractRouteMaker(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public abstract Route getRoute(Coordinates source, Coordinates destination, RoutingPredicate routingPredicate);
}
