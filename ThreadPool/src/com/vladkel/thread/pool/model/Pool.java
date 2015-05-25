package com.vladkel.thread.pool.model;

import com.vladkel.thread.pool.interfaces.IPool;

public class Pool implements IPool {

	private static final String DEFAUL_NAME = "worker_";

	private Object monitor;

	public Pool(int workersNumber) {

		monitor = new Object();

		for (int i = 0; i < workersNumber; i++) {
			workers.add(new Worker(this, DEFAUL_NAME + i));
			workers.get(i).start();

		}
	}

	@Override
	public void addJob(Runnable job) {
		synchronized (monitor) {
			this.circularlist.add(new Node(job));
			monitor.notify();
		}

	}

	@Override
	public Runnable nextJob() {

		synchronized (monitor) {

			try {
				if (circularlist.getSize() == 0) {
					monitor.wait();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				return null;
			}

			return this.circularlist.remove(0).getJob();
		}
	}

}
