package com.Problems.NQueens.Algorithm

import com.Problems.NQueens.Structures.{Board, Position, Empty, Queen, Blocked}

object Solve {
  /**
   * Given a board and the number of queens, the algorithm tries to find a solution using BACK-TRACKING technique.
   * @param board of NxN size
   * @param queens: no of queens to be placed on board
   * @return : final board
   */
    def apply(board: Board, queens: Int) = {
      /**========================
       *  LOCAL FUNCTIONS
       *
       *  I'm using local functions because I want to have a single function provide the solution.
       *  in Scala, local functions must be declared before anything else.
       * ================ */

      /**
       * Gets an Iterable of Position that are "Empty"
        * @param b1: Board
       * @return: Iterable[Position]
       */
    def getAvailablePositions(b1: Board):Iterable[Position] = {
      var items = for(x<- 0 to b1.limits.x;y<- 0 to b1.limits.y if b1(x, y) == Empty)
                    yield Position(x,y)
      items.toIterable
    }

      /**
       * Calculates the positons that are affected by the queen placement on the board
       * @param cur: Current position
       * @param limit: Size of the board
       * @param xop: function that gives the next position of X
       * @param yop: function that gives the next position of Y
       * @return: a Seq[Position] that are affected
       */
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

      /**
       * Places a queen at a position if possible, calculates the Squares that are affected, marks them and returns the board
       * @param b1: Current Board
       * @param position: Position of the Queen placement
       * @return: New Board
       */
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

      /**
       * Iteratively, places a queen on board, calculates positions, then repeats until either no queens are left to place or the board is full
       * @param b1: Initial Board
       * @return: Final board
       */
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

      /**
       * Iterative solution, faster, crude but simple
       * @param b1: Board
       * @param q1: queens
       * @return: final board
       */
    def iterative_solution(b1: Board, q1: Int) ={
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

      /**
       * recursive solution: slow: elegant but hard to debug
       * @param input
       * @param q1
       * @return
       */
    def recursive_solution(input: Board, q1: Int): Board = {
      def recurse(input: (Boolean,Board), q1: Int): (Boolean,Board)={
        val (success, b1) = input
        if(success) {
          q1 match {
            case 0 => (true, b1)   // Nothing to do
            case 1 => calculate(b1)  // Solve for base case
            case n => {
              val tmp = recurse(input, q1 - 1) // Solve for N-1
              recurse(tmp, 1)  // Now solve for N
            }
          }
        }
        else
          input
      }

      var (_, solution) = recurse((true, board), queens)
      solution
    }


     /** Algorithm starts,
      *  We have an iterative solution or a recursive solution.
      *  recursive is elegant but slightly fucked up in debugging
      *  iterative one is plain and simple to understand and debug.
      * */


    recursive_solution(board, queens)
    //iterative_solution(board, queens)

  }

}
