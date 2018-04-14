package snake;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.net.URL;

import javax.imageio.ImageIO;

import snake.SnakeHead.AbilityMode;
import snake.SnakeSprites.BodySprites;

public class SnakeBody extends GameObject{
	public SnakeHead getOwner() {
		return owner;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public AbilityMode getAbility() {
		return ability;
	}
	private SnakeHead owner;
	
	private int posX;
	private int posY;
	private int width;
	private int height;
	AbilityMode ability;
	public SnakeBody(SnakeHead owner, int x, int y, AbilityMode ability) {
		this.owner = owner;
		this.posX = x;
		this.posY = y;
		width = 16;
		height = 16;
		this.ability = ability;
	}
	public void draw(Graphics window)
	{
/*		window.setColor(Color.green);
		window.fillRect(xPos, yPos, 16, 16);*/
		BodySprites s;
		switch(ability) {
		case TUNNELLING:
			s = BodySprites.BODY;
			break;
		default:
			s = BodySprites.BODY_TUNNELLING;
			break;
		}
		Image i = owner.getSprites().getBodySprite(s);
		window.drawImage(i, posX, posY, width, height, null);
	}
}
