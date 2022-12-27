/**
 * NotificacionElectronicaPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.osi.notificacionelectronica.service;

public interface NotificacionElectronicaPortType extends java.rmi.Remote {
    public com.osi.notificacionelectronica.service.AprobarEnvioSMSOUT aprobarEnviosSMS(java.lang.String aplicacion, com.osi.notificacionelectronica.service.AprobacionEnvioSMS aprobarEnvioSMSIN) throws java.rmi.RemoteException;
    public com.osi.notificacionelectronica.service.EnviarNotificacionDispositivoInteligenteOUT enviarNotificacionDispositivoInteligente(java.lang.String aplicacion, com.osi.notificacionelectronica.service.NotificacionDispositivoInteligente notificacionDispositivoInteligenteIN) throws java.rmi.RemoteException;
    public com.osi.notificacionelectronica.service.EnviarSMSlOUT enviarSMS(java.lang.String aplicacion, com.osi.notificacionelectronica.service.Sms smsIN) throws java.rmi.RemoteException;
    public com.osi.notificacionelectronica.service.EnviarEmailOUT enviarEmail(java.lang.String aplicacion, com.osi.notificacionelectronica.service.Email emailIN) throws java.rmi.RemoteException;
    public com.osi.notificacionelectronica.service.EnviarEmailOUT enviarEmailAutoAprobado(java.lang.String aplicacion, com.osi.notificacionelectronica.service.Email emailIN) throws java.rmi.RemoteException;
    public com.osi.notificacionelectronica.service.AprobarEnvioEMailOUT aprobarEnvioMail(java.lang.String aplicacion, com.osi.notificacionelectronica.service.AprobarEnvioEMail aprobarEnvioEMailIN) throws java.rmi.RemoteException;
    public com.osi.notificacionelectronica.service.AprobarEnvioNotificacionDispositivoInteligenteOUT aprobarEnvioNotificacionDispositivoInteligente(java.lang.String aplicacion, com.osi.notificacionelectronica.service.AprobacionEnvioNotificacionDispositivoInteligente aprobacionEnvioNotificacionDispositivoInteligenteIN) throws java.rmi.RemoteException;
}
