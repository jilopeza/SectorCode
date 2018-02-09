/*
 * Novatronic S.A.C. Todos los derechos reservados
 * www.novatronic.com
 */
package com.novatronic.components.hsm.host.impl;

import com.novatronic.components.hsm.exception.InvalidIPFormatException;
import com.novatronic.components.hsm.exception.InvalidLMKIdException;
import com.novatronic.components.hsm.exception.InvalidPortValueException;
import com.novatronic.components.hsm.host.HSMHostCommand;
import com.novatronic.components.hsm.host.HSMHostResponse;
import com.novatronic.components.hsm.host.HardwareSecurityModule;
import com.novatronic.components.hsm.type.HSMBalanceType;
import com.novatronic.components.hsm.type.HSMConnectionType;
import com.novatronic.components.hsm.type.HSMEnvironmentType;
import com.novatronic.components.hsm.utils.HSMValidator;

/**
 * @author vcarreno
 * 
 */
public abstract class HardwareSecurityModuleImpl implements HardwareSecurityModule {

    // HSM IP
    private String ipHSM;
    // HSM Port
    private Integer portHSM;
    // HSM Identification
    private Integer idHSM;
    // HSM Length Header Command
    private Integer lenHeader;
    // HSM Version
    private Integer version;
    // Connection Type
    private HSMConnectionType connType;
    // HSM Model
    private String hsmModelName;
    // HSM Description
    private String description;
    // HSM Balance Type (Use Future)
    private HSMBalanceType balanceType;
    // HSM Percent Work (Use Future)
    private Integer percentWork; 
    // Environment Type
    private HSMEnvironmentType environmentType;
    // HSM Timeout
    private Integer timeout; 
    // Interval Echo
    private Integer intervalEcho; 
    // Retries Number
    private Integer retriesNumber;
    // HSM LMK Identification
    private Integer idxLMK; 
    // HSM Command Interface
    private HSMHostCommand hostCMD; 
    // HSM Response Interface
    private HSMHostResponse hostRSP; 

    /**
     * @throws InvalidIPFormatException
     *             , InvalidPortValue
     * 
     */
    public HardwareSecurityModuleImpl(String ipHSM, Integer portHSM,
            String  hsmModelName) throws InvalidIPFormatException,
            InvalidPortValueException, InvalidLMKIdException {

    	 // Verify IP format
        if (!HSMValidator.validateIP(ipHSM)) {
            throw new InvalidIPFormatException();
        }

        // Verify port value
        if (!HSMValidator.validatePort(portHSM)) { 
            throw new InvalidPortValueException(); 
        }

        // Set the IP, port and HSM Model
        setIpHSM(ipHSM); 
        setPortHSM(portHSM);
        setHsmModelName(hsmModelName);
    }

    public String getIpHSM() {
        return ipHSM;
    }

    public void setIpHSM(String ipHSM) {
        this.ipHSM = ipHSM;
    }

    public Integer getPortHSM() {
        return portHSM;
    }

    public void setPortHSM(Integer portHSM) {
        this.portHSM = portHSM;
    }

    public Integer getIdHSM() {
        return idHSM;
    }

    public void setIdHSM(Integer idHSM) {
        this.idHSM = idHSM;
    }

    public Integer getLenHeader() {
        return lenHeader;
    }

    public void setLenHeader(Integer lenHeader) {
        hostCMD.setLenHdr(lenHeader);

        this.lenHeader = lenHeader;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public HSMConnectionType getConnType() {
        return connType;
    }

    public void setConnType(HSMConnectionType connType) {
        this.connType = connType;
    }

    public String getHsmModelName() {
        return hsmModelName;
    }

    public void setHsmModelName(String hsmModelName) {
        this.hsmModelName = hsmModelName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public HSMBalanceType getBalanceType() {
        return balanceType;
    }

    public void setBalanceType(HSMBalanceType balanceType) {
        this.balanceType = balanceType;
    }

    public Integer getPercentWork() {
        return percentWork;
    }

    public void setPercentWork(Integer percentWork) {
        this.percentWork = percentWork;
    }

    public HSMEnvironmentType getEnvironmentType() {
        return environmentType;
    }

    public void setEnvironmentType(HSMEnvironmentType environmentType) {
        this.environmentType = environmentType;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public Integer getIntervalEcho() {
        return intervalEcho;
    }

    public void setIntervalEcho(Integer intervalEcho) {
        this.intervalEcho = intervalEcho;
    }

    public Integer getRetriesNumber() {
        return retriesNumber;
    }

    public void setRetriesNumber(Integer retriesNumber) {
        this.retriesNumber = retriesNumber;
    }

    public Integer getIdxLMK() {
        return idxLMK;
    }

    public void setIdxLMK(Integer idxLMK) throws InvalidLMKIdException {

        hostCMD.setIdxLMK(idxLMK);

        this.idxLMK = idxLMK;
    }

    public HSMHostCommand getHostCMD() {
        return hostCMD;
    }

    public void setHostCMD(HSMHostCommand hostCMD) {
        this.hostCMD = hostCMD;
    }

    public HSMHostResponse getHostRSP() {
        return hostRSP;
    }

    public void setHostRSP(HSMHostResponse hostRSP) {
        this.hostRSP = hostRSP;
    }

}
