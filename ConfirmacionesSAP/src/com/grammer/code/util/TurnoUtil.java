package com.grammer.code.util;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;


public class TurnoUtil {
	// Método para determinar el turno
    public static TurnoInfo determinarTurno() {
    	LocalDateTime hora = LocalDateTime.now();
        int horaExacta = hora.getHour();
        int minExacto = hora.getMinute();
       

        String hostname = "";
        try {
            InetAddress addr = InetAddress.getLocalHost();
            hostname = addr.getHostName();
        } catch (UnknownHostException e) {
            hostname = "Desconocido"; 
            e.printStackTrace();
        }
   
           
       // Determinar el turno
       String turno = turnoNombre(horaExacta, minExacto);

       // Registrar valores para depuración
       
       System.out.println("\n**************************************Validando HOSTNAME...*****************************************");
       System.out.println("Hora exacta: " + horaExacta);
       System.out.println("Minutos exactos: " + minExacto);
       System.out.println("Hostname: " + hostname);
       System.out.println("Turno: " + turno);

       return new TurnoInfo(turno, horaExacta, minExacto, hostname);
    }
    
    public static String turnoNombre(int hora, int minutos) {
		String turno = "";
 
		if (hora > 21) {
			turno = "Tercer Turno";
		}
		if ((hora >= 0 && hora < 7 )) {
			turno = "Tercer Turno";
			if ((hora == 6 && minutos > 29 )) {
				turno = "Primer Turno";
			}
		}
		if ((hora > 6 && hora < 15 )) {
			turno = "Primer Turno";
			if ((hora == 14 && minutos > 29 )) {
				turno = "Segundo Turno";
			}
		}
		if ((hora > 14 && hora < 22 )) {
			turno = "Segundo Turno";
		}
		
		/*
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
	    }*/
	    
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
    
}

