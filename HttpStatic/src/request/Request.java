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
	
	private DataOutputStream writer;
	
	
	public Request(Socket socket){
		setSocket(socket);
		init();
	}
	
	public void init(){
		try {
			String s = "";
			List<String> headers = new ArrayList<>();
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			//System.out.println("... reading request ... ");
			while( null != (s = br.readLine())){
				
				headers.add(s);
				if(s.length() == 0)
					break;
			}
			
			String [] tab = headers.get(0).split(" ");
			
			setHeader(headers.get(0));
			setRelativeUrl(tab[1]);
			setHost(headers.get(1).replace("Host: ", ""));
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

	public DataOutputStream getWriter() {
		return writer;
	}

	public void setWriter(DataOutputStream writer) {
		this.writer = writer;
	}

}
