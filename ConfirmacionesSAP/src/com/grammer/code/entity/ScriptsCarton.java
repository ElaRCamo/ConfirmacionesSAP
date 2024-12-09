package com.grammer.code.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SCRIPT_CARTON")
public class ScriptsCarton {

    @Id
    @Column(name="ID")//uniqueidentifier = newid()
    private String id;

    @Column(name="CANTIDAD")//INT   
    private int cantidad;   //--------------> antes estaba como String

    @Column(name="SCRIPT")//nvarchar(max)
    private String script;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	@Override
	public String toString() {
		return "ScriptsCarton [id=" + id + ", cantidad=" + cantidad + ", script=" + script + "]";
	}
}