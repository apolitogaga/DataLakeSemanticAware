package dataLake;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import utils.TextProcesser;
import dataAmazonType.*;

public class LoadXML {
	// change this to add the absolute path to the amazon dataset.
	public static final String DATA_DIRECTORY = "data/amazonData/";
	public static final String META_DIR = "data/metadata/";

	public static final String rootMeta= "sources";
	public static final String sourceMeta= "source";
	public static final String sparql = "sparql";
	public static final String entity = "entity";
	public static final String entity_name = "entity_name";
	public static final String uri = "uri";
	public static final String name = "name";
	public static final String service = "service";
	public static final String type = "type";
	
//	public final String amazonRoot;
//	public final ArrayList<String> amazonNode;
	
	
	public static void parseXML(ProcessReviews proc) {
		File fXmlFile = new File(DATA_DIRECTORY + "fixed/negative.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;

		String title = null;
		String user = null;
		String rating = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			System.out.println("Root element :"
					+ doc.getDocumentElement().getNodeName());
			NodeList nList = doc.getElementsByTagName("review");

			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					title = eElement.getElementsByTagName("product_name")
							.item(0).getTextContent();
					rating = eElement.getElementsByTagName("rating").item(0)
							.getTextContent();
					user = eElement.getElementsByTagName("reviewer").item(0)
							.getTextContent();
					proc.newReview(TextProcesser.processTitle(title).trim(),
							rating.trim(), user.trim());
				}
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void parseMetaDataConfig(){
		File fXmlFile = new File(META_DIR + "metadataConfig.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName(sourceMeta);
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					String title = eElement.getElementsByTagName(sparql)
							.item(0).getTextContent();
					String rating = eElement.getElementsByTagName(entity).item(0)
							.getTextContent();
					String user = eElement.getElementsByTagName("reviewer").item(0)
							.getTextContent();
				}
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
