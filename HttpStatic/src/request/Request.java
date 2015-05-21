package request;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Request {
	
	private Socket socket;

	private String header;
	
	private String relativeUrl;
	
	private String host;
	
	private String userAgent;
	
	private DataOutputStream writer;
	
	private List<String> headers;
	
	
	public Request(Socket socket){
		setSocket(socket);
		init();
	}
	
	public void init(){
		try {
			String s = "";
			headers = new ArrayList<>();
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			//System.out.println("... reading request ... ");
			while( null != (s = br.readLine())){
				
				if(s.contains("Host")){
					setHost(s.replace("Host: ", ""));
				}
				else if(s.contains("User-Agent")){
					setUserAgent(s);
				}
				else if(s.contains("HTTP")){
					setHeader(s);
				}
				
				headers.add(s);
				if(s.length() == 0)
					break;
			}
			/*
			System.out.println("HEADERS : ");
			for(String str : headers){
				System.out.println("\t" + str);
			}
			*/
			//System.out.println("test header");
			
			if(getHeader() != null){
				String [] tab = getHeader().split(" ");
				//System.out.println("test header ok");
				
				setRelativeUrl(tab[1]);
			}
			
			setWriter(new DataOutputStream(socket.getOutputStream()));
						
		} catch(Exception e){
			System.out.println(e);
		}
		
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getRelativeUrl() {
		return relativeUrl;
	}

	public void setRelativeUrl(String relativeUrl) {
		this.relativeUrl = relativeUrl;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public DataOutputStream getWriter() {
		return writer;
	}

	public void setWriter(DataOutputStream writer) {
		this.writer = writer;
	}

	public List<String> getHeaders() {
		return headers;
	}

	public void setHeaders(List<String> headers) {
		this.headers = headers;
	}

}
