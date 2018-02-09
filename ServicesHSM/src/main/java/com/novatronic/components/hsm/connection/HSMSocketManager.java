/*
 * Novatronic S.A.C. Todos los derechos reservados
 * www.novatronic.com
 */
package com.novatronic.components.hsm.connection;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.novatronic.components.hsm.exception.HSMException;
import com.novatronic.components.hsm.host.HardwareSecurityModule;
import com.novatronic.components.hsm.params.HSMParameters;
import com.novatronic.components.hsm.utils.HSMXmlReader;


/**
 * Socket Manager Servers
 * @author Novatronic
 *
 */

public  final class HSMSocketManager {

    protected  static final	Logger LOG = LoggerFactory.getLogger(HSMSocketManager.class);
    
    private static ClienteHSMSocket 			 clienteHSMSocket ;
    private static List <HardwareSecurityModule> lstServers ;
    private static InputStream 					 inputXMLFile ;
    
    /**
     * 
     * @param hsmServersXMLFile
     * @throws HSMException
     * @throws JAXBException
     */
    public static void serverInit(String hsmServersXMLFile)   throws HSMException, JAXBException 
    {
    	LOG.info("Iniciando conexion a servidores HSM definidos...:" + hsmServersXMLFile);
        final InputStream inputXMLFile = HSMSocketManager.class.getClassLoader().getResourceAsStream(hsmServersXMLFile);
        HSMSocketManager.serverInit(inputXMLFile);
    }
    
    /**
     * Config servers from from XML file.
     * @param inputXMLFile Xml File
     * @throws HSMException
     * @throws JAXBException
     */
    public static void serverInit(InputStream inputXMLFile)   throws HSMException, JAXBException {

    	LOG.info("Cargando propiedades de los servidores");
       	lstServers = HSMXmlReader.loadHSMServers(inputXMLFile);

        // getting pool connection
        try 
        {
        	LOG.info("Iniciando conexion a servidores HSM definidos...");
            List<ClienteHSMSocketContent> hsmS = new ArrayList<ClienteHSMSocketContent>();
            
            // init ClienteHSMSocket 
            for ( HardwareSecurityModule server : lstServers )
            {
            	hsmS.add(new ClienteHSMSocketContent(server, null, 0 ));
            }
            
            clienteHSMSocket  = new ClienteHSMSocket(hsmS) ;
        } 
        catch (Exception e) 
        {
            throw new HSMException(e);
        }
	}
    
    /**
     * Release server
     * @throws HSMException
     */
    public static  void serverRelease() throws HSMException
    {
        try 
        {
            if (clienteHSMSocket != null) 
            {
            	clienteHSMSocket = null;
            }
        } 
        catch (Exception e) 
        {
            throw new HSMException(e);
        }
    }
    
    /**
     * Force release to server
     * @throws HSMException 
     */
    public static  void serverForceRelease() throws HSMException
    {
        try 
        {
            if (clienteHSMSocket != null) 
            {
            	clienteHSMSocket = null;
            }
        } 
        catch (Exception e) 
        {
            throw new HSMException(e);
        }
    }

    
    /**
     * Send and Receive parameters
     * @param params Request parameters value
     * @return Response parameters value
     * @throws HSMException
     */
    public static  HSMParameters sendReceive(HSMParameters params) throws HSMException 
    {
        HSMParameters responseParameters = null;
        try 
        {
        	responseParameters = clienteHSMSocket.sendReceive(params);
        } 
        catch (Exception e) 
        {
        	throw new HSMException(e);
        }
        finally 
        {
        }
        return responseParameters;
    }    
 
    /**
     * Get InputXM LFile
     * @return InputStream data
     */
    public static InputStream getInputXMLFile() {
		return inputXMLFile;
	}

    /**
     * set InputX MLFile
     * @param inputXMLFile InputStream data
     */
	public static void setInputXMLFile(InputStream inputXMLFile) {
		HSMSocketManager.inputXMLFile = inputXMLFile;
	}

}
 