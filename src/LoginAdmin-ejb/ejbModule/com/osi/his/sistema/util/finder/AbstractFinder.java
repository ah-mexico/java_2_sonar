package com.osi.his.sistema.util.finder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;

import com.osi.his.historiaclinica.general.enums.ETipoNotificacion;
import com.osi.his.sistema.util.EntityClass;
import com.osi.his.sistema.util.IEntityHandler;
import com.osi.his.sistema.util.IEntityHandlerListener;

/**
 * Define el comportamiento de una clase Web que usa el buscador
 * 
 * @author amarquez Alex Ricardo Marquez Coronel (armarquez@colsanitas.com)
 * @since Sep 24, 2008
 * 
 */
public abstract class AbstractFinder<T extends EntityClass> implements
		IFinder<T>, IEntityHandler<T> {

	private static final long serialVersionUID = 6447629192231542790L;
	protected static final String FETCH_VALUE = "id";
	protected static final String FETCH_LABEL = "descripcionCorta";

	protected final String EMPTY_STRING = "";
	protected Set<IEntityHandlerListener<T>> listeners; 
	protected Set<String> listenerNames; 
	protected List<T> foundElements = new ArrayList<T>();;

	public AbstractFinder(){
	}

	public AbstractFinder(IEntityHandlerListener<T> listener){
		this.addListener(listener);
	}
	
	public void setUpRanking(Class<T> clase){
	}
	
	/**
	 * Define el nombre de la propiedad en el element con el "value"
	 * 
	 * @return nombre del la propiedad "value"
	 */
	public String getFetchValue() {
		return FETCH_VALUE;
	}

	/**
	 * Define el nombre de la propiedad en el element con el "label"
	 * 
	 * @return nombre del la propiedad "label"
	 */
	public String getFetchLabel() {
		return FETCH_LABEL;
	}

	/**
	 * Necesario para el sugestion box, realiza ciertas validaciones comounes y
	 * luego llama al metodo {@link #findByText(String)}
	 * 
	 * realiza la busqueda de los m?s buscados cuando se busque la palabra clave
	 * @param event
	 *            Objecto con la informaci?n a buscar
	 * @return lista de objetos soportados por un datattable
	 */
	public List<T> findByText(Object event) {
		String texto = (String) event;

		List<T> result = null;
		// No se porque toca quemarle lo de null pero est? haciendo esta
		// consulta
		if (texto != null && !texto.isEmpty() && !texto.equals("null")) {
				result = this.findByText(texto);
		} 

		return result;
	}

	public abstract void findByCode(T entity);

	/**
	 * Devuelve los entities que en la descripci?n tengan dicho texto
	 * 
	 * @param texto
	 *            text a buscar dentro de la descripci?n
	 * @return entities encontrados
	 */
	public abstract List<T> findByText(String texto);
	
	/**
	 * Con objetos complejos el convertidor de objetos javascript del finder se
	 * Muere, por eso se debe normalizar la lista traida del motor de datos
	 * lo que consiste en hacer una copia de los valores FETCH_VALUE y FETCH_LABEL de
	 * cada objeto de la lista en un objeto nuevo. 
	 * @param lista lista a normalizar
	 * @return lista normalizada;
	 */
	public abstract List<T> normalizar(List<T> lista);

	/**
	 * Agrega  un listener del finder
	 * @param listener listener a agregar 
	 */
	public void addListener(IEntityHandlerListener<T> listener){
		if(listeners == null){
			listeners =  new HashSet<IEntityHandlerListener<T>>();
		}
		if(listener != null) {listeners.add(listener);}
	}
	
	/**
	 * Notifica a todos los listener que se ha encontrado un elemento
	 * @param found elemento encontrado o seleccionado
	 */
	public void notify(T found){
		foundElements.clear();
		foundElements.add(found);
		notifyListeners();
	}
	
	@SuppressWarnings("unchecked")
	public void notifyListeners(){
		if(listeners != null ){
			for (IEntityHandlerListener<T> listener :  listeners){
				listener.notify(foundElements, ETipoNotificacion.ENCONTRADA_FINDER);
			}
		}
		if(listenerNames != null ){
			for (String seamName :  listenerNames){
				IEntityHandlerListener<T> listener = (IEntityHandlerListener<T>) Component.getInstance(seamName, ScopeType.CONVERSATION);
				if (listener != null) {listener.notify(foundElements, ETipoNotificacion.ENCONTRADA_FINDER);}
			}
		}
	}

	/**
	 * Agrega un nombre seam del listener que se encuentre en conversaci?n
	 * @param name nombre seam del listener.
	 */
	public void addListener(String name){
		if(listenerNames == null){
			listenerNames =  new HashSet<String>();
		}
		if(name != null) {listenerNames.add(name);}
	}
}
