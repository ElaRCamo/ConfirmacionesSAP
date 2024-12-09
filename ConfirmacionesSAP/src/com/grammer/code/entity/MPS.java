package com.grammer.code.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MPS")
public class MPS {

    @Id
    @Column(name = "ID") //uniqueidentifier = newid()
    private String id;

    @Column(name = "CONTADOR") //int
    private int contador;      //------->antes estaba con string

    @Column(name = "MPS_PLATFORM")//nvarchar(max)
    private String mpsPlatform;

    @Column(name = "TYPE")//nvarchar(max)
    private String type;
    
    @Column(name = "PKG_ELEMENT")//nvarchar(20)
    private String pkgElement;

    @Column(name = "NUMERO_PARTE")//nvarchar(20)
    private String numeroParte;

    @Column(name = "QTY")//float
    private float qty;   //------->antes estaba con string

    @Column(name = "STD_PCK")//float
    private float stdPck;//------->antes estaba con string

    @Column(name = "UNO")//int
    private int uno;    //------->antes estaba con string

    @Column(name = "DOS")//int
    private int dos;  //------->antes estaba con string

    @Column(name = "TRES")//int
    private int tres;  //------->antes estaba con string

    @Column(name = "FULL_UNIT")//int
    private int fullUnit;  //------->antes estaba con string

    @Column(name = "RANGO") //float
    private float RANGO;    //------->antes estaba con string

    @Column(name = "PRISMA") //nvarchar(max)
    private String prisma;
    
    @Column(name = "VLOOK") //nvarchar(max)
    private String vlook;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getContador() {
		return contador;
	}

	public void setContador(int contador) {
		this.contador = contador;
	}

	public String getMpsPlatform() {
		return mpsPlatform;
	}

	public void setMpsPlatform(String mpsPlatform) {
		this.mpsPlatform = mpsPlatform;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPkgElement() {
		return pkgElement;
	}

	public void setPkgElement(String pkgElement) {
		this.pkgElement = pkgElement;
	}

	public String getNumeroParte() {
		return numeroParte;
	}

	public void setNumeroParte(String numeroParte) {
		this.numeroParte = numeroParte;
	}

	public float getQty() {
		return qty;
	}

	public void setQty(float qty) {
		this.qty = qty;
	}

	public float getStdPck() {
		return stdPck;
	}

	public void setStdPck(float stdPck) {
		this.stdPck = stdPck;
	}

	public int getUno() {
		return uno;
	}

	public void setUno(int uno) {
		this.uno = uno;
	}

	public int getDos() {
		return dos;
	}

	public void setDos(int dos) {
		this.dos = dos;
	}

	public int getTres() {
		return tres;
	}

	public void setTres(int tres) {
		this.tres = tres;
	}

	public int getFullUnit() {
		return fullUnit;
	}

	public void setFullUnit(int fullUnit) {
		this.fullUnit = fullUnit;
	}

	public float getRANGO() {
		return RANGO;
	}

	public void setRANGO(float rANGO) {
		RANGO = rANGO;
	}

	public String getPrisma() {
		return prisma;
	}

	public void setPrisma(String prisma) {
		this.prisma = prisma;
	}

	public String getVlook() {
		return vlook;
	}

	public void setVlook(String vlook) {
		this.vlook = vlook;
	}

	@Override
	public String toString() {
		return "MPS [id=" + id + ", contador=" + contador + ", mpsPlatform=" + mpsPlatform + ", type=" + type
				+ ", pkgElement=" + pkgElement + ", numeroParte=" + numeroParte + ", qty=" + qty + ", stdPck=" + stdPck
				+ ", uno=" + uno + ", dos=" + dos + ", tres=" + tres + ", fullUnit=" + fullUnit + ", RANGO=" + RANGO
				+ ", prisma=" + prisma + ", vlook=" + vlook + "]";
	}


}
