package com.colsanitas.loginadmin.audit;

import org.hibernate.envers.RevisionListener;
import org.jboss.seam.Component;
import org.jboss.seam.security.Identity;

/**
 * Clase que escucha los cambios para agregar la información adicional de
 * auditoría en la entidad respectiva.
 * 
 * @author armarquez
 * 
 */
public class AuditoriaListener implements RevisionListener {
	public void newRevision(Object revisionEntity) {
		AuditoriaRevisionEntity oEntity = (AuditoriaRevisionEntity) revisionEntity;
		// Tambien se puede inyectar del contexto
		Identity identity = (Identity) Component.getInstance("org.jboss.seam.security.identity");
		oEntity.setUsername(identity.getCredentials().getUsername());
	}
}
