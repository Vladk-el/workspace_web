package com.vladkel.thread.pool.model;

public class Worker extends Thread {
	
	private String name;
	
	private Pool pool;
		
	public Worker(Pool pool, String name) {
		this.pool = pool;
		this.name = name;
	}

	@Override
	public void run() {
		while(true){
			System.out.println(name + " start job");
			pool.nextJob().run();
			System.out.println(name + " finish job");
		}
	}

	

}
