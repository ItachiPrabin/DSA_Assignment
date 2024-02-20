package Question_5_b;

import java.util.*;

public class NetworkDeviceImpactFinder {

    // Function to find the devices impacted by a power outage on a target device
    public static List<Integer> findImpactedDevices(int[][] networkConnections, int targetDevice) {
        // Create a graph to represent network connections
        Map<Integer, List<Integer>> graph = new HashMap<>();
        // Map to store the in-degree of each device
        Map<Integer, Integer> inDegree = new HashMap<>();

        // Build the graph and calculate in-degree of each node
        for (int[] connection : networkConnections) {
            int from = connection[0];
            int to = connection[1];
            // Add connection to the graph
            graph.putIfAbsent(from, new ArrayList<>());
            graph.get(from).add(to);
            // Increment in-degree of the destination device
            inDegree.put(to, inDegree.getOrDefault(to, 0) + 1);
        }

        // Perform Depth-First Search (DFS) starting from the target device
        List<Integer> result = new ArrayList<>();
        dfs(graph, inDegree, targetDevice, targetDevice, result);

        return result;
    }

    // Depth-First Search (DFS) to traverse the network
    private static void dfs(Map<Integer, List<Integer>> graph, Map<Integer, Integer> inDegree, int device, int target,
            List<Integer> result) {
        // If the current device has only one incoming connection from the target device
        if (inDegree.getOrDefault(device, 0) == 1 && graph.get(target).contains(device)) {
            // Adding the device to the list of impacted devices
            result.add(device);
            // Recursively adding child devices
            addChildren(graph, device, result);
        }

        // Recursively exploring the children of the current device
        if (graph.containsKey(device)) {
            for (int child : graph.get(device)) {
                dfs(graph, inDegree, child, target, result);
            }
        }
    }

    // Function to add child devices recursively
    private static void addChildren(Map<Integer, List<Integer>> graph, int device, List<Integer> result) {
        if (graph.containsKey(device)) {
            for (int child : graph.get(device)) {
                result.add(child);
                // Recursively adding children of children
                addChildren(graph, child, result);
            }
        }
    }

    // Main method to test the functionality
    public static void main(String[] args) {
        // Example network connections
        int[][] networkConnections = { { 0, 1 }, { 0, 2 }, { 1, 3 }, { 1, 6 }, { 2, 4 }, { 4, 6 }, { 4, 5 }, { 5, 7 } };
        // Target device experiencing power outage
        int targetDevice = 4;

        // Finding impacted devices
        List<Integer> impactedDevices = findImpactedDevices(networkConnections, targetDevice);

        // Printing the list of impacted devices
        System.out.println("Impacted devices due to power outage on device " + targetDevice + ":");
        for (int device : impactedDevices) {
            System.out.println(device);
        }
    }
}
