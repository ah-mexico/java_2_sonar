
package com.colsanitas.loginadmin.administracion.integration.biosanitas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for sucursalesByPrestadorDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="sucursalesByPrestadorDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codigoPersonaPrestador" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="codigoPrestador" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroIDPrestador" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sucursalPrestador" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoIDPrestador" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sucursalesByPrestadorDTO", propOrder = {
    "codigoPersonaPrestador",
    "codigoPrestador",
    "numeroIDPrestador",
    "sucursalPrestador",
    "tipoIDPrestador"
})
public class SucursalesByPrestadorDTO {

    protected Long codigoPersonaPrestador;
    protected String codigoPrestador;
    protected String numeroIDPrestador;
    protected String sucursalPrestador;
    protected String tipoIDPrestador;

    /**
     * Gets the value of the codigoPersonaPrestador property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCodigoPersonaPrestador() {
        return codigoPersonaPrestador;
    }

    /**
     * Sets the value of the codigoPersonaPrestador property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCodigoPersonaPrestador(Long value) {
        this.codigoPersonaPrestador = value;
    }

    /**
     * Gets the value of the codigoPrestador property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoPrestador() {
        return codigoPrestador;
    }

    /**
     * Sets the value of the codigoPrestador property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoPrestador(String value) {
        this.codigoPrestador = value;
    }

    /**
     * Gets the value of the numeroIDPrestador property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroIDPrestador() {
        return numeroIDPrestador;
    }

    /**
     * Sets the value of the numeroIDPrestador property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroIDPrestador(String value) {
        this.numeroIDPrestador = value;
    }

    /**
     * Gets the value of the sucursalPrestador property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSucursalPrestador() {
        return sucursalPrestador;
    }

    /**
     * Sets the value of the sucursalPrestador property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSucursalPrestador(String value) {
        this.sucursalPrestador = value;
    }

    /**
     * Gets the value of the tipoIDPrestador property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoIDPrestador() {
        return tipoIDPrestador;
    }

    /**
     * Sets the value of the tipoIDPrestador property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoIDPrestador(String value) {
        this.tipoIDPrestador = value;
    }

}
