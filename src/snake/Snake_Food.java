package snake;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.imageio.ImageIO;


public class Snake_Food extends GameObject {
	
	String imgBase;
	Image img;
	int width = 16;
	int height = 16;
	public Snake_Food()
	{
		this(0, 0, "Snake_Food.png");
	}
	
	public Snake_Food(int x, int y, String ib)
	{
		setPos(x, y);
		setImgBase(ib);
		updateImage();
	}
	public void setImgBase(String base) {
		imgBase = base;
	}
	public void updateImage() {
		try
		{
			URL url = getClass().getResource(imgBase);
			img = ImageIO.read(url);
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
		window.drawImage(img, posX, posY, width, height, null);
	}
}
