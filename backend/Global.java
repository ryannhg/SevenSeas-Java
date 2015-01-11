package backend;

public class Global 
{
	//	GAME SETTINGS
	public static final int		TILE_SIZE = 48,
								NUM_COLS = 11,
								NUM_ROWS = NUM_COLS,
								NUM_DIRS = 8,
								SAFE_DISTANCE = 3,
								CANNON_RANGE = 3,
								TIMER_MS = 200,
								LEFT_INSET = 0,
								TOP_INSET = 28;
								
	public static final int		PLAYER_INIT_X = NUM_COLS/2,
								PLAYER_INIT_Y = NUM_ROWS/2,
								INIT_LEVEL = 1;
	
	//	IMAGES
	public static final int		IMG_PLAYER = 0,
								
								IMG_ISLAND = 8,
								IMG_WHIRLPOOL = 9,
								IMG_WRECKAGE = 10,
								IMG_CANNONBALL = 11, 
								IMG_EXPLOSION = 12,
								
								IMG_PIRATE= 20;
	
	//	COLORS
	public static final int		COLOR_BLACK = 0, 	COLOR_SEABLUE = 1;
	
	//	DIRECTIONS
	public static final int		DIR_U = 0, DIR_UR = 1, DIR_R = 2, DIR_DR = 3,
								DIR_D = 4, DIR_DL = 5, DIR_L = 6, DIR_UL = 7;
	
	//	INPUT COMMANDS
	public static final int 	CMD_U = 0, CMD_UR = 1, CMD_R = 2, CMD_DR = 3,
								CMD_D = 4, CMD_DL = 5, CMD_L = 6, CMD_UL = 7,
								CMD_NONE = 8,
								CMD_FIRE = 9;
	
	//	TILE TYPE
	public static final int		TILE_EMPTY = 0,
								TILE_PLAYER = 1,
								TILE_PIRATE = 2,
								TILE_ISLAND = 3,
								TILE_WHIRLPOOL = 4,
								TILE_WRECKAGE = 5;
	
	
	public static boolean inBounds(int x, int y)
	{
		return (x > 0 && x < Global.NUM_COLS && y > 0 && y < Global.NUM_ROWS);
	}
	
	public static int getDirection(int newX, int newY, int oldX, int oldY)
	{
		if(newX > oldX)
		{
			if(newY > oldY)			return DIR_DR;
			else if(newY < oldY)	return DIR_UR;
			else					return DIR_R;
		}
		else if(newX < oldX)
		{
			if(newY > oldY)			return DIR_DL;
			else if(newY < oldY)	return DIR_UL;
			else					return DIR_L;
		}
		else if(newY < oldY)		return DIR_U;
		else						return DIR_D;
	}
	
}
