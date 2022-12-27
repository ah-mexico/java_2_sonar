package com.colsanitas.loginadmin.administracion.enums;


/**
 * Define los estados de las relaciones que existen entre un usuario del sistema y un prestador.
 * @author armarquez
 *
 */
public enum ETipoEstadoRelacion {
	ACTIVA(1),
	INACTIVA(0);
	
	private int id;
	
	private ETipoEstadoRelacion( int id ) {
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
	public static ETipoEstadoRelacion fromInt (int id) {
		ETipoEstadoRelacion retorno = null;
		
		for (ETipoEstadoRelacion t : ETipoEstadoRelacion.values()){
			if (t.getId() == id){
				retorno = t;
			}
		}

		return retorno;
	}
	

}
