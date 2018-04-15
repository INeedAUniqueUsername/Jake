package snake;
import java.awt.Graphics;
import java.awt.Point;

public abstract class GameObject {
	protected boolean active = true;
	protected int posX = 0, posY = 0;
	public int getPosX() {
		return posX;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	public void incPosX(int posX) {
		this.posX += posX;
	}
	public int getPosY() {
		return posY;
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}
	public void incPosY(int posY) {
		this.posY += posY;
	}
	public Point getPos() {
		return new Point(posX, posY);
	}
	public void setPos(int x, int y) {
		setPosX(x);
		setPosY(y);
	}
	public void setPos(Point pos) {
		setPosX(pos.x);
		setPosY(pos.y);
	}
	public void setActive(boolean a) {
		active = a;
	}
	public boolean getActive() {
		return active;
	}
	public void destroy() {
		setActive(false);
	}
	public abstract void update(int timeInterval);
	public abstract void draw(Graphics g);
	public abstract void onCollision(GameObject o);
}
