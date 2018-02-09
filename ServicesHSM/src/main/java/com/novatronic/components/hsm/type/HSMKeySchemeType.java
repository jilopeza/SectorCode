/*
 * Novatronic S.A.C. Todos los derechos reservados
 * www.novatronic.com
 */
package com.novatronic.components.hsm.type;

public enum HSMKeySchemeType {

	SINGLE_LENGTH (0, "Z", 16),
	DOUBLE_LENGTH (1, "U", 32),
	TRIPLE_LENGTH (2, "T", 48);
    
    private final int scheme;
    private final String code;
    private final int length;

    private HSMKeySchemeType(int scheme, String code, int length) {
        this.scheme = scheme;
        this.code = code;
        this.length = length;
    }

    public int getScheme() {
        return scheme;
    }

    public String getCode() {
        return code;
    }

    public int getLength() {
        return length;
    }

}
