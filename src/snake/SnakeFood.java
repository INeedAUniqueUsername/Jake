package snake;
import java.awt.Graphics;
import java.awt.Image;


public class SnakeFood extends GameObject {
	int width = 16;
	int height = 16;
	public static final Image sprite = SnakeSprites.load(SnakeSprites.DIR + "food.png");
	public SnakeFood() {
		this(0, 0);
	}
	public SnakeFood(int x, int y) {
		setPos(x, y);
	}
	public void draw(Graphics window) {
/*		window.setColor(Color.yellow);
		window.fillRect(xPos, yPos, 16, 16);*/
		window.drawImage(sprite, posX, posY, width, height, null);
	}
	public void onCollision(GameObject o) {}
	@Override
	public void update(int time) {
		// TODO Auto-generated method stub
		
	}
}
