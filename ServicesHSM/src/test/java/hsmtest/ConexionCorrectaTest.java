package hsmtest;

import org.junit.Test;

import com.novatronic.components.hsm.connection.HSMSocketManager;
import com.novatronic.components.hsm.exception.HSMException;
import com.novatronic.components.hsm.params.HSMParameters;
import com.novatronic.components.hsm.type.HSMCommandType;
import com.novatronic.components.hsm.type.HSMKeySchemeType;
import com.novatronic.components.hsm.type.HSMKeyType;
import com.novatronic.components.hsm.type.HSMMACLengthType;

public class ConexionCorrectaTest extends HSMTest {

	// Cifrar data modo CBC con vector de inicializaci�n por defecto.
	// ---------------------------------------------------------------------------------------------
	@Test
	public void test() throws HSMException, Exception {

		try {
			
			// ---------------------------------------------------------------
			// El servidor ya se inicializó y no es necesario volver hacerlo
			// ---------------------------------------------------------------
			
			init();
			
			for (int n=0; n<10; ++n)
			{
				System.out.println(String.format(">>> #%d, Enviando comando CMD_ENCODE_DATA", n));
					
				paramReq = new HSMParameters(
						HSMKeySchemeType.TRIPLE_LENGTH,
						HSMKeyType.KEY_TAK,
						"T45B03CFDF03D5C0EDBC824CB547CA3AE6D98FC10130B4ADF",
						HSMMACLengthType.MAC_08,
						new String(
								"Emp000150504060A61E674E88C6A7E0A61E674E88C6A7E9912813"),
						HSMCommandType.CMD_GENERATE_MAC);
				paramRsp = HSMSocketManager.sendReceive(paramReq);
				macValue = paramRsp.getMacValue();
				System.out.print(String.format(
						"RC: [%s: %s], encodedData: [%s]",
						paramRsp.getResponseCode(),
						paramRsp.getResponseMessage(), encodedData));
			}
			
			release();
			
			// --------------------------------------------
			// Después de esta línea se libera el servidor
			// --------------------------------------------
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
