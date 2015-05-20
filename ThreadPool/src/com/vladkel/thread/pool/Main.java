package com.vladkel.thread.pool;

import com.vladkel.thread.pool.model.Pool;

public class Main {

	public static void main(String[] args) {

		int workers = 5;

		Pool pool = new Pool(workers);

		pool.addJob(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

			}
		});

		System.out.println(pool.circularlist.getSize() + " : ");
		pool.circularlist.print();

		pool.addJob(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

			}
		});

		System.out.println(pool.circularlist.getSize() + " : ");
		pool.circularlist.print();

		pool.addJob(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

			}
		});

		System.out.println(pool.circularlist.getSize() + " : ");
		pool.circularlist.print();
		
		pool.circularlist.remove(0);
		
		System.out.println(pool.circularlist.getSize() + " : ");
		pool.circularlist.print();
		
		pool.circularlist.remove(0);
		
		System.out.println(pool.circularlist.getSize() + " : ");
		pool.circularlist.print();
		
		pool.circularlist.remove(0);
		
		System.out.println(pool.circularlist.getSize() + " : ");
		pool.circularlist.print();
		
		pool.circularlist.remove(0);
	}
}
