Introduction
==================

For this project, we would like to implement a program that is flexible enough to simulate any type of celluar automata (CA), but also customizable by the user in terms of grid size, simulation type, and several other animation properties. The program should be able to read in these properties from an XML file provided by the user. The user interace is encapsulated from the actual grid, which is represented by a matrix of Cell classes. Only the CellManager class can modify the cells, which provides further encapsulation and modularity. It is easy to create different simulations by implementing the Rule interface. 

Overview
==================
* XML Parser
    * Class to determine the characteristics of the simulation chosen by user
* UI
    * Method to display grid
    * Methods for start/stop/reset/step buttons and choose XML file option
* Backend
    * Cell class
        * Cell grid will be structured as a 2D ArrayList<Integer>
    * CellManager
        * Responsible for editing cell states and the grid
    * Rule Interface
        * Extendable to create different rulesets for different simulations

![Design workflow](https://i.imgur.com/N350CrF.jpg)

User Interface
==================
* Functions
    * Load XML file
    * Play, stop, pause, step frame buttons
* Errors messages
    * No input file, 
    * Input file is not complete or valid
* Input file
    * Colors for states
    * Initial conditions
    * Author, title
    * Simulation type

![UI sketch](https://i.imgur.com/lbxURei.jpg)

Design Details
==================
* Cell
    * The Cell class tracks the cell's grid position, currentState and nextState. By tracking both states, the rules can be applied "simultaneously" in a first pass and the currentState can be applied in a second. 
    * Methods/Functions
        * getNeighbors(int neighborhoodSize)
    * Interactions with other components
        * CellManager
            * Modifies Cell's states
            * Passes cell to Rule
        * Rule
            * Passed to applyRule() in order to generate a nextState
* CellManager
    * CellManager is responsible for initializing the grid and modifying cells on it.  This design ensures that there is only one class that has the access to modify cells. CellManager  contains the grid of cells. 
    * Methods/Functions
        * Initial setup method (grid size, etc.)
        * Apply rules to generate nextState for all cells (1st pass)
            * Pass a cell to Rule class
            * Modify current cell's nextState to output of applyRule()
        * Update cells’ currentState to nextState (2nd pass)
    * Interactions with other components
        * Rule
            * Initializes Rule instances
            * Passes cells to Rule
        * Cell
            * modifies states
        * UImanager
            * Handle play, pause, step, stop, reset
    * Error handling:
        * Detect incompatible neighborhood/grid sizes
* Rule interface
    * The Rule interface can be extended to create different simulations. Implementing rules as an interface provides a flexible system for implementing different kinds of simulations. Rule does not edit cells, it only generates the next state; editing cells is the sole responsibility of CellManager.
    * Methods/Functions
        * applyRule() generates nextState
    * Interactions with other components:
        * CellManager
            * CellManager initializes Rule instances 
            * Passes Cells to applyRule()
        * Cell
            * Passes list of neighbors to Rule
    * Extendable by subclasses implementing applyRule()
* UImanager
    * UImanager is responsible for maintaining parity between the abstract grid of cells and the graphical display of the grid. It also provides an interface to load XML files and control CellManager. By having a seperate class to manage the interface, the grid and cells can be completely abstract. 
    * Methods/Functions
        * Initial setup UI (when you load file)
        * Go through and update cell displays
    * Interactions with other components:
        * CellManager
            * pause/play/next step
            * Change rule class to switch simulation type
        * XMLparser
            * Choose file to be parsed
* XMLparser
    * The XML parser reads data from the user provided file and passes it to CellManager, which then initializes the grid. 
    * Methods/Functions
        * Read XML file
    * Interactions with other components
        * CellManager
            * Passes initial conditions
        * UIManager
            * Passes state colors
    * Error Handling:
        * Check that all parameters are present
        * Check that initial conditions are valid (no overlapping cells, all cells in grid bounds)


Design Considerations
==================

* Edges constant vs. edges wrap vs. edges have different rules
    * We decided that edges will *remain constant*
    * Pro: Easier implementation
    * Con: Less complete view because corners will be unchanging

* Cell[][] vs. 2D Arraylist<Cell>
    * We decided to use 2D Arraylist in case size needs to be altered
    * Pros: Flexibility in size, easier to use interface
    * Con: Worse performance, worse use of memory

Use Cases
---
* Apply rules to middle cells
    1. CellManager passes cell to instance of Rule
    2. Rule invokes getNeighbors from cell
    3. Rule generates nextState based on currentStates of neighbors and passes it to CellManager
    4. CellManager sets cell's nextState
* Apply rules to edge cell
    1. Ignore cell. State remains constant.
* Move to next generation
    1. CellManager sets currentState to nextState for all cells
* Set a simulation parameter
    1. User provides XML file with the parameter
    2. Parameter is decoded by XMLparser
    3. XMLparser sends all the rules to CellManager to create the rules
* Switch simulations
    1. Instantiate new Rule
    2. CellManager passes current cell and all subsequent cells to new Rule
    
    
Team Responsibilities
==================
* Scott and Christopher on XML/backend, Allen on frontend
