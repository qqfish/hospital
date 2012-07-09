/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author fish
 */
public class Server {

    private static final int SERVER_PORT = 10000;
    private ThreadManager TM;
    ServerSocket providerSocket;
    String message;

    public Server() {
	TM = new ThreadManager();
    }

    void run() throws IOException {
	try {
	    providerSocket = new ServerSocket(SERVER_PORT);
	    while (true) {
		System.out.println("Waiting for connection");
		Socket connection = providerSocket.accept();
		System.out.println("Connection received from " + connection.getInetAddress().getHostName());
		TM.addThread(connection);
	    }
	} catch (IOException ioException) {
	    ioException.printStackTrace();
	} finally {
	    providerSocket.close();
	}
    }

    public static void main(String[] args) throws IOException {
	Server s = new Server();
	s.run();
    }
}
