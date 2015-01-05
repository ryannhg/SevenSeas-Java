package frontend;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import backend.Input;

public class Mouse implements MouseListener, Input 
{
	//	PLAYER MOVEMENT
	public static int 	NO_MOVE = 0,
						MOVE_UL = 1,	MOVE_U = 2,		MOVE_UR = 3,
						MOVE_L = 4,		FIRE = 5,		MOVE_R = 6,
						MOVE_DL = 7,	MOVE_D = 8,		MOVE_DR = 9;
	
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
