package xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import userInterface.AbstractGridView;
import userInterface.CellShapeType;
import userInterface.UserInterface;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public abstract class AbstractXml {
    protected ArrayList<ArrayList<Integer>> myColArray = new ArrayList<>();
    protected ArrayList<ArrayList<Integer>> myRowArray = new ArrayList<>();
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
        setUpSimulationParameters(this.myColArray, this.myRowArray, this.doc);
    }

    protected abstract void setUpSimulationParameters(ArrayList<ArrayList<Integer>> myColArray, ArrayList<ArrayList<Integer>> myRowArray, Document doc);

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

    public abstract void saveCurrentSimulation(AbstractGridView myGridView, File xmlFilePath);


}
