/**
 *
 * @author Bryan Urquia
 *
 * Driver class that keeps track of tile locations after every move, it determines when the game is over
 * and computes the position and probability of new tiles.
 *
**/
import java.util.Random;

public class Game2048 {
	private DenseBoard board;
	private int seed, score;
	private Random internalRandomNum;

	public Game2048(int rows, int cols, int seed) {
		board = new DenseBoard(rows, cols);
		this.seed = seed;
		this.internalRandomNum = new Random(seed); //Determines where the next tile will appear.
		score = 0;
	}

	//Second constructor for in-game functionality
	public Game2048(Tile tiles[][], int seed){
		board = new DenseBoard(tiles);
		this.internalRandomNum =new Random(seed);
		score = 0;
	}

	/* Add a random tile to a random free position:
	  1. Generate a random location using randomFreeLocation()
	  2. Generate a random tile using getRandomTile()
	  3. Add the tile to board using one of its methods
	  */


	/*  Helper method to determine next tile position  */
	public int randomFreeLocation() {
		int numberOfFreeSpaces = board.getFreeSpaceCount();
		return this.internalRandomNum.nextInt(this.board.getNumberOfEmptySpaces());
	}


	/*  Method to determine if next tile is 2, 4 or 8 base on probability (2 being the highest). */
	public Tile getRandomTile() {
		
		double tileProbability = internalRandomNum.nextDouble();
		int tileScore = 0;
				
		if(tileProbability >=0.0 && tileProbability <= 0.7){
			tileScore = 2;
		}
		else if(tileProbability >= 0.7 && tileProbability <= 0.95){
			tileScore = 4;
		}
		else if(tileProbability >= 0.95 && tileProbability <= 1.0){
			tileScore = 8;
		}
		Tile randomTile = new TwoNTile(tileScore);
		
		return randomTile;
	}

	/*  Final step on adding a tile to the board, makes use of previous methods  */
	public void addRandomTile() {
		
		 if(board.getFreeSpaceCount() == 0){
		      return;
		    }
		
		int randomFreeLocation = randomFreeLocation();	
		
		Tile randomTile = getRandomTile();
		
		board.addTileAtFreeSpace(randomFreeLocation, randomTile);
		
	}

	public boolean isGameOver() {
		boolean itIsOver = false;

		//Game is over when no more moves or score is 2048!
		if(this.board.getFreeSpaceCount() == 0 && !(this.board.mergePossible()) || (this.score>=2048)){
			itIsOver = true;
		}		
		return itIsOver;
	}

	public Tile tileAt(int row, int col) {
		Tile target = board.tileAt(row, col);
		return target;
	}

	/*   Shift helper functions to update the score after every move  */

	public void shiftLeft() {
		score =score + board.shiftLeft();
	}

	public void shiftRight() {
		score = score+ board.shiftRight();
	}

	public void shiftUp() {
		score = score + board.shiftUp();
	}

	public void shiftDown() {
		score = score + board.shiftDown();
	}

	/* Used to determine if a new tiles gets inserted */
	public boolean lastShiftMovedTiles() {
		return board.lastShiftMovedTiles();
	}

	public int getScore() {
		return score;
	}

	public String boardString() {
		return board.toString();
	}

	public int getRows() {
		return this.board.getRows();
	}

	public int getCols() {
		return this.board.getCols();
	}

}
