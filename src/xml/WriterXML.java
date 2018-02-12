package xml;

import java.io.*;
import java.util.Map;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;

/**
 * WriterXML class, designed to write XML files of a simulations state
 * @author Edward Zhuang
 * 		Help received from
 *         https://www.mkyong.com/java/how-to-create-xml-file-in-java-dom/
 *         https://stackoverflow.com/questions/7373567/how-to-read-and-write-xml-files
 */
public class WriterXML {

	private StreamResult result;
	private DOMSource source;
	private Document doc;
	private Element baseXML;
	private Element dimensions;
	private Element parameterList;

	/**
	 * Constructor for the WriterXML file
	 * @param fileName name of desired file to be created
	 * @param simType type of simulation that is being created
	 * @param map contains map with Parameter keys and Double values
	 * @param cellMap contains map of cell types as keys and int[][] of location of cells with aforementioned cell types
	 * @param x x value for dimension 
	 * @param y y value for dimension
	 */
	public WriterXML(String fileName, String simType, Map<String, Double> map, Map<String, int[][]> cellMap, int x,
			int y) {
		String destination = "/Users/edwar/eclipse-workspace/cellsociety_team08/data/" + fileName + ".xml";
		result = new StreamResult(new File(destination));

		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			// root elements
			doc = docBuilder.newDocument();
		} catch (Exception e) {
			e.printStackTrace();
		}

		baseXML = doc.createElement(simType);
		doc.appendChild(baseXML);

		addAuthors();
		addDimensions(x, y);
		addParams(map);
		addCells(cellMap);

	}

	/**
	 * Adds authors to the XML file
	 */
	public void addAuthors() {
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
	}

	/**
	 * Adds all cells to the XML file, organizes them by type
	 * @param map map containing cell types as values and int[][] of coordinates of that type of cell
	 */
	public void addCells(Map<String, int[][]> map) {
		Element cellsList = doc.createElement("cell_on_list");
		Attr attribute = doc.createAttribute("isOn");
		attribute.setValue("true");
		cellsList.setAttributeNode(attribute);
		for (String key : map.keySet()) {
			Element cellType = doc.createElement("type");
			Attr typeAttribute = doc.createAttribute("id");
			typeAttribute.setValue(key);
			cellType.setAttributeNode(typeAttribute);
			int[][] tempArray = map.get(key);
			for (int x = 0; x < tempArray.length; x++) {
				Element cell = doc.createElement("cell");
				Element ycoord = doc.createElement("ycoord");
				Element xcoord = doc.createElement("xcoord");
				ycoord.appendChild(doc.createTextNode(Integer.toString(tempArray[x][0])));
				xcoord.appendChild(doc.createTextNode(Integer.toString(tempArray[x][1])));

				cell.appendChild(ycoord);
				cell.appendChild(xcoord);
				cellType.appendChild(cell);
			}
			cellsList.appendChild(cellType);

		}
		baseXML.appendChild(cellsList);

	}

	/**
	 * Adds all parameters to the XML file along with specific values
	 * @param map map containing parameter keys and double values
	 */
	public void addParams(Map<String, Double> map) {
		parameterList = doc.createElement("parameter_list");

		for (String key : map.keySet()) {
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
	 * Adds dimensions to the XML file
	 * @param x x size of dimension
	 * @param y y size of dimension
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
	 * Creates the actual XML file, formats it as well
	 */
	public void convert() {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();

		try {
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

		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
