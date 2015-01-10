package backend;

public abstract class Actor 
{
	//	VARIABLES
	private int dir;
	
	public Actor()
	{
		this.dir = (int)(Math.random()*Global.NUM_DIRS);
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
