package xml;

import simulation.CellState;
import userInterface.AbstractGridView;
import userInterface.UserInterface;


import javax.xml.parsers.ParserConfigurationException;
import java.io.File;

/**
 * @author  diane lin
 * parses and saves the game of life simulations xml files
 */
public class GameOfLifeXml extends AbstractXml {
    public GameOfLifeXml(UserInterface myUserInterface) {
        super(myUserInterface);
    }


/**
 * saves the current simulation for the game of life simulation
 */
    public void saveCurrentSimulation(AbstractGridView myGridView, File xmlFilePath) throws ParserConfigurationException {
        super.saveCurrentSimulation(myGridView, CellState.ALIVE, xmlFilePath);
    }

}
