package hsmtest;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.junit.Test;

import com.novatronic.components.hsm.connection.HSMSocketManager;
import com.novatronic.components.hsm.exception.HSMException;
import com.novatronic.components.hsm.params.HSMParameters;
import com.novatronic.components.hsm.type.HSMCommandType;
import com.novatronic.components.hsm.type.HSMEncryptionModeType;
import com.novatronic.components.hsm.type.HSMKeySchemeType;
import com.novatronic.components.hsm.type.HSMKeyType;
import com.novatronic.components.services.HSMServiceManager;

public class CifrarDataCBCTest extends HSMTest {

	// Cifrar data modo CBC con vector de inicializaci�n por defecto.
	// ---------------------------------------------------------------------------------------------
	@Test
	public void test() throws HSMException, Exception {
		
		
		URL url = new URL("http://localhost:8080/ServicesHSM-0.0.1-SNAPSHOT/hsmmanager?wsdl");
		QName qname = new QName("http://www.novatronic.com/ws", "HSMServiceManager");
		Service service = Service.create(url, qname);
		HSMServiceManager hsmService = service.getPort(HSMServiceManager.class);

		System.out.println("Enviando comando CMD_ENCODE_DATA ");
		
		HSMParameters parameters = hsmService.encodeData(HSMKeySchemeType.TRIPLE_LENGTH,
														 HSMKeyType.KEY_DEK,
														 __DEK_KEY_1,
														 "0123456789ABCDEF",
														 HSMEncryptionModeType.M_CBC,
														 clearData);

		encodedData = parameters.getDataBuffer();
		System.out.println(String.format("Recibiendo RC: [%s: %s], encodedData: [%s]",
							parameters.getResponseCode(), 
							parameters.getResponseMessage(), 
							encodedData));

	}
}
