package xml;

import simulation.CellState;
import userInterface.AbstractGridView;
import userInterface.UserInterface;


import javax.xml.parsers.ParserConfigurationException;
import java.io.File;

/**
 * @author  diane lin
 * parses and saves xml files related to the fire simulation
 */
public class FireXml extends AbstractXml {
    public FireXml(UserInterface myUserInterface) {
        super(myUserInterface);
    }


    /**
     * saves the current state of the fire simulation
     * @param myGridView contains the cell grid where are the cells and their states are displayed
     * @param xmlFilePath the file path where the current xml files are saved
     * @throws ParserConfigurationException is thrown when the xml file cannot be saved
     */
    public void saveCurrentSimulation(AbstractGridView myGridView, File xmlFilePath) throws ParserConfigurationException {
        super.saveCurrentSimulation(myGridView, CellState.BURNING, CellState.FIREEMPTY, xmlFilePath);

    }


}
