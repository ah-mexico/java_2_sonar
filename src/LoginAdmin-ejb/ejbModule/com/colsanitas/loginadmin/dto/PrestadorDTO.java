package com.colsanitas.loginadmin.dto;

import com.colsanitas.loginadmin.administracion.entity.PrestadorEntity;

public class PrestadorDTO {

	private PrestadorEntity prestadorEntity;
	private boolean estado;
	/**
	 * @return the prestadorEntity
	 */
	public PrestadorEntity getPrestadorEntity() {
		return prestadorEntity;
	}
	/**
	 * @param prestadorEntity the prestadorEntity to set
	 */
	public void setPrestadorEntity(PrestadorEntity prestadorEntity) {
		this.prestadorEntity = prestadorEntity;
	}
	/**
	 * @return the estado
	 */
	public boolean isEstado() {
		return estado;
	}
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	
	
	
	
}
