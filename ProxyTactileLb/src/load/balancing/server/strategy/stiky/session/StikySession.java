package load.balancing.server.strategy.stiky.session;

public class StikySession {
	
	private String userAgent;
	
	private String ip;
	
	private Integer server;
	
	
	public StikySession(){
		super();
	}
	
	public StikySession(String userAgent, String ip, Integer server){
		this.userAgent = userAgent;
		this.ip = ip;
		this.server = server;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getServer() {
		return server;
	}

	public void setServer(Integer server) {
		this.server = server;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(userAgent);
		sb.append("|");
		sb.append(ip);
		sb.append(" ==> ");
		sb.append(server);
		
		return sb.toString();
	}

}
