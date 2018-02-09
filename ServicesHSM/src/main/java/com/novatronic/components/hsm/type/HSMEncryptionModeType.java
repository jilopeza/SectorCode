/*
 * Novatronic S.A.C. Todos los derechos reservados
 * www.novatronic.com
 */
package com.novatronic.components.hsm.type;

public enum HSMEncryptionModeType {

	M_ECB    (0), 
	M_CBC    (1),
	M_CFB8   (2),
	M_CFB64  (3);
    
    private final int mode;

    private HSMEncryptionModeType(int mode) {
        this.mode = mode;
    }

    public int getMode() {
        return mode;
    }
    
}
