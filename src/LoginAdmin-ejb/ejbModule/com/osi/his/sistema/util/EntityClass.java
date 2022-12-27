package com.osi.his.sistema.util;

import java.io.Serializable;

/**
 * Clase de la cual deben heredar todos los entities y que va a tener los valores 
 * comunes.
 * 
 * @author amarquez Alex Ricardo Marquez Coronel (armarquez@colsanitas.com)
 * @since Sep 24, 2008
 *
 */
public class EntityClass implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5517655485470304790L;
	protected String codigoMensaje;
	protected String descripcionMensaje;
	protected String tipoMensaje;

	/**
	 * @return the codigoMensaje
	 */
	public String getCodigoMensaje() {
		return codigoMensaje;
	}
	/**
	 * @param codigoMensaje the codigoMensaje to set
	 */
	public void setCodigoMensaje(String codigoMensaje) {
		this.codigoMensaje = codigoMensaje;
	}
	/**
	 * @return the descripcionMensaje
	 */
	public String getDescripcionMensaje() {
		return descripcionMensaje;
	}
	/**
	 * @param descripcionMensaje the descripcionMensaje to set
	 */
	public void setDescripcionMensaje(String descripcionMensaje) {
		this.descripcionMensaje = descripcionMensaje;
	}
	/**
	 * @return the tipoMensaje
	 */
	public String getTipoMensaje() {
		return tipoMensaje;
	}
	/**
	 * @param tipoMensaje the tipoMensaje to set
	 */
	public void setTipoMensaje(String tipoMensaje) {
		this.tipoMensaje = tipoMensaje;
	}
    
}