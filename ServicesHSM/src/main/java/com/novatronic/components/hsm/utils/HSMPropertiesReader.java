/*
 * Novatronic S.A.C. Todos los derechos reservados
 * www.novatronic.com
 */
package com.novatronic.components.hsm.utils;

import java.util.Properties;

import com.novatronic.components.hsm.exception.HSMException;
import com.novatronic.components.hsm.exception.HSMResponseCode;

public class HSMPropertiesReader {

    static public String readParam (Properties conf, String param) 
            throws HSMException {
        
        String paramValue = conf.getProperty(param);
        
        if ( paramValue == null || paramValue.trim().length() == 0 )
        {
            throw new HSMException(HSMResponseCode.SEC_HSM_BAD_LENGTH_PARAM);
        }
        
        return paramValue.trim();
    }    
}
