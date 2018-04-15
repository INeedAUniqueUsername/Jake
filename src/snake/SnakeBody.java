package snake;
import java.awt.Graphics;
import java.awt.Image;
import snake.SnakeHead.Ability;
import snake.SnakeSprites.BodySprites;

public class SnakeBody extends GameObject implements SnakeObject {
	public SnakeHead getOwner() {
		return owner;
	}
	public Ability getAbility() {
		return ability;
	}
	private SnakeHead owner;
	
	private int width;
	private int height;
	Ability ability;
	public SnakeBody(SnakeHead owner, int x, int y, Ability ability) {
		this.owner = owner;
		setPos(x, y);
		width = 16;
		height = 16;
		this.ability = ability;
	}
	public void draw(Graphics window) {
/*		window.setColor(Color.green);
		window.fillRect(xPos, yPos, 16, 16);*/
		BodySprites s;
		switch(ability) {
		case TUNNELLING:
			s = BodySprites.BODY_TUNNELLING;
			break;
		default:
			s = BodySprites.BODY;
			break;
		}
		Image i = owner.getSprites().getBodySprite(s);
		window.drawImage(i, posX, posY, width, height, null);
	}
	@Override
	public void onCollision(GameObject o) { }
	@Override
	public void update(int timeInterval) {
		/*
		if(!owner.getActive()) {
			setActive(false);
		}
		*/
	}
	public void destroy() {
		owner.destroy();
	}
	@Override
	public SnakeHead getHead() {
		return owner;
	}
}
