
# Manifest Company 

<img src="./src/main/resources/mc/manifestcompany/images/manifest_company.png" width="50%" height="50%"/>

  ## Table of Contents:
  1. [Description](#description)
  2. [Gameplay](#gameplay)
  3. [Documentation](#documentation) 
  4. [Screenshots](#screenshots) 
  5. [Credits](#credits)

## Description 

Manifest Company is a business management game built in Java with JavaFX as a final project for CIT 5940. This program 
demonstrates the use of data structures in order to store and manipulate data in the program to perform
complex tasks. 

The game allows users to take control of a company and invest in its future in order to dominate
an industry among other CPU competitors.

## Gameplay

Upon booting up the game, the user is presented with a title screen and options to start a new game,
continue from a save file, or quit. Pressing New Game allows the user to pick a level, name your company,
and start playing. Pressing Continue allows the user to pick from the available save files to continue
progress in the game.

When the game boots up, the user is presented with a prompt on a grey screen. Clicking that prompt will
initialize the game and take the user to main game screen, with buttons to Show Data, take Actions, 
go to the Next Turn, Save, and Quit.

The Show Data button shows a chart of the statistics of each company. 

The Actions button opens a window to select investment values for certain sectors in a company. These sectors
are as follows:
- Marketing: increases the Multiplier granted to profits during calculation.
- Research & Development (R&D): increases the price of goods sold during a turn
- Goods: Increases the maximum capacity of goods able to be sold during a turn
- Human Capital (HR): Decreases the expenses (cost of operation) that cuts into profits during calculation 
- A user may also choose to invest in Tiles. Tiles are the primary score and goal of the gain, representing
company expansion and influence in an industry. Each tile costs $300, and a user may buy tiles to gain score,
or sell previously bought tiles to regain money in a pinch. 

After deciding on investments in the Action menu, a user may confirm these decisions and finalize them. Then,
changes will be reflected in the system, but only visible in the Data menu after the next turn progresses. A 
user may continue to invest after this point, but will be limited by the amount of cash their company currently owns.

The Next Turn button will advance the turn, taking the user's decisions and making decisions for the 3 other 
CPUs. These changes depend on the market demand and price, and eventually amount to either positive or negative
profit that goes into a company's Cash. After a turn completes, the next turn starts, showing a transition screen
with information about the current event that may change the market demand and price. After clicking the transition
screen, the user will return to the main game screen with updated statistics in the Data menu and be allowed
to continue making investing decisions.

After the user gets enough negative profit to have negative cash, they will go bankrupt and lose. This may
occur with the other CPUs as well, and if all CPUs go bankrupt, the user will win by default. If the board
becomes full, a winner is chosen as the company with the highest number of tiles.

At any time, a user may choose to save their progress or quit out of the game.

## Documentation
Project Structure/Packages:
* company package
  * Company: superclass for subclasses:
    * UserCompany: A company belonging the user player
    * NPCCompany: The companies belonging to the CPU players
    * These classes manage the company statistics and methods relating to their operation. Actions towards these companies are accessed by a Mediator design pattern mediated by CompanyAction.
    * Company statistics is stored in an EnumMap using the enum DataType
  * CompanyAction: interface
    * CompanyActionImpl: Implementation of CompanyAction as the mediator of the Mediator design pattern. Includes methods to handle changing and investing in company statistics.
    * NPCActionImpl: A subclass of company action, that inherits all its methods and has extra methods to make random decisions with random values.
* gamelogic package
  * Event: interface
    * EventImpl: Implementation of Event that changes the market values according to which event is chosen.
    * EventType: Java Enum, stores the chances of the event occurring and the text that will be displayed to the GUI once that event is chosen
  * Turn: interface
    * TurnImpl: Implementation of Turn that controls the revenue, expenses, and profit gained during a company's turn, and has methods to check the win/loss condition of the game for each company.
  * Game: class that controls the entire backend of the game
    * Initializes the game board and players
    * Has a method to advance the turn logically and update the company statistics according to the decisions made by each company
    * Manages various game parameters, such as turn number, market statistics, and game over state
    * GameOverState: Java Enum, stores values representing the state of the game when certain conditions are met.
* gui package
  * Tile: Manages a logical and graphical tile in the game.
    * TileType: Java Enum, stores a color associated with player 1(user), and the 3 other CPU players.
    * Stores tile position, and color according to the TileType
  * FXShape: Superclass of tile, defines that a shape in this program must be a Rectangle and have Point2D location.
  * GameController: controls all the GUI logic for the game
    * Many, many FXML private variables to load various GUI elements into variables in order to manipulate and change them according to user actions.
    * Initializes various aspects upon the user's first click on the game GUI, alongside listeners for changes in input.
    * Updates the grid and chart after each turn or each user decision.
    * Adds and removes text from a text queue.
    * Calls nextTurn() in Game when the Next Turn button is clicked, and updates all associated GUI elements as the turn passes.
    * Manages the investing inputs from the investing menu, calling the UserCompany's investing methods upon confirmation
    * Manages the game-over-states and exiting of the game once finished
    * Allows saving of the game by taking in various parameters into the FileLoader's save method
  * TitleController: Manages the title screen and loading of save files
    * Can start a new game given input of a company name for the user and the level selected by the user on the title screen
    * Shows all the files currently in the saveFiles directory upon clicking Continue
    * Loads the given file as a new Game to be loaded into the game screen upon clicking one of the files to load
    * Quits the program upon clicking quit
  * EndController: Manages the end screen
    * Sets the end screen text given certain game-over-states
    * Returns to the main menu upon clicking the end screen
* hashtable package
  * HashTable: an abstract hash table with hash, insert, remove, and search methods
  * OpenAddressingBucket: an open addressable bucket in an open addressable hash table
    * stores a key-value pair
    * stores final static variables to indicate whether a bucket has been empty from the start or empty since removal
  * OpenAddressingHashTable: an open addressable hash table
    * Fleshes out the insert, remove, and search methods of the hash table
    * According to the probing strategy set by QuadraticProbingHashTable, retrieves, removes, or searches items in the hash table
  * QuadraticProbingHashTable: a subclass of OpenAddressingHashTable that implements quadratic probing
    * Defines the probing strategy of OpenAddressingHashTable as quadratic probing, one that uses 2 constants and an index to return a bucket index for items to be put in a hash table
  * This package was adapted from CIT 5940: Data Structures and Software Design zyBooks Activity 6.10
* misc
  * App: JavaFX method that starts the application, contains the main method
  * DataType: Java Enum, defines the data stored in a company's statistics EnumMap
  * FileHandler: Handles saving and loading of files
    * Saving takes certain important game parameters and saves them into a file
    * Loading takes a save file name and returns a game containing all the parameters from that file pertaining to the Game's re-construction

## Screenshots

* Main Menu

 <img alt="main menu screen" src="./src/main/resources/mc/manifestcompany/images/screenmain.PNG" /> 

* Game Screen

 <img alt="game screen" src="./src/main/resources/mc/manifestcompany/images/gamescreen.PNG" /> 

* Data Menu

 <img alt="data chart menu" src="./src/main/resources/mc/manifestcompany/images/data.PNG" /> 

* Invest Menu

 <img alt="invest menu" src="./src/main/resources/mc/manifestcompany/images/invest.PNG" /> 

* Ending Screen

 <img alt="end game screen" src="./src/main/resources/mc/manifestcompany/images/lose.PNG" /> 

 ## Credits

Sung Je Moon: https://github.com/solar268

Antina Yeh: https://github.com/antinayeh

Peici Qiu: https://github.com/peiciqiu

Voravich Silapachairueng: https://github.com/voravichs
 



