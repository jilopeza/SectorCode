package com.novatronic.components.hsm.utils;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.novatronic.components.hsm.bean.ApiHSMConfig;
import com.novatronic.components.hsm.bean.HsmServer;
import com.novatronic.components.hsm.exception.HSMException;
import com.novatronic.components.hsm.exception.HSMResponseCode;
import com.novatronic.components.hsm.host.HardwareSecurityModule;
import com.novatronic.components.hsm.host.impl.HSMHostCommandImpl;
import com.novatronic.components.hsm.host.impl.HSMHostResponseImpl;
import com.novatronic.components.hsm.host.impl.HardwareSecurityModuleImpl;
import com.novatronic.components.hsm.type.HSMBalanceType;
import com.novatronic.components.hsm.type.HSMConnectionType;
import com.novatronic.components.hsm.type.HSMEnvironmentType;

public class HSMXmlReader {
    
    protected static final Logger LOG = LoggerFactory.getLogger(HSMXmlReader.class);
    
    static private ApiHSMConfig apiHSMConfig = null;

	public HSMXmlReader() {
		
	}
		
	static public void init(InputStream inputXMLFile)
		throws JAXBException {

		// Se carga el XML que define los servidores HSM
    	JAXBContext jc = JAXBContext.newInstance(ApiHSMConfig.class);
		Unmarshaller u = jc.createUnmarshaller();

		HSMXmlReader.apiHSMConfig = (ApiHSMConfig)u.unmarshal(inputXMLFile);
        
	}
	
//	static public General loadApiHSMGeneralConfig(InputStream inputXMLFile) {
//		
//		if (HSMXmlReader.apiHSMConfig == null)
//			HSMXmlReader.init(inputXMLFile);        
//
//		return apiHSMConfig.getGeneral();
//	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	static public List<HardwareSecurityModule> loadHSMServers (InputStream inputXMLFile) 
		throws HSMException, JAXBException {
		
		if (HSMXmlReader.apiHSMConfig == null)
			HSMXmlReader.init(inputXMLFile);        

		List <HardwareSecurityModule> lstServers = new ArrayList <HardwareSecurityModule>();
        Iterator<HsmServer>it = apiHSMConfig.getServers().getHsmServer().iterator();
        
		try {
            while ( it.hasNext() ) {
                
                HsmServer svr =  it.next();
                
                if ( !(svr.getLenHeader() >= 1 && svr.getLenHeader()  <= 255) )
                    throw new HSMException("La longitud del header (<lenHeader/>) debe ser un valor entre 1 y 255..");
                
                // Creating constructor arguments
                Class[] hsmArgsClass = new Class[] { String.class, Integer.class, String.class };
                Object[] hsmArgsValues = new Object[] { svr.getIp(), svr.getPort(), svr.getModelName() };

                // Getting the instance server
                Class classHSM = Class.forName(svr.getClassModelName());            // Getting the HSM class name
                Constructor constructor = classHSM.getConstructor(hsmArgsClass);    // Creating the constructor
                HardwareSecurityModuleImpl hsm = (HardwareSecurityModuleImpl) constructor.newInstance(hsmArgsValues);
                
                // Getting the instance of request commands
                Class classHCReq = Class.forName(svr.getClassRequestName());    // Getting the HSM Request command class name
                HSMHostCommandImpl hcReq = (HSMHostCommandImpl)classHCReq.newInstance();
                
                // Getting the instance of response commands
                Class classHCRsp = Class.forName(svr.getClassResponseName());    // Getting the HSM Response commands class name
                HSMHostResponseImpl hcRsp = (HSMHostResponseImpl) classHCRsp
                        .getDeclaredConstructor(Integer.class).newInstance(
                                svr.getLenHeader());
                
                hsm.setHostCMD(hcReq);
                hsm.setHostRSP(hcRsp);
                
                // Set additional Attributes
                hsm.setIdHSM(Integer.parseInt(svr.getId()));
                hsm.setIdxLMK(svr.getIdLMK());
                hsm.setLenHeader(svr.getLenHeader());

                hsm.setConnType(HSMConnectionType.toConnectionType(svr.getConnectionType()));
                hsm.setTimeout(svr.getTimeout());
                hsm.setIntervalEcho(svr.getIntervalEcho());
                hsm.setRetriesNumber(svr.getRetriesNumber());
                hsm.setBalanceType(HSMBalanceType.toBalanceType(svr.getBalanceType()));
                hsm.setPercentWork(svr.getPercentWork());
                hsm.setEnvironmentType(HSMEnvironmentType.toEnvironmentType(svr.getEnvironmentType()));
                hsm.setVersion(svr.getVersion());
                
                // Add server 
                lstServers.add(hsm);

                LOG.info( String.format("[SERVER %d]\n" +                
                            "   Id                  [%s]\n" + 
                            "   Class Name          [%s]\n" + 
                            "   Class Name Request  [%s]\n" + 
                            "   Class Name Response [%s]\n" + 
                            "   Model Name          [%s]\n" + 
                            "   LMK Indetifier      [%02d]\n" + 
                            "   Length Header       [%02d]\n" + 
                            "   Ip                  [%s]\n" + 
                            "   Port Number         [%d]\n" + 
                            "   Connection Type     [%s]\n" + 
                            "   Timeout(sec)        [%d]\n" + 
                            "   Interval Echo(sec)  [%d]\n" + 
                            "   Retries Number(sec) [%d]\n" + 
                            "   Balance Type        [%s]\n" + 
                            "   Percent Work        [%d]\n" + 
                            "   Environment Type    [%s]\n" + 
                            "   Version             [%s]",
                            lstServers.size(),
                            svr.getId(),
                            svr.getClassModelName(),
                            svr.getClassRequestName(),
                            svr.getClassResponseName(),
                            svr.getModelName(),
                            svr.getIdLMK(),
                            svr.getLenHeader(),
                            svr.getIp(),
                            svr.getPort(),
                            svr.getConnectionType(),
                            svr.getTimeout(),
                            svr.getIntervalEcho(),
                            svr.getRetriesNumber(),
                            svr.getBalanceType(),
                            svr.getPercentWork(),
                            svr.getEnvironmentType(),
                            svr.getVersion()) );

                inputXMLFile.close();

            }        
		} catch (Exception e) {
			throw new HSMException(e);
		}
        
        int quantityServers = lstServers.size();
        
        if(quantityServers == 1)
        	LOG.info(HSMResponseCode.SEC_HSM_ALTERNATIVE_IS_UNDEFINED.getMessage());
        
        //Se valida que los servidores asignados no sean los mismos
        if(quantityServers > 1){
	        for(int i = 0; i < quantityServers; i++){
	        	HardwareSecurityModule hsmServerA = lstServers.get(i);
	        	
	        	for(int j = 0; j < quantityServers; j++){
	        		
	        		if(j > i){
	        			HardwareSecurityModule hsmAlternB = lstServers.get(j);
	        			if (hsmServerA.getIdHSM().equals(hsmAlternB.getIdHSM()))
	    					throw new HSMException(HSMResponseCode.SEC_HSM_SERVERS_ID_EQUALS);
	    	
	    				if (hsmServerA.getIpHSM().equals(hsmAlternB.getIpHSM()))
	    					throw new HSMException(HSMResponseCode.SEC_HSM_SERVERS_IP_EQUALS);
	        		}
	            }
	        }
        }

        LOG.info(String.format("'%d' Servidores HSM inicializados correctamente...", lstServers.size()) ); 
        
        return lstServers;
	}

}
