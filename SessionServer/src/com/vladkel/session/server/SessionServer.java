package com.vladkel.session.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.vladkel.session.server.model.request.SessionRequest;
import com.vladkel.session.server.model.session.ISession;
import com.vladkel.session.server.model.session.Session;

public class SessionServer {

	private Map<String, ISession> sessions;
	
	
	public SessionServer(){
		init();
		run();
	}
	
	public void init(){
		sessions = new HashMap<String, ISession>();
	}
	
	public void run(){
		ServerSocket server = null;
		Socket client = null;
		
		try{
			server = new ServerSocket(81);
			System.out.println("server session online, waiting for connexions...");
			
			while(true){
				try {
					client = server.accept();
					System.out.println("client " + client + " connected");
					handle(client);
				} catch(Exception e){
					System.out.println(e);
				} finally {
					System.out.println("client " + client + " disconnected");
					client.close();
				}
			}
		} catch (Exception e) {
			
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
	
	public void handle(Socket socket){
		SessionRequest request = new SessionRequest(socket);
		addSession(request.getKey());
		for(String str : request.getAttributes().keySet()){
			putAttribute(request.getKey(), str, request.getAttributes().get(str));
		}
		
		System.out.println(request);
		
		/**
		 * write ISession in socket
		 */
		
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(
					socket.getOutputStream()));
			
			bw.write("SESSION_KEY=" + request.getKey() + System.getProperty("line.separator"));
			
			for(String str : getSession(request.getKey()).getAttributes().keySet()){
				bw.write("SESSION_PROPERTY=" + str + ":" + getSession(request.getKey()).getAttribute(str) + System.getProperty("line.separator"));
			}
			
			bw.write(("__END__" + System.getProperty("line.separator")));
			
			bw.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		
		
	}
	
	
	
	
	/**
	 * Gestion des sessions
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

	public void setSessions(Map<String, ISession> sessions) {
		this.sessions = sessions;
	}
	
	
}
