package backend;

public abstract class Actor 
{
	//	VARIABLES
	private int x,y,dir;
	
	public Actor()
	{
		this.x = 0;
		this.y = 0;
		this.dir = (int)(Math.random()*Global.NUM_DIRS);
	}
	
	public Actor(int x, int y)
	{
		this.x = x;
		this.y = y;
		this.dir = (int)(Math.random()*Global.NUM_DIRS);
	}
	
	public int getX()
	{
		return x;
	}
	
	public void setX(int x)
	{
		this.x = x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public void setY(int y)
	{
		this.y = y;
	}
	
	public int getDir()
	{
		return dir;
	}
	
	public void setDir(int dir)
	{
		this.dir = dir;
	}
		
}
