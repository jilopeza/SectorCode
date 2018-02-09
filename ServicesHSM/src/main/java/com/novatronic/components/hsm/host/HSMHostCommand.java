/*
 * Novatronic S.A.C. Todos los derechos reservados
 * www.novatronic.com
 */
package com.novatronic.components.hsm.host;

import com.novatronic.components.hsm.exception.HSMException;
import com.novatronic.components.hsm.exception.InvalidLMKIdException;
import com.novatronic.components.hsm.params.HSMParameters;

public interface HSMHostCommand {

	Integer getIdxLMK();

	void setIdxLMK(Integer idxLMK) throws InvalidLMKIdException;

	void setLenHdr(Integer lenHdr);

	Integer getLenHdr();

	String getHdrCommand(String command);

	String getEchoTestCommand() throws HSMException;

	String getGenkeyCommand(HSMParameters params) throws HSMException;

	String getEncodeCommand(HSMParameters params) throws HSMException;

	String getDecodeCommand(HSMParameters params) throws HSMException;

	String getTranslateDataCommand(HSMParameters params) throws HSMException;

	String getGenMACCommand(HSMParameters params) throws HSMException;

	String getValMACCommand(HSMParameters params) throws HSMException;

	String getTranslateMACCommand(HSMParameters params) throws HSMException;
}
