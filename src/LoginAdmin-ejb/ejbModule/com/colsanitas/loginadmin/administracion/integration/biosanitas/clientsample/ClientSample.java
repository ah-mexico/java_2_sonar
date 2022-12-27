package com.colsanitas.loginadmin.administracion.integration.biosanitas.clientsample;

import com.colsanitas.loginadmin.administracion.integration.biosanitas.*;

public class ClientSample {

	public static void main(String[] args) throws BioSanitasWSException_Exception, PrestadorNotFoundExcepcion_Exception, PrestadorNotMigratedException_Exception {
	        System.out.println("***********************");
	        System.out.println("Create Web Service Client...");
	        LoginAdminEJBWSService service1 = new LoginAdminEJBWSService();
	        System.out.println("Create Web Service...");
	        LoginAdminEJBWS port1 = service1.getLoginAdminEJBWSPort();
	        System.out.println("Call Web Service Operation...");
	        System.out.println("Server said: " + port1.consultaDatosPrestadorByID(null));
	        //Please input the parameters instead of 'null' for the upper method!
	
	        System.out.println("Server said: " + port1.consultaSucursalesPrestadorByID(null));
	        //Please input the parameters instead of 'null' for the upper method!
	
	        System.out.println("***********************");
	        System.out.println("Call Over!");
	}
}
