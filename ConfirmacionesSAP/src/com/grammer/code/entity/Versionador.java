package com.grammer.code.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "VERSIONADOR")
public class Versionador {
	@Id
    @Column(name = "ID")//uniqueidentifier = newid()
    private String id;

    @Column(name = "VERSION")//float
    private float version;   //antes era string

    @Column(name = "STATUS_LETRA")//nvarchar(20)
    private String statusLetra;

    @Column(name = "STATUS")//nvarchar(5)
    private String status;

    @Column(name = "FECHA_INS")//datetime
    private Date fechaIns;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public float getVersion() {
		return version;
	}

	public void setVersion(float version) {
		this.version = version;
	}

	public String getStatusLetra() {
		return statusLetra;
	}

	public void setStatusLetra(String statusLetra) {
		this.statusLetra = statusLetra;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getFechaIns() {
		return fechaIns;
	}

	public void setFechaIns(Date fechaIns) {
		this.fechaIns = fechaIns;
	}

	@Override
	public String toString() {
		return "Versionador [id=" + id + ", version=" + version + ", statusLetra=" + statusLetra + ", status=" + status
				+ ", fechaIns=" + fechaIns + "]";
	}
}
