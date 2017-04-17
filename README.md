# JavaProjects

Main class: PlayText2048.java

Tile Game implemented using a Dense Board to keep track of the tiles using a 2D array.

By Default, the game begins with a 5 x 5 grid.

Usage to specidy rows/cols: java PlayText2048 rows cols
To specify random seed: java PlayText2048 rows cols [random-seed]

Instructions: d moves tiles down
	      u moves tiles down
              r moves tiles right
              l moves tiles left

Merging occurs if tiles are of the same value. The added value of the converged tiles are added up to the total score.

Game ends once no moves are possible or when the score equals 2048.

Have fun!
