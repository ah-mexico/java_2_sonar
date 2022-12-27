/**
 * Sms.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.osi.notificacionelectronica.service;

public class Sms  implements java.io.Serializable {
    private java.lang.String contenidoSms;

    private java.lang.String destinoSms;

    private java.lang.String dummy;

    private java.lang.String observaciones;

    public Sms() {
    }

    public Sms(
           java.lang.String contenidoSms,
           java.lang.String destinoSms,
           java.lang.String dummy,
           java.lang.String observaciones) {
           this.contenidoSms = contenidoSms;
           this.destinoSms = destinoSms;
           this.dummy = dummy;
           this.observaciones = observaciones;
    }


    /**
     * Gets the contenidoSms value for this Sms.
     * 
     * @return contenidoSms
     */
    public java.lang.String getContenidoSms() {
        return contenidoSms;
    }


    /**
     * Sets the contenidoSms value for this Sms.
     * 
     * @param contenidoSms
     */
    public void setContenidoSms(java.lang.String contenidoSms) {
        this.contenidoSms = contenidoSms;
    }


    /**
     * Gets the destinoSms value for this Sms.
     * 
     * @return destinoSms
     */
    public java.lang.String getDestinoSms() {
        return destinoSms;
    }


    /**
     * Sets the destinoSms value for this Sms.
     * 
     * @param destinoSms
     */
    public void setDestinoSms(java.lang.String destinoSms) {
        this.destinoSms = destinoSms;
    }


    /**
     * Gets the dummy value for this Sms.
     * 
     * @return dummy
     */
    public java.lang.String getDummy() {
        return dummy;
    }


    /**
     * Sets the dummy value for this Sms.
     * 
     * @param dummy
     */
    public void setDummy(java.lang.String dummy) {
        this.dummy = dummy;
    }


    /**
     * Gets the observaciones value for this Sms.
     * 
     * @return observaciones
     */
    public java.lang.String getObservaciones() {
        return observaciones;
    }


    /**
     * Sets the observaciones value for this Sms.
     * 
     * @param observaciones
     */
    public void setObservaciones(java.lang.String observaciones) {
        this.observaciones = observaciones;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Sms)) return false;
        Sms other = (Sms) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.contenidoSms==null && other.getContenidoSms()==null) || 
             (this.contenidoSms!=null &&
              this.contenidoSms.equals(other.getContenidoSms()))) &&
            ((this.destinoSms==null && other.getDestinoSms()==null) || 
             (this.destinoSms!=null &&
              this.destinoSms.equals(other.getDestinoSms()))) &&
            ((this.dummy==null && other.getDummy()==null) || 
             (this.dummy!=null &&
              this.dummy.equals(other.getDummy()))) &&
            ((this.observaciones==null && other.getObservaciones()==null) || 
             (this.observaciones!=null &&
              this.observaciones.equals(other.getObservaciones())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getContenidoSms() != null) {
            _hashCode += getContenidoSms().hashCode();
        }
        if (getDestinoSms() != null) {
            _hashCode += getDestinoSms().hashCode();
        }
        if (getDummy() != null) {
            _hashCode += getDummy().hashCode();
        }
        if (getObservaciones() != null) {
            _hashCode += getObservaciones().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Sms.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://service.notificacionelectronica.osi.com/", "sms"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contenidoSms");
        elemField.setXmlName(new javax.xml.namespace.QName("", "contenidoSms"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("destinoSms");
        elemField.setXmlName(new javax.xml.namespace.QName("", "destinoSms"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dummy");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dummy"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("observaciones");
        elemField.setXmlName(new javax.xml.namespace.QName("", "observaciones"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
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
