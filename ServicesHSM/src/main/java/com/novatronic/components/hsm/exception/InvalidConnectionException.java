package com.novatronic.components.hsm.exception;

/**
 * Class of invalid connection exception
 * @author Josue
 *
 */
public class InvalidConnectionException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * InvalidConnectionException constructor
     */
    public InvalidConnectionException() {
    }

	/**
	 * InvalidConnectionException contructor    
	 * @param mensaje message exception
	 */
    public InvalidConnectionException(String mensaje) {
        super(mensaje);
    }
}
