package xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import userInterface.AbstractGridView;
import userInterface.UserInterface;

import java.io.File;
import java.util.ArrayList;

public class GameOfLifeXml extends AbstractXml {
    public GameOfLifeXml(UserInterface myUserInterface){
        super(myUserInterface);
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
            String row = n.getElementsByTagName("Row").item(0).getTextContent();
            String col = n.getElementsByTagName("Column").item(0).getTextContent();
            myRowArray.add(stringToIntArray(row));
            myColArray.add(stringToIntArray(col));
        }
    }

    @Override
    public void saveCurrentSimulation(AbstractGridView myGridView, File xmlFilePath) {

    }
}
