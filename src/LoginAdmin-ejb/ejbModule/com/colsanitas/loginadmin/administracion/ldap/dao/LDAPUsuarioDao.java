package com.colsanitas.loginadmin.administracion.ldap.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.naming.InvalidNameException;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InvalidAttributeValueException;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapName;

import org.jboss.seam.annotations.Name;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.colsanitas.loginadmin.administracion.dao.interfaces.IUsuarioDao;
import com.colsanitas.loginadmin.administracion.entity.UserEntity;
import com.colsanitas.loginadmin.administracion.enums.ETipoDocumento;
import com.colsanitas.loginadmin.administracion.utils.EResponse;
import com.colsanitas.loginadmin.administracion.view.TipoDocumentoView;
import com.colsanitas.loginadmin.administracion.view.UserResponseView;
import com.colsanitas.loginadmin.exception.LoginAdminException;
import com.osi.gaudi.cfg.Configurator;
import com.osi.gaudi.exception.Failure;
import com.osi.gaudi.msg.Messenger;
import com.osi.gaudi.security.authentication.ldap.ELDAPAttribute;
import com.osi.gaudi.security.authentication.ldap.LDAPConnection;

@Name("ldapUsuarioDao")
public class LDAPUsuarioDao  implements IUsuarioDao  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2415681911239091982L;

	private static Logger logger = LoggerFactory.getLogger(LDAPUsuarioDao.class);
	
	LDAPConnection ldapConnection = LDAPConnection.getInstance(false);
	private String usersPath = Configurator.getInstance().getString("administracion", "PATH_USERS", ""); 
	private static final String lockAccountValue = Configurator.getInstance().getString("administracion", "lockAccountValue", "000001010000Z");
		
	/**
	 * Crea el usuario en LDAP.	
	 */
	public void registrarUsuarioEntity(UserEntity userEntity, boolean flush) throws LoginAdminException {

		DirContext dirContext = ldapConnection.borrowAdminContext();

		try {

		
			Attributes attrs = new BasicAttributes(true); // case-ignore
			Attribute objclass = new BasicAttribute("objectclass");
			objclass.add("top");
			objclass.add("inetOrgPerson");
			objclass.add("organizationalPerson");
			objclass.add("shadowAccount");
			objclass.add("person");
			
			
			attrs.put(objclass);
			attrs.put(ELDAPAttribute.SURNAME.getLdapName(), userEntity.getUserLastName() );
			attrs.put(ELDAPAttribute.NAME.getLdapName(), userEntity.getUserName());
			attrs.put(ELDAPAttribute.IDENTIFICATION.getLdapName(), userEntity.getUserDocument());
			attrs.put(ELDAPAttribute.USERNAME.getLdapName(),userEntity.getUserLogin());
			attrs.put(ELDAPAttribute.EMAIL.getLdapName(),userEntity.getUserMail() );
			attrs.put(ELDAPAttribute.EMPLOYEE_TYPE.getLdapName(),"ACTIVO");
			/*CC-fechaexpiracion-inicio*/
			attrs.put(ELDAPAttribute.SHADOW_EXPIRE.getLdapName(), String.valueOf(userEntity.getExpDate().getTime()/1000));
			/*CC-fechaexpiracion-fin*/
			
			String principal = "uid=" + userEntity.getUserLogin() + "," + this.usersPath;
			javax.naming.Name dn = new LdapName(principal);	
			
			attrs.put(ELDAPAttribute.USER_PASSWORD.getLdapName(), userEntity.getUserPassword());
			attrs.put(ELDAPAttribute.OPER_MUST_CHANGE_PWD.getLdapName(),"TRUE");
			dirContext.createSubcontext(dn, attrs);

			
			
		} catch (NameAlreadyBoundException e) {
			throw new Failure("USER_REGISTERED_LDAP", Messenger.getInstance().getMsg("messages", "USER_REGISTERED_LDAP"), e);
		} catch (Exception e) {
			throw new Failure("", "Error creando el usuario", e);
		}
		
		ldapConnection.returnAdminContext(dirContext);

	}

	public UserEntity actualizarUsuarioEntity(UserEntity userEntity) throws LoginAdminException {
		DirContext dirContext = ldapConnection.borrowAdminContext();
		
		try {

			ModificationItem[] mods = new ModificationItem[4];

			mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute(ELDAPAttribute.EMAIL.getLdapName(), userEntity.getUserMail()));

			mods[1] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute(ELDAPAttribute.NAME.getLdapName(), userEntity.getUserName()));
			
		    mods[2] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute(ELDAPAttribute.IDENTIFICATION.getLdapName(), userEntity.getUserDocument()));
		    
			mods[3] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
					new BasicAttribute(ELDAPAttribute.SHADOW_EXPIRE.getLdapName(),
							String.valueOf(userEntity.getExpDate().getTime())));
			   		
		    String principal = "uid=" + userEntity.getUserId() + "," + this.usersPath;
			javax.naming.Name dn = new LdapName(principal);
			dirContext.modifyAttributes(dn, mods);
			

		} catch (Exception e) {
			logger.error("Error: LDAPUsuarioDao.actualizarUsuario ", e);
			throw new LoginAdminException("Error en LDAPUsuarioDao.actualizarUsuario ", e);
		}
		
		ldapConnection.returnAdminContext(dirContext);

		
		return userEntity;
	}

	public UserEntity findById(String userName) throws LoginAdminException  {
		UserEntity userEntity = null; 
		DirContext dirContext = ldapConnection.borrowAdminContext();
		
		try {
			String principal = "uid=" + userName + "," + this.usersPath;
			javax.naming.Name dn = new LdapName(principal);
			Attributes attrs = dirContext.getAttributes(dn);
			userEntity = cargarDatosUsuario(attrs);
		} catch (NamingException e) {
			logger.error("No es posible la consulta del usuario ", e);
		}

		ldapConnection.returnAdminContext(dirContext);
		return userEntity;
	}

	public List<UserEntity> consultarUsuarios(String filtroConsulta) throws LoginAdminException {
		List<UserEntity> usuarios = new ArrayList<UserEntity>();
		SearchControls ctls = new SearchControls();	
		String[] attrIDs = {ELDAPAttribute.USERNAME.getLdapName(),
							ELDAPAttribute.IDENTIFICATION.getLdapName(),
							ELDAPAttribute.NAME.getLdapName(), 
							ELDAPAttribute.USER_PASSWORD.getLdapName(),
							ELDAPAttribute.EMAIL.getLdapName(),
							ELDAPAttribute.SHADOW_EXPIRE.getLdapName()};

		ctls.setReturningAttributes(attrIDs);
		DirContext dirContext = ldapConnection.borrowAdminContext();
		try {
			try {
				NamingEnumeration<SearchResult> results = dirContext.search(this.usersPath,filtroConsulta,ctls);
	
				if (results != null && results.hasMore()) {
					while (results.hasMoreElements()) {
						SearchResult result = (SearchResult) results.next();
						Attributes attrs = result.getAttributes();			
						UserEntity usuarioExternoDTO = cargarDatosUsuario(attrs);
						usuarios.add(usuarioExternoDTO);
					}
				}			
			
			}catch (Exception e){
				logger.error("Error: LDAPUsuarioDao.consultarUsuarios "+e.getMessage(), e);
				throw new LoginAdminException("Error en LDAPUsuarioDao.consultarUsuarios ", e);
			}
		} catch (Failure e) {
			logger.error("Error: LDAPUsuarioDao.consultarUsuarios "+e.getMessage(), e);
			throw new LoginAdminException("Error en LDAPUsuarioDao.consultarUsuarios ", e);
		} 
		ldapConnection.returnAdminContext(dirContext);
	
		return usuarios;
	}
	
	
	public void actualizarPassword(String nombreUsuario, String oldPassword, String newPassword) throws LoginAdminException {
		String principal = "uid=" + nombreUsuario + "," + this.usersPath;
		DirContext userContext = null;
		try {
			userContext = ldapConnection.getUserContext(principal, oldPassword);
		} catch (NamingException e) {
			throw new LoginAdminException(Messenger.getInstance().getMsg("messages","msgInvalidCredential"), e);
		}

		try {
			ModificationItem[] mods = new ModificationItem[1];
			mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute(ELDAPAttribute.USER_PASSWORD.getLdapName(), newPassword));
			
			javax.naming.Name dn = new LdapName(principal);
			userContext.modifyAttributes(dn, mods);
		} catch (InvalidAttributeValueException e) {
			logger.warn("Nuevo password invalido ", e);
			throw new LoginAdminException(Messenger.getInstance().getMsg("messages","msgValidacionPassword"), e);
		} catch (InvalidNameException e) {
			throw new Failure("", "Atrubutos password mal definido en la aplicación ", e);
		} catch (NamingException e) {
			throw new Failure("", "No se pudo realizar la modificación de la clave", e);
		} finally{
			try {
				userContext.close();
			} catch (NamingException e) {
				logger.warn("No se pudo cerrar el contexto del usuario.",e);
			}
		}
		
		
    }
	
	public void actualizarPasswordByAdmin(String userLogin, String newPassword) throws LoginAdminException {
		String principal = "uid=" + userLogin + "," + this.usersPath;
		DirContext dirContext = ldapConnection.borrowAdminContext();
		
		try {
			ModificationItem[] mods = new ModificationItem[2];
			mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute(ELDAPAttribute.USER_PASSWORD.getLdapName(), newPassword));
			mods[1] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute(ELDAPAttribute.OPER_MUST_CHANGE_PWD.getLdapName(), "TRUE"));
			
			javax.naming.Name dn = new LdapName(principal);
			dirContext.modifyAttributes(dn, mods);

		} catch (InvalidNameException e) {
			throw new LoginAdminException("Atrubutos password mal definido en la aplicación ", e);
		} catch (NamingException e) {
			throw new LoginAdminException("No se pudo realizar la modificación de la clave", e);
		}
		
		ldapConnection.returnAdminContext(dirContext);
		
    }
	
	
	public UserEntity cargarDatosUsuario(Attributes attrs) {
		
		UserEntity userPrestadorDTO = null;
		List<TipoDocumentoView> listTipoDoc = null;
		Long tipoDocBusq = null;
		String tipoDocDesBusq = null;
		try {
			userPrestadorDTO = new UserEntity();
			listTipoDoc = this.listTipoDocs();
			String userLogin = null;
			int posPref;

			if (attrs.get(ELDAPAttribute.USERNAME.getLdapName()) != null) {
				userLogin = attrs.get(ELDAPAttribute.USERNAME.getLdapName()).get().toString();
				posPref = userLogin.lastIndexOf('_');
				if( posPref != -1 ) {
					posPref++;
					userPrestadorDTO.setUserLogin( userLogin.substring(posPref, userLogin.length()) );
					userPrestadorDTO.setUserId( userLogin.substring(posPref, userLogin.length()) );
				} else {
					userPrestadorDTO.setUserLogin(attrs.get(ELDAPAttribute.USERNAME.getLdapName()).get().toString());
					userPrestadorDTO.setUserId(attrs.get(ELDAPAttribute.USERNAME.getLdapName()).get().toString());
				}
			}
			
			if (attrs.get(ELDAPAttribute.IDENTIFICATION.getLdapName()) != null) {
				userPrestadorDTO.setUserDocument((attrs.get(ELDAPAttribute.IDENTIFICATION.getLdapName()).get().toString()));
			} else {
				userPrestadorDTO.setUserDocument("");
			}
			
			if (attrs.get(ELDAPAttribute.NAME.getLdapName()) != null) {
				userPrestadorDTO.setUserName(attrs.get(ELDAPAttribute.NAME.getLdapName()).get().toString());
			}
			
			if (attrs.get(ELDAPAttribute.SURNAME.getLdapName()) != null) {
				userPrestadorDTO.setUserLastName(attrs.get(ELDAPAttribute.SURNAME.getLdapName()).get().toString());			}

			
			if (attrs.get(ELDAPAttribute.EMAIL.getLdapName()) != null) {
				userPrestadorDTO.setUserMail(attrs.get(ELDAPAttribute.EMAIL.getLdapName()).get().toString());
			}
			
			if (attrs.get(ELDAPAttribute.USER_PASSWORD.getLdapName()) != null) {
				userPrestadorDTO.setUserPassword(attrs.get(ELDAPAttribute.USER_PASSWORD.getLdapName()).get().toString());
			}
			int posElem;
			if( userPrestadorDTO != null && userPrestadorDTO.getUserDocument() != null ){
				posElem = userPrestadorDTO.getUserDocument().indexOf('.');
				if( posElem != -1 ) {
					posElem++;
					forTipoDoc:
					for( TipoDocumentoView tipoDoc: listTipoDoc ){
						tipoDocBusq = Long.valueOf( userPrestadorDTO.getUserDocument().substring(0, posElem-1));
						if( tipoDoc.getId().longValue() == tipoDocBusq.longValue()  ){
							userPrestadorDTO.setTipoDoc(  String.valueOf( tipoDoc.getId() ) );
							tipoDocDesBusq = tipoDoc.getName();
							break forTipoDoc;
						}
					}
					userPrestadorDTO.setDocument( userPrestadorDTO.getUserDocument().substring(posElem, userPrestadorDTO.getUserDocument().length()) );
					userPrestadorDTO.setUserDocument( tipoDocDesBusq + userPrestadorDTO.getDocument() );
				}
			}
			
		} catch (Exception e) {
			logger.error("No es posible realizar la carga del usuario", e);
		}
		
		return userPrestadorDTO;
	}


	public UserResponseView consultarUsuarioLogin(String login) throws LoginAdminException {
		DirContext dirContext = ldapConnection.borrowAdminContext();
		UserResponseView userResponseDTO = new UserResponseView();
	
		try {

			String principal = "uid=" + login + "," + this.usersPath;
			javax.naming.Name dn = new LdapName(principal);

			Attributes attrs = dirContext.getAttributes(dn);
			UserEntity userEntity = cargarDatosUsuario(attrs);
			
			userResponseDTO.setUserDTO(userEntity);
			userResponseDTO.setResponseCode(EResponse.GETBASICDATA_OK.getCode()); 
			return userResponseDTO;

		} catch (InvalidNameException e) {
			userResponseDTO.setResponseCode(EResponse.GETBASICDATA_ERROR.getCode());
			e.printStackTrace();
		} catch (NamingException e) { // /
			e.printStackTrace();
			userResponseDTO.setResponseCode(EResponse.GETBASICDATA_ERROR.getCode());
		} catch (Exception e) {
			e.printStackTrace();
			userResponseDTO.setResponseCode(EResponse.GETBASICDATA_ERROR.getCode());
		}
	
		ldapConnection.returnAdminContext(dirContext);
		return userResponseDTO;
	}
	
	public List<TipoDocumentoView> listTipoDocs() throws LoginAdminException {
		List<TipoDocumentoView> listTipoDocs = null;
		TipoDocumentoView tipoDocumentoView = null;
		try {
			listTipoDocs = new ArrayList<TipoDocumentoView>();
			tipoDocumentoView = new TipoDocumentoView();
			tipoDocumentoView.setId( new Long(ETipoDocumento.CC.getCode()) );
			tipoDocumentoView.setName( ETipoDocumento.CC.getValor() );
			listTipoDocs.add( tipoDocumentoView );
			
			tipoDocumentoView = new TipoDocumentoView();
			tipoDocumentoView.setId( new Long(ETipoDocumento.CE.getCode()) );
			tipoDocumentoView.setName( ETipoDocumento.CE.getValor() );
			listTipoDocs.add( tipoDocumentoView );
			
			tipoDocumentoView = new TipoDocumentoView();
			tipoDocumentoView.setId( new Long(ETipoDocumento.NIT.getCode()) );
			tipoDocumentoView.setName( ETipoDocumento.NIT.getValor() );
			listTipoDocs.add( tipoDocumentoView );
			
			tipoDocumentoView = new TipoDocumentoView();
			tipoDocumentoView.setId( new Long(ETipoDocumento.PASAPORTE.getCode()) );
			tipoDocumentoView.setName( ETipoDocumento.PASAPORTE.getValor() );
			listTipoDocs.add( tipoDocumentoView );
			
		} catch (Exception e) {
			logger.error("Error: LDAPUsuarioDao.listTipoDocs "+e.getMessage(), e);
			throw new LoginAdminException("Error en LDAPUsuarioDao.listTipoDocs ", e);		
		}
		return listTipoDocs;
	}


	public List<UserEntity> consultarUsuariosPrest(List<String> usersLogin,
			Long cdPerson) throws LoginAdminException {
		// TODO Auto-generated method stub
		return null;
	}

	public UserEntity consultUser(String usersLogin) throws LoginAdminException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<UserEntity> listUser(String userName)
			throws LoginAdminException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<UserEntity> buscarUsuariosCriterios(UserEntity datosBusq)
			throws LoginAdminException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<UserEntity> buscarUsuariosPrest(UserEntity datosBusq,
			String userName) throws LoginAdminException {
		// TODO Auto-generated method stub
		return null;
	}

	public void desactivarUsuarioByIds(Set<String> listaUserIds) {
		DirContext dirContext = ldapConnection.borrowAdminContext();
		
		ModificationItem[] mods = 
			new ModificationItem[]{new ModificationItem(DirContext.REPLACE_ATTRIBUTE,  new BasicAttribute(ELDAPAttribute.OPER_LOCK_ACCOUNT.getLdapName(), lockAccountValue))};

		for (String id : listaUserIds){
			try {
				String principal = "uid=" + id + "," + this.usersPath;
				javax.naming.Name dn = new LdapName(principal);
				dirContext.modifyAttributes(dn, mods);
			} catch (Exception e) {
				logger.error("No se pude desactivar el usuario: " + id, e);
			}
		}

		ldapConnection.returnAdminContext(dirContext);
	}

	public void reactivarUsuarioByIds(List<String> listaUserIds){
		DirContext dirContext = ldapConnection.borrowAdminContext();
		
		ModificationItem[] mods = 
			new ModificationItem[]{new ModificationItem(DirContext.REMOVE_ATTRIBUTE,  new BasicAttribute(ELDAPAttribute.OPER_LOCK_ACCOUNT.getLdapName()))};

		for (String id : listaUserIds){
			try {
				String principal = "uid=" + id + "," + this.usersPath;
				javax.naming.Name dn = new LdapName(principal);
				dirContext.modifyAttributes(dn, mods);
			} catch (Exception e) {
				logger.error("No se pude desactivar el usuario: " + id, e);
			}
		}

		ldapConnection.returnAdminContext(dirContext);
	}
	
	
	public boolean isLocked(String userId) {
		boolean isLocked = false;
		Attributes operAttributes = ldapConnection.loadOperationalAttributes(userId, this.usersPath);
		
		if(operAttributes != null && operAttributes.get(ELDAPAttribute.OPER_LOCK_ACCOUNT.getLdapName()) != null){
			try {
				isLocked = operAttributes.get(ELDAPAttribute.OPER_LOCK_ACCOUNT.getLdapName()).get().toString().equals(lockAccountValue);
			} catch (NamingException e) {
				logger.error("Atributo de bloqueo mal definido");
			}
		}
		
		return isLocked;
	}

}
