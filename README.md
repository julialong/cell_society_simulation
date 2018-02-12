
# cellsociety_team08 README

### General Info

Edward Zhuang, Julia Long, and Jeffrey Li worked on this project.
We started on February 1st, and finished on February 11 (early AM on
February 12th). Julia estimates she spent around 35 hours on this project in total, including time spent working on the design plan. Jeffrey estimates he spent 40 hours on this project. Edward estimates he spent around 35 hours on this project. 

The initial roles laid out in our design plan did not exactly match our actual roles, because we initially had very different ideas on how much work different parts of the project truly entailed. For example, Julia was initially going to work on the Cell Controller classes, Edward on the UI, and Jeffrey on the different Cell types themselves. Our design quickly evolved, and Julia ended up working on the UI, Edward ended up working on the XML aspects, and Jeffrey ended up working on the back end simulation components. We all made an effort to help each other out on their parts when necessary.

### Any books, papers, online, or human resources that you used in developing the project

To design the XML writer and parser classes, Edward first began by discussing with teammates and classmates about the various methods by which the XML files could be organized. In addition, he used [several online resources](https://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm) [to assist with](https://www.mkyong.com/java/how-to-create-xml-file-in-java-dom/) the design of the classes.

Julia used [this blog post](http://code.makery.ch/blog/javafx-dialogs-official/) to learn how to add JavaFX dialog boxes to the user interface. Prior to implementing this, she was manually creating error messages that would show up in the window as Rectangle objects. This was functional but not very graphically appealing. She also consulted with friends in the class who were also working on UI for suggestions on ways to approach certain problems, such as implementing the
graph. Jeffrey often consulted friends who had previously taken 308 on their opinion regarding his designs. Apart from that, he didn’t use many external resources.

### Files used to start the project (the class(es) containing main)

The Main class contains the main method.

### Files used to test the project and errors you expect your program to handle without crashing

The XML files contained in the data folder were used to test the program. We expect the program to handle the upload of invalid XML files without crashing -- the UI should display a JavaFX error dialog box. 

### Any data or resource files required by the project (including format of non-standard files)

In order to run, the simulator needs to be supplied with a XML file with the following format:
```xml
<simulation type>
	<authors>
		<name></name>
	</authors>
	<dimensions>
		<xsize></xsize>
		<ysize></ysize>
	</dimensions>
	<parameter_list>
		<parameters>
			<value></value>
		</parameters>
	</parameter_list>
	<cell_on_list>
		<type>
	<cell></cell>
</type>	
<cell_on_list>
<simulation type>
```

### Any information about using the program (i.e., command-line/applet arguments, key inputs, interesting example data files, or easter eggs)

The program contains a resource file that can change the language used in the program to German.

### Any decisions, assumptions, or simplifications you made to handle vague, ambiguous, or conflicting requirements

When we received the guidelines for the third sprint, we realized that it would be impossible to implement all the suggested features. To solve this issue, we decided early on which additional features we wanted to try and include.

### Any known bugs, crashes, or problems with the project's functionality

When simulation grid size is reduced dynamically during running Wator, graph values don’t reduce accordingly. Triangular simulations don’t work. If the simulation is left running for extended periods of time, the program begins to lag significantly.
	

 ### Your impressions of the assignment to help improve it in the future

Overall, we found this to be a challenging yet fun assignment to work on. One of the biggest difficulties we had to overcome when creating this program was mastering the use of GIT to share code. Julia did not always enjoy using JavaFX. Some simulations, such as Wa-Tor, took significantly more time to implement compared to others such as Fire, due to the wa we structured our code. We learned a lot from this project, such as how to use XML files, how to use JavaFX, and create a user friendly gui. If were to redo this assignment, we would spend more time planning the structure of our code before implementing it, as this would make it easier to improve upon in the future.
	







