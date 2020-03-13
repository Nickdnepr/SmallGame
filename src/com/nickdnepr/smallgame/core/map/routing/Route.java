package com.nickdnepr.smallgame.core.map.routing;

import com.nickdnepr.smallgame.core.map.Coordinates;

import java.util.ArrayList;

public class Route {

    private ArrayList<Coordinates> routeBody;

    public Route(ArrayList<Coordinates> routeBody) {
        this.routeBody = routeBody;
    }

    public ArrayList<Coordinates> getRouteBody() {
        return routeBody;
    }
}
