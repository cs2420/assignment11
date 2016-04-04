package Assignment11;

import static org.junit.Assert.*;

import org.junit.Test;

public class PriorityQueueTest {

	@Test
	public void test() {
		PriorityQueue testy = new PriorityQueue();
		testy.add(12);
		testy.add(14);
		testy.add(6);
		testy.add(8);
		testy.add(9);
		testy.add(10);
		testy.add(5);
		testy.add(4);
		testy.add(3);
		testy.add(-7);
		testy.add(500);
		testy.generateDotFile("test");
		
	}

}
