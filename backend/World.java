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
	private Pirate[]			pirates;
	private Player				player;
	private	int 				level;
	
	//	NOTIFICATIONS
	private boolean 			notify;
	private String 				notification;
	
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
		player = new Player();
		
		tiles[player.getX()][player.getY()].setType(Global.TILE_PLAYER);
		tiles[player.getX()][player.getY()].setActor(player);
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
		int num_pirates = (int)(Math.random()*AVG_PIRATES_PER_LEVEL*2*level)+BASE_PIRATES;
			
		pirates = new Pirate[num_pirates];
		
		while(num < num_pirates)
		{
			int randX = (int)(Math.random()*Global.NUM_COLS);
			int randY = (int)(Math.random()*Global.NUM_ROWS);
			
			if(tiles[randX][randY].isType(Global.TILE_EMPTY) && isDistanceFrom(Global.TILE_PLAYER, Global.SAFE_DISTANCE, randX, randY))
			{
				pirates[num] = new Pirate(randX,randY);
				
				tiles[randX][randY].setType(Global.TILE_PIRATE);
				tiles[randX][randY].setActor(pirates[num]);
				
				num++;
			}
		}
		
		
		
	}
	
	private boolean isDistanceFrom(int type, int distance, int x, int y)
	{
		for(int i = x-distance; i < x + distance+1; i++)
			for(int j = y-distance; j < y + distance+1; j++)
			{
				if(Global.inBounds(i,j) && tiles[i][j].isType(type))
					return false;
			}
		
		return true;
	}

	
	//	HANDLING INPUT
	
	public boolean update(int tilePressed)
	{
		int command = getCommand(tilePressed);
		
		if(command != Global.CMD_NONE)
		{
			if(command == Global.CMD_FIRE)
				firePlayerCannons();
			else if(getTileInDirection(player.getX(),player.getY(),command).isType(Global.TILE_WHIRLPOOL))
				whirlpoolPlayer();				
			else if(!getTileInDirection(player.getX(),player.getY(),command).isType(Global.TILE_EMPTY))
				return false;
			else movePlayer(command);
			
			movePirates();	
			checkWinCondition();
			
			return true;
		}
		else return false;
	}
	
	private int getCommand(int tilePressed)
	{
		int x = tilePressed%Global.NUM_COLS;
		int y = tilePressed/Global.NUM_COLS;
		
		
		for(int i = x-1; i < x+2; i++)
			for(int j = y-1; j < y+2; j++)
			{
				if(Global.inBounds(i,j) && tiles[i][j].isType(Global.TILE_PLAYER))
				{
					if(i < x)		//	PLAYER TO LEFT
					{
						if(j < y)		//	PLAYER ABOVE
							return Global.CMD_DR;
						else if (j > y) 	//	PLAYER BELOW
							return Global.CMD_UR;
						else return Global.CMD_R;
					}
					else if(i > x)	//	PLAYER TO RIGHT
					{
						if(j < y)		//	PLAYER ABOVE
							return Global.CMD_DL;
						else if (j > y) 	//	PLAYER BELOW
							return Global.CMD_UL;
						else return Global.CMD_L;
					}
					else if(j < y)		//	PLAYER ABOVE
						return Global.CMD_D;
					else if (j > y) 	//	PLAYER BELOW
						return Global.CMD_U;
					else return Global.CMD_FIRE;
				}
			}
		
		return Global.CMD_NONE;
	}
	
	private void firePlayerCannons()
	{
		int x = player.getX();
		int y = player.getY();
		int dir = player.getDir();
				
		int dx = 0, dy = 0;
		boolean left = true, right = true;
		
		//	FIRE CANNONS
		for(int range = 1; range < Global.CANNON_RANGE+1; range++)
		{
			if(dir == Global.DIR_U || dir == Global.DIR_D)
			{
				dx = range;
				dy = 0;
			}
			else if(dir == Global.DIR_L || dir == Global.DIR_R)
			{
				dx = 0;
				dy = range;
			}
			else if(dir == Global.DIR_UL || dir == Global.DIR_DR)
			{
				dx = range;
				dy = -range;
			}
			else
			{
				dx = range;
				dy = range;
			}
			
			if(left && Global.inBounds(x+dx,y+dy) && tiles[x+dx][y+dy].isType(Global.TILE_PIRATE))
			{
				left = false;
				killPirate(x+dx,y+dy);
			}
			if(right && Global.inBounds(x-dx,y-dy) && tiles[x-dx][y-dy].isType(Global.TILE_PIRATE))
			{
				right = false;
				killPirate(x-dx,y-dy);
			}

		}
	}
	
	private void killPirate(int x, int y)
	{
		//	REMOVE PIRATE
			
			for(int i = 0; i < pirates.length; i++)
			{
				if(pirates[i] != null && pirates[i].getX()==x && pirates[i].getY()==y)
				{
					pirates[i] = null;
					
					tiles[x][y].setType(Global.TILE_WRECKAGE);
					tiles[x][y].setActor(null);
					
					break;
				}
			}
		
		//	ADD TO PLAYER SCORE
			
		//	CHECK WIN CONDITION
			checkWinCondition();
	}
	
	private Tile getTileInDirection(int x, int y, int direction)
	{
		switch(direction)
		{
		case Global.DIR_U:
			return tiles[x][y-1];
		case Global.DIR_UR:
			return tiles[x+1][y-1];
		case Global.DIR_R:
			return tiles[x+1][y];
		case Global.DIR_DR:
			return tiles[x+1][y+1];
		case Global.DIR_D:
			return tiles[x][y+1];
		case Global.DIR_DL:
			return tiles[x-1][y+1];
		case Global.DIR_L:
			return tiles[x-1][y];
		case Global.DIR_UL:
			return tiles[x-1][y-1];
		default:
			return null;
		}
	}
	
	//	TO-DO: Catch the evil bug in my code.
	private void whirlpoolPlayer()
	{
		
		int oldX = player.getX();
		int oldY = player.getY();
		tiles[oldX][oldY] = new Tile();
		
		while(true)
		{
			
			int randX = (int)(Math.random()*Global.NUM_COLS);
			int randY = (int)(Math.random()*Global.NUM_ROWS);
						
			if( tiles[randX][randY].isType(Global.TILE_EMPTY) 
				&& randX != 0 && randY != 0 
				&& randX != Global.NUM_COLS-1 && randY != Global.NUM_ROWS-1
				&& isDistanceFromPirates(Global.SAFE_DISTANCE, randX, randY))
			{
				player = new Player(randX,randY);
				
				tiles[randX][randY].setType(Global.TILE_PLAYER);
				tiles[randX][randY].setActor(player);
				
				return;
			}
		}
	}
	
	private boolean isDistanceFromPirates(int distance, int x, int y)
	{
		for(int i = 0; i < pirates.length; i++)
		{
			if(pirates[i] != null && !isDistanceFrom(distance, x, y, pirates[i].getX(), pirates[i].getY()))
				return false;
		}
		
		return true;
	}
	
	private boolean isDistanceFrom(int distance, int x1, int y1, int x2, int y2)
	{
		for(int i = x1-distance; i < x1 + distance+1; i++)
			for(int j = y1-distance; j < y1 + distance+1; j++)
			{
				if(i==x2 && j==y2)
					return false;
			}
		
		return true;
	}

	
	private void movePlayer(int direction)
	{
		//	REMOVE PLAYER FROM CURRENT TILE
		int x = player.getX();
		int y = player.getY();
		tiles[x][y] = new Tile();
		
		//	MOVE PLAYER TO NEW TILE
		player.move(direction);
		x = player.getX();
		y = player.getY();
		tiles[x][y].setType(Global.TILE_PLAYER);
		tiles[x][y].setActor(player);
	}
	
	private void checkWinCondition()
	{
		//	IF TILES STILL CONTAIN PIRATES, RETURN
		for(int i = 0; i < pirates.length; i++)
			if(pirates[i] != null)
				return;
		//	ELSE PLAYER WINS
		
		//	NOTIFY PLAYER
		setNotification("You win!");
		
		//	GO TO NEXT LEVEL
		level++;
		loadLevel(level);
	}
	
	private void movePirates()
	{		
		//	MOVE ALL PIRATES
		for(int i = 0; i < pirates.length; i++)
		{
			if(pirates[i] != null)
			{
				//	GET OLD LOCATION
				int oldX = pirates[i].getX();
				int oldY = pirates[i].getY();
				tiles[oldX][oldY] = new Tile();
				
				//	MOVE PIRATE
				pirates[i].moveToward(player.getX(), player.getY());
				
				int newX = pirates[i].getX();
				int newY = pirates[i].getY();
				
				//	IF PIRATE COLLIDES WITH THE PLAYER
				if(tiles[newX][newY].isType(Global.TILE_PLAYER))
				{
					//	SET TILE TO WRECKAGE
					tiles[newX][newY] = new Tile();
					tiles[newX][newY].setType(Global.TILE_WRECKAGE);
					
					//	GAME OVER
					gameOver();
				}
				//	IF PIRATE COLLIDES WITH ANOTHER PIRATE
				else if(tiles[newX][newY].isType(Global.TILE_PIRATE))
				{
					//	KILL THIS PIRATE
					pirates[i] = null;
					
					//	KILL OTHER PIRATE
					killPirate(newX,newY);
				}
				//	IF PIRATE COLLIDES WITH ANOTHER OBSTACLE
				else if(tiles[newX][newY].isType(Global.TILE_ISLAND) || 
						tiles[newX][newY].isType(Global.TILE_WRECKAGE))
				{
					//	KILL PIRATE
					pirates[i] = null;
					
					//	CHECK WIN CONDITION
					checkWinCondition();
				}
				//	ELSE TILE IS EMPTY
				else
				{
					tiles[newX][newY].setType(Global.TILE_PIRATE);
					tiles[newX][newY].setActor(pirates[i]);
				}
			}
		}
	}
	
	private void gameOver()
	{
		//	NOTIFY PLAYER GAME OVER
		setNotification("You lose!");
		
		//	SET GAME TO LEVEL 1
		level = 1;
		loadLevel(level);
		
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
	
	public void setNotification(String s)
	{
		notify = true;
		notification = s;
	}
	
	public boolean hasNotification()
	{
		return notify;
	}
	
	public void sendNotification(Display d)
	{
		d.sendNotification(notification);
		notify = false;
	}
}
