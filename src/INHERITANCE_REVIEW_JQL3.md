# Part 1


## What is an implementation decision that your design is encapsulating (i.e., hiding) for other areas of the program?

The cells only know who they are beside and what state they have, and don't know the rules, full grid etc. The Simulator just responds by simulating what the what the Cell Controller passes it and doesn't know any of the information such as rules, individual cell states, next states etc. The CellController is the only class that knows the rules.

## What inheritance hierarchies are you intending to build within your area and what behavior are they based around?

The Cell has subclasses that are different shapes and have different numbers of neighbours. The states are different per game to have the specific different possible states incapsulated eg fire, tree, empty.

## What parts within your area are you trying to make closed and what parts open to take advantage of this polymorphism you are creating?

The design is open to adding simulations. New simulations can be added by implementing new rules in the cell Controller class and new types of cells and state subclasses.

## What exceptions (error cases) might occur in your area and how will you handle them (or not, by throwing)?

Some examples would be if incorrect arguments were passed into the State methods that weren't valid to the game, you would throw a WrongGameException which is a InvalidArgumentException.

## Why do you think your design is good (also define what your measure of good is)?

It's easy to add a new simulation.


# Part 2

## How is your area linked to/dependent on other areas of the project?
The cells and states pass information to and react to the cell controller. The cell controller tells the simulator what needs to be shown. 

## Are these dependencies based on the other class's behavior or implementation?

Behaviour. It doesn't matter how the classes do what they do, as long as they return the right thing (black box).

## How can you minimize these dependencies?
They should be giving information and receiving information but not calling methods of other classes. Classes should fit the black box model.

## Go over one pair of super/sub classes in detail to see if there is room for improvement. Focus on what things they have in common (these go in the superclass) and what about them varies (these go in the subclass).



# Part 3

## Come up with at least five use cases for your part (most likely these will be useful for both teams).

1) State of cell needs to be changes
2) Cell needs to move to another location
3) Simulation type change

## What feature/design problem are you most excited to work on?

How to react to new shapes of cells.

## What feature/design problem are you most worried about working on?

Adapting to new simulation rules.