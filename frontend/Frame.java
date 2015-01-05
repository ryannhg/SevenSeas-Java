package frontend;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import backend.Display;
import backend.Global;

public class Frame extends JFrame implements Display 
{
	//	SCREEN PROPERTIES
	private static final int 	SCREEN_WIDTH = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
								SCREEN_HEIGHT = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	
	//	FRAME PROPERTIES
	private static String 	FRAME_TITLE = "Seven Seas";
	private static boolean 	FRAME_RESIZABLE = false,
							FRAME_VISIBLE = true,
							FRAME_UNDECORATED = true;
	private static final int 	FRAME_WIDTH = Global.TILE_SIZE*Global.NUM_COLS,
								FRAME_HEIGHT = Global.TILE_SIZE*Global.NUM_ROWS,
								FRAME_X = (SCREEN_WIDTH-FRAME_WIDTH)/2,
								FRAME_Y = (SCREEN_HEIGHT-FRAME_HEIGHT)/2,
								FRAME_DEFAULT_CLOSE_OPERATION=JFrame.EXIT_ON_CLOSE;
	
	//	DRAWING THINGS
	private BufferedImage image;
	private Graphics graphics;
	private static final Color CLEAR_COLOR = Color.BLACK;
	private static final int DEFAULT_IMAGE_TYPE = BufferedImage.TYPE_INT_ARGB; 
	
	private static String[] filepaths = {	"img/player_ul.png","img/player_u.png","img/player_ur.png",
											"img/player_l.png",                    "img/player_r.png",
											"img/player_dl.png","img/player_d.png","img/player_dr.png",
											
											"img/island.png", "img/whirlpool.png", "img/wreckage.png",
											"img/cannonball.png","","","","","","","","",
											
											"img/pirate_ul.png","img/pirate_u.png","img/pirate_ur.png",
											"img/pirate_l.png",                    "img/pirate_r.png",
											"img/pirate_dl.png","img/pirate_d.png","img/pirate_dr.png"};
	
	private static Color[] colors = {Color.BLACK, new Color(0x00,0x66,0xFF)};
	
	
	
	public Frame()
	{
		super();
		
		initFrame();
		initImage();
	}
	
	private void initFrame()
	{
		this.setTitle(FRAME_TITLE);
		this.setResizable(FRAME_RESIZABLE);
		this.setUndecorated(FRAME_UNDECORATED);
		
		this.getContentPane().setPreferredSize(new Dimension(FRAME_WIDTH,FRAME_HEIGHT));
		this.pack();
		this.setLocation(FRAME_X,FRAME_Y);
		
		this.setDefaultCloseOperation(FRAME_DEFAULT_CLOSE_OPERATION);
		
		this.setVisible(FRAME_VISIBLE);
	}
	
	private void initImage()
	{
		image = new BufferedImage(FRAME_WIDTH, FRAME_HEIGHT, DEFAULT_IMAGE_TYPE);
		graphics = image.getGraphics();
	}
	
	public void paint(Graphics g)
	{
		if(image != null)
			g.drawImage(image, this.getInsets().left, this.getInsets().top, null);
	}
	

	public void drawColorTile(int x, int y, int color) 
	{
		graphics.setColor(colors[color]);
		graphics.fillRect(x*Global.TILE_SIZE, y*Global.TILE_SIZE, Global.TILE_SIZE, Global.TILE_SIZE);
	}

	public void drawImageTile(int x, int y, int image) 
	{	
		graphics.drawImage(getImage(image), x, y, null);
	}
	
		private BufferedImage getImage(int image)
		{
			try {
				return (BufferedImage)ImageIO.read(new File(filepaths[image]));
			}
			catch (IOException e){
				e.printStackTrace();
			}
			return null;
		}

	@Override
	public void clear() 
	{
		graphics.setColor(CLEAR_COLOR);
		graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
	}
	
	

}
