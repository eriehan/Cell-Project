package xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import simulation.Cell;
import simulation.CellState;
import userInterface.AbstractGridView;
import userInterface.UserInterface;
import utils.Point;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
