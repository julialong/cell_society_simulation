package cellsociety_team08;

import java.io.*;
import java.util.Map;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;

// https://www.mkyong.com/java/how-to-create-xml-file-in-java-dom/

public class WriterXML {
	
	private StreamResult result;
	private DOMSource source;
	private Document doc;
	private Element baseXML;
	private Element dimensions;
	private Element parameterList;
	/**
	 * creates a writerXML file
	 * @param fileName desired name of written XML file
	 */
	public WriterXML(String fileName, String simType, Map<String, Double> map, int x, int y) {
		String destination = "/Users/edwar/eclipse-workspace/cellsociety_team08/data/" + fileName + ".xml";
		System.out.println(destination);
		result = new StreamResult(new File(destination));
		
		try {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		// root elements
		doc = docBuilder.newDocument();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		baseXML = doc.createElement(simType);
		doc.appendChild(baseXML);
		
		Element authors = doc.createElement("authors");
		Element name1 = doc.createElement("name");
		name1.appendChild(doc.createTextNode("Edward Zhuang"));
		Element name2 = doc.createElement("name");
		name2.appendChild(doc.createTextNode("Julia Long"));
		Element name3 = doc.createElement("name");
		name3.appendChild(doc.createTextNode("Jeffrey Li"));
		
		authors.appendChild(name1);
		authors.appendChild(name2);
		authors.appendChild(name3);
		
		baseXML.appendChild(authors);
		
		addDimensions(x, y);
		addParams(map);
		
		
		
		}
	/**
	 * adds cells
	 */
	public void addCells() {
		Element cellsList = doc.createElement("cell_on_list");
		
	}
	/**
	 * adds all parameters
	 */
	public void addParams(Map<String, Double> map) {
		parameterList = doc.createElement("parameter_list");
		
		for (String key: map.keySet()) {
			
			Element temp = doc.createElement("parameters");
			Attr attribute = doc.createAttribute("id");
			attribute.setValue(key);
			temp.setAttributeNode(attribute);
			
			Element tempValue = doc.createElement("value");
			tempValue.appendChild(doc.createTextNode(Double.toString(map.get(key))));
			temp.appendChild(tempValue);
			parameterList.appendChild(temp);
			
		}
		
		baseXML.appendChild(parameterList);
		
	}
	/**
	 * adds dimensions of cell
	 */
	public void addDimensions(int x, int y) {
		dimensions = doc.createElement("dimensions");
		
		Element xsize = doc.createElement("xsize");
		xsize.appendChild(doc.createTextNode(Integer.toString(x)));
		dimensions.appendChild(xsize);
		
		Element ysize = doc.createElement("ysize");
		ysize.appendChild(doc.createTextNode(Integer.toString(y)));
		dimensions.appendChild(ysize);
		
		baseXML.appendChild(dimensions);
	}
	/**
	 * add's author information, adds title of simulation
	 */
	public void addInfo() {
		
	}
	
	public void convert() {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		
		
		
		
		try {
			//https://stackoverflow.com/questions/7373567/how-to-read-and-write-xml-files
			Transformer transformer = transformerFactory.newTransformer();
			  transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			  transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			  transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			  transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			
		source = new DOMSource(doc);
		
		try {
			transformer.transform(source, result);
			
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.out.println("Success!");
		
		
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
