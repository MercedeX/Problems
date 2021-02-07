package com.Problems.NQueens.Algorithm

import com.Problems.NQueens.Structures.{Board, Position, Empty, Queen, Blocked}

object Solve {
    def apply(board: Board, queens: Int) = {
    def getAvailablePositions(b1: Board):Iterable[Position] = {
      var items = for(x<- 0 to b1.limits.x;y<- 0 to b1.limits.y if b1(x, y) == Empty)
                    yield Position(x,y)
      items.toIterable
    }
    def calculatePositions(cur: Position, limit: Position, xop: (Int) => Int, yop: (Int) => Int): Seq[Position] = {
      val buffer = new scala.collection.mutable.ListBuffer[Position]()
      var Position(x, y) = cur
      while (x >= 0 && y >= 0 && x <= limit.x && y <= limit.y) {
        buffer.append(Position(x, y))
        x = xop(x);
        y = yop(y)
      }
      buffer.distinct.toSeq
    }
    def tryPlacingQueenAt(b1: Board, position: Position): Option[Board] = {
      var limits = b1.limits

      // check if queen can be placed there.
      val res1 = b1(position) match {
        case Empty => {
          b1(position) = Queen
          Some(b1)
        }
        case _ => None
      }

      val res2 = res1.map(board => {
        val r1 = calculatePositions(Position(0, position.y), b1.limits, x => x + 1, y => y)
        val r2 = calculatePositions(Position(position.x, 0), b1.limits, x => x, y => y + 1)
        val r3 = calculatePositions(position, b1.limits, x => x - 1, y => y - 1)
        val r4 = calculatePositions(position, b1.limits, x => x + 1, y => y - 1)
        val r5 = calculatePositions(position, b1.limits, x => x - 1, y => y + 1)
        val r6 = calculatePositions(position, b1.limits, x => x + 1, y => y + 1)

        val rf = Array(r1, r2, r3, r4, r5, r6).flatMap(x => x).distinct
        for (blocked <- rf)
        {
          board(blocked) = board(blocked) match {
            case Empty => Blocked
            case state => state
          }
        }
        board
      })
      res2
    }
    def calculate(b1: Board): (Boolean, Board) = {
      for (position <- getAvailablePositions(b1)) {
        val b0 = tryPlacingQueenAt(b1, position)

        val result = b0 match {
          case Some(b1) => (true, b1)
          case None => (false, Board(0, 0))
        }

        if (result._1)
          return result
      }
      (false, Board(0, 0))
    }
    def iterative(b1: Board, q1: Int) ={
      var currentBoard = b1
      var count = queens
      var continue = true

      while (count > 0 && continue) {
        val (continue, newBoard) = calculate(currentBoard)
        count = count - 1
        if (continue)
          currentBoard = newBoard
      }
      currentBoard
    }
    def recurse(input: Board, q1: Int): Board = {
      def innerRecursion(input: (Boolean,Board), q1: Int): (Boolean,Board)={
        val (success, b1) = input
        if(success) {
          q1 match {
            case 0 => (true, b1)   // Nothing to do
            case 1 => calculate(b1)  // Solve for base case
            case n => {
              val tmp = innerRecursion(input, q1 - 1) // Solve for N-1
              innerRecursion(tmp, 1)  // Now solve for N
            }
          }
        }
        else
          input
      }

      var (_, solution) = innerRecursion((true, board), queens)
      solution
    }

    // Two ways of solving same problem, uncomment any one
    recurse(board, queens)
    //iterative(board, queens)

  }

}
