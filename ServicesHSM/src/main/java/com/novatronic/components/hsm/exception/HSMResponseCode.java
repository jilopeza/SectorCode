/*
 * Novatronic S.A.C. Todos los derechos reservados
 * www.novatronic.com
 */
package com.novatronic.components.hsm.exception;

/**
 * 
 * @author Novatronic
 * Enum constant for HSMResponseCode
 */
public enum HSMResponseCode {

	SEC_HSM_UNDEFINED_ERROR_MESSAGE    (  -1, "Message response not defined"                                          ),
	SEC_HSM_RCOK                       (0x00, "Successful operation"                                                  ), 
	SEC_HSM_BAD_MAC_VERIFICATION       (0x01, "Verification failure or warning of imported key parity error"          ), 
	SEC_HSM_BAD_MODE_FLAG_FIELD        (0x02, "Invalid mode flag field"                                               ), 
	SEC_HSM_BAD_IN_FMT_OR_MAC_ALGORITHM(0x03, "Invalid input format flag field"                                       ), 
	SEC_HSM_BAD_OUTPUT_FORMAT          (0x04, "Invalid output format flag field"                                      ), 
	SEC_HSM_BAD_KEY_TYPE               (0x05, "Invalid key type field"                                                ), 
	SEC_HSM_BAD_MESSAGE_LENGTH         (0x06, "Invalid message length field"                                          ), 
	SEC_HSM_BAD_DESTINAT_KEY_TYPE      (0x07, "Invalid destination mode flag field"                                   ), 
	SEC_HSM_BAD_DESTINAT_MAC_ALGORITHM (0x08, "Invalid destination key type field"                                    ), 
	SEC_HSM_KEY_PARITY_ERROR           (0x0A, "Decryption key parity error"                                           ), 
	SEC_HSM_DESTINATION_MAC_KEY_PARITY (0x0B, "Encryption key parity error"                                           ), 
	SEC_HSM_MESSAGE_LENGTH_IS_TOO_LONG (0x0F, "Actual message length is too long"                                     ), 
	SEC_HSM_ILLEGAL_MESSAGE_FORMAT     (0x35, "Illegal message format"                                                ), 
	SEC_HSM_BAD_CONNECTION             (0xE0, "HSM can not be connected"                                              ), 
	SEC_HSM_BAD_KEY_LENGTH             (0xE1, "Invalid key length"                                                    ), 
	SEC_HSM_BAD_NOT_HSM                (0xE2, "HSM not supported"                                                     ), 
	SEC_HSM_BAD_CONNECTION_TYPE        (0xE3, "Invalid connection type "                                              ), 
	SEC_HSM_BAD_NOT_HSM_CONNECTION     (0xE4, "There are no connections enabled in the HSM"                           ), 
	SEC_HSM_BAD_TIMEOUT                (0xE5, "HSM elapsed timeout"                                                   ), 
	SEC_HSM_BAD_SERVER_MODEL           (0xE6, "Invalid HSM server model"                                              ), 
	SEC_HSM_BAD_BALANCE_TYPE           (0xE7, "Invalid balance type "                                                 ), 
	SEC_HSM_BAD_ENVIRONMENT_TYPE       (0xE8, "Invalid environment type"                                              ), 
	SEC_HSM_BAD_NO_SERVERS             (0xE9, "No HSM servers have been defined"                                      ), 
	SEC_HSM_BAD_LENGTH_COMMAND         (0xEA, "Invalid response length"                                               ), 
	SEC_HSM_BAD_RSP_CMD_CODE           (0xEB, "Command code does not correspond to requirement"                       ), 
	SEC_HSM_MODE_UNSUPPORT_IV          (0xEC, "Mode or algorithm of encryption does not support initialization vector"), 
	SEC_HSM_ALTERNATIVE_IS_UNDEFINED   (0xEE, "No alternative server has been defined"                                ), 
	SEC_HSM_SERVERS_ID_EQUALS          (0xEF, "The HSM servers id must be different"                                  ), 
	SEC_HSM_SERVERS_IP_EQUALS          (0xF0, "The HSM servers ip must be different"                                  ), 
	SEC_HSM_NO_SERVERS_DEFINED         (0xF1, "No HSM server is defined"                                              ),
	SEC_HSM_BAD_LENGTH_PARAM           (0xF2, "Invalid parameter length or parameter does not exist"                  ),
	SEC_HSM_BAD_NULL_ARGUMENT          (0xF3, "Invalid or Null value not allowed in parameter"                        ),
	SEC_HSM_MODE_REQUIRES_IV           (0xF4, "Mode or algorithm of encryption requires initialization vector"        ); 
	
	
    private final int code;
    private final String message;

    /**
     * HSMResponseCode contructor
     * @param code code of response code
     * @param message message of response code
     */
    HSMResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Get code of HSMResponseCode
     * @return HSMResponseCode
     */
    public int getCode() {
        return code;
    }

    /**
     * Get message of HSMResponseCode
     * @return Message of HSMResponseCode
     */
    public String getMessage() {
        return message;
    }

    /**
     * HSMResponseCode for response code
     * @param responseCode response code
     * @return HSMResponseCode
     */
    public static HSMResponseCode toResponseCode(String responseCode) {
        HSMResponseCode rc = SEC_HSM_UNDEFINED_ERROR_MESSAGE;

        for (HSMResponseCode rspCode : HSMResponseCode.values()) {
            if (rspCode.getCode() == Integer.valueOf(responseCode))
                rc = rspCode;
        }
        
        return rc;
    }
};
