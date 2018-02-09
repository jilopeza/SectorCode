/*
 * Novatronic S.A.C. Todos los derechos reservados
 * www.novatronic.com
 */
package com.novatronic.components.hsm.type;

public enum HSMCommandType {
    
	CMD_ECHO_TEST      (1, "__@@_EC", "ECHO_TEST"),       // NC
	CMD_GENERATE_KEY   (2, "__@@_GK", "GENERATE_KEY" ),   // A0
	CMD_ENCODE_DATA    (3, "__@@_ED", "ENCODE_DATA" ),    // M0
	CMD_DECODE_DATA    (4, "__@@_DD", "DECODE_DATA" ),    // M2
	CMD_TRANSLATE_DATA (5, "__@@_TD", "TRANSLATE_DATA" ), // M4
	CMD_GENERATE_MAC   (6, "__@@_GM", "GENERATE_MAC" ),   // M6
	CMD_VALIDATE_MAC   (7, "__@@_VM", "VALIDATE_MAC" ),   // M8
	CMD_TRANSLATE_MAC  (8, "__@@_TM", "TRANSLATE_MAC" );  // MY
    
    Integer code;
    String id;
    String commandName;

    private HSMCommandType(Integer code, String id, String commandName) {
        this.code = code;
        this.id = id;
        this.commandName = commandName;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

	public String getCommandName() {
		return commandName;
	}

	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}

}
