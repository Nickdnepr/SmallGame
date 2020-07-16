package com.nickdnepr.smallgame.core.map.routing;

import com.nickdnepr.smallgame.core.map.Coordinates;
import com.nickdnepr.smallgame.core.map.GameMap;
import com.nickdnepr.smallgame.core.map.Point;
import com.nickdnepr.smallgame.core.utils.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class DijkstraRouteMaker extends AbstractRouteMaker{


    public DijkstraRouteMaker(GameMap gameMap) {
        super(gameMap);
    }

    @Override
    public Route getRoute(Coordinates source, Coordinates destination, RoutingPredicate routingPredicate) {
        Point sourcePoint = gameMap.getPoint(source);
        Point destinationPoint = gameMap.getPoint(destination);
        ArrayList<Point> routingAreaPoints = getRoutingArea(source, destination, routingPredicate);
        System.out.println("Routing area calculated");
        if (!routingAreaPoints.contains(destinationPoint)) {
            return null;
        }
        double[][] distanceMatrix = getDistanceMatrix(routingAreaPoints, routingPredicate);
        System.out.println("Distance matrix calculated");
        HashMap<Point, Pair<Double, Boolean>> distanceMap = getDistanceMap(routingAreaPoints, sourcePoint, distanceMatrix);
        System.out.println("Distance map calculated");
        ArrayList<Point> routeSequence = getPointsSequence(sourcePoint, destinationPoint, distanceMap, distanceMatrix, routingAreaPoints);
        System.out.println("Route sequence calculated");
        System.out.println("Over");
        return null;
    }

    private ArrayList<Point> getRoutingArea(Coordinates source, Coordinates destination, RoutingPredicate routingPredicate) {
        int width = gameMap.getWidth();
        int height = gameMap.getHeight();
        int[][] routingArea = new int[height][width];
        Point sourcePoint = gameMap.getPoint(source);
        Point destinationPoint = gameMap.getPoint(destination);
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

//        System.out.println(routingAreaPoints.size());
        return routingAreaPoints;
    }

    private double[][] getDistanceMatrix(ArrayList<Point> routingAreaPoints, RoutingPredicate routingPredicate) {
        double millis = System.currentTimeMillis();
        double[][] distanceMatrix = new double[routingAreaPoints.size()][routingAreaPoints.size()];
        for (int y = 0; y < routingAreaPoints.size(); y++) {
            for (int x = 0; x < routingAreaPoints.size(); x++) {
                distanceMatrix[y][x] = routingPredicate.getMovementPrice(routingAreaPoints.get(x), routingAreaPoints.get(y));
            }
        }
        System.out.println("Matrix time: " + (System.currentTimeMillis() - millis));
        return distanceMatrix;
    }

//    private double[][] getDistanceMatrix(ArrayList<Point> routingAreaPoints, RoutingPredicate routingPredicate) {
//        double millis = System.currentTimeMillis();
//        double[][] distanceMatrix = new double[routingAreaPoints.size()][routingAreaPoints.size()];
//        int size = routingAreaPoints.size();
//        for (int y = 0; y < size; y++) {
//            distanceMatrix[y][y] = 0;
//            for (int x = y + 1; x < size; x++) {
//                double price = routingPredicate.getMovementPrice(routingAreaPoints.get(x), routingAreaPoints.get(y));
//                distanceMatrix[y][x] = price;
////                distanceMatrix[x][y] = price;
//            }
//        }
//        System.out.println("Matrix time: "+(System.currentTimeMillis() - millis));
//        return distanceMatrix;
//    }

    private HashMap<Point, Pair<Double, Boolean>> getDistanceMap(ArrayList<Point> routingAreaPoints, Point sourcePoint, double[][] distanceMatrix) {

        System.out.println("-- Distance map init");
        HashMap<Point, Pair<Double, Boolean>> distanceMap = new HashMap<>();
        Pair<Point, Double> marker = new Pair<>(sourcePoint, 0.0);
        distanceMap.put(sourcePoint, new Pair<>(0.0, false));
        for (Point point : routingAreaPoints) {
            distanceMap.putIfAbsent(point, new Pair<>(-1.0, false));
        }
        System.out.println("-- Distance map all points added");
//        System.out.println("\n\n\n\n\n");
        int processed = 0;
        double millis = System.currentTimeMillis();
//        while (allProcessed(distanceMap.values())){
        while (processed < distanceMap.size()) {
//            System.out.println("\n\nMarker is " + marker.getKey().getCoordinates().toString());
            Pair<Point, Double> nextMarker = null;
            for (Point point : getProcessingNeighbours(marker.getKey(), routingAreaPoints)) {
//                System.out.println("Point is " + point.getCoordinates().toString());
//                getProcessingNeighbours(point);
                if (point != marker.getKey() && !distanceMap.get(point).getValue()) {
                    double rawDistance = distanceMatrix[routingAreaPoints.indexOf(point)][routingAreaPoints.indexOf(marker.getKey())];
//                    System.out.println("Raw distance is " + rawDistance);
                    if (rawDistance > 0) {

                        double fullDistance = rawDistance + distanceMap.get(marker.getKey()).getKey();
                        double pointDistance = distanceMap.get(point).getKey();
                        if (pointDistance > fullDistance || pointDistance == -1.0) {
//                            System.out.println(marker.toString() + " -> " + point.toString() + " from " + pointDistance + " to " + fullDistance);
//                            System.out.println("Changing " + pointDistance + " to " + fullDistance);
                            distanceMap.get(point).setKey(fullDistance);
                        }
                    }
                }
            }
            distanceMap.get(marker.getKey()).setValue(true);
            processed++;
            for (Point point : distanceMap.keySet()) {
                Pair<Double, Boolean> pair = distanceMap.get(point);
                if (!pair.getValue()) {
                    if (nextMarker == null) {
                        nextMarker = new Pair<>(point, pair.getKey());
                    } else {
                        double distance = pair.getKey();
//                        System.out.println("Marker distance is " + distance);
                        if ((distance < nextMarker.getValue() && distance != -1.0) || nextMarker.getValue() == -1.0) {
                            nextMarker.setKey(point);
                            nextMarker.setValue(distance);
//                            System.out.println("New marker is set to " + nextMarker.getKey().getCoordinates().toString() + " with distance " + nextMarker.getValue());

                        }
                    }
                }
            }
            marker = nextMarker;


//            System.out.println("Processed: " + processed + "/" + distanceMap.size());
//            System.out.println("Point processed: " + (System.currentTimeMillis() - millis));
        }
        return distanceMap;
    }

    private ArrayList<Point> getPointsSequence(Point sourcePoint, Point destinationPoint, HashMap<Point, Pair<Double, Boolean>> distanceMap, double[][] distanceMatrix, ArrayList<Point> routingAreaPoints) {
        ArrayList<Point> finalSequence = new ArrayList<>();
        finalSequence.add(destinationPoint);
        while (!finalSequence.contains(sourcePoint)) {
//            System.out.println("Loooop2");
            Point processingPoint = finalSequence.get(0);
            for (Point nextPoint : distanceMap.keySet()) {
                if (nextPoint != processingPoint) {
                    double rawDistance = distanceMatrix[routingAreaPoints.indexOf(nextPoint)][routingAreaPoints.indexOf(processingPoint)];
                    if (rawDistance != -1.0) {
                        if (Math.abs(distanceMap.get(processingPoint).getKey() - rawDistance - distanceMap.get(nextPoint).getKey()) < 0.001) {
                            finalSequence.add(0, nextPoint);
                            break;
                        }
                    }
                }
            }
        }
//        System.out.println(finalSequence.toString());
        return finalSequence;
    }

    private ArrayList<Point> getProcessingNeighbours(Point point, ArrayList<Point> routingAreaPoints) {
        ArrayList<Point> points = new ArrayList<>();
        ArrayList<Coordinates> coordinates = getNeighbours(point.getCoordinates(), new int[gameMap.getHeight()][gameMap.getWidth()]);
        for (Coordinates cords : coordinates) {
            Point neighbour = gameMap.getPoint(cords);
            if (routingAreaPoints.contains(neighbour)) {
                points.add(neighbour);
            }
        }
        return points;
    }

    private static boolean allProcessed(Collection<Pair<Double, Boolean>> pairs) {
        long startMillis = System.currentTimeMillis();
//        System.out.println("-- allProcessed start");
        for (Pair<Double, Boolean> pair : pairs) {
            if (!pair.getValue()) {
//                System.out.println("-- allProcessed end " + (System.currentTimeMillis() - startMillis));
                return false;
            }
        }
//        System.out.println("-- allProcessed end " + (System.currentTimeMillis() - startMillis));
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
