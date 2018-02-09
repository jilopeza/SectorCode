/*
 * Novatronic S.A.C. Todos los derechos reservados
 * www.novatronic.com
 */
package com.novatronic.components.hsm.type;

public enum HSMKeyType {

	KEY_DEK   ("01", "00B"),    // Data Encryption Key
	KEY_ZEK   ("02", "00A"),    // Zone Encryption Key
	KEY_BDK   ("03", "009"),    // Base Derivation Key
	KEY_IPEK  ("04", "302"),    // Initial PIN Encryption Key
	KEY_TPK   ("05", "002"),    // Terminal PIN Key
	KEY_TMK   ("06", "002"),    // Terminal Master Key
	KEY_ZMK   ("07", "000"),    // Zone Master Key
	KEY_ZPK   ("08", "001"),    // Zone PIN Key
	KEY_TEK   ("09", "30B"),    // Terminal Encryption Key
	KEY_TAK   ("10", "003"),    // Terminal Authentication Key
	KEY_ZAK   ("11", "008"),    // Zone Authentication Key
	KEY_MKAC  ("12", "109"),    // Master Key for Application Cryptograms
	KEY_MKSMI ("13", "209"),    // Master Key for Secure Messaging (for Integrity)
	KEY_MKSMC ("14", "309");    // Master Key for Secure Messaging (for Confidentiality)

    private final String type;
    private final String code;

    private HSMKeyType(String type, String code) {
        this.type = type;
        this.code = code;
    }

    public String getType() {
        return type;
    }
    
    public String getCode() {
        return code;
    }

}
