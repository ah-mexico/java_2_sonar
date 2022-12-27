package com.osi.his.sistema.util;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author amarquez Alex Ricardo Marquez Coronel (armarquez@colsanitas.com)
 * @since Sep 24, 2008
 * 
 * Se encarga de las funciones adicionales de CrudTableWeb como mantener una
 * instancia en memoria para la creaci�n, definici�n de m�todos comunes en las
 * Operaciones CRUD de determinado entity.
 * @param <ELEMENT>
 *            Clase del elemento
 */
public abstract class CrudTableExtWeb<ELEMENT extends EntityClass> extends
		CrudTableWeb<ELEMENT> implements Serializable{
	private static final long serialVersionUID = 2398580638691379818L;
	private static Logger logger = LoggerFactory.getLogger(CrudTableExtWeb.class);
	private ELEMENT newVal;
	
	/**
	 * obtiene el valuor nuevo, y si no existe, lo crea y devuelve el valor creado.
	 * @return newVal valor nuevo.
	 */
	public ELEMENT getNewVal() {
		if (newVal == null) {
			newVal = createNew();
		}
		return newVal;
	}
	/**
	 * Establece el valor de newVal.
	 * @param newVal valor a establecer.
	 */
	public void setNewVal(ELEMENT newVal){
		this.newVal = newVal;
	}
	
	/**
	 * Constructor.
	 */
	public CrudTableExtWeb() {
		super();
	}

	/**
	 * 
	 * Guarda y Agrega el elemento newVal como un nuevo elmento de la lista y
	 * resetea el valor o usa el metodo add normal de acuerdo al parametro.
	 * 
	 * @param useNewVal
	 *            define si se guarda el newVal o si crea uno nuevo para agregar
	 *            a la lista
	 */
	public void add(boolean useNewVal) {
		try {
			if (useNewVal) {
				newVal = doCreate(newVal);
				if (!doNotAdd) {
					elementList.add(0, newVal);
					disabled.add(0, true);
					newVal = createNew();
				}
				
			} else {
				add();
			}
		} catch (Exception e) {
			logger.error("Error guardando el nuevo objeto", e);
		} finally {
			doNotAdd = false;
		}
	}
	
	/**
	 * Vuelve a dejar a newVal con un registro totalmente nuevo.
	 */
	public void cancel() {
		newVal = createNew();
	}

	/** 
	 * isEditando.
	 * @return boolean
	 *  */
	public boolean isEditando(){
		boolean resultado = false;
		for(boolean item: disabled){
			if(!item){
				resultado = true;
				break;
			}
		}
		return resultado;
	}
	
	/**
	 * Devuelve true cuando un registro de la lista se puede borrar.
	 * 
	 * @param selection
	 *            indice del elemento de la lista por el cual se pregunta.
	 * @return dice si se puede borrar o no.
	 */
	public abstract boolean isDeleteable(int selection);

	/**
	 * Devuelve true cuando un registro de la lista se puede editar.
	 * 
	 * @param selection
	 *            indice del elemento de la lista por el cual se pregunta.
	 * @return dice si se puede editar o no.
	 */
	public abstract boolean isEditable(int selection);
	
	public void filterResults() {}
	
	public void cargarAutAdmin( ELEMENT element ) {}
	
	public void cargarAutDele( ELEMENT element ) {}

}
