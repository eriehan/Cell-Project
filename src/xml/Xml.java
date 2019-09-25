package xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import userInterface.CellShapeType;
import userInterface.UserInterface;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Xml {
    private ArrayList<ArrayList<Integer>> myColArray = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> myRowArray = new ArrayList<>();
    private File xmlFile;
    private int numAgents = 0;
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
        return myInts;
    }

    private void determineCellShape(Document doc) {
        Node shapeNode = doc.getElementsByTagName("Shape").item(0);
        Element shapeElement = (Element) shapeNode;
        String shape = shapeElement.getAttribute("name");
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
}
