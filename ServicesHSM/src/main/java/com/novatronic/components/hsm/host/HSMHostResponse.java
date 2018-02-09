/*
 * Novatronic S.A.C. Todos los derechos reservados
 * www.novatronic.com
 */
package com.novatronic.components.hsm.host;

import java.io.UnsupportedEncodingException;

import com.novatronic.components.hsm.exception.HSMException;
import com.novatronic.components.hsm.params.HSMParameters;

public interface HSMHostResponse {

	String getResponseCode(String rspCommad) throws HSMException;

	boolean isValidResponse(String reqCommand, String rspCommad)
			throws HSMException;

	String getEchoTestResponse(String rspCommad) throws HSMException;

	String getGenkeyResponse(String rspCommad, HSMParameters param)
			throws HSMException;

	String getEncodeResponse(String rspCommad, HSMParameters param)
			throws HSMException;

	String getDecodeResponse(String rspCommad, HSMParameters param)
			throws HSMException, UnsupportedEncodingException;

	String getTranslateDataResponse(String rspCommad, HSMParameters param)
			throws HSMException;

	String getGenMACResponse(String rspCommad, HSMParameters param)
			throws HSMException;

	String getValMACResponse(String rspCommad, HSMParameters param)
			throws HSMException;

	String getTranslateMACResponse(String rspCommad, HSMParameters param)
			throws HSMException;
}
