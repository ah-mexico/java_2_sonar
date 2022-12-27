
package com.colsanitas.loginadmin.administracion.integration.biosanitas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for datosPrestadorDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="datosPrestadorDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codigoPersona" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="codigoTipoIDNegocio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="estado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="razonSocial" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoID" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "datosPrestadorDTO", propOrder = {
    "codigoPersona",
    "codigoTipoIDNegocio",
    "estado",
    "numeroID",
    "razonSocial",
    "tipoID"
})
public class DatosPrestadorDTO {

    protected Long codigoPersona;
    protected String codigoTipoIDNegocio;
    protected String estado;
    protected String numeroID;
    protected String razonSocial;
    protected Long tipoID;

    /**
     * Gets the value of the codigoPersona property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCodigoPersona() {
        return codigoPersona;
    }

    /**
     * Sets the value of the codigoPersona property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCodigoPersona(Long value) {
        this.codigoPersona = value;
    }

    /**
     * Gets the value of the codigoTipoIDNegocio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoTipoIDNegocio() {
        return codigoTipoIDNegocio;
    }

    /**
     * Sets the value of the codigoTipoIDNegocio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoTipoIDNegocio(String value) {
        this.codigoTipoIDNegocio = value;
    }

    /**
     * Gets the value of the estado property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Sets the value of the estado property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEstado(String value) {
        this.estado = value;
    }

    /**
     * Gets the value of the numeroID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroID() {
        return numeroID;
    }

    /**
     * Sets the value of the numeroID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroID(String value) {
        this.numeroID = value;
    }

    /**
     * Gets the value of the razonSocial property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRazonSocial() {
        return razonSocial;
    }

    /**
     * Sets the value of the razonSocial property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRazonSocial(String value) {
        this.razonSocial = value;
    }

    /**
     * Gets the value of the tipoID property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getTipoID() {
        return tipoID;
    }

    /**
     * Sets the value of the tipoID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setTipoID(Long value) {
        this.tipoID = value;
    }

}
