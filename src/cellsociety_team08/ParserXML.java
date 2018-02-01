package cellsociety_team08;

import java.io.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;

public class ParserXML {
	Document doc;
	public ParserXML(String filename) {
		try {
		File input = new File(filename);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance(); // https://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		doc = dBuilder.parse(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
		doc.getDocumentElement().normalize();
	}
	
	public void getCells() {
		NodeList cellList = doc.getElementsByTagName("cell");
		for (int x = 0; x < cellList.getLength(); x++) {
			Node cellNode = cellList.item(x);
			System.out.println(cellNode.getNodeName());
			Element eElement = (Element) cellNode;
			System.out.println("x: " + eElement.getElementsByTagName("xcoord").item(0).getTextContent());
			System.out.println("y: " + eElement.getElementsByTagName("ycoord").item(0).getTextContent());
			
		}
	}
	
	public String getSimulationType() {
		return doc.getDocumentElement().getNodeName();
	}
	
	public int[] getDimensions(){
			
			NodeList dimensions = doc.getElementsByTagName("dimensions");
			Node dimensionsNode = dimensions.item(0);
			Element eElement = (Element) dimensionsNode;						
			
			int xsize = Integer.parseInt(eElement.getElementsByTagName("xsize").item(0).getTextContent());
			int ysize = Integer.parseInt(eElement.getElementsByTagName("ysize").item(0).getTextContent());
			
			System.out.println("Dimensions: " + xsize + " wide by " + ysize + " tall");
			
			int[] dim = new int[2];
			dim[0] = xsize;
			dim[1] = ysize;
			return dim;
	
	}
	
}

