package Deprecated;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.imageio.ImageIO;


public class Player_Snake_Body {
	String img = "Player_1_Snake_Body.png";
	Image image;
	
	
	int lifeSpan;
	//LEGACY
	
	int xPos;
	int yPos;
	int width;
	int height;
	String tunnelling;
	
	public Player_Snake_Body(int x, int y, String tunnelMode) //int lifeTimer
	{
		xPos = x;
		yPos = y;
		width = 16;
		height = 16;
		//lifeSpan = lifeTimer;
		tunnelling = tunnelMode;
		img = "Player/Body/Player_Snake_Body_" + tunnelling + ".png";
		
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
