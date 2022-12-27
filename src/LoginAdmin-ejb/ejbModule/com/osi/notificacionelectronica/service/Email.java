/**
 * Email.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.osi.notificacionelectronica.service;

public class Email  implements java.io.Serializable {
    private com.osi.notificacionelectronica.service.AdjuntoEmail[] adjuntos;

    private java.lang.String asunto;

    private java.lang.String certimail;

    private java.lang.String destinatario;

    private java.lang.String dummy;

    private java.lang.String mailConfirmacion;

    private java.lang.String mensaje;

    private java.lang.String observaciones;

    public Email() {
    }

    public Email(
           com.osi.notificacionelectronica.service.AdjuntoEmail[] adjuntos,
           java.lang.String asunto,
           java.lang.String certimail,
           java.lang.String destinatario,
           java.lang.String dummy,
           java.lang.String mailConfirmacion,
           java.lang.String mensaje,
           java.lang.String observaciones) {
           this.adjuntos = adjuntos;
           this.asunto = asunto;
           this.certimail = certimail;
           this.destinatario = destinatario;
           this.dummy = dummy;
           this.mailConfirmacion = mailConfirmacion;
           this.mensaje = mensaje;
           this.observaciones = observaciones;
    }


    /**
     * Gets the adjuntos value for this Email.
     * 
     * @return adjuntos
     */
    public com.osi.notificacionelectronica.service.AdjuntoEmail[] getAdjuntos() {
        return adjuntos;
    }


    /**
     * Sets the adjuntos value for this Email.
     * 
     * @param adjuntos
     */
    public void setAdjuntos(com.osi.notificacionelectronica.service.AdjuntoEmail[] adjuntos) {
        this.adjuntos = adjuntos;
    }

    public com.osi.notificacionelectronica.service.AdjuntoEmail getAdjuntos(int i) {
        return this.adjuntos[i];
    }

    public void setAdjuntos(int i, com.osi.notificacionelectronica.service.AdjuntoEmail _value) {
        this.adjuntos[i] = _value;
    }


    /**
     * Gets the asunto value for this Email.
     * 
     * @return asunto
     */
    public java.lang.String getAsunto() {
        return asunto;
    }


    /**
     * Sets the asunto value for this Email.
     * 
     * @param asunto
     */
    public void setAsunto(java.lang.String asunto) {
        this.asunto = asunto;
    }


    /**
     * Gets the certimail value for this Email.
     * 
     * @return certimail
     */
    public java.lang.String getCertimail() {
        return certimail;
    }


    /**
     * Sets the certimail value for this Email.
     * 
     * @param certimail
     */
    public void setCertimail(java.lang.String certimail) {
        this.certimail = certimail;
    }


    /**
     * Gets the destinatario value for this Email.
     * 
     * @return destinatario
     */
    public java.lang.String getDestinatario() {
        return destinatario;
    }


    /**
     * Sets the destinatario value for this Email.
     * 
     * @param destinatario
     */
    public void setDestinatario(java.lang.String destinatario) {
        this.destinatario = destinatario;
    }


    /**
     * Gets the dummy value for this Email.
     * 
     * @return dummy
     */
    public java.lang.String getDummy() {
        return dummy;
    }


    /**
     * Sets the dummy value for this Email.
     * 
     * @param dummy
     */
    public void setDummy(java.lang.String dummy) {
        this.dummy = dummy;
    }


    /**
     * Gets the mailConfirmacion value for this Email.
     * 
     * @return mailConfirmacion
     */
    public java.lang.String getMailConfirmacion() {
        return mailConfirmacion;
    }


    /**
     * Sets the mailConfirmacion value for this Email.
     * 
     * @param mailConfirmacion
     */
    public void setMailConfirmacion(java.lang.String mailConfirmacion) {
        this.mailConfirmacion = mailConfirmacion;
    }


    /**
     * Gets the mensaje value for this Email.
     * 
     * @return mensaje
     */
    public java.lang.String getMensaje() {
        return mensaje;
    }


    /**
     * Sets the mensaje value for this Email.
     * 
     * @param mensaje
     */
    public void setMensaje(java.lang.String mensaje) {
        this.mensaje = mensaje;
    }


    /**
     * Gets the observaciones value for this Email.
     * 
     * @return observaciones
     */
    public java.lang.String getObservaciones() {
        return observaciones;
    }


    /**
     * Sets the observaciones value for this Email.
     * 
     * @param observaciones
     */
    public void setObservaciones(java.lang.String observaciones) {
        this.observaciones = observaciones;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Email)) return false;
        Email other = (Email) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.adjuntos==null && other.getAdjuntos()==null) || 
             (this.adjuntos!=null &&
              java.util.Arrays.equals(this.adjuntos, other.getAdjuntos()))) &&
            ((this.asunto==null && other.getAsunto()==null) || 
             (this.asunto!=null &&
              this.asunto.equals(other.getAsunto()))) &&
            ((this.certimail==null && other.getCertimail()==null) || 
             (this.certimail!=null &&
              this.certimail.equals(other.getCertimail()))) &&
            ((this.destinatario==null && other.getDestinatario()==null) || 
             (this.destinatario!=null &&
              this.destinatario.equals(other.getDestinatario()))) &&
            ((this.dummy==null && other.getDummy()==null) || 
             (this.dummy!=null &&
              this.dummy.equals(other.getDummy()))) &&
            ((this.mailConfirmacion==null && other.getMailConfirmacion()==null) || 
             (this.mailConfirmacion!=null &&
              this.mailConfirmacion.equals(other.getMailConfirmacion()))) &&
            ((this.mensaje==null && other.getMensaje()==null) || 
             (this.mensaje!=null &&
              this.mensaje.equals(other.getMensaje()))) &&
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
        if (getAdjuntos() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAdjuntos());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAdjuntos(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getAsunto() != null) {
            _hashCode += getAsunto().hashCode();
        }
        if (getCertimail() != null) {
            _hashCode += getCertimail().hashCode();
        }
        if (getDestinatario() != null) {
            _hashCode += getDestinatario().hashCode();
        }
        if (getDummy() != null) {
            _hashCode += getDummy().hashCode();
        }
        if (getMailConfirmacion() != null) {
            _hashCode += getMailConfirmacion().hashCode();
        }
        if (getMensaje() != null) {
            _hashCode += getMensaje().hashCode();
        }
        if (getObservaciones() != null) {
            _hashCode += getObservaciones().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Email.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://service.notificacionelectronica.osi.com/", "email"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("adjuntos");
        elemField.setXmlName(new javax.xml.namespace.QName("", "adjuntos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://service.notificacionelectronica.osi.com/", "adjuntoEmail"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("asunto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "asunto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("certimail");
        elemField.setXmlName(new javax.xml.namespace.QName("", "certimail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("destinatario");
        elemField.setXmlName(new javax.xml.namespace.QName("", "destinatario"));
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
        elemField.setFieldName("mailConfirmacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mailConfirmacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mensaje");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mensaje"));
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
