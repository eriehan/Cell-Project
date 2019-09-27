package xml;

import simulation.CellState;
import userInterface.AbstractGridView;
import userInterface.UserInterface;


import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.util.List;


public class FireXml extends AbstractXml {
    public FireXml(UserInterface myUserInterface) {
        super(myUserInterface);
    }


    public void saveCurrentSimulation(AbstractGridView myGridView, File xmlFilePath) throws ParserConfigurationException, TransformerException {
        super.saveCurrentSimulation(myGridView, CellState.BURNING, CellState.FIREEMPTY, xmlFilePath);

    }

    @Override
    public List<Integer> getMaturityArray() {
        return null;
    }

    @Override
    public List<Integer> getEnergyArray() {
        return null;
    }
}
