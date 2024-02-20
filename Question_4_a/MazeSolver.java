package Question_4_a;

import java.util.*;

public class MazeSolver {

    // Define a static nested class to represent a state in the maze
    static class State {
        int x, y; // Coordinates of the state
        String keys; // Keys collected at this state

        // Constructor to initialize the state with coordinates and keys
        State(int x, int y, String keys) {
            this.x = x;
            this.y = y;
            this.keys = keys;
        }
    }

    // Method to find the shortest path in the maze
    public static int shortestPath(char[][] grid) {
        int m = grid.length; // Number of rows in the grid
        int n = grid[0].length; // Number of columns in the grid
        Set<Character> keys = new HashSet<>(); // Set to store keys found in the maze
        Map<Character, int[]> doors = new HashMap<>(); // Map to store locations of doors
        int start_x = -1, start_y = -1; // Initialize start position coordinates

        // Iterate through the grid to find keys, doors, and start position
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char cell = grid[i][j];
                if (cell == 'S') {
                    start_x = i;
                    start_y = j;
                } else if ('a' <= cell && cell <= 'z') {
                    keys.add(cell);
                } else if ('A' <= cell && cell <= 'Z') {
                    doors.put(cell, new int[] { i, j });
                }
            }
        }

        // Convert set of keys to a list for easier manipulation
        List<Character> keysList = new ArrayList<>(keys);
        int[] minDistance = { Integer.MAX_VALUE }; // Array to store minimum distance
        // Call DFS to find the shortest path recursively
        dfs(grid, start_x, start_y, keysList, doors, new boolean[m][n], "", 0, minDistance);

        // Return the shortest path distance if found, otherwise return -1
        return minDistance[0] == Integer.MAX_VALUE ? -1 : minDistance[0];
    }

    // Depth-first search (DFS) algorithm to explore possible paths in the maze
    private static void dfs(char[][] grid, int x, int y, List<Character> keys, Map<Character, int[]> doors,
            boolean[][] visited, String collectedKeys, int distance, int[] minDistance) {
        // If the current distance exceeds the minimum distance found so far, return
        if (distance >= minDistance[0])
            return;

        // Mark the current position as visited
        visited[x][y] = true;

        // Explore each direction (up, down, left, right) from the current position
        for (int[] dir : new int[][] { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } }) {
            int nx = x + dir[0]; // New x-coordinate after moving in the current direction
            int ny = y + dir[1]; // New y-coordinate after moving in the current direction

            // Check if the new position is within the bounds of the grid and not visited
            if (nx >= 0 && nx < grid.length && ny >= 0 && ny < grid[0].length && !visited[nx][ny]) {
                char cell = grid[nx][ny]; // Get the cell value at the new position

                // If the cell is a passage or the start position, continue exploring
                if (cell == 'P' || cell == 'S') {
                    dfs(grid, nx, ny, keys, doors, visited, collectedKeys, distance + 1, minDistance);
                }
                // If the cell contains a key
                else if ('a' <= cell && cell <= 'z') {
                    String newCollectedKeys = collectedKeys + cell; // Collect the key
                    // If all keys have been collected, update the minimum distance
                    if (newCollectedKeys.length() == keys.size()) {
                        minDistance[0] = Math.min(minDistance[0], distance + 1);
                    } else {
                        // Continue exploring with the new collected keys
                        dfs(grid, nx, ny, keys, doors, visited, newCollectedKeys, distance + 1, minDistance);
                    }
                }
                // If the cell contains a locked door
                else if ('A' <= cell && cell <= 'Z') {
                    char key = Character.toLowerCase(cell); // Corresponding key to unlock the door
                    // If the required key has been collected, continue exploring
                    if (collectedKeys.indexOf(key) != -1) {
                        dfs(grid, nx, ny, keys, doors, visited, collectedKeys, distance + 1, minDistance);
                    }
                }
            }
        }

        // Mark the current position as unvisited before backtracking
        visited[x][y] = false;
    }

    // Main method to test the MazeSolver class
    public static void main(String[] args) {
        // Sample maze represented as a 2D character array
        char[][] grid = {
                { 'S', 'P', 'q', 'P', 'P' },
                { 'W', 'W', 'W', 'P', 'W' },
                { 'r', 'P', 'Q', 'P', 'R' }
        };
        // Find and print the shortest path in the maze
        System.out.println(shortestPath(grid)); // Output: 8
    }
}
