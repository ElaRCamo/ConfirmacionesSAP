package com.grammer.code.util;

import java.io.IOException;
import java.net.InetAddress;

public class JPing {
	/* Se verifica si el host localhost es accesible dentro de un tiempo de espera de 5000 milisegundos (5 segundos), 
	 * imprimiendo un mensaje en caso de que no responda. 
	 */
	public static void main(String[] args) {
	    InetAddress ping;
	    String ip = "localhost";
	    String respuesta = "";
	    
	    try {
	        ping = InetAddress.getByName(ip);
	        if(ping.isReachable(5000)) {
	        	respuesta = "Conexión exitosa con "+ ip;
	        } else {
	        	respuesta = "No se pudo conectar con " + ip;
	        }
	        
	        System.out.println(respuesta);
	        
	    } catch(IOException ex) {
	        System.out.println(ex);
	    }
	}

	/* realiza una prueba similar, pero para cualquier dirección IP proporcionada, 
	 * y devuelve true si el host es accesible dentro de 500 milisegundos (0.5 segundos) o false en caso contrario
	 */
	public boolean validaPing(String ip) {
		InetAddress ping;
		boolean respuesta = false;
		
		try {
			ping = InetAddress.getByName(ip);
			if(ping.isReachable(500)) {
				respuesta = true;
			}
		}catch(IOException ex) {
			respuesta = false;
		}
		
		System.out.println("---> class JPing - validaPing(String ip) | Conexion exitosa: " + respuesta);
		return respuesta ;
	}

}
