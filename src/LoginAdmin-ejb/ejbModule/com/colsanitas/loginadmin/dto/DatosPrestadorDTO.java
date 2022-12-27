package com.colsanitas.loginadmin.dto;





public class DatosPrestadorDTO  {


	
	private String codigoPersona;
    private String codigoTipoIDNegocio;
    private String estado;
    private String numeroID;
    private String razonSocial;
    private Long tipoID;
	private String nombre;
	
	
	/**
	 * Constructor
	 */
	public DatosPrestadorDTO() {
		
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return
	 */
	public String getCodigoPersona() {
		return codigoPersona;
	}

	/**
	 * @param codigoPersona
	 */
	public void setCodigoPersona(String codigoPersona) {
		this.codigoPersona = codigoPersona;
	}

	/**
	 * @return
	 */
	public String getCodigoTipoIDNegocio() {
		return codigoTipoIDNegocio;
	}

	/**
	 * @param codigoTipoIDNegocio
	 */
	public void setCodigoTipoIDNegocio(String codigoTipoIDNegocio) {
		this.codigoTipoIDNegocio = codigoTipoIDNegocio;
	}

	/**
	 * @return
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * @return
	 */
	public String getNumeroID() {
		return numeroID;
	}

	/**
	 * @param numeroID
	 */
	public void setNumeroID(String numeroID) {
		this.numeroID = numeroID;
	}

	/**
	 * @return
	 */
	public String getRazonSocial() {
		return razonSocial;
	}

	/**
	 * @param razonSocial
	 */
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	/**
	 * @return
	 */
	public Long getTipoID() {
		return tipoID;
	}

	/**
	 * @param tipoID
	 */
	public void setTipoID(Long tipoID) {
		this.tipoID = tipoID;
	}

}
