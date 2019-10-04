package xml;

import userInterface.AbstractGridView;
import userInterface.UserInterface;
import java.io.File;


/**
 * @author  diane lin
 * parses and saves the xml files related to the AntForaging simulation
 */
public class AntForagingXml extends AbstractXml {


    /**
     * constructor
     * passes in the myUserInterface in order to modify the variables in the cell grid
     * @param myUserInterface
     */
    public AntForagingXml(UserInterface myUserInterface) {
        super(myUserInterface);


    }




    /**
     * sets up the ant foraging simulation
     */
    protected void setUpSimulationParameters(){
        super.setUpSimulationParameters();
        this.diffusion = Double.parseDouble(doc.getElementsByTagName("Diffusion").item(0).getTextContent());
        this.evaporation = Double.parseDouble(doc.getElementsByTagName("Evaporation").item(0).getTextContent());
        this.maxAnts = Integer.parseInt(doc.getElementsByTagName("MaxAnts").item(0).getTextContent());
        this.birthRate = Integer.parseInt(doc.getElementsByTagName("Birthrate").item(0).getTextContent());
    }

    @Override
    public void saveCurrentSimulation(AbstractGridView myGridView, File myConfigFile)  {
        this.myUserInterface.displayErrorMsg(resourceBundle.getString("ErrorMsg_savingFile"));

    }
}
