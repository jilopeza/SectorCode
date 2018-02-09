/*
 * Novatronic S.A.C. Todos los derechos reservados
 * www.novatronic.com
 */
package com.novatronic.components.hsm.type;

import com.novatronic.components.hsm.exception.HSMException;
import com.novatronic.components.hsm.exception.HSMResponseCode;

public enum HSMEnvironmentType 
{
    ENV_PRODUCTION    (1, "Production Environment"),
    ENV_CONTINGENCY   (2, "Contingency Environment"),
    ENV_DEVELOPMENT   (3, "Development Environment"),
    ENV_CERTIFICATION (4, "Certification Environment");
    
    Integer code;
    String name;
    
    private HSMEnvironmentType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    static public HSMEnvironmentType toEnvironmentType(String envName)
            throws HSMException
    {
        HSMEnvironmentType envType;
        
        try {
            envType = HSMEnvironmentType.valueOf(envName);
            
        } catch (IllegalArgumentException e) {
            throw new HSMException(HSMResponseCode.SEC_HSM_BAD_ENVIRONMENT_TYPE);
        }
        
        return envType;        
    }    
    
}
