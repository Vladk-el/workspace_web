package com.vladkel.thread.pool.model;

import com.vladkel.thread.pool.interfaces.IPool;

public class Pool implements IPool {

	private static final String DEFAUL_NAME = "worker_";
	
	public Pool(int workersNumber){
		for(int i = 0; i < workersNumber; i++){
			workers.add(new Worker(this, DEFAUL_NAME + i));
			workers.get(i).start();
		}
	}
	
	@Override
	public void addJob(Runnable job) {
		this.circularlist.add(new Node(job));
	}

	@Override
	public synchronized Runnable nextJob() {
		return this.circularlist.remove(0).getJob();
	}

}
