package com.grammer.code.util;

public class HoraXHora {
 
	public String getHora(int hora, int minuto, String turno) {
		System.out.println(hora + " " + minuto);
		if (turno.equals("Primer Turno")) {
			for (int i = 6; i <= 14; i++) {
				if (i > 6 && hora == i && minuto < 30) {
					return getMascaraHora(i - 1 + "") + ":30";
				}
				if (i < 14 && hora == i && minuto >= 30) {
					return getMascaraHora(i + "") + ":30";
				}
			}
		}
		if (turno.equals("Segundo Turno")) {
 
			for (int i = 14; i <= 21; i++) {
				if (i > 14 && hora == i && minuto < 30) {
					return getMascaraHora(i - 1 + "") + ":30";
				}
				if (hora == i && minuto >= 30) {
					return getMascaraHora(i + "") + ":30";
				}
			}
		}
		if (turno.equals("Tercer Turno")) {
			for (int i = 22; i <= 23; i++) {
				if (hora == i) {
					return getMascaraHora(i + "") + ":00";
				}
			}
 
			for (int i = 0; i < 5; i++) {
				if (hora == i) {
					return getMascaraHora(i + "") + ":00";
				}
			}
 
			if ((hora >= 5 && hora <= 6)) {
 
				if (hora == 5) {
					return getMascaraHora(hora + "") + ":00";
				}
 
				if (hora == 6 && minuto < 30) {
					return getMascaraHora(hora + "") + ":00";
				}
 
			}
 
		}
 
		return "";
	}
 
	public String getMascaraHora(String hora) {
 
		if (hora.length() == 1) {
			hora = "0" + hora;
		}
		return hora;
	}
}