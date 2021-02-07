package com.Problems.NQueens.Structures

case class Board(rows: Int=8, columns: Int=8){
  private val board = Array.ofDim[Piece](rows, columns)

  for (x <- 0 to limits.x; y <- 0 to limits.y)
    board(x)(y) = Empty
  /**
   * Returns a new board based on rows and columns
   * @param x
   * @param y
   * @return new board
   */
  def apply(x: Int, y: Int): Piece = {
    throwIfInvalid(x, y)
    board(x)(y)
  }
  def apply(position: Position):Piece = apply(position.x, position.y)
  /**
   * returns the size of the board
   * @return
   */
  def limits = Position(rows-1, columns-1)
  /**
   * returns the size of the board
   * @return
   */
  def size = Position(rows, columns)
  /**
   * Updates the board with the specified otherwise throws exception
   *
   * @param x
   * @param y
   * @param piece
   */
  def update(x: Int, y: Int, piece: Piece): Unit = {
    throwIfInvalid(x, y)
    if (board(x)(y) == Empty || board(x)(y) == Blocked)
      board(x)(y) = piece
  }
  def update(boardPosition: Position, piece: Piece):Unit = update(boardPosition.x, boardPosition.y, piece)
  /**
   * creates a copy with current state
   *  @return
   */
  def duplicate(): Board = {
    val that = Board(this.rows, this.columns)
    for(x<- 0 to this.limits.x; y<- 0 to this.limits.y) that(x, y) = this(x, y)
    that
  }

  private def throwIfInvalid(x: Int, y: Int): Unit = if (x<0 || x>limits.x || y<0 || y>limits.y) throw new Exception(s"Invalid position $x, $y")
}




