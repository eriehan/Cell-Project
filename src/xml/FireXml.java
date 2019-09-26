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

public class FireXml extends AbstractXml{
    public FireXml(UserInterface myUserInterface){
        super(myUserInterface);
    }



    @Override
    public void saveCurrentSimulation(AbstractGridView myGridView, File xmlFilePath) throws ParserConfigurationException, TransformerException {
        Document document = stageXml();
        System.out.println("Entered");
        Map<Point, Cell> myMap = myGridView.getMyCellGrid().getGridOfCells();
        List<ArrayList<Integer>> colArray = new ArrayList<>();
        List<ArrayList<Integer>> rowArray = new ArrayList<>();
        saveCellState(myMap, CellState.BURNING, CellState.FIREEMPTY);
        colArray.add(agent0Col);
        colArray.add(agent1Col);
        rowArray.add(agent0Row);
        rowArray.add(agent1Row);
        addAgents(document, "0", rowArray.get(0), colArray.get(0));
        addAgents(document, "1", rowArray.get(1), colArray.get(1));
        createXmlFilePath(document, xmlFilePath);

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
