package com.nickdnepr.smallgame.core.map.routing;

import com.nickdnepr.smallgame.core.map.Coordinates;
import com.nickdnepr.smallgame.core.map.GameMap;
import com.nickdnepr.smallgame.core.map.Point;

import java.util.ArrayList;

public class RouteMaker {

    private GameMap gameMap;

    public RouteMaker(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public Route getRoute(Coordinates source, Coordinates destination, RoutingPredicate routingPredicate) {
        int width = gameMap.getWidth();
        int height = gameMap.getHeight();
        int[][] routingArea = new int[height][width];
        Point sourcePoint = gameMap.getPoint(source);
        Point destinationPoint = gameMap.getPoint(source);
        ArrayList<Point> pointsToProcess = new ArrayList<>();
        ArrayList<Point> routingAreaPoints = new ArrayList<>();
        pointsToProcess.add(sourcePoint);
        routingArea[source.getY()][source.getX()] = 1;
        routingAreaPoints.add(gameMap.getPoint(source));
        while (!pointsToProcess.isEmpty()) {
            Point point = pointsToProcess.get(0);
            if (!routingPredicate.validatePoint(point)) {
                throw new IllegalArgumentException("Point is not valid for current predicate");
            }

            ArrayList<Coordinates> coordinatesToCheck = getNeighbours(point.getCoordinates(), routingArea);
            while (!coordinatesToCheck.isEmpty()) {
                Coordinates coordinatesToProcess = coordinatesToCheck.get(0);
                Point p = gameMap.getPoint(coordinatesToProcess);
                if (routingPredicate.validatePoint(p) && routingArea[coordinatesToProcess.getY()][coordinatesToProcess.getX()] == 0) {
                    pointsToProcess.add(p);
                    routingAreaPoints.add(p);
                    routingArea[coordinatesToProcess.getY()][coordinatesToProcess.getX()] = 1;
                } else {
                    routingArea[coordinatesToProcess.getY()][coordinatesToProcess.getX()] = -1;
                }
                coordinatesToCheck.remove(coordinatesToProcess);
            }
            pointsToProcess.remove(point);
        }

        System.out.println(routingAreaPoints.size());

        return null;
    }

    private static ArrayList<Coordinates> getNeighbours(Coordinates coordinates, int[][] checkedMatrix) {
        ArrayList<Coordinates> result = new ArrayList<>();
        int height = checkedMatrix.length;
        int width = checkedMatrix[0].length;
        boolean topBorder = coordinates.getY() == 0;
        boolean bottomBorder = coordinates.getY() == height - 1;
        boolean leftBorder = coordinates.getX() == 0;
        boolean rightBorder = coordinates.getX() == width - 1;

        if (!topBorder) {
            if (checkedMatrix[coordinates.getY() - 1][coordinates.getX()] == 0) {
                result.add(new Coordinates(coordinates.getX(), coordinates.getY() - 1));
            }
            if (!leftBorder && checkedMatrix[coordinates.getY() - 1][coordinates.getX() - 1] == 0) {
                result.add(new Coordinates(coordinates.getX() - 1, coordinates.getY() - 1));
            }
            if (!rightBorder && checkedMatrix[coordinates.getY() - 1][coordinates.getX() + 1] == 0) {
                result.add(new Coordinates(coordinates.getX() + 1, coordinates.getY() - 1));
            }
        }
        if (!bottomBorder) {
            if (checkedMatrix[coordinates.getY() + 1][coordinates.getX()] == 0) {
                result.add(new Coordinates(coordinates.getX(), coordinates.getY() + 1));
            }
            if (!leftBorder && checkedMatrix[coordinates.getY() + 1][coordinates.getX() - 1] == 0) {
                result.add(new Coordinates(coordinates.getX() - 1, coordinates.getY() + 1));
            }
            if (!rightBorder && checkedMatrix[coordinates.getY() + 1][coordinates.getX() + 1] == 0) {
                result.add(new Coordinates(coordinates.getX() + 1, coordinates.getY() + 1));
            }
        }
        if (!leftBorder) {
            if (checkedMatrix[coordinates.getY()][coordinates.getX() - 1] == 0) {
                result.add(new Coordinates(coordinates.getX() - 1, coordinates.getY()));
            }
        }
        if (!rightBorder) {
            if (checkedMatrix[coordinates.getY()][coordinates.getX() + 1] == 0) {
                result.add(new Coordinates(coordinates.getX() + 1, coordinates.getY()));
            }
        }
        return result;
    }
}
