package backend;

public class Game 
{
	//	INTERFACES
	private Display 			display;
	private Input 				input;
	private GameClock			gameClock;
	
	//	GAME VARIABLES
	private World				world;
	
	public Game()
	{
		initWorld();
	}
	
	private void initWorld()
	{
		world = new World();
	}
	
	public void setDisplay(Display display)
	{
		this.display = display;
		this.gameClock = display.getGameClock();
	}
	
	public void setInput(Input input)
	{
		this.input = input;
	}
	
	public void start()
	{
		world.draw(display);
		gameClock.start();
	}
	
	public void step()
	{
		//	INPUT
		int tilePressed = input.getTilePressed();
		
		//	LOGIC
		if(world.update(tilePressed))
		{
			//	OUTPUT
			world.draw(display);
		}
		
		checkForNotifications();
	}
	
	public void checkForNotifications()
	{
		if(world.hasNotification())
			world.sendNotification(display);
	}
}
