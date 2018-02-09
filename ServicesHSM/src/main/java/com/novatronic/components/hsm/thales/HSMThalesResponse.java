/*
 * Novatronic S.A.C. Todos los derechos reservados
 * www.novatronic.com
 */
package com.novatronic.components.hsm.thales;

import java.io.UnsupportedEncodingException;

import com.novatronic.components.hsm.exception.HSMException;
import com.novatronic.components.hsm.exception.HSMResponseCode;
import com.novatronic.components.hsm.host.impl.HSMHostResponseImpl;
import com.novatronic.components.hsm.params.HSMParameters;
import com.novatronic.components.hsm.utils.StringUtil;

public class HSMThalesResponse extends HSMHostResponseImpl {

	protected static final Integer COMMAND_LEN = 2;
	protected static final Integer RESPONSE_LEN = 2;

	public HSMThalesResponse(Integer lenHeader) {
		super(lenHeader);
	}

	/*
	 * Get the response code
	 * 
	 * @see
	 * com.novatronic.components.hsm.HSMHostResponse#getResponseCode(java.lang
	 * .String)
	 */
	@Override
	public String getResponseCode(String rspCommad) {
		return rspCommad.substring(getLenHeader() + COMMAND_LEN, getLenHeader() + COMMAND_LEN + 2);
	}

	/*
	 * Validate the response
	 * 
	 * @see
	 * com.novatronic.components.hsm.HSMHostResponse#isValidResponse(java.lang
	 * .String, java.lang.String)
	 */
	@Override
	public boolean isValidResponse(String reqCommand, String rspCommad) throws HSMException {

		// Validate length, minimum 8 bytes, ex. abdeXX##
		if (rspCommad.length() < (getLenHeader() + COMMAND_LEN + RESPONSE_LEN)) {
			throw new HSMException(HSMResponseCode.SEC_HSM_BAD_LENGTH_COMMAND);
		}

		// Validate if response command corresponds to the request command
		if ((int) reqCommand.charAt(getLenHeader() + 1) != (int) rspCommad.charAt(getLenHeader() + 1) - 1) {
			throw new HSMException(HSMResponseCode.SEC_HSM_BAD_RSP_CMD_CODE);
		}

		// Validate if response sequence corresponds to the request command
		if (rspCommad.length() > (getLenHeader() + COMMAND_LEN + RESPONSE_LEN)) {
			String idReq = reqCommand.substring(reqCommand.indexOf(HSMThalesCommand.END_MESSAGE_DELIMITER)),
					idRsp = rspCommad.substring(rspCommad.indexOf(HSMThalesCommand.END_MESSAGE_DELIMITER));

			// If the request and response are differents ...
			if (!idReq.equals(idRsp)) {
				throw new HSMException(HSMResponseCode.SEC_HSM_BAD_RSP_CMD_CODE);
			}
		}

		return true;
	}

	/*
	 * Get the key generated command response
	 * 
	 * @see
	 * com.novatronic.components.hsm.HSMHostResponse#getGenkeyResponse(java.
	 * lang.String, com.novatronic.components.hsm.params.HSMParameters)
	 */
	@Override
	public String getGenkeyResponse(String rspCommad, HSMParameters param) throws HSMException {

		// Obtains the randomly generated key
		String keyValue = rspCommad.substring((getLenHeader() + COMMAND_LEN + RESPONSE_LEN),
				(getLenHeader() + COMMAND_LEN + RESPONSE_LEN) + param.getKeySchemeLength().getLength() + 1);

		// Obtains the key check value
		String keyCheck = rspCommad.substring((getLenHeader() + COMMAND_LEN + RESPONSE_LEN) + keyValue.length(),
				(getLenHeader() + COMMAND_LEN + RESPONSE_LEN) + keyValue.length() + 6);

		// Add response values
		param.setKeySourceValue(keyValue);
		param.setKeyCheckValue(keyCheck);

		return super.getGenkeyResponse(rspCommad, param);
	}

	/*
	 * Get the encoded command response
	 * 
	 * @see
	 * com.novatronic.components.hsm.HSMHostResponse#getEncodeResponse(java.
	 * lang.String, com.novatronic.components.hsm.params.HSMParameters)
	 */
	@Override
	public String getEncodeResponse(String rspCommad, HSMParameters param) throws HSMException {

		int ivLength = 0, dataLength = 0;
		
		// Verify if exists the vector initialization ...
		if (!StringUtil.isEmpty(param.getSourceInitVector())) {
			// if its length is considered
			ivLength = param.getSourceInitVector().length();
			
			String ivValue = rspCommad.substring((getLenHeader() + COMMAND_LEN + RESPONSE_LEN),
					(getLenHeader() + COMMAND_LEN + RESPONSE_LEN) + ivLength);
			
			param.setSourceInitVector(ivValue);
		}

		// Get the data length
		dataLength = Integer.valueOf(rspCommad.substring((getLenHeader() + COMMAND_LEN + RESPONSE_LEN) + ivLength,
				(getLenHeader() + COMMAND_LEN + RESPONSE_LEN) + ivLength + 4), 16);

		// Get the encoded data
		String encodedData = rspCommad.substring((getLenHeader() + COMMAND_LEN + RESPONSE_LEN) + 4 + ivLength,
				(getLenHeader() + COMMAND_LEN + RESPONSE_LEN) + 4 + ivLength + dataLength);

		// Add the encoded data
		param.setDataBuffer(encodedData);

		return super.getEncodeResponse(rspCommad, param);
	}

	/*
	 * Get the decoded command response
	 * 
	 * @see
	 * com.novatronic.components.hsm.HSMHostResponse#getDecodeResponse(java.
	 * lang.String, com.novatronic.components.hsm.params.HSMParameters)
	 */
	@Override
	public String getDecodeResponse(String rspCommad, HSMParameters param) 
			throws HSMException, UnsupportedEncodingException {

		int ivLength = 0, dataLength = 0;

		// Verify if exists the vector initialization ...
		if (!StringUtil.isEmpty(param.getSourceInitVector())) {
			// if its length is considered
			ivLength = param.getSourceInitVector().length();

			String ivValue = rspCommad.substring((getLenHeader() + COMMAND_LEN + RESPONSE_LEN),
					(getLenHeader() + COMMAND_LEN + RESPONSE_LEN) + ivLength);
			
			param.setSourceInitVector(ivValue);
		}

		// Get the data length
		dataLength = Integer.valueOf(rspCommad.substring((getLenHeader() + COMMAND_LEN + RESPONSE_LEN) + ivLength,
				(getLenHeader() + COMMAND_LEN + RESPONSE_LEN) + ivLength + 4), 16);

		// Get the decoded data
		String decodedData = rspCommad.substring((getLenHeader() + COMMAND_LEN + RESPONSE_LEN) + 4 + ivLength,
				(getLenHeader() + COMMAND_LEN + RESPONSE_LEN) + 4 + ivLength + dataLength);

		// Add the decoded data
		param.setDataBuffer(new String(decodedData.getBytes(), "UTF-8"));

		return super.getDecodeResponse(rspCommad, param);
	}

	/*
	 * Get the translated command response
	 * 
	 * @see
	 * com.novatronic.components.hsm.HSMHostResponse#getTranslateDataResponse
	 * (java.lang.String, com.novatronic.components.hsm.params.HSMParameters)
	 */
	@Override
	public String getTranslateDataResponse(String rspCommad, HSMParameters param) throws HSMException {

		int ivLength = 0, dataLength = 0;

		// Verify if exists the vector initialization ...
		if (!StringUtil.isEmpty(param.getSourceInitVector())) {
			ivLength = param.getSourceInitVector().length();
		}

		// Get the data length
		dataLength = Integer.valueOf(rspCommad.substring((getLenHeader() + COMMAND_LEN + RESPONSE_LEN) + ivLength,
				(getLenHeader() + COMMAND_LEN + RESPONSE_LEN) + ivLength + 4), 16);

		// Get the translated data
		String translatedData = rspCommad.substring((getLenHeader() + COMMAND_LEN + RESPONSE_LEN) + 4 + ivLength,
				(getLenHeader() + COMMAND_LEN + RESPONSE_LEN) + 4 + ivLength + dataLength);

		// Add the translated data
		param.setDataBuffer(translatedData);

		return super.getTranslateDataResponse(rspCommad, param);
	}

	/*
	 * Get the created mac command response
	 * 
	 * @see
	 * com.novatronic.components.hsm.HSMHostResponse#getGenMACResponse(java.
	 * lang.String, com.novatronic.components.hsm.params.HSMParameters)
	 */
	@Override
	public String getGenMACResponse(String rspCommad, HSMParameters param) throws HSMException {

		// Getthe MAC value
		String macValue = rspCommad.substring((getLenHeader() + COMMAND_LEN + RESPONSE_LEN),
				rspCommad.indexOf(HSMThalesCommand.END_MESSAGE_DELIMITER));

		// Add the mac value
		param.setMacValue(macValue);

		return super.getGenMACResponse(rspCommad, param);
	}

	@Override
	public String getValMACResponse(String rspCommad, HSMParameters param) throws HSMException {

		return super.getValMACResponse(rspCommad, param);
	}

	@Override
	public String getTranslateMACResponse(String rspCommad, HSMParameters param) throws HSMException {

		String macValue = rspCommad.substring((getLenHeader() + COMMAND_LEN + RESPONSE_LEN),
				rspCommad.indexOf(HSMThalesCommand.END_MESSAGE_DELIMITER));

		param.setMacValue(macValue);

		return super.getTranslateMACResponse(rspCommad, param);
	}

}
