package load.balancing.server.strategy.stiky.session;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import load.balancing.server.strategy.Strategy;

public class StrategyStikySession extends Strategy<StikySession> {
	
	private StikySession current;
	
	private List<Integer> workers;
	
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

	public List<Integer> getWorkers() {
		return workers;
	}

	public void setWorkers(List<Integer> workers) {
		this.workers = workers;
	}

}
