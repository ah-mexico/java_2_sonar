
package com.colsanitas.loginadmin.administracion.integration.biosanitas;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.colsanitas.loginadmin.administracion.integration.biosanitas package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ConsultaDatosPrestadorByIDResponse_QNAME = new QName("http://ejb.business.ws.biosanitas.osi.com/", "consultaDatosPrestadorByIDResponse");
    private final static QName _PrestadorNotMigratedException_QNAME = new QName("http://ejb.business.ws.biosanitas.osi.com/", "PrestadorNotMigratedException");
    private final static QName _ConsultaSucursalesPrestadorByIDResponse_QNAME = new QName("http://ejb.business.ws.biosanitas.osi.com/", "consultaSucursalesPrestadorByIDResponse");
    private final static QName _ConsultaSucursalesPrestadorByID_QNAME = new QName("http://ejb.business.ws.biosanitas.osi.com/", "consultaSucursalesPrestadorByID");
    private final static QName _PrestadorNotFoundExcepcion_QNAME = new QName("http://ejb.business.ws.biosanitas.osi.com/", "PrestadorNotFoundExcepcion");
    private final static QName _ConsultaDatosPrestadorByID_QNAME = new QName("http://ejb.business.ws.biosanitas.osi.com/", "consultaDatosPrestadorByID");
    private final static QName _BioSanitasWSException_QNAME = new QName("http://ejb.business.ws.biosanitas.osi.com/", "BioSanitasWSException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.colsanitas.loginadmin.administracion.integration.biosanitas
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PrestadorNotFoundExcepcion }
     * 
     */
    public PrestadorNotFoundExcepcion createPrestadorNotFoundExcepcion() {
        return new PrestadorNotFoundExcepcion();
    }

    /**
     * Create an instance of {@link BioSanitasWSException }
     * 
     */
    public BioSanitasWSException createBioSanitasWSException() {
        return new BioSanitasWSException();
    }

    /**
     * Create an instance of {@link PrestadorNotMigratedException }
     * 
     */
    public PrestadorNotMigratedException createPrestadorNotMigratedException() {
        return new PrestadorNotMigratedException();
    }

    /**
     * Create an instance of {@link SucursalesByPrestadorDTO }
     * 
     */
    public SucursalesByPrestadorDTO createSucursalesByPrestadorDTO() {
        return new SucursalesByPrestadorDTO();
    }

    /**
     * Create an instance of {@link DatosPrestadorDTO }
     * 
     */
    public DatosPrestadorDTO createDatosPrestadorDTO() {
        return new DatosPrestadorDTO();
    }

    /**
     * Create an instance of {@link ConsultaSucursalesPrestadorByIDResponse }
     * 
     */
    public ConsultaSucursalesPrestadorByIDResponse createConsultaSucursalesPrestadorByIDResponse() {
        return new ConsultaSucursalesPrestadorByIDResponse();
    }

    /**
     * Create an instance of {@link ConsultaSucursalesPrestadorByID }
     * 
     */
    public ConsultaSucursalesPrestadorByID createConsultaSucursalesPrestadorByID() {
        return new ConsultaSucursalesPrestadorByID();
    }

    /**
     * Create an instance of {@link ConsultaDatosPrestadorByIDResponse }
     * 
     */
    public ConsultaDatosPrestadorByIDResponse createConsultaDatosPrestadorByIDResponse() {
        return new ConsultaDatosPrestadorByIDResponse();
    }

    /**
     * Create an instance of {@link ConsultaDatosPrestadorByID }
     * 
     */
    public ConsultaDatosPrestadorByID createConsultaDatosPrestadorByID() {
        return new ConsultaDatosPrestadorByID();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConsultaDatosPrestadorByIDResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ejb.business.ws.biosanitas.osi.com/", name = "consultaDatosPrestadorByIDResponse")
    public JAXBElement<ConsultaDatosPrestadorByIDResponse> createConsultaDatosPrestadorByIDResponse(ConsultaDatosPrestadorByIDResponse value) {
        return new JAXBElement<ConsultaDatosPrestadorByIDResponse>(_ConsultaDatosPrestadorByIDResponse_QNAME, ConsultaDatosPrestadorByIDResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PrestadorNotMigratedException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ejb.business.ws.biosanitas.osi.com/", name = "PrestadorNotMigratedException")
    public JAXBElement<PrestadorNotMigratedException> createPrestadorNotMigratedException(PrestadorNotMigratedException value) {
        return new JAXBElement<PrestadorNotMigratedException>(_PrestadorNotMigratedException_QNAME, PrestadorNotMigratedException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConsultaSucursalesPrestadorByIDResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ejb.business.ws.biosanitas.osi.com/", name = "consultaSucursalesPrestadorByIDResponse")
    public JAXBElement<ConsultaSucursalesPrestadorByIDResponse> createConsultaSucursalesPrestadorByIDResponse(ConsultaSucursalesPrestadorByIDResponse value) {
        return new JAXBElement<ConsultaSucursalesPrestadorByIDResponse>(_ConsultaSucursalesPrestadorByIDResponse_QNAME, ConsultaSucursalesPrestadorByIDResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConsultaSucursalesPrestadorByID }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ejb.business.ws.biosanitas.osi.com/", name = "consultaSucursalesPrestadorByID")
    public JAXBElement<ConsultaSucursalesPrestadorByID> createConsultaSucursalesPrestadorByID(ConsultaSucursalesPrestadorByID value) {
        return new JAXBElement<ConsultaSucursalesPrestadorByID>(_ConsultaSucursalesPrestadorByID_QNAME, ConsultaSucursalesPrestadorByID.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PrestadorNotFoundExcepcion }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ejb.business.ws.biosanitas.osi.com/", name = "PrestadorNotFoundExcepcion")
    public JAXBElement<PrestadorNotFoundExcepcion> createPrestadorNotFoundExcepcion(PrestadorNotFoundExcepcion value) {
        return new JAXBElement<PrestadorNotFoundExcepcion>(_PrestadorNotFoundExcepcion_QNAME, PrestadorNotFoundExcepcion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConsultaDatosPrestadorByID }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ejb.business.ws.biosanitas.osi.com/", name = "consultaDatosPrestadorByID")
    public JAXBElement<ConsultaDatosPrestadorByID> createConsultaDatosPrestadorByID(ConsultaDatosPrestadorByID value) {
        return new JAXBElement<ConsultaDatosPrestadorByID>(_ConsultaDatosPrestadorByID_QNAME, ConsultaDatosPrestadorByID.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BioSanitasWSException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ejb.business.ws.biosanitas.osi.com/", name = "BioSanitasWSException")
    public JAXBElement<BioSanitasWSException> createBioSanitasWSException(BioSanitasWSException value) {
        return new JAXBElement<BioSanitasWSException>(_BioSanitasWSException_QNAME, BioSanitasWSException.class, null, value);
    }

}
