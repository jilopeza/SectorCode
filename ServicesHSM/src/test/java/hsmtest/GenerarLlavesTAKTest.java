package hsmtest;

import org.junit.Test;

import com.novatronic.components.hsm.connection.HSMSocketManager;
import com.novatronic.components.hsm.exception.HSMException;
import com.novatronic.components.hsm.params.HSMParameters;
import com.novatronic.components.hsm.type.HSMCommandType;
import com.novatronic.components.hsm.type.HSMKeySchemeType;
import com.novatronic.components.hsm.type.HSMKeyType;

public class GenerarLlavesTAKTest extends HSMTest {

	// Generar llaves TAK
	// ---------------------------------------------------------------------------------------------
	@Test
	public void test() throws HSMException, Exception {
		
		System.out.println(">>> Enviando comando CMD_GENERATE_KEY");
		
		paramReq = new HSMParameters(HSMKeySchemeType.TRIPLE_LENGTH,
									 HSMKeyType.KEY_TAK, 
									 HSMCommandType.CMD_GENERATE_KEY);

		init();
		paramRsp = HSMSocketManager.sendReceive(paramReq);
		release();

		System.out.println(String.format(
				"RC: [%s: %s], keyValue: [%s], checkValue: [%s]",
				paramRsp.getResponseCode(),
				paramRsp.getResponseMessage(),
				paramRsp.getKeySourceValue(),
				paramRsp.getKeyCheckValue()));		
		
	}

}
