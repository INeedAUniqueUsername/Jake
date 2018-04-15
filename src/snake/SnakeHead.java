package snake;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.LinkedList;

import snake.SnakeHead.Direction;
import snake.SnakeSprites.HeadSprites;

public class SnakeHead extends GameObject implements SnakeObject {
	public Direction getDirection() {
		return direction;
	}
	public int getMoveTimer() {
		return moveTimer;
	}
	public int getMoveInterval() {
		return moveInterval;
	}
	public int getBodyLength() {
		return bodyLength;
	}
	public double getSpeedMultiplier() {
		return speedMultiplier;
	}
	public SnakeSprites getSprites() {
		return sprites;
	}
	public LinkedList<SnakeBody> getBodies() {
		return bodies;
	}
	public Ability getAbility() {
		return ability;
	}
	public int getAbilityTime() {
		return abilityTime;
	}
	public int getAbilityTimeMax() {
		return abilityTimeMax;
	}
	private int width = 16, height = 16;
	public enum Direction {
		RIGHT, UP, LEFT, DOWN
	}
	protected Direction direction;
	protected int moveTimer;
	private int moveInterval;
	protected int bodyLength = 2;
	protected double speedMultiplier;
	private SnakeSprites sprites;
	private LinkedList<SnakeBody> bodies;
	protected boolean abilityReady = true;
	
	enum Ability {
		NONE, RUNNING, TUNNELLING
	}
	protected Ability ability = Ability.NONE;
	private int abilityTime = 2500;
	protected int abilityTimeMax = 2500;
	
	protected GameArea world;
	
	public SnakeHead(GameArea world, SnakeSprites sprites) {
		this.world = world;
		
		ability = Ability.NONE;
		direction = Direction.RIGHT;
		
		this.sprites = sprites;
		
		moveTimer = moveInterval = 150;
		speedMultiplier = 1;
		bodies = new LinkedList<>();
	}
	
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
			s = HeadSprites.RIGHT;
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
	public void activateAbility(Ability activate)
	{
		if(abilityReady) {
			if(ability != Ability.NONE) {
				abilityTime = Math.max(0, abilityTime - 600);
			}
			if(ability == activate) {
				stopAbility();
			} else {
				stopAbility();
				startAbility(activate);
			}
		}
	}
	private void startAbility(Ability next) {
		if(ability == next) return;
		ability = next;
		switch(ability) {
		case RUNNING:
			startRunning();
			break;
		default:
			break;
		}
	}
	private void stopAbility() {
		switch(ability) {
		case RUNNING:
			stopRunning();
			break;
		default:
			break;
		}
		ability = Ability.NONE;
	}
	public void startRunning() {
		speedMultiplier *= 3;
	}
	public void stopRunning() {
		speedMultiplier /= 3;
	}
	public void moveForward() {
		setPos(getPosDirectional(direction));
	}
	public Point getPosDirectional(Direction d) {
		switch(d) {
		case UP:
			return new Point(posX, posY - height);
		case DOWN:
			return new Point(posX, posY + height);
		case RIGHT:
			return new Point(posX + width, posY);
		case LEFT:
			return new Point(posX - width, posY);
		default:
			return getPos();
		}
	}
	public boolean directionCheck(Direction d) {
		/*
		System.out.println(bodies.getFirst().getPos());
		System.out.println(getPosDirectional(d));
		*/
		return !bodies.getFirst().getPos().equals(getPosDirectional(d));
	}
	@Override
	public void onCollision(GameObject o) {
		if(o instanceof SnakeObject) {
			SnakeObject other = (SnakeObject) o;
			
			boolean tunnelling1 = ability == Ability.TUNNELLING;
			boolean tunnelling2 = other.getAbility() == Ability.TUNNELLING;
			if(tunnelling1 == tunnelling2) {
				destroy();
			}
		} else if(o instanceof SnakeFood) {
			bodyLength++;
			abilityTimeMax += 250;
			speedMultiplier += 0.1;
			o.setActive(false);
			world.onFoodEaten(this, (SnakeFood) o);
		}
	}
	public void destroy() {
		setActive(false);
		bodies.forEach(b -> b.setActive(false));
	}
	@Override
	public void update(int timeInterval) {
		moveTimer -= timeInterval * speedMultiplier;
		if(moveTimer <= 0) {
			updateMove();
		}
	}
	public void updateMove() {
		moveTimer = moveInterval;
		switch(ability) {
		case NONE:
			if(abilityTime >= abilityTimeMax) {
				abilityReady = true;
				abilityTime = abilityTimeMax;
			} else {
				abilityTime += moveInterval;
			}
			break;
		default:
			System.out.println("Running");
			if(abilityTime > 0) {
				abilityTime -= moveInterval;
				/*
				//Smooth running speed test
				if(ability == Ability.RUNNING) {
					int decelTime = (int) (moveInterval * ((speedMultiplier - 1.0)/0.4));
					if(decelTime < abilityTime) {
						speedMultiplier -= 0.4;
					} else {
						speedMultiplier += 0.4;
					}
				}
				*/
			} else {
				if(ability.equals(Ability.RUNNING)) {
					stopRunning();
				}
				ability = Ability.NONE;
				abilityReady = false;
			}
			break;
		}
		SnakeBody b = new SnakeBody(this, posX, posY, ability);
		bodies.addFirst(b);
		world.create(b);
		while(bodies.size() > bodyLength) {
			SnakeBody last = bodies.removeLast();
			last.setActive(false);
		}
		moveForward();
	}
	@Override
	public SnakeHead getHead() {
		return this;
	}
}
