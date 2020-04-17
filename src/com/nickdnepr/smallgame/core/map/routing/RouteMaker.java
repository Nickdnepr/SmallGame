package com.nickdnepr.smallgame.core.map.routing;

import com.nickdnepr.smallgame.core.map.Coordinates;
import com.nickdnepr.smallgame.core.map.GameMap;
import com.nickdnepr.smallgame.core.map.Point;
import com.nickdnepr.smallgame.core.utils.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class RouteMaker {

    private GameMap gameMap;

    public RouteMaker(GameMap gameMap) {
        this.gameMap = gameMap;
    }


    //TODO split in separate methods
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

        double[][] weights = new double[routingAreaPoints.size()][routingAreaPoints.size()];
        for (int y = 0; y < routingAreaPoints.size(); y++) {
            for (int x = 0; x < routingAreaPoints.size(); x++) {
                weights[y][x] = routingPredicate.getMovementPrice(routingAreaPoints.get(x), routingAreaPoints.get(y));
//                System.out.println(routingAreaPoints.get(x).getCoordinates().toString() + " " + routingAreaPoints.get(y).getCoordinates().toString() + " " + weights[y][x]);
            }
        }

        HashMap<Point, Double> distanceMap = new HashMap<>();
        Point marker = sourcePoint;
        int processed = 0;
        for (Point point : routingAreaPoints) {
            distanceMap.put(point, -1.0);
        }

        while (processed < distanceMap.size()) {
            int markerIndex = routingAreaPoints.indexOf(marker);
            for (int i = 0; i < routingAreaPoints.size(); i++) {
                double rawDistance = weights[i][markerIndex];
                if (rawDistance > 0) {
                    double distance = rawDistance + distanceMap.get(marker);
                    Point processingPoint = routingAreaPoints.get(i);
                    double processingPointValue = distanceMap.get(processingPoint);
                    if (processingPointValue > distance || processingPointValue == -1) {

                    }
                }
            }

            processed++;
        }


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
