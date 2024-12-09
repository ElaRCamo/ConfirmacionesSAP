package com.grammer.code.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="[dbo].[USUARIOS]")
public class UserEntity {
	@Id
	@Column(name="ID")//uniqueidentifier = newid()
	private String id;

	@Column(name="USUARIO")//NVARCHAR(50)
	private String idUsuario;
	
	@Column(name="PASSWORD")//NVARCHAR(50)
	private String password; 

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserEntity [id=" + id + ", idUsuario=" + idUsuario + ", password=" + password + "]";
	}
	
}
