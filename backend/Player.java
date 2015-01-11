package backend;

public class Player extends Actor 
{
	//	VARIABLES
	
	public Player()
	{
		super(Global.PLAYER_INIT_X,Global.PLAYER_INIT_Y);
	}
	
	public Player(int x, int y)
	{
		super(x,y);
	}
	
	public void move(int direction)
	{
		int oldX = getX();
		int oldY = getY();
		setDir(direction);
		
		switch(direction)
		{
		case Global.DIR_U:
			setY(oldY-1);
			break;
		case Global.DIR_UR:
			setX(oldX+1);
			setY(oldY-1);
			break;
		case Global.DIR_R:
			setX(oldX+1);
			break;
		case Global.DIR_DR:
			setX(oldX+1);
			setY(oldY+1);
			break;
		case Global.DIR_D:
			setY(oldY+1);
			break;
		case Global.DIR_DL:
			setX(oldX-1);
			setY(oldY+1);
			break;
		case Global.DIR_L:
			setX(oldX-1);
			break;
		case Global.DIR_UL:
			setX(oldX-1);
			setY(oldY-1);
			break;
		}
			
	}
}
