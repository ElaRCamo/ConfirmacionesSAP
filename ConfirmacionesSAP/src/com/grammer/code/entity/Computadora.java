package com.grammer.code.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "COMPUTADORA")
public class Computadora {
	
	 @Id
	 @Column(name = "ID")//uniqueidentifier = newid()
	 private String ID;

	 @Column(name = "COMPUTADORA")//nvarchar(50)
	 private String computadora;

	 @Column(name = "ESTATUS")//nvarchar(2)
	 private String estatus;

	 @Column(name = "INICIO_SESION")//datetime
	 private Date inicioSesion;

	 @Column(name = "CIERRE_SESION")//datetime
	 private Date cerrieSesion;

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getComputadora() {
		return computadora;
	}

	public void setComputadora(String computadora) {
		this.computadora = computadora;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public Date getInicioSesion() {
		return inicioSesion;
	}

	public void setInicioSesion(Date inicioSesion) {
		this.inicioSesion = inicioSesion;
	}

	public Date getCerrieSesion() {
		return cerrieSesion;
	}

	public void setCerrieSesion(Date cerrieSesion) {
		this.cerrieSesion = cerrieSesion;
	}

	@Override
	public String toString() {
		return "Computadora [ID=" + ID + ", computadora=" + computadora + ", estatus=" + estatus + ", inicioSesion="
				+ inicioSesion + ", cerrieSesion=" + cerrieSesion + "]";
	}


}
