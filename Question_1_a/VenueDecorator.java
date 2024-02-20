
package Question_1_a;

public class VenueDecorator {

    // finding minimum cost of decorating venues given an array of decoration costs

    public static int FindMinCost(int[][] decorationCosts) {
        // If the decorationCosts array is empty or null, it returns 0
        if (decorationCosts == null || decorationCosts.length == 0 || decorationCosts[0].length == 0) {
            return 0;
        }

        // Getting the number of venues and themes from the array dimensions
        int numOfVenues = decorationCosts.length;
        int numOfThemes = decorationCosts[0].length;

        // Creating a 2D array to store the minimum costs for each venue and theme
        int[][] dp = new int[numOfVenues][numOfThemes];

        // Initializing minimum costs for first venue based on decoration costs provided
        for (int theme = 0; theme < numOfThemes; theme++) {
            dp[0][theme] = decorationCosts[0][theme];
        }

        // Iterating over each venue starting from the second one
        for (int venue = 1; venue < numOfVenues; venue++) {
            // For each venue, iterating over each theme
            for (int currentTheme = 0; currentTheme < numOfThemes; currentTheme++) {
                // Initializing minimum cost for current venue and theme to max possible value
                dp[venue][currentTheme] = Integer.MAX_VALUE;
                // Iterating over each previous theme
                for (int prevTheme = 0; prevTheme < numOfThemes; prevTheme++) {
                    // If the current theme is different from the previous one,
                    // updating the minimum cost for the current venue and theme
                    if (currentTheme != prevTheme) {
                        dp[venue][currentTheme] = Math.min(dp[venue][currentTheme],
                                dp[venue - 1][prevTheme] + decorationCosts[venue][currentTheme]);
                    }
                }
            }
        }

        // Finding the minimum cost among the themes for the last venue
        int minCost = Integer.MAX_VALUE;
        for (int themeCost : dp[numOfVenues - 1]) {
            minCost = Math.min(minCost, themeCost);
        }

        // Returning the minimum cost
        return minCost;
    }

    // The main method is testing the functionality of the FindMinCost method with
    // an example decoration costs array
    public static void main(String[] args) {
        // Example decoration costs array
        int[][] costs = { { 1, 3, 2 }, { 4, 6, 8 }, { 3, 1, 5 } };
        // Finding and printing the minimum cost
        int result = FindMinCost(costs);
        System.out.println(result);
    }
}
