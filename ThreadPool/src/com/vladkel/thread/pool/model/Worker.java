package com.vladkel.thread.pool.model;

public class Worker extends Thread {
	
	private Pool pool;
	
	private Runnable runnable;
	
	public Worker(Pool pool) {
		this.pool = pool;
	}

	@Override
	public void run() {
		while(true){
			pool.nextJob().run();
		}
	}

	

}
