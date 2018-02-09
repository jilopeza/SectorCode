package hsmtest;

import java.io.InputStream;

import org.junit.Test;

import com.novatronic.components.hsm.connection.HSMSocketManager;
import com.novatronic.components.hsm.exception.HSMException;
import com.novatronic.components.hsm.params.HSMParameters;
import com.novatronic.components.hsm.type.HSMCommandType;
import com.novatronic.components.hsm.type.HSMEncryptionModeType;
import com.novatronic.components.hsm.type.HSMKeySchemeType;
import com.novatronic.components.hsm.type.HSMKeyType;

public class ConexionIncorrecta extends HSMTest {

	// Cifrar data modo CBC con vector de inicializaci�n por defecto.
	// ---------------------------------------------------------------------------------------------
	@Test
	public void test() throws HSMException, Exception {

		try {
			// ------------------------------------------------------------------------------
			// El servidor ya se inicializó y no es necesario volver hacerlo, esto fallaría
			// ------------------------------------------------------------------------------
			InputStream is = HSMTest.class.getClassLoader().getResourceAsStream("hsm-servers.xml");

			HSMSocketManager.serverInit(is);
			HSMSocketManager.serverInit(is);
			
			for (int n=0; n<10; ++n)
			{
				System.out.println ( ">>> Número: " + n );
		
					System.out.println(">>> Enviando comando CMD_ENCODE_DATA");
					paramReq = new HSMParameters(HSMKeySchemeType.TRIPLE_LENGTH,
												 HSMKeyType.KEY_DEK,
												 __DEK_KEY_1,
												 "0123456789ABCDEF",
												 HSMEncryptionModeType.M_CBC,
												 clearData,
												 HSMCommandType.CMD_ENCODE_DATA);
					paramRsp = HSMSocketManager.sendReceive(paramReq);
					encodedData = paramRsp.getDataBuffer();
					System.out.print(String.format("RC: [%s: %s], encodedData: [%s]",
										paramRsp.getResponseCode(), 
										paramRsp.getResponseMessage(), 
										encodedData));
					
			}
			
			// ----------------------------------------------------------------------
			// Al fallar la segunda inicialización del servidor, esto es innecesario
			// ----------------------------------------------------------------------
			HSMSocketManager.serverRelease();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
