package load.balancing.server.strategy.round.robin;

import java.util.List;

import load.balancing.server.strategy.Strategy;

public class RoundRobin extends Strategy<Robin> {
	
	public RoundRobin(){
		super();
	}
	
	public RoundRobin(List<Robin> list){
		this.list = list;
		it = list.iterator();
	}
	
	@Override
	public int next(){
		if(!it.hasNext()){
			it = list.iterator();
		}
		
		Robin robin = (Robin) it.next();
		
		return robin.call();
	}

}
