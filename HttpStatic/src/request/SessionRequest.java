package request;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class SessionRequest {
	
	private Socket socket;
	
	private String key;
		
	private Map<String, String> attributes;
	
	
	public SessionRequest(){
		attributes = new HashMap<String, String>();
	}
	
	
	public SessionRequest(Socket socket){
		setSocket(socket);
		init();
	}
	
	public void init(){
		try {
			String s = "";
			attributes = new HashMap<String, String>();
						
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						
			System.out.println("... reading request ... ");
			while( null != (s = br.readLine())){
								
				if(s.contains("SESSION_KEY=")){
					setKey(s.replace("SESSION_KEY=", ""));
				}
				else if(s.contains("SESSION_PROPERTY=")){
					String [] tab = s.replace("SESSION_PROPERTY=", "").split(":");
					attributes.put(tab[0], tab[1]);
				}
				
				if(s.contains("__END__")){
					break;
				}
			}
				
			System.out.println("... end reading request ... ");
		} catch(Exception e){
			e.printStackTrace();
		}
		
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		
		sb.append("SessionRequest : \n");
		sb.append("\t" + key);
		sb.append("\n");
		
		for(String str : attributes.keySet()){
			sb.append("\t\t" + str);
			sb.append(" ==> " + attributes.get(str));
			sb.append("\n");
		}
		
		return sb.toString();
	}

}
