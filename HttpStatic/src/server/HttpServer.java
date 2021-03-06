package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import request.Request;
import request.SessionRequest;
import session.ISession;
import session.Session;
import session.SessionKeyGenerator;

public class HttpServer {
	
	private Map<String, Map<String, String>> properties;
	
	private Map<String, String> sessionProperties;
	
	private Map<String, ISession> sessions;

	public HttpServer() {
		init();
		run();
	}
	
	public void init(){
		
		BufferedReader br = null;
		properties = new HashMap<String, Map<String,String>>();
		sessionProperties = new HashMap<String, String>();
		sessions = new HashMap<String, ISession>();
		
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream("config.ini")));
			
			String s = "";
			
			String lastKey = null;
			while(null != (s = br.readLine())){
				String value = s.split("=")[1];
				if(s.startsWith("domain")){
					if(s.contains("name")){
						properties.put(value, new HashMap<>());
						lastKey = value;
					}
					else if(s.contains("document_root")){
						if(properties.get(lastKey) != null){
							properties.get(lastKey).put("document_root", value);
						}
					}
				}
				else if(s.startsWith("session")){
					if(s.contains("session.mode")){
						sessionProperties.put("mode", value);
					}
					else if(s.contains("session.timeout")){
						sessionProperties.put("timeout", value);
					}
					else if(s.contains("session.ip")){
						sessionProperties.put("ip", value);
					}
					else if(s.contains("session.port")){
						sessionProperties.put("port", value);
					}
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/*
		System.out.println("properties : ");
		for(String key : properties.keySet()){
			System.out.println("\t" + key + 
					" => " + properties.get(key).get("document_root")
					);
		}
		*/
	}
	
	public void run(){
		ServerSocket server = null;
		Socket client = null;
		
		try {
			server = new ServerSocket(80);
			System.out.println("server online, waiting for connexions...");
			
			while(true){
				try {
					client = server.accept();
					System.out.println("client " + client + " connected");
					handle(client);
				} catch(Exception e){
					e.printStackTrace();
				} finally {
					System.out.println("client " + client + " disconnected");
					client.close();
				}
			}
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			if(server != null){
				try {
					server.close();
					System.out.println("server offline");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void handle(Socket client){
		Request request = new Request(client);
		manageSession(request);
		execute(request);
	}
	
	// TODO local
	public void manageSession(Request request){
		Socket socket = null;
		SessionRequest sessionRequest = null;
		String key = null;
		
		if(request.getSessionRequest().getKey() != null){
			key = request.getSessionRequest().getKey();
		} else {
			key = SessionKeyGenerator.generateSessionKey();
		}
		
		//String key = request.getUserAgent() + "|" + request.getSocket().getInetAddress().getHostAddress();
		
		if(sessionProperties.get("mode").equalsIgnoreCase("local")) {
			
			sessionRequest = new SessionRequest();
			
			
			// TODO NON TESTé
			
			sessionRequest.setKey(key);
			sessionRequest.getAttributes().put("local1", "1");
			sessionRequest.getAttributes().put("local2", "2");
			sessionRequest.getAttributes().put("local3", "3");
			sessionRequest.getAttributes().put("local4", "4");
			
			
			addSession(sessionRequest.getKey());
			for(String str : sessionRequest.getAttributes().keySet()){
				putAttribute(sessionRequest.getKey(), str, sessionRequest.getAttributes().get(str));
			}
			
			System.out.println("local session added : " + sessionRequest);
			
			
		}
		else if(sessionProperties.get("mode").equalsIgnoreCase("remote")) {
			try {
				socket = new Socket(sessionProperties.get("ip"), Integer.parseInt(sessionProperties.get("port")));
				
				OutputStream bw = socket.getOutputStream();
				
				bw.write(("SESSION_KEY=" + key + System.getProperty("line.separator")).getBytes());
				bw.write(("SESSION_PROPERTY=key1:1" + System.getProperty("line.separator")).getBytes());
				bw.write(("SESSION_PROPERTY=key2:2" + System.getProperty("line.separator")).getBytes());
				bw.write(("SESSION_PROPERTY=key3:3" + System.getProperty("line.separator")).getBytes());
				
				bw.write(("__END__" + System.getProperty("line.separator")).getBytes());

				
				bw.flush();
				
				sessionRequest = new SessionRequest(socket);
				
				System.out.println(sessionRequest); 
				
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if(socket != null){
						socket.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		request.setSessionRequest(sessionRequest);
		
	}
	
	@SuppressWarnings("unused")
	public void execute(Request request){
		File file = null;
		
		try{
			file = new File(properties.get(request.getHost()).get("document_root") + request.getRelativeUrl());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(file.exists()){
			
			try {
				//System.out.println("file existing : " + file.getName());
				//System.out.println("file is a " + (file.isDirectory() ? "directory" : "file"));
				
				if(file.isDirectory()){
					request.getWriter().writeBytes("HTTP/1.1 200 OK\r\n");
					request.getWriter().writeBytes("Content-Type: text/html\r\n");
					request.getWriter().writeBytes("Set-Cookie: " + "SESSION_ID" + "=" + request.getSessionRequest().getKey() + "\r\n\r\n");
					
					File[] files = file.listFiles();
					//System.out.println("\tsize : " + files.length);
					String present = "<h1>Index of " + file.getName() + "</h1>";
					request.getWriter().write(present.getBytes());

					for(File f : files){
						//System.out.println("\t\t" + f.getName());
						String p = "<p><a href='" + request.getRelativeUrl() + (request.getRelativeUrl().endsWith("/") ? "" : "/") + f.getName() + "'>" + f.getName() + "</a></p>";
						request.getWriter().write(p.getBytes());
					}
					
				}
				
				if(file.isFile()){
					request.getWriter().writeBytes("HTTP/1.1 200 OK\r\n");
					request.getWriter().writeBytes("Content-Type: octet/stream\r\n\r\n");
					request.getWriter().writeBytes("Set-Cookie: " + "SESSION_ID" + "=" + request.getSessionRequest().getKey() + "\r\n\r\n");
					
					FileInputStream in = new FileInputStream(file);
					byte[] buffer = new byte[4096];
					int length;
					while ((length = in.read(buffer)) > 0){
						request.getWriter().write(buffer);
					}
					in.close();
					request.getWriter().flush();
					
				}
			} catch(Exception e){
				e.printStackTrace();
			}
		} 
		else {
			System.out.println("ressource inexistante");
			try {
				request.getWriter().writeBytes("ressource inexistante");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * For local sessions only
	 */
	
	public void addSession(String key){		
		if(sessions.get(key) == null){
			sessions.put(key, new Session());
		}
	}
	
	public void putAttribute(String key, String attribute, Object value){
		if(sessions.get(key) != null){
			sessions.get(key).setAttribute(attribute, value);
		}
	}
	
	public ISession getSession(String key){
		return (sessions.get(key) != null) ? sessions.get(key) : null;
	}
	
	

	public Map<String, ISession> getSessions() {
		return sessions;
	}
	
	
}
