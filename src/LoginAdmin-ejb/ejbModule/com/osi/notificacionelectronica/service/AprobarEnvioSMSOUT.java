/**
 * AprobarEnvioSMSOUT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.osi.notificacionelectronica.service;

public class AprobarEnvioSMSOUT  extends com.osi.notificacionelectronica.service.RespuestaNotificacionElectronica  implements java.io.Serializable {
    private boolean respuestaAprobacion;

    public AprobarEnvioSMSOUT() {
    }

    public AprobarEnvioSMSOUT(
           int codigoRespuesta,
           java.lang.String mensajeRespuesta,
           boolean respuestaAprobacion) {
        super(
            codigoRespuesta,
            mensajeRespuesta);
        this.respuestaAprobacion = respuestaAprobacion;
    }


    /**
     * Gets the respuestaAprobacion value for this AprobarEnvioSMSOUT.
     * 
     * @return respuestaAprobacion
     */
    public boolean isRespuestaAprobacion() {
        return respuestaAprobacion;
    }


    /**
     * Sets the respuestaAprobacion value for this AprobarEnvioSMSOUT.
     * 
     * @param respuestaAprobacion
     */
    public void setRespuestaAprobacion(boolean respuestaAprobacion) {
        this.respuestaAprobacion = respuestaAprobacion;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AprobarEnvioSMSOUT)) return false;
        AprobarEnvioSMSOUT other = (AprobarEnvioSMSOUT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            this.respuestaAprobacion == other.isRespuestaAprobacion();
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        _hashCode += (isRespuestaAprobacion() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AprobarEnvioSMSOUT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://service.notificacionelectronica.osi.com/", "aprobarEnvioSMSOUT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("respuestaAprobacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "respuestaAprobacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
