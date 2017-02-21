import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.net.URL;

import javax.imageio.ImageIO;

public class Snake_Body {
	public Snake_Head getOwner() {
		return owner;
	}

	public void setOwner(Snake_Head owner) {
		this.owner = owner;
	}
	public String getImgBase() {
		return imgBase;
	}

	public void setImgBase(String imgBase) {
		this.imgBase = imgBase;
	}

	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}
	public int getPosX() {
		return posX;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	public int getPosY() {
		return posY;
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}
	public Point getPos() {
		return new Point(posX, posY);
	}
	public void setPos(int x, int y) {
		setPosX(x);
		setPosY(y);
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
	public Point getDimensions() {
		return new Point(width, height);
	}
	public void setDimensions(int width, int height) {
		setWidth(width);
		setHeight(height);
	}
	public Ability_State getAbility() {
		return ability;
	}

	public void setAbility(Ability_State ability) {
		this.ability = ability;
	}
	private Snake_Head owner;
	private String imgBase;
	private Image img;
	
	private int posX;
	private int posY;
	private int width;
	private int height;
	private enum Ability_State {
		NORMAL, TUNNELLING
	}
	Ability_State ability = Ability_State.NORMAL;
	public Snake_Body(Snake_Head owner, int x, int y, String imgBase, String abilityState) {
		setOwner(owner);
		setPos(x, y);
		setDimensions(16, 16);
		setImgBase(imgBase);
		setAbility(Ability_State.valueOf(abilityState));
		updateImg();
	}
	public void updateImg() {
		try
		{
			String imgName = imgBase + "_" + ability + ".png";
			URL url = getClass().getResource(imgName);
			setImg(ImageIO.read(url));
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
		window.drawImage(img, posX, posY, width, height, null);
	}
}
