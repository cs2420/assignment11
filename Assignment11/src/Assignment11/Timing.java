package Assignment11;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

/**
 * A timing tool to test the speed of DoublyLinkedList.
 * 
 * @author Doug Garding and Connor Ottenbacher
 */
public class Timing {

	private static final int ITER_COUNT = 10000;

	public static void main(String[] args) {
		Random rand = new Random();
		rand.setSeed(System.currentTimeMillis());

		// You spin me round baby, right round
		long startTime = System.nanoTime();
		while (System.nanoTime() - startTime < 1000000000)
			;

		// Used as the exponent to calculate the size of the set (Size N).
		for (int exp = 10; exp <= 25; exp++) {

			int size = (int) Math.pow(2, exp); // or ..
			size = 1 << exp;
			// The 2 statements are equivalent, see bit-shifting for more info.

			// Do the experiment multiple times, and average out the results
			long totalTime = 0;

			// SET UP - ArrayList
			ArrayList<Integer> arrayList = new ArrayList<Integer>();
			for (int i = 0; i < size; i++) {
				arrayList.add(i);
			}
			Collections.shuffle(arrayList);

			PriorityQueue<Integer> queue = new PriorityQueue<Integer>();
			for (int i = 0; i < size; i++) {
				queue.add(arrayList.get(i));
			}

			for (int iter = 0; iter < ITER_COUNT; iter++) {
				Integer toAdd = rand.nextInt(queue.size());
				// START TIMER
				long start = System.nanoTime();

				
				queue.findMin();

				// STOP TIMER
				long stop = System.nanoTime();
				totalTime += stop - start ;
				


			}
			double averageTime = totalTime / (double) ITER_COUNT;
			System.out.println(size + "\t" + averageTime); 
		}
	}
}