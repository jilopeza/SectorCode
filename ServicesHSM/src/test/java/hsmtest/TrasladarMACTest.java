package hsmtest;

import org.junit.Test;

import com.novatronic.components.hsm.connection.HSMSocketManager;
import com.novatronic.components.hsm.exception.HSMException;
import com.novatronic.components.hsm.params.HSMParameters;
import com.novatronic.components.hsm.type.HSMCommandType;
import com.novatronic.components.hsm.type.HSMKeySchemeType;
import com.novatronic.components.hsm.type.HSMKeyType;
import com.novatronic.components.hsm.type.HSMMACLengthType;

public class TrasladarMACTest extends HSMTest {

	// Trasladar MAC de longitud 8 con diferentes llaves
	// ---------------------------------------------------------------------------------------------
	@Test
	public void test() throws HSMException, Exception {
		
		System.out.println(">>> Enviando comando CMD_TRANSLATE_MAC");

		paramReq = new HSMParameters(HSMKeySchemeType.TRIPLE_LENGTH,
									 HSMKeyType.KEY_TAK, 
									 __TAK_KEY_1, 
									 HSMKeyType.KEY_TAK,
									 __TAK_KEY_2, 
									 HSMMACLengthType.MAC_08, 
									 macValue, 
									 clearData,
									 HSMCommandType.CMD_TRANSLATE_MAC);

		init();
		paramRsp = HSMSocketManager.sendReceive(paramReq);
		release();

		translatedMAC = paramRsp.getMacValue();
		System.out.print(String.format("RC: [%s: %s], translatedMAC: [%s]",
							paramRsp.getResponseCode(), 
							paramRsp.getResponseMessage(), 
							translatedMAC));
		
	}

}
