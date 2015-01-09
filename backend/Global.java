package backend;

public class Global 
{
	//	GAME SETTINGS
	public static final int		TILE_SIZE = 48,
								NUM_COLS = 11,
								NUM_ROWS = NUM_COLS,
								NUM_DIRS = 8,
								SAFE_DISTANCE = 3,
								CANNON_RANGE = 3;
	
	//	IMAGES
	public static final int		IMG_PLAYER_U = 0, 	IMG_PLAYER_UR = 1,	IMG_PLAYER_R = 2,	IMG_PLAYER_DR = 3,	
								IMG_PLAYER_D = 4,	IMG_PLAYER_DL = 5,	IMG_PLAYER_L = 6, 	IMG_PLAYER_UL = 7, 
								
								IMG_ISLAND = 8,		IMG_WHIRLPOOL = 9,  IMG_WRECKAGE = 10,
								IMG_CANNONBALL = 11,
								
								IMG_PIRATE_U = 20, 	IMG_PIRATE_UR = 21,	IMG_PIRATE_R = 22,	IMG_PIRATE_DR = 23,	
								IMG_PIRATE_D = 24,	IMG_PIRATE_DL = 25,	IMG_PIRATE_L = 26, 	IMG_PIRATE_UL = 27;
	
	//	COLORS
	public static final int		COLOR_BLACK = 0, 	COLOR_SEABLUE = 1;
	
	//	DIRECTIONS
	public static final int		DIR_U = 0, DIR_UR = 1, DIR_R = 2, DIR_DR = 3,
								DIR_D = 4, DIR_DL = 5, DIR_L = 6, DIR_UL = 7;
	
	//	INPUT COMMANDS
	public static final int 	NO_MOVE = 0,
								MOVE_UL = 1,	MOVE_U = 2,		MOVE_UR = 3,
								MOVE_L = 4,		FIRE = 5,		MOVE_R = 6,
								MOVE_DL = 7,	MOVE_D = 8,		MOVE_DR = 9;
	
}
