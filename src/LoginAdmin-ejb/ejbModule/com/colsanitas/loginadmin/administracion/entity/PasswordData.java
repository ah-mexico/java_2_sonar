package com.colsanitas.loginadmin.administracion.entity;

import java.util.Date;

public class PasswordData {
	private Date fechaExpiracion;
	private boolean debeCambiar;

	public Date getFechaExpiracion() {
		return fechaExpiracion;
	}

	public void setFechaExpiracion(Date expiracion) {
		this.fechaExpiracion = expiracion;
	}

	public boolean isDebeCambiar() {
		return debeCambiar;
	}

	public void setDebeCambiar(boolean debeCambiar) {
		this.debeCambiar = debeCambiar;
	}	

	@Override
	public PasswordData clone() {
		PasswordData object = new PasswordData();
		object.setDebeCambiar(this.debeCambiar);
		object.setFechaExpiracion(this.fechaExpiracion);
		return object;
	}

}
