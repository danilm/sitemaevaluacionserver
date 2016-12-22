package com.ucavila.sisevaulacion.servidor;

import java.util.ArrayList;
import java.util.Date;

import com.ucavila.sisevaulacion.comm.ServidorSocket;
import com.ucavila.sisevaulacion.model.Tienda;
import com.ucavila.sisevaulacion.model.Vendedor;

public class Servidor {

	private static final int PORT=2222;
	private static final String PASSWORD="1qazxsw2";
	
	public static void main(String[] args) {
		ServidorSocket server = new ServidorSocket();
		server.escuchar(PORT);
		System.out.println("Servidor iniciado en modo escucha");
		
		//TODO: Abrir un hilo para que de forma ciclica vaya escuchando peticiones 
		//del cliente (ya sean para enviar las excepciones o enviarlas)
		
		Tienda tienda = crearTienda();
		server.enviarTienda(PASSWORD, tienda);
		System.out.println("Se ha detectado una peticion de datos");

	}
	
	private static Tienda crearTienda(){
		Tienda tienda = new Tienda("Mi tienda Servidor");
		ArrayList<Vendedor> listaVendedores = new ArrayList<Vendedor>();
		Vendedor vendedor = new Vendedor();
		vendedor.setApellidos("LOPEZ MESA");
		vendedor.setNombre("DANIEL");
		vendedor.setTotal(1000.0);
		vendedor.setFecha(new Date());
		
		listaVendedores.add(vendedor);
		
		tienda.setListaVendedores(listaVendedores);
		tienda.setNombreTienda("Mi tienda Servidor");
		
		return tienda;
	}

}
