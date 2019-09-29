package xml;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import userInterface.AbstractGridView;
import userInterface.UserInterface;
import java.io.File;

public class AntForagingXml extends AbstractXml {
    private static final double INT_TO_PERCENT = 10.0;


    public AntForagingXml(UserInterface myUserInterface) {
        super(myUserInterface);


    }


    protected void setUpSimulationParameters(){
        super.setUpSimulationParameters();
        double tempDiff = Double.parseDouble(doc.getElementsByTagName("Diffusion").item(0).getTextContent());
        double tempEvap = Double.parseDouble(doc.getElementsByTagName("Evaporation").item(0).getTextContent());
        this.diffusion = tempDiff/INT_TO_PERCENT;
        this.evaporation = tempEvap/INT_TO_PERCENT;
        NodeList agent = doc.getElementsByTagName("Agent0");
        Element n = (Element)agent.item(0);
        this.maxAnts = Integer.parseInt(n.getElementsByTagName("MaxAnts").item(0).getTextContent());
        this.birthRate = Integer.parseInt(n.getElementsByTagName("Birthrate").item(0).getTextContent());
    }

    @Override
    public void saveCurrentSimulation(AbstractGridView myGridView, File myConfigFile)  {
        this.myUserInterface.displayErrorMsg(resourceBundle.getString("ErrorMsg_savingFile"));

    }


}
