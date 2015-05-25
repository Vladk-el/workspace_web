package com.vladkel.dns.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

import sun.misc.IOUtils;

public class DNSClient {
	
	private String dnsAddr;
	
	private Integer port = 80;
	
	private Integer timeout;
	
	private Socket socket;
	
	
	public DNSClient(){
		super();
		init();
	}
	
	public DNSClient(String dns, Integer port, Integer timeout){
		this.dnsAddr = dns;
		this.port = port;
		this.timeout = timeout;
		init();
	}
	
	private void init(){
		
	}
	
	private void connect(){
		
		socket = null; 
		
		try {
			
			socket = new Socket();
			socket.connect(new InetSocketAddress(dnsAddr, port), timeout);
			
			OutputStream os = socket.getOutputStream();
			
			os.write( "".getBytes() );
			
			
			os.flush();
			
			readResponse();
			
			
			
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			if(socket != null){
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	private void readResponse(){
		
		InputStream is = null;
		
		try {
			is = socket.getInputStream();
			
			String response = convertStreamToString(is);
			
			
		} catch(Exception e) {
			
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
	public String convertStreamToString(InputStream is) {
	    Scanner s = new Scanner(is).useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}

	public String getDnsAddr() {
		return dnsAddr;
	}

	public void setDnsAddr(String dnsAddr) {
		this.dnsAddr = dnsAddr;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}
}
