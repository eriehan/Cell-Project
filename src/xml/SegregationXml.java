package xml;


import simulation.CellState;
import userInterface.AbstractGridView;
import userInterface.UserInterface;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;

/**
 * @author  diane lin
 * parses and saves the segregation xml files
 */
public class SegregationXml extends AbstractXml {
    public SegregationXml(UserInterface myUserInterface) {
        super(myUserInterface);
    }

    /**
     * saves the current simulation
     * @param myGridView holds the current cell states
     * @param xmlFilePath path where all the xml files are being saved
     * @throws ParserConfigurationException is thrown when the simulation cannot be saved
     */
    public void saveCurrentSimulation(AbstractGridView myGridView, File xmlFilePath) throws ParserConfigurationException {
        super.saveCurrentSimulation(myGridView, CellState.FIRSTAGENT, CellState.SECONDAGENT, xmlFilePath);
    }


}
