/*
 * Novatronic S.A.C. Todos los derechos reservados
 * www.novatronic.com
 */
package com.novatronic.components.hsm.thales;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.novatronic.components.hsm.exception.HSMException;
import com.novatronic.components.hsm.exception.HSMResponseCode;
import com.novatronic.components.hsm.params.HSMParameters;
import com.novatronic.components.hsm.utils.HSMValidator;

public class HSMThalesCommand8000 extends HSMThalesCommand {

    protected static final Logger LOG = LoggerFactory.getLogger(HSMThalesCommand8000.class);

    public HSMThalesCommand8000() {
        super();
    }

    /*
     * Format the generate mac command
     * @see com.novatronic.components.hsm.HSMHostCommand#getGenMACCommand(com.novatronic.components.hsm.params.HSMParameters)
     */
    @Override
    public String getGenMACCommand(HSMParameters params)
            throws HSMException {
        String command = "";
        
        // Validate parameters
        if ( !HSMValidator.validateKeyMACType(params.getKeySourceType()) ) {
            throw new HSMException(HSMResponseCode.SEC_HSM_BAD_KEY_TYPE);
        }
        
        if ( !HSMValidator.validateKeyLength(params.getKeySchemeLength(), params.getKeySourceValue()) ) {
            throw new HSMException(HSMResponseCode.SEC_HSM_BAD_KEY_LENGTH);
        }
        
        try {
            command =  String.format ( "%sM600%d11%s%s%04X%s%%%02d%c%s",
                                    getHdrCommand("gmac"),    
                                    params.getLengthMACType().getCode(), 
                                    params.getKeySourceType().getCode(), 
                                    params.getKeySourceValue(),  
                                    params.getDataBuffer().toString().getBytes("UTF8").length,
                                    params.getDataBuffer(), 
                                    getIdxLMK().intValue(), 
                                    HSMThalesCommand.END_MESSAGE_DELIMITER, 
                                    getIdCommand() );
        } catch (UnsupportedEncodingException e) {
            throw new HSMException(e);
        }
        
        LOG.info ( String.format("GenMACCommand: [%8.8s]", command) );
       

        return command;
    }

    /*
     * Format the validate mac command
     * @see com.novatronic.components.hsm.HSMHostCommand#getValMACCommand(com.novatronic.components.hsm.params.HSMParameters)
     */
    @Override
    public String getValMACCommand(HSMParameters params)
            throws HSMException {
        String command = "";

        // Validate parameters
        if ( !HSMValidator.validateKeyMACType(params.getKeySourceType()) ) {
            throw new HSMException(HSMResponseCode.SEC_HSM_BAD_KEY_TYPE);
        }

        if ( !HSMValidator.validateKeyLength(params.getKeySchemeLength(), params.getKeySourceValue()) ) {
            throw new HSMException(HSMResponseCode.SEC_HSM_BAD_KEY_LENGTH);
        }
    
        try {
            command =  String.format ( "%sM800%d11%s%s%04X%s%s%%%02d%c%s",
                                    getHdrCommand("vmac"),
                                    params.getLengthMACType().getCode(),
                                    params.getKeySourceType().getCode(),
                                    params.getKeySourceValue(),
                                    params.getDataBuffer().toString().getBytes("UTF8").length,
                                    params.getDataBuffer(),
                                    params.getMacValue(),
                                    getIdxLMK().intValue(),
                                    HSMThalesCommand.END_MESSAGE_DELIMITER,
                                    getIdCommand() );
        } catch (UnsupportedEncodingException e) {
            throw new HSMException(e);
        }
        
        LOG.info ( String.format("getValMACCommand: [%8.8s]", command) );
        
        return command;
    }

    /*
     * Format the translate mac command
     * @see com.novatronic.components.hsm.HSMHostCommand#getTranslateMACCommand(com.novatronic.components.hsm.params.HSMParameters)
     */
    @Override
    public String getTranslateMACCommand(HSMParameters params)
            throws HSMException {
        String command = "";

        // Validate parameters
        if ( !HSMValidator.validateKeyMACType(params.getKeySourceType()) ) {
            throw new HSMException(HSMResponseCode.SEC_HSM_BAD_KEY_TYPE);
        }

        if ( !HSMValidator.validateKeyMACType(params.getKeyTargetType()) ) {
            throw new HSMException(HSMResponseCode.SEC_HSM_BAD_KEY_TYPE);
        }
        
        if ( !HSMValidator.validateKeyLength(params.getKeySchemeLength(), params.getKeySourceValue()) ) {
            throw new HSMException(HSMResponseCode.SEC_HSM_BAD_KEY_LENGTH);
        }

        if ( !HSMValidator.validateKeyLength(params.getKeySchemeLength(), params.getKeyTargetValue()) ) {
            throw new HSMException(HSMResponseCode.SEC_HSM_BAD_KEY_LENGTH);
        }
        
        try {
            command =  String.format ( "%sMY00%d11%s%s%d11%s%s%04X%s%s%%%02d%c%s",
                                    getHdrCommand("tmac"),
                                    params.getLengthMACType().getCode(),
                                    params.getKeySourceType().getCode(),
                                    params.getKeySourceValue(),
                                    params.getLengthMACType().getCode(),
                                    params.getKeyTargetType().getCode(),
                                    params.getKeyTargetValue(),
                                    params.getDataBuffer().toString().getBytes("UTF8").length,
                                    params.getDataBuffer(),
                                    params.getMacValue(),
                                    getIdxLMK().intValue(),
                                    HSMThalesCommand.END_MESSAGE_DELIMITER,
                                    getIdCommand() );
        } catch (UnsupportedEncodingException e) {
            throw new HSMException(e);
        }
        
        LOG.info ( String.format("getTranslateMACCommand: [%8.8s]", command) );

        return command;
    }    

}
