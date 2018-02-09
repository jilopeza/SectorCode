package com.novatronic.components.services;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Use;

import com.novatronic.components.hsm.exception.HSMException;
import com.novatronic.components.hsm.params.HSMParameters;
import com.novatronic.components.hsm.type.HSMEncryptionModeType;
import com.novatronic.components.hsm.type.HSMKeySchemeType;
import com.novatronic.components.hsm.type.HSMKeyType;
import com.novatronic.components.hsm.type.HSMMACLengthType;

@WebService (targetNamespace = "http://www.novatronic.com/ws")
@SOAPBinding(style=SOAPBinding.Style.DOCUMENT,parameterStyle=ParameterStyle.WRAPPED, use=Use.LITERAL )
public interface HSMServiceManager {
	
    // CMD_ECHO_TEST
	@WebMethod(operationName = "echoTest", action = "echoTestAction")
	@WebResult(name = "echoTestResponse")	
    public HSMParameters echoTest( ) throws HSMException ;
	
	// CMD_GENERATE_KEY
	@WebMethod(operationName = "generateKey", action = "generateKeyAction")
	@WebResult(name = "generateKeyResponse")
    public HSMParameters generateKey (
		@WebParam(name = "keySchemeLength", mode = WebParam.Mode.IN) HSMKeySchemeType keySchemeLength,
		@WebParam(name = "keySourceType", mode = WebParam.Mode.IN) HSMKeyType keySourceType ) throws HSMException ;
	

     // Constructor para cifrar data sin y con vector de inicializacion retorna un databuffer
    // CMD_ENCODE_DATA
	@WebMethod(operationName = "encodeData", action = "encodeDataAction")
	@WebResult(name = "encodeDataResponse")
	public HSMParameters encodeData (
		@WebParam(name = "keySchemeLength", mode = WebParam.Mode.IN) HSMKeySchemeType keySchemeLength,
		@WebParam(name = "keySourceType", mode = WebParam.Mode.IN) HSMKeyType keySourceType, 
		@WebParam(name = "keySourceValue", mode = WebParam.Mode.IN) String keySourceValue,
		@WebParam(name = "sourceInitVector", mode = WebParam.Mode.IN) String sourceInitVector, 
		@WebParam(name = "encrySourceMode", mode = WebParam.Mode.IN) HSMEncryptionModeType encrySourceMode,
		@WebParam(name = "dataBuffer", mode = WebParam.Mode.IN) String dataBuffer) throws HSMException ;
	

    // Constructor para descifrar data sin y con vector de inicializacion
    // CMD_DECODE_DATA
	@WebMethod(operationName = "decodeData", action = "decodeDataAction")
	@WebResult(name = "decodeDataResponse")
	public HSMParameters decodeData (
		@WebParam(name = "keySchemeLength", mode = WebParam.Mode.IN) HSMKeySchemeType keySchemeLength,
		@WebParam(name = "keySourceType", mode = WebParam.Mode.IN) HSMKeyType keySourceType, 
		@WebParam(name = "keySourceValue", mode = WebParam.Mode.IN) String keySourceValue,
		@WebParam(name = "sourceInitVector", mode = WebParam.Mode.IN) String sourceInitVector, 
		@WebParam(name = "encrySourceMode", mode = WebParam.Mode.IN) HSMEncryptionModeType encrySourceMode,
		@WebParam(name = "dataBuffer", mode = WebParam.Mode.IN) String dataBuffer) throws HSMException ;
	
	
    // CMD_TRANSLATE_DATA
	@WebMethod(operationName = "translateData", action = "translateDataAction")
	@WebResult(name = "translateDataResponse")	
    public HSMParameters translateData (
		@WebParam(name = "keySchemeLength", mode = WebParam.Mode.IN) HSMKeySchemeType keySchemeLength,
		@WebParam(name = "keySourceType", mode = WebParam.Mode.IN) HSMKeyType keySourceType, 
		@WebParam(name = "keySourceValue", mode = WebParam.Mode.IN) String keySourceValue,
		@WebParam(name = "keyTargetType", mode = WebParam.Mode.IN) HSMKeyType keyTargetType, 
		@WebParam(name = "keyTargetValue", mode = WebParam.Mode.IN) String keyTargetValue,
		@WebParam(name = "dataBuffer", mode = WebParam.Mode.IN) String dataBuffer  ) throws HSMException ;
	
	
    // CMD_GENERATE_MAC
	@WebMethod(operationName = "generateMac", action = "generateMacAction")
	@WebResult(name = "generateMacResponse")	
    public HSMParameters generateMac (
	    @WebParam(name = "keySchemeLength", mode = WebParam.Mode.IN) HSMKeySchemeType keySchemeLength,
		@WebParam(name = "keySourceType", mode = WebParam.Mode.IN) HSMKeyType keySourceType,
		@WebParam(name = "keySourceValue", mode = WebParam.Mode.IN) String keySourceValue,
		@WebParam(name = "lengthMACType", mode = WebParam.Mode.IN) HSMMACLengthType lengthMACType,
		@WebParam(name = "dataBuffer", mode = WebParam.Mode.IN) String dataBuffer ) throws HSMException;
	

    // CMD_VALIDATE_MAC
	@WebMethod(operationName = "validateMac", action = "validateMacAction")
	@WebResult(name = "validateMacResponse")	
    public HSMParameters validateMac (
	    @WebParam(name = "keySchemeLength", mode = WebParam.Mode.IN) HSMKeySchemeType keySchemeLength,
		@WebParam(name = "keySourceType", mode = WebParam.Mode.IN) HSMKeyType keySourceType,
		@WebParam(name = "keySourceValue", mode = WebParam.Mode.IN) String keySourceValue,
		@WebParam(name = "lengthMACType", mode = WebParam.Mode.IN) HSMMACLengthType lengthMACType, 
		@WebParam(name = "macValue", mode = WebParam.Mode.IN) String macValue, 
		@WebParam(name = "dataBuffer", mode = WebParam.Mode.IN) String dataBuffer ) throws HSMException;
	
	
    // CMD_TRANSLATE_MAC
	@WebMethod(operationName = "translateMac", action = "translateMacAction")
	@WebResult(name = "translateMacResponse")	
    public HSMParameters translateMac (
	    @WebParam(name = "keySchemeLength", mode = WebParam.Mode.IN) HSMKeySchemeType keySchemeLength,
		@WebParam(name = "keySourceType", mode = WebParam.Mode.IN) HSMKeyType keySourceType,
		@WebParam(name = "keySourceValue", mode = WebParam.Mode.IN) String keySourceValue,
		@WebParam(name = "keyTargetType", mode = WebParam.Mode.IN) HSMKeyType keyTargetType,
		@WebParam(name = "keyTargetValue", mode = WebParam.Mode.IN) String keyTargetValue,
		@WebParam(name = "lengthMACType", mode = WebParam.Mode.IN) HSMMACLengthType lengthMACType, 
		@WebParam(name = "macValue", mode = WebParam.Mode.IN) String macValue, 
		@WebParam(name = "dataBuffer", mode = WebParam.Mode.IN) String dataBuffer ) throws HSMException;

}

