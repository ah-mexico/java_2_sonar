/**
 * NotificacionElectronicaLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.osi.notificacionelectronica.service;

public class NotificacionElectronicaLocator extends org.apache.axis.client.Service implements com.osi.notificacionelectronica.service.NotificacionElectronica {

    public NotificacionElectronicaLocator() {
    }


    public NotificacionElectronicaLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public NotificacionElectronicaLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for NotificacionElectronicaHttpSoap12Endpoint
    private java.lang.String NotificacionElectronicaHttpSoap12Endpoint_address = "http://wso2pruebas.colsanitas.com:8283/services/NotificacionElectronica.NotificacionElectronicaHttpSoap12Endpoint";

    public java.lang.String getNotificacionElectronicaHttpSoap12EndpointAddress() {
        return NotificacionElectronicaHttpSoap12Endpoint_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String NotificacionElectronicaHttpSoap12EndpointWSDDServiceName = "NotificacionElectronicaHttpSoap12Endpoint";

    public java.lang.String getNotificacionElectronicaHttpSoap12EndpointWSDDServiceName() {
        return NotificacionElectronicaHttpSoap12EndpointWSDDServiceName;
    }

    public void setNotificacionElectronicaHttpSoap12EndpointWSDDServiceName(java.lang.String name) {
        NotificacionElectronicaHttpSoap12EndpointWSDDServiceName = name;
    }

    public com.osi.notificacionelectronica.service.NotificacionElectronicaPortType getNotificacionElectronicaHttpSoap12Endpoint() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(NotificacionElectronicaHttpSoap12Endpoint_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getNotificacionElectronicaHttpSoap12Endpoint(endpoint);
    }

    public com.osi.notificacionelectronica.service.NotificacionElectronicaPortType getNotificacionElectronicaHttpSoap12Endpoint(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.osi.notificacionelectronica.service.NotificacionElectronicaSoap12BindingStub _stub = new com.osi.notificacionelectronica.service.NotificacionElectronicaSoap12BindingStub(portAddress, this);
            _stub.setPortName(getNotificacionElectronicaHttpSoap12EndpointWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setNotificacionElectronicaHttpSoap12EndpointEndpointAddress(java.lang.String address) {
        NotificacionElectronicaHttpSoap12Endpoint_address = address;
    }


    // Use to get a proxy class for NotificacionElectronicaHttpsSoap11Endpoint
    private java.lang.String NotificacionElectronicaHttpsSoap11Endpoint_address = "https://wso2pruebas.colsanitas.com:8246/services/NotificacionElectronica.NotificacionElectronicaHttpsSoap11Endpoint";

    public java.lang.String getNotificacionElectronicaHttpsSoap11EndpointAddress() {
        return NotificacionElectronicaHttpsSoap11Endpoint_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String NotificacionElectronicaHttpsSoap11EndpointWSDDServiceName = "NotificacionElectronicaHttpsSoap11Endpoint";

    public java.lang.String getNotificacionElectronicaHttpsSoap11EndpointWSDDServiceName() {
        return NotificacionElectronicaHttpsSoap11EndpointWSDDServiceName;
    }

    public void setNotificacionElectronicaHttpsSoap11EndpointWSDDServiceName(java.lang.String name) {
        NotificacionElectronicaHttpsSoap11EndpointWSDDServiceName = name;
    }

    public com.osi.notificacionelectronica.service.NotificacionElectronicaPortType getNotificacionElectronicaHttpsSoap11Endpoint() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(NotificacionElectronicaHttpsSoap11Endpoint_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getNotificacionElectronicaHttpsSoap11Endpoint(endpoint);
    }

    public com.osi.notificacionelectronica.service.NotificacionElectronicaPortType getNotificacionElectronicaHttpsSoap11Endpoint(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.osi.notificacionelectronica.service.NotificacionElectronicaSoap11BindingStub _stub = new com.osi.notificacionelectronica.service.NotificacionElectronicaSoap11BindingStub(portAddress, this);
            _stub.setPortName(getNotificacionElectronicaHttpsSoap11EndpointWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setNotificacionElectronicaHttpsSoap11EndpointEndpointAddress(java.lang.String address) {
        NotificacionElectronicaHttpsSoap11Endpoint_address = address;
    }


    // Use to get a proxy class for NotificacionElectronicaHttpsSoap12Endpoint
    private java.lang.String NotificacionElectronicaHttpsSoap12Endpoint_address = "https://wso2pruebas.colsanitas.com:8246/services/NotificacionElectronica.NotificacionElectronicaHttpsSoap12Endpoint";

    public java.lang.String getNotificacionElectronicaHttpsSoap12EndpointAddress() {
        return NotificacionElectronicaHttpsSoap12Endpoint_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String NotificacionElectronicaHttpsSoap12EndpointWSDDServiceName = "NotificacionElectronicaHttpsSoap12Endpoint";

    public java.lang.String getNotificacionElectronicaHttpsSoap12EndpointWSDDServiceName() {
        return NotificacionElectronicaHttpsSoap12EndpointWSDDServiceName;
    }

    public void setNotificacionElectronicaHttpsSoap12EndpointWSDDServiceName(java.lang.String name) {
        NotificacionElectronicaHttpsSoap12EndpointWSDDServiceName = name;
    }

    public com.osi.notificacionelectronica.service.NotificacionElectronicaPortType getNotificacionElectronicaHttpsSoap12Endpoint() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(NotificacionElectronicaHttpsSoap12Endpoint_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getNotificacionElectronicaHttpsSoap12Endpoint(endpoint);
    }

    public com.osi.notificacionelectronica.service.NotificacionElectronicaPortType getNotificacionElectronicaHttpsSoap12Endpoint(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.osi.notificacionelectronica.service.NotificacionElectronicaSoap12BindingStub _stub = new com.osi.notificacionelectronica.service.NotificacionElectronicaSoap12BindingStub(portAddress, this);
            _stub.setPortName(getNotificacionElectronicaHttpsSoap12EndpointWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setNotificacionElectronicaHttpsSoap12EndpointEndpointAddress(java.lang.String address) {
        NotificacionElectronicaHttpsSoap12Endpoint_address = address;
    }


    // Use to get a proxy class for NotificacionElectronicaHttpSoap11Endpoint
    private java.lang.String NotificacionElectronicaHttpSoap11Endpoint_address = "http://wso2pruebas.colsanitas.com:8283/services/NotificacionElectronica.NotificacionElectronicaHttpSoap11Endpoint";

    public java.lang.String getNotificacionElectronicaHttpSoap11EndpointAddress() {
        return NotificacionElectronicaHttpSoap11Endpoint_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String NotificacionElectronicaHttpSoap11EndpointWSDDServiceName = "NotificacionElectronicaHttpSoap11Endpoint";

    public java.lang.String getNotificacionElectronicaHttpSoap11EndpointWSDDServiceName() {
        return NotificacionElectronicaHttpSoap11EndpointWSDDServiceName;
    }

    public void setNotificacionElectronicaHttpSoap11EndpointWSDDServiceName(java.lang.String name) {
        NotificacionElectronicaHttpSoap11EndpointWSDDServiceName = name;
    }

    public com.osi.notificacionelectronica.service.NotificacionElectronicaPortType getNotificacionElectronicaHttpSoap11Endpoint() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(NotificacionElectronicaHttpSoap11Endpoint_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getNotificacionElectronicaHttpSoap11Endpoint(endpoint);
    }

    public com.osi.notificacionelectronica.service.NotificacionElectronicaPortType getNotificacionElectronicaHttpSoap11Endpoint(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.osi.notificacionelectronica.service.NotificacionElectronicaSoap11BindingStub _stub = new com.osi.notificacionelectronica.service.NotificacionElectronicaSoap11BindingStub(portAddress, this);
            _stub.setPortName(getNotificacionElectronicaHttpSoap11EndpointWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setNotificacionElectronicaHttpSoap11EndpointEndpointAddress(java.lang.String address) {
        NotificacionElectronicaHttpSoap11Endpoint_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     * This service has multiple ports for a given interface;
     * the proxy implementation returned may be indeterminate.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.osi.notificacionelectronica.service.NotificacionElectronicaPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.osi.notificacionelectronica.service.NotificacionElectronicaSoap12BindingStub _stub = new com.osi.notificacionelectronica.service.NotificacionElectronicaSoap12BindingStub(new java.net.URL(NotificacionElectronicaHttpSoap12Endpoint_address), this);
                _stub.setPortName(getNotificacionElectronicaHttpSoap12EndpointWSDDServiceName());
                return _stub;
            }
            if (com.osi.notificacionelectronica.service.NotificacionElectronicaPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.osi.notificacionelectronica.service.NotificacionElectronicaSoap11BindingStub _stub = new com.osi.notificacionelectronica.service.NotificacionElectronicaSoap11BindingStub(new java.net.URL(NotificacionElectronicaHttpsSoap11Endpoint_address), this);
                _stub.setPortName(getNotificacionElectronicaHttpsSoap11EndpointWSDDServiceName());
                return _stub;
            }
            if (com.osi.notificacionelectronica.service.NotificacionElectronicaPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.osi.notificacionelectronica.service.NotificacionElectronicaSoap12BindingStub _stub = new com.osi.notificacionelectronica.service.NotificacionElectronicaSoap12BindingStub(new java.net.URL(NotificacionElectronicaHttpsSoap12Endpoint_address), this);
                _stub.setPortName(getNotificacionElectronicaHttpsSoap12EndpointWSDDServiceName());
                return _stub;
            }
            if (com.osi.notificacionelectronica.service.NotificacionElectronicaPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.osi.notificacionelectronica.service.NotificacionElectronicaSoap11BindingStub _stub = new com.osi.notificacionelectronica.service.NotificacionElectronicaSoap11BindingStub(new java.net.URL(NotificacionElectronicaHttpSoap11Endpoint_address), this);
                _stub.setPortName(getNotificacionElectronicaHttpSoap11EndpointWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("NotificacionElectronicaHttpSoap12Endpoint".equals(inputPortName)) {
            return getNotificacionElectronicaHttpSoap12Endpoint();
        }
        else if ("NotificacionElectronicaHttpsSoap11Endpoint".equals(inputPortName)) {
            return getNotificacionElectronicaHttpsSoap11Endpoint();
        }
        else if ("NotificacionElectronicaHttpsSoap12Endpoint".equals(inputPortName)) {
            return getNotificacionElectronicaHttpsSoap12Endpoint();
        }
        else if ("NotificacionElectronicaHttpSoap11Endpoint".equals(inputPortName)) {
            return getNotificacionElectronicaHttpSoap11Endpoint();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://service.notificacionelectronica.osi.com/", "NotificacionElectronica");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://service.notificacionelectronica.osi.com/", "NotificacionElectronicaHttpSoap12Endpoint"));
            ports.add(new javax.xml.namespace.QName("http://service.notificacionelectronica.osi.com/", "NotificacionElectronicaHttpsSoap11Endpoint"));
            ports.add(new javax.xml.namespace.QName("http://service.notificacionelectronica.osi.com/", "NotificacionElectronicaHttpsSoap12Endpoint"));
            ports.add(new javax.xml.namespace.QName("http://service.notificacionelectronica.osi.com/", "NotificacionElectronicaHttpSoap11Endpoint"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("NotificacionElectronicaHttpSoap12Endpoint".equals(portName)) {
            setNotificacionElectronicaHttpSoap12EndpointEndpointAddress(address);
        }
        else 
if ("NotificacionElectronicaHttpsSoap11Endpoint".equals(portName)) {
            setNotificacionElectronicaHttpsSoap11EndpointEndpointAddress(address);
        }
        else 
if ("NotificacionElectronicaHttpsSoap12Endpoint".equals(portName)) {
            setNotificacionElectronicaHttpsSoap12EndpointEndpointAddress(address);
        }
        else 
if ("NotificacionElectronicaHttpSoap11Endpoint".equals(portName)) {
            setNotificacionElectronicaHttpSoap11EndpointEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
