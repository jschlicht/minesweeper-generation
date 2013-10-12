Scala Minesweeper Generation
============================

An implementation of the [Reddit DailyProgrammer challenge #108](http://www.reddit.com/r/dailyprogrammer/comments/126905/10272012_challenge_108_intermediate_minesweeper/).

* Implemented with Scala
* Uses only immutable data structures/variables.
* Stores mine coordinates in an immutable HashSet:
  * Much lower memory usage than a "typical" 2D Array/List of Lists implementation.

Possible Future Improvements
============================
* Use Akka or parallel collections to use multiple CPU cores to count the number of adjacent mines.
   * Calculating the adjacent mines is embarrassingly parallel once you have the mine coordinates calculated. Each cell on the board can calculate the adjacent mine count independently of the other cells.
   * The mine coordinates are immutable, so there's no need to worry about thread safety.
   * The mine coordinates list should be much cheaper to send to a remote Akka actor than a 2D array of the whole board.