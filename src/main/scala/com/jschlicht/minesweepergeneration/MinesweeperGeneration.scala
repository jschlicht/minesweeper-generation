package com.jschlicht.minesweepergeneration

import scala.collection.immutable._
import scala.util.Random
import scala.annotation.tailrec

/**
 * Generates and prints a Minesweeper grid.
 * @example
 * To generate a board with 20 rows, 15 columns, and 30 mines:
 * scala MinesweeperGeneration 20 15 30
 * @author Jared Schlicht <jschlicht@gmail.com>
 */
object MinesweeperGeneration extends App {
  val rows = args(0).toInt
  val columns = args(1).toInt
  val mines = args(2).toInt

  val mineCoordinates = generateMineCoordinates(rows, columns, mines)

  for (rowCoordinate <- 0 until rows) {
    for (columnCoordinate <- 0 until columns) {
      print(mineLabel((columnCoordinate, rowCoordinate), mineCoordinates))
    }
    println()
  }

  /**
   * Generates a list of mine coordinates for a game of minesweeper.
   * @param rows the number of rows on the board
   * @param columns the number of columns on the board
   * @param mineCount the number of mines to place on the board
   * @return a set coordinates of the form (columnNumber, rowNumber) at which mines are located.
   */
  private def generateMineCoordinates(rows: Int, columns: Int, mineCount: Int) = {
    @tailrec
    def generateMineCoordinates(
                                 rows: Int,
                                 columns: Int,
                                 remainingMineCount: Int,
                                 currentMineCoordinates: Set[(Int, Int)]): Set[(Int, Int)] = {
      if (remainingMineCount == 0) {
        currentMineCoordinates
      } else {
        val newMineCoordinate = (Random.nextInt(columns), Random.nextInt(rows))

        if (!currentMineCoordinates.contains(newMineCoordinate)) {
          generateMineCoordinates(rows, columns, remainingMineCount - 1, currentMineCoordinates + newMineCoordinate)
        } else {
          //It's possible to randomly generate coordinates for places we've already placed mines. Try again, if needed.
          generateMineCoordinates(rows, columns, remainingMineCount, currentMineCoordinates)
        }
      }
    }

    generateMineCoordinates(rows, columns, mineCount, HashSet.empty[(Int, Int)])
  }

  /**
   * Generates a label for a particular coordinate on a Minesweeper game board.
   * @param coordinate 0-based coordinate of the form (columnNumber, rowNumber) for which to generate a label.
   * @param mineCoordinates a set coordinates of the form (columnNumber, rowNumber) at which mines are located.
   * @return
   * "x" if this coordinate contains a mine
   * "." if this coordinate has no adjacent mines
   * "n", where n is the number of adjacent mines to this coordinate
   */
  private def mineLabel(coordinate: (Int, Int), mineCoordinates: Set[(Int, Int)]): String = {
    val (columnNumber, rowNumber) = coordinate

    if (mineCoordinates.contains(coordinate)) {
      "x"
    } else {
      val topLeftHasMine = mineCoordinates.contains(columnNumber - 1, rowNumber - 1)
      val topHasMine = mineCoordinates.contains(columnNumber, rowNumber - 1)
      val topRightHasMine = mineCoordinates.contains(columnNumber + 1, rowNumber - 1)

      val leftHasMine = mineCoordinates.contains(columnNumber - 1, rowNumber)
      val rightHasMine = mineCoordinates.contains(columnNumber + 1, rowNumber)

      val bottomLeftHasMine = mineCoordinates.contains(columnNumber - 1, rowNumber + 1)
      val bottomHasMine = mineCoordinates.contains(columnNumber, rowNumber + 1)
      val bottomRightHasMine = mineCoordinates.contains(columnNumber + 1, rowNumber + 1)

      val mineCount = List(
        topLeftHasMine,
        topHasMine,
        topRightHasMine,
        leftHasMine,
        rightHasMine,
        bottomLeftHasMine,
        bottomHasMine,
        bottomRightHasMine).count(_ == true)
      if (mineCount == 0) "." else mineCount.toString
    }
  }
}
