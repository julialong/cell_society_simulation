package cellsociety_team08;

import java.io.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;

public class ParserXML {
	private Document doc;
	public ParserXML(File file) {
		try {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance(); // https://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		doc = dBuilder.parse(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		doc.getDocumentElement().normalize();
	}
	
	public int[][] getCellList() {
		
		NodeList cellList = doc.getElementsByTagName("cell");
		int[][] onCells = new int[cellList.getLength()][];
		
		for (int x = 0; x < cellList.getLength(); x++) {
			Node cellNode = cellList.item(x);
			
			Element eElement = (Element) cellNode;
			
			int xsize = Integer.parseInt(eElement.getElementsByTagName("ycoord").item(0).getTextContent());
			int ysize = Integer.parseInt(eElement.getElementsByTagName("xcoord").item(0).getTextContent());			
			
			int[] tempCoordinate = {xsize, ysize};
			onCells[x] = tempCoordinate;			
			
		}
		
		return onCells;
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
			
			int[] dim = {xsize, ysize};
	
			return dim;
	
	}
	
}

