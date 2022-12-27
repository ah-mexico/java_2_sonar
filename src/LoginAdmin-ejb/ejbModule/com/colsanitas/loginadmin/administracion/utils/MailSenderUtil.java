package com.colsanitas.loginadmin.administracion.utils;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.colsanitas.loginadmin.administracion.entity.RelacionEntity;
import com.colsanitas.loginadmin.administracion.entity.UserEntity;
import com.colsanitas.loginadmin.administracion.enums.ETipoRelacion;
import com.osi.gaudi.cfg.Configurator;
import com.osi.gaudi.msg.Messenger;
import com.osi.gaudi.notification.Recipient;
import com.osi.gaudi.notification.mail.MailNotification;
import com.osi.gaudi.notification.mail.MailNotificationSender;

/***
 * Clase ayundante para el envio de correos a los usuarios de la aplicacion.
 * @author armarquez
 *
 */
public class MailSenderUtil {
	
	private static Logger logger = LoggerFactory.getLogger(MailSenderUtil.class);
	
	/**
	 * Contructor de la clase MailSenderUtil
	 */
	public MailSenderUtil() {
		
	}

	/**
	 * Envia un correo utilizando el remitente por defecto de la aplicacion
	 * @param mail email con todos los datos salvo los del emisor.
	 * @return <b>false</b> si genera Exception, de lo contrario <b>true</b>
	 */
	private boolean sendEmail(MailNotification mail)  {
		try {
			String emailRemitente = (Configurator.getInstance().getString("administracion", ("email_account"), ""));
			String nombreRemitente = (Configurator.getInstance().getString("administracion", ("name_account"), ""));
			mail.setSender(emailRemitente, nombreRemitente);
			logger.info("MailNotificationSender - " + emailRemitente);
			MailNotificationSender sender = new MailNotificationSender();
			sender.send(mail);
			logger.info("MailNotificationSender finalizo correctamente");
			return true;
		} catch (Exception e) {
			logger.error("MailNotificationSender finalizo con excepcion", e);
			return false;
		}

	}	
	
	/**
	 * Metodo encargado de enviar el correo
	 * 
	 * @param asunto
	 * @param destinatario
	 * @param mensaje
	 * @return <b>true</b> si puede enviar el correo, de lo contrario <b>false</b>
	 */
	private boolean sendEmail(String asunto, String destinatario, String mensaje, Recipient recipient){
		boolean enviado;
		
		ServicioNotificacionElectronicaClient servicioNotificacionElectronicaClient = new ServicioNotificacionElectronicaClient();
		enviado = servicioNotificacionElectronicaClient.enviarMensaje(asunto, destinatario, mensaje);
		if(!enviado) {
			enviado = servicioNotificacionElectronicaClient.enviarMensaje(asunto, destinatario, mensaje);
			if(!enviado) {
				MailNotification mail = new MailNotification();
				mail.setSubject(asunto);
				mail.addRecipient(recipient);
				mail.setText(mensaje);
				enviado = sendEmail(mail);
			}
		}
		return enviado;
	}
	

	/**
	 * Configura el correo para un nuevo usuario del sistema y envia el correo.
	 * @param userEntity nuevo usuario del sistema.
	 * @return <b>true</b> si puede enviar el correo, de lo contrario <b>false</b>
	 */
	public boolean sendEmailNewUser(UserEntity userEntity) {
		String pwd = userEntity.getUserPassword();
		String login = userEntity.getUserId(); 
		Date pwdExpi = userEntity.getPwdData().getFechaExpiracion();

		String body = Messenger.getInstance().getMsg("notificaciones", "creacion_usuario_body", login, pwd, pwdExpi);
		String subject = Messenger.getInstance().getMsg("notificaciones", "creacion_usuario_subject");
		Recipient recipient = new Recipient(userEntity.getUserMail(), userEntity.getNombreCompleto());
		
		//Envio del correo
		return sendEmail(subject, userEntity.getUserMail(), body, recipient);
	}

	/**
	 * Configura el correo para la mofifacion de un usuario del sistema y envia el correo.
	 * @param userEntity nuevo usuario del sistema.
	 * @return <b>true</b> si puede enviar el correo, de lo contrario <b>false</b>
	 */
	public boolean sendEmailMofifyUser(UserEntity userEntity) {
		String subject = Messenger.getInstance().getMsg("notificaciones", "modificacion_datos_usuario_subject");
		String body = Messenger.getInstance().getMsg("notificaciones", "modificacion_datos_usuario_body", userEntity.getNombreCompleto(), userEntity.getUserDocument(), userEntity.getUserMail());
		Recipient recipient = new Recipient(userEntity.getUserMail(), userEntity.getNombreCompleto());
	
		//Envio del correo
		return sendEmail(subject, userEntity.getUserMail(), body, recipient);
	}

	/**
	 * Configura el correo para recordar la clave de un usuario del sistema y envia el correo.
	 * @param userEntity nuevo usuario del sistema.
	 * @return <b>true</b> si puede enviar el correo, de lo contrario <b>false</b>
	 */
	public boolean sendEmailRememberPassword(UserEntity userEntity) {
		String subject = Messenger.getInstance().getMsg("notificaciones","recordar_contrasena_subject");
		String body = Messenger.getInstance().getMsg("notificaciones","recordar_contrasena_body", userEntity.getUserName(), userEntity.getUserLogin(), userEntity.getUserPassword());
		Recipient recipient = new Recipient(userEntity.getUserMail(), userEntity.getNombreCompleto());
	
		//Envio del correo
		return sendEmail(subject, userEntity.getUserMail(), body, recipient);
	}
	
	/**
	 * Envia el email de autorizacion o desautorizacion.
	 * @param userEntity
	 * @return <b>true</b> si puede enviar el correo, de lo contrario <b>false</b>
	 */
	public boolean sendEmailChangeRelationship(RelacionEntity relacion) {
		String subjectMsg = ( relacion.getEstado() ?  "notificacion_autorizacion_subject" : "notificacion_desautorizacion_subject");
		String bodyMsg = ( relacion.getEstado() ?  "notificacion_autorizacion_body" : "notificacion_desautorizacion_body");

		String prestador = relacion.getPrestador().getRazonSocial();
		String nombreRelacion = ETipoRelacion.fromInt(relacion.getTipoRelacion()).toString().toLowerCase();

		String subject = Messenger.getInstance().getMsg("notificaciones",subjectMsg, nombreRelacion);
		String body = Messenger.getInstance().getMsg("notificaciones",bodyMsg, nombreRelacion, prestador);

		Recipient recipient = new Recipient(relacion.getUsuario().getUserMail(), relacion.getUsuario().getUserName());

		//Envio del correo
		return sendEmail(subject, relacion.getUsuario().getUserMail(), body, recipient);
	}

}
