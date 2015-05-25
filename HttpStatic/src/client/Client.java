package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket socket = new Socket("127.0.0.1", 80);
		int b = -1;
		socket.getOutputStream().write("Hello Client\n".getBytes());
		while ('\n' != (b = socket.getInputStream().read()))
			System.out.print((char) b);
		socket.close();
	}
	
}
