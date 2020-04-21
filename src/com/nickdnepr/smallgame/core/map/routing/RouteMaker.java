package com.nickdnepr.smallgame.core.map.routing;

import com.nickdnepr.smallgame.core.map.Coordinates;
import com.nickdnepr.smallgame.core.map.GameMap;
import com.nickdnepr.smallgame.core.map.Point;
import com.nickdnepr.smallgame.core.utils.Pair;

import java.util.ArrayList;
import java.util.Collection;
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

        HashMap<Point, Pair<Double, Boolean>> distanceMap = new HashMap<>();
        Pair<Point, Double> marker = new Pair<>(sourcePoint, 0.0);
        int processed = 0;
        distanceMap.put(sourcePoint, new Pair<>(0.0, false));
        for (Point point : routingAreaPoints) {
            distanceMap.putIfAbsent(point, new Pair<>(-1.0, false));
        }
//        System.out.println(distanceMap.toString());
        while (!allProcessed(distanceMap.values())) {
            Pair<Point, Double> nextMarker = null;
            for (Point point : distanceMap.keySet()) {
                if (point != marker.getKey()) {
                    double rawDistance = weights[routingAreaPoints.indexOf(point)][routingAreaPoints.indexOf(marker.getKey())];
                    if (rawDistance > 0) {
                        double fullDistance = rawDistance + distanceMap.get(marker.getKey()).getKey();
                        double pointDistance = distanceMap.get(point).getKey();
                        if (pointDistance > fullDistance || pointDistance == -1.0) {
                            distanceMap.get(point).setKey(fullDistance);
                        }
                    }
                }else {
                    continue;
                }
                if (!distanceMap.get(point).getValue()){
                    if (nextMarker == null) {
                        nextMarker = new Pair<>(point, distanceMap.get(point).getKey());
                    } else {
                        double distance = distanceMap.get(point).getKey();
                        if (distance < nextMarker.getValue() && distance != -1.0) {
                            nextMarker.setKey(point);
                            nextMarker.setValue(distance);
                        }
                    }
                }
            }
            distanceMap.get(marker.getKey()).setValue(true);
            marker = nextMarker;
        }
        for (Point point : distanceMap.keySet()){
            System.out.println(point.getCoordinates().toString()+" "+distanceMap.get(point).toString());
        }

        return null;
    }

    private static boolean allProcessed(Collection<Pair<Double, Boolean>> pairs) {
        for (Pair<Double, Boolean> pair : pairs) {
            if (!pair.getValue()) {
                return false;
            }
        }
        return true;
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
