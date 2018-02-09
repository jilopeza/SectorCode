/**
 * 
 */
package com.novatronic.components.hsm.connection;

import java.net.Socket;

import com.novatronic.components.hsm.host.HardwareSecurityModule;

/**
 * @author Novatronic
 *
 * Client class of connection content with HSM.
 */
public class ClienteHSMSocketContent {

	private HardwareSecurityModule 	hsmServer 		= null;
	private Socket 					socket 			= null;
	private Integer 				socketStatus 	= null;
	
	/**
	 * Client socket content constructor
	 */
	public ClienteHSMSocketContent(  ) 
	{
	}

	/**
	 *  Client socket content constructor
	 *  
	 * @param hsmServer HSM Server Object
	 * @param socket Socket for connection 
	 * @param socketsStatus Status of socket
	 */
	public ClienteHSMSocketContent( HardwareSecurityModule hsmServer,  Socket socket, Integer socketsStatus ) 
	{
		this.hsmServer 		= hsmServer 	;  
		this.socket 		= socket 		;                     
		this.socketStatus 	= socketsStatus ;             
	}

	/**
	 * Get HSM Server
	 * @return HSM Server Object
	 */
	public HardwareSecurityModule getHsmServer() {
		return hsmServer;
	}

	/**
	 * Set HSM Server Object
	 * @param hsmServer HSM Server Object
	 */
	public void setHsmServer(HardwareSecurityModule hsmServer) {
		this.hsmServer = hsmServer;
	}

	/**
	 * Get socket object
	 * @return Socket object
	 */
	public Socket getSocket() {
		return socket;
	}

	/**
	 * Set socket object
	 * @param socket
	 */
	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	/**
	 * Get socket status
	 * @return socket status
	 */
	public Integer getSocketStatus() {
		return socketStatus;
	}

	/**
	 * Set socket status
	 * @param socketStatus socket status
	 */
	public void setSocketStatus(Integer socketStatus) {
		this.socketStatus = socketStatus;
	}

}
