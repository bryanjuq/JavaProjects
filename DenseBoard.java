/**
 *
 * @author Bryan Urquia
 *
 *
 * DenseBoard class that holds the tiles, implemented with a 2D array. It can be view as the game's ingine.
 *
 * Shifting oprations are defined here.
 *
 *
 */


public class DenseBoard extends Board {
	private Tile[][] board;
	private int rows;
	private int cols;
	private boolean moveTile;
	private boolean shiftMergedTiles; //Used for determining score
	private int numberOfEmptySpaces;
	private int numberOfTiles;

	public DenseBoard(int rows, int cols){
		this.rows = rows;
		this.cols = cols;
		this.numberOfEmptySpaces = 0;
		board = new TwoNTile[this.rows][this.cols];

		//Initializing board
		for(int r = 0; r < this.rows; r++){
			for(int c = 0; c < this.getCols(); c++){
				board[r][c] = null;
				this.numberOfEmptySpaces++;
			}
		}
		this.numberOfTiles = 0;
		moveTile = false;
		shiftMergedTiles = false;
		
	}

	public DenseBoard(Tile t[][]){  
		board = new TwoNTile[t.length][t[0].length];
		
		this.numberOfEmptySpaces=0;
		this.numberOfTiles = 0;
		for(int r = 0; r < t.length; r++){
			for(int c = 0; c < t[0].length; c++){
				if(t[r][c] == null){
					board[r][c] = null;
					this.numberOfEmptySpaces++;
				}
				else{
					board[r][c] = new TwoNTile(t[r][c].getScore());
					this.numberOfTiles++;
				}
			}
		}
		this.rows = board.length;
		this.cols = board[0].length;
		moveTile = false;
		shiftMergedTiles = false;
		
	}

	/* Method used for  */
	public Board copy() {
		DenseBoard copy = new DenseBoard(this.board);
		
		copy.setMoveTile(this.moveTile);
		copy.setShiftMergedTiles(this.shiftMergedTiles);
		copy.setNumberOfEmptySpaces(this.numberOfEmptySpaces);
		copy.setMoveTile(this.moveTile);
		copy.setShiftMergedTiles(this.shiftMergedTiles);
		copy.setNumberOfTile(this.numberOfTiles);
		return copy;
	}
	public Tile[][] getBoard(){
		return this.board;
	}
	
	public void setRows(int row){
		this.rows = row;
	}

	public int getRows(){
		return rows;
	}
	
	public void setCols(int col){
		this.cols = col;
	}
	
	public int getCols(){
		return cols;
	}
	
	public int getNumberOfEmptySpaces() {
		return numberOfEmptySpaces;
	}
	
	public void setNumberOfEmptySpaces(int numberOfEmptySpaces) {
		this.numberOfEmptySpaces = numberOfEmptySpaces;
	}
	
	public int getNumberOfTiles() {
		return numberOfTiles;
	}
	
	public void setNumberOfTile(int numberOfTiles){
		this.numberOfTiles = numberOfTiles;
	}
	
	public void setMoveTile(boolean move){
		this.moveTile = move;
	}

	public boolean moveTile(){
		return moveTile;
	}
	
	public void setShiftMergedTiles(boolean merged){
		this.shiftMergedTiles = merged;
	}
	public boolean shiftMergedTiles(){
		return this.shiftMergedTiles;
	}


	/*  Returns the amount of tiles currently on the board  */
	@Override
	public int getTileCount() {
		int tileCount = 0;
		for(int row = 0; row < getRows(); row++){
			for(int col = 0; col < getCols(); col++){
				if(board[row][col] != null){
					tileCount++;
				}	
			}
		}
		
		this.setNumberOfTile(tileCount);
		return tileCount;
	}

	/*   Returns the number of free spaces   */
	public int getFreeSpaceCount() {
		int emptySlots = 0;
		for(int row = 0; row < getRows(); row++){
			for(int col = 0; col < getCols(); col++){
				if(board[row][col] == null){
					emptySlots++;
				}	
			}
		}
		this.setNumberOfEmptySpaces(emptySlots);
		return emptySlots;
	}

	/*   Used for retrieving a tile value at specified position on the board   */
	@Override
	public Tile tileAt(int row, int col){				

		if((row >= 0 && row <= this.getRows()) && (col >= 0 && col <= getCols())){
			Tile target = null;
			if(this.board[row][col] != null){
				target = new TwoNTile(this.board[row][col].getScore());
			}
			return target;
		}
		else{
			throw new NullPointerException("Atempt out of array boundary");
		}
	}

	// Used throughout the game to determine if another tile should be inserted
	@Override
	public boolean lastShiftMovedTiles() {
		return moveTile;
	}

	public boolean mergePossible() {
		boolean posible = false;
		Board testBoard;

		for(int i =1; i <= 4; i++){ //the loop is only useful to take shift turn
			
			// Keeping a copy of the internal board to test shifts without making permanent changes to the board
			testBoard = (DenseBoard)this.copy();
			if(i == 1){
				testBoard.shiftUp();
				if(((DenseBoard) testBoard).shiftMergedTiles()){
					posible =true;
				}
			}
			testBoard = (DenseBoard)this.copy();
			if(i == 2){
				testBoard.shiftDown();
				if(((DenseBoard) testBoard).shiftMergedTiles()){
					posible =true;
				}
			}
			
			testBoard = (DenseBoard)this.copy();
			if(i == 3){
				testBoard.shiftLeft();
				if(((DenseBoard) testBoard).shiftMergedTiles()){
					posible =true;
				}
			}
			testBoard = (DenseBoard)this.copy();
			if(i == 4){
				testBoard.shiftRight();
				if(((DenseBoard) testBoard).shiftMergedTiles()){
					posible =true;
				}
			}
		}

		return posible;
	}

	public void addTileAtFreeSpace(int freeT, Tile tile) {		
		tile = new TwoNTile(tile.getScore());
		int spacePosition = -1;
		int targetRow = 0;
		int targetCol = 0;

		int r = 0;
		int c = 0;
		for(r = 0; r < getRows();r++){			
			for(c = 0; c < getCols(); c++){
				if(board[r][c] == null){
					spacePosition++;
					if(spacePosition == freeT){
						targetRow = r;
						targetCol = c;
						this.board[r][c] = tile;
						return;
					}
				}
			}

		}

		//It would only get to this part if it went for the whole grid
		// and didn't find the desired space
		if(((r == getRows())) && (c == getCols())){
			throw new NullPointerException("Full Grid");
		}

	}

	
	//I didn't have to use this
	public int compareTo (DenseBoard other){
		int n = 0;

		for(int r = 0; r < getRows(); r++){
			for(int c = 0; c < getCols(); c++){
				if(this.board[r][c] == other.board[r][c]){	
				}
				else{
					n = 1;
				}
			}
		}

		return n;
	}
	
	
	/* All shifting operations follow the same procedure, what changes is the col/row pointer based on direction.
	 */
	@Override
	public int shiftLeft() {
		Tile moving = null;	
		int scoreSum = 0;
		
		for(int row = 0; row < getRows(); row++){ 
			int spaceCount = 0;
			int stationaryCol = -1; //starts outside of bound so that it points to the left/right most position
									//when it is assigned a tile to point to.
			for(int col = 0; col < getCols(); col++){
				moving = board[row][col];

				//This first set of if else are to determine where stationary will point
				// also to increment the space count for each row
				if (moving != null && stationaryCol < 0){
					stationaryCol = col;
				}
				else if(moving != null && stationaryCol < col){
					//keep it as it is
				}
				else if(moving == null){
					spaceCount++;
				}
				
				//merging is done only in the following if statement body
				//The rest are for shifting purposes and also keeping track of stationary
				if(moving != null && stationaryCol >=0 && stationaryCol < col){
					if(moving.mergesWith(board[row][stationaryCol])){
						this.shiftMergedTiles = true;
						board[row][stationaryCol] = moving.merge(board[row][stationaryCol]);
						scoreSum += board[row][stationaryCol].getScore();
						board[row][col] = null;
						stationaryCol = col;
						spaceCount++;
						this.moveTile = true;
					}

					else if(!(moving.mergesWith(board[row][stationaryCol])) && spaceCount!=0){
						board[row][col-spaceCount] = board[row][col];
						board[row][col] = null;
						stationaryCol = col-spaceCount;
						this.moveTile = true;
					}
					else if(!(moving.mergesWith(board[row][stationaryCol])) && spaceCount==0){
						stationaryCol = col;
					}	
				}

				else if(moving != null && stationaryCol==col && spaceCount > 0){
					board[row][col-spaceCount] = board[row][col];
					board[row][col] = null;
					stationaryCol = col-spaceCount;
					this.moveTile = true;


				}
				else if(moving != null && stationaryCol==col && spaceCount == 0){

				}
			}
		}
	
		return scoreSum;
	}

	@Override
	public int shiftRight() {
		Tile moving = null;	
		int scoreSum = 0;


		for(int row = 0; row < getRows(); row++){ 
			int spaceCount = 0;
			int stationaryCol = getCols()+1;

			for(int col = getCols()-1; col >= 0; col--){
				moving = board[row][col];


				if (moving != null && stationaryCol >= getCols()){
					stationaryCol = col;
				}
				else if(moving != null && stationaryCol > col){
					//keep it as it is
				}
				else if(moving == null){
					spaceCount++;
				}


				if(moving != null && stationaryCol < getCols() && stationaryCol > col){
					if(moving.mergesWith(board[row][stationaryCol])){
						this.shiftMergedTiles = true;
						board[row][stationaryCol] = moving.merge(board[row][stationaryCol]);
						scoreSum += board[row][stationaryCol].getScore();
						board[row][col] = null;
						stationaryCol = col;
						spaceCount++;
						this.moveTile = true;
					}

					else if(!(moving.mergesWith(board[row][stationaryCol])) && spaceCount!=0){
						board[row][col+spaceCount] = board[row][col];
						board[row][col] = null;
						stationaryCol = col+spaceCount;
						this.moveTile = true;
					}
					else if(!(moving.mergesWith(board[row][stationaryCol])) && spaceCount==0){
						stationaryCol = col;
					}	
				}

				else if(moving != null && stationaryCol==col && spaceCount > 0){
					board[row][col+spaceCount] = board[row][col];
					board[row][col] = null;
					stationaryCol = col+spaceCount;
					this.moveTile = true;


				}
				else if(moving != null && stationaryCol==col && spaceCount == 0){

				}
			}
		}
		return scoreSum;
	}

	@Override
	public int shiftUp() {
		Tile moving = null;	
		int scoreSum = 0;

		for(int col = 0; col < getCols(); col++){ 
			int spaceCount = 0;
			int stationaryRow = -1;
			for(int row = 0; row < getRows(); row++){
				moving = board[row][col];


				if (moving != null && stationaryRow < 0){
					stationaryRow = row;
				}
				else if(moving != null && stationaryRow < row){
					//keep it as it is
				}
				else if(moving == null){
					spaceCount++;
				}

				if(moving != null && stationaryRow >=0 && stationaryRow < row){
					if(moving.mergesWith(board[stationaryRow][col])){
						this.shiftMergedTiles = true;
						board[stationaryRow][col] = moving.merge(board[stationaryRow][col]);
						scoreSum += board[stationaryRow][col].getScore();
						board[row][col] = null;
						stationaryRow = row;
						spaceCount++;
						this.moveTile = true;
					}

					else if(!(moving.mergesWith(board[stationaryRow][col])) && spaceCount!=0){
						board[row-spaceCount][col] = board[row][col];
						board[row][col] = null;
						stationaryRow = row-spaceCount;
						this.moveTile = true;
					}
					else if(!(moving.mergesWith(board[stationaryRow][col])) && spaceCount==0){
						stationaryRow = row;
					}	
				}

				else if(moving != null && stationaryRow==row && spaceCount > 0){
					board[row-spaceCount][col] = board[row][col];
					board[row][col] = null;
					stationaryRow = row-spaceCount;
					this.moveTile = true;


				}
				else if(moving != null && stationaryRow==col && spaceCount == 0){

				}
			}
		}
		return scoreSum;
	}

	@Override
	public int shiftDown() {
		Tile moving = null;	
		int scoreSum = 0;

		for(int col = 0; col < getCols(); col++){ 
			int spaceCount = 0;
			int stationaryRow = getRows()+1;
			for(int row = getRows()-1; row >= 0; row--){
				moving = board[row][col];


				if (moving != null && stationaryRow >= getRows()){
					stationaryRow = row;
				}
				else if(moving != null && stationaryRow > row){
					//keep it as it is
				}
				else if(moving == null){
					spaceCount++;
				}


				if(moving != null && stationaryRow < getRows() && stationaryRow > row){
					if(moving.mergesWith(board[stationaryRow][col])){
						this.shiftMergedTiles = true;
						board[stationaryRow][col] = moving.merge(board[stationaryRow][col]);
						scoreSum += board[stationaryRow][col].getScore();
						board[row][col] = null;
						stationaryRow = row;
						spaceCount++;
						this.moveTile = true;
					}

					else if(!(moving.mergesWith(board[stationaryRow][col])) && spaceCount!=0){
						board[row+spaceCount][col] = board[row][col];
						board[row][col] = null;
						stationaryRow = row+spaceCount;
						this.moveTile = true;
					}
					else if(!(moving.mergesWith(board[stationaryRow][col])) && spaceCount==0){
						stationaryRow = row;
					}	
				}

				else if(moving != null && stationaryRow==row && spaceCount > 0){
					board[row+spaceCount][col] = board[row][col];
					board[row][col] = null;
					stationaryRow = row+spaceCount;
					this.moveTile = true;


				}
				else if(moving != null && stationaryRow==col && spaceCount == 0){

				}
			}
		}
		return scoreSum;
	}

	public String toString(){
		
		String table = "";
		String space = "-";
		String format = "%4s ";
		for(int r=0; r<rows; r++){
			for(int c=0; c<cols; c++){
				Tile currentTile = board[r][c];
				if(currentTile != null){
					table += String.format(format, currentTile.toString());
				}
				else{
					table += String.format(format, space);
				}
				
			}
			
			table += "\n";
		}	
		return table;
	}
}
