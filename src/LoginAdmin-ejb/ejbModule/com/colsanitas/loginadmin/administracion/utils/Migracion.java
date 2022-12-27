package com.colsanitas.loginadmin.administracion.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.colsanitas.loginadmin.administracion.entity.UserEntity;
import com.colsanitas.loginadmin.administracion.ldap.dao.LDAPUsuarioDao;
import com.colsanitas.loginadmin.administracion.view.MensajeView;
import com.colsanitas.loginadmin.administracion.view.UserResponseView;
import com.osi.gaudi.cfg.Configurator;
import com.osi.gaudi.exception.Failure;
import com.osi.gaudi.notification.Recipient;

public class Migracion {

	/**
	 * Migra datos de personas externas a LDAP (Rama externos) 
	 * @param args
	 * @throws SQLException
	 */
	public static void main(String args[]) throws SQLException {

//		System.out.println(EncryptingUtil.encrypt(
//				"miclave1",
//				EEncryptingAlgorithm.AES));
//		
//		System.out.println(EncryptingUtil.encrypt(
//				"yaaperez.Prest",
//				EEncryptingAlgorithm.AES));
			String login = "";	
			int cantidadMigrados =0;
			int cantidadFallidos =0;
			int cantidadMigradosSinCorreo =0;
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

			Connection conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@muisca.colsanitas.com:1521:csbd04",
					"usbd04", "colombia");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt
					.executeQuery("select aut.nick, aut.clave, p.PRIMER_NOMBRE,p.SEGUNDO_NOMBRE,p.PRIMER_APELLIDO,p.SEGUNDO_APELLIDO " +
							",p.CDIDEPER documento , t. bdua TIPO_DOC " +
							"from tbup_persona  p, tbup_info_autentic aut, tbup_tipo_identificacion t " +
							"where p.codigo = aut.pers_codigo and " +
							"p.tide_codigo = t.codigo");

			UserEntity userEntity = new UserEntity();
			System.out.println("****** INFORME **********");
			while (rs.next()) {
				
				String primerNombre = rs.getString("PRIMER_NOMBRE");
				String segundoNombre = rs.getString("SEGUNDO_NOMBRE");
				String primerApellido = rs.getString("PRIMER_APELLIDO");
				String segundoApellido = rs.getString("SEGUNDO_APELLIDO");
				login = Migracion.conformarNombreUsuario(2, primerNombre,
						segundoNombre, primerApellido)
						+ Configurator.getInstance().getString("administracion",
								"prefijo", ".Pres");
				userEntity.setUserDocument(rs.getString("TIPO_DOC")+rs.getString("documento"));
				userEntity.setUserId(login);
				userEntity.setUserLogin(login);
				userEntity.setUserMail(rs.getString("nick"));
				userEntity.setUserPassword(rs.getString("clave"));
				userEntity.setUserName(primerNombre + " " + segundoNombre + " "
						+ primerApellido + " " + segundoApellido);
				System.out.println("--Migrando usuario.....: "+userEntity.getUserLogin());
				LDAPUsuarioDao l = new LDAPUsuarioDao();
			}
				//l.registrarUsuarioEntity(userEntity, false);
				
//				try{
////					String body = Utilidades.conformarMensaje(userResponseView.getUserDTO());
////					List<Recipient> receptores = new ArrayList<Recipient>();
////					receptores.add(new Recipient(userResponseView.getUserDTO().getUserMail(), userResponseView.getUserDTO().getUserName()));
////					MensajeView mensajeDTO = new MensajeView("Nueva cuenta", receptores,body);
////					Utilidades.enviarEmail(mensajeDTO);// enviar atributos
////					System.out.println("--Usuario migrado satisfactoriamente : "+userEntity.getUserLogin());
//
//					cantidadMigrados++;
//				}catch (Failure e)
//				{
//					cantidadMigradosSinCorreo++;
//					userResponseView.setMessage(EResponse.EMAIL_NO_SEND.getMessage());
//					userResponseView.setResponseCode(EResponse.EMAIL_NO_SEND.getCode());
//					System.err.println(userResponseView.getMessage());
//				}
//				}else
//				{
//			      System.err.println(userResponseView.getMessage());
//			      cantidadFallidos++;
//				}
//			}
//
//			System.out.println("*CANTIDAD MIGRADOS: ="+(cantidadMigrados + cantidadMigradosSinCorreo)+"**********");
//			System.out.println("*CANTIDAD FALLIDOS: ="+cantidadFallidos+"**********");
//			System.out.println("*CANTIDAD CORRREOS NO ENVIADOS: ="+cantidadMigradosSinCorreo+"**********");
//			
//			System.out.println("****** FIN INFORME **********");
//			stmt.close();
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
	}

	/**
	 * Obtiene la cadena indicada para el nombre de usuario de acuerdo al
	 * n&uacute;mero de caracteres, tomando primeras letras del primer nombre,
	 * primera letra del segundo nombre si existe, más el apellido: avargas,
	 * advargas, adrvargas, etc.
	 * 
	 * @param numLetras
	 *            int, n&uacute;mero de letras del nombre a incluir.
	 * @param primerNombre
	 * @param segundoNombre
	 * @param primerApellido
	 * @return String, nuevo nombre de usuario.
	 */
	private static String conformarNombreUsuario(int numLetras,
			String primerNombre, String segundoNombre, String primerApellido) {
		// Conforma nombre de usuario
		
		primerNombre=EncodingUtils.reemplazarCaracteres(primerNombre);
		primerApellido = EncodingUtils.reemplazarCaracteres(primerApellido);
		String nombreUsuario = "";
		if (numLetras > primerNombre.length()) {
			numLetras = numLetras - primerNombre.length();
			if (segundoNombre.length() >= numLetras + 1) {
				nombreUsuario = primerNombre
						+ segundoNombre.substring(0, numLetras + 1)
						+ primerApellido;
			} else {
				nombreUsuario = primerNombre + segundoNombre + primerApellido;
			}
		} else {
			if (!StringUtils.isEmpty(segundoNombre)) {
				nombreUsuario = primerNombre.substring(0, numLetras)
						+ segundoNombre.substring(0, 1) + primerApellido;
			} else {
				nombreUsuario = primerNombre.substring(0, numLetras)
						+ primerApellido;
			}
		}
		return nombreUsuario;
	}

}
