package xml;

import simulation.CellState;
import userInterface.AbstractGridView;
import userInterface.UserInterface;


import javax.xml.parsers.ParserConfigurationException;
import java.io.File;

/**
 * @author  diane lin
 * responsible for parsing and saving the percolation xml files
 */
public class PercolationXml extends AbstractXml {
    public PercolationXml(UserInterface myUserInterface) {
        super(myUserInterface);
    }

    /**
     * saves the current percolation simulation and states
     * @param myGridView contains the current cell states and cell coordinates of each cell
     * @param xmlFilePath the file path that currently contains all the xml files
     * @throws ParserConfigurationException is thrown when the file cannot be saved
     */
    public void saveCurrentSimulation(AbstractGridView myGridView, File xmlFilePath) throws ParserConfigurationException {
        super.saveCurrentSimulation(myGridView, CellState.PERCOLATED, CellState.BLOCKED, xmlFilePath);
    }


}
