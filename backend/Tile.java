package backend;

public class Tile 
{
	//	VARIABLES
	private int type;
	private Actor actor;
	
	public Tile()
	{
		type = Global.TILE_EMPTY;
		actor = null;
	}
	
	public void setActor(Actor actor)
	{
		this.actor = actor;
	}
	
	public Actor getActor()
	{
		return actor;
	}
	
	public void setType(int type)
	{
		this.type = type;
	}
	
	public int getType()
	{
		return type;
	}
	
	public boolean isType(int type)
	{
		return (this.type == type);
	}
}
