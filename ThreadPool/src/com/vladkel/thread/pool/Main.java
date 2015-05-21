package com.vladkel.thread.pool;

import java.util.Random;
import java.util.Scanner;

import com.vladkel.thread.pool.model.Pool;
import com.vladkel.thread.pool.model.Worker;

public class Main {

	public static void main(String[] args) {

		int workers = 4;

		Pool pool = new Pool(workers);

		addRunnable(pool);
		addRunnable(pool);
		addRunnable(pool);
		addRunnable(pool);
		addRunnable(pool);
		addRunnable(pool);
		addRunnable(pool);
		addRunnable(pool);
		addRunnable(pool);
		addRunnable(pool);
		addRunnable(pool);
		addRunnable(pool);
		
		
		controlPool(pool);
		
		addDynamicRunnable(pool);
	}
	
	public static void addRunnable(Pool pool){
		pool.addJob(new Runnable() {

			@Override
			public void run() {
				try {
					Random random = new Random();
					Thread.sleep(random.nextInt(5000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void controlPool(Pool pool){
		System.out.println(pool.circularlist.getSize() + " : ");
		pool.circularlist.print();
	}
	
	public static void addDynamicRunnable(Pool pool){
		Scanner sc = new Scanner(System.in);
		
		while(true){
			sc.nextLine();
			addRunnable(pool);
			addRunnable(pool);
			addRunnable(pool);
			addRunnable(pool);
			addRunnable(pool);
			System.out.println("add a job ==> stack size : " + pool.circularlist.getSize());
			controlWorkersState(pool);
		}
	}
	
	public static void controlWorkersState(Pool pool){
		for(Worker worker : pool.workers){
			System.out.println(worker.getName() + " is " + worker.getState());
		}
	}
}
