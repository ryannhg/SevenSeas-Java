package backend;

public class Player 
{
	private int x, y, dir;
	
	public Player()
	{
		this.x = Global.NUM_COLS/2;
		this.y = Global.NUM_ROWS/2;
		this.dir = (int)(Math.random()*Global.NUM_DIRS);
	}
	
	public void setRandomLocation(World w)
	{
		int num = 0;
		
		while(num < 10)
		{
			int rand_x = (int)(Math.random()*Global.NUM_COLS);
			int rand_y = (int)(Math.random()*Global.NUM_ROWS);
			
			if(world.isEmpty(rand_x, rand_y) && world.isSafeDistanceFromPlayer(rand_x, rand_y))
			{
				this.x = rand_x;
				this.y = rand_y;
				this.dir = (int)(Math.random()*Global.NUM_DIRS);
				return;
			}
			
			num++;
		}
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
