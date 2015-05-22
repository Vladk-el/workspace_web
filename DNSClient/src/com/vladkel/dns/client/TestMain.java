package com.vladkel.dns.client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class TestMain {

	public static void main(String[] args) {
		
		try
		{
			QueryDNS query = new QueryDNS ("google.fr", DNS.TYPE_ANY, DNS.CLASS_IN);
			DatagramSocket socket = new DatagramSocket();
			socket.setSoTimeout (5000);
			
            byte[] data = query.extractQuery();
            
            System.out.println("Length : " + data.length);
    	    DatagramPacket packet = new DatagramPacket(data, data.length, InetAddress.getByName("8.8.8.8"), 53);
    	    socket.send (packet);
    	    
    	    System.out.println("END");
		}
		catch(Exception E)
		{
			
		}
	}
}