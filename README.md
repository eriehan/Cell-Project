simulation
====

This project implements a cellular automata simulator.

Names: Eric Han, Diane Lin, Junyu Liang

### Timeline

Start Date: 9/12/2019

Finish Date: 9/22/2019

Hours Spent:

### Primary Roles
Simulation (backend): Eric Han
* Implement a 2D grid of cells that is used to represent the simulation.
* Implement a simulation where the state information of each cell is updated each step based on rules applied to their state and those of their neighbors.
* Implement Java code for the rules of five different simulations: game of life (for testing), segregation, predator-prey, fire, and percolation 

Visualization: Junyu Liang
* Display the current states of the 2D grid and animate the simulation from its initial state until the user stops it.
* Allow users to load a new configuration file, which stops the current simulation and starts the new one.
* Allow users to pause and resume the simulation, as well as step forward through it.
* Allow users to speed up or slow down the simulation's animation rate.

Configuration: Diane Lin
* Read in an XML formatted file that contains the initial settings for a simulation. The file contains three parts:
    * kind of simulation it represents, as well as a title for this simulation and the author of this data file
    * settings for global configuration parameters specific to the simulation
    * width and height of the grid and the initial configuration of the states for the cells in the grid

### Resources Used
https://www2.cs.duke.edu/courses/compsci308/fall19/assign/02_simulation/


### Running the Program

Main class: MainController

Data files needed: 

Interesting data files:

Features implemented: 
* create a simulation cell grid that updates each step based on the previous state
* implemented the five different CA simulations: game of life, segregation, predator-prey, fire, and percolation.
* simulation initial configuration is contained in XML files, and is parsed in by the program to initialize the simulation. The configuration includes:
    * type of simulation
    * shape of the grid cells (currently support rectangle and triangle)
    * dimensions of the simulation grid (number of rows and columns)
    * initial grid setup
* friendly user interface for user to start and control simulation progress
    * grid display
        * display the grid status at each state, the size of each individual cell is determined by xml file's number of row and columns, while the overall size of the grid stays constant for every simulation.
        * 
    * buttons
        * select file: select configuration file that sets up the simulation (if the user choose a new simulation)
        * start: start simulation
        * pause / resume: pause and resume the simulation
        * reset: reset the grid back to blank grid
        * step: step through the simulation step by step
        * speed up & slow down: change simulation animation rate
    * error messages to guide users
    
Assumptions or Simplifications:

Known Bugs:

Extra credit:
* triangular grid cells

### Notes


### Impressions

