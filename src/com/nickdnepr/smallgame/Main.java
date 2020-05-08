package com.nickdnepr.smallgame;


import com.nickdnepr.smallgame.core.map.Coordinates;
import com.nickdnepr.smallgame.core.map.GameMap;
import com.nickdnepr.smallgame.core.map.Point;
import com.nickdnepr.smallgame.core.map.Relief;
import com.nickdnepr.smallgame.core.map.routing.RouteMaker;
import com.nickdnepr.smallgame.core.map.routing.RoutingPredicate;

public class Main {

    public static void main(String[] args) {

//        System.out.println(CoordinatesUtils.getNeighbours(new Coordinates(0, 0), 1, 2));
        long millis = System.currentTimeMillis();

        GameMap gameMap = new GameMap(3, 3);
        gameMap.addPoint(new Point(new Coordinates(0, 0), Relief.EARTH));
        gameMap.addPoint(new Point(new Coordinates(1, 0), Relief.EARTH));
        gameMap.addPoint(new Point(new Coordinates(2, 0), Relief.EARTH));

        gameMap.addPoint(new Point(new Coordinates(0, 1), Relief.EARTH));
        gameMap.addPoint(new Point(new Coordinates(1, 1), Relief.WATER));
        gameMap.addPoint(new Point(new Coordinates(2, 1), Relief.WATER));

        gameMap.addPoint(new Point(new Coordinates(0, 2), Relief.EARTH));
        gameMap.addPoint(new Point(new Coordinates(1, 2), Relief.WATER));
        gameMap.addPoint(new Point(new Coordinates(2, 2), Relief.EARTH));

        RouteMaker routeMaker = new RouteMaker(gameMap);
        routeMaker.getRoute(new Coordinates(1, 0), new Coordinates(0, 2), RoutingPredicate.standardPredicate());
        System.out.println("Time: "+(System.currentTimeMillis() - millis));
        millis = System.currentTimeMillis();

//        System.exit(0);

        int width = 64;
        int height = 64;
        GameMap gameMap2 = new GameMap(width, height);
//        gameMap.getPoint(new Coordinates(40,40));
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                gameMap2.addPoint(new Point(new Coordinates(x,y), Relief.EARTH));
            }
        }
        RouteMaker routeMaker2 = new RouteMaker(gameMap2);
        routeMaker2.getRoute(new Coordinates(0, 0), new Coordinates(2, 2), RoutingPredicate.standardPredicate());
//        CoordinatesUtils.getNeighbours(new Coordinates(0,0),2048, 2048);

//        Point[][] points = new Point[2048][2048]; 4 000 000
//        for (int i=0; i<200; i++){
//            points[i][i] = new Point(new Coordinates(i,i), Relief.EARTH);
//        }

        System.out.println();
        System.out.println("Time: "+(System.currentTimeMillis() - millis));
    }
}
