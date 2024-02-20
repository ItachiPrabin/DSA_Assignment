package Question_5_a;

import java.util.Arrays;

// Class representing the Ant Colony Optimization algorithm for solving the Traveling Salesman Problem
class AntColony {
    private int[][] distances; // Matrix representing distances between cities
    private int numAnts; // Number of ants in the colony
    private double[][] pheromoneMatrix; // Matrix representing pheromone levels on edges
    private double[][] probabilities; // Matrix representing probabilities of selecting each city
    private int numCities; // Number of cities
    private int[] bestTour; // Best tour found by the ants
    private int bestTourLength; // Length of the best tour found
    private double evaporationRate; // Rate at which pheromones evaporate
    private double alpha; // Parameter controlling the influence of pheromones
    private double beta; // Parameter controlling the influence of distances

    // Constructor to initialize the AntColony with parameters
    public AntColony(int[][] distances, int numAnts, double evaporationRate, double alpha, double beta) {
        // Assigning parameter values to class variables
        this.distances = distances;
        this.numAnts = numAnts;
        this.evaporationRate = evaporationRate;
        this.alpha = alpha;
        this.beta = beta;
        this.numCities = distances.length;
        // Initializing pheromone and probability matrices
        this.pheromoneMatrix = new double[numCities][numCities];
        this.probabilities = new double[numCities][numCities];
        initializePheromones(); // Initializing pheromone levels
    }

    // Method to initialize pheromone levels on edges
    private void initializePheromones() {
        // Setting initial pheromone level on each edge
        double initialPheromone = 1.0 / numCities;
        for (int i = 0; i < numCities; i++) {
            for (int j = 0; j < numCities; j++) {
                if (i != j) {
                    pheromoneMatrix[i][j] = initialPheromone;
                }
            }
        }
    }

    // Method to solve the Traveling Salesman Problem using Ant Colony Optimization
    public void solve(int maxIterations) {
        bestTourLength = Integer.MAX_VALUE; // Initializing best tour length
        bestTour = new int[numCities]; // Initializing best tour array
        java.util.Random random = new java.util.Random(); // Creating random number generator

        // Performing iterations
        for (int iteration = 0; iteration < maxIterations; iteration++) {
            // Moving ants
            for (int ant = 0; ant < numAnts; ant++) {
                boolean[] visited = new boolean[numCities]; // Array to track visited cities
                int[] tour = new int[numCities]; // Array to store tour for each ant
                int currentCity = random.nextInt(numCities); // Start from a random city
                tour[0] = currentCity; // Record initial city
                visited[currentCity] = true; // Mark initial city as visited

                // Moving to next cities
                for (int i = 1; i < numCities; i++) {
                    calculateProbabilities(currentCity, visited); // Calculating probabilities
                    int nextCity = selectNextCity(currentCity); // Selecting next city based on probabilities
                    tour[i] = nextCity; // Recording next city in tour
                    visited[nextCity] = true; // Marking next city as visited
                    currentCity = nextCity; // Moving to next city
                }

                int tourLength = calculateTourLength(tour); // Calculating tour length
                // Updating best tour if a shorter tour is found
                if (tourLength < bestTourLength) {
                    bestTourLength = tourLength;
                    bestTour = tour.clone(); // Cloning the tour to avoid reference issues
                }
            }

            updatePheromones(); // Updating pheromone levels
        }
    }

    // Method to calculate probabilities of selecting each city
    private void calculateProbabilities(int city, boolean[] visited) {
        double total = 0.0;
        for (int i = 0; i < numCities; i++) {
            if (!visited[i]) {
                probabilities[city][i] = Math.pow(pheromoneMatrix[city][i], alpha) *
                        Math.pow(1.0 / distances[city][i], beta);
                total += probabilities[city][i];
            } else {
                probabilities[city][i] = 0.0;
            }
        }

        // Normalizing probabilities
        for (int i = 0; i < numCities; i++) {
            probabilities[city][i] /= total;
        }
    }

    // Method to select next city based on probabilities
    private int selectNextCity(int city) {
        double[] probabilities = this.probabilities[city];
        double r = Math.random();
        double cumulativeProbability = 0.0;
        for (int i = 0; i < numCities; i++) {
            cumulativeProbability += probabilities[i];
            if (r <= cumulativeProbability) {
                return i;
            }
        }
        return -1; // Return -1 if no city is selected
    }

    // Method to update pheromone levels
    private void updatePheromones() {
        // Evaporating pheromones
        for (int i = 0; i < numCities; i++) {
            for (int j = 0; j < numCities; j++) {
                pheromoneMatrix[i][j] *= (1.0 - evaporationRate);
            }
        }
        // Adding new pheromones based on best tour found
        for (int i = 0; i < numCities - 1; i++) {
            int city1 = bestTour[i];
            int city2 = bestTour[i + 1];
            pheromoneMatrix[city1][city2] += (1.0 / bestTourLength);
            pheromoneMatrix[city2][city1] += (1.0 / bestTourLength);
        }
    }

    // Method to calculate length of a tour
    private int calculateTourLength(int[] tour) {
        int length = 0;
        for (int i = 0; i < numCities - 1; i++) {
            length += distances[tour[i]][tour[i + 1]];
        }
        length += distances[tour[numCities - 1]][tour[0]]; // Returning to the starting city
        return length;
    }

    // Getter method for the length of the best tour
    public int getBestTourLength() {
        return bestTourLength;
    }

    // Getter method for the best tour
    public int[] getBestTour() {
        return bestTour;
    }
}

// Main class for running the Ant Colony Optimization for the Traveling Salesman
// Problem
public class AntColonyTSPSolverApp {
    public static void main(String[] args) {
        // Defining distance matrix between cities
        int[][] distances = {
                { 0, 5, 10, 15 },
                { 10, 0, 35, 25 },
                { 10, 30, 0, 26 },
                { 30, 35, 40, 0 }
        };
        int numAnts = 6; // Number of ants in the colony
        double evaporationRate = 0.5; // Evaporation rate of pheromones
        double alpha = 1.0; // Alpha parameter controlling the influence of pheromones
        double beta = 2.0; // Beta parameter controlling the influence of distances

        // Creating an instance of AntColony and solving the TSP problem
        AntColony solver = new AntColony(distances, numAnts, evaporationRate, alpha, beta);
        solver.solve(700); // Solving TSP with 700 iterations

        // Retrieving the best tour and its length
        int[] bestTour = solver.getBestTour();
        int bestTourLength = solver.getBestTourLength();

        // Printing the best tour and its length
        System.out.println("Best tour: " + Arrays.toString(bestTour));
        System.out.println("Best tour length: " + bestTourLength);
    }
}
