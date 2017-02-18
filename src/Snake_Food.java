import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.imageio.ImageIO;


public class Snake_Food {
	
	String img = "Snake_Food.png";
	Image image;
	int xPos;
	int yPos;
	int width = 16;
	int height = 16;
	public Snake_Food()
	{
		xPos = 960;
		yPos = 512;
		
		try
		{
			URL url = getClass().getResource(img);
			image = ImageIO.read(url);
		}
		catch(Exception e)
		{
			System.err.println(e);
		}
	}
	
	public Snake_Food(int x, int y)
	{
		xPos = x;
		yPos = y;
		
		try
		{
			URL url = getClass().getResource(img);
			image = ImageIO.read(url);
		}
		catch(Exception e)
		{
			System.err.println(e);
		}
	}
	public void draw(Graphics window)
	{
/*		window.setColor(Color.yellow);
		window.fillRect(xPos, yPos, 16, 16);*/
		window.drawImage(image, xPos, yPos, width, height, null);
	}
}
