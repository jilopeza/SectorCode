package hsmtest;

import org.junit.Test;

import com.novatronic.components.hsm.connection.HSMSocketManager;
import com.novatronic.components.hsm.exception.HSMException;
//import org.apache.commons.codec.binary.Base64;
//import org.apache.commons.codec.binary.Base64;
import com.novatronic.components.hsm.params.HSMParameters;
import com.novatronic.components.hsm.type.HSMCommandType;
import com.novatronic.components.hsm.type.HSMEncryptionModeType;
import com.novatronic.components.hsm.type.HSMKeySchemeType;
import com.novatronic.components.hsm.type.HSMKeyType;

public class CifrarDataECBTest extends HSMTest {

	// Cifrar data con modo ECB sin vector de inicializaci�n / sin KSN
	// ---------------------------------------------------------------------------------------------
	
	@Test
	public void test() throws HSMException, Exception {
		
		System.out.println(">>> Enviando comando CMD_ENCODE_DATA");
		
		paramReq = new HSMParameters(HSMKeySchemeType.TRIPLE_LENGTH,
									 HSMKeyType.KEY_DEK, 
									 __DEK_KEY_1,
									 HSMEncryptionModeType.M_ECB, 
									 clearData,
									 HSMCommandType.CMD_ENCODE_DATA);

//		init();
		paramRsp = HSMSocketManager.sendReceive(paramReq);
//		release();

		System.out.print(String.format("RC: [%s: %s], encodedData: [%s]",
							paramRsp.getResponseCode(), 
							paramRsp.getResponseMessage(), 
							encodedData));
		
	}

}


