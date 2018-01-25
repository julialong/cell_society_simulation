# DESIGN EXERCISE

Completed by JEFFREY LI, JULIA LONG, AND EDWARD ZHUANG

## Design Description

We decided on making three distinct classes. We will make a CellController class, a Cell class, and a State class.
The CellController class will be resonsible for controlling all the Cells (it will hold a list of all the cells). It will also
contain methods that will control the states of each cell after each iteration. For example, a CellController may have a method that checks the neighbors of each cell
and uses this information to generate the next iteration of cell states (by first storing this information in a list). 
There will be CellController subclasses, and each subclass will be responsible for
creating different types of simulations. The Cell class will hold it's neighbor cells (possibly in an arraylist or other structure), 
and a state object. The State class will have a string indicating what state it is. We are still
thinking of other things to add to the State class.

## Iterations
To generate the next iteration of cell states, the CellController will have an list containing the next set of cell states. This way, all 
the cell states can be updated simultaneously.  

