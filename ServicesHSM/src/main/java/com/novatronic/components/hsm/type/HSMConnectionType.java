/*
 * Novatronic S.A.C. Todos los derechos reservados
 * www.novatronic.com
 */
package com.novatronic.components.hsm.type;

import com.novatronic.components.hsm.exception.HSMException;
import com.novatronic.components.hsm.exception.HSMResponseCode;

public enum HSMConnectionType {

    FAST_CONNECT  (0),
    FULL_SESSION  (1);
    
    private final int type;

    private HSMConnectionType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
    
    static public HSMConnectionType toConnectionType(String connName)
            throws HSMException
    {
        HSMConnectionType connType;
        
        try {
            connType = HSMConnectionType.valueOf(connName);
            
        } catch (IllegalArgumentException e) {
            throw new HSMException(HSMResponseCode.SEC_HSM_BAD_CONNECTION_TYPE);
        }
        
        return connType;        
    }
}
