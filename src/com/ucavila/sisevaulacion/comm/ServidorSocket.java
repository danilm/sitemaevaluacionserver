package com.ucavila.sisevaulacion.comm;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorSocket {
	
	
	public void escuchar(int port) {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
			while (true){
				Socket socket = serverSocket.accept();
				System.out.println("Socket " + socket.getLocalPort() + " creado");
				new ThreadServidor(socket).start();
			}
			
			
		} catch (IOException e) {
			System.out.println("Se ha producido un error en la conexion" + e.getMessage());
			e.printStackTrace();
		
			System.exit(1);
		} 
	}
	
}
