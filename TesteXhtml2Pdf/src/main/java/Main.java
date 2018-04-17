import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;

import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.FormattingResults;
import org.apache.fop.apps.MimeConstants;
import org.apache.fop.apps.PageSequenceResults;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class Main {

	public static void main(String[] args) {

		URL html = Main.class.getResource("templates/declaration_template.html");
		URL styleSheet = Main.class.getResource("html2pdf/xhtml2fo.xsl");

		File input = new File(html.getFile());
		File baseDir = new File(".");
		File outDir = new File(baseDir, "out");
		outDir.mkdirs();
		File pdffile = new File(outDir, "ResultFO2PDF.pdf");

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

		if (db != null) {
			db.setEntityResolver((publicId, systemId) -> {
                return new InputSource(new StringReader(""));
            });
			
			Document w3cDoc = null;
			
			try {
				w3cDoc = db.parse(input);
			} catch (Exception e) {
				e.printStackTrace();
			}

			Document foDoc = xml2FO(w3cDoc, styleSheet);
			
			
			// PRINTA O XML - Somente para testes
			try {
				printDocument(foDoc, System.out);
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				convertFO2PDF(foDoc, pdffile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static Document xml2FO(Document xml, URL styleSheet) {

		DOMSource xmlDomSource = new DOMSource(xml);
		DOMResult domResult = new DOMResult();

		Transformer transformer = getTransformer(styleSheet);


		if (transformer == null) {
			System.out.println("Error creating transformer for " + styleSheet);
			System.exit(1);
		}
		try {
			transformer.transform(xmlDomSource, domResult);
		}
		catch (javax.xml.transform.TransformerException e) {
			e.printStackTrace();
			return null;
		}
		return (Document) domResult.getNode();

	}

	private static Transformer getTransformer(URL styleSheet) {

		try {

			TransformerFactory tFactory = TransformerFactory.newInstance();

			DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();

			dFactory.setNamespaceAware(true);

			DocumentBuilder dBuilder = dFactory.newDocumentBuilder();
			Document xslDoc = dBuilder.parse(styleSheet.openStream());
			DOMSource xslDomSource = new DOMSource(xslDoc);

			return tFactory.newTransformer(xslDomSource);

		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	//http://svn.apache.org/viewvc/xmlgraphics/fop/trunk/fop/examples/embedding/java/embedding/ExampleFO2PDF.java?view=markup

	public static void convertFO2PDF(Document fo, File pdf) throws IOException, FOPException {


		FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
		OutputStream out = null;

		try {
			FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
			// configure foUserAgent as desired

			// Setup output stream.  Note: Using BufferedOutputStream
			// for performance reasons (helpful with FileOutputStreams).
			out = new FileOutputStream(pdf);
			out = new BufferedOutputStream(out);

			// Construct fop with desired output format
			Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

			// Setup JAXP using identity transformer
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(); // identity transformer

			// Setup input stream
			Source src = new DOMSource(fo);

			// Resulting SAX events (the generated FO) must be piped through to FOP
			Result res = new SAXResult(fop.getDefaultHandler());

			// Start XSLT transformation and FOP processing
			transformer.transform(src, res);

			// Result processing
			FormattingResults foResults = fop.getResults();
			List<?> pageSequences = foResults.getPageSequences();
			for (Object pageSequence : pageSequences) {
				PageSequenceResults pageSequenceResults = (PageSequenceResults) pageSequence;
				System.out.println("PageSequence "
						+ (String.valueOf(pageSequenceResults.getID()).length() > 0
								? pageSequenceResults.getID() : "<no id>")
						+ " generated " + pageSequenceResults.getPageCount() + " pages.");
			}
			System.out.println("Generated " + foResults.getPageCount() + " pages in total.");

			out.close();
		} catch (Exception e) {
			e.printStackTrace(System.err);
			System.exit(-1);
		}
	}

	public static void printDocument(Document doc, OutputStream out) throws IOException, TransformerException {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

		transformer.transform(new DOMSource(doc), 
				new StreamResult(new OutputStreamWriter(out, "UTF-8")));
	}
}
