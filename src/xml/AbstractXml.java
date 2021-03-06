package xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import simulation.Cell;
import simulation.CellState;
import userInterface.AbstractGridView;
import userInterface.CellShapeType;
import userInterface.UserInterface;
import utils.Point;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Contains all the abstract and concrete methods that each simulation requires to be parsed/saved
 * @author  Diane Lin
 */
public abstract class AbstractXml {

    protected static final Logger logger = Logger.getAnonymousLogger();
    private static final String RESOURCE_FILE_PATH = "resources/XmlResources";
    private static final String defaultTitle = "Simulation";
    private static final int SIZE_OF_XML_STRING = 4;
    private List<List<Integer>> myColArray = new ArrayList<>();
    private List<List<Integer>> myRowArray = new ArrayList<>();
    protected List<Integer> percentage;
    private String shape;
    protected int isSaved;
    protected int numAgents;
    private int cellGridColNum;
    private int cellGridRowNum;
    private int rate;
    private String myTitle;
    protected UserInterface myUserInterface;
    protected Document doc;
    protected ResourceBundle resourceBundle;
    protected ArrayList<Integer> EnergyArray = new ArrayList<>();
    protected ArrayList<Integer> MaturityArray = new ArrayList<>();
    protected double evaporation;
    protected double diffusion;
    protected int maxAnts;
    protected int birthRate;


    /**
     * Constructor of the AbstractXml class
     * @param myUserInterface is the object that allows access to the different variables in the cellGrid where all the data needs to be passed to
     */
    public AbstractXml(UserInterface myUserInterface){
        this.myUserInterface = myUserInterface;
        resourceBundle = ResourceBundle.getBundle(RESOURCE_FILE_PATH);
    }

    /**
     * changes the number of rows in the cell grid
     * @param n the number to change the grid row to
     */
    public void changeRowNum(int n){
        this.cellGridRowNum = n;
    }

    /**
     * changes the number of columns in the cell grid
     * @param n the new number of columns in the cell grid
     */
    public void changeColNum(int n){
        this.cellGridColNum = n;
    }


    /**
     * gets the number of columns in the cell grid
     * @return the number of columns in the cell grid
     */
    public int getCellGridColNum(){
        return this.cellGridColNum;
    }

    /**
     * gets the number of rows in the cell grid
     * @return the number of rows in the cell grid
     */
    public int getCellGridRowNum(){
        return this.cellGridRowNum;
    }

    /**
     * gets the name of the current simulation
     * @return the name of the current simulation
     */
    public String getMyTitle(){
        return this.myTitle;
    }

    /**
     * gets the List that contains the column numbers of each respective agent
     * each agent has its own set of column positions in the grid
     * the ith index of myColArray corresponds to the ith agent's col array
     * @return List that contains the column numbers of each respective agent
     */
    public List<List<Integer>> getMyColArray(){
        List<List<Integer>> copy = new ArrayList<>();
        copy.addAll(myColArray);
        return copy;
    }


    /**
     * gets the List that contains the row numbers of each respective agent
     * each agent has its own set of row positions in the grid
     * the ith index of myRowArray corresponds to the ith agent's row array
     * @return List that contains the column numbers of each respective agent
     */
    public List<List<Integer>> getMyRowArray(){
        List<List<Integer>> copy = new ArrayList<>();
        copy.addAll(myRowArray);
        return copy;
    }



    public List<Integer> getPercentage(){
        return this.percentage;
    }
    public int getRate(){
        return this.rate;
    }


    /**
     * calls determineCellShape and setUpSimulationParameters methods to parse the xml file
     * @param file the file to be parsed
     * @throws ParserConfigurationException the xml file is not configured correctly for the parser
     */
    public void parse(String file) throws ParserConfigurationException {
        File xmlFile = new File(String.valueOf(file));
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = documentBuilderFactory.newDocumentBuilder();

        try {
            this.doc = docBuilder.parse(xmlFile);
        } catch (SAXException | IOException e) {
            logger.log(Level.SEVERE, "an exception was thrown", e);
        }
        determineCellShape(this.doc);
        setUpSimulationParameters();
    }




    /**
     * takes all the information from the xml file and passes the data to the correct variables within myUserInterface
     */
    protected void setUpSimulationParameters(){
            this.cellGridColNum = Integer.parseInt(doc.getElementsByTagName("Col").item(0).getTextContent());
            this.myUserInterface.setNumOfCols(this.cellGridColNum);
            this.cellGridRowNum = Integer.parseInt(doc.getElementsByTagName("Row").item(0).getTextContent());
            this.myUserInterface.setNumOfRows(this.cellGridRowNum);
            this.rate = Integer.parseInt(doc.getElementsByTagName("Rate").item(0).getTextContent());
            this.isSaved = Integer.parseInt(doc.getElementsByTagName("Saved").item(0).getTextContent());
            NodeList typeOfSimulation = doc.getElementsByTagName("Type");
            //goes through NodeList to get information about the different simulation
            for (int i = 0; i < typeOfSimulation.getLength(); i++) {
                //gets the i-th simulation type and casts it as a node
                Node currentSimulationType = typeOfSimulation.item(i);
                Element currentSimulationElement = (Element) currentSimulationType;
                try{
                    this.myTitle = currentSimulationElement.getAttribute("name");
                    this.myUserInterface.changeTitle(this.myTitle);
                }
                catch (NullPointerException ex){
                    logger.log(Level.SEVERE, "an exception was thrown", ex);
                    this.myUserInterface.changeTitle(defaultTitle);

                }
                this.numAgents = Integer.parseInt(currentSimulationElement.getTextContent());
            }
            for (int i = 0; i < this.numAgents; i++) {
                NodeList agent = doc.getElementsByTagName("Agent" + i);
                Element n = (Element) agent.item(0);
                String row = n.getElementsByTagName("Row").item(0).getTextContent();
                String col = n.getElementsByTagName("Column").item(0).getTextContent();
                myRowArray.add(stringToIntArray(row));
                myColArray.add(stringToIntArray(col));
            }
    }

    //determines the shell shape for the simulation

    /**
     * determines the shape of the cell to be instantiated
     * @param doc the Document that contains the shape information
     */
    private void determineCellShape(Document doc) {
        Node shapeNode = doc.getElementsByTagName("Shape").item(0);
        Element shapeElement = (Element) shapeNode;
        shape = shapeElement.getAttribute("name");
        switch (shape) {
            case "triangle":
                this.myUserInterface.setCellShape(CellShapeType.TRIANGLE);
                break;
            case "rectangle":
                this.myUserInterface.setCellShape(CellShapeType.RECTANGLE);
                break;
            case "hexagon":
                this.myUserInterface.setCellShape(CellShapeType.HEXAGON);
                break;
        }
    }


    /**
     * stages a new XML that will contain the information of the simulation that was saved
     * creates all the elements needed for the simulation
     * @return Document that was staged with the elements and tags needed for the new xml file
     * @throws ParserConfigurationException is thrown when the elements/tags cannot be created because there is no text element
     * that can be added
     */
    protected Document stageXml() throws ParserConfigurationException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();

        //make root element
        Element root = document.createElement("Simulation");
        document.appendChild(root);

        Element saved = document.createElement("Saved");
        saved.appendChild(document.createTextNode("1"));
        root.appendChild(saved);

        //Shape element
        Element shape = document.createElement("Shape");
        shape.appendChild(document.createTextNode("1"));
        shape.setAttribute("name", this.shape);
        root.appendChild(shape);


        Element type = document.createElement("Type");
        type.setAttribute("name", this.myTitle);
        type.appendChild(document.createTextNode(Integer.toString(numAgents)));
        root.appendChild(type);

        Element Row = document.createElement("Row");
        Row.appendChild(document.createTextNode(Integer.toString(this.cellGridRowNum)));
        root.appendChild(Row);

        Element Col = document.createElement("Col");
        Col.appendChild(document.createTextNode(Integer.toString(this.cellGridColNum)));
        root.appendChild(Col);

        Element Rate = document.createElement("Rate");
        Rate.appendChild(document.createTextNode(Integer.toString(this.rate)));
        root.appendChild(Rate);
        return document;
    }



    /**
     * convers the string retrieved from the xml file to an array of ints to be used as coordinates within the cell grid
     * @param s the string to be converted
     * @return a list of ints
     */
    protected List<Integer> stringToIntArray(String s) {
        String[] sArray = s.split(" ");
        StringBuilder myStringBuilder = new StringBuilder(s);
        int startIndex = 0;
        int endIndex = startIndex;

        List<Integer> myInts = new ArrayList<>();
        for(int i = 0; i < sArray.length; i++){
            if(Integer.parseInt(sArray[i]) > this.cellGridColNum){
                this.myUserInterface.displayErrorMsg(resourceBundle.getString("ErrorMsg_invalidLocation"));
                myInts.add(this.cellGridColNum -1);
            }
            else {
                myInts.add(Integer.parseInt(sArray[i]));
            }
        }
        return myInts;
    }



    /**
     * saves the current cell states for the simulation to be saved
     * @param myMap contains all the current cell states for each respective point within the cell grid
     * @param state1 the first state to be saved
     * @param state2 the second state to be saved
     * @param colArray the array that holds the current column positions for each respective agent
     * @param rowArray the array that holds the current row positions for each respective agent
     */
    protected void saveCellState(Map<Point, Cell> myMap, CellState state1, CellState state2, List<List<Integer>> colArray,
                                 List<List<Integer>> rowArray){
        List<Integer> agent0Col = new ArrayList<>();
        List<Integer> agent0Row = new ArrayList<>();
        List<Integer> agent1Col = new ArrayList<>();
        List<Integer> agent1Row = new ArrayList<>();
        for (Map.Entry<Point, Cell> entry: myMap.entrySet()
        ) {
            Cell c = entry.getValue();
            if(c.getState().equals(state1)){
                agent0Row.add(entry.getKey().getRow());
                agent0Col.add(entry.getKey().getCol());
            }
            else if(c.getState().equals(state2)){
                agent1Row.add(entry.getKey().getRow());
                agent1Col.add(entry.getKey().getCol());
            }
        }

        colArray.add(agent0Col);
        colArray.add(agent1Col);
        rowArray.add(agent0Row);
        rowArray.add(agent1Row);
    }




    /**
     * changes the integer list that is passed in as a parameter to a string
     * @param array List of ints that is to be converted to a string
     * @return a string of all the elements within the list array separated by a space
     */
    private String convertArrayToString(List<Integer> array){
        StringBuilder s = new StringBuilder();
        s.append(array.get(0));
        for(int i = 1; i < array.size(); i++){
            s.append(" ");
            s.append(array.get(i));
        }
        return s.toString();
    }



    /**
     * saves the current cell state for a simulation that requires 2 agents
     * @param myMap map that contains all the current positions of each cell as points and contains the state of each point
     * @param state1 the first state to be saved
     * @param colArray the array that holds the new column position for each agent to be saved
     * @param rowArray the array that holds the new row position for each agent to be saved
     */
    protected void saveCellState(Map<Point, Cell> myMap, CellState state1, List<List<Integer>> colArray, List<List<Integer>> rowArray){
        List<Integer> agent0Col = new ArrayList<>();
        List<Integer> agent0Row = new ArrayList<>();
        for (Map.Entry<Point, Cell> entry: myMap.entrySet()
        ) {
            Cell c = entry.getValue();
            if(c.getState().equals(state1)){
                agent0Row.add(entry.getKey().getRow());
                agent0Col.add(entry.getKey().getCol());
            }
        }
        colArray.add(agent0Col);
        rowArray.add(agent0Row);

    }


    /**
     * creates the agent element for the new xml file to be saved
     * @param agentIdentifier which agent number it is
     * @param document the document that will hold the agent elements
     * @param rowArray the list that holds all the row positions for each agent
     * @param colArray the list that holds all the column positions for each agent
     * @return the agent element that is to be added to the xml file
     */
    private Element createAgentElement(String agentIdentifier, Document document,List<Integer> rowArray, List<Integer> colArray){
        String agent = "Agent" + agentIdentifier;
        Element agentElement = document.createElement(agent);
        Element row = document.createElement("Row");
        row.appendChild(document.createTextNode(convertArrayToString(rowArray)));
        Element col = document.createElement("Column");
        col.appendChild(document.createTextNode(convertArrayToString(colArray)));
        agentElement.appendChild(row);
        agentElement.appendChild(col);
        return agentElement;
    }


    /**
     * adds the agents to the new xml file
     * @param document the document that the agent element is added to
     * @param agentIdentifier identifies which agent was created
     * @param rowArray the list that holds all the current row position for the agent to be added
     * @param colArray the list that holds all the current column positions for the agent to be added
     */
    protected void addAgents(Document document, String agentIdentifier,
                             List<Integer> rowArray, List<Integer> colArray){
        Element agentElement = createAgentElement(agentIdentifier, document, rowArray, colArray);
        addChildToDocument(document, agentElement);
    }

    /**
     * adds the agent element to the document
     * @param document document that the agent element is added to
     * @param agentElement the element to be added to the document
     */
    protected void addChildToDocument(Document document, Element agentElement){
        document.getFirstChild().appendChild(agentElement);
    }

    /**
     * calls the other methods to create the agent elements and adds the agents to the document
     * @param document document that the agent element is added to
     * @param agentIdentifier the agent number to be created
     * @param rowArray the list that holds the row positions of the agent
     * @param colArray the list that holds the column positions of the agent
     * @param maturity the int that determines how many life cycles is needed for an agent to reproduce
     * @param energy the int that determines how many life cycles an agent can go without eating
     */
    protected void addAgents(Document document, String agentIdentifier,
                             List<Integer> rowArray, List<Integer> colArray,
                             int maturity, int energy){
        Element agentElement = createAgentElement(agentIdentifier, document, rowArray, colArray);

        Element ma = document.createElement("Mature");
        Element en = document.createElement("Energy");
        ma.appendChild(document.createTextNode(Integer.toString(maturity)));
        en.appendChild(document.createTextNode(Integer.toString(energy)));
        agentElement.appendChild(ma);
        agentElement.appendChild(en);
        document.getFirstChild().appendChild(agentElement);
    }


    /**
     * creates the xml path for the file to be saved
     * @param document the document that needs to be converted to an xml file
     * @param xmlFilePath the current filePath where the xml files are stored
     * @throws TransformerException is thrown if a new instance cannot be created
     */
    protected void createXmlFilePath(Document document, File xmlFilePath) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StringBuilder file = new StringBuilder(String.valueOf(xmlFilePath));
        String filePath = file.substring(0, file.length() - SIZE_OF_XML_STRING);
        filePath = filePath + "Saved.xml";
        StreamResult streamResult = new StreamResult(new File(filePath));
        transformer.transform(domSource, streamResult);

    }




    /**
     * called when the save button is clicked
     * starts the saving process for the new xml file of a simulation that has 3 agents
     * @param myGridView the variable that holds the map that contains all the cells and states of the current cells
     * @param state1 the first state to be saved
     * @param state2 the second state to be saved
     * @param xmlFilePath the current filepath where the xml file that was initially parsed is located
     * @throws ParserConfigurationException is thrown when the file cannot be parsed
     */
    protected void saveCurrentSimulation(AbstractGridView myGridView, CellState state1,
                                         CellState state2, File xmlFilePath) throws ParserConfigurationException {
        Document document = stageXml();
        Map<Point, Cell> myMap = myGridView.getGridManager().getCellGrid().getGridOfCells();
        List<List<Integer>> colArray = new ArrayList<>();
        List<List<Integer>> rowArray = new ArrayList<>();
        saveCellState(myMap, state1, state2, colArray, rowArray);
        for(int i = 0; i < rowArray.size(); i++){
            addAgents(document, Integer.toString(i), rowArray.get(i), colArray.get(i));
        }
        try {
            createXmlFilePath(document, xmlFilePath);
        } catch (TransformerException e) {
            //myUserInterface.displayErrorMsg(resourceBundle.getString("ErrorMsg_filePath"));
            logger.log(Level.SEVERE, "an exception was thrown", e);
        }
    }

    /**
     * saves the current simulation for a simulation that has 2 agents
     * @param myGridView the variable that holds the map that contains all the cells and states of the current cells
     * @param state1 the first state to be saved
     * @param xmlFilePath the current filepath where the xml file that was initially parsed is located
     * @throws ParserConfigurationException is thrown when the file cannot be parsed
     */
    protected void saveCurrentSimulation(AbstractGridView myGridView, CellState state1,
                                         File xmlFilePath) throws ParserConfigurationException {
        Document document = stageXml();
        Map<Point, Cell> myMap = myGridView.getGridManager().getCellGrid().getGridOfCells();
        List<List<Integer>> colArray = new ArrayList<>();
        List<List<Integer>> rowArray = new ArrayList<>();
        saveCellState(myMap, state1, colArray, rowArray);
        for(int i = 0; i < rowArray.size(); i++){
            addAgents(document, Integer.toString(i), rowArray.get(i), colArray.get(i));
        }
        try {
            createXmlFilePath(document, xmlFilePath);
        } catch (TransformerException e) {
            logger.log(Level.SEVERE, "an exception was thrown", e);
        }
    }

    public List<Integer> getEnergyArray() {
        List<Integer> copy = new ArrayList<>();
        copy.addAll(EnergyArray);
        return copy;
    }

    public List<Integer> getMaturityArray() {
        List<Integer> copy = new ArrayList<>();
        copy.addAll(MaturityArray);
        return copy;
    }

    public abstract void saveCurrentSimulation(AbstractGridView myGridView, File myConfigFile) throws ParserConfigurationException;

    public double getEvaporation(){
        return this.evaporation;

    }

    public double getDiffusion(){
        return this.diffusion;
    }

    public int getMaxAnts(){
        return this.maxAnts;
    }

}
