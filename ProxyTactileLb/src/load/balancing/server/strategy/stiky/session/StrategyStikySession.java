package load.balancing.server.strategy.stiky.session;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import load.balancing.server.strategy.Strategy;

public class StrategyStikySession extends Strategy<StikySession> {
	
	private StikySession current;
		
	public StrategyStikySession() {
		super();
		setList(new ArrayList<StikySession>());
	}

	@Override
	public int getTheGoodOne() {

		for(StikySession ss : list){
			if(ss.getIp().equalsIgnoreCase(current.getIp()) && 
					ss.getUserAgent().equalsIgnoreCase(current.getUserAgent())){
				current.setServer(ss.getServer());
				System.out.println("client already exist");
				return ss.getServer();
			}
		}
		
		List<Integer> servers = new ArrayList<>();
		
		for(Integer number : workers){
			if(number != current.getServer()){
				servers.add(number);
				System.out.println("\t" + number);
			}
		}
		
		int number = 0;
		if(servers.size() > 1){
			Random randomGenerator = new Random();
			number = randomGenerator.nextInt(servers.size());
		}
		
		//System.out.println("number : " + number);
		current.setServer(servers.get(number));
		list.add(current);
		
		return current.getServer();
	}

	public StikySession getCurrent() {
		return current;
	}

	public void setCurrent(StikySession current) {
		this.current = current;
	}
	
	@Override
	public void removeWorker(Integer worker){
		super.removeWorker(worker);
		
		List<StikySession> toRemove = new ArrayList<StikySession>();
		for(StikySession ss : list){
			if(ss.getServer() == worker){
				toRemove.add(ss);
			}
		}
		
		for(StikySession ss : toRemove){
			list.remove(ss);
		}
	}

}
