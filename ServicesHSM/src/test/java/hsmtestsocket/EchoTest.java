package hsmtestsocket;

import org.junit.Test;

import com.novatronic.components.hsm.connection.HSMSocketManager;
import com.novatronic.components.hsm.exception.HSMException;
import com.novatronic.components.hsm.params.HSMParameters;
import com.novatronic.components.hsm.type.HSMCommandType;

public class EchoTest extends HSMTest {
	
	// Enviar Echo Test
	// ---------------------------------------------------------------------------------------------
	@Test
	public void test() throws HSMException, Exception {
		
		try {
			init();
			
			System.out.println(">>> Enviando comando de CMD_ECHO_TEST");
			paramReq = new HSMParameters(HSMCommandType.CMD_ECHO_TEST);
			paramRsp = HSMSocketManager.sendReceive(paramReq);
			
			release();

			System.out.println(String.format("RC: [%s: %s]",
					paramRsp.getResponseCode(),
					paramRsp.getResponseMessage()) );
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

