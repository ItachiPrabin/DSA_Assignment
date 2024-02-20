
package Question_2_a;

public class SewingMachineManager {

    // Method to calculate the minimum number of moves required to equalize the
    // dresses in sewing machines
    public static int minMovesToEqualizeDresses(int[] sewingMachines) {
        int totalDresses = 0;
        int numMachines = sewingMachines.length;

        // Calculate the total number of dresses in all sewing machines
        for (int dresses : sewingMachines) {
            totalDresses += dresses;
        }

        // Check if the total dresses can be evenly distributed among the machines
        if (totalDresses % numMachines != 0) {
            return -1; // If dresses cannot be evenly distributed, return -1 indicating impossibility
        }

        // Calculate the average number of dresses per machine
        int averageDressesPerMachine = totalDresses / numMachines;

        // Initialize variables to keep track of moves and cumulative difference
        int moves = 0;
        int cumulativeSum = 0;

        // Iterate through each sewing machine
        for (int dresses : sewingMachines) {
            // Calculate the difference between actual dresses and average dresses per
            // machine
            cumulativeSum += dresses - averageDressesPerMachine;
            // Update the maximum moves required
            moves = Math.max(moves, Math.abs(cumulativeSum));
        }

        // Return the minimum number of moves required to equalize dresses
        return moves;
    }

    public static void main(String[] args) {
        // Sample array representing the number of dresses in each sewing machine
        int[] sewingMachines = { 1, 0, 5 };
        // Calculate and print the minimum moves required to equalize dresses
        System.out.println(minMovesToEqualizeDresses(sewingMachines)); // Output: 3
    }
}
