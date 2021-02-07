package com.Problems.NQueens

import Structures.{Board, Position}
import Algorithm.Solve
import com.Problems.NQueens.Structures.Structures.display

import scala.io.StdIn
import scala.util.{Try, Failure, Success}

object Application{
  def promptForInt(message: String):Int={
    print(s"$message:")
    StdIn.readLine().toInt
  }
  def promptForIntPair(message: String) = {
    //val pattern = "\\s*(\\d+)\\s*\\,\\s*(\\d+)\\s*".r
    print(s"$message:")
    Try(
      StdIn
        .readLine()
        .split(",")
        .map(x => x.trim.toInt)
        .toArray
    ) match {
      case Success(numbers) => Position(numbers(0), numbers(1))
      case Failure(_) => Position(8, 8)
    }
  }
  def readKey = System.in.read()
  val getBoard = ()=> {
    val Position(rows, columns) = promptForIntPair("Get board size")
    Board(rows, columns)
  }
  val getQueensTobePlaced = ()=> promptForInt("how many queens") match{
    case n if n<0 => 0
    case x => x
  }

  def main(args: Array[String]): Unit ={
    val myBoard = getBoard()
    val queens = getQueensTobePlaced();
    val finalBoard = Solve(myBoard, queens)

    display(finalBoard)
  }
}