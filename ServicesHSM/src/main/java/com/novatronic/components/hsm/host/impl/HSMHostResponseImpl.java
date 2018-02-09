/*
 * Novatronic S.A.C. Todos los derechos reservados
 * www.novatronic.com
 */
package com.novatronic.components.hsm.host.impl;

import java.io.UnsupportedEncodingException;

import com.novatronic.components.hsm.exception.HSMException;
import com.novatronic.components.hsm.host.HSMHostResponse;
import com.novatronic.components.hsm.params.HSMParameters;

public class HSMHostResponseImpl implements HSMHostResponse {

	private Integer lenHeader;

	public HSMHostResponseImpl(Integer lenHeader) {
		this.lenHeader = lenHeader;
	}

	public String getResponseCode(String rspCommad) {
		return null;
	}

	public boolean isValidResponse(String reqCommand, String rspCommad) throws HSMException {

		return false;
	}

	public String getEchoTestResponse(String rspCommad) throws HSMException {

		return getResponseCode(rspCommad);
	}

	public String getGenkeyResponse(String rspCommad, HSMParameters param) throws HSMException {

		return getResponseCode(rspCommad);
	}

	public String getEncodeResponse(String rspCommad, HSMParameters param) throws HSMException {

		return getResponseCode(rspCommad);
	}

	public String getDecodeResponse(String rspCommad, HSMParameters param) 
			throws HSMException, UnsupportedEncodingException {

		return getResponseCode(rspCommad);
	}

	public String getTranslateDataResponse(String rspCommad, HSMParameters param) throws HSMException {

		return getResponseCode(rspCommad);
	}

	public String getGenMACResponse(String rspCommad, HSMParameters param) throws HSMException {

		return getResponseCode(rspCommad);
	}

	public String getValMACResponse(String rspCommad, HSMParameters param) throws HSMException {

		return null;
	}

	public String getTranslateMACResponse(String rspCommad, HSMParameters param) throws HSMException {

		return getResponseCode(rspCommad);
	}

	public Integer getLenHeader() {
		return lenHeader;
	}

	public void setLenHeader(Integer lenHeader) {
		this.lenHeader = lenHeader;
	}
}
