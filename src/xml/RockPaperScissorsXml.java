package xml;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import userInterface.AbstractGridView;
import userInterface.UserInterface;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.util.ArrayList;


public class RockPaperScissorsXml extends AbstractXml {


    public RockPaperScissorsXml(UserInterface myUserInferface){
        super(myUserInferface);
        percentage = new ArrayList<>();

    }



    public void setUpSimulationParameters() {
        super.setUpSimulationParameters();

        for (int i = 0; i < this.numAgents; i++) {
            NodeList agent = doc.getElementsByTagName("Agent" + i);
            Element n = (Element) agent.item(0);
            int percent = Integer.parseInt(n.getElementsByTagName("Percent").item(0).getTextContent());
            percentage.add(percent);
        }
        System.out.println(percentage);

    }


    public void saveCurrentSimulation(AbstractGridView myGridView, File xmlFilePath) throws ParserConfigurationException, TransformerException {



    }


}
