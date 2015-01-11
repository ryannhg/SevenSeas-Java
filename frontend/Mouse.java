package frontend;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import backend.Global;
import backend.Input;

public class Mouse implements MouseListener, Input 
{
	private int 		tile;
	
	public Mouse()
	{
		tile = -1;
	}

	@Override
	public void mouseClicked(MouseEvent e) 
	{
		int x = e.getX()-Global.LEFT_INSET;
		int y = e.getY()-Global.TOP_INSET;
				
		int tile_x = x/Global.TILE_SIZE;
		int tile_y = y/Global.TILE_SIZE;
				
		tile = tile_x+tile_y*Global.NUM_COLS;
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
	public int getTilePressed() 
	{
		// TODO Auto-generated method stub
		int tile = this.tile;
		this.tile = -1;
		return tile;
	}

}
