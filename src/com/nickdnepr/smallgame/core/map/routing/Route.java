package com.nickdnepr.smallgame.core.map.routing;

import com.nickdnepr.smallgame.core.map.Coordinates;
import com.nickdnepr.smallgame.core.utils.Pair;

import java.util.ArrayList;

public class Route {

    private ArrayList<Pair<Coordinates, Double>> routeBody;

    public Route(ArrayList<Pair<Coordinates, Double>> routeBody) {
        this.routeBody = routeBody;
    }

    public ArrayList<Pair<Coordinates, Double>> getRouteBody() {
        return routeBody;
    }
}
