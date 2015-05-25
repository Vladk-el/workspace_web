package server;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Server {

	public static void run() throws IOException {
		
		Properties prop = new Properties();
		InputStream input = null;
		
		input = new FileInputStream("config.ini");
		prop.load(input);
		
		System.out.println("Properties : ");
		for(Object key : prop.keySet()){
			System.out.println("\t" + key + " => " + prop.getProperty((String) key));
		}
		
		
		ServerSocket server = new ServerSocket(80);
		Socket client = null;
		Integer c;
		System.out.println("Server started");
		while (true) {
			try {
				client = server.accept();
				
				/*
				System.out.println("Port : " + client.getLocalPort());
				System.out.println("InetAddres : " + client.getInetAddress());
				System.out.println("RemoteSocketAddress : " + client.getRemoteSocketAddress());
				*/
				
				String s = "";
				List<String> headers = new ArrayList();
				BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
				
				System.out.println("... reading request ... ");
				while( null != (s = br.readLine())){
					
					headers.add(s);
					if(s.length() == 0)
						break;
				}
				
				/*
				System.out.println("Headers : ");
				for(String str : headers){
					System.out.println("\t" + str);
				}
				*/
				
				/**
				 * Traitement de la réponse
				 */
				
				String [] tab = headers.get(0).split(" ");
				
				/*
				System.out.println("Headers.get(0) : ");
				for(String str : tab){
					System.out.println("\t" + str);
				}
				*/
				
				String host_method = tab[0];
				String host_path = tab[1];
				String host_protocol = tab[2];
				String host_url = headers.get(1).replace("Host: ", "");
				
				System.out.println("Method : " + host_method);
				System.out.println("Path : " + host_path);
				System.out.println("Protocol : " + host_protocol);
				System.out.println("Url : " + host_url);
				
				
				//System.out.println("prop.getProperty(host_url) ======> " +  prop.getProperty(host_url));
				File file = null;
				
				try{
					file = new File(prop.getProperty(host_url) + host_path);
				}catch(Exception e){
					System.out.println(e);
				}
				
				if(file.exists()){
					
					System.out.println("file existing : " + file.getName());
					System.out.println("file is a " + (file.isDirectory() ? "directory" : "file"));
					
					if(file.isDirectory()){
						File[] files = file.listFiles();
						
						System.out.println("\tsize : " + files.length);
						
						String present = "<h1>Index of " + file.getName() + "</h1>";
						client.getOutputStream().write(present.getBytes());

						/**
						 * Ajouter les liens pour les fichiers
						 */
						
						for(File f : files){
							System.out.println("\t\t" + f.getName());
							String p = "<p><a href='" + host_path + (host_path.endsWith("/") ? "" : "/") + f.getName() + "'>" + f.getName() + "</a></p>";
							client.getOutputStream().write(p.getBytes());
						}
						
					}
					
					if(file.isFile()){
						//BufferedWriter bos = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
						/**
						 * read file
						 */
						/*
						FileInputStream in = new FileInputStream(file);
						byte[] buffer = new byte[4096];
						int length;
						while ((length = in.read(buffer)) > 0){
						    client.getOutputStream().write(buffer, 0, length);
						}
						in.close();
						client.getOutputStream().flush();
						*/
						
						/**
						 * download file
						 */
												
						DataOutputStream dos = new DataOutputStream(client.getOutputStream());
						dos.writeBytes("HTTP/1.1 200 OK\r\n");
						dos.writeBytes("Content-Type: octet/stream\r\n\r\n");
						FileInputStream in = new FileInputStream(file);
						byte[] buffer = new byte[4096];
						int length;
						while ((length = in.read(buffer)) > 0){
						    dos.write(buffer);
						}
						in.close();
						dos.flush();
						
					}
				}
				
				
				
				
				//System.out.println("... writing response ...");
				//client.getOutputStream().write("Hello World\n".getBytes());
				//System.out.println("... writing response OK ...");
				
				//client.getOutputStream().close();
				client.close();
				
				System.out.println("\n\n");
			}catch(Exception e){
				System.out.println(e);
			}
		}
	}

}
