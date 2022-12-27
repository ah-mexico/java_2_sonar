/**
 * NotificacionElectronicaSoap12BindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.osi.notificacionelectronica.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.osi.gaudi.cfg.Configurator;

public class NotificacionElectronicaSoap12BindingStub extends org.apache.axis.client.Stub implements com.osi.notificacionelectronica.service.NotificacionElectronicaPortType {
	private java.util.Vector cachedSerClasses = new java.util.Vector();
	private java.util.Vector cachedSerQNames = new java.util.Vector();
	private java.util.Vector cachedSerFactories = new java.util.Vector();
	private java.util.Vector cachedDeserFactories = new java.util.Vector();

	private static Logger logger = LoggerFactory.getLogger(NotificacionElectronicaSoap12BindingStub.class);
	/** Variable Creada para Manejar el TimeOut de mensajes a WS
	 */
	private static Integer timeoutMensajeWS;
	private static final int FACTOR_CONVERSION_A_MILISEGUNDOS = 1000;
	private static final Integer TIMEOUT_DEFAULT = new Integer(5000);

	static org.apache.axis.description.OperationDesc [] _operations;

	static {
		_operations = new org.apache.axis.description.OperationDesc[7];
		_initOperationDesc1();
        try {
			int timeoutAux = Integer.parseInt(Configurator.getInstance().getString("administracion", ("email_timeout"), "5")) * FACTOR_CONVERSION_A_MILISEGUNDOS;
			timeoutMensajeWS = new Integer(timeoutAux);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			timeoutMensajeWS = TIMEOUT_DEFAULT;
		}
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("aprobarEnviosSMS");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "aplicacion"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "aprobarEnvioSMSIN"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://service.notificacionelectronica.osi.com/", "aprobacionEnvioSMS"), com.osi.notificacionelectronica.service.AprobacionEnvioSMS.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://service.notificacionelectronica.osi.com/", "aprobarEnvioSMSOUT"));
        oper.setReturnClass(com.osi.notificacionelectronica.service.AprobarEnvioSMSOUT.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "aprobarEnvioSMSOUT"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("enviarNotificacionDispositivoInteligente");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "aplicacion"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "notificacionDispositivoInteligenteIN"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://service.notificacionelectronica.osi.com/", "notificacionDispositivoInteligente"), com.osi.notificacionelectronica.service.NotificacionDispositivoInteligente.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://service.notificacionelectronica.osi.com/", "enviarNotificacionDispositivoInteligenteOUT"));
        oper.setReturnClass(com.osi.notificacionelectronica.service.EnviarNotificacionDispositivoInteligenteOUT.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "enviarNotificacionDispositivoInteligenteOUT"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("enviarSMS");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "aplicacion"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "smsIN"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://service.notificacionelectronica.osi.com/", "sms"), com.osi.notificacionelectronica.service.Sms.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://service.notificacionelectronica.osi.com/", "enviarSMSlOUT"));
        oper.setReturnClass(com.osi.notificacionelectronica.service.EnviarSMSlOUT.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "enviarSMSlOUT"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("enviarEmail");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "aplicacion"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "emailIN"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://service.notificacionelectronica.osi.com/", "email"), com.osi.notificacionelectronica.service.Email.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://service.notificacionelectronica.osi.com/", "enviarEmailOUT"));
        oper.setReturnClass(com.osi.notificacionelectronica.service.EnviarEmailOUT.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "enviarEmailOUT"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("aprobarEnvioMail");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "aplicacion"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "aprobarEnvioEMailIN"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://service.notificacionelectronica.osi.com/", "aprobarEnvioEMail"), com.osi.notificacionelectronica.service.AprobarEnvioEMail.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://service.notificacionelectronica.osi.com/", "aprobarEnvioEMailOUT"));
        oper.setReturnClass(com.osi.notificacionelectronica.service.AprobarEnvioEMailOUT.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "aprobarEnvioEMailOUT"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("enviarEmailAutoAprobado");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "aplicacion"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "emailIN"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://service.notificacionelectronica.osi.com/", "email"), com.osi.notificacionelectronica.service.Email.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://service.notificacionelectronica.osi.com/", "enviarEmailOUT"));
        oper.setReturnClass(com.osi.notificacionelectronica.service.EnviarEmailOUT.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "enviarEmailOUT"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("aprobarEnvioNotificacionDispositivoInteligente");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "aplicacion"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "aprobacionEnvioNotificacionDispositivoInteligenteIN"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://service.notificacionelectronica.osi.com/", "aprobacionEnvioNotificacionDispositivoInteligente"), com.osi.notificacionelectronica.service.AprobacionEnvioNotificacionDispositivoInteligente.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://service.notificacionelectronica.osi.com/", "aprobarEnvioNotificacionDispositivoInteligenteOUT"));
        oper.setReturnClass(com.osi.notificacionelectronica.service.AprobarEnvioNotificacionDispositivoInteligenteOUT.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "aprobarEnvioNotificacionDispositivoInteligenteOUT"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[6] = oper;

    }

    public NotificacionElectronicaSoap12BindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public NotificacionElectronicaSoap12BindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public NotificacionElectronicaSoap12BindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://service.notificacionelectronica.osi.com/", "adjuntoEmail");
            cachedSerQNames.add(qName);
            cls = com.osi.notificacionelectronica.service.AdjuntoEmail.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.notificacionelectronica.osi.com/", "aprobacionEnvioNotificacionDispositivoInteligente");
            cachedSerQNames.add(qName);
            cls = com.osi.notificacionelectronica.service.AprobacionEnvioNotificacionDispositivoInteligente.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.notificacionelectronica.osi.com/", "aprobacionEnvioSMS");
            cachedSerQNames.add(qName);
            cls = com.osi.notificacionelectronica.service.AprobacionEnvioSMS.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.notificacionelectronica.osi.com/", "aprobarEnvioEMail");
            cachedSerQNames.add(qName);
            cls = com.osi.notificacionelectronica.service.AprobarEnvioEMail.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.notificacionelectronica.osi.com/", "aprobarEnvioEMailOUT");
            cachedSerQNames.add(qName);
            cls = com.osi.notificacionelectronica.service.AprobarEnvioEMailOUT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.notificacionelectronica.osi.com/", "aprobarEnvioNotificacionDispositivoInteligenteOUT");
            cachedSerQNames.add(qName);
            cls = com.osi.notificacionelectronica.service.AprobarEnvioNotificacionDispositivoInteligenteOUT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.notificacionelectronica.osi.com/", "aprobarEnvioSMSOUT");
            cachedSerQNames.add(qName);
            cls = com.osi.notificacionelectronica.service.AprobarEnvioSMSOUT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.notificacionelectronica.osi.com/", "email");
            cachedSerQNames.add(qName);
            cls = com.osi.notificacionelectronica.service.Email.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.notificacionelectronica.osi.com/", "enviarEmailOUT");
            cachedSerQNames.add(qName);
            cls = com.osi.notificacionelectronica.service.EnviarEmailOUT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.notificacionelectronica.osi.com/", "enviarNotificacionDispositivoInteligenteOUT");
            cachedSerQNames.add(qName);
            cls = com.osi.notificacionelectronica.service.EnviarNotificacionDispositivoInteligenteOUT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.notificacionelectronica.osi.com/", "enviarSMSlOUT");
            cachedSerQNames.add(qName);
            cls = com.osi.notificacionelectronica.service.EnviarSMSlOUT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.notificacionelectronica.osi.com/", "imagenMovil");
            cachedSerQNames.add(qName);
            cls = com.osi.notificacionelectronica.service.ImagenMovil.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.notificacionelectronica.osi.com/", "notificacionDispositivoInteligente");
            cachedSerQNames.add(qName);
            cls = com.osi.notificacionelectronica.service.NotificacionDispositivoInteligente.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.notificacionelectronica.osi.com/", "respuestaNotificacionElectronica");
            cachedSerQNames.add(qName);
            cls = com.osi.notificacionelectronica.service.RespuestaNotificacionElectronica.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://service.notificacionelectronica.osi.com/", "sms");
            cachedSerQNames.add(qName);
            cls = com.osi.notificacionelectronica.service.Sms.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public com.osi.notificacionelectronica.service.AprobarEnvioSMSOUT aprobarEnviosSMS(java.lang.String aplicacion, com.osi.notificacionelectronica.service.AprobacionEnvioSMS aprobarEnvioSMSIN) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP12_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://service.notificacionelectronica.osi.com/", "aprobarEnviosSMS"));
        _call.setTimeout(timeoutMensajeWS);

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {aplicacion, aprobarEnvioSMSIN});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.osi.notificacionelectronica.service.AprobarEnvioSMSOUT) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.osi.notificacionelectronica.service.AprobarEnvioSMSOUT) org.apache.axis.utils.JavaUtils.convert(_resp, com.osi.notificacionelectronica.service.AprobarEnvioSMSOUT.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.osi.notificacionelectronica.service.EnviarNotificacionDispositivoInteligenteOUT enviarNotificacionDispositivoInteligente(java.lang.String aplicacion, com.osi.notificacionelectronica.service.NotificacionDispositivoInteligente notificacionDispositivoInteligenteIN) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP12_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://service.notificacionelectronica.osi.com/", "enviarNotificacionDispositivoInteligente"));
        _call.setTimeout(timeoutMensajeWS);

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {aplicacion, notificacionDispositivoInteligenteIN});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.osi.notificacionelectronica.service.EnviarNotificacionDispositivoInteligenteOUT) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.osi.notificacionelectronica.service.EnviarNotificacionDispositivoInteligenteOUT) org.apache.axis.utils.JavaUtils.convert(_resp, com.osi.notificacionelectronica.service.EnviarNotificacionDispositivoInteligenteOUT.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.osi.notificacionelectronica.service.EnviarSMSlOUT enviarSMS(java.lang.String aplicacion, com.osi.notificacionelectronica.service.Sms smsIN) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP12_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://service.notificacionelectronica.osi.com/", "enviarSMS"));
        _call.setTimeout(timeoutMensajeWS);

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {aplicacion, smsIN});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.osi.notificacionelectronica.service.EnviarSMSlOUT) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.osi.notificacionelectronica.service.EnviarSMSlOUT) org.apache.axis.utils.JavaUtils.convert(_resp, com.osi.notificacionelectronica.service.EnviarSMSlOUT.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.osi.notificacionelectronica.service.EnviarEmailOUT enviarEmail(java.lang.String aplicacion, com.osi.notificacionelectronica.service.Email emailIN) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP12_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://service.notificacionelectronica.osi.com/", "enviarEmail"));
        _call.setTimeout(timeoutMensajeWS);

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {aplicacion, emailIN});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.osi.notificacionelectronica.service.EnviarEmailOUT) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.osi.notificacionelectronica.service.EnviarEmailOUT) org.apache.axis.utils.JavaUtils.convert(_resp, com.osi.notificacionelectronica.service.EnviarEmailOUT.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.osi.notificacionelectronica.service.AprobarEnvioEMailOUT aprobarEnvioMail(java.lang.String aplicacion, com.osi.notificacionelectronica.service.AprobarEnvioEMail aprobarEnvioEMailIN) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP12_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://service.notificacionelectronica.osi.com/", "aprobarEnvioMail"));
        _call.setTimeout(timeoutMensajeWS);

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {aplicacion, aprobarEnvioEMailIN});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.osi.notificacionelectronica.service.AprobarEnvioEMailOUT) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.osi.notificacionelectronica.service.AprobarEnvioEMailOUT) org.apache.axis.utils.JavaUtils.convert(_resp, com.osi.notificacionelectronica.service.AprobarEnvioEMailOUT.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.osi.notificacionelectronica.service.EnviarEmailOUT enviarEmailAutoAprobado(java.lang.String aplicacion, com.osi.notificacionelectronica.service.Email emailIN) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP12_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://service.notificacionelectronica.osi.com/", "enviarEmailAutoAprobado"));
        _call.setTimeout(timeoutMensajeWS);

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {aplicacion, emailIN});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.osi.notificacionelectronica.service.EnviarEmailOUT) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.osi.notificacionelectronica.service.EnviarEmailOUT) org.apache.axis.utils.JavaUtils.convert(_resp, com.osi.notificacionelectronica.service.EnviarEmailOUT.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.osi.notificacionelectronica.service.AprobarEnvioNotificacionDispositivoInteligenteOUT aprobarEnvioNotificacionDispositivoInteligente(java.lang.String aplicacion, com.osi.notificacionelectronica.service.AprobacionEnvioNotificacionDispositivoInteligente aprobacionEnvioNotificacionDispositivoInteligenteIN) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP12_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://service.notificacionelectronica.osi.com/", "aprobarEnvioNotificacionDispositivoInteligente"));
        _call.setTimeout(timeoutMensajeWS);

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {aplicacion, aprobacionEnvioNotificacionDispositivoInteligenteIN});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.osi.notificacionelectronica.service.AprobarEnvioNotificacionDispositivoInteligenteOUT) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.osi.notificacionelectronica.service.AprobarEnvioNotificacionDispositivoInteligenteOUT) org.apache.axis.utils.JavaUtils.convert(_resp, com.osi.notificacionelectronica.service.AprobarEnvioNotificacionDispositivoInteligenteOUT.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
