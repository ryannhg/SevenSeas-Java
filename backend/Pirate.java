package backend;

public class Pirate extends Actor
{
	//	VARIABLES
	private int		level;
	
	public Pirate(int x, int y)
	{
		super(x,y);
		level = 1;
	}
	
	public void moveToward(int x, int y)
    {
        int oldX = getX(), oldY = getY();
        int bestX = 0, bestY=0;
        int dist = Integer.MAX_VALUE;

        for(int i = -1; i < 2; i++)
        {
            for(int j = -1; j < 2; j++)
            {
                if(i == 0 && j == 0) continue;

                int newX = getX()+i;
                int newY = getY()+j;

                if(!Global.inBounds(newX,newY)) continue;

                if(dist > (newX-x)*(newX-x)+(newY-y)*(newY-y))
                {
                    dist = (newX-x)*(newX-x)+(newY-y)*(newY-y);
                    bestX = newX;
                    bestY = newY;
                }
            }
        }

        setX(bestX);
        setY(bestY);
        
        //	SET DIRECTION
        setDir(Global.getDirection(bestX, bestY, oldX, oldY));
    }
}
