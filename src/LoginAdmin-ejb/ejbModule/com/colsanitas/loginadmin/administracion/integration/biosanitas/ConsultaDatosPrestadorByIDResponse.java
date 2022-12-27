
package com.colsanitas.loginadmin.administracion.integration.biosanitas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for consultaDatosPrestadorByIDResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="consultaDatosPrestadorByIDResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://ejb.business.ws.biosanitas.osi.com/}datosPrestadorDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "consultaDatosPrestadorByIDResponse", propOrder = {
    "_return"
})
public class ConsultaDatosPrestadorByIDResponse {

    @XmlElement(name = "return")
    protected DatosPrestadorDTO _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link DatosPrestadorDTO }
     *     
     */
    public DatosPrestadorDTO getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatosPrestadorDTO }
     *     
     */
    public void setReturn(DatosPrestadorDTO value) {
        this._return = value;
    }

}
