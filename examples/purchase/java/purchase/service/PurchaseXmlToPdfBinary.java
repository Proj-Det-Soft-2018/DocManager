package purchase.service;

import java.net.URL;

import org.apache.log4j.Logger;

import business.service.ConcreteProcessService;
import business.service.XmlToPdfBinary;

public class PurchaseXmlToPdfBinary extends XmlToPdfBinary {
	
	private static final URL FO_TEMPLATE_PATH = ConcreteProcessService.class.getResource("/fo_templates/purchase_xml2fo.xsl");
	private static final Logger LOGGER = Logger.getLogger(PurchaseXmlToPdfBinary.class);
	
	public PurchaseXmlToPdfBinary() {
		super(LOGGER);
	}

	@Override
	protected URL getXslPath() {
		return FO_TEMPLATE_PATH;
	}

	@Override
	protected String getXslFolderPath() {
		String path = FO_TEMPLATE_PATH.getPath();
		return "file://" + path.substring(0, path.lastIndexOf("/fo_templates/purchase_xml2fo.xsl"));
	}

}
