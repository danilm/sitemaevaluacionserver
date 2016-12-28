package com.ucavila.sisevaulacion.servidor;



import com.ucavila.sisevaulacion.comm.ServidorSocket;
import com.ucavila.sisevaulacion.model.Tienda;


public class Servidor {

	private static final int PORT=2244;
	private static final String PASSWORD="1qazxsw2";
	private static Tienda tienda = new Tienda();
	private static Integer indice=1;
	
	public static void main(String[] args) {
		System.out.println("Iniciando servidor...");
		ServidorSocket server = new ServidorSocket();
		server.escuchar(PORT);
		
	}

	public static Tienda getTienda() {
		return tienda;
	}

	public static void setTienda(Tienda tienda) {
		Servidor.tienda = tienda;
	}

	public static Integer getIndice() {
		return indice;
	}

	public static void setIndice(Integer indice) {
		Servidor.indice = indice;
	}
	
	
	
	

}
