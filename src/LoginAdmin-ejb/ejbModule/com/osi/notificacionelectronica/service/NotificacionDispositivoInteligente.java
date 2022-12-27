/**
 * NotificacionDispositivoInteligente.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.osi.notificacionelectronica.service;

public class NotificacionDispositivoInteligente  implements java.io.Serializable {
    private java.lang.String asunto;

    private java.lang.String contenidob;

    private java.lang.String destino;

    private java.lang.String dummy;

    private com.osi.notificacionelectronica.service.ImagenMovil imagenMovilDTO;

    private java.lang.String observaciones;

    public NotificacionDispositivoInteligente() {
    }

    public NotificacionDispositivoInteligente(
           java.lang.String asunto,
           java.lang.String contenidob,
           java.lang.String destino,
           java.lang.String dummy,
           com.osi.notificacionelectronica.service.ImagenMovil imagenMovilDTO,
           java.lang.String observaciones) {
           this.asunto = asunto;
           this.contenidob = contenidob;
           this.destino = destino;
           this.dummy = dummy;
           this.imagenMovilDTO = imagenMovilDTO;
           this.observaciones = observaciones;
    }


    /**
     * Gets the asunto value for this NotificacionDispositivoInteligente.
     * 
     * @return asunto
     */
    public java.lang.String getAsunto() {
        return asunto;
    }


    /**
     * Sets the asunto value for this NotificacionDispositivoInteligente.
     * 
     * @param asunto
     */
    public void setAsunto(java.lang.String asunto) {
        this.asunto = asunto;
    }


    /**
     * Gets the contenidob value for this NotificacionDispositivoInteligente.
     * 
     * @return contenidob
     */
    public java.lang.String getContenidob() {
        return contenidob;
    }


    /**
     * Sets the contenidob value for this NotificacionDispositivoInteligente.
     * 
     * @param contenidob
     */
    public void setContenidob(java.lang.String contenidob) {
        this.contenidob = contenidob;
    }


    /**
     * Gets the destino value for this NotificacionDispositivoInteligente.
     * 
     * @return destino
     */
    public java.lang.String getDestino() {
        return destino;
    }


    /**
     * Sets the destino value for this NotificacionDispositivoInteligente.
     * 
     * @param destino
     */
    public void setDestino(java.lang.String destino) {
        this.destino = destino;
    }


    /**
     * Gets the dummy value for this NotificacionDispositivoInteligente.
     * 
     * @return dummy
     */
    public java.lang.String getDummy() {
        return dummy;
    }


    /**
     * Sets the dummy value for this NotificacionDispositivoInteligente.
     * 
     * @param dummy
     */
    public void setDummy(java.lang.String dummy) {
        this.dummy = dummy;
    }


    /**
     * Gets the imagenMovilDTO value for this NotificacionDispositivoInteligente.
     * 
     * @return imagenMovilDTO
     */
    public com.osi.notificacionelectronica.service.ImagenMovil getImagenMovilDTO() {
        return imagenMovilDTO;
    }


    /**
     * Sets the imagenMovilDTO value for this NotificacionDispositivoInteligente.
     * 
     * @param imagenMovilDTO
     */
    public void setImagenMovilDTO(com.osi.notificacionelectronica.service.ImagenMovil imagenMovilDTO) {
        this.imagenMovilDTO = imagenMovilDTO;
    }


    /**
     * Gets the observaciones value for this NotificacionDispositivoInteligente.
     * 
     * @return observaciones
     */
    public java.lang.String getObservaciones() {
        return observaciones;
    }


    /**
     * Sets the observaciones value for this NotificacionDispositivoInteligente.
     * 
     * @param observaciones
     */
    public void setObservaciones(java.lang.String observaciones) {
        this.observaciones = observaciones;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof NotificacionDispositivoInteligente)) return false;
        NotificacionDispositivoInteligente other = (NotificacionDispositivoInteligente) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.asunto==null && other.getAsunto()==null) || 
             (this.asunto!=null &&
              this.asunto.equals(other.getAsunto()))) &&
            ((this.contenidob==null && other.getContenidob()==null) || 
             (this.contenidob!=null &&
              this.contenidob.equals(other.getContenidob()))) &&
            ((this.destino==null && other.getDestino()==null) || 
             (this.destino!=null &&
              this.destino.equals(other.getDestino()))) &&
            ((this.dummy==null && other.getDummy()==null) || 
             (this.dummy!=null &&
              this.dummy.equals(other.getDummy()))) &&
            ((this.imagenMovilDTO==null && other.getImagenMovilDTO()==null) || 
             (this.imagenMovilDTO!=null &&
              this.imagenMovilDTO.equals(other.getImagenMovilDTO()))) &&
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
        if (getAsunto() != null) {
            _hashCode += getAsunto().hashCode();
        }
        if (getContenidob() != null) {
            _hashCode += getContenidob().hashCode();
        }
        if (getDestino() != null) {
            _hashCode += getDestino().hashCode();
        }
        if (getDummy() != null) {
            _hashCode += getDummy().hashCode();
        }
        if (getImagenMovilDTO() != null) {
            _hashCode += getImagenMovilDTO().hashCode();
        }
        if (getObservaciones() != null) {
            _hashCode += getObservaciones().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(NotificacionDispositivoInteligente.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://service.notificacionelectronica.osi.com/", "notificacionDispositivoInteligente"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("asunto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "asunto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contenidob");
        elemField.setXmlName(new javax.xml.namespace.QName("", "contenidob"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("destino");
        elemField.setXmlName(new javax.xml.namespace.QName("", "destino"));
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
        elemField.setFieldName("imagenMovilDTO");
        elemField.setXmlName(new javax.xml.namespace.QName("", "imagenMovilDTO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://service.notificacionelectronica.osi.com/", "imagenMovil"));
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
