package com.novatronic.components.hsm.connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.novatronic.components.hsm.exception.HSMException;
import com.novatronic.components.hsm.exception.HSMResponseCode;
import com.novatronic.components.hsm.exception.InvalidConnectionException;
import com.novatronic.components.hsm.params.HSMParameters;
import com.novatronic.components.hsm.type.HSMConnectionType;
import com.novatronic.components.hsm.type.HSMEnvironmentType;

/**
 * @author Novatronic
 *
 * Client class of connection with HSM.
 */
public class ClienteHSMSocket {

	protected static final Logger LOG = LoggerFactory.getLogger(ClienteHSMSocket.class);

	private List<ClienteHSMSocketContent> 	lstHSMServers 	= null;
	private ClienteHSMSocketContent 		hsmServerSocket = null;
	
	
	private int idxHsm = 0;
	private DataInputStream input;
	private DataOutputStream output;
	
	/**
	 * Class constructor of ClienteHSMSocket
	 */
	public ClienteHSMSocket ()
	{
		lstHSMServers = new ArrayList<ClienteHSMSocketContent>();
	}

	/**
	 * Class constructor of ClienteHSMSocket
	 * @param hsmS List of HSM socket content
	 */
	public ClienteHSMSocket(List<ClienteHSMSocketContent> hsmS) 
	{
		this ();
		
		lstHSMServers.addAll(hsmS);

		for (int i = 0; i < lstHSMServers.size(); i++) {

			hsmServerSocket = lstHSMServers.get(i);
			hsmServerSocket.setSocket(new Socket());
			hsmServerSocket.setSocketStatus(0);
			
			// Si NO es fast connect ? 
			if (hsmServerSocket.getHsmServer().getConnType() != HSMConnectionType.FAST_CONNECT) 
			{
				LOG.info("Session está full");
				if (openSocket( hsmServerSocket ) == 0)
				{
					LOG.info("Conectado OK");
				}
			}
		}
	}
	
	/**
	 * Open sockets in client socket content
	 * @param hsmServerSocket client socket content
	 * @return client socket content index
	 */
	public int openSocket( ClienteHSMSocketContent hsmServerSocket ) {
		
		int n_retry = hsmServerSocket.getHsmServer().getRetriesNumber();
		
		Socket socket = null;
		
		System.out.println("reintentos: ");
		System.out.println(n_retry);
		
		while (n_retry > 0) 
		{
			n_retry = n_retry - 1;
		
			try 
			{
				System.out.println("abrimos tcp ");
				// Se crea el socket
				socket = hsmServerSocket.getSocket();
				
				socket.connect(new InetSocketAddress( hsmServerSocket.getHsmServer().getIpHSM(), 
													  hsmServerSocket.getHsmServer().getPortHSM()));
				
				socket.setSoTimeout(hsmServerSocket.getHsmServer().getTimeout() * 1000);
				
				output 	= new DataOutputStream(socket.getOutputStream());
				input 	= new DataInputStream(socket.getInputStream());

				hsmServerSocket.setSocketStatus(1);
				
				return 0;
			} 
			catch (IOException e) 
			{
				closeSocket(hsmServerSocket);
			
				continue;
			}
		}

		return -1;
	}

	
	/**
	 * Close sockets for ClienteHSMSocketContent
	 * @param hsmServerSocket client socket content
	 */
	
	public void closeSocket(ClienteHSMSocketContent hsmServerSocket ) {

		// Siempre intentamos cerrar el socket
		Socket socket = hsmServerSocket.getSocket();
		try 
		{
			if (socket != null)
				socket.close();
			hsmServerSocket.setSocketStatus(0);
			
			LOG.info ("cerramos tcp ");
		}
		catch (IOException e) 
		{
			LOG.debug("No se pudo cerrar el socket.", e);
		}
	}	
	
	

	/**
	 * SendRecieve Send and receive data on client socket
	 * @param request Parameter request
	 * @return HSMParameters Parameter response
	 * @throws HSMException 
	 * @throws UnsupportedEncodingException
	 */
	public HSMParameters sendReceive(HSMParameters request)  throws HSMException, UnsupportedEncodingException {

		HSMParameters response = new HSMParameters(request);
		
		int retConex = 0;

		if (idxHsm >= lstHSMServers.size())
		{
			// Establecemos por defecto al primer HSM
			idxHsm = 0; 
		}

		hsmServerSocket = lstHSMServers.get(idxHsm);

		// Si es Produccion 
		if (!(hsmServerSocket.getHsmServer().getEnvironmentType() == HSMEnvironmentType.ENV_CONTINGENCY)) 
		{
			System.out.println("Produccion");
			
			// Si es FAST CONNECT o HSM no esta activo?
			if ( hsmServerSocket.getHsmServer().getConnType() == HSMConnectionType.FAST_CONNECT ||
				 hsmServerSocket.getSocketStatus() == 0 ) 
				retConex = openSocket(hsmServerSocket);
		} 
		else
			retConex = -1;

		// Si no conseguimos conectarnos 
		if (retConex != 0) 
		{
			System.out.println("Buscamos otros?");

			// Buscamos en los activos 
			int memoPos = idxHsm;
			idxHsm++;

			if (idxHsm >= lstHSMServers.size())
				idxHsm = 0;

			while (retConex != 0 && idxHsm != memoPos) {
				
				hsmServerSocket = lstHSMServers.get(idxHsm);
				
				// Si es produccion?
				if (!( hsmServerSocket.getHsmServer().getEnvironmentType() == HSMEnvironmentType.ENV_CONTINGENCY ) ) 
				{
					System.out.println("Produccion  buscado");
					if ( hsmServerSocket.getHsmServer().getConnType() == HSMConnectionType.FAST_CONNECT || 
						 hsmServerSocket.getSocketStatus() == 0 )/* (fast connect? o no activo?) */
						retConex = openSocket(hsmServerSocket);
					else
						retConex = 0;/* conexion permanente */

				}
				
				System.out.println(retConex);
				System.out.print("idx:" + idxHsm);
				
				
				if (retConex != 0) idxHsm++;
				else
					break;
				
				
				if (idxHsm >= lstHSMServers.size())
					idxHsm = 0;

			}
			
			
			System.out.println("ret_conex");

			/* buscamos en contingencia */
			if (retConex != 0) 
			{
				idxHsm = 0;
				System.out.println("Buscamos contigencia");
			}
			
			
			while (retConex != 0)
			{
				hsmServerSocket = lstHSMServers.get(idxHsm);
				
				System.out.println(hsmServerSocket.getHsmServer().getEnvironmentType());
				
				if ( hsmServerSocket.getHsmServer().getEnvironmentType() == HSMEnvironmentType.ENV_CONTINGENCY )/* produccion?? */
				{
					System.out.println("HSM_contingencia");

					if (hsmServerSocket.getHsmServer().getConnType() == HSMConnectionType.FAST_CONNECT ||  
						hsmServerSocket.getSocketStatus() == 0 ) /* (fast connect? o no activo?) */
						retConex = openSocket( hsmServerSocket );
					else
						retConex = 0;/* conexion permanente */
				}
				
				
				if (retConex != 0) idxHsm++;
				else 
					break;
				
				if (idxHsm >= lstHSMServers.size()) 
				{
					idxHsm = 0;
					break;
				}
				
			}

			if (retConex != 0) 
			{
				response.setResponseError(HSMResponseCode.SEC_HSM_BAD_CONNECTION);
				closeSocket(hsmServerSocket);
				idxHsm += 1;
			}

			return response;
		}

		String tramaOut = "", tramaIn = "";
		
		try {
			System.out.println("enviar");
			
			// Convert request to TXN
			tramaOut = getRequestHSM(request);

			// Send TXN
			sendMsg(tramaOut.getBytes("UTF8"));
			
            LOG.info(String.format("Sending request [%8.8s][%d]", tramaOut.trim(), tramaOut.length()));

		} catch (InvalidConnectionException e) {
			e.printStackTrace();
		}

		try 
		{
			System.out.println("leer");
			

            tramaIn = receiveMsg();

			String responseCode = getResponseHSM(response, tramaOut, tramaIn);
			
			LOG.info(String.format("Response received [RC:%s][%8.8s][%d]", responseCode, tramaIn.trim(), tramaIn.length()));
		
		}
		catch (InvalidConnectionException e) 
		{
			e.printStackTrace();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		if (hsmServerSocket.getHsmServer().getConnType().getType() == 0) /* fast connect? */
			closeSocket(hsmServerSocket);
		
		idxHsm += 1;  
		
		return response;
	}

	/**
	 * Get length of encoded buffer
	 * @param buffer
	 * @return byte[] two bytes with length of buffer
	 * @throws UnsupportedEncodingException
	 */

	private byte[] getEncodedLength(byte buffer[]) throws UnsupportedEncodingException {
		String strHEX = String.format("%04X", buffer.length);
		byte len[] = { 0, 0 };

		len[0] = Byte.parseByte(strHEX.substring(0, 2), 16);
		len[1] = (byte) Integer.parseInt(strHEX.substring(2, 4), 16);

		return len;
	}

	private void sendMsg(byte msg[]) throws InvalidConnectionException {

		if (output != null) 
		{
			try 
			{
				output.write(getEncodedLength(msg));
				output.write(msg);
				output.flush();
			} 
			catch (IOException e) 
			{
				throw new InvalidConnectionException("No se pudo enviar el mensaje");
			}
		} 
		else 
		{
			throw new InvalidConnectionException("El flujo de envio es NULO");
		}
	}

	private String receiveMsg() throws InvalidConnectionException, Exception 
	{
		/* return leerMsgLocal(); */
		byte[] bytesLeidos = null;

		if (input != null)
		{
			try 
			{
				int length = input.readUnsignedShort();
				bytesLeidos = new byte[length];
				input.readFully(bytesLeidos);
			} 
			catch (IOException e) 
			{
				throw new InvalidConnectionException("No se pudo leer mensaje");
			}
		}
		
		return new String(bytesLeidos);
	}

	private String getRequestHSM(HSMParameters params) throws HSMException 
	{
		String command = new String();

		switch (params.getCommandType()) 
		{
			case CMD_ECHO_TEST:
				command = hsmServerSocket.getHsmServer().getHostCMD().getEchoTestCommand();
				break;
	
			case CMD_GENERATE_KEY:
				command = hsmServerSocket.getHsmServer().getHostCMD().getGenkeyCommand(params);
				break;
	
			case CMD_ENCODE_DATA:
				command = hsmServerSocket.getHsmServer().getHostCMD().getEncodeCommand(params);
				break;
	
			case CMD_DECODE_DATA:
				command = hsmServerSocket.getHsmServer().getHostCMD().getDecodeCommand(params);
				break;
	
			case CMD_TRANSLATE_DATA:
				command = hsmServerSocket.getHsmServer().getHostCMD().getTranslateDataCommand(params);
				break;
	
			case CMD_GENERATE_MAC:
				command = hsmServerSocket.getHsmServer().getHostCMD().getGenMACCommand(params);
				break;
	
			case CMD_VALIDATE_MAC:
				command = hsmServerSocket.getHsmServer().getHostCMD().getValMACCommand(params);
				break;
	
			case CMD_TRANSLATE_MAC:
				command = hsmServerSocket.getHsmServer().getHostCMD().getTranslateMACCommand(params);
		}

		return command;
	}

	private String getResponseHSM(HSMParameters params, String reqCommand, String rspCommad) 
		throws HSMException, UnsupportedEncodingException {
		
		hsmServerSocket.getHsmServer().getHostRSP().isValidResponse(reqCommand, rspCommad);

		String strRspCode = hsmServerSocket.getHsmServer().getHostRSP().getResponseCode(rspCommad);

		if (strRspCode.equals("00")) {
			
			switch (params.getCommandType()) {
			
				case CMD_ECHO_TEST:
					break;
	
				case CMD_GENERATE_KEY:
					hsmServerSocket.getHsmServer().getHostRSP().getGenkeyResponse(rspCommad, params);
					break;
	
				case CMD_ENCODE_DATA:
					hsmServerSocket.getHsmServer().getHostRSP().getEncodeResponse(rspCommad, params);
					break;
	
				case CMD_DECODE_DATA:
					hsmServerSocket.getHsmServer().getHostRSP().getDecodeResponse(rspCommad, params);
					break;
	
				case CMD_TRANSLATE_DATA:
					hsmServerSocket.getHsmServer().getHostRSP().getTranslateDataResponse(rspCommad, params);
					break;
	
				case CMD_GENERATE_MAC:
					hsmServerSocket.getHsmServer().getHostRSP().getGenMACResponse(rspCommad, params);
					break;
	
				case CMD_VALIDATE_MAC:
					hsmServerSocket.getHsmServer().getHostRSP().getValMACResponse(rspCommad, params);
					break;
	
				case CMD_TRANSLATE_MAC:
					hsmServerSocket.getHsmServer().getHostRSP().getTranslateMACResponse(rspCommad, params);
					break;
			}

		}
		
		params.setCommandType(null);
		params.setKeySchemeLength(null);
		params.setResponseCode(strRspCode);
		params.setResponseMessage(HSMResponseCode.toResponseCode(strRspCode).getMessage());

		return strRspCode;
	}
}
