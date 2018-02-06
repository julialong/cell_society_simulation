# Inheritance Review
jbl34, dpb20, hy115

## Part One
1. One of the people in our group hides the individual cells from the "Menu" class and handles all of that data within their controller class. I do not personally feel like I encapsulate anything within my controller class, and neither does the other group member.
2. One of the group members decided to make their controller class a superclass and implement each set of rules within the subclasses, with a subclass for each game. This is similar to what our group decided to do. 
3. My CellController class decides the next state of each cell, and this logic is closed to the other classes and simply tells the cell what its next state will be. The other group members have similar methods, but they are open, so the Cell class has to ask what its next state will be.
4. We will have to handle cases such as cells on the edge or on the corner of the grid. We'll have to work together with the Cell classes to see how they handle these cases, and may have to catch exceptions if they are thrown.
5. We feel that having subclasses for different types of simulations is good design because it allows us expand and add more simulations if we want to do that. "Good code" is readable and easy to expand on, which is why we feel that this design choice constitutes good design.

## Part Two
1. We work with the cell often, deciding on the next state of the cell and then updating each cell, as well as creating an initial grid of cells. We also tell the user interface how the simulation should look at each step, except for one of our group members, who said his controller class adds the grid directly to a Group class and then the main class just has to display that Group. 
2. No, our dependencies do not depend on a specific implementation of these other classes. We feel like this is a specific example of bad design.
3. We can reduce dependencies by encapsulating as much information as we can into other classes, so that we don't have to access much specific information from each class and instead can pull only a few results as needed.
4. We implement different subclasses for different simulations, and change the method for updating cell states based on the rules for each simulation.

## Part Three
1. Use cases:
	* Initialize cells
	* Decide on next state for cells
	* Update cells 
	* Adjust cells to show previous states 
	* Step simulation
2. One of our group members is working on the simulation, and he is excited about that. Another member is excited to get his cells to go back and forth. I'm excited to try my hand at abstract classes and implementing them.