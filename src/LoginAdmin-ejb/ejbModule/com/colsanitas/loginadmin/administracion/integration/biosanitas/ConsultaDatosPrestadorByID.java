
package com.colsanitas.loginadmin.administracion.integration.biosanitas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for consultaDatosPrestadorByID complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="consultaDatosPrestadorByID">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dtoIn" type="{http://ejb.business.ws.biosanitas.osi.com/}datosPrestadorDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "consultaDatosPrestadorByID", propOrder = {
    "dtoIn"
})
public class ConsultaDatosPrestadorByID {

    protected DatosPrestadorDTO dtoIn;

    /**
     * Gets the value of the dtoIn property.
     * 
     * @return
     *     possible object is
     *     {@link DatosPrestadorDTO }
     *     
     */
    public DatosPrestadorDTO getDtoIn() {
        return dtoIn;
    }

    /**
     * Sets the value of the dtoIn property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatosPrestadorDTO }
     *     
     */
    public void setDtoIn(DatosPrestadorDTO value) {
        this.dtoIn = value;
    }

}
