package health.service;

import java.net.URL;

import org.apache.log4j.Logger;

import business.service.ConcreteProcessService;
import business.service.XmlToPdfBinary;

public class HealthXmlToPdfBinary extends XmlToPdfBinary {

	private static final URL FO_TEMPLATE_PATH = ConcreteProcessService.class.getResource("/fo_templates/xml2fo.xsl");
	private static final Logger LOGGER = Logger.getLogger(HealthXmlToPdfBinary.class);

	public HealthXmlToPdfBinary() {
		super(LOGGER);
	}

	@Override
	protected URL getXslPath() {
		return FO_TEMPLATE_PATH;
	}

	@Override
	protected String getXslFolderPath() {
		String path = FO_TEMPLATE_PATH.getPath();
		String folderPath = "file://" + path.substring(0, path.lastIndexOf("/fo_templates/xml2fo.xsl"));
		return folderPath;
	}

}