package load.balancing.server.strategy.round.robin;

public class Robin {
	
	private int i;

	public Robin(int i){
		this.i = i;
	}
	
	public int call(){
		return i;
	}
	
	
}
