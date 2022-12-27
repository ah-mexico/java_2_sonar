package com.colsanitas.loginadmin.administracion.view;

/**
 * Clase para la consulta de los tipos de relaciones no autorizadas para el usuario.
 * @author armarquez
 *
 */
public class UsuarioDesutorizadoView {
	private int tipoRelacion;
	private String userId;
	
	public UsuarioDesutorizadoView(String userId, int tipoRelacion){
		this.userId = userId;
		this.tipoRelacion = tipoRelacion;
	}
	
	public int getTipoRelacion() {
		return tipoRelacion;
	}
	public void setTipoRelacion(int tipoRelacion) {
		this.tipoRelacion = tipoRelacion;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
