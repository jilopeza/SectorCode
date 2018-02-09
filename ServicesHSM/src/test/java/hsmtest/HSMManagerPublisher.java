/**
 * 
 */
package hsmtest;

import javax.xml.ws.Endpoint;

import com.novatronic.components.services.impl.HSMServiceManagerImpl;

/**
 * @author Administrador
 *
 */
public class HSMManagerPublisher {

	/**
	 * 
	 */
	public HSMManagerPublisher() 
	{
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		Endpoint.publish("http://localhost:8080/ServicesHSM-0.0.1-SNAPSHOT/hsmmanager", new HSMServiceManagerImpl());
	}

}
