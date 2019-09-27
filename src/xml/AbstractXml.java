package xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import simulation.Cell;
import simulation.CellState;
import userInterface.AbstractGridView;
import userInterface.CellShapeType;
import userInterface.SimulationButton;
import userInterface.UserInterface;
import utils.Point;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AbstractXml {
    protected ArrayList<ArrayList<Integer>> myColArray = new ArrayList<>();
    protected ArrayList<ArrayList<Integer>> myRowArray = new ArrayList<>();
    protected ArrayList<Integer> agent0Col;
    protected ArrayList<Integer> agent0Row;
    protected ArrayList<Integer> agent1Col;
    protected ArrayList<Integer> agent1Row;
    protected File xmlFile;
    protected String shape;
    protected int isSaved;
    protected int numAgents;
    protected int cellGridColNum;
    protected int cellGridRowNum;
    protected int rate;
    protected String myTitle;
    protected UserInterface myUserInterface;
    protected DocumentBuilderFactory documentBuilderFactory;
    protected DocumentBuilder docBuilder;
    protected Document doc;

    public AbstractXml(UserInterface myUserInterface){
        this.myUserInterface = myUserInterface;
    }

    public int getNumAgents(){
        return this.numAgents;
    }
    public int getIsSaved(){return this.isSaved;}
    public int getCellGridColNum(){
        return this.cellGridColNum;
    }
    public int getCellGridRowNum(){
        return this.cellGridRowNum;
    }
    public String getMyTitle(){
        return this.myTitle;
    }
    public ArrayList<ArrayList<Integer>> getMyColArray(){
        ArrayList<ArrayList<Integer>> copy = new ArrayList<>();
        copy.addAll(myColArray);
        return copy;
    }

    public ArrayList<ArrayList<Integer>> getMyRowArray(){
        ArrayList<ArrayList<Integer>> copy = new ArrayList<>();
        copy.addAll(myRowArray);
        return copy;
    }

    public int getRate(){
        return this.rate;
    }

    public void parse(String file) throws IOException, SAXException, ParserConfigurationException {
        this.xmlFile = new File(String.valueOf(file));
        this.documentBuilderFactory = DocumentBuilderFactory.newInstance();
        this.docBuilder = this.documentBuilderFactory.newDocumentBuilder();
        this.doc = this.docBuilder.parse(this.xmlFile);
        determineCellShape(this.doc);
        setUpSimulationParameters();
       // setUpSimulationParameters(this.myColArray, this.myRowArray, this.doc);
    }

   // protected abstract void setUpSimulationParameters(ArrayList<ArrayList<Integer>> myColArray, ArrayList<ArrayList<Integer>> myRowArray, Document doc);



    protected void setUpSimulationParameters(){
            this.cellGridColNum = Integer.parseInt(doc.getElementsByTagName("Col").item(0).getTextContent());
            this.myUserInterface.setNumOfCols(this.cellGridColNum);
            this.cellGridRowNum = Integer.parseInt(doc.getElementsByTagName("Row").item(0).getTextContent());
            this.myUserInterface.setNumOfRows(this.cellGridRowNum);
            this.rate = Integer.parseInt(doc.getElementsByTagName("Rate").item(0).getTextContent());
            this.isSaved = Integer.parseInt(doc.getElementsByTagName("Saved").item(0).getTextContent());
            NodeList typeOfSimulation = doc.getElementsByTagName("Type");
            //goes through NodeList to get information about the different simulation
            for (int i = 0; i < typeOfSimulation.getLength(); i++) {
                //gets the i-th simulation type and casts it as a node
                Node currentSimulationType = typeOfSimulation.item(i);
                Element currentSimulationElement = (Element) currentSimulationType;
                this.myTitle = currentSimulationElement.getAttribute("name");
                this.myUserInterface.changeTitle(this.myTitle);
                this.numAgents = Integer.parseInt(currentSimulationElement.getTextContent());
            }
            for (int i = 0; i < this.numAgents; i++) {
                NodeList agent = doc.getElementsByTagName("Agent" + i);
                Element n = (Element) agent.item(0);
                String row = n.getElementsByTagName("Row").item(0).getTextContent();
                String col = n.getElementsByTagName("Column").item(0).getTextContent();
                myRowArray.add(stringToIntArray(row));
                myColArray.add(stringToIntArray(col));
            }
    }

    private void determineCellShape(Document doc) {
        Node shapeNode = doc.getElementsByTagName("Shape").item(0);
        Element shapeElement = (Element) shapeNode;
        shape = shapeElement.getAttribute("name");
        switch (shape) {
            case "triangle":
                this.myUserInterface.setCellShape(CellShapeType.TRIANGLE);
                break;
            case "rectangle":
                this.myUserInterface.setCellShape(CellShapeType.RECTANGLE);
                break;
            case "hexagon":
                this.myUserInterface.setCellShape(CellShapeType.HEXAGON);
                break;
        }
    }

    protected Document stageXml() throws ParserConfigurationException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();

        //make root element
        Element root = document.createElement("Simulation");
        document.appendChild(root);

        Element saved = document.createElement("Saved");
        saved.appendChild(document.createTextNode("1"));
        root.appendChild(saved);

        //Shape element
        Element shape = document.createElement("Shape");
        shape.appendChild(document.createTextNode("1"));
        shape.setAttribute("name", this.shape);
        root.appendChild(shape);


        Element type = document.createElement("Type");
        type.setAttribute("name", this.myTitle);
        type.appendChild(document.createTextNode(Integer.toString(numAgents)));
        root.appendChild(type);

        Element Row = document.createElement("Row");
        Row.appendChild(document.createTextNode(Integer.toString(this.cellGridRowNum)));
        root.appendChild(Row);

        Element Col = document.createElement("Col");
        Col.appendChild(document.createTextNode(Integer.toString(this.cellGridColNum)));
        root.appendChild(Col);

        Element Rate = document.createElement("Rate");
        Rate.appendChild(document.createTextNode(Integer.toString(this.rate)));
        root.appendChild(Rate);
        return document;
    }

    protected ArrayList<Integer> stringToIntArray(String s) {
        StringBuilder myStringBuilder = new StringBuilder(s);
        int startIndex = 0;
        int endIndex = 1;

        ArrayList<Integer> myInts = new ArrayList<>();
        while (endIndex < myStringBuilder.length()) {
            if (myStringBuilder.charAt(endIndex) == ' ') {
                myInts.add(Integer.parseInt(myStringBuilder.substring(startIndex, endIndex)));
                startIndex = endIndex + 1;
                endIndex = startIndex + 1;
            } else {
                endIndex++;
            }
        }
        myInts.add(Integer.parseInt("" + myStringBuilder.charAt(myStringBuilder.length()-1)));
        return myInts;
    }

    protected void saveCellState(Map<Point, Cell> myMap, CellState state1, CellState state2, List<ArrayList<Integer>> colArray,
                                 List<ArrayList<Integer>> rowArray){
        agent0Col = new ArrayList<>();
        agent0Row = new ArrayList<>();
        agent1Col = new ArrayList<>();
        agent1Row = new ArrayList<>();
        for (Map.Entry<Point, Cell> entry: myMap.entrySet()
        ) {
            Cell c = entry.getValue();
            if(c.getState().equals(state1)){
                agent0Row.add(entry.getKey().getRow());
                agent0Col.add(entry.getKey().getCol());
            }
            else if(c.getState().equals(state2)){
                agent1Row.add(entry.getKey().getRow());
                agent1Col.add(entry.getKey().getCol());
            }
        }

        colArray.add(agent0Col);
        colArray.add(agent1Col);
        rowArray.add(agent0Row);
        rowArray.add(agent1Row);


    }

    private String convertArrayToString(ArrayList<Integer> array){
        String s = "" + array.get(0);
        for(int i = 1; i < array.size(); i++){
            s = s + " " + array.get(i);
        }
        return s;
    }


    protected void saveCellState(Map<Point, Cell> myMap, CellState state1, List<ArrayList<Integer>> colArray, List<ArrayList<Integer>> rowArray){
        agent0Col = new ArrayList<>();
        agent0Row = new ArrayList<>();
        for (Map.Entry<Point, Cell> entry: myMap.entrySet()
        ) {
            Cell c = entry.getValue();
            if(c.getState().equals(state1)){
                agent0Row.add(entry.getKey().getRow());
                agent0Col.add(entry.getKey().getCol());
            }
        }
        colArray.add(agent0Col);
        rowArray.add(agent0Row);

    }

    protected void addAgents(Document document, String agentIdentifier,
                             ArrayList<Integer> rowArray, ArrayList<Integer> colArray){
        String agent = "Agent" + agentIdentifier;
        Element agentElement = document.createElement(agent);
        Element row = document.createElement("Row");
        row.appendChild(document.createTextNode(convertArrayToString(rowArray)));
        Element col = document.createElement("Column");
        col.appendChild(document.createTextNode(convertArrayToString(colArray)));
        agentElement.appendChild(row);
        agentElement.appendChild(col);
       // (Element)document.getElementsByTagName("Simulation").item(0).appendChild(agentElement);
        document.getFirstChild().appendChild(agentElement);
    }

    protected void addAgents(Document document, String agentIdentifier,
                             ArrayList<Integer> rowArray, ArrayList<Integer> colArray,
                             int maturity, int energy){
        String agent = "Agent" + agentIdentifier;
        Element agentElement = document.createElement(agent);
        Element row = document.createElement("Row");
        row.appendChild(document.createTextNode(convertArrayToString(rowArray)));
        Element col = document.createElement("Column");
        col.appendChild(document.createTextNode(convertArrayToString(colArray)));
        agentElement.appendChild(row);
        agentElement.appendChild(col);

        Element ma = document.createElement("Mature");
        Element en = document.createElement("Energy");
        ma.appendChild(document.createTextNode(Integer.toString(maturity)));
        en.appendChild(document.createTextNode(Integer.toString(energy)));
        agentElement.appendChild(ma);
        agentElement.appendChild(en);
        // (Element)document.getElementsByTagName("Simulation").item(0).appendChild(agentElement);
        document.getFirstChild().appendChild(agentElement);
    }

    protected void createXmlFilePath(Document document, File xmlFilePath) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StringBuilder file = new StringBuilder(String.valueOf(xmlFilePath));
        String filePath = file.substring(0, file.length() - 4);
        filePath = filePath + "Saved.xml";
        System.out.println(filePath);
        StreamResult streamResult = new StreamResult(new File(filePath));
        System.out.println(streamResult);
        transformer.transform(domSource, streamResult);

    }


    protected void saveCurrentSimulation(AbstractGridView myGridView, CellState state1,
                                         CellState state2, File xmlFilePath) throws ParserConfigurationException, TransformerException {
        Document document = stageXml();
        Map<Point, Cell> myMap = myGridView.getMyCellGrid().getGridOfCells();
        ArrayList<ArrayList<Integer>> colArray = new ArrayList<>();
        ArrayList<ArrayList<Integer>> rowArray = new ArrayList<>();
        saveCellState(myMap, state1, state2, colArray, rowArray);
        for(int i = 0; i < rowArray.size(); i++){
            addAgents(document, Integer.toString(i), rowArray.get(i), colArray.get(i));
        }
//        addAgents(document, "0", rowArray.get(0), colArray.get(0));
//        addAgents(document, "1", rowArray.get(1), colArray.get(1));
        createXmlFilePath(document, xmlFilePath);
    }
   // public abstract void saveCurrentSimulation(AbstractGridView myGridView, File xmlFilePath) throws ParserConfigurationException, TransformerException;

    protected void saveCurrentSimulation(AbstractGridView myGridView, CellState state1,
                                         File xmlFilePath) throws TransformerException, ParserConfigurationException {
        Document document = stageXml();
        Map<Point, Cell> myMap = myGridView.getMyCellGrid().getGridOfCells();
        ArrayList<ArrayList<Integer>> colArray = new ArrayList<>();
        ArrayList<ArrayList<Integer>> rowArray = new ArrayList<>();
        saveCellState(myMap, state1, colArray, rowArray);
        for(int i = 0; i < rowArray.size(); i++){
            addAgents(document, Integer.toString(i), rowArray.get(i), colArray.get(i));
        }
//        addAgents(document, "0", rowArray.get(0), colArray.get(0));
//        addAgents(document, "1", rowArray.get(1), colArray.get(1));
        createXmlFilePath(document, xmlFilePath);
    }

    public abstract List<Integer> getMaturityArray();

    public abstract List<Integer> getEnergyArray();

    public abstract void saveCurrentSimulation(AbstractGridView myGridView, File myConfigFile) throws ParserConfigurationException, TransformerException;

    // public abstract void saveCurrentSimulation(AbstractGridView myGridView, File myConfigFile) throws ParserConfigurationException, TransformerException;
}
