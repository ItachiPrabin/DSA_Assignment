package Question_2_b;

import java.util.*;

public class SecretSharingProcess {
    public static void main(String[] args) {

        int n = 5; // Total number of individuals
        int[][] intervals = { { 0, 2 }, { 1, 3 }, { 2, 4 } }; // Intervals during which the secret can be shared
        int firstPerson = 0; // ID of the first person who possesses the secret

        // Calling the method to find individuals who eventually know the secret
        List<Integer> result = findSecrets(n, intervals, firstPerson);

        // Printing the result
        System.out.println(result);
    }

    // Method to find individuals who eventually know the secret
    public static List<Integer> findSecrets(int n, int[][] intervals, int firstPerson) {
        // Set to keep track of individuals who know the secret
        Set<Integer> secretsKnown = new HashSet<>();
        secretsKnown.add(firstPerson); // Initially, the first person possesses the secret

        // Iterating through each interval
        for (int[] interval : intervals) {
            int start = interval[0];
            int end = interval[1];

            // Set to keep track of individuals who receive the secret during the current
            // interval
            Set<Integer> newShares = new HashSet<>();
            // Checking if any person in the interval already knows the secret
            for (int i = start; i <= end; i++) {
                if (secretsKnown.contains(i)) {
                    // If any person in the interval already knows the secret, share it with
                    // everyone in the interval
                    for (int j = start; j <= end; j++) {
                        newShares.add(j);
                    }
                    break; // No need to continue checking for this interval
                }
            }
            secretsKnown.addAll(newShares); // Update the set of individuals who know the secret
        }

        return new ArrayList<>(secretsKnown); // Convert the set to a list and return
    }
}
