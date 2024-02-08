package Question_1_b;

import java.util.PriorityQueue;

// MinHeap class to represent a min-heap data structure
class MinHeap {
  private PriorityQueue<Integer> heap;

  // Constructor to initialize the priority queue
  public MinHeap() {
    this.heap = new PriorityQueue<>();
  }

  // Add a value to the heap and maintain the heap property
  public void push(int value) {
    heap.offer(value);
    heapifyUp();
  }

  // Remove and return the smallest element from the heap
  public int pop() {
    if (heap.isEmpty()) {
      return -1; // or throw an exception
    }
    int popped = heap.poll();
    heapifyDown();
    return popped;
  }

  // Move the element up the tree to maintain the heap property
  private void heapifyUp() {
    int currentIndex = heap.size() - 1;
    while (currentIndex > 0) {
      int parentIndex = (currentIndex - 1) / 2;
      if (heap.peek() < heap.toArray(new Integer[0])[parentIndex]) {
        swap(currentIndex, parentIndex);
        currentIndex = parentIndex;
      } else {
        break;
      }
    }
  }

  // Move the element down the tree to maintain the heap property
  private void heapifyDown() {
    int currentIndex = 0;
    while (true) {
      int leftChildIndex = 2 * currentIndex + 1;
      int rightChildIndex = 2 * currentIndex + 2;
      int smallestChildIndex = -1;

      if (leftChildIndex < heap.size() && heap.peek() > heap.toArray(new Integer[0])[leftChildIndex]) {
        smallestChildIndex = leftChildIndex;
      }

      if (rightChildIndex < heap.size()
          && (smallestChildIndex == -1
              || heap.toArray(new Integer[0])[rightChildIndex] < heap.toArray(new Integer[0])[smallestChildIndex])) {
        smallestChildIndex = rightChildIndex;
      }

      if (smallestChildIndex != -1) {
        swap(currentIndex, smallestChildIndex);
        currentIndex = smallestChildIndex;
      } else {
        break;
      }
    }
  }

  // Return the size of the heap
  public int size() {
    return heap.size();
  }

  // Helper method to swap two elements in the heap
  private void swap(int i, int j) {
    Integer[] array = heap.toArray(new Integer[0]);
    int temp = array[i];
    array[i] = array[j];
    array[j] = temp;

    heap.clear();
    for (int value : array) {
      heap.offer(value);
    }
  }
}

// Main class for calculating the minimum time to build engines
public class MinTimeToBuildEngines {
  // Function to calculate the minimum time to build engines
  public static int minTimeToBuildEngines(int[] engines, int splitCost) {
    if (engines.length == 0) {
      return 0;
    }

    // Create a MinHeap to efficiently manage the building process
    MinHeap heap = new MinHeap();

    // Add each engine's building time to the heap
    for (int engineTime : engines) {
      heap.push(engineTime);
    }

    int totalTime = 0;

    // Continue building until there is only one element left in the heap
    while (heap.size() > 1) {
      // Pop the two smallest building times from the heap
      int firstEngine = heap.pop();
      int secondEngine = heap.pop();

      // Calculate the time for the current step, considering splitting if needed
      int stepTime = Math.min(firstEngine + splitCost, secondEngine);

      // Update the total time and push the new building time back to the heap
      totalTime += stepTime;
      heap.push(stepTime);
    }

    // The remaining element in the heap is the total time needed to build all
    // engines
    return totalTime;
  }

  // Main method for example usage
  public static void main(String[] args) {
    // Example usage
    int[] engines = { 3, 4, 5, 2 };
    int splitCost = 2;
    int result = minTimeToBuildEngines(engines, splitCost);
    System.out.println(result);
  }
}
