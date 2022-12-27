package com.osi.his.sistema.util;


/**
 * Se encarga de realizar cambios o consultas u otros a objetos de tipo
 * EntityClass Y luego notifica esos cambios a los listener suscritos
 * {@link  #addListener(IEntityHandlerListener<ELEMENT>)  addListener}.
 * 
 * @author amarquez Alex Ricardo Marquez Coronel (armarquez@colsanitas.com)
 * @since Sep 24, 2008
 * 
 * @param <ELEMENT>
 *            Tipo de datos que maneja esta clase
 */
public interface IEntityHandler<ELEMENT extends EntityClass> {

	/**
	 * Agregar un listener a la lista para luego ser notificados.
	 * 
	 * @param listener
	 *            objeto a agregar.
	 */
	public void addListener(IEntityHandlerListener<ELEMENT> listener);

	/**
	 * Env�a a los listeners los manejos hechos en los elementos.
	 */
	public void notifyListeners();

}
