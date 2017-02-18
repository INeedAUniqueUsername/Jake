import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.net.URL;

import javax.imageio.ImageIO;


public class Player_Snake_Head {
 
	int xPos;
	int yPos;
	int width = 16;
	int height = 16;
	String dir;
	int nextMoveTimer;
	int moveSpeedTimer;
	//int bodyLengthTimer = 250;
	int bodyLength = 2;
	String img = "";
	Image sprite;
	Player_Snake_Body[] bodies;
	Player_Snake_Body[] bodies2;
	//Use bodies.length to refer to the body length
	int speedMultiplier;
	boolean canUseAbility = true;
	String abilityMode = "";
	int abilityTime = 4500000;
	int abilityTimeMax = 4500000;
	boolean canTurn = true;

	//default constructor
	public Player_Snake_Head()
	{
		xPos = 16;
		yPos = 16;
		dir = "RIGHT";
		speedMultiplier = 1;
		Player_Snake_Body[] bodies = new Player_Snake_Body[2];
		moveSpeedTimer = 200;
		nextMoveTimer = 200;
	}
	
	//specific constructor
	public Player_Snake_Head(int x, int y, String d, int sM, int bL, int mST)
	{
		xPos = x;
		yPos = y;
		dir = d;
		speedMultiplier = sM;
		bodies = new Player_Snake_Body[bL];
		moveSpeedTimer = mST;
		nextMoveTimer = mST;
	}
	
	public void draw( Graphics window )
	{
		// Recover Graphics2D
	    Graphics2D g2 = (Graphics2D) window;

/*		window.setColor(Color.green);
		window.fillRect(xPos, yPos, 16, 16);*/
	    String img = "Player/Head/Player_Snake_Head_" + dir + "_" + abilityMode + ".png";
		try
		{
			URL url = getClass().getResource(img);
			sprite = ImageIO.read(url);
		}
		catch(Exception e)
		{
			System.err.println(e);
		}
		
/*		System.out.println(img);*/
		
		window.drawImage(sprite, xPos, yPos, width, height, null);
	    
	}
	
	public void increaseLength()
	{
		bodies2 = new Player_Snake_Body[bodies.length];
		for(int i = 0; i < bodies.length; i++)
		{
			bodies2[i] = bodies[i];
		}
		bodies = new Player_Snake_Body[bodies.length + 1];
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
		}
		else if(canUseAbility == true && action.equals("RUN") && abilityMode.equals(""))
		{
			//System.out.println("Running initiated");
			abilityMode = "RUNNING";
			speedMultiplier = speedMultiplier*3;
			//ADD A PLAYER_1_SNAKE_BODY_TUNNELLING AND ADD CODE TO AVOID COLLIDING WITH THE SNAKE HEAD WHILE TUNNELLING
			//MAKE SURE TUNNELLING SNAKES CANNOT EAT FOOD WHILE TUNNELLING
		}
		else if(!(abilityMode.equals("")))
		{
			if(abilityMode.equals("TUNNELLING"))
			{
				//System.out.println("Tunnelling cancelled");
				abilityMode = "";
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
	
	public void move(){
		
		if(nextMoveTimer <= 0)
		{
			nextMoveTimer = moveSpeedTimer;
			
/*			if(tunnelRegen)
			{
				System.out.println("Tunnel Regenerating: true");
			}
			else
			{
				System.out.println("Tunnel Regenerating: false");
			}*/
			
/*			System.out.println("Tunnel Regeneration: " + tunnelRegenTime);
			System.out.println("Tunnel Time: " + tunnelTime);*/
			
			//Regen is now constant while inactive
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
			
			for(int i = 0; i < bodies.length; i++)
			{
				//Get rid of the oldest
				if(i > 0)
				{
					bodies[i - 1] = bodies[i];
				}
			}
			
			bodies[bodies.length - 1] = new Player_Snake_Body(xPos, yPos, abilityMode); //(bodyLengthTimer*bodies.length)/speedMultiplier, 
			
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
			
/*			System.out.println("x: " + xPos);
			System.out.println("y: " + yPos);*/
			
/*			System.out.println(dir);*/
		}
	}
}
