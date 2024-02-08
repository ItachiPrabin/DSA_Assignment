package Question_1_a;

public class VenueDecorator {

    public static int findMinimumCost(int[][] decorationCosts) {
        if (decorationCosts == null || decorationCosts.length == 0 || decorationCosts[0].length == 0) {
            return 0;
        }

        int numOfVenues = decorationCosts.length;
        int numOfThemes = decorationCosts[0].length;

        int[][] dp = new int[numOfVenues][numOfThemes];

        for (int theme = 0; theme < numOfThemes; theme++) {
            dp[0][theme] = decorationCosts[0][theme];
        }

        for (int venue = 1; venue < numOfVenues; venue++) {
            for (int currentTheme = 0; currentTheme < numOfThemes; currentTheme++) {
                dp[venue][currentTheme] = Integer.MAX_VALUE;
                for (int prevTheme = 0; prevTheme < numOfThemes; prevTheme++) {
                    if (currentTheme != prevTheme) {
                        dp[venue][currentTheme] = Math.min(dp[venue][currentTheme],
                                dp[venue - 1][prevTheme] + decorationCosts[venue][currentTheme]);
                    }
                }
            }
        }

        int minCost = Integer.MAX_VALUE;
        for (int themeCost : dp[numOfVenues - 1]) {
            minCost = Math.min(minCost, themeCost);
        }

        return minCost;
    }

    public static void main(String[] args) {
        int[][] costs = { { 1, 3, 2 }, { 4, 6, 8 }, { 3, 1, 5 } };
        int result = findMinimumCost(costs);
        System.out.println(result); // Expected output: 7
    }
}
