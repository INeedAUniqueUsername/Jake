import java.awt.Point;

public class GameObject {
	boolean active = true;
	int posX = 0;
	int posY = 0;
	
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
	public void setActive(boolean a) {
		active = a;
	}
	public boolean getActive() {
		return active;
	}
	public void onCollision(GameObject o) {
		
	}
}
