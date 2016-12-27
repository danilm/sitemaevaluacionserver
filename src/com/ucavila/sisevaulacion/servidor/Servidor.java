package com.ucavila.sisevaulacion.servidor;



import com.ucavila.sisevaulacion.comm.ServidorSocket;


public class Servidor {

	private static final int PORT=2244;
	private static final String PASSWORD="1qazxsw2";
	
	public static void main(String[] args) {
		System.out.println("Iniciando servidor...");
		ServidorSocket server = new ServidorSocket();
		server.escuchar(PORT);
		
	}
	
	

}
