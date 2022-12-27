package com.colsanitas.loginadmin.administracion.utils;



import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.colsanitas.loginadmin.administracion.entity.UserEntity;
import com.colsanitas.loginadmin.administracion.view.MensajeView;
import com.osi.gaudi.cfg.Configurator;
import com.osi.gaudi.exception.Failure;
import com.osi.gaudi.msg.Messenger;
import com.osi.gaudi.notification.mail.MailNotification;
import com.osi.gaudi.notification.mail.MailNotificationSender;
import com.sun.org.apache.regexp.internal.StringCharacterIterator;

public class Utilidades {
	
	private static Logger logger = LoggerFactory.getLogger(Utilidades.class);
	private static final String sufijo = Configurator.getInstance().getString("administracion", "prefijo", ".Pres");
	private static int START = 1000;
	private static int END = 9999;
	
	public static void enviarEmail(MensajeView mensajeDTO) throws Failure {
		try{
		MailNotification mail = new MailNotification ();
		
		String emailRemitente = (Configurator.getInstance().getString("administracion",("email_account"),""));
		String nombreRemitente = (Configurator.getInstance().getString("administracion",("name_account"),""));	
		mail.setSender(emailRemitente,nombreRemitente);
		mail.setSubject(mensajeDTO.getSubject());
		mail.setText(mensajeDTO.getBody());
		mail.addRecipient(mensajeDTO.getRecipients().get(0));
		//mail.addCC(new Recipient ("receptorCC@serv.com", "NombreCC"));
	
		MailNotificationSender sender = new MailNotificationSender ();
		sender.send(mail);
		}catch (Failure e){
			logger.error("Error: Utilidades.enviarEmail  "+e.getMessage(), e);
			throw e;
		}
		
	}
	
	/**
	 * Metodo que permite validar la estructura del password
	 * @param password string que contien el password para verificacion
	 * @return String vacio si el password contiene estructura correcta. contrario mensaje de error
	 * */	
	public static String  validatePassword(String login, String password) {
	    int sizeKey = Configurator.getInstance().getInt("administracion","sizeKey",8);
	    
	    String loginSinSufijo = login.substring(0, login.length() - sufijo.length());
		boolean pwdHasContent = (password != null) && !password.isEmpty() && password.length() >= sizeKey && (password.indexOf(loginSinSufijo) == -1);
		
		String message = "";
		if (!pwdHasContent) {
			message = Messenger.getInstance().getMsg("messages","msgValidacionPassword");			
		} else {
			StringCharacterIterator iterator = new StringCharacterIterator(password);
			int countDig = 0;
			int countLet = 0;
			int countLetMay = 0;
			int countLetMin = 0;
			char character = iterator.charAt(0);
			for (int i = 0;i<password.length(); i++) {
				character = iterator.charAt(i);
				boolean isValidChar = (Character.isLetter(character));
				boolean isValidInt = (Character.isDigit(character));
				boolean isValidCharMay = (Character.isUpperCase(character));
				boolean isValidCharMin = (Character.isLowerCase(character));
				
				if (isValidChar) countLet++;
				if (isValidInt)  countDig++;
				if (isValidCharMay)  countLetMay++;
				if (isValidCharMin)  countLetMin++;
			}		
			if (countDig == 0 || countLet == 0 || countLetMay == 0 || countLetMin == 0){
				message = Messenger.getInstance().getMsg("messages","msgValidacionPassword");			
			} 
					
		}
		return message;
	}
	
	 public static boolean validateNotEmpty(String str){
	    	return (str != null) && (!str.isEmpty());
	    }
	

	 /**
		 * Metodo que permite genera password
		 * @param value string que contiene una cadena inicial
		 * @return String password generado segun algoritmo
		 * */
	public static String generatePassword(String value){
			
					
			boolean content = (value != null) && (!value.equals(""))&&value.length()>=4;
			if (!content) {
				throw new IllegalArgumentException("la cadena requerida para generar el password debe conetener 4 caracteres");			
			}
			String subStr= value.substring(0,4);
			return subStr+generarIntegerRandom(START,END);
		}
		/**
		 * Metodo que permite generar u numero aleatorio dentro de un rango de datos
		 * @param  aStart numero inicial
		 * @param  aEnd numero final
		 * @return int  entero con el numero aleatorio
		 * */
		private static int generarIntegerRandom(int aStart, int aEnd){
			Random aRandom=new Random();
		    if ( aStart > aEnd ) {
		      throw new IllegalArgumentException("Numero inicial no puede ser mayor al final.");
		    }
		    long range = (long)aEnd - (long)aStart + 1;
		    long fraction = (long)(range * aRandom.nextDouble());
		    int randomNumber =  (int)(fraction + aStart);    
		   return randomNumber;
		  }
		
		/**
		 * Suma el n�mero de d�as especificados a la fecha indicada.
		 * @param fecha Date
		 * @param dias int, n�mero de d�as a sumar.
		 * @return Date, nueva fecha
		 */
		public static Date sumarDias(Date fecha, int dias) {
	        Calendar c = Calendar.getInstance();
	        c.setTime(fecha);
	        c.add(Calendar.DAY_OF_MONTH, dias);
	        Date nuevaFecha = c.getTime();
	        return nuevaFecha;
	    }
		
		
		public static String conformarMensajeActInfoUser(UserEntity userEntity) {
			 StringBuilder body = null; 
			 try {
				body = new StringBuilder();
				body.append((Configurator.getInstance().getString("administracion","msgSaludosEmail","")));
				body.append(userEntity.getUserName());
				body.append((Configurator.getInstance().getString("administracion","msgNotificacionActualizacionInfo","")));
			 } catch (Exception e) {
				 logger.error("Error: Utilidades.conformarMensajeActInfoUser  "+e.getMessage(), e);
			 }
			 return body.toString();
		 }

}
