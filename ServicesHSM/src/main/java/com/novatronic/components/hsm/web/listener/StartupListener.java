package com.novatronic.components.hsm.web.listener;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Paths;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.novatronic.components.hsm.connection.ClienteHSMSocket;
import com.novatronic.components.hsm.connection.HSMSocketManager;
import com.novatronic.components.hsm.exception.HSMException;

@WebListener
public class StartupListener implements ServletContextListener {

    /**
     * {@inheritDoc}
     */
    synchronized public void contextInitialized(ServletContextEvent event) {
    	
    	System.out.println("Inicializando contexto...");
    	  
    	ServletContext context = event.getServletContext();
    	 
		
    	String hsmServicesFolder = System.getenv("HSM_SERVICES_FOLDER");
    	
    	context.setAttribute("HSM_SERVICES_FOLDER", hsmServicesFolder );
    	context.setAttribute("HSM_RELATIVE_FOLDER", context.getContextPath());
 
    	try 
		{
			System.out.println(hsmServicesFolder);
			
			if (hsmServicesFolder == null)
				throw new HSMException("No se encuentra la carpeta de configuración");
				
			String hsmServersXMLFile = Paths.get(hsmServicesFolder, "hsm", "config", "hsm-servers.xml").toString();
			
			HSMSocketManager.setInputXMLFile( new FileInputStream(new File(hsmServersXMLFile)) );
			HSMSocketManager.serverInit(HSMSocketManager.getInputXMLFile());
		} 
		catch (Exception e_1) 
		{
			try 
			{
				HSMSocketManager.setInputXMLFile(  ClienteHSMSocket.class.getClassLoader().getResourceAsStream("hsm-servers.xml") );
				HSMSocketManager.serverInit(HSMSocketManager.getInputXMLFile());
			} 
			catch (Exception e_2) 
			{
				e_1.printStackTrace();
			}
		}

        setupContext(context);

        
    }

    /**
     * @param context The servlet context
     */
    public static void setupContext(ServletContext context) 
    {
    }


    /**
     * @param servletContextEvent 
     */
    public void contextDestroyed(ServletContextEvent servletContextEvent) 
    {
        //LogFactory.release(Thread.currentThread().getContextClassLoader());
        //Commented out the above call to avoid warning when SLF4J in classpath.
        //WARN: The method class org.apache.commons.logging.impl.SLF4JLogFactory#release() was invoked.
        //WARN: Please see http://www.slf4j.org/codes.html for an explanation.
    }
}
