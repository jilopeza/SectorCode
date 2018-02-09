/*
 * Novatronic S.A.C. Todos los derechos reservados
 * www.novatronic.com
 */
package com.novatronic.components.hsm.exception;

/**
 * @author Novatronic
 *
 */
public class InvalidIPFormatException extends Exception {

    private static final long serialVersionUID = 1L;

    public InvalidIPFormatException() {
    }

    public InvalidIPFormatException(String mensaje) {
        super(mensaje);
    }

}
