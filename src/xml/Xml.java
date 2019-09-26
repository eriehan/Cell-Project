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
import userInterface.UserInterface;
import utils.Point;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class Xml {
    private ArrayList<ArrayList<Integer>> myColArray = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> myRowArray = new ArrayList<>();
    private File xmlFile;
    private String shape;
    private int isSaved;
    private int numAgents;
    private int cellGridColNum;
    private int cellGridRowNum;
    private int rate;
    private String myTitle;
    private UserInterface myUserInterface;
    private ArrayList<Integer> EnergyArray = new ArrayList<>();
    private ArrayList<Integer> MaturityArray = new ArrayList<>();
    private DocumentBuilderFactory documentBuilderFactory;
    private DocumentBuilder docBuilder;
    private Document doc;

    public Xml(UserInterface myUserInterface){
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

    public ArrayList<Integer> getEnergyArray(){
        ArrayList<Integer> copy = new ArrayList<>();
        copy.addAll(EnergyArray);
        return copy;
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

    public ArrayList<Integer> getMaturityArray(){
        ArrayList<Integer> copy = new ArrayList<>();
        copy.addAll(MaturityArray);
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
        setUpSimulationParameters(this.myColArray, this.myRowArray, this.doc);
    }



    private void setUpSimulationParameters(ArrayList<ArrayList<Integer>> myColArray, ArrayList<ArrayList<Integer>> myRowArray, Document doc) {
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
            if (this.myTitle.equals("Wa-Tor")) {
                this.EnergyArray.add(Integer.parseInt(n.getElementsByTagName("Energy").item(0).getTextContent()));
                this.MaturityArray.add(Integer.parseInt(n.getElementsByTagName("Mature").item(0).getTextContent()));
            }
            String row = n.getElementsByTagName("Row").item(0).getTextContent();
            String col = n.getElementsByTagName("Column").item(0).getTextContent();
            myRowArray.add(stringToIntArray(row));
            myColArray.add(stringToIntArray(col));
        }
    }

    private ArrayList<Integer> stringToIntArray(String s) {
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

    public void saveCurrentSimulation(AbstractGridView myGridView, File xmlFilePath) throws ParserConfigurationException, TransformerException {
        Map<Point, Cell> myMap = myGridView.getMyCellGrid().getGridOfCells();

        ArrayList<ArrayList<Integer>> colArray = new ArrayList<>();
        ArrayList<ArrayList<Integer>> rowArray = new ArrayList<>();
        ArrayList<Integer> agent1Row = new ArrayList<>();
        ArrayList<Integer> agent2Row = new ArrayList<>();
        ArrayList<Integer> agent1Col = new ArrayList<>();
        ArrayList<Integer> agent2Col = new ArrayList<>();
        for (Map.Entry<Point, Cell> entry: myMap.entrySet()
        ) {
            Cell c = entry.getValue();
            //have one for the single states
            if(c.getState().equals(CellState.PERCOLATED) || c.getState().equals(CellState.ALIVE) ||
                    c.getState().equals(CellState.FIRSTAGENT)||
                    c.getState().equals(CellState.SHARK) || c.getState().equals(CellState.BURNING)){
                agent1Row.add(entry.getKey().getRow());
                agent1Col.add(entry.getKey().getCol());
            }
            else if (c.getState().equals(CellState.SECONDAGENT) || c.getState().equals(CellState.FISH)
            || c.getState().equals(CellState.BLOCKED) || c.getState().equals(CellState.FIREEMPTY)){
                agent2Row.add(entry.getKey().getRow());
                agent2Col.add(entry.getKey().getCol());
            }
        }

        if(agent1Col.size() != 0){
            colArray.add(agent1Col);
        }
        if(agent2Col.size() != 0){
            colArray.add(agent2Col);
        }
        if(agent1Row.size() != 0){
            rowArray.add(agent1Row);
        }
        if(agent2Row.size() != 0){
            rowArray.add(agent2Row);
        }

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

        if(rowArray.size() >= 1){
            Element agent0 = document.createElement("Agent0");
            if(this.myTitle.equals("Wa-Tor")){
                Element energy = document.createElement("Energy");
                energy.appendChild(document.createTextNode(Integer.toString(this.getEnergyArray().get(0))));
                agent0.appendChild(energy);
                Element maturity = document.createElement("Mature");
                maturity.appendChild(document.createTextNode(Integer.toString(this.getMaturityArray().get(0))));
                agent0.appendChild(maturity);
            }
            Element row0 = document.createElement("Row");

            String s = "" + rowArray.get(0).get(0);
            for(int i = 1; i < rowArray.get(0).size(); i++){
                s = s + " " + rowArray.get(0).get(i);
            }

            row0.appendChild(document.createTextNode(s));
            agent0.appendChild(row0);


            String c = "" + colArray.get(0).get(0);
            for(int i = 1; i < colArray.get(0).size(); i++){
                c = c + " " + colArray.get(0).get(i);
            }
            Element col0 = document.createElement("Column");
            col0.appendChild(document.createTextNode(c));
            agent0.appendChild(col0);
            root.appendChild(agent0);

            System.out.println(rowArray);

            if(rowArray.size() > 1) {
                Element agent1 = document.createElement("Agent1");
                if(this.myTitle.equals("Wa-Tor")){
                    Element energy = document.createElement("Energy");
                    energy.appendChild(document.createTextNode(Integer.toString(this.getEnergyArray().get(1))));
                    agent1.appendChild(energy);
                    Element maturity = document.createElement("Mature");
                    maturity.appendChild(document.createTextNode(Integer.toString(this.getMaturityArray().get(1))));
                    agent1.appendChild(maturity);
                }
                Element row1 = document.createElement(("Row"));
                String r1 = "" + rowArray.get(1).get(0);
                for (int i = 1; i < rowArray.get(1).size(); i++) {
                    r1 = r1 + " " + rowArray.get(1).get(i);
                }
                row1.appendChild(document.createTextNode(r1));
                agent1.appendChild(row1);

                Element col1 = document.createElement("Column");
                String c1 = "" + rowArray.get(1).get(0);
                for (int i = 1; i < colArray.get(1).size(); i++) {
                    c1 = c1 + " " + colArray.get(1).get(i);
                }
                col1.appendChild(document.createTextNode(c1));
                agent1.appendChild(col1);
                root.appendChild(agent1);
            }
        }


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
}
