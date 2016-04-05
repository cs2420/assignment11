package Assignment11;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * Represents a priority queue of generically-typed items. The queue is
 * implemented as a min heap. The min heap is implemented implicitly as an
 * array.
 * 
 * @author Connor Ottenbacher
 * @author Doug Garding
 *
 */
@SuppressWarnings("unchecked")
public class PriorityQueue<AnyType> {

	private int currentSize;
	private AnyType[] array;
	private Comparator<? super AnyType> cmp;

	/**
	 * Constructs an empty priority queue. Orders elements according to their
	 * natural ordering (i.e., AnyType is expected to be Comparable) AnyType is
	 * not forced to be Comparable.
	 */
	public PriorityQueue() {
		currentSize = 0;
		cmp = null;
		array = (AnyType[]) new Object[10]; // safe to ignore warning
	}

	/**
	 * Construct an empty priority queue with a specified comparator. Orders
	 * elements according to the input Comparator (i.e., AnyType need not be
	 * Comparable).
	 */
	public PriorityQueue(Comparator<? super AnyType> c) {
		currentSize = 0;
		cmp = c;
		array = (AnyType[]) new Object[10]; // safe to ignore warning
	}

	/**
	 * @return the number of items in this priority queue.
	 */
	public int size() {
		return currentSize;
	}

	/**
	 * Makes this priority queue empty.
	 */
	public void clear() {
		currentSize = 0;
	}

	/**
	 * @return the minimum item in this priority queue.
	 * @throws NoSuchElementException
	 *             if this priority queue is empty.
	 * 
	 *             (Runs in constant time.)
	 */
	public AnyType findMin() throws NoSuchElementException {
		// If the PQ is empty
		if (currentSize < 0) {
			throw new NoSuchElementException();
		}

		// Top item should be smallest, so return it.
		return array[1];
	}

	/**
	 * Removes and returns the minimum item in this priority queue.
	 * 
	 * @throws NoSuchElementException
	 *             if this priority queue is empty.
	 * 
	 *             (Runs in logarithmic time.)
	 */
	public AnyType deleteMin() throws NoSuchElementException {
		// if the heap is empty, throw a NoSuchElementException
		if (currentSize < 1) {
			throw new NoSuchElementException();
		}

		// store the minimum item so that it may be returned at the end
		AnyType minItem = array[1];

		// replace the item at minIndex with the last item in the tree
		array[1] = array[currentSize];
		array[currentSize] = null;

		// update size
		this.currentSize--;

		// percolate the item at minIndex down the tree until heap order is
		// restored
		percolateDown(1);

		// return the minimum item that was stored
		return minItem;
	}

	/**
	 * Private helper method that percolates down. Takes the top item of the
	 * min-heap and percolates it down the tree into its correct spot.
	 */
	private void percolateDown(int parent) {

		AnyType parentItem = array[parent];
		int smallest = parent;
		int rightChild = rightChildIndex(parent);
		int leftChild = leftChildIndex(parent);

		// If left child < parent item, and left < than right
		if (compare(array[leftChild], parentItem) < 0 && compare(array[leftChild], array[rightChild]) < 0) {
			smallest = leftChild;
		}

		// If right child < parent item, and right < than left
		if (compare(array[rightChild], parentItem) < 0 && compare(array[rightChild], array[leftChild]) < 0) {
			smallest = rightChild;
		}

		// If parent isn't the smallest, need to swap and check order again
		if (smallest != parent) {
			swap(parent, smallest);
			percolateDown(smallest);
		}
	}

	/**
	 * Private helper method that swaps two objects within an array.
	 */
	private void swap(int index1, int index2) {
		AnyType firstItem = array[index1];
		array[index1] = array[index2];
		array[index2] = firstItem;
	}

	/**
	 * Private helper method. Returns index of left child.
	 */
	private int leftChildIndex(int originalIndex) {
		return (originalIndex * 2) + 1;
	}

	/**
	 * Private helper method. Returns index of right child.
	 */
	private int rightChildIndex(int originalIndex) {
		return (originalIndex * 2) + 2;
	}

	/**
	 * Adds an item to this priority queue.
	 * 
	 * (Runs in logarithmic time.) Can sometimes terminate early.
	 * 
	 * @param item
	 *            -- the item to be inserted
	 */
	public void add(AnyType item) {
		// backing array too small, so double it.
		if (currentSize == array.length) {
			AnyType[] temp = (AnyType[]) new Object[array.length * 2];
			for (int i = 0; i < array.length; i++) {
				temp[i] = array[i];
			}
			array = temp;
		}
		currentSize++;
		array[currentSize] = item;
		percolateUp(item, currentSize);
	}

	private void percolateUp(AnyType item, int index) {
		int parentIndex = (index - 1) / 2;
		if (index == 1 || compare(item, array[parentIndex]) > 0) {
			return;
		} else {
			AnyType temp = array[parentIndex];
			array[parentIndex] = item;
			array[index] = temp;
			percolateUp(item, parentIndex);
		}
	}

	/**
	 * Generates a DOT file for visualizing the binary heap.
	 */
	public void generateDotFile(String filename) {
		try (PrintWriter out = new PrintWriter(filename)) {
			out.println("digraph Heap {\n\tnode [shape=record]\n");

			for (int i = 0; i < currentSize; i++) {
				out.println("\tnode" + i + " [label = \"<f0> |<f1> " + array[i] + "|<f2> \"]");
				if (((i * 2) + 1) < currentSize)
					out.println("\tnode" + i + ":f0 -> node" + ((i * 2) + 1) + ":f1");
				if (((i * 2) + 2) < currentSize)
					out.println("\tnode" + i + ":f2 -> node" + ((i * 2) + 2) + ":f1");
			}
			out.println("}");
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	/**
	 * Internal method for comparing lhs and rhs using Comparator if provided by
	 * the user at construction time, or Comparable, if no Comparator was
	 * provided.
	 */
	private int compare(AnyType lhs, AnyType rhs) {
		if (cmp == null) {
			// safe to ignore warning
			return ((Comparable<? super AnyType>) lhs).compareTo(rhs);
		}
		// We won't test your code on non-Comparable types if we didn't supply a
		// Comparator

		return cmp.compare(lhs, rhs);
	}

	// LEAVE IN for grading purposes
	public Object[] toArray() {
		Object[] ret = new Object[currentSize];
		for (int i = 0; i < currentSize; i++) {
			ret[i] = array[i];
		}
		return ret;
	}
}