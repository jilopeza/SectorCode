package hsmtest;

import org.junit.Test;

import com.novatronic.components.hsm.connection.HSMSocketManager;
import com.novatronic.components.hsm.exception.HSMException;
import com.novatronic.components.hsm.params.HSMParameters;
import com.novatronic.components.hsm.type.HSMCommandType;
import com.novatronic.components.hsm.type.HSMKeySchemeType;
import com.novatronic.components.hsm.type.HSMKeyType;

public class TrasladarDataECBTest extends HSMTest {

	// Trasladar data modo ECB con diferentes llaves
	// ---------------------------------------------------------------------------------------------
	@Test
	public void test() throws HSMException, Exception {

		System.out.println(">>> Enviando comando CMD_TRANSLATE_DATA");

		paramReq = new HSMParameters(HSMKeySchemeType.TRIPLE_LENGTH,
									  HSMKeyType.KEY_DEK, 
									  __DEK_KEY_1,
									  HSMKeyType.KEY_DEK, 
									  __DEK_KEY_2,
									  encodedData,
									  HSMCommandType.CMD_TRANSLATE_DATA);

		init();
		paramRsp = HSMSocketManager.sendReceive(paramReq);
		release();
		
		translatedData = paramRsp.getDataBuffer();
		System.out.print(String.format("RC: [%s: %s], encodedData: [%s]",
								paramRsp.getResponseCode(), 
								paramRsp.getResponseMessage(), 
								encodedData));
		
	}

}
