package cellsociety_team08;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
	
	
	public void findCellTypes() {
		NodeList typeList = doc.getElementsByTagName("state");
		for (int x = 0; x < typeList.getLength(); x++) {
			Node typeNode = typeList.item(x);
			System.out.println("\nCurrent Element:" + typeNode.getNodeName());
			Element eElement = (Element) typeNode;
			System.out.println("Type: " + eElement.getAttribute("category"));
			System.out.println("Name: " + eElement.getElementsByTagName("label").item(0).getTextContent());
		}
		
	}
	
	public int[] getCellInfo(Node cellNode) {
		
//		NodeList cellList = doc.getElementsByTagName(cells);
//		int[][] onCells = new int[cellList.getLength()][];
		
			
			Element eElement = (Element) cellNode;
			
			int xsize = Integer.parseInt(eElement.getElementsByTagName("ycoord").item(0).getTextContent());
			int ysize = Integer.parseInt(eElement.getElementsByTagName("xcoord").item(0).getTextContent());		
			
			
			int[] cell = {xsize, ysize};

		
		return cell;
	}
	
	public Map<String, int[][]> getAllCells() {

		Map<String, int[][]> cellMap = new HashMap<String, int[][]>();
		NodeList types = doc.getElementsByTagName("type");
		for (int i = 0; i < types.getLength(); i++) {
			Node node = types.item(i);
			Element eElement = (Element) node;

			String temp = eElement.getAttribute("id");

			NodeList childNodes = eElement.getElementsByTagName("cell");

			int[][] tempArray = new int[childNodes.getLength()][];

			for (int x = 0; x < childNodes.getLength(); x++) {
				Node cellNode = childNodes.item(x);
				Element cElement = (Element) cellNode;

				tempArray[x] = getCellInfo(cellNode);
			}

			cellMap.put(temp, tempArray);
		}

		return cellMap;
	}
	
	public Map<String, Double> getParameters(){
		Map<String, Double> paramMap = new HashMap<String, Double>();
		NodeList params = doc.getElementsByTagName("parameters");
		for (int i = 0; i < params.getLength(); i++) {
			Node node = params.item(i);
			Element eElement = (Element) node;

			String temp = eElement.getAttribute("id");

			Double tempDouble = Double.parseDouble(eElement.getElementsByTagName("value").item(0).getTextContent());

			paramMap.put(temp, tempDouble);
		}

		return paramMap;
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
			
			//System.out.println("Dimensions: " + xsize + " wide by " + ysize + " tall");
			
			int[] dim = {xsize, ysize};
	
			return dim;
	
	}
}

