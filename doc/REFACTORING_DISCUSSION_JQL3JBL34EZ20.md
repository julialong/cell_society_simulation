# Changes Made

The first change we decided to make was to remove string literals within our WatorController class and replace them with string variables. This was done to reduce the amount of duplicate strings and to better organize method inputs.

We repeated this process for integer inputs as well, in both this class and the GUI class, where a lot of "magic" integer values were used to position objects on the screen.

While replacing magic string and integer values, we found that there were some sections of code we could refactor. For example, we first had this method within a cell manager class:
```java
	if (sharkCell.getAnimal().getTime() %1 0 == 0) 
```
This was replaced by a boolean method in our animal class called timeToMultiply, which looked like this, where TIME_TO_MULTIPLY was set to 10.: 
```java
	public boolean timeToMultiply(){
		return ((time % TIME_TO_MULTIPLY) == 0);
	}
```
We made changes similar to this at least three more times. This was done to remove getter methods and to make classes more active.
