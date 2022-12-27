
package com.colsanitas.loginadmin.integration.prestadores.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "desactivarRelacionadosPrestadores", namespace = "http://prestadores.integration.loginadmin.colsanitas.com/")
@XmlType(name = "desactivarRelacionadosPrestadores", namespace = "http://prestadores.integration.loginadmin.colsanitas.com/")
@XmlAccessorType(XmlAccessType.FIELD)
public class DesactivarRelacionadosPrestadores {

    @XmlElement(name = "idPrestadores", namespace = "")
    private String[] idPrestadores;

    public String[] getIdPrestadores() {
        return this.idPrestadores;
    }

    public void setIdPrestadores(String[] idPrestadores) {
        this.idPrestadores = idPrestadores;
    }

}
