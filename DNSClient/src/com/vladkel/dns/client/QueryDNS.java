package com.vladkel.dns.client;

import java.io.*;
import java.util.*;

public class QueryDNS {
	
	private String queryHost;
	
	private int queryType, 
				queryClass, 
				queryID;
	
	private static int id;
	
	private boolean authoritative, 
					truncated, 
					recursive;
	
	
	public QueryDNS(){
		super();
	}

	public QueryDNS(String host, int type, int clazz) {
		StringTokenizer labels = new StringTokenizer(host, ".");
		while (labels.hasMoreTokens())
			if (labels.nextToken().length() > 63)
				throw new IllegalArgumentException("Invalid hostname: " + host);
		queryHost = host;
		queryType = type;
		queryClass = clazz;
		synchronized (getClass()) {
			queryID = (++id) % 65536;
		}
	}
	
	
	public byte[] extractQuery() {
		ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
		DataOutputStream dataOut = new DataOutputStream(byteArrayOut);
		try {
			
			// ID
			dataOut.writeShort(queryID);
			
			System.out.println("(0 << DNS.SHIFT_QUERY)" + DNS.SHIFT_QUERY + " : " + (0 << DNS.SHIFT_QUERY));
			
			System.out.println("(DNS.OPCODE_QUERY << DNS.SHIFT_OPCODE)" + DNS.OPCODE_QUERY + " ET " + DNS.SHIFT_OPCODE + " : " + (DNS.OPCODE_QUERY << DNS.SHIFT_OPCODE));
			
			System.out.println("(1 << DNS.SHIFT_RECURSE_PLEASE)" + DNS.SHIFT_RECURSE_PLEASE + " : " + (1 << DNS.SHIFT_RECURSE_PLEASE));
			
			
			// Flags
			dataOut.writeShort((0 << DNS.SHIFT_QUERY) // 0 décallé de 15 car REQUETE ==> [0]000 0000 0000 0000
					| (DNS.OPCODE_QUERY << DNS.SHIFT_OPCODE) // 0 décallé de 11 == > 0000 [0]000 0000 0000
					| (1 << DNS.SHIFT_RECURSE_PLEASE)); // 1 décallé de 8 ==> 0000 000[1] 0000 0000
			
			/**
			 * ==> [0]000 0000 0000 0000 | 0000 [0]000 0000 0000 | 0000 000[1] 0000 0000 ==> 0000 0001 0000 0000 (256)
			 */
			
			// Questions number
			dataOut.writeShort(1);
			
			// Responses number
			dataOut.writeShort(0);
			
			// Authority RR number
			dataOut.writeShort(0);
			
			// Additionnal RR number
			dataOut.writeShort(0);

			StringTokenizer labels = new StringTokenizer(queryHost, ".");
			while (labels.hasMoreTokens()) {
				String label = labels.nextToken();
				System.out.println("Label : " + label);
				dataOut.writeByte(label.length());
				dataOut.writeBytes(label);
			}
			
			dataOut.writeByte(0);
			dataOut.writeShort(queryType);
			dataOut.writeShort(queryClass);
		} catch (IOException ignored) {
		}
		return byteArrayOut.toByteArray();
	}
	

	public String getQueryHost() {
		return queryHost;
	}

	public int getQueryType() {
		return queryType;
	}

	public int getQueryClass() {
		return queryClass;
	}

	public int getQueryID() {
		return queryID;
	}

	public boolean isAuthoritative() {
		return authoritative;
	}

	public boolean isTruncated() {
		return truncated;
	}

	public boolean isRecursive() {
		return recursive;
	}
	
}