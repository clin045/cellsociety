cell society
====

This project implements a cellular automata simulator.

Names:
Allen Qiu asq3
Christopher Lin cl349
Scott McConnell skm44

### Timeline

Start Date: 09.07.18

Finish Date: 10.01.18

Hours Spent: 90 total

### Primary Roles
Christopher: Model/Simulation
Allen: UI
Scott: XML/Config


### Resources Used
http://necsi.org/postdocs/sayama/sdsr/java/loops.java

### Running the Program

Main class: UI.UIManager

Data files needed: Fire.xml, ForagingAnts.xml, GameOfLife.xml, PredatorPrey.xml, RPS.xml, Segregation.xml, Langton.xml

Interesting data files: ForagingAnts.xml (play at 2x speed), RPS.xml

Features implemented:
- Differing shapes for square neighborhoods
- Square and triangular grids
- Finite and toroidal edges
- Simulations
    - Fire
    - Foraging Ants
    - Game of Life
    - Predator Prey (wator world)
    - Rock Paper Scissors (bacterial growth)
    - Segregation
    - Langton's Loop
- Config file error checking
- Specific and completely random initial conditions
- Configurable cell colors and outline 
- Exceptions handled:
    - Empty string passed in (returns “0”)
    - Invalid or no simulation type given (throws exception)
    - Invalid cell states given (throws exception)
    - Cell locations given that are outside the bounds of the grid’s size (throws exception)
    - No initial configurations given (randomizes based on a total number of locations to occupy)
    - Negative value rows/columns integers (gets absolute valued)
    - Zero value rows/columns integers (throws exception)
    - Too many / not enough colors given (throws exception)
    - Invalid shape type string given (defaults to square)
    - Odd rows/columns parameter when triangle is chosen (throws exception)
    - Invalid edge type string given (defaults to finite)
    - Invalid gridlines integer given (default hides gridlines)
    - Null/Empty Title, Author, Description strings even (default shows “No Title” / “No Author” / “No Description”)
- Cell population graph
- Dynamic simulation parameter changing
- Interaction with cells through the UI
- Multiple windows
    - Graph only works for last graph opened

Assumptions or Simplifications:
- RPS uses simplified methods for autoinducer decay and release
- Foraging ants does not care about ant orientation
- Langton's loop is always run on a square grid

Known Bugs:
- Triangle grid does not allow selection of neighbors

Extra credit:


### Notes

- For multiple windows, the graph only displays for the last window opened. Additionally, a previously implemented reset button no longer works and was commented out.
    - We chose to create new windows because we wanted to give the users the flexibility to arrange the simulations as they saw fit.
    - They do not need to be the same simulation type because we

### Impressions

Many of the assumptions that were made during design week turned out to be untrue. 

