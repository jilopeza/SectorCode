/*
 * Novatronic S.A.C. Todos los derechos reservados
 * www.novatronic.com
 */
package com.novatronic.components.hsm.exception;

public class InvalidPortValueException extends Exception {

    private static final long serialVersionUID = 1L;

    public InvalidPortValueException() {
    }

    public InvalidPortValueException(String mensaje) {
        super(mensaje);
    }
}
