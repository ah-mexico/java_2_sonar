package com.colsanitas.loginadmin.administracion.view;

import java.util.List;

import com.colsanitas.loginadmin.administracion.entity.UserEntity;
import com.osi.gaudi.notification.Recipient;
import com.osi.gaudi.notification.mail.MailNotification;
import com.osi.gaudi.notification.mail.MailNotificationSender;

public class MensajeView {

	/** Asunto del mensaje */
	private String subject;
	/** Direcciones de destino */
	private List<Recipient> recipients;
	/** Cuerpo del mensaje */
	private String body;
	
	/**
	 * Direcciones para copia a receptores
	 */
	private List<Recipient> copiaRecipients;
	
	
	
	public MensajeView(String subject, List<Recipient> recipients, String body) {
		super();
		this.subject = subject;
		this.recipients = recipients;
		this.body = body;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public List<Recipient> getRecipients() {
		return recipients;
	}

	public void setRecipients(List<Recipient> recipients) {
		this.recipients = recipients;
	}

	public List<Recipient> getCopiaRecipients() {
		return copiaRecipients;
	}

	public void setCopiaRecipients(List<Recipient> copiaRecipients) {
		this.copiaRecipients = copiaRecipients;
	}

	
}
