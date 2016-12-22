package com.ucavila.sisevaulacion.comm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.ucavila.sisevaulacion.model.Tienda;

public class ServidorSocket {
	private int port;
	private Socket socket;
	private ServerSocket serverSocket;
	
	public void escuchar(int port) {
		this.port = port;
		
		try {
			this.serverSocket = new ServerSocket(port);
			this.socket = serverSocket.accept();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Tienda enviarTienda(String password,Tienda tienda){
		try {
			PrintWriter pw = new PrintWriter(this.socket.getOutputStream(),true);
			BufferedReader br = new BufferedReader (new InputStreamReader (this.socket.getInputStream()));
			String recibido = br.readLine();
			
			if (recibido.equals("ClienteSiS")){
				pw.println("ServidorSiS-passw");
				recibido = br.readLine();
				
				if (recibido.equals(password)){
					pw.println("OK");
					//TODO: Y le mandamos los datos de Tienda
					pw.println(tienda);
				} else {
					//Lo que viene no es correcto
					return null;
				}
			} else {
				//Lo que viene no es correcto
				return null;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tienda;
	}
}
