package load.balancing.server.strategy.round.robin;

import java.util.List;

import load.balancing.server.strategy.Strategy;

public class StrategyRoundRobin extends Strategy<Robin> {
	
	public StrategyRoundRobin(){
		super();
	}
	
	public StrategyRoundRobin(List<Robin> list){
		this.list = list;
		it = list.iterator();
	}
	
	@Override
	public int getTheGoodOne(){
		if(!it.hasNext()){
			it = list.iterator();
		}
		
		Robin robin = (Robin) it.next();
		
		return robin.call();
	}

}
