/*
 * Novatronic S.A.C. Todos los derechos reservados
 * www.novatronic.com
 */
package com.novatronic.components.hsm.type;

public enum HSMConnectionStateType {

	CONECT_DESCONECTADO     	(0), 
	CONECT_OPERATIVO        	(1), 
	CONECT_HABILITADO        	(2), 
	CONECT_BLOQUEADO        	(3), 
	CONECT_CIERRE_ERROR       	(4), 
	CONECT_CIERRE_TIMEOUT    	(5);
    
    private final int state;

    private HSMConnectionStateType(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }
    
}
