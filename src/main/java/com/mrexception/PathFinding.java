package com.mrexception;

import java.util.*;

public class PathFinding {

    public static List<MapNode> doReadingDistance(Map2D map, MapNode start, MapNode goal) {
        List<MapNode> route = new LinkedList<>();
        route.add(start);
        MapNode current = start;
        while (!current.equals(goal)) {

        }
        return route;
    }

    public static List<MapNode> doAStar(Map2D map, MapNode start, MapNode goal) {
        Set<MapNode> closed = new HashSet<>();
        Map<MapNode, MapNode> fromMap = new HashMap<>();
        List<MapNode> route = new LinkedList<>();
        Map<MapNode, Integer> gScore = new HashMap<>();
        Map<MapNode, Integer> fScore = new HashMap<>();
        PriorityQueue<MapNode> open = new PriorityQueue<>(11, Comparator.comparingDouble(fScore::get));

        gScore.put(start, 0);
        fScore.put(start, start.manhattanDistance(goal));
        open.offer(start);

        while (!open.isEmpty()) {
            MapNode current = open.poll();
            if (current.equals(goal)) {
                while (current != null) {
                    route.add(0, current);
                    current = fromMap.get(current);
                }

                return route;
            }

            closed.add(current);

            for (MapNode neighbour : map.getNeighbours(current)) {
                if (closed.contains(neighbour)) {
                    continue;
                }

                int tentG = gScore.get(current)
                        + current.getTraversalCost(neighbour);

                boolean contains = open.contains(neighbour);
                if (!contains || tentG < gScore.get(neighbour)) {
                    gScore.put(neighbour, tentG);
                    fScore.put(neighbour, tentG + neighbour.manhattanDistance(goal));

                    if (contains) {
                        open.remove(neighbour);
                    }

                    open.offer(neighbour);
                    fromMap.put(neighbour, current);
                }
            }
        }

        return null;
    }
}
