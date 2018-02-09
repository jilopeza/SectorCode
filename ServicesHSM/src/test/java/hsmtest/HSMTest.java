package hsmtest;

import java.io.InputStream;
import java.util.Date;

import com.novatronic.components.hsm.connection.HSMSocketManager;
import com.novatronic.components.hsm.params.HSMParameters;

public class HSMTest {
	static String __DEK_KEY_1 = "T6773E0FE6742060CBDB1C5BA7339ABB13242AADEF31C37D8";
	static String __DEK_KEY_2 = "TE019535F1D41FFC9D17D34B38C5DE2040BE7BB120C624240";
	static String __TAK_KEY_1 = "T665510B179815293CCAFCB42513911FFA4680526DE0666BD";
	static String __TAK_KEY_2 = "TFBAC42A7A1CA1B53178C468F1626D729D9C611637BD51738";
	static Date dateIni, dateFin;

	protected String clearData;
	protected String encodedData;
	protected String translatedData;

	protected String macValue;
	protected String translatedMAC;

	protected HSMParameters paramReq;
	protected HSMParameters paramRsp;

	public HSMTest() {
		clearData = new String(
//				"MANTÉN TUS PENSAMIENTOS POSITIVOS PORQUE ÉSTOS SE CONVIERTEN EN TUS PALABRAS. "
//				+ "MANTÉN TUS PALABRAS EN POSITIVO PORQUE ÉSTAS SE CONVIERTEN EN TU COMPORTAMIENTO. "
//				+ "MANTÉN TU COMPORTAMIENTO EN POSITIVO PORQUE ÉSTE SE CONVIERTE EN TUS HÁBITOS. "
//				+ "MANTÉN TUS HÁBITOS EN POSITIVO PORQUE ÉSTOS SE TRANSFORMAN EN TUS VALORES. "
//				+ "MANTÉN TUS VALORES EN POSITIVO PORQUE ÉSTOS SE TRANSFORMAN Y SE CONVIERTEN FINALMENTE EN TU DESTINO Y SE CONVIERTEN EN TU");
//				"MANTEN TUS PENSAMIENTOS POSITIVOS PORQUE ESTOS SE CONVIERTEN EN TUS PALABRAS. MANTEN TUS PALABRAS EN POSITIVO PORQUE ESTAS SE CONVIERTEN EN TU COMPORTAMIENTO. MANTEN TU COMPORTAMIENTO EN POSITIVO PORQUE ESTE SE CONVIERTE EN TUS HABITOS. MANTEN TUS HABITOS EN POSITIVO PORQUE ESTOS SE TRANSFORMAN EN TUS VALORES MANTEN TUS VALORES EN POSITIVO PORQUE ESTOS SE TRANSFORMAN Y SE CONVIERTEN FINALMENTE EN TU DESTINO Y SE CONVIERTEN EN TU");
				"Cuarteta XXXV, 3º centuria, Nostradamus: "
						+ "Bestias feroces de hambre ríos tragar, / "
						+ "la mayor parte del campo contra Hister estará, / "
						+ "en jaula de hierro el grande hará llevar, / "
						+ "cuando nada el hijo de germano observará. Víctor Carreño  ");
		encodedData = new String(
				"A5CAD287C389B6A3866E13805E954C5E632F21E6CAA87604EF2E14BE0C60A9AFC08FE"
						+ "6F16556BB7359A4871284AD14D691D2CEA21A2B470C06D7952CE40795A8F4E3B9B8DB5D42B5FE473516B71E719"
						+ "FAC6F2CDB710EB99FDDAD7810045843292DA2CCD30D4FC4B2D9465D6AAF6977557E1A6C5C9E598242EC211A3F9"
						+ "19A040CC5209EBCA42FDEADD648C4032D7F9B8864B68CED3C429CDDFE53EA004DA2AD2D43B6A17C261CE1B6770"
						+ "E5A3C45A6BD60CCD66D338D3D339732963BA309DD1A1C68B587E1CB9CF38AD58C870139697EE2E3E51CBC80E78"
						+ "EDA779A2086255FADA34D87CF0EC1B6DD50A772F4EDD452E72C");
		translatedData = new String();

		macValue = "2EDBDCDD";
		translatedMAC = "";

		paramReq = null;
		paramRsp = null;

	}

	public static void init() {
		try {
			InputStream is = HSMTest.class.getClassLoader().getResourceAsStream("hsm-servers.xml");

			HSMSocketManager.serverInit(is);
			dateIni = new Date();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static void release() throws Exception {
		dateFin = new Date();
		System.out.println(String.format(">>> Elapsed time[%s]",
				dateFin.getTime() - dateIni.getTime()));
		HSMSocketManager.serverRelease();
	}
}
