/*
 * Novatronic S.A.C. Todos los derechos reservados
 * www.novatronic.com
 */
package com.novatronic.components.hsm.type;

import com.novatronic.components.hsm.exception.HSMException;
import com.novatronic.components.hsm.exception.HSMResponseCode;

public enum HSMBalanceType {

	BALANCE_AUTO    (1), 
	BALANCE_MANUAL  (2);
    
    private final int type;

    private HSMBalanceType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
    
    public static HSMBalanceType toBalanceType(String balanceName) throws HSMException
    {
        HSMBalanceType balanceType;
        
        try {
            balanceType = HSMBalanceType.valueOf(balanceName);
            
        } catch (IllegalArgumentException e) {
            throw new HSMException(HSMResponseCode.SEC_HSM_BAD_BALANCE_TYPE);
        }
        
        return balanceType;        
    }    

}
