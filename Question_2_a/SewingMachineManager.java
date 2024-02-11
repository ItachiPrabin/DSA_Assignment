/*
Question 2. a.) 

You are the manager of a clothing manufacturing factory with a production line of super sewing machines. The 
production line consists of n super sewing machines placed in a line. Initially, each sewing machine has a certain 
number of dresses or is empty. 
For each move, you can select any m (1 <= m <= n) consecutive sewing machines on the production line and pass 
one dress from each selected sewing machine to its adjacent sewing machine simultaneously. 
Your goal is to equalize the number of dresses in all the sewing machines on the production line. You need to 
determine the minimum number of moves required to achieve this goal. If it is not possible to equalize the number 
of dresses, return -1. 
Input: [2, 1, 3, 0, 2] 
Output: 5 
Example 1: 
Imagine you have a production line with the following number of dresses in each sewing machine: [2, 1, 3, 0, 2]. 
The production line has 5 sewing machines. 
Here's how the process works: 
1. Initial state: [2, 1, 3, 0, 2] 
2. Move 1: Pass one dress from the second sewing machine to the first sewing machine, resulting in [2, 2, 2, 
0, 2] 
3. Move 2: Pass one dress from the second sewing machine to the first sewing machine, resulting in [3, 1, 2, 
0, 2] 
4. Move 3: Pass one dress from the third sewing machine to the second sewing machine, resulting in [3, 2, 1, 
0, 2] 
5. Move 4: Pass one dress from the third sewing machine to the second sewing machine, resulting in [3, 3, 0, 
0, 2] 
6. Move 5: Pass one dress from the fourth sewing machine to the third sewing machine, resulting in [3, 3, 1, 
0, 1] 
After these 5 moves, the number of dresses in each sewing machine is equalized to 1. Therefore, the minimum 
number of moves required to equalize the number of dresses is 5. 
 
*/

// package Question_2_a;

// public class Main {
//     public static void main(String[] args) {
//         int[] input = { 2, 1, 3, 0, 2 };
//         System.out.println(minMoves(input));
//     }

//     public static int minMoves(int[] nums) {
//         int min = Integer.MAX_VALUE;
//         int max = Integer.MIN_VALUE;
//         int total = 0;
//         for (int num : nums) {
//             min = Math.min(min, num);
//             max = Math.max(max, num);
//             total += num;
//         }

//         if (max - min > nums.length) {
//             return -1;
//         }

//         if (max - min == 0) {
//             return 0;
//         }

//         int subarraySize = max - min + 1;
//         int moves = (total + subarraySize - 1) / subarraySize - min;
//         return moves;
//     }
// }

// moves = (total + subarraySize - 1) / subarraySize - min
// = (8 + 4 - 1) / 4 - 0
// = 11 / 4
// = 2

// from here actual 2 a ko here

package Question_2_a;

public class SewingMachineManager {
    public static void main(String[] args) {
        int[] input = { 1, 0, 5 };
        System.out.println(minMoves(input));
    }

    public static int minMoves(int[] sewingMachines) {
        int totalDresses = 0;
        int numberOfMachines = sewingMachines.length;

        // Calculate the total number of dresses
        for (int dresses : sewingMachines) {
            totalDresses += dresses;
        }

        // Calculate the minimum number of dresses among all machines
        int minDresses = Integer.MAX_VALUE;
        for (int dresses : sewingMachines) {
            minDresses = Math.min(minDresses, dresses);
        }

        // Calculate the target number of dresses per machine
        int targetDressesPerMachine = totalDresses / numberOfMachines;

        // Perform moves to equalize the dresses
        int moves = 0;
        for (int i = 0; i < numberOfMachines - 1; i++) {
            int difference = targetDressesPerMachine - sewingMachines[i];
            if (difference > 0) {
                int transfer = Math.min(difference, sewingMachines[i + 1]);
                sewingMachines[i] += transfer;
                sewingMachines[i + 1] -= transfer;
                moves += transfer;
            }
        }

        return moves;
    }
}
