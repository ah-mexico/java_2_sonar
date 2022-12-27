package com.osi.his.sistema.util;


import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.osi.his.sistema.util.EntityClass;

/**
 * 
 * @author wjrojas
 *
 */
@SuppressWarnings("deprecation")
public class JSFUtil {

    private static final String NO_RESOURCE_FOUND = "No Resource found for ";
    private static final int BUFFER_SIZE = 100;
    
    private JSFUtil() {
    }
    
    /**
     * Convenience method to construct a FacesMesssage
     * from a defined error key and severity
     * This assumes that the error keys follow the convention of
     * using _detail for the detailed part of the
     * message, otherwise the main message is returned for the
     * detail as well.
     * @param key for the error message in the resource bundle
     * @param severity
     * @return Faces Message object
     */
    public static FacesMessage getMessageFromBundle(String key, FacesMessage.Severity severity) {
        ResourceBundle bundle = getBundle();
        String summary = getStringSafely(bundle, key, null);
        //String detail = getStringSafely(bundle, key + "_detail", summary);
        FacesMessage message = new FacesMessage(summary, null);
        message.setSeverity(severity);
        return message;
    }
    
    /**
     * Devuelve Servlet Context
     * @return Devuelve Servlet Context
     */
    public static ServletContext getServletContext() {
        return (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
    }
    
    /**
     * Devuelve Servlet Request
     * @return Devuelve Servlet Request
     */
    public static HttpServletRequest getServletRequest() {
        return (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }
    
    /**
     * Devuelve Servlet Respones
     * @return Devuelve Servlet Respones
     */
    public static HttpServletResponse getServletResponse() {
        return (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
    }
    
    /**
     * Devuelve HttpSession
     * @return Devuelve HttpSession
     */
    public static HttpSession getHttpSession() {
        return (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    }
    
    /**
     * Remover uno de los beans
     * @param beanName del bean que va a ser removido
     */
    public static void resetManagedBean(String beanName) {
            getValueBinding(getJsfEl(beanName)).setValue(FacesContext.getCurrentInstance(), null);
    }

    /**
     * Method for taking a reference to a JSF binding expression and returning
     * the matching object (or creating it)
     * Partially refactored out of AbstractPetClinicBackingBean
     * so it can be reused in converters
     * @param ctx FacesContext
     * @param expression
     * @return Managed object
     */
    public static Object resolveExpression(FacesContext ctx, 
                                           String expression) {
        Application app = ctx.getApplication();
        ValueBinding bind = app.createValueBinding(expression);
        return bind.getValue(ctx);
    }

    /**
     * Convenience method for resolving a reference to a managed bean by name
     * rather than by expression
     * Refactored out of AbstractPetClinicBackingBean so it can be
     * reused in converters
     * @param ctx FacesContext
     * @param beanName
     * @return Managed object
     */
    public static Object getManagedBeanValue(FacesContext ctx, 
                                             String beanName) {
    	StringBuilder buff = new StringBuilder(BUFFER_SIZE);
        buff.append("#{");
        buff.append(beanName).append("}");

        return resolveExpression(ctx, buff.toString());
    }

    /**
     * Method for setting a new object into a JSF managed bean
     * Note: will fail silently if the supplied object does
     * not match the type of the managed bean
     * @param ctx FacesContext
     * @param expression
     * @param newValue
     */
    public static void setExpressionValue(FacesContext ctx, String expression, 
                                          Object newValue) {
        Application app = ctx.getApplication();
        ValueBinding bind = app.createValueBinding(expression);
        if (bind.getType(ctx) == newValue.getClass()) {
            bind.setValue(ctx, newValue);
        }
    }

    /**
     * Convenience method for setting the value of a managed bean by name
     * rather than by expression
     * @param ctx FacesContext
     * @param beanName
     * @param newValue
     */
    public static void setManagedBeanValue(FacesContext ctx, String beanName, 
                                           Object newValue) {
    	StringBuilder buff = new StringBuilder(BUFFER_SIZE);
        buff.append("#{");
        buff.append(beanName).append("}");
        setExpressionValue(ctx, buff.toString(), newValue);
    }

    /**
     * Convenience method for setting Session variables
     * @param ctx FacesContext
     * @param key object key
     * @param object value to store
     */
    public static void storeOnSession(FacesContext ctx, String key, Object object) {
		Map<String, Object> sessionState = ctx.getExternalContext().getSessionMap();
        sessionState.put(key, object);
    }

    /**
     * Convenience method for getting Session variables
     * @param ctx FacesContext
     * @param key object key
     */
    @SuppressWarnings("rawtypes")
	public static Object getFromSession(FacesContext ctx, String key) {
        Map sessionState = ctx.getExternalContext().getSessionMap();
        return sessionState.get(key);
    }

    /**
     * Pulls a String resource from the property bundle that
     * is defined under the application message-bundle element in
     * the faces config. Respects Locale
     * @param key
     * @return Resource value or placeholder error String
     */
    public static String getStringFromBundle(String key) {
        ResourceBundle bundle = getBundle();
        return getStringSafely(bundle, key, null);
    }

    /**
     * Convenience method to construct a FacesMesssage
     * from a defined error key and severity
     * This assumes that the error keys follow the convention of
     * using _detail for the detailed part of the
     * message, otherwise the main message is returned for the
     * detail as well.
     * @param key for the error message in the resource bundle
     * @param severity
     * @return Faces Message object
     */
    public static FacesMessage newMessage(String key, FacesMessage.Severity severity) {
        ResourceBundle bundle = getBundle();
        String summary = getStringSafely(bundle, key, null);
        //String detail = getStringSafely(bundle, key + "_detail", summary);
        FacesMessage message = new FacesMessage(summary, null);
        message.setSeverity(severity);
        return message;
    }
    
    /**
     * Add information message.
     * @param key key for obtein the message on bundle
     */
    public static void addInfoMessage(String key) {
        addInfoMessage(null, key);
    }

    /**
     * Add information message to a sepcific client.
     * @param clientId the client id 
     * @param key key for obtein the message on bundle
     */
    public static void addInfoMessage(String clientId, String key) {
        FacesContext.getCurrentInstance().addMessage(clientId, 
            getMessageFromBundle(key,FacesMessage.SEVERITY_INFO));
    }

    /**
     * Add information message to any client.
     * @param key key for obtein the message on bundle
     * @param parameter string to add to the message
     */
    public static void addInfoMessage(String clientId, String key, String parameter) {
        
        FacesMessage newMessage  = getMessageFromBundle(key,FacesMessage.SEVERITY_INFO);
        newMessage.setSummary(new StringBuilder(newMessage.getSummary()).append(parameter).toString());  
        FacesContext.getCurrentInstance().addMessage(clientId, newMessage);
    }
    
    /**
     * Add error message.
     * @param key key for obtein the message on bundle
     */
    public static void addErrorMessage(String key) {
        addErrorMessage(null, key);
    }

    /**
     * Add error message to a sepcific client.
     * @param clientId the client id 
     * @param key key for obtein the message on bundle
     */
    public static void addErrorMessage(String clientId, String key) {
        FacesContext.getCurrentInstance().addMessage(clientId, 
            getMessageFromBundle(key,FacesMessage.SEVERITY_ERROR));
    }
    
    /**
     * Add an error message to any client.
     * @param key key for obtein the message on bundle
     * @param parameter string to add to the message
     */
    public static void addErrorMessage(String clientId, String key, String parameter) {
        
        FacesMessage newMessage  = getMessageFromBundle(key,FacesMessage.SEVERITY_ERROR);
        newMessage.setSummary(new StringBuilder(newMessage.getSummary()).append(" ").append(parameter).toString());  
        FacesContext.getCurrentInstance().addMessage(clientId, newMessage);
    }
    
    /**
     * Add warnning message.
     * @param key key for obtein the message on bundle
     */
    public static void addWarnMessage(String key) {
    	addWarnMessage(null, key);
    }
    
    /**
     * Add warning message to a specific client.
     * @param clientId the client id 
     * @param key key for obtein the message on bundle
     */
    public static void addWarnMessage(String clientId, String key) {
        FacesContext.getCurrentInstance().addMessage(clientId, 
            getMessageFromBundle(key,FacesMessage.SEVERITY_WARN));
    }
    
    /**
     * Add an warning message to any client.
     * @param key key for obtein the message on bundle
     * @param parameter string to add to the message
     */
    public static void addWarnMessage(String clientId, String key, String parameter) {
        
        FacesMessage newMessage  = getMessageFromBundle(key,FacesMessage.SEVERITY_WARN);
        newMessage.setSummary(new StringBuilder(newMessage.getSummary()).append(" ").append(parameter).toString());  
        FacesContext.getCurrentInstance().addMessage(clientId, newMessage);
    }
    
    /**
     * Add message.
     * @param key key for obtein the message on bundle
     */
    public static void addMessage(EntityClass entity) {
    	if(entity.getTipoMensaje()!=null){
    		FacesMessage newMessage  = null;
    		if(entity.getTipoMensaje().equals(LoginBusinessProperties.getInstance().getParameter(LoginBusinessProperties.TIPO_MENSAJE_INFO))){
    			newMessage = getMessageFromBundle(ConstantesMessages.MENSAJE_GENERICO_MESSAGE,FacesMessage.SEVERITY_INFO);
    	        
    		}
    		else if(entity.getTipoMensaje().equals(LoginBusinessProperties.getInstance().getParameter(LoginBusinessProperties.TIPO_MENSAJE_ERROR))){
    			newMessage = getMessageFromBundle(ConstantesMessages.MENSAJE_GENERICO_MESSAGE,FacesMessage.SEVERITY_ERROR);
    		}
    		else if(entity.getTipoMensaje().equals(LoginBusinessProperties.getInstance().getParameter(LoginBusinessProperties.TIPO_MENSAJE_WARNING))){
    			newMessage = getMessageFromBundle(ConstantesMessages.MENSAJE_GENERICO_MESSAGE,FacesMessage.SEVERITY_WARN);
    		}
    		
    		newMessage.setSummary(new StringBuilder(newMessage.getSummary()).append(entity.getDescripcionMensaje()).toString());  
	        FacesContext.getCurrentInstance().addMessage(null, newMessage);
    	}
        
    }
    
    /**
     * Add message.
     * @param key key for obtein the message on bundle
     */
    public static void addMessage(EntityClass entity, String clientId) {
    	if(entity.getTipoMensaje()!=null){
    		FacesMessage newMessage  = null;
    		if(entity.getTipoMensaje().equals(LoginBusinessProperties.getInstance().getParameter(LoginBusinessProperties.TIPO_MENSAJE_INFO))){
    			newMessage = getMessageFromBundle(ConstantesMessages.MENSAJE_GENERICO_MESSAGE,FacesMessage.SEVERITY_INFO);
    	        
    		}
    		else if(entity.getTipoMensaje().equals(LoginBusinessProperties.getInstance().getParameter(LoginBusinessProperties.TIPO_MENSAJE_ERROR))){
    			newMessage = getMessageFromBundle(ConstantesMessages.MENSAJE_GENERICO_MESSAGE,FacesMessage.SEVERITY_ERROR);
    		}
    		else if(entity.getTipoMensaje().equals(LoginBusinessProperties.getInstance().getParameter(LoginBusinessProperties.TIPO_MENSAJE_WARNING))){
    			newMessage = getMessageFromBundle(ConstantesMessages.MENSAJE_GENERICO_MESSAGE,FacesMessage.SEVERITY_WARN);
    		}
    		
    		newMessage.setSummary(new StringBuilder(newMessage.getSummary()).append(entity.getDescripcionMensaje()).toString());  
	        FacesContext.getCurrentInstance().addMessage(clientId, newMessage);
    	}
        
    }
   
    /**
     * Get managed bean based on the bean name.
     * @param beanName the bean name
     * @return the managed bean associated with the bean name
     */
    public static Object getManagedBean(String beanName) {
        Object o = 
            getValueBinding(getJsfEl(beanName)).getValue(FacesContext.getCurrentInstance());

        return o;
    }
    
    private static String getJsfEl(String value) {
        return "#{" + value + "}";
    }
    
    private static ValueBinding getValueBinding(String el) {
        return getApplication().createValueBinding(el);
    }
    
    private static Application getApplication() {
        ApplicationFactory appFactory = 
            (ApplicationFactory)FactoryFinder.getFactory(FactoryFinder.APPLICATION_FACTORY);
        return appFactory.getApplication();
    }

    /*
   * Internal method to pull out the correct local
   * message bundle
   */

    private static ResourceBundle getBundle() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        UIViewRoot uiRoot = ctx.getViewRoot();
        Locale locale = uiRoot.getLocale();
        ClassLoader ldr = Thread.currentThread().getContextClassLoader();
        return ResourceBundle.getBundle(ctx.getApplication().getMessageBundle(), 
                                        locale, ldr);
    }

    /*
   * Internal method to proxy for resource keys that don't exist
   */

    private static String getStringSafely(ResourceBundle bundle, String key, 
                                          String defaultValue) {
        String resource = bundle.getString(key);
        if (resource == null) {
            if (defaultValue != null) {
                resource = defaultValue;
            } else {
                resource = NO_RESOURCE_FOUND + key;
            }
        }
        return resource;
    }
    
    public static String getActionAttribute(ActionEvent event, String name) {
        return (String) event.getComponent().getAttributes().get(name);
    }
    
    /** Metodo que retorna el FacesMessage con el mensaje
     * 
     * @param msg String mensaje
     * @param severity FacesMessage.Severity
     * 
     * @return FacesMessage
     */
    public static FacesMessage getMessageFromBundleByMessage(String msg, FacesMessage.Severity severity) {
        FacesMessage message = new FacesMessage(msg, null);
        message.setSeverity(severity);
        return message;
    }
		
}

