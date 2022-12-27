package com.colsanitas.loginadmin.administracion.view;


import java.io.Serializable;
import java.util.List;

import com.colsanitas.loginadmin.administracion.entity.RelacionEntity;
import com.colsanitas.loginadmin.administracion.entity.UserEntity;


public class UserResponseView implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int responseCode;
	private String message;
	private String cause;	
	private UserEntity userEntity;
	private List<RelacionEntity> listRelacioUser;

	/**
	 * @return the responseCode
	 */
	public int getResponseCode() {
		return responseCode;
	}

	/**
	 * @param responseCode the responseCode to set
	 */
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}	

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the userDTO
	 */
	public UserEntity getUserDTO() {
		return userEntity;
	}

	/**
	 * @param userDTO the userDTO to set
	 */
	public void setUserDTO(UserEntity userDTO) {
		this.userEntity = userDTO;
	}

	/**
	 * Causa raiz del mensaje de error, si aplica. 
	 * @return
	 */
	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}
	
	public List<RelacionEntity> getListRelacioUser() {
		return listRelacioUser;
	}

	public void setListRelacioUser(List<RelacionEntity> listRelacioUser) {
		this.listRelacioUser = listRelacioUser;
	}
}
