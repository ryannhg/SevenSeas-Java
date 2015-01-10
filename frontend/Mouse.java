package frontend;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import backend.Global;
import backend.Input;

public class Mouse implements MouseListener, Input 
{
	private int 		command;	
	
	public Mouse()
	{
		command = Global.CMD_NONE;
	}

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
	public int getPlayerCommand() 
	{
		return command;
	}

}
