package hsmtest;

import org.junit.Test;

import com.novatronic.components.hsm.connection.HSMSocketManager;
import com.novatronic.components.hsm.exception.HSMException;
import com.novatronic.components.hsm.params.HSMParameters;
import com.novatronic.components.hsm.type.HSMCommandType;
import com.novatronic.components.hsm.type.HSMKeySchemeType;
import com.novatronic.components.hsm.type.HSMKeyType;
import com.novatronic.components.hsm.type.HSMMACLengthType;

public class GenerarMACTest extends HSMTest {

	// Generar MAC de longitud 8
	// ---------------------------------------------------------------------------------------------
	@Test
	public void test() throws HSMException, Exception {
		
		System.out.println(">>> Enviando comando CMD_GENERATE_MAC");

		paramReq = new HSMParameters(HSMKeySchemeType.TRIPLE_LENGTH,
									 HSMKeyType.KEY_TAK,
									 __TAK_KEY_1,
									 HSMMACLengthType.MAC_08,
									 clearData,
									 HSMCommandType.CMD_GENERATE_MAC);
		init();
		paramRsp = HSMSocketManager.sendReceive(paramReq);
	    release();

	    macValue = paramRsp.getMacValue();
		System.out.print(String.format("RC: [%s: %s], macValue: [%s]",
				paramRsp.getResponseCode(), 
				paramRsp.getResponseMessage(), 
				macValue));
			
	}
}

