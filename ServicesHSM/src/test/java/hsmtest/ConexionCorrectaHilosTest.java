package hsmtest;

import org.junit.Test;

import com.novatronic.components.hsm.connection.HSMSocketManager;
import com.novatronic.components.hsm.exception.HSMException;
import com.novatronic.components.hsm.params.HSMParameters;
import com.novatronic.components.hsm.type.HSMCommandType;
import com.novatronic.components.hsm.type.HSMEncryptionModeType;
import com.novatronic.components.hsm.type.HSMKeySchemeType;
import com.novatronic.components.hsm.type.HSMKeyType;

public class ConexionCorrectaHilosTest extends HSMTest implements Runnable {
	static int num = 1;
	
	public ConexionCorrectaHilosTest(){
	}

	/*
	 * Ejecutar test con un solo hilo en consola
	 */
	public static void main(String ... args)throws HSMException, Exception{
		ConexionCorrectaHilosTest testFuerza = new ConexionCorrectaHilosTest();
		testFuerza.testeo();
	}
	
	/*
	 * Ejecutar un testeo con "n" hilos
	 */
	@Test
	public void test() throws HSMException, Exception {
		for (int i = 0; i < 5 ; i++){
			new Thread((Runnable)new ConexionCorrectaHilosTest()).start();
		}
		Thread.currentThread().join();
	}

	@Override
	public void run() {
		try {
			this.testeo();
			System.out.println("TEST FINAL - HILO : " + num);
			num++;
		} catch (HSMException e) {
			System.out.println("HSMException");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Exception");
			e.printStackTrace();
		}
	}
	
	public void testeo()throws HSMException, Exception{
		encodedData = new String("A5CAD287C389B6A3866E13805E954C5E632F21E6CAA87604EF2E14BE0C60A9AFC08FE"
				+ "6F16556BB7359A4871284AD14D691D2CEA21A2B470C06D7952CE40795A8F4E3B9B8DB5D42B5FE473516B71E719"
				+ "FAC6F2CDB710EB99FDDAD7810045843292DA2CCD30D4FC4B2D9465D6AAF6977557E1A6C5C9E598242EC211A3F9"
				+ "19A040CC5209EBCA42FDEADD648C4032D7F9B8864B68CED3C429CDDFE53EA004DA2AD2D43B6A17C261CE1B6770"
				+ "E5A3C45A6BD60CCD66D338D3D339732963BA309DD1A1C68B587E1CB9CF38AD58C870139697EE2E3E51CBC80E78"
				+ "EDA779A2086255FADA34D87CF0EC1B6DD50A772F4EDD452E72C");
		
		System.out.println(">>> Enviando comando CMD_DECODE_DATA");
		// Se generan los parámetros 
		paramReq = new HSMParameters(HSMKeySchemeType.TRIPLE_LENGTH,
							HSMKeyType.KEY_DEK, 
							__DEK_KEY_1, 
							 "0123456789ABCDEF",
							HSMEncryptionModeType.M_CBC,
							encodedData,
							HSMCommandType.CMD_DECODE_DATA);
		System.out.println(">>> Enviando...");
		// Se envía el comando
		init();
		
		paramRsp = HSMSocketManager.sendReceive(paramReq);
		
		release();

		// Se obtiene la data descifrada y el código de respuesta.
		System.out.println(String.format("RC: [%s: %s], decodedData: [%s]",
							paramRsp.getResponseCode(),
							paramRsp.getResponseMessage(), 
							paramRsp.getDataBuffer()) );
	}
}
