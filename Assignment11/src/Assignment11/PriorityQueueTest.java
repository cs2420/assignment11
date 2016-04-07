package Assignment11;

import static org.junit.Assert.*;

import org.junit.Test;

public class PriorityQueueTest {

	
	@Test
	public void Add() {
		PriorityQueue<Integer> test = new PriorityQueue<Integer>();
		test.add(5);
		test.add(1);
		test.add(4);
		test.add(2);
		test.add(3);
		test.add(0);
		Object[] arr = test.toArray();
		assertEquals(6, arr.length);
		test.generateDotFile("test");
		assertEquals(true, testOrder(arr));
		
	}
	
	@Test
	public void addFull(){
		PriorityQueue<Integer> test = new PriorityQueue<Integer>();
		for(int i = 0; i<10; i++){
			test.add(i);
		}
		test.add(50);
		assertTrue(testOrder(test.toArray()));
		test.add(-3);
		assertTrue(testOrder(test.toArray()));
		test.generateDotFile("add");
	}
	
	@Test
	public void deleteMin(){
		PriorityQueue<Integer> test = new PriorityQueue<Integer>();
		test.add(5);
		assertEquals(1, test.size());
		assertEquals((Integer)5, (Integer)test.findMin());
		assertEquals((Integer)5, (Integer)test.deleteMin());
		assertEquals(0, test.size());
		test.add(5);
		test.add(8);
		test.add(1);
		assertEquals(3, test.size());
		assertEquals((Integer)1, (Integer)test.findMin());
		assertEquals((Integer)1, (Integer)test.deleteMin());
		test.add(7);
		test.add(-5);
		assertEquals(4, test.size());
		assertEquals((Integer)(-5), (Integer)test.findMin());
		assertEquals((Integer)(-5), test.deleteMin());
		assertEquals(3, test.size());
		assertTrue(testOrder(test.toArray()));
		test.generateDotFile("test2");
	}
	
	private boolean testOrder(Object [] a){
		for(int i = 0; i<a.length; i++){
			int left = (i * 2) + 1;
			int right = (i * 2) + 2;
			if(left >=a.length || right >=a.length){
				return true;
			}
			if((Integer)a[i] > (Integer)a[left] || (Integer)a[i] > (Integer)a[right]){
				return false;
			}
		}
		return true;
	}

}
