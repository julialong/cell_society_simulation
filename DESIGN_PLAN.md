# Design Plan

Cell Society [assignment](https://www2.cs.duke.edu/courses/compsci308/current/assign/02_cellsociety/index.php)

Completed by Julia Long, Jeffrey Li, and Edward Zhuang

### Introduction

The goal is to create a 2D simulation of cells arranged in a grid. These cells have a finite number of states that change  in reaction to their adjacent cells. Sets of rules dictating responses can be imposed onto these cells and they react accordingly with time. The simulation can have different rules of controlling cell reaction and different grid sizes as dictated by the user. How cells react and the type of cells is open for extension; how our super-classes act will be closed.

### Overview 

In our program, we plan to make several classes, including a Simulation, Cell Controller, Cell, and State class. The Simulation class will be responsible for the overall user interface and structure, and it will have methods that set up specific simulations. We will have a Cell Controller superclass for each set of rules. The Cell Controller class will extract information from the cells, apply rules, and inform the cells of what state to change to next. We will have Cell Controller subclasses for each set of rules. The Cell Controller will be responsible for all the cells in the simulation; it will store the states of all the cells and then update them on each iteration. The cell class will contain information about its current state and its adjacent cells. It will pass this information to the Cell Controller, and receive information from the Cell Controller about which state to change to next. The state class will hold a color value, which will represent various states (on, off, etc.). The colour will likely be represented by an imageView.

### User Interface

The user interface will feature a main display where the user can see the starting grid. On this screen, the user can choose which simulation they would like to run, potentially by a drop down menu.. The user will also be able to choose the grid size of the simulation. There will be a “play” button that will start the simulation, a “pause” button that will allow the user to pause the simulation, a “skip” button that will move the simulation forward a specified amount of time, and a “stop” button that will stop the simulation and reset to the beginning. In addition, there will be labels that present statistics about the current simulation (ex. How many cells are on). When the simulation is finished, the programme will restart and the user can choose another simulation.

### Design Details

Our design contains the classes: Simulator, CellController (Super class), Cell (Super Class) and State.


The Simulator class deals with the user interface and user interaction. It will contain the user interaction flow, read in the appropriate files, guide the user to choose a simulation type,  and call the appropriate methods that set up the other classes. We needed this class in order to keep the user interface distinct from the back end programming.


The Cell Controller super class will include the rules of the simulation and keep track of the cells in the simulation in an array. The class will loop over the cells, asking the cells to send information regarding their neighbours, and then use the rules to work out the correct next state. The next states will be stored in a parallel array. When all the next states are determined, the Cell Controller class will loop over the cells again and tell them to change to the next state. Subclasses will contain different rules for different simulations. If a new type of simulation needs to be added, a new subclass can be added.


The Cell super class is simply the cells themselves. They will have instance variables (top, bottom, left, right) which keep track of the neighbours. The cells pass information to the Cell Controller regarding their current state and the current state of their neighbours. Then the cells receive information back from the Cell Controller about what state to change to next, and then apply this change. The subclasses we have thought of so far are EdgeCell, CornerCell and MiddleCell which mainly differ in the number of neighbours. If the user wants to change the shapes of the cells, they can add new Cell subclasses.


The State class is an instance variable of the Cell class. It will contain an ImageView and String in order to determine the current state. It is changed by the Cell Controller through the Cell.


### Design Considerations

### Team Responsibilities

