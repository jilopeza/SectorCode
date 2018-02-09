/*
 * Novatronic S.A.C. Todos los derechos reservados
 * www.novatronic.com
 */
package com.novatronic.components.hsm.type;

public enum HSMMACLengthType {

	MAC_08 (0, 8),
	MAC_16 (1, 16);
    
    private final int code;
    private final int length;

    private HSMMACLengthType(int code, int length) {
        this.code = code;
        this.length = length;
    }

    public int getCode() {
        return code;
    }

    public int getLength() {
        return length;
    }

}
