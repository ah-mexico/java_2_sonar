package com.colsanitas.loginadmin.integration.prestadores;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.jws.WebService;

import org.jboss.seam.Seam;
import org.jboss.seam.annotations.In;
import org.jboss.seam.contexts.Lifecycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.colsanitas.loginadmin.prestadores.business.IGestionPrestadordEjb;




/***
 * Clase que implementa Servicio Web que desabilita los usuarios de los prestadores incativos.
 * @author armarquez
 *
 */
@WebService(endpointInterface = "com.colsanitas.loginadmin.integration.prestadores.IPrestador", serviceName="Prestador")
public class Prestador implements IPrestador {
    @In(create = true)
	
    private IGestionPrestadordEjb gestionPrestadordEjb;

	public Prestador(){
	}
	
	private static Logger logger = LoggerFactory.getLogger(Prestador.class);

	public void desactivarRelacionadosPrestadores( String[] idPrestadores ) {
		Lifecycle.beginCall();
        gestionPrestadordEjb = (IGestionPrestadordEjb) Seam.componentForName("gestionPrestadordEjb").newInstance();
        
        //Convierte el parametro en array de Long
        ArrayList<Long> listaIdPrestadores = new ArrayList<Long>();
        for (String id : idPrestadores){
        	listaIdPrestadores.add(Long.valueOf(id));
        }
        
        gestionPrestadordEjb.desactivarRelacionadosPrestadores(listaIdPrestadores);
        Lifecycle.endCall();
	}
	
	
	public void desactivarSucursalesRetiradas( String[] idSucursales ) {
		
        gestionPrestadordEjb = (IGestionPrestadordEjb) Seam.componentForName("gestionPrestadordEjb").newInstance();
        
        List<String> list = Arrays.asList(idSucursales); 
        
        try {
			if( list != null && list.size() > 0 ) {
				if( idSucursales != null && !list.isEmpty() ){

					Iterator<String> it = list.iterator();
					while(it.hasNext()){
						String idSucursal = it.next();
						try{
							Lifecycle.beginCall();
							gestionPrestadordEjb.desactivarSucursalRetirada(idSucursal);
							Lifecycle.endCall();
						}catch(Exception e){
							logger.error("Error: GestionPrestadordEjb.desactivarSucursalRetirada "+idSucursal+ ": "+e.getMessage());
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("Error: GestionPrestadordEjb.desactivarSucursalesRetiradas  "+e.getMessage());
		}
       
	}

}
