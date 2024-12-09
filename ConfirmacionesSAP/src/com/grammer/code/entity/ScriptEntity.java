package com.grammer.code.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="[dbo].[SCRIPTS]")

public class ScriptEntity {
	
	@Id
	@Column(name="ID")//uniqueidentifier = newid()  ------->antes estaba con minusculas
	private String id;
	
	@Column(name="SCRIPT")//nvarchar(max)           ------->antes estaba con minusculas
	private String script;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	@Override
	public String toString() {
		return "ScriptEntity [id=" + id + ", script=" + script + "]";
	}
	
	
	

}
