/**
 * EnviarNotificacionDispositivoInteligenteOUT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.osi.notificacionelectronica.service;

public class EnviarNotificacionDispositivoInteligenteOUT  extends com.osi.notificacionelectronica.service.RespuestaNotificacionElectronica  implements java.io.Serializable {
    private long idProceso;

    public EnviarNotificacionDispositivoInteligenteOUT() {
    }

    public EnviarNotificacionDispositivoInteligenteOUT(
           int codigoRespuesta,
           java.lang.String mensajeRespuesta,
           long idProceso) {
        super(
            codigoRespuesta,
            mensajeRespuesta);
        this.idProceso = idProceso;
    }


    /**
     * Gets the idProceso value for this EnviarNotificacionDispositivoInteligenteOUT.
     * 
     * @return idProceso
     */
    public long getIdProceso() {
        return idProceso;
    }


    /**
     * Sets the idProceso value for this EnviarNotificacionDispositivoInteligenteOUT.
     * 
     * @param idProceso
     */
    public void setIdProceso(long idProceso) {
        this.idProceso = idProceso;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EnviarNotificacionDispositivoInteligenteOUT)) return false;
        EnviarNotificacionDispositivoInteligenteOUT other = (EnviarNotificacionDispositivoInteligenteOUT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            this.idProceso == other.getIdProceso();
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
        _hashCode += new Long(getIdProceso()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(EnviarNotificacionDispositivoInteligenteOUT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://service.notificacionelectronica.osi.com/", "enviarNotificacionDispositivoInteligenteOUT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idProceso");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idProceso"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
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
