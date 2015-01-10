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
		
		/*
		this.playing = true;
		while(playing)
		{
			//	INPUT
			int command = input.getPlayerCommand();
			
			//	LOGIC
			world.update(command);
			
			//	OUTPUT
			world.draw(display);
		}*/
		
		world.draw(display);
	}
	
	private void initWorld()
	{
		world = new World();
	}
	
}
