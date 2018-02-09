/*
 * Novatronic S.A.C. Todos los derechos reservados
 * www.novatronic.com
 */
package com.novatronic.components.hsm.params;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.novatronic.components.hsm.exception.HSMResponseCode;
import com.novatronic.components.hsm.type.HSMCommandType;
import com.novatronic.components.hsm.type.HSMEncryptionModeType;
import com.novatronic.components.hsm.type.HSMKeySchemeType;
import com.novatronic.components.hsm.type.HSMKeyType;
import com.novatronic.components.hsm.type.HSMMACLengthType;
import com.novatronic.components.hsm.utils.StringUtil;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HSMParameters")
public class HSMParameters {
	
    private HSMKeySchemeType       keySchemeLength;    // Esquema de la llave
    private HSMKeyType             keySourceType;      // Tipo de llave a usar
    private String                 keySourceValue;     // Llave utilizada para cifrar la informacion
    private String                 sourceInitVector;   // Vector de iniciacion para cifrado 3DES.
    private HSMEncryptionModeType  encrySourceMode;    // Tipo de algoritmo a utilizar para el cifrado
    private String                 sourceKSN;          // N�mero serial que permite al servidor identificar un equipo POS y validarlo, entre otras cosas

    private HSMKeyType             keyTargetType;      // Tipo de llave a usar
    private String                 keyTargetValue;     // Llave utilizada para traslado
    private String                 targetInitVector;   // Vector de iniciaci�n para cifrado 3DES.
    private HSMEncryptionModeType  encryTargetMode;    // Tipo de algoritmo a utilizar para el cifrado
    private String                 targetKSN;          // Numero serial que permite al servidor identificar un equipo POS y validarlo, entre otras cosas.
    
    private String                 macValue;           // El valor de la MAC
    private String                 keyCheckValue;      // Llave utilizada para cifrar la informacion
    
    private String                 responseCode;       // C�digo de respuesta de la operacion
    private String                 responseMessage;    // Mensaje de respuesta de la operacion
    private HSMResponseCode        responseError;      //
    
    private HSMMACLengthType       lengthMACType;      // Longitud de MAC      
    
    private String                 dataBuffer;         // Buffer de la data
    private HSMCommandType         commandType;        // El comando a ejecutar
    
    public HSMParameters() {
    	
	}

    public HSMParameters(HSMParameters parameter) {
    	
	    this.commandType      = parameter.commandType;
	    this.lengthMACType    = null;
    	this.keySchemeLength  = parameter.getKeySchemeLength();
		this.keySourceType    = null;
		this.keySourceValue   = null;
		
		if (!StringUtil.isEmpty(parameter.getSourceInitVector())) 
			this.sourceInitVector = parameter.sourceInitVector;
		else
			this.sourceInitVector = null;
		this.encrySourceMode  = null;
		this.sourceKSN        = null;

		this.keyTargetType    = null;
		this.keyTargetValue   = null;
		this.targetInitVector = null;
		this.encryTargetMode  = null;
		this.targetKSN        = null;

		this.macValue         = null; 
		this.keyCheckValue    = null; 
		this.responseCode     = null; 
        this.responseMessage  = null; 
	}
    
    // Constructor general
    public HSMParameters(HSMKeySchemeType keySchemeLength,
            HSMKeyType keySourceType, String keySourceValue,
            String sourceInitVector, HSMEncryptionModeType encrySourceMode,
            String sourceKSN, HSMKeyType keyTargetType, String keyTargetValue,
            String targetInitVector, HSMEncryptionModeType encryTargetMode,
            String targetKSN, String keyCheckValue,
            HSMMACLengthType lengthMACType, String macValue, 
            String dataBuffer,
            HSMCommandType commandType) {
        this.keySchemeLength = keySchemeLength;

        this.keySourceType = keySourceType;
        this.keySourceValue = keySourceValue;
        this.sourceInitVector = sourceInitVector;
        this.encrySourceMode = encrySourceMode;
        this.sourceKSN = sourceKSN;
        
        this.keyTargetType = keyTargetType;
        this.keyTargetValue = keyTargetValue;
        this.targetInitVector = targetInitVector;
        this.encryTargetMode = encryTargetMode;
        this.targetKSN = targetKSN;
        this.keyCheckValue = keyCheckValue;
        this.lengthMACType = lengthMACType;
        this.macValue = macValue;
        this.dataBuffer = dataBuffer;
        this.commandType = commandType;
    }

    // Constructor para enviar Echo Test
    // CMD_ECHO_TEST
    public HSMParameters(HSMCommandType commandType) {

        this (null,
                null, null,
                null, null,
                null, null, null,
                null, null,
                null, null,
                null, null, null,
                commandType);
        }
    
    // Constructor para generar llave dinamicamente 
    // CMD_GENERATE_KEY
    public HSMParameters(HSMKeySchemeType keySchemeLength,
            HSMKeyType keySourceType, 
            HSMCommandType commandType) {
        
        this (keySchemeLength,
                keySourceType, null,
                null, null,
                null, null, null,
                null, null,
                null, null,
                null, null, null,
                commandType);
    }    
    
    // Constructor para cifrar y descifrar data sin vector de inicializacion
    // CMD_ENCODE_DATA
    // CMD_DECODE_DATA
    public HSMParameters(HSMKeySchemeType keySchemeLength,
            HSMKeyType keySourceType, String keySourceValue,
            HSMEncryptionModeType encrySourceMode,
            String dataBuffer,
            HSMCommandType commandType) {
        
        this (keySchemeLength,
              keySourceType, keySourceValue, null, encrySourceMode,
              null, null, null,
              null, null,
              null, null, null,
              null, dataBuffer, 
              commandType);
    }

    // Constructor para cifrar y descifrar data con vector de inicializacion
    // CMD_ENCODE_DATA
    // CMD_DECODE_DATA
    public HSMParameters(HSMKeySchemeType keySchemeLength,
            HSMKeyType keySourceType, String keySourceValue,
            String sourceInitVector, HSMEncryptionModeType encrySourceMode,
            String dataBuffer,
            HSMCommandType commandType) {
        
        this (keySchemeLength,
              keySourceType, keySourceValue, sourceInitVector, encrySourceMode,
              null, null, null,
              null, null,
              null, null, null,
              null, dataBuffer, 
              commandType);
    }
    
    // Constructor para trasladar data en mismo modo ECB con diferentes llaves
    // CMD_TRANSLATE_DATA
    public HSMParameters(HSMKeySchemeType keySchemeLength,
            HSMKeyType keySourceType, String keySourceValue,
            HSMKeyType keyTargetType, String keyTargetValue,
            String dataBuffer,
            HSMCommandType commandType) {
    
        this (keySchemeLength,
                keySourceType, keySourceValue, null, HSMEncryptionModeType.M_ECB,
                null, keyTargetType, keyTargetValue,
                null, HSMEncryptionModeType.M_ECB,
                null, null, null,
                null, dataBuffer, 
                commandType);
    }

    // Constructor para trasladar data en mismo modo CBC con diferentes llaves
    // CMD_TRANSLATE_DATA
    public HSMParameters(HSMKeySchemeType keySchemeLength,
            HSMKeyType keySourceType, String keySourceValue,
            String sourceInitVector,
            HSMKeyType keyTargetType, String keyTargetValue,
            String dataBuffer,
            HSMCommandType commandType) {
    
        this (keySchemeLength,
                keySourceType, keySourceValue, sourceInitVector, HSMEncryptionModeType.M_CBC,
                null, keyTargetType, keyTargetValue,
                null, HSMEncryptionModeType.M_CBC,
                null, null, null,
                null, dataBuffer, 
                commandType);
    }
    
    
    // Constructor para generar MAC sin vector de inicializacion
    // CMD_GENERATE_MAC
    public HSMParameters(HSMKeySchemeType keySchemeLength,
            HSMKeyType keySourceType, String keySourceValue,
            String sourceInitVector,
            HSMMACLengthType lengthMACType, String dataBuffer,
            HSMCommandType commandType) {
        
        this (keySchemeLength,
                keySourceType, keySourceValue, sourceInitVector, 
                null, null, null, 
                null, null, null,
                null, null, 
                lengthMACType, null,
                dataBuffer,
                commandType);
    }
    
    // Constructor para generar MAC con vector de inicializacion
    // CMD_GENERATE_MAC
    public HSMParameters(HSMKeySchemeType keySchemeLength,
            HSMKeyType keySourceType, String keySourceValue,
            HSMMACLengthType lengthMACType, String dataBuffer,
            HSMCommandType commandType) {
        
        this (keySchemeLength,
                keySourceType, keySourceValue,
                null, null,
                null, null, null,
                null, null,
                null, null, 
                lengthMACType, null,
                dataBuffer,
                commandType);
    }
    
    // Constructor para validar una  MAC sin vector de inicializacion
    // CMD_VALIDATE_MAC
    public HSMParameters(HSMKeySchemeType keySchemeLength,
            HSMKeyType keySourceType, String keySourceValue,
            HSMMACLengthType lengthMACType, String macValue, 
            String dataBuffer,
            HSMCommandType commandType) {
        
        this (keySchemeLength,
                keySourceType, keySourceValue,
                null, null,
                null, null, null,
                null, null,
                null, null,
                lengthMACType, macValue,
                dataBuffer,
                commandType); 
    }

    // Constructor para validar una  MAC con vector de inicializacion
    // CMD_VALIDATE_MAC
    public HSMParameters(HSMKeySchemeType keySchemeLength,
            HSMKeyType keySourceType, String keySourceValue,
            String sourceInitVector,
            HSMMACLengthType lengthMACType, String macValue, 
            String dataBuffer,
            HSMCommandType commandType) {
        
        this (keySchemeLength,
                keySourceType, keySourceValue, sourceInitVector, 
                null, null, null, 
                null, null, null,
                null, null,
                lengthMACType, macValue,
                dataBuffer,
                commandType); 
    }
   
    
    // Constructor para trasladar una  MAC sin vector de inicializacion
    // CMD_TRANSLATE_MAC
    public HSMParameters(HSMKeySchemeType keySchemeLength,
            HSMKeyType keySourceType, String keySourceValue,
            HSMKeyType keyTargetType, String keyTargetValue,
            HSMMACLengthType lengthMACType, String macValue,
            String dataBuffer,
            HSMCommandType commandType)
    {
        this (keySchemeLength,
                keySourceType, keySourceValue,
                null, null, null, 
                keyTargetType, keyTargetValue,
                null, null,
                null, null,
                lengthMACType, macValue, 
                dataBuffer,
                commandType);
    }
    
    // Constructor para trasladar una  MAC con vector de inicializacion
    // CMD_TRANSLATE_MAC
    public HSMParameters(HSMKeySchemeType keySchemeLength,
            HSMKeyType keySourceType, String keySourceValue,
            String sourceInitVector,
            HSMKeyType keyTargetType, String keyTargetValue,
            HSMMACLengthType lengthMACType, String macValue,
            String dataBuffer,
            HSMCommandType commandType)
    {
        this (keySchemeLength,
                keySourceType, keySourceValue, sourceInitVector, 
                null, null, 
                keyTargetType, keyTargetValue,
                null, null,
                null, null,
                lengthMACType, macValue, 
                dataBuffer,
                commandType);
    }
    
	public HSMKeySchemeType getKeySchemeLength() {
		return keySchemeLength;
	}

	public void setKeySchemeLength(HSMKeySchemeType keySchemeLength) {
		this.keySchemeLength = keySchemeLength;
	}

	public HSMKeyType getKeySourceType() {
		return keySourceType;
	}

	public void setKeySourceType(HSMKeyType keySourceType) {
		this.keySourceType = keySourceType;
	}

	public String getKeySourceValue() {
		return keySourceValue;
	}

	public void setKeySourceValue(String keySourceValue) {
		this.keySourceValue = keySourceValue;
	}

	public String getSourceInitVector() {
		return sourceInitVector;
	}

	public void setSourceInitVector(String sourceInitVector) {
		this.sourceInitVector = sourceInitVector;
	}

	public HSMEncryptionModeType getEncrySourceMode() {
		return encrySourceMode;
	}

	public void setEncrySourceMode(HSMEncryptionModeType encrySourceMode) {
		this.encrySourceMode = encrySourceMode;
	}

	public String getSourceKSN() {
		return sourceKSN;
	}

	public void setSourceKSN(String sourceKSN) {
		this.sourceKSN = sourceKSN;
	}

	public HSMKeyType getKeyTargetType() {
		return keyTargetType;
	}

	public void setKeyTargetType(HSMKeyType keyTargetType) {
		this.keyTargetType = keyTargetType;
	}

	public String getKeyTargetValue() {
		return keyTargetValue;
	}

	public void setKeyTargetValue(String keyTargetValue) {
		this.keyTargetValue = keyTargetValue;
	}

	public String getTargetInitVector() {
		return targetInitVector;
	}

	public void setTargetInitVector(String targetInitVector) {
		this.targetInitVector = targetInitVector;
	}

	public HSMEncryptionModeType getEncryTargetMode() {
		return encryTargetMode;
	}

	public void setEncryTargetMode(HSMEncryptionModeType encryTargetMode) {
		this.encryTargetMode = encryTargetMode;
	}

	public String getTargetKSN() {
		return targetKSN;
	}

	public void setTargetKSN(String targetKSN) {
		this.targetKSN = targetKSN;
	}

	public String getMacValue() {
		return macValue;
	}

	public void setMacValue(String macValue) {
		this.macValue = macValue;
	}

	public String getKeyCheckValue() {
		return keyCheckValue;
	}

	public void setKeyCheckValue(String keyCheckValue) {
		this.keyCheckValue = keyCheckValue;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public HSMResponseCode getResponseError() {
		return responseError;
	}

	public void setResponseError(HSMResponseCode responseError) {
		this.responseError = responseError;
	}

	public HSMMACLengthType getLengthMACType() {
		return lengthMACType;
	}

	public void setLengthMACType(HSMMACLengthType lengthMACType) {
		this.lengthMACType = lengthMACType;
	}

	public String getDataBuffer() {
		return dataBuffer;
	}

	public void setDataBuffer(String dataBuffer) {
		this.dataBuffer = dataBuffer;
	}

	public HSMCommandType getCommandType() {
		return commandType;
	}

	public void setCommandType(HSMCommandType commandType) {
		this.commandType = commandType;
	}
                
}
