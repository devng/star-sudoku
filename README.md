# Star SUDOKU

## TL;DR

Java version of the popular Sudoku puzzle game. Intends to provide an advanced user interface.
With Star SUDOKU you can create random sudoku grids or design your own to play/solve.
It can be used as sudoku generator, solver. You can export sudoku grids as PDF, RTF, HTML, XML and CSV.

![](screen1.png?raw=true)

![](screen2.png?raw=true)

### This Readme includes:

1. System requirements
2. How to build Star SUDOKU
3. Running Star SUDOKU on your system
4. Features
5. Instructions
  * Entering numbers in a cell
  * Select number then cells
  * Select cell then number
  * Deleting numbers from a cell
  * Selecting a cell without entering a number
  * Design mode
  * Additional options
  * Save / Load
  * Completing a game
6. Special thanks to

1. System requirements
----------------------

Star SUDOKU is written in JAVA programming language, this means that it can be run on any Operating System which has JAVA Runtime Environment (JRE).

You need JRE 1.7.0 (also know as JRE 7) or above. You can download JRE for free at www.java.com

2. How to build Star SUDOKU
---------------------------
If you want to build Star SUDOKU on your local machine you need to install the Java Development Kit (JDK). You need JDK 1.7.0 or above.

Star SUDOKU uses the Gradle build system, you can build the project locally just by typing the following in the console:

```
./gradlew build
```

On Windows use:
```
./gradlew.bat build
```

The generated jar file can be found in the folder _build/libs_

### How to extend Star SUDOKU
If you want to import the project in an IDE such as Eclipse or IntelliJ IDEA then Gradle provides a way to generate all the necessary project files.

Generate Eclipse project:
```
./gradlew eclipse
```

Generate IntelliJ IDEA project:
```
./gradlew idea
```

3. Running Star SUDOKU on your system
-------------------------------------

To start Star SUDOKU try one of the following options:

* Double click on the jar File to start Star SUDOKU. If this didn't work, then you didn't associate your jar Files with your JRE.

* For Windows users we also provide an exe file, which ist just a wrapper for the JAR file. Double click on the exe file to start the application.

* Double click on `StarSUDOKU.bat` for Windows users or on `StarSUDOKU` for Linux users. This will start the application, but only if you have built it with Gradle first.

* Open the console go to your Star SUDOKU folder and type: 
```
java -jar StarSUDOKU-1.0.jar
```

4. Features
-----------

* Random puzzles every time you play
* 2 different systems of selecting cells and entering numbers.
* Helping lines mode in the option menu, so You can see easier, if there is a collision
* 3 difficulty levels and an user custom level\*
* 3 Different Numbers Distributions\*\*
* Load/Save Sudoku games without any kind of losses
* Design your own puzzles
* Export Puzzles to PDF, RTF, HTML, XML, CSV

\* An user custom level means that you can enter a number between 1 and 81. And next time you generate a new puzzle exactly that many numbers will be shown at the Sudoku grid. So if you enter 81 then the next puzzle will already be solved when you generate it.

\*\* Number distribution in Star SUDOKU means, how are the numbers distributed when you generate a new puzzle. There are 3 numbers distributions:

* Evenly filled 3x3 Squares - means all 3x3 Squares are evenly filled with numbers, when you start a new puzzle.
* Evenly distributed numbers - means that all numbers from 1 to 9 are evenly distributed. So when you generate a new puzzle there will be even number of 1s, 2s, 3s and so on...
* Random - all numbers you will see at the beginning of a new puzzle will be randomly chosen.


5. Instructions
---------------

### Entering numbers in a cell
  
There now 2 different systems of selecting cells and entering numbers. The system being used can be changed from the option menu at any time.
The two systems are:

* Select number then cells (default)
* Select cell then number
  
### Select number then cells
  
Select a number using the buttons at the bottom of the main playing area (or using the keyboard). After the required number has been selected you can use the left mouse button to place that number in the cell. You can also use the right mouse button to toggle the grid notes. 

### Select cell then number
  
Once the required cell has been selected with the left mouse button, you can place a number into that cell either using the number keys on the keyboard, or by left clicking on the buttons at the bottom of the main playing area. 

By right clicking on cell You will see a blue border around the cell and You can enter a note into the cell, the same way you enter a number.
  
### Deleting numbers from a cell

If you want to delete a number or a note from a cell just enter the same number or note again. There is also a "Delete" button. If you click it or press 'D' and click on a cell with the left mouse button the number in the cell will be deleted, if you click with the right mouse button ALL notes will be deleted from the cell.  

### Selecting a cell without entering a number
  
Click on the cell with the middle mouse button.

### Design mode

Click on the 'Design New Sudoku' Button to start the Design Mode, by default Star Sudoku starts with Design Mode on. Enter Your numbers in the Grid and hit 'Play the designed Sudoku' button, if the game cannot find any solution to the numbers you've just entered then a dialog window is shown, otherwise You can begin solving the Sudoku puzzle.

### Additional options
  
Draw helping lines in the options menu - You can see easier, if there is a collision

Solve - Give up the game and reveal the complete solution.

Clear - Restart the game from the beginning.

### Save / Load

You can save the grid at any time (either when designing or when playing). All of your notes will be saved too, so you won't loose any information by saving/loading.

### Completing a game

If you complete the puzzle successfully you will be informed by a pop up window automatically. 

6. Special thanks to
--------------------

Jivko Vantchev

And to You for using Star SUDOKU :)
