/*
 * Novatronic S.A.C. Todos los derechos reservados
 * www.novatronic.com
 */
package com.novatronic.components.services.impl;

import javax.jws.WebService;

import com.novatronic.components.hsm.connection.HSMSocketManager;
import com.novatronic.components.hsm.exception.HSMException;
import com.novatronic.components.hsm.exception.HSMResponseCode;
import com.novatronic.components.hsm.params.HSMParameters;
import com.novatronic.components.hsm.type.HSMCommandType;
import com.novatronic.components.hsm.type.HSMEncryptionModeType;
import com.novatronic.components.hsm.type.HSMKeySchemeType;
import com.novatronic.components.hsm.type.HSMKeyType;
import com.novatronic.components.hsm.type.HSMMACLengthType;
import com.novatronic.components.services.HSMServiceManager;

@WebService(serviceName = "HSMServiceManager", 
portName = "HSMServiceManager",  
endpointInterface = "com.novatronic.components.services.HSMServiceManager",
targetNamespace = "http://www.novatronic.com/ws")

public class HSMServiceManagerImpl implements HSMServiceManager {


	/* EL conjunto de funciones queda en entredicho  puesto que se 
	 * apertura, se envia y se cierra.
	 */
	    
	// keySchemeLength --> Esquema de la llave                                    
	// keySourceType   --> Tipo de llave a usar                                   
	// keySourceValue  --> Llave utilizada para cifrar la informacion             
	// sourceInitVector--> Vector de iniciacion para cifrado 3DES.                
	// encrySourceMode --> Tipo de algoritmo a utilizar para el cifrado           
	// sourceKSN       --> Numero serial que permite al servidor identificar un 
	                  
	// keyTargetType   --> Tipo de llave a usar                                   
	// keyTargetValue  --> Llave utilizada para traslado                          
	// targetInitVector--> Vector de iniciaci�n para cifrado 3DES.              
	// encryTargetMode --> Tipo de algoritmo a utilizar para el cifrado           
	// targetKSN       --> Numero serial que permite al servidor identificar un eq
	                 
	// macValue        --> El valor de la MAC                                     
	// keyCheckValue   --> Llave utilizada para cifrar la informacion             
	                   
	// responseCode    --> Codigo de respuesta de la operacion                  
	// responseMessage --> Mensaje de respuesta de la operacion                   
	// responseError   -->                                                        
	                   
	// lengthMACType   --> Longitud de MAC                                        
	                 
	// dataBuffer      --> Buffer de la data                                      
	// commandType     --> El comando a ejecutar                                  
      
	@Override
	public HSMParameters echoTest() throws HSMException 
	{
		HSMParameters parameters = new HSMParameters(HSMCommandType.CMD_ECHO_TEST);
		
		return HSMSocketManager.sendReceive(parameters);
	}

	@Override
	public HSMParameters generateKey(HSMKeySchemeType keySchemeLength, HSMKeyType keySourceType) throws HSMException 
	{
		if (keySchemeLength ==  null)
			throw new HSMException("keySchemeLength", HSMResponseCode.SEC_HSM_BAD_NULL_ARGUMENT);
		
		if (keySourceType ==  null)
			throw new HSMException("keySourceType", HSMResponseCode.SEC_HSM_BAD_NULL_ARGUMENT);

		HSMParameters parameters = new HSMParameters(keySchemeLength, 
													keySourceType, 
													HSMCommandType.CMD_GENERATE_KEY);
		
		return HSMSocketManager.sendReceive(parameters);
	}
	
	@Override
	public HSMParameters encodeData( HSMKeySchemeType keySchemeLength, 
									 HSMKeyType keySourceType, 
									 String keySourceValue,
									 String sourceInitVector, 
									 HSMEncryptionModeType encrySourceMode, 
									 String dataBuffer) throws HSMException 
	{

		HSMParameters parameters = new HSMParameters(keySchemeLength, 
													keySourceType, 
													keySourceValue,
													sourceInitVector,
													encrySourceMode, 
													dataBuffer,
													HSMCommandType.CMD_ENCODE_DATA);
		
		return HSMSocketManager.sendReceive(parameters);
	}

	@Override
	public HSMParameters decodeData( HSMKeySchemeType keySchemeLength, 
									 HSMKeyType keySourceType, 
									 String keySourceValue,
									 String sourceInitVector,
									 HSMEncryptionModeType encrySourceMode, 
									 String dataBuffer) throws HSMException 
	{
	
		if (keySchemeLength ==  null)
			throw new HSMException("keySchemeLength", HSMResponseCode.SEC_HSM_BAD_NULL_ARGUMENT);
		
		if (keySourceType ==  null)
			throw new HSMException("keySourceType", HSMResponseCode.SEC_HSM_BAD_NULL_ARGUMENT);

		if (encrySourceMode ==  null)
			throw new HSMException("encrySourceMode", HSMResponseCode.SEC_HSM_BAD_NULL_ARGUMENT);

		HSMParameters parameters = new HSMParameters(keySchemeLength, 
													keySourceType, 
													keySourceValue,
													sourceInitVector, 
													encrySourceMode, 
													dataBuffer,
													HSMCommandType.CMD_DECODE_DATA);

		return HSMSocketManager.sendReceive(parameters);		
		
	}

	@Override
	public HSMParameters translateData( HSMKeySchemeType keySchemeLength, 
										HSMKeyType keySourceType,
										String keySourceValue, 
										HSMKeyType keyTargetType, 
										String keyTargetValue, 
										String dataBuffer) throws HSMException 
	{
		HSMParameters parameters = new HSMParameters (keySchemeLength, 
													 keySourceType,
													 keySourceValue, 
													 keyTargetType, 
													 keyTargetValue, 
													 dataBuffer, 
													 HSMCommandType.CMD_TRANSLATE_DATA) ;
		
		return HSMSocketManager.sendReceive(parameters);
	}

	@Override
	public HSMParameters generateMac(HSMKeySchemeType keySchemeLength, 
									 HSMKeyType keySourceType, 
									 String keySourceValue,
									 HSMMACLengthType lengthMACType, 
									 String dataBuffer) throws HSMException {
		
		HSMParameters parameters = new HSMParameters (keySchemeLength, 
													 keySourceType,
													 keySourceValue, 
													 lengthMACType, 
													 dataBuffer, 
													 dataBuffer, 
													 HSMCommandType.CMD_GENERATE_MAC);
		
		return HSMSocketManager.sendReceive(parameters);
	}

	@Override
	public HSMParameters validateMac(HSMKeySchemeType keySchemeLength, HSMKeyType keySourceType, String keySourceValue,
			HSMMACLengthType lengthMACType, String macValue, String dataBuffer) throws HSMException {


		HSMParameters parameters = new HSMParameters(keySchemeLength, 
													keySourceType,
													keySourceValue,
													lengthMACType,
													macValue,
													dataBuffer,
													HSMCommandType.CMD_VALIDATE_MAC);

		return HSMSocketManager.sendReceive(parameters);
	}

	@Override
	public HSMParameters translateMac(HSMKeySchemeType keySchemeLength, HSMKeyType keySourceType, String keySourceValue,
			HSMKeyType keyTargetType, String keyTargetValue, HSMMACLengthType lengthMACType, String macValue,
			String dataBuffer) throws HSMException {

		HSMParameters parameters = new HSMParameters(keySchemeLength, 
													keySourceType, keySourceValue, keyTargetType,
													keyTargetValue,  
													lengthMACType, 
													macValue, 
													dataBuffer, 
													HSMCommandType.CMD_TRANSLATE_MAC);

		return HSMSocketManager.sendReceive(parameters);
	}

}
