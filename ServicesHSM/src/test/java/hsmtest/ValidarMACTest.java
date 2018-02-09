package hsmtest;

import org.junit.Test;

import com.novatronic.components.hsm.connection.HSMSocketManager;
import com.novatronic.components.hsm.exception.HSMException;
import com.novatronic.components.hsm.params.HSMParameters;
import com.novatronic.components.hsm.type.HSMCommandType;
import com.novatronic.components.hsm.type.HSMKeySchemeType;
import com.novatronic.components.hsm.type.HSMKeyType;
import com.novatronic.components.hsm.type.HSMMACLengthType;

public class ValidarMACTest extends HSMTest {

	// Validar MAC de longitud 8
	// ---------------------------------------------------------------------------------------------
	@Test
	public void test() throws HSMException, Exception {
		
		System.out.println(">>> Enviando comando CMD_VALIDATE_MAC");
		
		paramReq = new HSMParameters(HSMKeySchemeType.TRIPLE_LENGTH,
									 HSMKeyType.KEY_TAK, 
									 __TAK_KEY_1, 
									 HSMMACLengthType.MAC_08,
									 macValue, 
									 clearData, 
									 HSMCommandType.CMD_VALIDATE_MAC);
		
		init();
		paramRsp = HSMSocketManager.sendReceive(paramReq);
		release();
		
		System.out.print(String.format("RC: [%s: %s]",
							paramRsp.getResponseCode(), 
							paramRsp.getResponseMessage()));
		
	}

}
