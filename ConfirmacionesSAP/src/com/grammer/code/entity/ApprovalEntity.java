package com.grammer.code.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "[dbo].[APPROVAL]")
public class ApprovalEntity {

    @Id
    @Column(name = "ID")//uniqueidentifier = newid()
    private String id;

    @Column(name = "NUMERO_PARTE")//NVARCHAR(20)
    private String numP;

    @Column(name = "NUMERO_CONFIRMACION")//NVARCHAR(20)
    private String numConfirmacion;

    @Column(name = "FECHA_INS")//datetime
    private Date fechaIns;

    @Column(name = "TURNO")//NVARCHAR(20)
    private String turno;

    @Column(name = "USUARIO")//NVARCHAR(20)
    private String usuario;

    @Column(name = "LIGA")//NVARCHAR(150)
    private String liga;

    @Column(name = "CANTIDAD")//int
    private String cantidad;

    @Column(name = "COMPUTADORA")//NVARCHAR(max)
    private String computadora;
    
    @Column(name = "HORAS")//NVARCHAR(50)
    private String horas;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNumP() {
		return numP;
	}

	public void setNumP(String numP) {
		this.numP = numP;
	}

	public String getNumConfirmacion() {
		return numConfirmacion;
	}

	public void setNumConfirmacion(String numConfirmacion) {
		this.numConfirmacion = numConfirmacion;
	}

	public Date getFechaIns() {
		return fechaIns;
	}

	public void setFechaIns(Date fechaIns) {
		this.fechaIns = fechaIns;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getLiga() {
		return liga;
	}

	public void setLiga(String liga) {
		this.liga = liga;
	}

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	public String getComputadora() {
		return computadora;
	}

	public void setComputadora(String computadora) {
		this.computadora = computadora;
	}

	public String getHoras() {
		return horas;
	}

	public void setHoras(String horas) {
		this.horas = horas;
	}

	@Override
	public String toString() {
		return "ApprovalEntity [id=" + id + ", numP=" + numP + ", numConfirmacion=" + numConfirmacion + ", fechaIns="
				+ fechaIns + ", turno=" + turno + ", usuario=" + usuario + ", liga=" + liga + ", cantidad=" + cantidad
				+ ", computadora=" + computadora + ", horas=" + horas + "]";
	}

	

}
