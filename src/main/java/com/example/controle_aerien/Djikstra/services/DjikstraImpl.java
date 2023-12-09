package com.example.controle_aerien.Djikstra.services;

import org.springframework.stereotype.Service;
import com.example.controle_aerien.Djikstra.classes.Edge;
import com.example.controle_aerien.Djikstra.classes.Graph;
import com.example.controle_aerien.Djikstra.classes.Node;
import com.example.controle_aerien.entities.Aeroport;

import java.util.*;

@Service
public class DjikstraImpl {

    public static Map<Long, Double> findShortestPaths(Graph graph, Long start, Long destination) {
        Map<Long, Double> distances = new HashMap<>();
        Map<Long, Long> previousNodes = new HashMap<>();
        PriorityQueue<Node> minHeap = new PriorityQueue<>(Comparator.comparingDouble(Node::getDistance));

        distances.put(start, 0.0);
        minHeap.add(new Node(start, 0.0));

        while (!minHeap.isEmpty()) {
            Node currentNode = minHeap.poll();
            Long currentVertex = currentNode.getId();
            double currentDistance = currentNode.getDistance();

            if (distances.get(currentVertex) < currentDistance) {
                continue;
            }

            for (Edge neighborEdge : graph.getNeighbors(currentVertex)) {
                Long neighbor = neighborEdge.getAeroportArriverId();
                double newDistance = currentDistance + neighborEdge.getWeight();

                if (newDistance < distances.getOrDefault(neighbor, Double.MAX_VALUE)) {
                    distances.put(neighbor, newDistance);
                    previousNodes.put(neighbor, currentVertex); // Keep track of the previous node for constructing the path
                    minHeap.add(new Node(neighbor, newDistance));
                }
            }
        }

        // If you need to find the shortest path to a specific destination, you can construct the path here
        if (destination != null) {
            List<Long> path = constructPath(start, destination, previousNodes);
            System.out.println("Shortest path from " + start + " to " + destination + ": " + path);
        }

        return distances;
    }

    private static List<Long> constructPath(Long start, Long destination, Map<Long, Long> previousNodes) {
        List<Long> path = new ArrayList<>();
        Long current = destination;

        while (current != null) {
            path.add(current);
            current = previousNodes.get(current);
        }

        Collections.reverse(path);
        return path;
    }
}

