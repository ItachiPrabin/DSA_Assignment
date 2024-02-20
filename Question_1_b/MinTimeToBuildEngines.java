
package Question_1_b;

import java.util.Arrays;

public class MinTimeToBuildEngines {

  // Method to calculate the minimum time required to build all engines
  public static int MinimumTimeToBuildEngines(int[] EngineList, int SplitCost) {
    // Creating an array to store the minimum time to build 'i' engines
    int[] dp = new int[EngineList.length + 1];

    // Filling array with the maximum possible integer value to represent infinity
    Arrays.fill(dp, Integer.MAX_VALUE);

    // Setting the minimum time to build 0 engines as 0
    dp[0] = 0;

    // Iterating through each engine
    for (int i = 1; i <= EngineList.length; i++) {
      // Calculating the time required to build the 'i'th engine without splitting
      dp[i] = EngineList[i - 1] + SplitCost;

      // Iterating through each possible split point
      for (int j = 1; j < i; j++) {
        // Updating the minimum time to build 'i' engines considering splitting at point
        // 'j'
        dp[i] = Math.min(dp[i], dp[j] + dp[i - j]);
      }
    }
    // Returning the final time required to build all engines
    return dp[EngineList.length];
  }

  public static void main(String[] args) {
    // Sample array representing the time required to build each engine
    int[] engines = { 1, 2, 3 };
    // Calling the method to calculate the minimum time to build all engines
    System.out.println("Minimum time to build all engines: " + MinimumTimeToBuildEngines(engines, 1));
  }
}
