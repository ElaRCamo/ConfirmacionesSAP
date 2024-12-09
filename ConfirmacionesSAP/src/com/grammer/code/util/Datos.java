package com.grammer.code.util;


import com.grammer.code.dao.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class Datos {
	
	/*
	 * El método openApp() ejecuta el comando tasklist.exe para listar todos los procesos en ejecución en un sistema Windows. 
	 * Luego, lee la salida del comando para verificar si el proceso saplogon.exe está en la lista. 
	 * Si encuentra el proceso, devuelve "OK"; de lo contrario, devuelve "saplogon.exe no se encuentra en la lista".
	 */
	public String openApp() {
	    String line;
	    String pidInfo = "";
	    Process p;
	    String respuesta = "";

	    try {
	        // Ejecuta el comando para listar los procesos en ejecución en el sistema.
	        p = Runtime.getRuntime().exec(System.getenv("windir") + "\\system32\\" + "tasklist.exe");

	        // Lee la salida del comando utilizando un BufferedReader.
	        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));

	        // Lee cada línea de la salida del comando.
	        while ((line = input.readLine()) != null) {
	            pidInfo += line; // Acumula cada línea en la variable pidInfo.
	        }

	        // Cierra el BufferedReader después de que se ha leído toda la salida.
	        input.close();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    // Verifica si la salida contiene el nombre del proceso "saplogon.exe".
	    if (pidInfo.contains("saplogon.exe")) {
	        respuesta = "OK"; // Si el proceso está en la lista, asigna "OK" a la respuesta.
	    } else {
	        respuesta = "saplogon.exe no se encuentra en la lista"; // Si no está en la lista, asigna un mensaje de error.
	    }

	    System.out.println("--> class Datos - openApp() | respuesta = " + respuesta);
	    return respuesta;
	}

    
    /*
     * Obtiene el nombre del host de la computadora local y verifica su estatus utilizando 
     * el método getEstatus de ComputadoraRes. 
     * Devuelve true si la computadora está activa, o false en caso contrario.
     */
    public static boolean ejecucion() {
        ComputadoraRes computadoraRes = new ComputadoraRes();
        InetAddress addr;
        boolean respuesta = false;
        try {
            addr = InetAddress.getLocalHost();
            String hostname = addr.getHostName();
            respuesta = computadoraRes.getEstatus(hostname);
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            respuesta = false;
        }
        if(!respuesta) {
        	JOptionPane.showMessageDialog(null, "Ya existe una sesión activa.", "Aviso de Sesión", JOptionPane.WARNING_MESSAGE);
        }
        System.out.println("--> class Datos - ejecucion() | respuesta = " + respuesta);
        return respuesta;
    }

    /* Obtiene el nombre del host de la computadora local y actualiza su estatus para marcarla 
     * como inactiva utilizando el método updateCerrarSesion de ComputadoraRes. 
     * Devuelve true si la actualización fue exitosa, o false en caso contrario.
     */
    public static boolean ejecucionCierre() {
        ComputadoraRes computadoraRes = new ComputadoraRes();
        InetAddress addr;
        boolean respuesta = false;
        
        try {
            addr = InetAddress.getLocalHost();
            String hostname = addr.getHostName();
            respuesta = computadoraRes.updateCerrarSesion(hostname);
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            respuesta = false;
        }
        System.out.println("--> class Datos - ejecucionCierre() | respuesta = " + respuesta);
        return respuesta;
    }
}
