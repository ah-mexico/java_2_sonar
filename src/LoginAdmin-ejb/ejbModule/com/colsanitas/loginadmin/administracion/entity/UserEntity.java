package com.colsanitas.loginadmin.administracion.entity;

import java.util.Date;

// Generated 17 ao�t 2011 17:36:03 by Hibernate Tools 3.4.0.CR1

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.envers.Audited;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

import com.osi.his.sistema.util.EntityClass;

/**
 * AuthTbUser generated by hbm2java
 */
@Entity
@Table(name = "AUTH_TB_USER")
@Audited
public class UserEntity extends EntityClass implements LocalEntity {

	private static final long serialVersionUID = -5614437431144870897L;
	
	private String userId;
	private String userName;
	private String userLastName;
	private String userMail;
	private boolean blockedUser;
	private String userDescription;
	
	
	//Para almacenar en el LDAP -- se deben agregar adem�s todos los campos de consulta del LDAP
	private String  userDocument;
	private String userPassword;
	private String userLogin;
	
	private String tipoDoc;
	private String document;
	private Long idPrestador;
	
	private boolean valUserInfo;


	private Set<RelacionEntity> relaciones;

	private PasswordData pwdData;
	
	@Transient
	private boolean adminOsi;
	
	private Date expDate;
	
	public UserEntity() {
	}

	public UserEntity(String userId, String userName, String userLastName) {
		this.userId = userId;
		this.userName = userName;
		this.userLastName = userLastName;
	}

	public UserEntity(String userId, String userName, String userLastName,
			String userMail, boolean blockedUser, String userDescription, String tipoDoc, String document, Date expDate) {
		this.userId = userId;
		this.userName = userName;
		this.userLastName = userLastName;
		this.userMail = userMail;
		this.blockedUser = blockedUser;
		this.userDescription = userDescription;
		this.tipoDoc = tipoDoc;
		this.document = document;
		this.expDate = expDate;
	}

	//LDAP
	@XmlTransient
	@Transient
	public String getUserDocument() {
		return userDocument;
	}

	public void setUserDocument(String userDocument) {
		this.userDocument = userDocument;
	}

	@XmlTransient
	@Transient
	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	@XmlTransient
	@Transient
	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	@XmlTransient
	@Transient
	public PasswordData getPwdData() {
		return pwdData;
	}

	public void setPwdData(PasswordData pwdData) {
		this.pwdData = pwdData;
	}

////

	@Id
	@Column(name = "USER_ID", unique = true, nullable = false, length = 64)
	@NotNull
	@Length(max = 64)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "USER_NAME", nullable = false, length = 128)
	@NotNull
	@Length(max = 128)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "USER_LAST_NAME", nullable = false, length = 50)
	@NotNull
	@Length(max = 50)
	public String getUserLastName() {
		return this.userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	@Column(name = "USER_MAIL", length = 254)
	@Length(max = 254)
	public String getUserMail() {
		return this.userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	@XmlTransient
	@Column(name = "BLOCKED_USER", precision = 1, scale = 0)
	public boolean getBlockedUser() {
		return this.blockedUser;
	}

	public void setBlockedUser(boolean blockedUser) {
		this.blockedUser = blockedUser;
	}

	@XmlTransient
	@Column(name = "USER_DESCRIPTION", length = 254)
	@Length(max = 254)
	public String getUserDescription() {
		return this.userDescription;
	}

	public void setUserDescription(String userDescription) {
		this.userDescription = userDescription;
	}

	@Column(name = "USER_TIPO_DOC", length = 2)
	@Length(max = 2)
	public String getTipoDoc() {
		return tipoDoc;
	}

	public void setTipoDoc(String tipoDoc) {
		this.tipoDoc = tipoDoc;
	}
	
	@Column(name = "USER_DOCUMENT", length = 32)
	@Length(max = 32)                  
	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}
	
	@Column(name = "EXP_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getExpDate() {
		return expDate;
	}

	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}
	
	
	@XmlTransient
	@Transient
	public String getNombreCompleto() {
		return (this.userName != null ? this.userName : "") + " " + (this.userLastName != null ? this.userLastName : "");
	}
	
	@XmlTransient
	@Transient
	public Long getIdPrestador() {
		return idPrestador;
	}

	public void setIdPrestador(Long idPrestador) {
		this.idPrestador = idPrestador;
	}

	@OneToMany(mappedBy="usuario", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@XmlTransient
	public Set<RelacionEntity> getRelaciones() {
		return this.relaciones;
	}

	public void setRelaciones(Set<RelacionEntity> relaciones) {
		this.relaciones = relaciones;
	}
	
	@XmlTransient
	@Transient
	public boolean isValUserInfo() {
		return valUserInfo;
	}
	
	
	public void setValUserInfo(boolean valUserInfo) {
		this.valUserInfo = valUserInfo;
	
	}
		
	
	@Transient
	@XmlTransient
	public boolean isAdminOsi() {
		return adminOsi;
	}
	@Transient
	@XmlTransient
	public boolean getAdminOsi() {
		return adminOsi;
	}

	public void setAdminOsi(boolean adminOsi) {
		this.adminOsi = adminOsi;
	}

	@Override
	public UserEntity clone() {
		UserEntity clon = new UserEntity();
		clon.setBlockedUser(this.blockedUser);
		clon.setCodigoMensaje(this.userDescription);
		clon.setDescripcionMensaje(this.userDescription);
		clon.setTipoMensaje(this.userDescription);
		clon.setUserDescription(this.userDescription);
		clon.setUserDocument(this.userDocument);
		clon.setUserId(this.userId);
		clon.setUserLastName(this.userLastName);
		clon.setUserLogin(this.userLogin);
		clon.setUserMail(this.userMail);
		clon.setUserMail(this.userMail);
		clon.setUserName(this.userName);
		if (this.pwdData != null ){ 
			clon.setPwdData((PasswordData) this.pwdData.clone());
		}
		clon.setUserPassword(this.userPassword);
		clon.setTipoDoc(this.tipoDoc);
		clon.setDocument(this.document);
		clon.setIdPrestador(this.idPrestador);
		//Modificado por Softmanagement
		//Yency Serrano
		//Abril 10 de 2013
		clon.setAdminOsi(this.adminOsi);
		clon.setExpDate(this.expDate);
		return clon;
	}
	
}
