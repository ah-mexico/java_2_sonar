package com.colsanitas.loginadmin.administracion.view;

import com.osi.his.sistema.util.EntityClass;


public class TipoDocumentoView extends EntityClass {

	private static final long serialVersionUID = -1423952149071450511L;
	
	private Long id;
	private String name;

	public TipoDocumentoView() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

