package backend;

public class World 
{
	//	TILES AND COLLISION
	private static final int		EMPTY=0, PLAYER=1, ISLAND=2, WHIRLPOOL=3, WRECKAGE=4, PIRATE=5;
	
	//	ISLAND GENERATION
	private static final int		PERCENT_ISLAND = 5;
	
	//	PIRATE GENERATION
	private static final int		BASE_PIRATES = 1,
									AVG_PIRATES_PER_LEVEL = 1;
	
	//	CONTENTS
	private int[][] 			tiles;
	private Player				player;
	private Pirate[]			pirates;
	private	int 				level;				
	
	public World()
	{		
		loadLevel(1);
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
		tiles = new int[Global.NUM_COLS][Global.NUM_ROWS];
		
		for(int x = 0; x < Global.NUM_COLS; x++)
			for(int y = 0; y < Global.NUM_ROWS; y++)
				tiles[x][y] = EMPTY;
	}
	
	private void initWhirlpools()
	{
		tiles[0][0] 								= WHIRLPOOL;
		tiles[0][Global.NUM_COLS-1] 				= WHIRLPOOL;
		tiles[Global.NUM_ROWS-1][0] 				= WHIRLPOOL;
		tiles[Global.NUM_ROWS-1][Global.NUM_COLS-1] = WHIRLPOOL;
	}
	
	private void initPlayer()
	{	
		player = new Player();
		tiles[Global.NUM_ROWS/2][Global.NUM_COLS/2] = PLAYER;
	}
		
	private void initIslands()
	{
		int num = 0;
		int num_islands = (int)(Math.random()*PERCENT_ISLAND/100*(Global.NUM_COLS*Global.NUM_ROWS));

		while(num < num_islands)
		{
			int rand_x = (int)(Math.random()*Global.NUM_COLS);
			int rand_y = (int)(Math.random()*Global.NUM_ROWS);
		
			if(tiles[rand_x][rand_y] == EMPTY)
			{
				tiles[rand_x][rand_y] = ISLAND;
				num++;
			}
		}
	}
	
	private void initPirates()
	{
		int num = 0;
		int num_pirates = (int)(Math.random()*AVG_PIRATES_PER_LEVEL*level)+BASE_PIRATES;
		
		pirates = new Pirate[num_pirates];
		
		for(int p = 0; p < pirates.length; p++)
		{
			Pirate pirate = new Pirate(this);
			
			tiles[pirate.getX()][pirate.getY()] = PIRATE;
			
			pirates[p] = pirate;
		}
		
	}
	
	public boolean isEmpty(int x, int y)
	{
		return (tiles[x][y] == EMPTY);
	}
	
	public boolean playerCanMove(int x, int y)
	{
		return (tiles[x][y]==EMPTY || tiles[x][y]==WHIRLPOOL);
	}
	
	public void movePlayer(int command)
	{
		switch(command)
		{
			case Global.NO_MOVE: return;
			case Global.FIRE: 
				fireCannons(player.getX(),player.getY(),player.getDir());
				return;
			default: 
				tryMove(command);
				return;
		}
	}
	
	private void fireCannons(int x, int y, int dir)
	{		
		boolean left = true, right = true;
		
		for(int range = 1; range < Global.CANNON_RANGE+1; range++)
		{
			int x1, x2, y1, y2;
			
			if(dir == Global.DIR_U || dir == Global.DIR_D)
			{
				x1 = x - range;
				x2 = x + range;
				y1 = y;
				y2 = y;
			}
			else if(dir == Global.DIR_L || dir == Global.DIR_R)
			{
				x1 = x;
				x2 = x;
				y1 = y - range;
				y2 = y + range;
			}
			else if(dir == Global.DIR_DR || dir == Global.DIR_UL)
			{
				x1 = x - range;
				x2 = x + range;
				y1 = y + range;
				y2 = y - range;
			}
			else
			{
				x1 = x - range;
				x2 = x + range;
				y1 = y - range;
				y2 = y + range;
			}
			
			if(left && tiles[x1][y1] == PIRATE)
			{
				killPirate(x1,y1);
				left = false;
			}
			if(right && tiles[x2][y2] == PIRATE)
			{
				killPirate(x2,y2);
				right = false;
			}
		}
		
		if(playerWins())
			loadLevel(level+1);
		else movePirates();	
	}
	
	private void killPirate(int x, int y)
	{
		for(Pirate p : pirates)
			if(p != null && p.getX()==x && p.getY()==y)
			{
				p = null;
				tiles[x][y] = WRECKAGE;
			}
	}
	
	private boolean playerWins()
	{
		for(Pirate p : pirates)
			if(p != null) return false;
		return true;
	}
	
	private void movePirates()
	{
		for(Pirate p : pirates)
		{
			if(p != null)
			{
				p.move(player.getX(), player.getY());
			}
		}
	}
	
	private void tryMove(int command)
	{
		int old_x = player.getX();
		int old_y = player.getY();
		
		int new_x = old_x;
		int new_y = old_y;
		
		if(command == Global.MOVE_U || command == Global.MOVE_UL || command == Global.MOVE_UR)
			new_y = old_y - 1;
		else if(command == Global.MOVE_D || command == Global.MOVE_DL || command == Global.MOVE_DR)
			new_y = old_y + 1;
		
		if(command == Global.MOVE_L || command == Global.MOVE_UL || command == Global.MOVE_DL)
			new_x = old_x - 1;
		else if(command == Global.MOVE_R || command == Global.MOVE_UR || command == Global.MOVE_DR)
			new_x = old_x + 1;
			
		if(playerCanMove(new_x,new_y))
		{
			tiles[old_x][old_y] = EMPTY;
			
			if(new_x > old_x)
			{
				if(new_y > old_y)
					player.setDir(Global.DIR_DR);
				else if(new_y < old_y)
					player.setDir(Global.DIR_UR);
				else
					player.setDir(Global.DIR_R);
			}
			else if(new_x < old_x)
			{
				if(new_y > old_y)
					player.setDir(Global.DIR_DL);
				else if(new_y < old_y)
					player.setDir(Global.DIR_UL);
				else
					player.setDir(Global.DIR_L);
			}
			else
			{
				if(new_y > old_y)
					player.setDir(Global.DIR_D);
				else if(new_y < old_y)
					player.setDir(Global.DIR_U);
			}
			
			if(tiles[new_x][new_y] != WHIRLPOOL)
			{
				player.setX(new_x);
				player.setY(new_y);
				tiles[new_x][new_y] = PLAYER;
			}
			else
			{
				player.setRandomLocation();
			}
			
			
			
			
			movePirates();
		}
	}
	
	public boolean isSafeDistanceFromPlayer(int x, int y)
	{
		int x_dist = (x-player.getX())*(x-player.getX());
		int y_dist = (y-player.getY())*(y-player.getY());
		
		return (x_dist+y_dist < Global.SAFE_DISTANCE*Global.SAFE_DISTANCE);
	}
	
	public void draw(Display d)
	{
		for(int x = 0; x < Global.NUM_COLS; x++)
			for(int y = 0; y < Global.NUM_ROWS; y++)
			{
				d.drawColorTile(x, y, Global.COLOR_SEABLUE);
				switch(tiles[x][y])
				{
					case EMPTY: break;
					case PLAYER: d.drawImageTile(x, y, Global.IMG_PLAYER_U+player.getDir()); break;
					case ISLAND: d.drawImageTile(x, y, Global.IMG_ISLAND); break;
					case WHIRLPOOL: d.drawImageTile(x, y, Global.IMG_WHIRLPOOL); break;
					case WRECKAGE: d.drawImageTile(x, y, Global.IMG_WRECKAGE); break;
					case PIRATE: d.drawImageTile(x, y, Global.IMG_PIRATE_U+getPirateDirection(x,y)); break;
					default: break;
				}
			}
	}
	
	private int getPirateDirection(int x, int y)
	{
		for(Pirate p : pirates)
		{
			if(p.getX() == x && p.getY() == y)
				return p.getDir();
		}
		
		System.out.println("ERROR: Could not find pirate at [" + x + ", " + y + "]");
		return 0;
	}
}
