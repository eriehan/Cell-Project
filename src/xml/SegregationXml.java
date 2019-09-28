package xml;


import simulation.CellState;
import userInterface.AbstractGridView;
import userInterface.UserInterface;


import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.util.List;

public class SegregationXml extends AbstractXml {
    public SegregationXml(UserInterface myUserInterface) {
        super(myUserInterface);
    }


    public void saveCurrentSimulation(AbstractGridView myGridView, File xmlFilePath) throws TransformerException, ParserConfigurationException {
        super.saveCurrentSimulation(myGridView, CellState.FIRSTAGENT, CellState.SECONDAGENT, xmlFilePath);
    }


}
