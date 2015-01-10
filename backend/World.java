package backend;

public class World 
{	
	//	ISLAND GENERATION
	private static final int	PERCENT_ISLAND = 10;
	
	//	PIRATE GENERATION
	private static final int	BASE_PIRATES = 1,
								AVG_PIRATES_PER_LEVEL = 1;
	
	//	VARIABLES
	private Tile[][] 			tiles;
	private int 				playerX, playerY;
	private	int 				level;
	
	//	INITIALIZING THE WORLD
	
	public World()
	{		
		loadLevel(Global.INIT_LEVEL);
	}
	
	private void loadLevel(int level)
	{
		this.level = level;
		
		initTiles();
		initWhirlpools();
		initPlayer();
		initIslands();
		initPirates();
	}
	
	private void initTiles()
	{
		tiles = new Tile[Global.NUM_COLS][Global.NUM_ROWS];
		
		for(int x = 0; x < Global.NUM_COLS; x++)
			for(int y = 0; y < Global.NUM_ROWS; y++)
				tiles[x][y] = new Tile();
	}
	
	private void initWhirlpools()
	{
		tiles[0][0].setType(Global.TILE_WHIRLPOOL);
		tiles[0][Global.NUM_COLS-1].setType(Global.TILE_WHIRLPOOL);
		tiles[Global.NUM_ROWS-1][0].setType(Global.TILE_WHIRLPOOL);
		tiles[Global.NUM_ROWS-1][Global.NUM_COLS-1].setType(Global.TILE_WHIRLPOOL);
	}
	
	private void initPlayer()
	{
		int playerX = Global.PLAYER_INIT_X;
		int playerY = Global.PLAYER_INIT_Y;
		
		tiles[playerX][playerY].setType(Global.TILE_PLAYER);
		tiles[playerX][playerY].setActor(new Player());
	}
		
	private void initIslands()
	{
		int num = 0;
		int num_islands = (int)(Math.random()*PERCENT_ISLAND/100*(Global.NUM_COLS*Global.NUM_ROWS));

		while(num < num_islands)
		{
			int randX = (int)(Math.random()*Global.NUM_COLS);
			int randY = (int)(Math.random()*Global.NUM_ROWS);
		
			if(tiles[randX][randY].isType(Global.TILE_EMPTY))
			{
				tiles[randX][randY].setType(Global.TILE_ISLAND);
				num++;
			}
		}
	}
	
	private void initPirates()
	{
		int num = 0;
		int num_pirates = (int)(Math.random()*AVG_PIRATES_PER_LEVEL*level)+BASE_PIRATES;
				
		while(num < num_pirates)
		{
			int randX = (int)(Math.random()*Global.NUM_COLS);
			int randY = (int)(Math.random()*Global.NUM_ROWS);
			
			if(tiles[randX][randY].isType(Global.TILE_EMPTY) && isDistanceFrom(Global.TILE_PLAYER, Global.SAFE_DISTANCE, randX, randY))
			{
				tiles[randX][randY].setType(Global.TILE_PIRATE);
				tiles[randX][randY].setActor(new Pirate());
				num++;
			}
		}
		
	}
	
	private boolean isDistanceFrom(int type, int distance, int x, int y)
	{
		for(int i = x-distance; i < x + distance; i++)
			for(int j = y-distance; j < y + distance; j++)
			{
				if(i > 0 && i < Global.NUM_COLS && j > 0 && j < Global.NUM_ROWS && tiles[i][j] != null && tiles[i][j].isType(type))
					return false;
			}
		
		return true;
	}
	
	//	HANDLING INPUT
	
	public void update(int command)
	{
		
	}
	
	//	DISPLAYING THE GAME
	
	public void draw(Display d)
	{
		for(int x = 0; x < Global.NUM_COLS; x++)
			for(int y = 0; y < Global.NUM_ROWS; y++)
			{
				Tile tile = tiles[x][y];
				int x2 = x*Global.TILE_SIZE;
				int y2 = y*Global.TILE_SIZE;
				
				d.drawColorTile(x2, y2, Global.COLOR_SEABLUE);
				
				switch(tile.getType())
				{
					case Global.TILE_EMPTY: break;
					case Global.TILE_PLAYER: d.drawImageTile(x2, y2, Global.IMG_PLAYER + tile.getActor().getDir()); break;
					case Global.TILE_ISLAND: d.drawImageTile(x2, y2, Global.IMG_ISLAND); break;
					case Global.TILE_WHIRLPOOL: d.drawImageTile(x2, y2, Global.IMG_WHIRLPOOL); break;
					case Global.TILE_WRECKAGE: d.drawImageTile(x2, y2, Global.IMG_WRECKAGE); break;
					case Global.TILE_PIRATE: d.drawImageTile(x2, y2, Global.IMG_PIRATE + tile.getActor().getDir()); break;
					default: break;
				}
			}
		
		d.draw();
	}
}
