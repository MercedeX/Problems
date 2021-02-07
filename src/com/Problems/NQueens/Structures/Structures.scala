package com.Problems.NQueens.Structures

package object Structures{
  def display(myboard: Board): Unit = {
    print("=======================\n")
    for (x <- 0 to myboard.limits.x) {
      for (y <- 0 to myboard.limits.y) {
        val ch = myboard(x, y) match {
          case Empty => "_"
          case Queen => "Q"
          case Blocked => "X"
        }
        print(s" $ch ")
      }
      println()
    }
    print("=======================\n")
  }
}