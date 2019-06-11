package edu.handong.csee.merge;

import java.util.LinkedList;

public class queue {
	private LinkedList<String> list = new LinkedList<String>();
	   
	public void enqueue(String item) {
	      list.addLast(item);
	   }
	   public String dequeue() {
	      return list.poll();
	   }
	   public boolean hasItems() {
	      return !list.isEmpty();
	   }
	   public int size() {
	      return list.size();
	   }
	   public void addItems(queue q) {
	      while (q.hasItems()) 
	    	  list.addLast(q.dequeue());
	   }
}
