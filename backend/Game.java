package backend;

public class Game 
{
	//	INTERFACES
	private Display 			display;
	private Input 				input;
	
	//	GAME VARIABLES
	private World				world;
	private boolean				playing;
	
	public Game(Display display, Input input)
	{
		this.display = display;
		this.input = input;
		
		initWorld();
		//initScore();
		
		this.playing = true;
		while(playing)
		{
			//	INPUT
			int command = input.getPlayerMovement();
			
			//	LOGIC
			world.movePlayer(command);
			
			//	OUTPUT
			world.draw(display);
		}
	}
	
	private void initWorld()
	{
		world = new World();
	}
	
}
