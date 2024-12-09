package com.grammer.code.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="PLATFORM")
public class Platform {

    @Id
    @Column(name = "ID")//uniqueidentifier = newid()
    private String id;

    //@Id
    @Column(name = "NUMERO_PARTE") //nvarchar(20)
    private String numeroParte;

    //@Id
    @Column(name = "PLATAFORMA") //nvarchar(20)
    private String plataforma;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNumeroParte() {
		return numeroParte;
	}

	public void setNumeroParte(String numeroParte) {
		this.numeroParte = numeroParte;
	}

	public String getPlataforma() {
		return plataforma;
	}

	public void setPlataforma(String plataforma) {
		this.plataforma = plataforma;
	}

	@Override
	public String toString() {
		return "Platform [id=" + id + ", numeroParte=" + numeroParte + ", plataforma=" + plataforma + "]";
	}


}
