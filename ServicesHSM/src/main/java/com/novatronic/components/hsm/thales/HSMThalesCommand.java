/*
 * Novatronic S.A.C. Todos los derechos reservados
 * www.novatronic.com
 */
package com.novatronic.components.hsm.thales;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.novatronic.components.hsm.exception.HSMException;
import com.novatronic.components.hsm.exception.HSMResponseCode;
import com.novatronic.components.hsm.exception.InvalidLMKIdException;
import com.novatronic.components.hsm.host.impl.HSMHostCommandImpl;
import com.novatronic.components.hsm.params.HSMParameters;
import com.novatronic.components.hsm.type.HSMEncryptionModeType;
import com.novatronic.components.hsm.utils.HSMValidator;

public class HSMThalesCommand extends HSMHostCommandImpl {

	protected static final Logger LOG = LoggerFactory.getLogger(HSMThalesCommand.class);
	public static final char END_MESSAGE_DELIMITER = 0x19;
	
	public HSMThalesCommand() {
	}

	@Override
	public void setIdxLMK(Integer idxLMK) throws InvalidLMKIdException {
		
		// Validate the LMK number
		if (!(idxLMK.intValue() >= 0 && idxLMK.intValue() <= 9)) {
			throw new InvalidLMKIdException();
		}

		super.setIdxLMK(idxLMK);
	}

	/*
	 * Format the echo test command
	 * 
	 * @see com.novatronic.components.hsm.HSMHostCommand#getEchoTestCommand()
	 */
	@Override
	public String getEchoTestCommand() {
		
		// Formating the command
		String command = String.format("%sNC%%%02d%c%s", getHdrCommand("diag"), 
				getIdxLMK().intValue(), 
				HSMThalesCommand.END_MESSAGE_DELIMITER,
				getIdCommand());

		LOG.info(String.format("getEchoTestCommand: [%8.8s]", command));

		return command;
	}

	/*
	 * Format the generate random key command
	 * 
	 * @see
	 * com.novatronic.components.hsm.HSMHostCommand#getGenkeyCommand(com.novatronic
	 * .components.hsm.params.HSMParameters)
	 */
	@Override
	public String getGenkeyCommand(HSMParameters params) {
		
		// Formating the command
		String command = String.format("%sA00%s%s%%%02d%c%s",
				getHdrCommand("genk"),
				params.getKeySourceType().getCode(), 
				params.getKeySchemeLength().getCode(), 
				getIdxLMK().intValue(), 
				HSMThalesCommand.END_MESSAGE_DELIMITER, 
				getIdCommand()); 

		LOG.info(String.format("getGenkeyCommand: [%8.8s]", command));

		return command;
	}

	/*
	 * Format the encode data command
	 * 
	 * @see
	 * com.novatronic.components.hsm.HSMHostCommand#getEncodeCommand(com.novatronic
	 * .components.hsm.params.HSMParameters)
	 */
	@Override
	public String getEncodeCommand(HSMParameters params) throws HSMException {
		
		String command = "";
		
		// Validate parameters
		if (!HSMValidator.validateKeyDataType(params.getKeySourceType())) {
			throw new HSMException(HSMResponseCode.SEC_HSM_BAD_KEY_TYPE);
		}

		if (!HSMValidator.validateKeyLength(params.getKeySchemeLength(),
				params.getKeySourceValue())) {
			throw new HSMException(HSMResponseCode.SEC_HSM_BAD_KEY_LENGTH);
		}

		if (!HSMValidator.validateInitialVector(params.getEncrySourceMode(),
				params.getSourceInitVector())) {
			
			if (params.getEncrySourceMode() == HSMEncryptionModeType.M_ECB)
				throw new HSMException(HSMResponseCode.SEC_HSM_MODE_UNSUPPORT_IV);
			else
				throw new HSMException(HSMResponseCode.SEC_HSM_MODE_REQUIRES_IV);
		}

		try {
			// Formating the command
			command = String.format("%sM0%02d01%s%s%s%04X%s%%%02d%c%s",
					getHdrCommand("cmsg"), 
					params.getEncrySourceMode().getMode(),
					params.getKeySourceType().getCode(), 
					params.getKeySourceValue(), 
					(params.getSourceInitVector() != null ? params.getSourceInitVector() : ""),
					params.getDataBuffer().toString().getBytes("UTF8").length, 
					params.getDataBuffer(), 
					getIdxLMK().intValue(), 
					HSMThalesCommand.END_MESSAGE_DELIMITER, 
					getIdCommand()); 
		} catch (UnsupportedEncodingException e) {
			throw new HSMException(e);
		}

		LOG.info(String.format("getEncodeCommand: [%8.8s]", command));

		return command;
	}

	/*
	 * Format the decode data command
	 * 
	 * @see
	 * com.novatronic.components.hsm.HSMHostCommand#getDecodeCommand(com.novatronic
	 * .components.hsm.params.HSMParameters)
	 */
	@Override
	public String getDecodeCommand(HSMParameters params) throws HSMException {

		String command = "";

		// Validate parameters
		if (!HSMValidator.validateKeyDataType(params.getKeySourceType())) {
			throw new HSMException(HSMResponseCode.SEC_HSM_BAD_KEY_TYPE);
		}

		if (!HSMValidator.validateKeyLength(params.getKeySchemeLength(),
				params.getKeySourceValue())) {
			throw new HSMException(HSMResponseCode.SEC_HSM_BAD_KEY_LENGTH);
		}

		try {
			// Formating the command
			command = String.format("%sM2%02d10%s%s%s%04X%s%%%02d%c%s",
					getHdrCommand("dmsg"),
					params.getEncrySourceMode().getMode(), 
					params.getKeySourceType().getCode(), 
					params.getKeySourceValue(), 
					(params.getSourceInitVector() != null ? params.getSourceInitVector() : ""),
					params.getDataBuffer().toString().getBytes("UTF8").length, 
					params.getDataBuffer(), 
					getIdxLMK().intValue(), 
					HSMThalesCommand.END_MESSAGE_DELIMITER, 
					getIdCommand()); 
		} catch (UnsupportedEncodingException e) {
			throw new HSMException(e);
		}

		LOG.info(String.format("getDecodeCommand: [%8.8s]", command));

		return command;
	}

	/*
	 * Format the translate data command from a key to another key
	 * 
	 * @see
	 * com.novatronic.components.hsm.HSMHostCommand#getTranslateDataCommand(
	 * com.novatronic.components.hsm.params.HSMParameters)
	 */
	@Override
	public String getTranslateDataCommand(HSMParameters params)
			throws HSMException {
		
		String command = "";

		// Validate parameters
		if (!HSMValidator.validateKeyDataType(params.getKeySourceType())) {
			throw new HSMException(HSMResponseCode.SEC_HSM_BAD_KEY_TYPE);
		}

		if (!HSMValidator.validateKeyDataType(params.getKeyTargetType())) {
			throw new HSMException(HSMResponseCode.SEC_HSM_BAD_KEY_TYPE);
		}

		if (!HSMValidator.validateKeyLength(params.getKeySchemeLength(),
				params.getKeySourceValue())) {
			throw new HSMException(HSMResponseCode.SEC_HSM_BAD_KEY_LENGTH);
		}

		if (!HSMValidator.validateKeyLength(params.getKeySchemeLength(),
				params.getKeyTargetValue())) {
			throw new HSMException(HSMResponseCode.SEC_HSM_BAD_KEY_LENGTH);
		}

		try {
			// Formating the command
			command = String.format(
					"%sM4%02d%02d11%s%s%s%s%s%s%04X%s%%%02d%c%s",
					getHdrCommand("tmsg"), params.getEncrySourceMode()
							.getMode(), 
					params.getEncryTargetMode().getMode(), 
					params.getKeySourceType().getCode(), 
					params.getKeySourceValue(), 
					params.getKeyTargetType().getCode(), 
					params.getKeyTargetValue(), 
					(params.getSourceInitVector() != null ? params.getSourceInitVector() : ""),
					(params.getTargetInitVector() != null ? params.getTargetInitVector() : ""),
					params.getDataBuffer().toString().getBytes("UTF8").length, 
					params.getDataBuffer(), 
					getIdxLMK().intValue(), 
					HSMThalesCommand.END_MESSAGE_DELIMITER, 
					getIdCommand()); 
		} catch (UnsupportedEncodingException e) {
			throw new HSMException(e);
		}

		LOG.info(String.format("getTranslateDataCommand: [%8.8s]", command));

		return command;
	}

}
