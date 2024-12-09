package com.grammer.code.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

public class TurnoTest {
	
    public static TurnoInfo determinarTurno() {
        LocalDateTime hora = LocalDateTime.now();
        int horaExacta = hora.getHour();
        int minExacto = hora.getMinute();
       

        String hostname = "";
        try {
            InetAddress addr = InetAddress.getLocalHost();
            hostname = addr.getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        String turno = turnoNombre(horaExacta, minExacto);
       /* if (horas >= 630 && horas <= 1430) {
            turno = "Primer turno";
        } else if (horas > 1430 && horas <= 2230) {
            turno = "Segundo turno";
        } else {
            turno = "Tercer turno";
        }*/

        return new TurnoInfo(turno, horaExacta, minExacto, hostname);
    }
    
    public static String turnoNombre(int hora, int minutos) {
		String turno = "";
 
		// Tercer turno: de 22:30 a 06:30
	    if ((hora > 22 || (hora == 22 && minutos >= 30)) || (hora >= 0 && hora < 7) || (hora == 6 && minutos <= 30)) {
	        turno = "Tercer Turno";
	    }
	    // Primer turno: de 06:30 a 14:30
	    else if ((hora > 6 && hora < 14) || (hora == 6 && minutos > 30) || (hora == 14 && minutos <= 30)) {
	        turno = "Primer Turno";
	    }
	    // Segundo turno: de 14:30 a 22:30
	    else if ((hora > 14 && hora < 22) || (hora == 14 && minutos > 30) || (hora == 22 && minutos < 30)) {
	        turno = "Segundo Turno";
	    }
	    
	    return turno;
	}

    public static class TurnoInfo {
        private String turno;
        private int horas;
        private int minutos;
        private String hostname;

        public TurnoInfo(String turno, int horas, int minutos, String hostname) {
            this.turno = turno;
            this.horas = horas;
            this.minutos = minutos;
            this.hostname = hostname;
        }

        public String getTurno() {
            return turno;
        }

        public int getHoras() {
            return horas;
        }
        
        public int getMinutos() {
            return minutos;
        }
        
        public String getHostname() {
            return hostname;
        }
    }
    public static void main(String[] args) {
    	        // Simulación de horarios para el Tercer Turno
    	        int[][] simulaciones = {
    	            {22, 0},  // Inicio del Tercer Turno
    	            {23, 45}, // Hora dentro del Tercer Turno (noche)
    	            {0, 00},  // Hora dentro del Tercer Turno (madrugada)
    	            {3, 30},  // Hora dentro del Tercer Turno (madrugada)
    	            {5, 59},  // Última hora completa del Tercer Turno
    	            {6, 29},  // Último minuto del Tercer Turno
    	        };

    	        // Procesar cada simulación
    	        System.out.println("Simulación de horarios del Tercer Turno:");
    	        for (int[] simulacion : simulaciones) {
    	            int hora = simulacion[0];
    	            int minutos = simulacion[1];
    	            String turno = TurnoTest.turnoNombre(hora, minutos);
    	            
    	         // Validación
        	        boolean success = true; // Suponemos que el proceso fue exitoso inicialmente
        	        System.out.println("Verificando condiciones...");
        	        if ( hora < 0 || hora > 23 ) {
        	            System.out.println("Error: Condición activada");
        	            success = false;
        	            interrupcionProceso();
        	            return;
        	        }

    	            System.out.printf("Hora: %02d:%02d - Turno: %s%n", hora, minutos, turno);
    	        }

    	        // Prueba con datos actuales de la función determinarTurno()
    	        TurnoTest.TurnoInfo info = TurnoTest.determinarTurno();
    	        String turno = info.getTurno();
    	        int horas = info.getHoras();
    	        int minutos = info.getMinutos();
    	        String horaConMin = horas + "" + minutos; // Concatenar horas y minutos
    	        int horaActual = Integer.parseInt(horaConMin); // Convertir a entero

    	        String hostname = info.getHostname();
    	        
    	        
    	    }
    
    	    // Función que simula la interrupción del proceso
    	    private static void interrupcionProceso() {
    	        System.out.println("Interrumpiendo el proceso...");
    	    }
    	}