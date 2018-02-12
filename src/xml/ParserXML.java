package xml;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.*;
import javax.xml.parsers.*;

/**
 * ParserXML class, designed to parse XML files to interpret for simulation
 * @author Edward Zhuang
 *	Help received from:
 *	https://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
 */
public class ParserXML {
	private Document doc;
	
	/**
	 * Constructor for ParserXML
	 * @param file XML file to be parsed
	 */
	public ParserXML(File file) {
		try {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		doc = dBuilder.parse(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		doc.getDocumentElement().normalize();
	}
	
	/**
	 * Finds cell types and prints them out
	 */
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
	
	/**
	 * 
	 * @param cellNode cellNode which contains x and y coordinates of a cell
	 * @return returns the coordinates of a cell
	 */
	public int[] getCellInfo(Node cellNode) {
			
			Element eElement = (Element) cellNode;
			
			int xsize = Integer.parseInt(eElement.getElementsByTagName("ycoord").item(0).getTextContent());
			int ysize = Integer.parseInt(eElement.getElementsByTagName("xcoord").item(0).getTextContent());		
						
			int[] cell = {xsize, ysize};
		
		return cell;
	}
	
	/**
	 * checks to see if a simulation is random
	 * @return returns true if the XML file calls for a random simulation, false otherwise
	 */
	public boolean isRandom() {
		NodeList checkRandom = doc.getElementsByTagName("cell_on_list");
		Node isRandom = checkRandom.item(0);
		Element rElement = (Element) isRandom;
		return (!Boolean.parseBoolean(rElement.getAttribute("isOn")));
	}
	
	/**
	 * Collects all the cells from an XML file, uses getCellInfo
	 * @return returns a map with key of cell type and value of int[][] which contains list of coordinates of that type of cell
	 */
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

				tempArray[x] = getCellInfo(cellNode);
			}

			cellMap.put(temp, tempArray);
		}

		return cellMap;
	}
	
	/**
	 * Gets the parameters of an XML file
	 * @return returns map with keys as parameters and values as doubles
	 */
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
	
	
	/**
	 * Gets the type of simulation
	 * @return String simulation type
	 */
	public String getSimulationType() {
		return doc.getDocumentElement().getNodeName();
	}
	/**
	 * Gets the dimensions of simulation
	 * @return int[] containing width and height of a simulation
	 */
	public int[] getDimensions(){
			
			NodeList dimensions = doc.getElementsByTagName("dimensions");
			Node dimensionsNode = dimensions.item(0);
			Element eElement = (Element) dimensionsNode;						
			
			int xsize = Integer.parseInt(eElement.getElementsByTagName("xsize").item(0).getTextContent());
			int ysize = Integer.parseInt(eElement.getElementsByTagName("ysize").item(0).getTextContent());
			
			int[] dim = {xsize, ysize};
	
			return dim;
	
	}
	
}

