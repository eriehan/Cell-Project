package xml;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import userInterface.AbstractGridView;
import userInterface.UserInterface;
import java.io.File;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * @author  diane lin
 * parses and saves the rock paper scissors xml files
 */
public class RockPaperScissorsXml extends AbstractXml {
    private static final String RESOURCE_FILE_PATH = "resources/MainResources";
    private ResourceBundle resourceBundle;


    /**
     * constructor
     * @param myUserInterface is passed into the object in order to modify the variables of the cell grid
     */
    public RockPaperScissorsXml(UserInterface myUserInterface){
        super(myUserInterface);
        resourceBundle = ResourceBundle.getBundle(RESOURCE_FILE_PATH);
        percentage = new ArrayList<>();
    }


    //sets up simulation parameters

    /**
     * sets up the simulation for rock paper scissors
     */
    public void setUpSimulationParameters() {
        super.setUpSimulationParameters();
        for (int i = 0; i < this.numAgents; i++) {
            NodeList agent = doc.getElementsByTagName("Agent" + i);
            Element n = (Element) agent.item(0);
            int percent = Integer.parseInt(n.getElementsByTagName("Percent").item(0).getTextContent());
            percentage.add(percent);
        }

    }

    /**
     * saves the current simulation for rock paper scissors
     * @param myGridView holds the current simulation and cell states
     * @param xmlFilePath file path where all the xml files are saved
     */
    public void saveCurrentSimulation(AbstractGridView myGridView, File xmlFilePath) {

        this.myUserInterface.displayErrorMsg(resourceBundle.getString("ErrorMsg_savingFile"));



    }


}
