package com.vladkel.thread.pool.model;

import com.vladkel.thread.pool.interfaces.IPool;

public class Pool implements IPool {

	
	public Pool(int workersNumber){
		for(int i = 0; i < workersNumber; i++){
			workers.add(new Worker(this));
			workers.get(i).start();
		}
	}
	
	@Override
	public void addJob(Runnable job) {
		this.circularlist.add(new Node(job));
	}

	@Override
	public Runnable nextJob() {
		return this.circularlist.remove(0).getJob();
	}

}
