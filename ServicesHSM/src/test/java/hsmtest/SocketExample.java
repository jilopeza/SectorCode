package hsmtest;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;

public class SocketExample {
	final String HOST = "172.29.43.164";
	final int PUERTO = 1500;
	Socket sc;
	DataOutputStream mensaje;
	DataInputStream entrada;
	static Date dateIni, dateFin;
	
    byte[] getEncodedLength(byte buffer[]) throws UnsupportedEncodingException
    {
        String strHEX = String.format("%04X", buffer.length);
        byte len[] = {0, 0};
        
        len[0] = Byte.parseByte(strHEX.substring(0,2), 16);
        len[1] = (byte)Integer.parseInt(strHEX.substring(2,4), 16);
        
        return len;        
    }	
	
	byte[] concat(byte[]...arrays)
	{
	    // Determine the length of the result array
	    int totalLength = 0;
	    for (int i = 0; i < arrays.length; i++)
	    {
	        totalLength += arrays[i].length;
	    }

	    // create the result array
	    byte[] result = new byte[totalLength];

	    // copy the source arrays into the result array
	    int currentIndex = 0;
	    for (int i = 0; i < arrays.length; i++)
	    {
	        System.arraycopy(arrays[i], 0, result, currentIndex, arrays[i].length);
	        currentIndex += arrays[i].length;
	    }

	    return result;
	}
	
	// Cliente
	public void sendCrypt() 
	{
		try {
			String cuarteta = "Cuarteta XXXV, 3º centuria, Nostradamus: "
					+ "Bestias feroces de hambre ríos tragar, / "
					+ "la mayor parte del campo contra Hister estará, / "
					+ "en jaula de hierro el grande hará llevar, / "
					+ "cuando nada el hijo de germano observará";
			
//			cuarteta = 
//					"MANTÉN TUS PENSAMIENTOS POSITIVOS PORQUE ÉSTOS SE CONVIERTEN EN TUS PALABRAS. "
//					+ "MANTÉN TUS PALABRAS EN POSITIVO PORQUE ÉSTAS SE CONVIERTEN EN TU COMPORTAMIENTO. "
//					+ "MANTÉN TU COMPORTAMIENTO EN POSITIVO PORQUE ÉSTE SE CONVIERTE EN TUS HÁBITOS. "
//					+ "MANTÉN TUS HÁBITOS EN POSITIVO PORQUE ÉSTOS SE TRANSFORMAN EN TUS VALORES. "
//					+ "MANTÉN TUS VALORES EN POSITIVO PORQUE ÉSTOS SE TRANSFORMAN Y SE CONVIERTEN FINALMENTE EN TU DESTINO Y SE CONVIERTEN EN TU";
			
			byte dataBuffer[] = Base64.encodeBase64(cuarteta.getBytes()); 
			int dataLength = dataBuffer.length; 
			byte trama_top[] = ("abcdM0000100BT6773E0FE6742060CBDB1C5BA7339ABB13242AADEF31C37D8" + String.format("%04X", dataLength)).getBytes();
			byte trama_bottom[] = "%00789505".getBytes();
			
			byte trama[] = concat ( trama_top, dataBuffer, trama_bottom );
			
			
//			byte[] bytesEncoded = Base64.encodeBase64(cuarteta.getBytes());
//			System.out.println("ecncoded value is " + new String(bytesEncoded));
//			
//			// Decode data on other side, by processing encoded data
//			byte[] valueDecoded= Base64.decodeBase64(bytesEncoded );
//			System.out.println("Decoded value is " + new String(valueDecoded));			
			
			sc = new Socket(HOST, PUERTO); 
			mensaje = new DataOutputStream(sc.getOutputStream());
			
			dateIni = new Date();
			
			System.out.println("Enviando mensaje: " );
			
			for (int i = 0; i < trama.length; i++) {
				System.out.print(String.format("%c", trama[i]));
			}
			
			System.out.println(String.format("\nLongitud = %d\n", trama.length) );
			
			mensaje.write(getEncodedLength(trama));
			mensaje.write(trama);
			mensaje.flush();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	public void sendDecrypt() 
	{
		try {
			String trama = "abcdM2011000BT92182608AA9880713797C964C87B8974DBC718E2604562D7000000000000000001C0"
					+ "E91D2405C9C7883052AA663E370CC33AA1A92E68E22DD2270D4D223222D3786D9E20D1687E6EBF4161854DF"
					+ "549BEF74DCD9DA636DE0E6AB77E9AA7E991AF2925458066252A549C613E3127589B47C5978CEDA250CFCFA2"
					+ "0EE6F7ED992B689AD732753D0C5E07C1724D69B8B73981C48411E5F2F9DADCF795D0D86B8C46A991E79C923"
					+ "B4BA1991F1F0E24B29E478B6B39BEF81B8F3BB5C2BB726DD99A64EAAF592DBF3A63F5930F19CF184B8FB5B7"
					+ "79A93E5EABB51E98CBB9B659420A84ECAA99C8F838ACFBDBCBBCAD8F5F33F1172C4D1BAE0D16B935E8876F5"
					+ "E95786EA66285%00789505";
			
			sc = new Socket(HOST, PUERTO); 
			mensaje = new DataOutputStream(sc.getOutputStream());
			
			// Enviamos el mensaje
			System.out.println("Enviando mensaje: " + trama);
			byte[] buffer = trama.getBytes("UTF8");
			
			System.out.println(String.format("Longitud = %d\n", buffer.length) );
			
			mensaje.write(getEncodedLength(buffer));
			mensaje.write(buffer);
			mensaje.flush();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	public void recive() 
	{
		try {
			// creamos el flujo de datos por el que se enviara un mensaje
			entrada = new DataInputStream(sc.getInputStream());
			
			// leemos el mensaje
			int length = entrada.readUnsignedShort();
			byte[] buffer = new byte[length];
			
			entrada.readFully(buffer);
			
			String trama = new String(buffer);
			
			System.out.println("Recibiendo: " + trama);
			System.out.println(String.format("Longitud = %d\n", buffer.length) );
			dateFin = new Date();

			// cerramos la conexi�n
			sc.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}	
	
	public static void main(String[] args) 
	{
		SocketExample socket = new SocketExample();
		
		try {
				socket.sendCrypt();
				socket.recive();
		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println(String.format(">>> Elapsed time[%s]",  dateFin.getTime() - dateIni.getTime()));
		

//		socket.sendDecrypt();
//		socket.recive();
	}
}


