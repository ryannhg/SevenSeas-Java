package frontend;
import backend.Game;

public class Main 
{

	public static void main(String[] args) 
	{
		Mouse mouse = new Mouse();
		Game game = new Game();
		Frame frame = new Frame(mouse, game);
		game.setDisplay(frame);
		game.setInput(mouse);
		
		game.start();
		
	}

}
