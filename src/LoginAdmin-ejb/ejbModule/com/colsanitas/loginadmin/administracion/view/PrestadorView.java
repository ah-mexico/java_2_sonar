package com.colsanitas.loginadmin.administracion.view;

import com.osi.his.sistema.util.EntityClass;


public class PrestadorView extends EntityClass {

	private static final long serialVersionUID = -1423952149071450511L;
	
	private Long prestadorId;
	private Long cdperson;
	private Long tipoId;
	private String numId;
	private String razonSocial;
	private String estado;
	private String codigoTipoIDNegocio;

	public PrestadorView() {
	}


	public Long getPrestadorId() {
		return this.prestadorId;
	}

	public void setPrestadorId(Long prestadorId) {
		this.prestadorId = prestadorId;
	}

	public Long getCdperson() {
		return this.cdperson;
	}

	public void setCdperson(Long cdperson) {
		this.cdperson = cdperson;
	}

	public Long getTipoId() {
		return this.tipoId;
	}

	public void setTipoId(Long tipoId) {
		this.tipoId = tipoId;
	}

	public String getNumId() {
		return this.numId;
	}

	public void setNumId(String numId) {
		this.numId = numId;
	}
	
	public String getRazonSocial() {
		return razonSocial;
	}


	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	
	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public String getCodigoTipoIDNegocio() {
		return codigoTipoIDNegocio;
	}


	public void setCodigoTipoIDNegocio(String codigoTipoIDNegocio) {
		this.codigoTipoIDNegocio = codigoTipoIDNegocio;
	}
}

