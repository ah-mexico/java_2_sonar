package com.osi.his.sistema.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * @author pescobar (pescobar@colsanitas.com)
 * @since 22/10/2008 <br>
 *        <br>
 *        <i> Clase que representa el listener web (o backing bean) para una 
 *        tabla editable con las operaciones basicas de CRUD, ofreciendo 
 *        los metodos basicos y comunes para su manejo.</i>
 */
public abstract class CrudTableWeb<ELEMENT extends EntityClass> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4589063839568655793L;
	protected ArrayList<Boolean> 		disabled;
	private ELEMENT						currVal;
	private int							currSel;
	protected List<ELEMENT> 			elementList;

	/**Puede ser utilizado por los metodos doCreate y doUpdate para marcar si se presento
	 * una excepcion, y no deberia agregarse el registro a la lista de elementos*/
	protected boolean doNotAdd;

	protected boolean isEditing;

	public CrudTableWeb() {
		elementList 	= new ArrayList<ELEMENT>();
		disabled 		= new ArrayList<Boolean>();
		currVal 		= null;
		currSel 		= -1;
	}


	/**
	 * Metodo que invoca la logica de negocio para crear un elemento.
	 * @param element	Entidad que se quiere crear.
	 */
	protected abstract ELEMENT doCreate( ELEMENT element );

	/**
	 * Metodo que invoca la logica de negocio para actualizar un elemento.
	 * @param element	Entidad que se quiere actualizar.
	 */
	protected abstract ELEMENT doUpdate( ELEMENT element );


	/**
	 * Metodo que invoca la logica de negocio para eleminar un elemento.
	 * @param element	Entidad a ser eliminada.
	 */
	protected abstract void doRemove( ELEMENT element );


	/**
	 * Clona un elemento dado.
	 * @param element	Elemento a ser clonado.
	 * @return
	 */
	protected abstract ELEMENT clone( ELEMENT element );



	/**
	 * Crea un nuevo elemento de tipo entidad.
	 * @return	Elemento creado.
	 */
	protected abstract ELEMENT createNew();


	/**
	 * Indica si el id o llave primaria del elemento evaluado esta establecida
	 * @param element	Elemento a ser evaluado.
	 * @return
	 */
	protected abstract boolean isIdSet( ELEMENT element );

	/**
	 * Indica si el id o llave primaria del elemento evaluado esta establecida
	 * @param element	Elemento a ser evaluado.
	 * @return
	 */
	public abstract void seleccionar( ELEMENT element );


	public void save( ELEMENT element, int selection ) {

		if( isIdSet( element ) ) {
			element = doUpdate( element );
		} else {
			element = doCreate( element );
		}

		if (! doNotAdd ){
			elementList.set( selection, element );
			disabled.set( selection, true);
			isEditing = false;

			currVal = null;
			currSel = -1;
		} 

		doNotAdd = false;
	}

	/**
	 * Indica si el registro seleccionado de la tabla de datos esta activo (modo
	 * de escritura) o inactivo (modo solo lectura).
	 * @param seleccion	Numero de registro seleccionado.
	 * @return	Verdadero si modo lectura/escritua, falso si modo lectura.
	 */
	public boolean isDisabled( int seleccion ) {
		boolean result = false;
		if( seleccion < disabled.size() ) {
			result = disabled.get(seleccion).booleanValue();
		}
		return result;
	}


	/**
	 * Activa la edicion de un registro en la tabla de datos.
	 * @param selection					Registro a ser editado
	 */
	public void edit( int selection ) {
		if( currVal != null ) {
			elementList.set( currSel, currVal );
			disabled.set( currSel, true );
			isEditing = false;
			currVal = null;
		}
		currSel = selection;

		disabled.set(selection, false);
		isEditing = true;

		ELEMENT temp = elementList.get( selection );
		currVal = clone( temp );
	}


	/**
	 * Cancela la ediciion de un registro en la tabla de datos, dejando los datos
	 * previos.
	 * @param selection		Registro seleccionado para dejar en modo lectura y 
	 * 						con los datos previos.
	 */
	public void cancel( int selection ) {
		ELEMENT element =  elementList.get(selection);
		if( isIdSet( element ) ) {
			disabled.set(selection, true);
			isEditing = false;
			elementList.set(selection, clone(currVal) );
			currVal = null;
		} else {
			remove( selection );
		}
	}


	/**
	 * Elimina un registro de la tabla de datos.
	 * @param selection	Numero de registro a ser eliminado.
	 */
	public void remove( int selection ) {
		ELEMENT element = elementList.get(selection);
		if( isIdSet( element ) ) {
			doRemove( element );
		} 
		elementList.remove(selection);	
		disabled.remove( 0 );
		isEditing = false;
		currVal = null;
	}


	/**
	 * Adiciona un nuevo registro en la tabla de datos, se hace al comienzo de la 
	 * misma.
	 */
	public void add( ) {
		if( elementList.isEmpty() || !isAlreadyCreate() ) {
			if( currVal != null ) {
				elementList.set( currSel, currVal );
				disabled.set( currSel, true );
				isEditing = false;
				currVal = null;
			}
			currSel = 0;

			ELEMENT newEntity = createNew();
			elementList.add( 0, newEntity );
			disabled.add(0, false);
			isEditing = true;
		}
	}

	private boolean isAlreadyCreate( ) {
		ELEMENT firstElement = elementList.get( 0 );
		return firstElement == null || !isIdSet( firstElement );
	}


	public List<ELEMENT> getElementList() {
		return elementList;
	}

	public void setElementList(List<ELEMENT> elementList) {
		this.elementList = elementList;
		if(elementList!=null){
			for( int i = 0; i < elementList.size(); i++ ) {
				disabled.add(true);
				isEditing = false;
			}
		}
	}
	
	public boolean isEditing() {
		return isEditing;
	}


	public void setEditing(boolean isEditing) {
		this.isEditing = isEditing;
	}


	public ELEMENT getCurrVal() {
		return currVal;
	}


	public void setCurrVal(ELEMENT currVal) {
		this.currVal = currVal;
	}


	public int getCurrSel() {
		return currSel;
	}


	public void setCurrSel(int currSel) {
		this.currSel = currSel;
	}

}
