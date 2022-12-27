package com.colsanitas.loginadmin.audit;

import javax.persistence.Entity;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

/**
 * Clase que contiene la información adicional que se desea adicionar de
 * auditoría.
 * 
 * @author armarquez
 * 
 */
@Entity
@RevisionEntity(AuditoriaListener.class)
public class AuditoriaRevisionEntity extends DefaultRevisionEntity {
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
