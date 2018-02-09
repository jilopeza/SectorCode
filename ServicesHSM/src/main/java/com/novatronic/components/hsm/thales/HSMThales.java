/*
 * Novatronic S.A.C. Todos los derechos reservados
 * www.novatronic.com
 */
package com.novatronic.components.hsm.thales;

import com.novatronic.components.hsm.exception.InvalidIPFormatException;
import com.novatronic.components.hsm.exception.InvalidLMKIdException;
import com.novatronic.components.hsm.exception.InvalidPortValueException;
import com.novatronic.components.hsm.host.impl.HardwareSecurityModuleImpl;

public class HSMThales extends HardwareSecurityModuleImpl {
    
    public HSMThales(String ipHSM, Integer portHSM, String hsmModelName)
            throws InvalidIPFormatException, InvalidPortValueException,
            InvalidLMKIdException {
        super(ipHSM, portHSM, hsmModelName);
    }

    
}
