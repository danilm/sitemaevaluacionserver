package com.ucavila.sisevaulacion.comm;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;

import com.ucavila.sisevaulacion.dao.ServidorDAO;
import com.ucavila.sisevaulacion.model.Tienda;
import com.ucavila.sisevaulacion.model.Vendedor;
import com.ucavila.sisevaulacion.servidor.Servidor;

public class ThreadServidor extends Thread{
	private Socket socket;
	private static final String PASSWORD="1qazxsw2";
	
	ThreadServidor (Socket socket){
		this.socket = socket;
	}
	
	public void run (){
		try{
			System.out.println("Usando Socket " + this.socket.getPort());
			//Obtiene el flujo de salida asociado al socket:
			
			ObjectOutputStream pw = new ObjectOutputStream(this.socket.getOutputStream());
			//Obtiene el flujo de entrada asociado al socket:
			ObjectInputStream br = new ObjectInputStream(this.socket.getInputStream());
			
			String recibido = (String)br.readObject();
			
			if (recibido.equals("ClienteSiS")){
				pw.writeObject("ServidorSiS-passw");
				recibido = (String)br.readObject();
				
				if (recibido.equals(PASSWORD)){
					pw.writeObject("OK");
					
					pw.writeObject(obtenerTienda());
				} else {
					//Lo que viene no es correcto
					System.out.println("Error, password incorrecto " + recibido);
					
				}
			} if (recibido.equals("ClienteSiS-excepcion")){
				pw.writeObject("ServidorSiS-enviarExcepcion");
				
				Vendedor vendedorEx = (Vendedor)br.readObject();
				insertarVendedor(vendedorEx);
				
				//Por último, guardamos en BBDD la IP y la fecha
				guardarDatosConexion(vendedorEx.getIp());
				
			}else {
				//Lo que viene no es correcto
				System.out.println("Error, lo recibido no se corresponde con los datos esperados de un cliente válido" + recibido);
				
			}
			//System.out.println("Peticion recibida por el servidor:" + br.readObject());
			
			br.close();
			pw.close();
			this.socket.close();
		} catch (IOException | ClassNotFoundException  e){
			System.out.println("Se ha producido un error en la conexion " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void guardarDatosConexion(String ip) {
		ServidorDAO servidorDAO = new ServidorDAO();
		try {
			servidorDAO.insertarIP(ip);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}

	/**
	 * Metodo para obtener la tienda
	 * @return
	 */
	private Tienda obtenerTienda(){
		return Servidor.getTienda();
	}
	
	/**
	 * Añadimos un vendedor que ha generado la excepcion a nuestra tienda
	 * @param vendedorEx
	 */
	private void insertarVendedor(Vendedor vendedorEx){
		Tienda tienda = Servidor.getTienda();
		tienda.getListaVendedores().put(vendedorEx.getApellidos(), vendedorEx);
	}
	
}
