package Asta_Application2;

import java.util.*;

public class Test {

	public static void main(String[] args) {
		
		givenUsingTimer_whenSchedulingTaskOnce_thenCorrect();

	}
	
	public static void givenUsingTimer_whenSchedulingTaskOnce_thenCorrect() {
	    TimerTask task = new TimerTask() {
	        public void run() {
	            System.out.println("Task performed on: " + new Date() + "n" +
	              "Thread's name: " + Thread.currentThread().getName());
	        }
	    };
	    Timer timer = new Timer("Timer");
	    
	    long delay = 3000;
	    
	    timer.schedule(task, delay);
	    System.out.println("ciao");
	    
	}

}
