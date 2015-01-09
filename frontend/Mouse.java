package frontend;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import backend.Input;

public class Mouse implements MouseListener, Input 
{
	private int 		player_movement;	
	

	@Override
	public void mouseClicked(MouseEvent e) 
	{
		
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{

	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{

	}

	@Override
	public void mouseEntered(MouseEvent e) 
	{

	}

	@Override
	public void mouseExited(MouseEvent e) 
	{

	}

	@Override
	public int getPlayerMovement() 
	{
		return player_movement;
	}

}
