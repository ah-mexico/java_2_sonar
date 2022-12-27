package com.colsanitas.loginadmin.administracion.utils;


import java.net.URL;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.jboss.seam.annotations.Name;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.osi.gaudi.cfg.Configurator;
import com.osi.notificacionelectronica.service.Email;
import com.osi.notificacionelectronica.service.EnviarEmailOUT;
import com.osi.notificacionelectronica.service.NotificacionElectronicaLocator;

/**
 * @author yeserrano-Softmanagement
 * @date 12/08/2014
 * Componente que es utilizado como Proxy del servicio de notificación electrónica para enviar los correos electrónicos
 * que son generados por el sistema
 */
@Name("servicioNotificacionElectronicaClient")
public class ServicioNotificacionElectronicaClient {

	private static Logger logger = LoggerFactory.getLogger(ServicioNotificacionElectronicaClient.class);

	/**
	 * Contructor de la clase ServicioNotificacionElectronicaClient
	 */
	public ServicioNotificacionElectronicaClient() {

	}

	/**
	 * Envia el mensaje solicitado, a traves del servicio de notificacion electronica.
	 * @param asunto
	 * @param destinatario
	 * @param mensaje
	 * @return <b>true</b> Exitoso <b>false</b> Ocurre un error en el envio del correo.
	 */
	public boolean enviarMensaje(String asunto, String destinatario, String mensaje) 
	{
		boolean rta = false;
		try
		{
			String urlString = Configurator.getInstance().getString("administracion","wsdlNotificacion","http://172.18.46.58:8283/services/NotificacionElectronica?wsdl");
			String aplicacion =  Configurator.getInstance().getString("administracion","nombreAplicacion" , "LoginAdmin");
			String certiMail = Configurator.getInstance().getString("administracion","Certimail" , "CNC"); 
			URL portAddress = new URL(urlString);
			Email email = new Email();
			email.setAsunto(asunto);
			//Se setea el tipo del certificado
			email.setCertimail(certiMail);
			email.setDestinatario(destinatario);
			email.setMailConfirmacion(destinatario);
			email.setMensaje(mensaje);
			logger.info("enviarMensaje() - " + urlString);
			NotificacionElectronicaLocator serviceNotificacion = new NotificacionElectronicaLocator();
			EnviarEmailOUT outMail = serviceNotificacion.getNotificacionElectronicaHttpsSoap12Endpoint(portAddress).enviarEmailAutoAprobado(aplicacion, email);
			if(outMail.getCodigoRespuesta() == 0)
			{
				logger.info("enviarMensaje() finalizo correctamente - idProceso="+outMail.getIdProceso());
				rta = true;
			}
			else
			{
				logger.error("enviarMensaje() finalizo error: "+outMail.getCodigoRespuesta()+" - "+outMail.getMensajeRespuesta() );
			}

		}
		catch (ServiceException e)
		{
			logger.error("enviarMensaje() finalizo con ServiceException", e);
		}
		catch (RemoteException e)
		{
			logger.error("enviarMensaje() finalizo con RemoteException", e);
		}
		catch (Exception e)
		{
			logger.error("enviarMensaje() finalizo con excepcion", e);
		}
		return rta;
	}
}