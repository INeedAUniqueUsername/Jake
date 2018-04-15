package snake;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SnakeHeadEnemy extends SnakeHead {
	private GameObject target;
	enum DirectionType {
		HORIZONTAL, VERTICAL
	}
	public SnakeHeadEnemy(GameArea world) {
		super(world, SnakeSprites.enemy);
	}
	public void update(int timeInterval) {
		moveTimer -= timeInterval * speedMultiplier;
		if(moveTimer <= 0) {
			think();
			
			Point nextPos = getPosDirectional(direction);
			GameObject obstacle = null;
			for(GameObject o : world.getObjects()) {
				if(o.getPos().equals(nextPos) && (obstacle instanceof SnakeObject) && ((SnakeObject) obstacle).getHead() != target) {
					obstacle = o;
					break;
				}
			}
			
			if(target.getPos().distance(getPos()) < 2 && ability == Ability.TUNNELLING) {
				//Resurface now to get the target
				activateAbility(Ability.TUNNELLING);
			} else if(obstacle != null && ability != Ability.TUNNELLING && abilityReady) {
				//Tunnel now to avoid an obstacle
				activateAbility(Ability.TUNNELLING);
			} else if(ability == Ability.NONE && abilityReady) {
				if(Math.random() < 0.2) {
					activateAbility(Ability.TUNNELLING);
				} else if(Math.random() < 0.4) {
					activateAbility(Ability.RUNNING);
				}
			}
			updateMove();
		}
	}
	public void onCollision(GameObject o) {
		if(o instanceof SnakeObject) {
			SnakeObject other = (SnakeObject) o;
			
			boolean tunnelling1 = ability == Ability.TUNNELLING;
			boolean tunnelling2 = other.getAbility() == Ability.TUNNELLING;
			if(tunnelling1 == tunnelling2) {
				destroy();
				o.destroy();
			}
		} else if(o instanceof SnakeFood) {
			bodyLength++;
			abilityTimeMax += 250;
			speedMultiplier += 0.1;
			o.setActive(false);
			world.onFoodEaten(this, (SnakeFood) o);
		}
	}
	private boolean canTarget(GameObject target) {
		return target.getActive() && !(target instanceof SnakeObject && ((SnakeObject) target).getAbility() == Ability.TUNNELLING);
	}
	private void think() {
		if(target != null && !canTarget(target)) {
			target = null;
		}
		if(target == null) {
			SnakeHeadPlayer player = world.getPlayer();
			if(canTarget(player)) {
				target = player;
			}
			List<GameObject> objects = new ArrayList<>(world.getObjects());
			Collections.sort(objects, new Comparator<GameObject>() {
				public int compare(GameObject o1, GameObject o2) {
					double dist1 = o1.getPos().distance(getPos());
					double dist2 = o2.getPos().distance(getPos());
					return dist1 < dist2 ? -1 : dist1 > dist2 ? 1 : 0;
				}
			});
			objects.removeIf(o -> {
				return (o instanceof SnakeHeadEnemy) || (o instanceof SnakeBody && ((SnakeBody) o).getOwner() instanceof SnakeHeadEnemy);
			});
			for(GameObject o : objects) {
				if(canTarget(o)) {
					target = o;
					break;
				}
			}
			if(target == null) {
				System.out.println("Target not found");
				return;
			} else {
				System.out.println(target);
			}
		}
		/*
		DirectionType t = null;
		switch(direction) {
		case UP:
		case DOWN:
			if(Math.abs(target.getPosX() - posX) > Math.abs(target.getPosY() - posY)) {
				think(DirectionType.HORIZONTAL);
			}
			break;
		case RIGHT:
		case LEFT:
			if(Math.abs(target.getPosY() - posY) > Math.abs(target.getPosX() - posX)) {
				think(DirectionType.VERTICAL);
			}
			break;
		}
		*/
		think(true, true);
	}
	/*
	private void think(DirectionType t) {
		if(t == DirectionType.HORIZONTAL) {
			int xDiff = target.getPosX() - posX;
			boolean rightToTarget = xDiff < 0, leftToTarget = xDiff > 0;
			if(rightToTarget) {
				direction = Direction.LEFT;
			} else if(leftToTarget) {
				direction = Direction.RIGHT;
			}
		} else if(t == DirectionType.VERTICAL) {
			int yDiff = target.getPosY() - posY;
			boolean belowTarget = yDiff < 0, aboveTarget = yDiff > 0;
			if(belowTarget) {
				direction = Direction.UP;
			} else if(aboveTarget) {
				direction = Direction.DOWN;
			}
		}
	}
	*/
	private void think(boolean horizontalAllowed, boolean verticalAllowed) {
		//System.out.println("think");
		
		int xDiff = target.getPosX() - posX, yDiff = target.getPosY() - posY;
		int xDist = Math.abs(xDiff), yDist = Math.abs(yDiff);
		DirectionType directionType = null;
		
		boolean shortestFirst = true;
		if(shortestFirst && xDist < yDist && xDist > 0 && horizontalAllowed) {
			directionType = DirectionType.HORIZONTAL;
		} else if(shortestFirst && yDist < xDist && yDist > 0 && verticalAllowed) {
			directionType = DirectionType.VERTICAL;
		} else if(xDist > yDist && horizontalAllowed) {
			directionType = DirectionType.HORIZONTAL;
		} else if(yDist > xDist && verticalAllowed) {
			directionType = DirectionType.VERTICAL;
		} else if(horizontalAllowed && !verticalAllowed) {
			directionType = DirectionType.HORIZONTAL;
		} else if(!horizontalAllowed && verticalAllowed) {
			directionType = DirectionType.VERTICAL;
		} else if(xDist == yDist && horizontalAllowed && verticalAllowed) {
			if((Math.random() * 100) > 50) {
				directionType = DirectionType.HORIZONTAL;
			} else {
				directionType = DirectionType.VERTICAL;
			}
		}
		
		if(directionType == DirectionType.HORIZONTAL) {
			boolean rightToTarget = xDiff < 0, leftToTarget = xDiff > 0;
			if(rightToTarget) {
				if(direction == Direction.RIGHT) {
					think(false, true);	//Turning around is a crime	
				} else {
					direction = Direction.LEFT;
				}
			} else if(leftToTarget) {
				if(direction == Direction.LEFT) {
					think(false, true);	//Turning around is a crime	
				} else {
					direction = Direction.RIGHT;
				}
			}
		} else if(directionType == DirectionType.VERTICAL) {
			boolean belowTarget = yDiff < 0, aboveTarget = yDiff > 0;
			if(belowTarget) {
				if(direction == Direction.DOWN) {
					think(true, false);	//Turning around is a crime	
				} else {
					direction = Direction.UP;
				}
			} else if(aboveTarget) {
				if(direction == Direction.UP) {
					think(true, false); //Turning around is a crime	
				} else {
					direction = Direction.DOWN;
				}
			}
		}
	}
}
