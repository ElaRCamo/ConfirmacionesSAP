package com.grammer.code.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AREAS")
public class Areas {

    @Id
    @Column(name = "ID")//uniqueidentifier = newid()
    private String id;

    @Column(name = "AREA")//nvarchr(20)
    private String area;

    @Column(name = "PVB")//nvarchar(15)
    private String pvb;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getPvb() {
		return pvb;
	}

	public void setPvb(String pvb) {
		this.pvb = pvb;
	}

	@Override
	public String toString() {
		return "Areas [id=" + id + ", area=" + area + ", pvb=" + pvb + "]";
	}


}
