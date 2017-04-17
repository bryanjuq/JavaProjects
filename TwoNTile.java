/**
 *
 * @author Bryan Urquia
 *
 * Class that executes merging and determines amount of points to add to the score.
 *
**/

public class TwoNTile extends Tile {

	private int tileScore;

	public TwoNTile (int n){
		super();
		tileScore = n;
	}

	@Override
	public boolean mergesWith(Tile moving) {
		return (moving instanceof TwoNTile && moving.getScore() == this.getScore());
	}

	@Override
	public Tile merge(Tile moving){		
			Tile newTile = null;
			if((moving instanceof TwoNTile) && moving.getScore() == this.getScore()){
				newTile = new TwoNTile(moving.getScore()+this.getScore());
				return newTile;
			}
			else{
				throw new NullPointerException("Unlike Tiles cant merge..");
			}	
	}

	@Override
	public int getScore() {
		return tileScore;
	}

	@Override
	public String toString() {
		String tile = ""+getScore(); 
		return tile;
	}

}
