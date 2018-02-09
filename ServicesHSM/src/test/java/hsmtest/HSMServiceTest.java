package hsmtest;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import com.novatronic.components.hsm.params.HSMParameters;
import com.novatronic.components.services.HSMServiceManager;

public class HSMServiceTest {

	public HSMServiceTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws Exception {

		URL url = new URL("http://localhost:8080/ServicesHSM-0.0.1-SNAPSHOT/hsmmanager?wsdl");
		QName qname = new QName("http://www.novatronic.com/ws", "HSMServiceManager");
		Service service = Service.create(url, qname);
		HSMServiceManager hsmService = service.getPort(HSMServiceManager.class);

		HSMParameters parameters = hsmService.echoTest();

		System.out.println(String.format(">>> EchoTest [%s][%s]", parameters.getResponseCode(), parameters.getResponseMessage()));

	}

}


