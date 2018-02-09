/*
 * Novatronic S.A.C. Todos los derechos reservados
 * www.novatronic.com
 */
package com.novatronic.components.hsm.host;

import com.novatronic.components.hsm.exception.InvalidLMKIdException;
import com.novatronic.components.hsm.type.HSMBalanceType;
import com.novatronic.components.hsm.type.HSMConnectionType;
import com.novatronic.components.hsm.type.HSMEnvironmentType;

public interface HardwareSecurityModule {

	String getIpHSM();

	void setIpHSM(String ipHSM);

	Integer getPortHSM();

	void setPortHSM(Integer portHSM);

	Integer getIdHSM();

	void setIdHSM(Integer idHSM);

	Integer getLenHeader();

	void setLenHeader(Integer lenHeader);

	Integer getVersion();

	void setVersion(Integer version);

	HSMConnectionType getConnType();

	void setConnType(HSMConnectionType connType);

	String getHsmModelName();

	void setHsmModelName(String hsmModel);

	String getDescription();

	void setDescription(String description);

	HSMBalanceType getBalanceType();

	void setBalanceType(HSMBalanceType balanceType);

	Integer getPercentWork();

	void setPercentWork(Integer percentWork);

	HSMEnvironmentType getEnvironmentType();

	void setEnvironmentType(HSMEnvironmentType environmentType);

	Integer getTimeout();

	void setTimeout(Integer timeout);

	Integer getIntervalEcho();

	void setIntervalEcho(Integer intervalEcho);

	Integer getRetriesNumber();

	void setRetriesNumber(Integer retriesNumber);

	Integer getIdxLMK();

	void setIdxLMK(Integer idxLMK) throws InvalidLMKIdException;

	HSMHostCommand getHostCMD();

	void setHostCMD(HSMHostCommand hostCMD);

	HSMHostResponse getHostRSP();

	void setHostRSP(HSMHostResponse hostRSP);

}
