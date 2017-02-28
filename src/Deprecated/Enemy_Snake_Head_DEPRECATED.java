package Deprecated;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.net.URL;

import javax.imageio.ImageIO;


public class Enemy_Snake_Head_DEPRECATED {
 
	int xPos;
	int yPos;
	int width = 16;
	int height = 16;
	String dir;
	int nextMoveTimer;
	int moveSpeedTimer;
//	int bodyLengthTimer = 200;
	int bodyLength = 2;
	String img = "";
	Image sprite;
	Enemy_Snake_Body_DEPRECATED[] bodies;
	Enemy_Snake_Body_DEPRECATED[] bodies2;
	//Use bodies.length to refer to the body length
	int speedMultiplier;
	boolean canUseAbility = true;
	String abilityMode = "";
	int abilityTime = 4500;
	int abilityTimeMax = 4500;
	boolean aboveTarget;
	boolean belowTarget;
	boolean rightToTarget;
	boolean leftToTarget;
	String dirType;
	//int[] posDiffs;
	int horizontalDiff;
	int verticalDiff;
	Player_Snake_Head_DEPRECATED targetPlayer = null;
	Snake_Food targetFood = null;
	String targetType = "";
	int pathMode = 2;
	int numberID;
	String tunnelling = "";

	//default constructor
	public Enemy_Snake_Head_DEPRECATED()
	{
		xPos = 16;
		yPos = 16;
		dir = "RIGHT";
		speedMultiplier = 1;
		Enemy_Snake_Body_DEPRECATED[] bodies = new Enemy_Snake_Body_DEPRECATED[2];
		moveSpeedTimer = 200;
		nextMoveTimer = 200;
	}
	
	//specific constructor
	public Enemy_Snake_Head_DEPRECATED(int x, int y, String d, int sM, int bL, int mST, int ID)
	{
		xPos = x;
		yPos = y;
		dir = d;
		speedMultiplier = sM;
		bodies = new Enemy_Snake_Body_DEPRECATED[bL];
		moveSpeedTimer = mST;
		nextMoveTimer = mST;
		numberID = ID;
	}
	
	public void increaseLength()
	{
		bodies2 = new Enemy_Snake_Body_DEPRECATED[bodies.length];
		for(int i = 0; i < bodies.length; i++)
		{
			bodies2[i] = bodies[i];
		}
		bodies = new Enemy_Snake_Body_DEPRECATED[bodies.length + 1];
		for(int i = 1; i < bodies.length; i++)
		{
			bodies[i] = bodies2[i-1];
		}
	}
	
	public void specialAbility(String action)
	{
		if(canUseAbility == true && action.equals("TUNNEL") && abilityMode.equals(""))
		{
			//System.out.println("Tunnelling initiated");
			abilityMode = "TUNNELLING";
			tunnelling = "TUNNELLING";
		}
		else if(canUseAbility == true && action.equals("RUN") && abilityMode.equals(""))
		{
			//System.out.println("Running initiated");
			abilityMode = "RUNNING";
			speedMultiplier = speedMultiplier*3;
			//ADD A PLAYER_1_SNAKE_BODY_TUNNELLING AND ADD CODE TO AVOID COLLIDING WITH THE SNAKE HEAD WHILE TUNNELLING
			//MAKE SURE TUNNELLING SNAKES CANNOT EAT FOOD WHILE TUNNELLING
		}
		else if(action.equals("") && !(abilityMode.equals("")))
		{
/*			if(abilityMode.equals("TUNNELLING"))
			{
				//System.out.println("Tunnelling cancelled");
			}
			else if(abilityMode.equals("RUNNING"))
			{
				//System.out.println("Running cancelled");
				speedMultiplier = speedMultiplier/3;
			}
			abilityMode = "";*/
			
			if(abilityMode.equals("TUNNELLING"))
			{
				//System.out.println("Tunnelling cancelled");
				abilityMode = "";
				tunnelling = "";
				if(!(action.equals("TUNNEL")))
				{
					specialAbility(action);
				}
			}
			else if(abilityMode.equals("RUNNING"))
			{
				//System.out.println("Running cancelled");
				speedMultiplier = speedMultiplier/3;
				abilityMode = "";
				if(!(action.equals("RUN")))
				{
					specialAbility(action);
				}
			}
		}
	}
	
	public void draw( Graphics window )
	{
		// Recover Graphics2D
	    Graphics2D g2 = (Graphics2D) window;

/*		window.setColor(Color.green);
		window.fillRect(xPos, yPos, 16, 16);*/
	    
		String img = "Enemy/Head/Enemy_Snake_Head_" + dir + "_" + tunnelling + ".png";
		try
		{
			URL url = getClass().getResource(img);
			sprite = ImageIO.read(url);
		}
		catch(Exception e)
		{
			System.err.println(e);
		}
		
/*		//System.out.println(img);*/
		
		window.drawImage(sprite, xPos, yPos, width, height, null);
	    
	}
	
	public void think(boolean horizontalAllowed, boolean verticalAllowed)
	{
		if(pathMode == 1)
		{
			if(horizontalDiff > verticalDiff && horizontalAllowed)
			{
				//dirType = "HORIZONTAL";
				thinkHorizontal();
				//System.out.println("Enemy ("+pathMode+") moving horizontally; position");
			}
			else if(horizontalAllowed && !verticalAllowed)
			{
				//dirType = "HORIZONTAL";
				thinkHorizontal();
				//System.out.println("Enemy ("+pathMode+") moving horizontally; restriction");
			}
			else if(verticalDiff > horizontalDiff && verticalAllowed)
			{
				//dirType = "VERTICAL";
				thinkVertical();
				//System.out.println("Enemy ("+pathMode+") moving vertically; position");
			}
			else if(!horizontalAllowed && verticalAllowed)
			{
				//dirType = "VERTICAL";
				thinkVertical();
				//System.out.println("Enemy ("+pathMode+") moving vertically; restriction");
			}
			else if(horizontalDiff == verticalDiff && horizontalAllowed && verticalAllowed)
			{
				if((Math.random() * 100) > 50)
				{
					//dirType = "HORIZONTAL";
					thinkHorizontal();
					//System.out.println("Enemy ("+pathMode+") moving horizontally; random");
				}
				else
				{
					//dirType = "VERTICAL";
					thinkVertical();
					//System.out.println("Enemy ("+pathMode+") moving vertically; random");
				}
			}
		}
		else if(pathMode == 2)
		{
			if(verticalDiff < horizontalDiff && verticalDiff > 0 && verticalAllowed)
			{
				//dirType = "VERTICAL";
				thinkVertical();
				//System.out.println("Enemy (2) moving horizontally; position");
			}
			else if(horizontalDiff > 0 && verticalDiff == 0 && horizontalAllowed)
			{
				thinkHorizontal();
				specialAbility("RUN");
				//System.out.println("Enemy (2) moving horizontally; no vertical difference");
			}
			else if(!horizontalAllowed && verticalAllowed)
			{
				//dirType = "VERTICAL";
				thinkVertical();
				//System.out.println("Enemy (2) moving vertically; restriction");
			}
			
			else if(horizontalDiff < verticalDiff && horizontalDiff > 0 && horizontalAllowed)
			{
				//dirType = "VERTICAL";
				thinkHorizontal();
				//System.out.println("Enemy (2) moving vertically; position");
			}
			else if(verticalDiff > 0 && horizontalDiff == 0 && verticalAllowed)
			{
				thinkVertical();
				specialAbility("RUN");
				//System.out.println("Enemy (2) moving vertical; no horizontal difference");
			}
			else if(horizontalAllowed && !verticalAllowed)
			{
				//dirType = "HORIZONTAL";
				thinkHorizontal();
				//System.out.println("Enemy (2) moving horizontally; restriction");
			}
			
			else if(horizontalDiff == verticalDiff && horizontalAllowed && verticalAllowed)
			{
				if((Math.random() * 100) > 50)
				{
					//dirType = "HORIZONTAL";
					thinkHorizontal();
					//System.out.println("Enemy (2) moving horizontally; random");
				}
				else
				{
					//dirType = "VERTICAL";
					thinkVertical();
					//System.out.println("Enemy (2) moving vertically; random");
				}
			}
		}
	}
	
	public void thinkHorizontal()
	{
		//if(dirType.equals("HORIZONTAL"))
		//{
			if(rightToTarget)
			{
				if(dir.equals("RIGHT"))
				{
					think(false, true);	//Turning around is a crime
					//System.out.println("Enemy is right to target; turning around");
				}
				else
				{
					dir = "LEFT";
					//System.out.println("Enemy is right to target; turning left");
				}
			}
			else if(leftToTarget)
			{
				if(dir.equals("LEFT"))
				{
					think(false, true);	//Turning around is a crime
					//System.out.println("Enemy is left to target; turning around");
				}
				else
				{
					dir = "RIGHT";
					//System.out.println("Enemy is left to target; turning right");
				}
			}
			else if(rightToTarget == leftToTarget)
			{
				if(dir.equals("RIGHT") || dir.equals("LEFT"))
				{
					think(false, true);
				}
				
				else if((Math.random() * 100) > 50)
				{
					dir = "RIGHT";
					//System.out.println("Enemy is vertically aligned with target; moving right");
				}
				else
				{
					dir = "LEFT";
					//System.out.println("Enemy is vertically aligned with target; moving left");
				}
			}
		//}
	}
	public void thinkVertical()
	{
		//else if(dirType.equals("VERTICAL"))
		//{
			if(aboveTarget)
			{
				if(dir.equals("UP"))
				{
					think(true, false); //Turning around is a crime
					//System.out.println("Enemy is above target; turning around");
				}
				else
				{
					dir = "DOWN";
					//System.out.println("Enemy is above target; turning down");
				}
			}
			else if(belowTarget)
			{
				if(dir.equals("DOWN"))
				{
					think(true, false);	//Turning around is a crime
					//System.out.println("Enemy is below target; turning around");
				}
				else
				{
					dir = "UP";
					//System.out.println("Enemy is below target; turning up");
				}
			}
			else if(aboveTarget == belowTarget)
			{
				if(dir.equals("UP") || dir.equals("DOWN"))
				{
					think(true, false);
				}
				else if((Math.random() * 100) > 50)
				{
					dir = "UP";
					//System.out.println("Enemy is horizontally aligned with target; moving up");
				}
				else
				{
					dir = "DOWN";
					//System.out.println("Enemy is horizontally aligned with target; moving down");
				}
			}
		//}
	}
	
	public void move(){
		
/*		//System.out.println(nextMoveTimer);
		//System.out.println(moveSpeedTimer);*/
		
		if(nextMoveTimer <= 0)
		{
			nextMoveTimer = moveSpeedTimer;
			
			if(abilityTime > 0 && !(abilityMode.equals("")))
			{
				abilityTime = abilityTime - moveSpeedTimer;
			}
			else if(abilityTime <= 0 && !(abilityMode.equals("")))
			{
				if(abilityMode.equals("RUNNING"))
				{
					speedMultiplier = speedMultiplier/3;
				}
				else if(abilityMode.equals("TUNNELLING"))
				{
					tunnelling = "";
				}
				abilityMode = "";
				canUseAbility = false;
			}
			else if(abilityMode.equals(""))
			{
				if(abilityTime >= abilityTimeMax)
				{
					canUseAbility = true;
					abilityTime = abilityTimeMax;
				}
				else
				{
					//System.out.println("Regen diff: " + (moveSpeedTimer));
					abilityTime = abilityTime + moveSpeedTimer;
				}
			}
			
			think(true, true);
			
			for(int i = 0; i < bodies.length; i++)
			{
				//Get rid of the oldest
				if(i > 0)
				{
					bodies[i - 1] = bodies[i];
				}
			}
			
			bodies[bodies.length - 1] = new Enemy_Snake_Body_DEPRECATED(xPos, yPos); //bodyLengthTimer*bodies.length)/speedMultiplier
			
			if(dir.equals("LEFT"))
			{
				xPos = xPos - 16;
			}
			else if(dir.equals("RIGHT"))
			{
				xPos = xPos + 16;
			}
			else if(dir.equals("UP"))
			{
				yPos = yPos - 16;
			}
			else if(dir.equals("DOWN"))
			{
				yPos = yPos + 16;
			}
			
/*			//System.out.println("x: " + xPos);
			//System.out.println("y: " + yPos);*/
			
/*			//System.out.println(dir);*/
			
		}
	}
}
