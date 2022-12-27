package com.colsanitas.loginadmin.administracion.enums;


/**
 * Define los estados de las relaciones que existen entre un usuario del sistema y un prestador.
 * @author armarquez
 *
 */
public enum ETipoEstadoPrestador {
	ACTIVA(1),
	INACTIVA(0);
	
	private int id;
	
	private ETipoEstadoPrestador( int id ) {
		this.id = id;
	}
	
	public String toString () {
		return String.valueOf(id);
	}

	public boolean toBoolean(){
		return (id == 1);
	}
	public int getId() {
		return id;
	}
	
	/**
	 * Retorna un TipoEstadoRelacion dado su representacion en entero
	 * @param id
	 * @return TipoRelacion
	 */
	public static ETipoEstadoPrestador fromInt (int id) {
		ETipoEstadoPrestador retorno = null;
		
		for (ETipoEstadoPrestador t : ETipoEstadoPrestador.values()){
			if (t.getId() == id){
				retorno = t;
			}
		}

		return retorno;
	}
	

}
