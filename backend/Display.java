package backend;

public interface Display 
{
	public void drawColorTile(int x, int y, int color);
	public void drawImageTile(int x, int y, int image);
	public void clear();
	public void draw();
}
