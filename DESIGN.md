Design
=======
## High Level Design Goals

We separated this project into three parts: Visualization, Configuration, and Simulation. Our visualization component, implemented by Julia, turned an array that contained data about cells into an actual visual interface that a user could watch and interact with. The configuration component, implemented by Edward, dealt with the challenge of both reading in an XML file to provide information to set up a simulation as well as writing an XML file based on the current state of the simulation. The simulation component, implemented by Jeffrey, implemented the rules of various types of simulations, and altered an array of cells to match the simulation rules step after step. The Visualization component managed the user interface and also worked as a middleman to pass the data in between the Configuration part and the Simulation part (specifically, the ParserXML class and the CellController classes). The Visualization component worked directly with both other components, but the Configuration and Simulation components only interacted with the Visualization component and not each other directly. 
We worked on making the Simulation component as easy to extend as possible. As more simulations were implemented, this became easier to manage and extend in  a way that is not intrusive to the other components and classes.

## New Features
To add a new simulation, a user would have to extend the CellController class and override the setUpRandom(), setUpSpecific(), and setNextState() methods so that they worked correctly with the actual rules of that simulation. They would also have to create an XML file that has  a new name for the type of simulation and add an if statement to check for that in the setupCellController() method in the Simulator class, or else the method would throw an InvalidArgumentException and the user interface would display an error. 
To add a new feature within an XML file, a user would have to modify at least the ParserXML class so the new tag for the feature is being looked for and returned to the Simulator class. The Simulator class would also have to be modified to request this new component, and the Cell Controller class would have to be modified to take in a parameter that allowed this component to be passed. 

## Major Design Choices

There were several major design choices we had to make. When beginning this project, our team met to discuss
several design choices which would serve as a foundation for how we modeled our simulations. 
After some team meetings, we quickly came to the consensus that we wanted to have a Cell class,
and a CellController class to maintain all the Cells. However, we were unsure if we wanted to include a tertiary State class, 
which would be contained within a Cell and hold information about the Cell's state. 
While we thought this would be a good way to practice effective design encapsulation, 
ultimately we decided to remove the State class from our hierarchy. We were unable to find an effective way
to make the State class meaningful; in many of the simulations, such as Game of Life and Spreading of Fire,
the State object would only hold information about a Color and String state type. 
This made it no more useful than two instance variables within the Cell. 
However, a case could be made for creating State classes for more complex models.
In simulations like Wa-Tor, the State class could contain information about what animal was in the Cell along with its variables.
For the majority of the simulation types, though, the State class was superfluous, 
and as a result we decided to omit it to make the code more uniform and easy to understand.
 Another design decision we spent a significant amount of time on was how we wanted the CellController 
 and Cell to interact. We were unsure as to whether or not we wanted the CellController to apply the rules
 of the simulation onto the Cell, or simply have the CellController tell the Cell to update itself. 
 In our considerations, we used ease of implementation as our primary metric to make this choice.
 By allowing the CellController to apply its specific rules onto the Cell, the Cell would not need to hold the rules within it, 
 making there less of a need for Cell subclasses. In our implementations of Segregation and Spreading of Fire, there
 are no Cells for these simulations. Instead of making unique Cells, only the basic Cell object is used. 

## Assumptions & Decisions Made

Within our project, we made the assumption that all cells would have a specific number of neighbors based on its shape. We also assumed
that the user would always want a square shaped grid for the simulation. This was done to simplify our methods for checking the neighbors of cells.
Another assumption we made was that the user would only want structured simulations or random simulations, as described within an XML file.
However, if a user wanted to create a half random, half structured simulation, they would be unable to do so. 
One critical decision we made was to keep the GUI relatively consistent. Within all simulations, there is a graph display at the bottom
of the window and a dimension slider right above it. We wanted to make a uniform GUI display for all simulations for convenience purposes.
We considered incorporating unique sliders for each simulation, along with the option to manipulate on screen cells, but due to time constraints 
and complexity, we decided against this idea.
