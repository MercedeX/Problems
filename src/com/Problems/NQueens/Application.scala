package com.Problems.NQueens

import Structures.{Board, Position}
import Algorithm.Solve
import com.Problems.NQueens.Structures.Structures.display

import scala.io.StdIn
import scala.util.{Try, Failure, Success}

object Application {

  /**
   * Asks the user to input the board size in L,B format. if incorrect size or no size is given then 8x8 is used by default
   * @param message
   * @return Board of specified size
   */
  def getBoard() = {
    print(s"Enter board size (default 8x8):")
    val size = Try(
        StdIn
        .readLine()
        .split(",")
        .map(x => x.trim.toInt)
        .toArray
    ) match {
      case Success(numbers) => Position(numbers(0), numbers(1))
      case Failure(_) => Position(8, 8)
    }
    Board(size.x, size.y)
  }

  /**
   * Prompts the user to input the number of queens for which the solution to be found
   * @return: number of queens or 0 if user inputs 0 or <0
   */
  def getQueensTobePlaced() = {
    print("how many queens:")
    val queens = Try(StdIn.readLine().toInt) match {
      case Success(n) => if (n < 0) 0 else n
      case _ => 0
    }
    queens
  }


  /**
   * void Main(string[] args) of Scala
   * @param args: string array
   */
  def main(args: Array[String]): Unit = {
    val myBoard = getBoard()
    val queens = getQueensTobePlaced()

    val finalBoard = Solve(myBoard, queens)

    display(finalBoard)
  }
}