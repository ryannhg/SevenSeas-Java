package frontend;

import java.awt.event.ActionListener;

import javax.swing.Timer;

import backend.Game;
import backend.GameClock;

public class Clock extends Timer implements GameClock
{
	public Clock(int delay, ActionListener listener, Game g) 
	{
		super(delay, listener);
	}

}
