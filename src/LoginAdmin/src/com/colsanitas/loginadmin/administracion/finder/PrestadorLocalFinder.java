package com.colsanitas.loginadmin.administracion.finder;

import java.io.Serializable;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.colsanitas.loginadmin.administracion.business.IAdministrarAutorizaciondEjb;
import com.colsanitas.loginadmin.administracion.entity.PrestadorEntity;
import com.colsanitas.loginadmin.administracion.web.AdministraPlantillaRolWeb;
import com.osi.his.sistema.util.finder.AbstractFinder;

@Name("prestadorLocalFinder")
@Scope(ScopeType.CONVERSATION)
public class PrestadorLocalFinder extends AbstractFinder<PrestadorEntity> implements Serializable {

	private static final long serialVersionUID = 7726546514304529406L;

	private static Logger logger = LoggerFactory.getLogger(PrestadorLocalFinder.class);
	
	@In(create = true)
	IAdministrarAutorizaciondEjb administrarAutorizacionEjb;
	
	@In(create=true, required=false)
	AdministraPlantillaRolWeb administraPlantillaRolWeb;

	@Override
	public void findByCode(PrestadorEntity entity) {
		PrestadorEntity prestadorEntity = null;
		try {
			prestadorEntity = this.administrarAutorizacionEjb.consultPrestActivo( entity );
			if( prestadorEntity != null ){
				entity.setPrestadorId( prestadorEntity.getPrestadorId() );
				entity.setCdperson( prestadorEntity.getCdperson() );
				entity.setTipoId( prestadorEntity.getTipoId() );
				entity.setNumId( prestadorEntity.getNumId() );
				entity.setRazonSocial( prestadorEntity.getRazonSocial() );
				entity.setSucursal( prestadorEntity.getSucursal() );
				entity.setEstado( prestadorEntity.isEstado() );
			} else {
				entity.setPrestadorId( null );
				entity.setCdperson( null );
				entity.setTipoId( null );
				entity.setNumId( null );
				entity.setRazonSocial( null );
				entity.setEstado(false);
			}
			administraPlantillaRolWeb.setCheckAll( false );
			administraPlantillaRolWeb.setListElimRolePlantPrest( null );
		} catch (Exception e) {
			logger.error("Error: PrestadorLocalFinder.findByCode  "+e.getMessage(), e);
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<PrestadorEntity> findByText(String razonSocial) {
		List<PrestadorEntity> listaPrest = null;
		PrestadorEntity prestadorEntity = null;
		try {
			prestadorEntity = new PrestadorEntity();
			prestadorEntity.setRazonSocial( razonSocial );
			prestadorEntity.setEstado(true);
			listaPrest = administrarAutorizacionEjb.listPrestadoresActivos(prestadorEntity);
			administraPlantillaRolWeb.setCheckAll( false );
			administraPlantillaRolWeb.setListElimRolePlantPrest( null );
		} catch (Exception e) {
			logger.error("Error: PrestadorLocalFinder.findByText  "+e.getMessage(), e);
		} finally {
			prestadorEntity = null;
			razonSocial = null;
		}
		return listaPrest;
	}

	@Override
	public List<PrestadorEntity> normalizar(List<PrestadorEntity> lista) {
		return null;
	}
	
	@Override
	public String getFetchLabel(){
	    return "razonSocial";
	}
	
	@Override
	public String getFetchValue(){
	    return "sucursal";
	}

}
