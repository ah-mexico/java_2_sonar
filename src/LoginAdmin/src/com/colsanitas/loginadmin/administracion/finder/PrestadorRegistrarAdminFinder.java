package com.colsanitas.loginadmin.administracion.finder;

import java.io.Serializable;
import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import com.colsanitas.loginadmin.administracion.business.IGestionAdmnistradorEjb;
import com.colsanitas.loginadmin.administracion.view.PrestadorView;
import com.colsanitas.loginadmin.dto.DatosPrestadorDTO;
import com.osi.his.sistema.util.finder.AbstractFinder;

@Name("prestadorRegistrarAdminFinder")
@Scope(ScopeType.CONVERSATION)
public class PrestadorRegistrarAdminFinder extends AbstractFinder<PrestadorView>implements Serializable {

	private static final long serialVersionUID = 1617432402997941968L;
	 
	@In(create = true)
	private IGestionAdmnistradorEjb gestionAdministradorEjb;
	
		
	@Override
	public void findByCode(PrestadorView entity) {
		DatosPrestadorDTO datosPrestadorDTO = null;
		Long tipoDoc = null;
		try {
			if( entity != null ) {
				datosPrestadorDTO = new DatosPrestadorDTO();
				
				
				
				datosPrestadorDTO.setTipoID( tipoDoc );
				datosPrestadorDTO.setNumeroID( entity.getNumId() );
				
				datosPrestadorDTO = this.gestionAdministradorEjb.consultarPrestadorBioSanitas(datosPrestadorDTO);
				
				if( datosPrestadorDTO != null ){
					entity.setCdperson(Long.valueOf(datosPrestadorDTO.getCodigoPersona()));
					entity.setEstado( datosPrestadorDTO.getEstado() );
					entity.setNumId( datosPrestadorDTO.getNumeroID() );
					entity.setRazonSocial( datosPrestadorDTO.getRazonSocial() );
					entity.setTipoId( datosPrestadorDTO.getTipoID() );
					entity.setCodigoTipoIDNegocio( datosPrestadorDTO.getCodigoTipoIDNegocio() );
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		
	}

	@Override
	public List<PrestadorView> findByText(String texto) {
	
		return null;
	}

	@Override
	public List<PrestadorView> normalizar(List<PrestadorView> lista) {
		return null;
	}
	
	@Override
	public String getFetchLabel(){
	    return "razonSocial";
	}
	
	@Override
	public String getFetchValue(){
	    return "numId";
	}
}