package com.vladkel.dns.client;

import java.io.*;
import java.util.*;

public class QueryDNS {
  private String queryHost;
  private int queryType, queryClass, queryID;
  private static int globalID;

  public QueryDNS (String host, int type, int clas) 
  {
	  StringTokenizer labels = new StringTokenizer (host, ".");
	    while (labels.hasMoreTokens ())
	      if (labels.nextToken ().length () > 63)
	        throw new IllegalArgumentException ("Invalid hostname: " + host);
	    queryHost = host;
	    queryType = type;
	    queryClass = clas;
	    synchronized (getClass ()) {
	      queryID = (++ globalID) % 65536;
	    }
  }

  public String getQueryHost () {
    return queryHost;
  }

  public int getQueryType () {
    return queryType;
  }

  public int getQueryClass () {
    return queryClass;
  }

  public int getQueryID () {
    return queryID;
  }

  public byte[] extractQuery ()
  {
    ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream ();
    DataOutputStream dataOut = new DataOutputStream (byteArrayOut);
    try {
      dataOut.writeShort (queryID);
      dataOut.writeShort ((0 << DNS.SHIFT_QUERY) |
              (DNS.OPCODE_QUERY << DNS.SHIFT_OPCODE) |
              (1 << DNS.SHIFT_RECURSE_PLEASE));
      dataOut.writeShort (1); // # queries
      dataOut.writeShort (0); // # answers
      dataOut.writeShort (0); // # authorities
      dataOut.writeShort (0); // # additional
      
      StringTokenizer labels = new StringTokenizer (queryHost, ".");
      while (labels.hasMoreTokens ()) {
        String label = labels.nextToken ();
        System.out.println("Label : " + label);
        dataOut.writeByte (label.length ());
        dataOut.writeBytes (label);
      }
      dataOut.writeByte (0);
      dataOut.writeShort (queryType);
      dataOut.writeShort (queryClass);
    } catch (IOException ignored) {
    }
    return byteArrayOut.toByteArray ();
  }

  private Vector answers = new Vector ();
  private Vector authorities = new Vector ();
  private Vector additional = new Vector ();

  private boolean authoritative, truncated, recursive;
  
  public boolean isAuthoritative () {
    return authoritative;
  }

  public boolean isTruncated () {
    return truncated;
  }

  public boolean isRecursive () {
    return recursive;
  }

  public Enumeration getAnswers () {
    return answers.elements ();
  }

  public Enumeration getAuthorities () {
    return authorities.elements ();
  }

  public Enumeration getAdditional () {
    return additional.elements ();
  }
}