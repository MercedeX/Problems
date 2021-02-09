# Problems

Solved N-Queen problem in my spare time. In case you are wondering I've got nothing better to do with spare time, think of me as a crazy person or Nerd...slightly older though, just trying to polish scala skills since I'm not actively working on it and I don't want to forget the language I like. You can read more about it ,and probably better that what I could explain, at

 - https://en.wikipedia.org/wiki/Eight_queens_puzzle
 - https://www.tutorialspoint.com/N-Queen-Problem

## Solution Explanation

You will see three separate areas. These areas are related to how code is structured. 
- Application
- Structures
- Logic

For a problem this small, you might think of it as overkill and that's okay but my purpose is to use, practice and polish scala so that's why I went to this length and don't forget --I'm a nerd

In structures, we have an enum **Piece**. I think that is like board square state. there are three
- Blocked: already in range of some queen
- Queen: queen is present at this point
- Empty: free!

By default, a position is empty. Typical chess has variety of pieces but since My intent was not to mimic chess so I'll go with 3 only. They do not represent or hold any other information.

Then is the **Position**. think of it as (Int, Int) and nothing else. so while it is mostly used as to identify a position on board, it is also used to define maximum possible position and also the size of the board. I just don't like referring to _1 _2  for tuple members and scala has no way of naming them. 

The next is **Board**. Again a class that holds a 2D array to represent the board. A board is a 2D array of pieces and any piece can be a queen, blocked or empty. it also holds the size, which can be computed from Array itself (rows and columns are passed as arguments) and also the limit which are the last maximum points one can traverse in array.

Next is the algorithm itself. it's very simple. for variety I solved the problem with 2 ways. recursive and non recursive. This whole class is the solution. singleton object and _apply()_. there is a function that takes a board and checks if queen can be placed there. If it can be then it places queen. there is another function that calculates the range i.e. the positions which cannot be occupied once a queen is placed on board. there is another function tht calculates the row, column or diagnol. 

In the actual algorithm I used these functions in a loop to solve the problem. The key is the function that returns iteratively the next vacant position. This is my way of implementing **BACKTRACKING** technique rather than using a stack.
