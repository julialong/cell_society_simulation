

Design
=======
## High Level Design Goals

We separated this project into three parts: Visualization, Configuration, and Simulation. Our visualization component, implemented by Julia, turned an array that contained data about cells into an actual visual interface that a user could watch and interact with. The configuration component, implemented by Edward, dealt with the challenge of both reading in an XML file to provide information to set up a simulation as well as writing an XML file based on the current state of the simulation. The simulation component, implemented by Jeffrey, implemented the rules of various types of simulations, and altered an array of cells to match the simulation rules step after step. The Visualization component managed the user interface and also worked as a middleman to pass the data in between the Configuration part and the Simulation part (specifically, the ParserXML class and the CellController classes). The Visualization component worked directly with both other components, but the Configuration and Simulation components only interacted with the Visualization component and not each other directly. 
We worked on making the Simulation component as easy to extend as possible. As more simulations were implemented, this became easier to manage and extend in  a way that is not intrusive to the other components and classes.

## New Features
To add a new simulation, a user would have to extend the CellController class and override the setUpRandom(), setUpSpecific(), and setNextState() methods so that they worked correctly with the actual rules of that simulation. They would also have to create an XML file that has  a new name for the type of simulation and add an if statement to check for that in the setupCellController() method in the Simulator class, or else the method would throw an InvalidArgumentException and the user interface would display an error. 
To add a new feature within an XML file, a user would have to modify at least the ParserXML class so the new tag for the feature is being looked for and returned to the Simulator class. The Simulator class would also have to be modified to request this new component, and the Cell Controller class would have to be modified to take in a parameter that allowed this component to be passed. 

## Major Design Choices

## Assumptions & Decisions Made