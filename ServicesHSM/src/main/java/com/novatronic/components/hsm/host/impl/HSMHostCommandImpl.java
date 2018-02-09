/*
 * Novatronic S.A.C. Todos los derechos reservados
 * www.novatronic.com
 */
package com.novatronic.components.hsm.host.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.novatronic.components.hsm.exception.HSMException;
import com.novatronic.components.hsm.exception.InvalidLMKIdException;
import com.novatronic.components.hsm.host.HSMHostCommand;
import com.novatronic.components.hsm.params.HSMParameters;

public class HSMHostCommandImpl implements HSMHostCommand {

	protected static final Logger LOG = LoggerFactory.getLogger(HSMHostCommandImpl.class);
	protected static final String FMT_HEADER_COMMAND = "%%%ds";
	private Integer lmkId = -1;
	private Integer lenHdr = -1;

	public Integer getIdxLMK() {
		return lmkId;
	}

	public void setIdxLMK(Integer idxLMK) throws InvalidLMKIdException {
		this.lmkId = idxLMK;
	}

	public Integer getLenHdr() {
		return lenHdr;
	}

	public void setLenHdr(Integer lenHdr) {
		this.lenHdr = lenHdr;
	}

	public String getHdrCommand(String command) {
		if (getLenHdr() > command.length()) {
			// Format the header to new length
			String fmtFiller = String.format(FMT_HEADER_COMMAND, getLenHdr());
			return (String.format(fmtFiller, command).substring(0, getLenHdr()));
		} else {
			return command;
		}

	}

	public String getIdCommand() {

		// Generate the id for the HSM command
		return String.format("%06d", (int) (Math.random() * 999999 + 1));
	}

	public String getEchoTestCommand() throws HSMException {

		return null;
	}

	public String getGenkeyCommand(HSMParameters params) throws HSMException {

		return null;
	}

	public String getEncodeCommand(HSMParameters params) throws HSMException {

		return null;
	}

	public String getDecodeCommand(HSMParameters params) throws HSMException {

		return null;
	}

	public String getTranslateDataCommand(HSMParameters params) throws HSMException {

		return null;
	}

	public String getGenMACCommand(HSMParameters params) throws HSMException {

		return null;
	}

	public String getValMACCommand(HSMParameters params) throws HSMException {

		return null;
	}

	public String getTranslateMACCommand(HSMParameters params) throws HSMException {

		return null;
	}

}
