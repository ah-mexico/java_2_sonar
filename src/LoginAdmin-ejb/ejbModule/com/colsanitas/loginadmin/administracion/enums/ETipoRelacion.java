package com.colsanitas.loginadmin.administracion.enums;

import com.osi.gaudi.cfg.Configurator;
import com.osi.gaudi.msg.Messenger;

/**
 * Define los tipos de relaciones que existen entre un usuario del sistema y un prestador.
 * @author armarquez
 *
 */
public enum ETipoRelacion {

	CREADO_POR(Configurator.getInstance().getInt("administracion", "tipoRelaCreadoPor", 0), Messenger.getInstance().getMsg("messages", "creadoPor")),
	ADMINISTRADOR(Configurator.getInstance().getInt("administracion", "tipoRelaAdministrador", 1), Messenger.getInstance().getMsg("messages", "administrador")),
	DELEGADO(Configurator.getInstance().getInt("administracion", "tipoRelaDelagado", 2), Messenger.getInstance().getMsg("messages", "delegado"));
	
	private int id;
	private String nombre;
	
	private ETipoRelacion( int id, String nombre ) {
		this.id = id;
		this.nombre = nombre;
	}
	
	@Override
	public String toString () {
		return nombre;
	}

	public int getId() {
		return id;
	}
	
	/**
	 * Retorna un TipoRelacion dado su representacion en entero
	 * @param id
	 * @return TipoRelacion
	 */
	public static ETipoRelacion fromInt (int id) {
		ETipoRelacion retorno = null;
		
		for (ETipoRelacion t : ETipoRelacion.values()){
			if (t.getId() == id){
				retorno = t;
			}
		}

		return retorno;
	}
	

}
