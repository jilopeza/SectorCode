/*
 * Novatronic S.A.C. Todos los derechos reservados
 * www.novatronic.com
 */
package com.novatronic.components.hsm.exception;

/**
 * 
 * @author Novatronic
 *
 * Class Exception for HSM
 *
 */
public class HSMException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * HSMException constructor
     * @param mensaje 
     */
    public HSMException(String mensaje) {
        super(mensaje);

    }

    /**
     * HSMException constructor
     * @param e Exception
     */
    public HSMException(Exception e) {
        this(e.getMessage());
    }
    
    /**
     * HSMException constructor
     * @param respCode HSMResponseCode
     */
    public HSMException(HSMResponseCode respCode) 
    {
    	super(String.format("%03d: %s ", respCode.getCode(), respCode.getMessage()));
    }

    /**
     * HSMException constructor
     * @param paramName Param name
     * @param respCode HSMResponseCode
     */
    public HSMException(String paramName, HSMResponseCode respCode) 
    { 
    	super(String.format("%03d: %s ", respCode.getCode(), String.format("%s '%s'", respCode.getMessage(), paramName)));
    }
}



