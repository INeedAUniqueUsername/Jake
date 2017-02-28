package Deprecated;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.imageio.ImageIO;


public class Enemy_Snake_Body_DEPRECATED {
	Image image;
	
	
	int lifeSpan;
	//LEGACY
	
	int xPos;
	int yPos;
	int width;
	int height;
	String tunnelling = "";
	
	public Enemy_Snake_Body_DEPRECATED(int x, int y) //int lifeTimer
	{
		xPos = x;
		yPos = y;
		width = 16;
		height = 16;
		//lifeSpan = lifeTimer;
		String img = "Enemy/Body/Enemy_Snake_Body_" + tunnelling + ".png";
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
/*		window.setColor(Color.green);
		window.fillRect(xPos, yPos, 16, 16);*/
		window.drawImage(image, xPos, yPos, width, height, null);
	}
	
}
