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

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HSMParametersResponse")
public class HSMParametersResponse {
	
    private String                 resultInitVector;   // Vector de iniciacion resultante.
	private String                 keyCheckValue;      // Llave utilizada para cifrar la informacion
    private String                 responseCode;       // C�digo de respuesta de la operacion
    private String                 responseMessage;    // Mensaje de respuesta de la operacion
    private HSMResponseCode        responseError;      //
    
    private String                 dataBuffer;         // Buffer de la data
    private HSMCommandType         commandType;        // El comando a ejecutar
    
    public HSMParametersResponse(HSMParametersResponse parameter) {
    	
		this.resultInitVector = parameter.resultInitVector;

		this.keyCheckValue    = parameter.keyCheckValue;   
		this.responseCode     = parameter.responseCode;    
        this.responseMessage  = parameter.responseMessage; 
	}
    
    
    public HSMParametersResponse(String resultInitVector, 
    				             String keyCheckValue,
    				             String responseCode,
    				             String responseMessage,
    				             HSMResponseCode responseError,
    				             String dataBuffer,
    				             HSMCommandType commandType) {
		super();
		this.resultInitVector = resultInitVector;
		this.keyCheckValue = keyCheckValue;
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
		this.responseError = responseError;
		this.dataBuffer = dataBuffer;
		this.commandType = commandType;
	}

	public String getResultInitVector() {
		return resultInitVector;
	}

	public void setResultInitVector(String resultInitVector) {
		this.resultInitVector = resultInitVector;
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
