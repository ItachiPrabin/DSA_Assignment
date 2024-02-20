package Question_4_b;

import java.util.ArrayList;
import java.util.List;

// Defining a class for balanced binary tree nodes
class BalancedBinaryTreeNode {
    int value;
    BalancedBinaryTreeNode left, right; // References to left and right child nodes

    // Constructor to initialize the node with a given value
    public BalancedBinaryTreeNode(int value) {
        this.value = value;
        this.left = this.right = null; // Initialize left and right child nodes as null
    }
}

// Class for finding closest K values in a balanced binary tree
public class ClosestValueFinder {
    // Method to find the closest K values to a given target in the binary tree
    public static List<Integer> closestKValues(BalancedBinaryTreeNode root, double target, int k) {
        List<Integer> result = new ArrayList<>(); // List to store the closest K values
        inorderTraversal(root, target, k, result); // Perform inorder traversal to find closest values
        return result; // Return the list of closest values
    }

    // Helper method for inorder traversal to find closest K values
    private static void inorderTraversal(BalancedBinaryTreeNode root, double target, int k, List<Integer> result) {
        if (root == null) {
            return; // Base case: If the node is null, return
        }

        inorderTraversal(root.left, target, k, result); // Traverse left subtree

        if (result.size() < k) { // If the result list is not full

            result.add(root.value); // Add current node's value to result
        } else {
            // If the result list is full, check if the current node is closer to the target
            if (Math.abs(target - root.value) < Math.abs(target - result.get(0))) {
                result.remove(0); // Remove the least closest value

                result.add(root.value); // Add current node's value to result
            } else {
                return; // No need to traverse further right if current node is not closer
            }
        }

        inorderTraversal(root.right, target, k, result); // Traverse right subtree
    }

    public static void main(String[] args) {
        // Create a balanced binary tree with sample values
        BalancedBinaryTreeNode root = new BalancedBinaryTreeNode(4);
        root.left = new BalancedBinaryTreeNode(2);
        root.right = new BalancedBinaryTreeNode(5);
        root.left.left = new BalancedBinaryTreeNode(1);
        root.left.right = new BalancedBinaryTreeNode(3);

        double target = 3.8; // Target value for finding closest values
        int k = 2; // Number of closest values to find

        // Find closest K values to the target in the binary tree
        List<Integer> closestValues = closestKValues(root, target, k);
        System.out.println(closestValues); // Output: [4, 5]
    }
}
