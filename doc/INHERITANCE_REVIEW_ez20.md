Part 1

What is an implementation decision that your design is encapsulating (i.e., hiding) for other areas of the program?

Our Cell classes don't have information about how the Rules are implemented. This is because we we have a Rule class that has its own private variables that determine the next state of the cell for it.

What inheritance hierarchies are you intending to build within your area and what behavior are they based around?

We want to have a Cell, Grid, Simulation, and Visualizer class. The Cell takes care of going to its next state. The Grid takes care of keeping track of the cell and assigning neighbors. The Simulation class takes care of updating the grid and its cells. The Visualizer class takes care of displaying the images on the screen.

What parts within your area are you trying to make closed and what parts open to take advantage of this polymorphism you are creating?

We are closing the XMLParser class as well as the Interface that we plan to make, called Rules. This Rules interface takes advantage of polymorphism because all of the Cells will contain a specific Rule class that implements this interface.

What exceptions (error cases) might occur in your area and how will you handle them (or not, by throwing)?

Exceptions could include reading the wrong file or getting the wrong tag name from an XML file. I would throw a FileNotFoundException or WrongTagNameException, for example.

Why do you think your design is good (also define what your measure of good is)?
I think the design is good because it has a good layer of abstraction, interface delegation, and encapsulation.

Part 2
How is your area linked to/dependent on other areas of the project?
The Grid is dependent on the cell and its neighbors because the Grid has access to this information
Are these dependencies based on the other class's behavior or implementation?
These dependencies are based on the implementation
How can you minimize these dependencies?
We can minimize these dependencies by hiding as much information as possible within each class (especially the Cell and Rules class)
Go over one pair of super/sub classes in detail to see if there is room for improvement. 
Focus on what things they have in common (these go in the superclass) and what about them varies (these go in the subclass).
The Rules interface will only return a state int that the Cell class will take, and each Rules subclass will implement the specific Rule for each simulation type.


Part 3

Come up with at least five use cases for your part (most likely these will be useful for both teams).

	Read in an XML file detailing. 
	Set up a grid with dimensions labeled within an XML file.
	Create various buttons for starting and stopping the simulation.
	Change from one simulation type to another.
	Present an error message when an incorrectly formatted XML file is provided.


What feature/design problem are you most excited to work on?

	We are most excited to work on the XML file creation and parsing. The flexibility of the XML file will allow us to parse our simulation files in various ways.

What feature/design problem are you most worried about working on?

	We are most worried about working on the UI. It will be difficult to create a UI system that is effective in bridging front end interactions with simulation.

