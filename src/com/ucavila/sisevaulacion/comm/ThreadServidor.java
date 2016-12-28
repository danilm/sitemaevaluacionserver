package com.ucavila.sisevaulacion.comm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;
import java.util.TreeSet;

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
			
			//OLD:BufferedWriter bw  = new BufferedWriter(new OutputStreamWriter(socketOut));
			ObjectOutputStream pw = new ObjectOutputStream(this.socket.getOutputStream());
			//Obtiene el flujo de entrada asociado al socket:
			//OLD:BufferedReader br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
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
	
	/**
	 * Metodo para obtener la tienda
	 * @return
	 */
	private Tienda obtenerTienda(){
		//Tienda tienda = crearTienda();
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
	/**
	 * Método exclusivamente de prueba
	 * @return
	 */
	private static Tienda crearTienda(){
		
		Tienda tienda = Servidor.getTienda();
		Integer indice = Servidor.getIndice();
		
		TreeMap<String,Vendedor> listaVendedores = new TreeMap<String,Vendedor>();
		Vendedor vendedor = new Vendedor();
		vendedor.setApellidos("LOPEZ MESA");
		vendedor.setNombre("DANIEL");
		vendedor.setTotal(1000.0);
		vendedor.setFecha(new Date());
		
		listaVendedores.put(vendedor.getApellidos(),vendedor);
		Servidor.setIndice(indice++);
		
		vendedor = new Vendedor();
		vendedor.setApellidos("ALVAREZ SOR");
		vendedor.setNombre("JAVIER");
		vendedor.setTotal(800.0);
		vendedor.setFecha(new Date());
		
		listaVendedores.put(vendedor.getApellidos(),vendedor);
		Servidor.setIndice(indice++);
		
	    vendedor = new Vendedor();
		vendedor.setApellidos("HIDALGO PRIEGO");
		vendedor.setNombre("ISABEL");
		vendedor.setTotal(900.0);
		vendedor.setFecha(new Date());
		
		listaVendedores.put(vendedor.getApellidos(),vendedor);
		Servidor.setIndice(indice++);
		
		
		
		vendedor = new Vendedor();
		vendedor.setApellidos("BERMUDEZ TRES");
		vendedor.setNombre("ALFONSO");
		vendedor.setTotal(700.0);
		vendedor.setFecha(new Date());
		
		listaVendedores.put(vendedor.getApellidos(),vendedor);
		Servidor.setIndice(indice++);
		
		tienda.setListaVendedores(listaVendedores);
		tienda.setNombreTienda("Mi tienda Servidor");
		
		//Actualizamos la tienda global
		Servidor.setTienda(tienda);
		
		return tienda;
	}
}
