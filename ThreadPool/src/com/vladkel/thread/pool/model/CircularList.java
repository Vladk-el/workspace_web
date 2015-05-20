package com.vladkel.thread.pool.model;

public class CircularList{

	private Node first;
	
	private Node last;
	
	private Integer size;
	
	public CircularList(){
		first = null;
		last = null;
		size = 0;
	}
	
	public void add(Node node){
		if(size == 0){
			first = node;
			first.setNext(node);
			last = first;
			size ++;
		} else {
			node.setNext(last.getNext());
			last.setNext(node);
			last = node;
			size++;
		}
	}
	
	public Node remove(int id){
		if(id >= size){
			return null; // TODO Exception
		}
		
		Node current = last;
		for(int i = 0;  i < id; i++){
			current = current.getNext();
		}
		
		if(size == 1){
			current = first;
			first = null;
		}
		else {
			if(current == last){
				this.first = this.first.getNext();
				this.last.setNext(first);
			} else {
				current.setNext(current.getNext().getNext());
			}
		}
		
		size--;
		return current;
		
	}
	
	public void print(){
		if(size > 0){
			System.out.println(this.first.getJob());
			Node current = this.first.getNext();
			while(current != this.first){
				System.out.println(current.getJob());
				current = current.getNext();
			}
		} else {
			System.out.println("List is empty !");
		}
	}

	public Node getFirst() {
		return first;
	}

	public void setFirst(Node first) {
		this.first = first;
	}

	public Node getLast() {
		return last;
	}

	public void setLast(Node last) {
		this.last = last;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}
}
