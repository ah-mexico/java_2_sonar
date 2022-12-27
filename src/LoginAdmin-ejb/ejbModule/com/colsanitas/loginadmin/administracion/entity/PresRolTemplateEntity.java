package com.colsanitas.loginadmin.administracion.entity;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.validator.NotNull;

import com.osi.gaudi.security.authorization.clientews.Role;

@Entity
@Table(name = "AUTH_TB_PRES_ROL_TEMPLATE")
public class PresRolTemplateEntity implements LocalEntity {

	private static final long serialVersionUID = 3837593293208412823L;

	private Long id;
	private Long roleId;
	private Long prestadorId;
	
	private Role role;
	
	private boolean estado;
	private boolean selected;
	
	private String accion;
	
	@Transient
	private boolean administradorDominio; 
	
	
	public PresRolTemplateEntity() {
	}

	public PresRolTemplateEntity(Long presRolTemplateId, Long prestadorId, Long roleId ) {
		this.id = presRolTemplateId;
		this.prestadorId = prestadorId;
		this.roleId = roleId;
	}
	
    @SequenceGenerator(name="AUTH_SQ_PRES_ROL_TEMPLATE", sequenceName="AUTH_SQ_PRES_ROL_TEMPLATE", allocationSize=1)
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="AUTH_SQ_PRES_ROL_TEMPLATE")
    @Column(name="pres_rol_id")
    public Long getId() {
		return this.id;
	}

	public void setId(Long prestadorId) {
		this.id = prestadorId;
	}
	
	@Column(name = "ROLE_ID", unique = true, nullable = false, length = 5)
	@NotNull
	public Long getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	
	@Column(name = "PRESTADOR_ID", unique = true, nullable = false, precision = 22, scale = 0)
	@NotNull
    public Long getPrestadorId() {
		return this.prestadorId;
	}

	public void setPrestadorId(Long prestadorId) {
		this.prestadorId = prestadorId;
	}
	
	@Column(name = "ESTADO", nullable = false, precision = 1, scale = 0)
	public boolean isEstado() {
		return this.estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	
	@Transient
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	@Transient
	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}
	
	@Transient
	public boolean isSelected() {
		return selected;
	}

	@Transient
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((prestadorId == null) ? 0 : prestadorId.hashCode());
		result = prime * result + ((roleId == null) ? 0 : roleId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PresRolTemplateEntity other = (PresRolTemplateEntity) obj;
		if (prestadorId == null) {
			if (other.prestadorId != null)
				return false;
		} else if (!prestadorId.equals(other.prestadorId))
			return false;
		if (roleId == null) {
			if (other.roleId != null)
				return false;
		} else if (!roleId.equals(other.roleId))
			return false;
		return true;
	}

	@Transient
	@XmlTransient
	public boolean getAdministradorDominio() {
		return administradorDominio;
	}
	@Transient
	@XmlTransient
	public boolean isAdministradorDominio() {
		return administradorDominio;
	}

	public void setAdministradorDominio(boolean administradorDominio) {
		this.administradorDominio = administradorDominio;
	}
	
	
	

}
