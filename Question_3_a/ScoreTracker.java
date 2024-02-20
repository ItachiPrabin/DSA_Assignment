package Question_3_a;

import java.util.Collections;
import java.util.PriorityQueue;

public class ScoreTracker {
    // Priority queues to store the scores: maxHeap for smaller scores and minHeap
    // for larger scores
    private PriorityQueue<Double> minHeap; // Storing the larger half of the scores
    private PriorityQueue<Double> maxHeap; // Storing the smaller half of the scores

    // Initializing the score tracker
    public ScoreTracker() {
        // Initializing the priority queues: minHeap for larger scores, maxHeap for
        // smaller scores (reversed order)
        minHeap = new PriorityQueue<>();
        maxHeap = new PriorityQueue<>(Collections.reverseOrder());
    }

    // Adding a new assignment score to the data stream
    public void addScore(double score) {
        // If the maxHeap is empty or the score is less than or equal to the largest
        // score in maxHeap,
        // adding it to maxHeap. Otherwise, adding it to minHeap.
        if (maxHeap.isEmpty() || score <= maxHeap.peek()) {
            maxHeap.offer(score); // Adding to maxHeap
        } else {
            minHeap.offer(score); // Adding to minHeap
        }

        // Balancing the heaps to ensure the size difference is at most 1
        if (maxHeap.size() > minHeap.size() + 1) {
            minHeap.offer(maxHeap.poll()); // Moving the largest score from maxHeap to minHeap
        } else if (minHeap.size() > maxHeap.size()) {
            maxHeap.offer(minHeap.poll()); // Moving the smallest score from minHeap to maxHeap
        }
    }

    // Calculating and returning the median of all the assignment scores in the
    // data stream
    public double getMedianScore() {
        // If maxHeap is empty, there are no scores available
        if (maxHeap.isEmpty()) {
            throw new IllegalStateException("No scores available");
        }

        // If the sizes of maxHeap and minHeap are equal, calculating the median by
        // taking
        // the average of the two middle scores
        if (maxHeap.size() == minHeap.size()) {
            return (maxHeap.peek() + minHeap.peek()) / 2.0; // Calculating the median
        } else {
            return maxHeap.peek(); // If maxHeap has more scores, the median is the largest score in maxHeap
        }
    }

    // Main method to test the ScoreTracker class
    public static void main(String[] args) {
        ScoreTracker scoreTracker = new ScoreTracker(); // Creating a new ScoreTracker object
        // Adding some scores to the score tracker
        scoreTracker.addScore(85.5);
        scoreTracker.addScore(92.3);
        scoreTracker.addScore(77.8);
        scoreTracker.addScore(90.1);
        double median1 = scoreTracker.getMedianScore(); // Calculating the median of the scores
        System.out.println("Median 1: " + median1); // Printing the first median

        // Adding more scores to the score tracker
        scoreTracker.addScore(81.2);
        scoreTracker.addScore(88.7);
        double median2 = scoreTracker.getMedianScore(); // Calculating the median of the updated scores
        System.out.println("Median 2: " + median2); // Printing the second median
    }
}
