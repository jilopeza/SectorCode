/*
 * Novatronic S.A.C. Todos los derechos reservados
 * www.novatronic.com
 */
package com.novatronic.components.hsm.exception;

public class InvalidLMKIdException extends Exception {

    private static final long serialVersionUID = 1L;

    public InvalidLMKIdException() {
        super();
    }

    public InvalidLMKIdException(String mensaje) {
        super(mensaje);
    }

}
