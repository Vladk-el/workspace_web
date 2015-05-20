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
			if(pool.circularlist.getSize() > 0){
				System.out.println(this.name + " start job");
				pool.nextJob().run();
				System.out.println(this.name + " has finished job");
			} else {
				System.out.println(name + " is " + this.getState());
				try {
					this.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	

}
