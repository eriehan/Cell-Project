package xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import simulation.Cell;
import simulation.CellState;
import userInterface.AbstractGridView;
import userInterface.UserInterface;
import utils.Point;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;

public class WaTorXml extends AbstractXml{
    private ArrayList<Integer> EnergyArray = new ArrayList<>();
    private ArrayList<Integer> MaturityArray = new ArrayList<>();

    public WaTorXml(UserInterface myUserInterFace){
        super(myUserInterFace);
    }

    @Override
    protected void setUpSimulationParameters(ArrayList<ArrayList<Integer>> myColArray, ArrayList<ArrayList<Integer>> myRowArray, Document doc) {
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
            this.EnergyArray.add(Integer.parseInt(n.getElementsByTagName("Energy").item(0).getTextContent()));
            this.MaturityArray.add(Integer.parseInt(n.getElementsByTagName("Mature").item(0).getTextContent()));
            String row = n.getElementsByTagName("Row").item(0).getTextContent();
            String col = n.getElementsByTagName("Column").item(0).getTextContent();
            myRowArray.add(stringToIntArray(row));
            myColArray.add(stringToIntArray(col));
        }

    }

    @Override
    public void saveCurrentSimulation(AbstractGridView myGridView, File xmlFilePath) throws ParserConfigurationException, TransformerException {
        Document document = stageXml();
        System.out.println("Entered");
        Map<Point, Cell> myMap = myGridView.getGridManager().getCellGrid().getGridOfCells();
        ArrayList<ArrayList<Integer>> colArray = new ArrayList<>();
        ArrayList<ArrayList<Integer>> rowArray = new ArrayList<>();
        saveCellState(myMap, CellState.SHARK, CellState.FISH);
        colArray.add(agent0Col);
        colArray.add(agent1Col);
        rowArray.add(agent0Row);
        rowArray.add(agent1Row);
        addAgents(document, "0", rowArray.get(0), colArray.get(0),
                getMaturityArray().get(0), getEnergyArray().get(0));
        addAgents(document, "1", rowArray.get(1), colArray.get(1),
                getMaturityArray().get(1), getEnergyArray().get(1));
        createXmlFilePath(document, xmlFilePath);

    }

    private void addEnergyAndMaturityToXml(int Energy, int Maturity, Document document, int agentNum){

    }


    public ArrayList<Integer> getEnergyArray(){
        ArrayList<Integer> copy = new ArrayList<>();
        copy.addAll(EnergyArray);
        return copy;
    }

    public ArrayList<Integer> getMaturityArray(){
        ArrayList<Integer> copy = new ArrayList<>();
        copy.addAll(MaturityArray);
        return copy;
    }
}