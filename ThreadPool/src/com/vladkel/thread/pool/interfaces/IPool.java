package com.vladkel.thread.pool.interfaces;

import java.util.ArrayList;
import java.util.List;

import com.vladkel.thread.pool.model.CircularList;
import com.vladkel.thread.pool.model.Worker;


public interface IPool {
		
	List<Worker> workers = new ArrayList<Worker>();
	
	CircularList circularlist = new CircularList();

	void addJob(Runnable job);
	
	Runnable nextJob();
	
}
