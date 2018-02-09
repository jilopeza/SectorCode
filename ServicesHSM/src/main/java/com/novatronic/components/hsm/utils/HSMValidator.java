/*
 * Novatronic S.A.C. Todos los derechos reservados
 * www.novatronic.com
 */
package com.novatronic.components.hsm.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.novatronic.components.hsm.type.HSMEncryptionModeType;
import com.novatronic.components.hsm.type.HSMKeySchemeType;
import com.novatronic.components.hsm.type.HSMKeyType;

public class HSMValidator {

    static private Pattern pattern;
    static private Matcher matcher;

    private static final String LOCAL_HOST = "localhost";
    private static final String IPADDRESS_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

    public HSMValidator() {
    }

    /**
     * Validate ip address with regular expression
     * 
     * @param ip, ip address for validation
     * @return true valid ip address, false invalid ip address
     */
    public static boolean validateIP(final String ip) 
    {
    	if (ip.compareToIgnoreCase(LOCAL_HOST)==0) return true;
    	
        pattern = Pattern.compile(IPADDRESS_PATTERN);

        matcher = pattern.matcher(ip);
        return matcher.matches();
    }
    
    /**
     * Validate ip address with regular expression
     * 
     * @param ip, ip address for validation
     * @return true valid ip address, false invalid ip address
     */
    public static boolean validatePort(final Integer port) {
        if ( port == 0 || port < 0 || port > 65535)
            return false;
        
        return true;
    }
    
    public static boolean validateKeyDataType(HSMKeyType keyType) {

        switch(keyType)
        {
            case KEY_DEK  : // DEK
            case KEY_ZEK  : // ZEK
            case KEY_BDK  : // BDK        
            case KEY_TEK  : // TEK
                return true;
            default : ;
        }

        return false;
    }
    
    public static boolean validateKeyMACType(HSMKeyType keyType) {

        switch(keyType)
        {
            case KEY_TAK  : // TAK
            case KEY_ZAK  : // ZAK
                return true;
            default : ;
        }

        return false;
    }

    public static boolean validateKeyLength(HSMKeySchemeType keyScheme, String keyValue ) {
        
        switch(keyScheme)
        {
            case SINGLE_LENGTH : 
                if ( keyValue.length() != 16 )
                    return false;
                break;
                
            case DOUBLE_LENGTH :         
                if ( keyValue.length() != 33 )
                    return false;
                break;

            case TRIPLE_LENGTH :
                if ( keyValue.length() != 49 )
                    return false;
        }

        return true;
    }
    
    public static boolean validateInitialVector(HSMEncryptionModeType modeType, String InitialVector ) {

        switch(modeType)
        {
            case M_ECB   :
                if ( !StringUtil.isEmpty(InitialVector) ) // Not requires an IV
                    return false;
                break;
                
            case M_CBC   :
            case M_CFB8  :
            case M_CFB64 :
                if ( StringUtil.isEmpty(InitialVector) ) // Requires an IV
                    return false;
        }

        return true;
    }
    
}
