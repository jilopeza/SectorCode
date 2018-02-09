package hsmtest;

import org.junit.Test;

import com.novatronic.components.hsm.connection.HSMSocketManager;
import com.novatronic.components.hsm.exception.HSMException;
import com.novatronic.components.hsm.params.HSMParameters;
import com.novatronic.components.hsm.type.HSMCommandType;
import com.novatronic.components.hsm.type.HSMEncryptionModeType;
import com.novatronic.components.hsm.type.HSMKeySchemeType;
import com.novatronic.components.hsm.type.HSMKeyType;

public class DescifrarDataECBTest extends HSMTest {

	// Descifrar data modo ECB
	// --------------------------------------------------------------------
	@Test
	public void test() throws HSMException, Exception {
		encodedData = new String("627A777AF21577665EA7ABFB82612F770C62F61C81B70FC0D6624C5A50EA5972E5A5"
				+ "CC51F3E7FC9E9E83F043C034ACE866E406C50A6802E17544E9B39FB5778573A6B1CE26CCA225403A8388F8B6CC"
				+ "FE5465A50988A7CF848D5EDC4E2EDA7D87817E881B9D3256C84C8771AF77464D5BE8B6B55DA1E52F313E222E42E"
				+ "E7BAC9349128DB73EA4FFC6F0FBB37921C042252B4180CD2F786F17AC2CDB1D716AF5FEF593DB018FCDB86F58167"
				+ "EC4C1B7C1A7780E6C17BBE2218DE77A9BF89568FEE40C904A29431F8A460DE8EF2C4E0D152C1D83FFBB2E69D6A7");
		
		System.out.println(">>> Enviando comando CMD_DECODE_DATA");

		// Se generan los parámetros 
		paramReq = new HSMParameters(HSMKeySchemeType.TRIPLE_LENGTH,
							HSMKeyType.KEY_DEK, 
							__DEK_KEY_1, 
							HSMEncryptionModeType.M_ECB,
							encodedData,
							HSMCommandType.CMD_DECODE_DATA);

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
