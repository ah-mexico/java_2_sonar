package com.osi.his.sistema.util.finder;

import java.io.Serializable;
import java.util.List;

import com.osi.his.sistema.util.EntityClass;

/**
 * Define el comportamiento de una clase Web que usa el buscador.
 * 
 * @author amarquez Alex Ricardo Marquez Coronel (armarquez@colsanitas.com)
 * @since Sep 24, 2008
 *
 * @param <ELEMENT> Clase de objetos que busca el finder
 */
public interface IFinder<ELEMENT extends EntityClass> extends Serializable {

	/**
	 * Obtiene el texto que indica el nombre de la propiedad que contiene el
	 * "valor".
	 * 
	 * @return texto
	 */
	public abstract String getFetchValue();

	/**
	 * Obtiene el texto que indica el nombre de la propiedad que contiene el
	 * "label".
	 * 
	 * @return texto
	 */
	public abstract String getFetchLabel();

	/**
	 * Necesario para el sugestion box.
	 * 
	 * @param event
	 *            Objecto con la información a buscar.
	 * @return lista de objetos soportados por un datattable.
	 */
	public abstract List<ELEMENT> findByText(Object event);

	/**
	 * busca el elemento correspondiente al codigo en la propiedad "value" y
	 * asigna el valor encontrado al en "label" a la misma propiedad en el
	 * entity pasado por referencia.
	 * 
	 * El entity debe ser modificado, es un error de lógica hacer entity =
	 * entityEncontrado.
	 * 
	 * @param entity
	 *            elemento con el id a buscar. y debe sus propiedades deben ser
	 *            modificado con el valor encontrado.
	 */
	public abstract void findByCode(ELEMENT entity);

}
