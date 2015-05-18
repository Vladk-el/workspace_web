package load.balancing.server;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;


public class Proxy {
	
	private Map<Integer, Map<String, String>> workers;
		
	private Map<Integer, Map<String, String>> loadBalancers;

	public Proxy() {
		init();
		run();
	}
	
	public void init(){
		
		BufferedReader br = null;
		workers = new HashMap<Integer, Map<String,String>>();
		loadBalancers = new HashMap<Integer, Map<String,String>>();
		
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream("config.ini")));
			
			String s = "";
			
			while(null != (s = br.readLine())){
				String key = s.split("=")[0];
				Integer id = Integer.parseInt(key.split("\\.")[1]);
				String value = s.split("=")[1];
				
				if(s.startsWith("worker")){
					if(workers.get(id) == null)
						workers.put(id, new HashMap<>());
					
					if(key.endsWith("ip")){
						workers.get(id).put("ip", value);
					}
					else if(key.endsWith("port")){
						workers.get(id).put("port", value);
					}
					
				}
				else if(s.startsWith("lb")){
					if(loadBalancers.get(id) == null)
						loadBalancers.put(id, new HashMap<>());
					
					if(key.endsWith("workers")){
						loadBalancers.get(id).put("workers", value);
					}
					else if(key.endsWith("strategy")){
						loadBalancers.get(id).put("strategy", value);
					}
					
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		System.out.println("workers : ");
		for(Integer key : workers.keySet()){
			System.out.println("\t" + key + 
					" => " + workers.get(key).get("ip") + 
					" => " + workers.get(key).get("port")
					);
		}
		
		System.out.println("loadBalancers : ");
		for(Integer key : loadBalancers.keySet()){
			System.out.println("\t" + key + 
					" => " + loadBalancers.get(key).get("workers") + 
					" => " + loadBalancers.get(key).get("strategy")
					);
		}
		
	}
	
	public void run(){
		ServerSocket server = null;
		Socket client = null;
		//InetAddress addr = null;
		
		try {
			//addr = InetAddress.getByName(workers.get(0).get("ip"));
			//server = new ServerSocket(Integer.parseInt(workers.get(0).get("port")), 50, addr);
			server = new ServerSocket(80);
			System.out.println("server online, waiting for connexions...");
			
			while(true){
				try {
					client = server.accept();
					System.out.println("client " + client + " connected");
					balance(client, 1);
				} catch(Exception e){
					System.out.println(e);
				} finally {
					System.out.println("client " + client + " disconnected");
					client.close();
				}
			}
		} catch(Exception e){
			System.out.println(e);
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
	
	public void balance(Socket client, Integer server){
		Socket socket = null;
		
		try {
			socket = new Socket(workers.get(server).get("ip"), Integer.parseInt(workers.get(server).get("port")));
			BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			String s = "";
			while(null != (s = br.readLine())){
				System.out.println(s);
				bw.write(s + System.getProperty("line.separator"));
				if(s.length() == 0)
					break;
			}
			bw.flush();
			
			System.out.println("writing ok");
			
			BufferedReader rbr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			BufferedWriter rbw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
			
			s = "";
			while(null != (s = rbr.readLine())){
				System.out.println("s : ***" + s + "***");
				rbw.write(s + System.getProperty("line.separator"));
				if(s.contains(System.getProperty("line.separator"))){
					if(s.length() == 0){
						break;
					}
				}
			}
			rbr.close();
			rbw.flush();
						
			System.out.println("reading ok");
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}

























