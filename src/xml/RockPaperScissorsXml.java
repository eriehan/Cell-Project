package xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import simulation.Cell;
import simulation.CellState;
import userInterface.AbstractGridView;
import userInterface.UserInterface;
import utils.Point;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RockPaperScissorsXml extends AbstractXml {


    public RockPaperScissorsXml(UserInterface myUserInferface){
        super(myUserInferface);
        percentage = new ArrayList<Integer>();

    }



    public void setUpSimulationParameters() {
        super.setUpSimulationParameters();

      //  System.out.println("entered wator");

        for (int i = 0; i < this.numAgents; i++) {
            NodeList agent = doc.getElementsByTagName("Agent" + i);
            Element n = (Element) agent.item(0);
            int percent = Integer.parseInt(n.getElementsByTagName("Percent").item(0).getTextContent());
            percentage.add(percent);
        }
        System.out.println(percentage);

    }

    @Override
    public List<Integer> getMaturityArray() {
        return null;
    }

    @Override
    public List<Integer> getEnergyArray() {
        return null;
    }

    public void saveCurrentSimulation(AbstractGridView myGridView, File xmlFilePath) throws ParserConfigurationException, TransformerException {
        //super.saveCurrentSimulation(myGridView, xmlFilePath);


    }


}
