package snake;
import java.awt.Graphics;
import java.awt.Image;
import java.util.LinkedList;

import snake.SnakeSprites.HeadSprites;

public class SnakeHead extends GameObject {
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public SnakeDirection getDirection() {
		return direction;
	}
	public int getNextMoveTimer() {
		return nextMoveTimer;
	}
	public int getMoveSpeedTimer() {
		return moveSpeedTimer;
	}
	public int getBodyLength() {
		return bodyLength;
	}
	public int getSpeedMultiplier() {
		return speedMultiplier;
	}
	public SnakeSprites getSprites() {
		return sprites;
	}
	public LinkedList<SnakeBody> getBodies() {
		return bodies;
	}
	public AbilityMode getAbility() {
		return ability;
	}
	public int getAbilityTime() {
		return abilityTime;
	}
	public int getAbilityTimeMax() {
		return abilityTimeMax;
	}
	public void setAbilityTimeMax(int abilityTimeMax) {
		this.abilityTimeMax = abilityTimeMax;
	}
	private int posX, posY, width, height;
	private enum SnakeDirection {
		RIGHT, UP, LEFT, DOWN
	}
	private SnakeDirection direction;
	private int nextMoveTimer, moveSpeedTimer, bodyLength = 2, speedMultiplier;
	private SnakeSprites sprites;
	private LinkedList<SnakeBody> bodies;
	private boolean abilityReady = true;
	
	enum AbilityMode {
		NONE, RUNNING, TUNNELLING
	}
	private AbilityMode ability = AbilityMode.NONE;
	private int abilityTime = 4500000, abilityTimeMax = 4500000;
	
	
	public void draw( Graphics window )
	{
		HeadSprites s = null;
		switch(direction) {
		case UP:
			s = HeadSprites.UP;
			break;
		case DOWN:
			s = HeadSprites.DOWN;
			break;
		case RIGHT:
			s = HeadSprites.UP;
			break;
		case LEFT:
			s = HeadSprites.LEFT;
			break;
		}
		
		switch(ability) {
		case TUNNELLING:
			s = HeadSprites.values()[s.ordinal()+4];
			break;
		default:
			break;
		}
		
		Image i = sprites.getHeadSprite(s);
	    window.drawImage(i, posX, posY, width, height, null);  
	}
	public void activateAbility(AbilityMode a)
	{
		if(abilityReady)
		{
			switch(ability) {
			case NONE:
				ability = a;
				break;
			default:
				ability = AbilityMode.NONE;
				break;
			}
		}
	}
	public void activateRunning() {
		speedMultiplier *= 3;
	}
	public void deactivateRunning() {
		speedMultiplier /= 3;
	}
	public void move(){
		
		if(nextMoveTimer <= 0)
		{
			nextMoveTimer = moveSpeedTimer;
			switch(ability) {
			case NONE:
				if(abilityTime >= abilityTimeMax) {
					abilityReady = true;
					abilityTime = abilityTimeMax;
				} else {
					abilityTime += moveSpeedTimer;
				}
				break;
			default:
				if(abilityTime > 0) {
					abilityTime -= moveSpeedTimer;
				} else {
					if(ability.equals(AbilityMode.RUNNING)) {
						deactivateRunning();
					}
					ability = AbilityMode.NONE;
					abilityReady = false;
				}
				break;
			}
			
			bodies.removeFirst();
			bodies.addLast(new SnakeBody(this, posX, posY, ability));
			updatePosition();
		}
	}
	public void updatePosition() {
		switch(direction) {
		case LEFT :
			setPosX(posX - width);
			break;
		case RIGHT :
			setPosX(posX + width);
			break;
		case UP :
			setPosY(posY - height);
			break;
		case DOWN :
			setPosY(posY + height);
			break;
		}
	}
}
