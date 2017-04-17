/**
 *
 * @author Bryan Urquia
 *
 * Main class.
 * 
 * Tile Game implemented using a Dense Board to keep track of the tiles using a 2D array.
 *
 * Usage: java PlayText2048 rows cols [random-seed]
 * 
 * Instructions: d moves tiles down
 *				u moves tiles down
 *				r moves tiles right
 *				l moves tiles left
 *
 *  Merging occurs if tiles are of the same value. 
 *  The added value of the converged tiles are added up to the total score.
 *  
 *  Game ends once no moves are possible or when the score equals 2048.
 *  
 *  ** By Default, the game begins with a 5 x 5 grid **
 * 
 */

import java.util.Scanner;

// Class to play a single game of 2048
public class PlayText2048 {

  // Play a game of 2048 of the given size
  // Usage: java PlayText2048 rows cols
  // To specify random seed: java PlayText2048 rows cols [random-seed]
  public static void main(String args[]){

    int rows, cols, seed;

    if(args.length == 2){
      rows = Integer.parseInt(args[0]);
      cols = Integer.parseInt(args[1]);
      seed = 13579; // Default seed for random number generator
    }
    else if(args.length == 3) {
      rows = Integer.parseInt(args[0]);
      cols = Integer.parseInt(args[1]);
      seed = Integer.parseInt(args[2]);
    }else{
      rows = 5;
      cols = 5;
      seed = 13579; // Default seed for random number generator
    }

    System.out.println("Instructions");
    System.out.println("Enter moves as l r u d q for------------");
    System.out.println("");
    System.out.println("l: shift left");
    System.out.println("r: shift right");
    System.out.println("u: shift up");
    System.out.println("d: shift down");
    System.out.println("q: quit game");
    System.out.println();
    
    Game2048 game = new Game2048(rows,cols,seed);
    int initialTiles = rows*cols/4; //Board starts 25% filled with tiles.
    for(int i=0; i<initialTiles; i++){
      game.addRandomTile();
    }

    Scanner stdin = new Scanner(System.in);
    while(!game.isGameOver()){
      System.out.printf("Score: %d\n",game.getScore());
      System.out.println(game.boardString());
      System.out.printf("Move: ");
      String input = stdin.next();

      if(input.equals("q")){ 
        break; 
      }
      else if(input.equals("l")){
        game.shiftLeft();
      }
      else if(input.equals("r")){
        game.shiftRight();
      }
      else if(input.equals("u")){
        game.shiftUp();
      }
      else if(input.equals("d")){
        game.shiftDown();
      }

      System.out.println(input);

      //Game rule, a new tile emerges when the board moves.
      if(game.lastShiftMovedTiles()){
        game.addRandomTile();
      }
    }
    System.out.printf("\n\nGame Over! Final Score: %d\n\n",game.getScore());
  }
}
