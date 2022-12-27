package com.osi.his.sistema.util;

import java.util.List;

import com.osi.his.historiaclinica.general.enums.ETipoNotificacion;

/**
 * Esta interfaz define los eventos que se dan cuando existan cambios en una
 * clase de tipo EntityHandler.
 *
 * @author amarquez Alex Ricardo Marquez Coronel (armarquez@colsanitas.com)
 * @since Sep 24, 2008
 *
 * @param <ELEMENT> Clase que est� esperando el listener.
 */
public interface IEntityHandlerListener<ELEMENT extends EntityClass> {

	/**
	 * Recibe la notificaci�n de un elemento y el tipo de manejo que se le ha
	 * dado.
	 * 
	 * @param elements
	 *            elementos que se notifican como que han tenido alg�n tipo de
	 *            manejo.
	 * @param tipoNotificacion
	 *            define el tipo demanejo que se le ha dado los elementos.
	 */
	public void notify(List<ELEMENT> elements, ETipoNotificacion tipoNotificacion);
}
