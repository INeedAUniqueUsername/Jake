package Deprecated;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Snake_Food;

public class GameArea_DEPRECATED extends JPanel implements KeyListener, Runnable {

	//ADD SOUNDS, RESTART/QUIT, AND ENEMIES
	//ADD SOUND FILES: lose.wav and eat.wav
	
	private Player_Snake_Head_DEPRECATED[] playerz;
	private Enemy_Snake_Head_DEPRECATED[] enemiez;
	private Snake_Food[] foodz;
	String direction = "";
	private boolean[] keys;
	private int score = 0;
	private int randomWidth;
	private int randomHeight;
	private int timer = 0;
	int timeInterval = 25;
	private boolean title;
	private boolean reset;
	private boolean playersLost;
	Random random;
	private JFrame FRAME;
	private int gameWidth;
	private int gameHeight;
	int exitTimer;
	int enemyTotal = 0;
	int foodTotal = 0;
	int enemyNumberStarting = 1;
	int foodNumberStarting = 1;
	int players = 2;
/*	Food[] foodzlist;*/

	public int nextInt(int range)
	{
		return (int)(Math.random()*range);
	}
	
	public int randomWidth()
	{
		return nextInt(gameWidth/16)*16;
	}
	public int randomHeight()
	{
		return nextInt(gameHeight/16)*16;
	}
	
	public void reset()
	{
		reset = false;
		
		keys = new boolean[5];
		playersLost = false;
		timer = 0;
		score = 0;
		playerz = new Player_Snake_Head_DEPRECATED[players];
		for(int i = 0; i < playerz.length; i++)
		{
			playerz[i] = new Player_Snake_Head_DEPRECATED(randomWidth(), randomHeight(), "RIGHT", 1, 20, 150);
		}
		enemiez = new Enemy_Snake_Head_DEPRECATED[enemyNumberStarting];
		for(int i = 0; i < enemiez.length; i++)
		{
			enemyTotal = enemyTotal + 1;
			enemiez[0] = new Enemy_Snake_Head_DEPRECATED(randomWidth(), randomHeight(), "LEFT", 1, 2, 300, enemyTotal); //1600, 160
		}
		foodz = new Snake_Food[foodNumberStarting];
		for(int i = 0; i < foodz.length; i++)
		{
			foodz[i] = new Snake_Food(randomWidth(), randomHeight());
		}
		
		title = true;
		
		this.addKeyListener(this);

		setVisible(true);
	}
	
	public GameArea_DEPRECATED(JFrame f, int Width, int Height) {
/*		reset = false;
		
		keys = new boolean[5];
		
		player = new Player_1_Snake_Head(16, 16, "RIGHT", 2, 2, 200);
		enemiez = new Enemy_Snake_Head[1];
		enemiez[0] = new Enemy_Snake_Head(randomWidth(), randomHeight(), "LEFT", 1, 10, 200); //1600, 160
		foodz = new Snake_Food[1];
		foodz[0] = new Snake_Food(randomWidth(), randomHeight());
		
		title = true;
		
		this.addKeyListener(this);
		new Thread(this).start();

		setVisible(true);*/
		FRAME = f;
		gameWidth = Width - 16;
		gameHeight = Height - 32;
		
		reset();
		new Thread(this).start();

	}
	
	Clip clip;
	Clip bgSound;
	
	public void playSound(String sndFile) {
		// load sound files
		try {
			// Open an audio input stream.
			java.net.URL url = this.getClass().getClassLoader()
					.getResource(sndFile);
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
			// Get a sound clip resource.
			clip = AudioSystem.getClip();
			// Open audio clip and load samples from the audio input stream.
			clip.open(audioIn);
			clip.start();

		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	
	public void music(String sndFile){
		// load sound files
				try {
					// Open an audio input stream.
					java.net.URL url = this.getClass().getClassLoader()
							.getResource(sndFile);
					AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
					// Get a sound clip resource.
					bgSound = AudioSystem.getClip();
					// Open audio clip and load samples from the audio input stream.
					bgSound.open(audioIn);
					bgSound.loop(100);

				} catch (UnsupportedAudioFileException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (LineUnavailableException e) {
					e.printStackTrace();
				}
		}
	
	
	public void update(Graphics window) {
		paint(window);
	}

	public void paint(Graphics window) {
		
		if(title)
		{
			window.setColor(Color.blue);
			window.fillRect(0, 0, gameWidth, gameHeight);
			window.setColor(Color.white);
			Font welcome = new Font("Comic Sand MS", Font.BOLD, 96);
			window.setFont(welcome);
			String welcomeMessage = "WELCOME!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!";
			window.drawString(welcomeMessage, 50, 100);
			welcome = new Font("Comic Sans MS", Font.PLAIN, 36);
			window.setFont(welcome);
			window.drawString("SNAKEBITE! You are now a green snake trapped in a white box. You need to get the food while enemy", 50, 150);
			window.drawString("snakes try to eat you. Don't try to break out because it will never work! Good luck!", 50, 200);
			window.drawString("Use the arrow keys and ONLY the arrow keys!", 50, 250);
			window.drawString("Press backspace to flee the current round!", 50, 300);
			//window.drawString("Press backspace NOW to quit the whole game!", 50, 350);
			window.drawString("Press Shift to tunnel under and hide from enemies/food", 50, 350);
			window.drawString("Press Space to move fast across a certain number of spaces", 50, 450);
			welcome = new Font("Comic Sans MS", Font.PLAIN, 60);
			window.setFont(welcome);
			window.drawString("A minigame developed by Alex C.", 50, 500);
			window.drawString("Press RIGHT ARROW to start or you will PROBABLY die instantly!", 20, 700);
			welcome = new Font("Comic Sans MS", Font.PLAIN, 48);
			window.setFont(welcome);
			window.drawString("Don't click with the mouse because it won't work either!", 100, 800);
		}
		else if(reset)
		{
			reset();
/*			reset = false;
			
			keys = new boolean[5];
			
			//randomWidth = (int)(Math.random()*700);
			//randomHeight = (int)(Math.random()*500);
			
			
			
			player = new Player_1_Snake_Head(16, 16, "RIGHT", 2, 2, 200);
			enemiez = new Enemy_Snake_Head[1];
			enemiez[0] = new Enemy_Snake_Head(1600, 160, "LEFT", 1, 10, 200);
			
			title = true;*/
		}
		else if(playersLost)
		{
			//playSound("lose.wav");------------------------------------------------------------------------------lose.wav
			window.setColor(Color.red);
			window.fillRect(0, 0, gameWidth, gameHeight);
			window.setColor(Color.white);
			Font welcome = new Font("Comic Sand MS", Font.BOLD, 96);
			window.setFont(welcome);
			String welcomeMessage = "YOU LOST!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!";
			window.drawString(welcomeMessage, 50, 100);
			welcome = new Font("Comic Sans MS", Font.PLAIN, 36);
			window.setFont(welcome);
			window.drawString("You probably tried to bite the red snake or something. They're filled with death, so don't go biting them.", 50, 150);
			window.drawString("Or maybe you accidently bit yourself. Don't bite yourself either.", 50, 200);
			welcome = new Font("Comic Sans MS", Font.PLAIN, 48);
			window.setFont(welcome);
			window.drawString("Because you lost, you now have to close the window to restart", 100, 600);
			window.drawString("Or you can just press backspace to return to the menu", 100, 700);
			//exitTimer = exitTimer + 1;
			//window.drawString("Time left: " + (5000-exitTimer)/1000 + " seconds", 100, 750);
			//System.exit(0);
		}
		else
		{	
			window.setColor(Color.WHITE);
			window.fillRect(0, 0, gameWidth, gameHeight);
			
			timer+=1;
			////System.out.println(timer);
			
			for(Player_Snake_Head_DEPRECATED p: playerz)
			{
				if(!(p == null))
				{
					p.nextMoveTimer = p.nextMoveTimer - (timeInterval*p.speedMultiplier);
					p.move();
					p.draw(window);
					for(Player_Snake_Body_DEPRECATED pb: p.bodies)
					{
						if(!(pb == null))
						{
							pb.draw(window);
						}
					}
					p.canTurn = true;
				}
			}
			
			for(Enemy_Snake_Head_DEPRECATED e: enemiez)
			{
				if(!(e == null))
				{
					
					////System.out.println("Enemy targeting initiated");
					//Find nearest target if current target no longer exists
					enemyTargeting("Enemy", e);
					
					if(e.targetType == "")
					{
						//Find nearest food
						int shortestDistanceFood = 1000000;
						Snake_Food nearestFood = null;
						for(int i_f = 0; i_f < foodz.length; i_f++)
						{
							Snake_Food f = foodz[i_f];
							if(!(f == null))
							{
								int distanceToFood = ((e.xPos - f.xPos)^2 + (e.yPos - f.yPos)^2)^(1/2);
								if(distanceToFood < shortestDistanceFood)
								{
									shortestDistanceFood = distanceToFood;
									nearestFood = f;
								}
							}
						}
						//Find nearest player
						int shortestDistancePlayer = 1000000;
						Player_Snake_Head_DEPRECATED nearestPlayer = null;
						for(int i_p = 0; i_p < playerz.length; i_p++)
						{
							Player_Snake_Head_DEPRECATED p = playerz[i_p];
							if(!(p == null))
							{
								if(!(p.abilityMode.equals("TUNNELLING")))
								{
									int distanceToPlayer = ((e.xPos - p.xPos)^2 + (e.yPos - p.yPos)^2)^(1/2);
									if(distanceToPlayer < shortestDistancePlayer)
									{
										shortestDistancePlayer = distanceToPlayer;
										nearestPlayer = p;
									}
								}
							}
						}
						
						//SET UP TARGET System
						if(nearestFood == null && nearestPlayer == null)
						{
							e.targetType = "";
							//System.out.println("Enemy targets nothing");
						}
						else if(!(nearestFood == null) && !(nearestPlayer == null))
						{
							if(shortestDistanceFood < shortestDistancePlayer)
							{
								e.targetType = "FOOD";
								e.targetFood = nearestFood;
								//System.out.println("Enemy targets food; distance");
							}
							else if(shortestDistancePlayer < shortestDistanceFood)
							{
								e.targetType = "PLAYER";
								e.targetPlayer = nearestPlayer;
								//System.out.println("Enemy targets player; distance");
							}
							else if(shortestDistancePlayer == shortestDistanceFood)
							{
								e.targetType = "FOOD";
								e.targetFood = nearestFood;
								//System.out.println("Enemy targets food; same distances");
							}
						}
						else if(nearestPlayer == null && !(nearestFood == null))
						{
							e.targetType = "FOOD";
							e.targetFood = nearestFood;
							//System.out.println("Enemy targets food; no player");
						}
						else if(nearestFood == null && !(nearestPlayer == null))
						{
							e.targetType = "PLAYER";
							e.targetPlayer = nearestPlayer;
							//System.out.println("Enemy targets player; no food");
						}
						enemyTargeting("Enemy", e);
					}
					e.nextMoveTimer = e.nextMoveTimer - (timeInterval*e.speedMultiplier);
					e.move();
					e.draw(window);
					for(Enemy_Snake_Body_DEPRECATED eb: e.bodies)
					{
						if(!(eb == null))
						{
							eb.draw(window);
						}
					}
				}
			}
			
			for(Snake_Food f: foodz)
			{
				if(!(f == null))
				{
					f.draw(window);
				}
			}
			
			
			
			//Food Collisions
			for(int n = 0; n < foodz.length; n++)
			{
				Snake_Food f = foodz[n];
				if(!(f == null))
				{
					//Food-Wall Collision
					if(f.xPos > gameWidth || f.xPos < 0 || f.yPos > gameHeight || f.yPos < 0)
					{
						foodz[n] = new Snake_Food(randomWidth(), randomHeight());
					}
					//Player-Food Collisions
					for(Player_Snake_Head_DEPRECATED p: playerz)
					{
						if(!(p == null))
						{
							//Player eats food
							if(f.xPos == p.xPos && f.yPos == p.yPos && !(p.abilityMode.equals("TUNNELLING")))
							{
								foodz[n] = new Snake_Food(randomWidth(), randomHeight());
								//playSound("eat.wav");-----------------------------------------------------------------------------------eat.wav
								p.increaseLength();
								p.abilityTimeMax = p.abilityTimeMax + 50;
								score = score + 1;
								Enemy_Snake_Head_DEPRECATED[] enemiez2 = new Enemy_Snake_Head_DEPRECATED[enemiez.length];
								for(int i = 0; i < enemiez.length; i++)
								{
									enemiez2[i] = enemiez[i];
								}
								enemiez = new Enemy_Snake_Head_DEPRECATED[enemiez.length + 1];
								for(int i = 0; i < enemiez2.length; i++)
								{
									enemiez[i] = enemiez2[i];
								}
								//Enemy snakes get longer and faster
								enemyTotal = enemyTotal + 1;
								enemiez[enemiez.length-1] = new Enemy_Snake_Head_DEPRECATED(randomWidth(), randomHeight(), "DOWN", 1, score + 2, 300 - (25*score), enemyTotal);
							}
						}
					}
					//Player-Food Collisions
					for(Enemy_Snake_Head_DEPRECATED e: enemiez)
					{
						if(!(e == null))
						{
							//Enemy eats food
							if(f.xPos == e.xPos && f.yPos == e.yPos) //if(f.xPos == e.xPos && f.yPos == e.yPos && !(e.tunnelMode.equals("TUNNELLING")))  //No tunnelling yet
							{	
								foodz[n] = new Snake_Food(randomWidth(), randomHeight());
								//playSound("eat.wav");-----------------------------------------------------------------------------------eat.wav
								e.increaseLength();
								//e.tunnelTimeMax = p.tunnelTimeMax + 50;
								Enemy_Snake_Head_DEPRECATED[] enemiez2 = new Enemy_Snake_Head_DEPRECATED[enemiez.length];
								for(int i = 0; i < enemiez.length; i++)
								{
									enemiez2[i] = enemiez[i];
								}
						
								int enemyNumber = 0;
								for(int i = 0; i < enemiez.length; i++)
								{
									if(!(enemiez[i] == null))
									{
										enemyNumber = enemyNumber + 1;
									}
								}
						
								enemiez = new Enemy_Snake_Head_DEPRECATED[enemiez.length+1];
								for(int i = 0; i < enemiez2.length; i++)
								{
									enemiez[i] = enemiez2[i];
								}
								//Enemy snakes get longer and faster
								for(int i = 0; i < enemiez.length; i++)
								{
									if(!(enemiez[i] == null))
									{
										//System.out.println("Enemy already exists");
									}
									else
									{
										enemyTotal = enemyTotal + 1;
										enemiez[i] = new Enemy_Snake_Head_DEPRECATED(randomWidth(), randomHeight(), "DOWN", 1, score + 2, 300 - (25*i), enemyTotal);
									}
								}
							}
						}
					}
				}
			}
			//Player-Self Collisions
			for(int ip = 0; ip < playerz.length; ip++)
			{
				Player_Snake_Head_DEPRECATED p = playerz[ip];
				if(!(p == null))
				{
					//Player bites wall
					if(p.xPos > gameWidth || p.xPos < 0 || p.yPos > gameHeight || p.yPos < 0)
					{
						destroyCharacter("PLAYER", ip);
						//System.out.println("Player bites wall");
					}
				
					for(Player_Snake_Body_DEPRECATED pb: p.bodies)
					{
						if(!(pb == null))
						{
							//Player bites itself
							if(p.xPos == pb.xPos && p.yPos == pb.yPos && (p.abilityMode == pb.abilityMode))
							{
								destroyCharacter("PLAYER", ip);
								//System.out.println("Player bites itself");
							}
						}
					}
				}
			}
			//Player-Player Collisions
			for(int ip = 0; ip < playerz.length; ip++)
			{
				Player_Snake_Head_DEPRECATED p = playerz[ip];
				if(!(p == null))
				{
					for(int i_other_p = 0; i_other_p < playerz.length; i_other_p++)
					{
						Player_Snake_Head_DEPRECATED other_p = playerz[i_other_p];
						//Player bites player (head)
						if(!(playerz[i_other_p] == null) && !(playerz[ip] == playerz[i_other_p]))
						{
							if(p.xPos == other_p.xPos && p.yPos == other_p.yPos && p.tunnelling == other_p.tunnelling)
							{
								destroyCharacter("PLAYER", ip);
								destroyCharacter("PLAYER",i_other_p);
								//System.out.println("Player (non-tunnelling) bites other player (non-tunnelling) (head)");
							}
							//Player-Player-Body collisions
							for(Player_Snake_Body_DEPRECATED other_pb: other_p.bodies)
							{
								//Player bites player (body)
								if(!(other_pb == null))
								{
									if(p.xPos == other_pb.xPos && p.yPos == other_pb.yPos && p.tunnelling == other_pb.tunnelling) //UPDATE ALL TUNNELLING CHECKS AND ENEMY TUNNELLING CODE (BE CAREFUL WITH THINKING CODE)
									{
										destroyCharacter("PLAYER", ip);
										//destroyCharacter("PLAYER", i_other_p);
										//System.out.println("Player (non-tunnelling) bites other player (non-tunnelling) (body)");
									}
								}
							}
						}
					}
				}
			}
			//Enemy-Self Collisions
			for(int ie = 0; ie < enemiez.length; ie++)
			{
				Enemy_Snake_Head_DEPRECATED e = enemiez[ie];
				if(!(e == null))
				{	
					//Enemy bites wall
					if(e.xPos > gameWidth || e.xPos < 0 || e.yPos > gameHeight || e.yPos < 0)
					{
						destroyCharacter("ENEMY", ie);
						//System.out.println("Enemy bites wall");
					}
					
					for(Enemy_Snake_Body_DEPRECATED eb: e.bodies)
					{
						if(!(eb == null))
						{
							//Enemy bites itself
							if(e.xPos == eb.xPos && e.yPos == eb.yPos)
							{
								destroyCharacter("ENEMY", ie);
								//System.out.println("Enemy bites itself");
							}
						}
					}
				}
			}
			//Enemy-Enemy Collisions
			for(int ie = 0; ie < enemiez.length; ie++)
			{
				Enemy_Snake_Head_DEPRECATED e = enemiez[ie];
				if(!(e == null))
				{
					for(int i_other_e = 0; i_other_e < enemiez.length; i_other_e++)
					{
						Enemy_Snake_Head_DEPRECATED other_e = enemiez[i_other_e];
						if(!(enemiez[i_other_e] == null) && !(enemiez[ie] == enemiez[i_other_e]))
						{
							//Enemy bites enemy (head)
							if(e.xPos == other_e.xPos && e.yPos == other_e.yPos)
							{
								destroyCharacter("ENEMY", ie);
								destroyCharacter("ENEMY", i_other_e);
								//System.out.println("Enemy bites other enemy (head)");
							}
							
							for(Enemy_Snake_Body_DEPRECATED other_eb: other_e.bodies)
							{
								//Enemy bites enemy (body)
								if(!(other_eb == null))
								{
									if(e.xPos == other_eb.xPos && e.yPos == other_eb.yPos)
									{
										destroyCharacter("ENEMY", ie);
										destroyCharacter("ENEMY", i_other_e);
										//System.out.println("Enemy bites other enemy (body)");
									}
								}
							}
						}
					}
				}
			}
			//Player-Enemy Collision Sets
			for(int ip = 0; ip < playerz.length; ip++)
			{
				Player_Snake_Head_DEPRECATED p = playerz[ip];
				if(!(p == null))
				{
					for(Player_Snake_Body_DEPRECATED pb: p.bodies)
					{
						if(!(pb == null))
						{
							for(int ie = 0; ie < enemiez.length; ie++)
							{
								Enemy_Snake_Head_DEPRECATED e = enemiez[ie];
								if(!(e == null))
								{	
									for(Enemy_Snake_Body_DEPRECATED eb: e.bodies)
									{
										if(!(eb == null))
										{		
											//Enemy bites player (body)
											if(pb.xPos == e.xPos && pb.yPos == e.yPos && !(pb.tunnelling.equals("TUNNELLING")))
											{
												//playerz[ip] = null;
												destroyCharacter("PLAYER", ip);
												//System.out.println("Enemy bites player (body)");
											}
											//Player bites enemy (body)
											if(p.xPos == eb.xPos && p.yPos == eb.yPos && !(p.abilityMode.equals("TUNNELLING")))
											{
												//playerz[ip] = null;
												destroyCharacter("PLAYER", ip);
												//System.out.println("Player bites enemy (body)");
											}
										
											//Player bites enemy (head) or enemy bites player (head)
											if(p.xPos == e.xPos && p.yPos == e.yPos && !(p.abilityMode.equals("TUNNELLING")))
											{
												//playerz[ip] = null;
												destroyCharacter("PLAYER", ip);
												//System.out.println("Player bites enemy (head) or enemy bites player (head)");
											}
										}
									}
								}
							}
						}
					}
				}
			}
			
			int playersDead = 0;
			for(int ip = 0; ip < playerz.length; ip++)
			{
				if(playerz[ip] == null)
				{
					playersDead = playersDead + 1;
					//System.out.println("Player " + ip + " is dead!");
				}
			}
			if(playersDead == playerz.length)
			{
				playersLost = true;
			}
				
			
/*			//System.out.println(foodz[0].xPos);
			//System.out.println(foodz[0].yPos);*/
			
			
			//draw score
			window.setColor(Color.black);;
			window.drawString("Score: " + score,25,50);
			if(!(playerz[0] == null))
			{
				window.drawString("Ability Distance: " + playerz[0].abilityTime/playerz[0].moveSpeedTimer, 25, 65);
				if(playerz[0].canUseAbility)
				{
					window.drawString("Can use ability", 25, 80);
				}
				else
				{
					window.drawString("Cannot use ability", 25, 80);
				}
				window.drawString("Enemies: " + enemiez.length, 25, 95);
				for(int i = 0; i < enemiez.length; i++)
				{
					Enemy_Snake_Head_DEPRECATED e = enemiez[i];
					if(!(e == null))
					{
						window.drawString("Enemy Ability Distance: " + e.abilityTime/e.moveSpeedTimer, 25, 110 + 15*i);
					}
				}
			}
		}
	}
	
	public void enemyTargeting(String enemyType, Enemy_Snake_Head_DEPRECATED e)
	{
		//The enemy is targeting a player
		if(e.targetType.equals("PLAYER"))
		{
			//The player is alive
			if(!(e.targetPlayer == null))
			{
				//The player has a tail
				if(!(e.targetPlayer.bodies[0] == null))
				{
					Player_Snake_Body_DEPRECATED targetPlayerTail = e.targetPlayer.bodies[0];
					int distanceToHead = ((e.xPos - e.targetPlayer.xPos)^2 + (e.yPos - e.targetPlayer.yPos)^2)^(1/2);
					int distanceToTail = ((e.xPos - targetPlayerTail.xPos)^2 + (e.yPos - targetPlayerTail.yPos)^2)^(1/2);
					
					//The player is completely visible
					if(e.targetPlayer.tunnelling.equals("") && targetPlayerTail.tunnelling.equals(""))
					{
						if(distanceToHead < distanceToTail)
						{
							targetPlayerHead(e);
						}
						else
						{
							targetPlayerBody(e, targetPlayerTail);
						}
					}
					//The player is partially visible
					else if(e.targetPlayer.tunnelling.equals("") && targetPlayerTail.tunnelling.equals("TUNNELLING"))
					{
						targetPlayerHead(e);
					}
					else if(e.targetPlayer.tunnelling.equals("TUNNELLING") && targetPlayerTail.tunnelling.equals(""))
					{
						targetPlayerBody(e, targetPlayerTail);
					}
					//The player is invisible
					else
					{
						e.targetType = "";
						e.targetPlayer = null;
						System.out.println("Enemy cannot find target player; player is tunnelling");
					}
					
				}
				//The player has no tail but is still visible
				else if(e.targetPlayer.tunnelling.equals(""))
				{
					targetPlayerHead(e);
				}
				//The player has no tail and is invisible
				else
				{
					e.targetType = "";
					e.targetPlayer = null;
					System.out.println("Enemy cannot find target player; player is tunnelling");
				}
				/*
				if(e.targetPlayer.tunnelling.equals("") && distanceToHead < distanceToTail || e.targetPlayer.tunnelling.equals("") && e.targetPlayer.bodies[0].tunnelling.equals("TUNNELLING"))
				{
					//e.posDiffs = new int[2];
					//posDiffs[0] is the horizontal distance between the approximate center of the enemy and the approximate center of the player
					e.horizontalDiff = Math.abs((e.xPos + (e.width/2)) - (e.targetPlayer.xPos + (e.targetPlayer.width/2)));
					//posDiffs[1] is the vertical distance between the approximate center of the enemy and the approximate center of the player
					e.verticalDiff = Math.abs((e.yPos + (e.height/2)) - (e.targetPlayer.yPos + (e.targetPlayer.height/2)));
					e.aboveTarget = e.yPos + e.height/2 < e.targetPlayer.yPos;
					e.belowTarget = e.yPos > e.targetPlayer.yPos + e.targetPlayer.height/2;
					e.rightToTarget = e.xPos > e.targetPlayer.xPos + e.targetPlayer.width/2;
					e.leftToTarget = e.xPos + e.width/2 < e.targetPlayer.xPos;
				}
				else if(!(e.targetPlayer.bodies[0] == null) && !(e.targetPlayer.bodies[0].equals("TUNNELLING")) && distanceToTail < distanceToHead || !(e.targetPlayer.bodies[0] == null) && e.targetPlayer.tunnelling.equals("TUNNELLING") && e.targetPlayer.bodies[0].tunnelling.equals(""))
				{
					//e.posDiffs = new int[2];
					//posDiffs[0] is the horizontal distance between the approximate center of the enemy and the approximate center of the player
					e.horizontalDiff = Math.abs((e.xPos + (e.width/2)) - (e.targetPlayer.xPos + (e.targetPlayer.width/2)));
					//posDiffs[1] is the vertical distance between the approximate center of the enemy and the approximate center of the player
					e.verticalDiff = Math.abs((e.yPos + (e.height/2)) - (e.targetPlayer.yPos + (e.targetPlayer.height/2)));
					e.aboveTarget = e.yPos + e.height/2 < e.targetPlayer.yPos;
					e.belowTarget = e.yPos > e.targetPlayer.yPos + e.targetPlayer.height/2;
					e.rightToTarget = e.xPos > e.targetPlayer.xPos + e.targetPlayer.width/2;
					e.leftToTarget = e.xPos + e.width/2 < e.targetPlayer.xPos;
				}*/
			}
			//The player is dead
			else
			{
				e.targetType = "";
				e.targetPlayer = null;
				System.out.println("Enemy cannot find target player; does not exist");
			}
		}
		else if(e.targetType.equals("FOOD"))
		{
			if(!(e.targetFood == null))
			{
				//e.posDiffs = new int[2];
				//posDiffs[0] is the horizontal distance between the approximate center of the enemy and the approximate center of the player
				e.horizontalDiff = Math.abs((e.xPos + (e.width/2)) - (e.targetFood.xPos + (e.targetFood.width/2)));
				//posDiffs[1] is the vertical distance between the approximate center of the enemy and the approximate center of the player
				e.verticalDiff = Math.abs((e.yPos + (e.height/2)) - (e.targetFood.yPos + (e.targetFood.height/2)));
				e.aboveTarget = e.yPos + e.height/2 < e.targetFood.yPos;
				e.belowTarget = e.yPos > e.targetFood.yPos + e.targetFood.height/2;
				e.rightToTarget = e.xPos > e.targetFood.xPos + e.targetFood.width/2;
				e.leftToTarget = e.xPos + e.width/2 < e.targetFood.xPos;
			}
			else
			{
				e.targetType = "";
				e.targetFood = null;
				System.out.println("Enemy cannot find target food; does not exist");
			}
		}
	}
	
	public void targetPlayerHead(Enemy_Snake_Head_DEPRECATED e)
	{
		e.horizontalDiff = Math.abs((e.xPos + (e.width/2)) - (e.targetPlayer.xPos + (e.targetPlayer.width/2)));
		e.verticalDiff = Math.abs((e.yPos + (e.height/2)) - (e.targetPlayer.yPos + (e.targetPlayer.height/2)));
		e.aboveTarget = e.yPos + e.height/2 < e.targetPlayer.yPos;
		e.belowTarget = e.yPos > e.targetPlayer.yPos + e.targetPlayer.height/2;
		e.rightToTarget = e.xPos > e.targetPlayer.xPos + e.targetPlayer.width/2;
		e.leftToTarget = e.xPos + e.width/2 < e.targetPlayer.xPos;
	}
	public void targetPlayerBody(Enemy_Snake_Head_DEPRECATED e, Player_Snake_Body_DEPRECATED pb)
	{
		e.horizontalDiff = Math.abs((e.xPos + (e.width/2)) - (pb.xPos + (pb.width/2)));
		e.verticalDiff = Math.abs((e.yPos + (e.height/2)) - (pb.yPos + (pb.height/2)));
		e.aboveTarget = e.yPos + e.height/2 < pb.yPos;
		e.belowTarget = e.yPos > pb.yPos + pb.height/2;
		e.rightToTarget = e.xPos > pb.xPos + pb.width/2;
		e.leftToTarget = e.xPos + e.width/2 < pb.xPos;
	}

	public void destroyCharacter(String type, int i)
	{
		if(type.equals("ENEMY"))
		{
			enemiez[i] = null;/*
			for(int i = ie; i < enemiez.length-1; i++)
			{
				enemiez[i] = enemiez[i+1];
			}
			Enemy_Snake_Head[] enemiezHolder = new Enemy_Snake_Head[enemiez.length-1];
			for(int i = 0; i < enemiezHolder.length; i++)
			{
				enemiezHolder[i] = enemiez[i];
			}
			enemiez = new Enemy_Snake_Head[enemiez.length-1];
			for(int i = 0; i < enemiezHolder.length; i++)
			{
				enemiez[i] = enemiezHolder[i];
			}*/
		}
		if(type.equals("PLAYER"))
		{
			playerz[i] = null;
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		//NO TURNING AROUND
		if(title)
		{
			title = false;
		}
		
		if(!(playerz[0] == null))
		{
			Player_Snake_Head_DEPRECATED player1 = playerz[0];
			if (e.getKeyCode() == KeyEvent.VK_LEFT && player1.dir != "RIGHT" && player1.canTurn) {
				//for(Player_1_Snake_Body pb: playerz[0].bodies)
				//{
					//if(!(pb == null) && !(pb.xPos == playerz[0].xPos - 16) && !(pb.yPos == playerz[0].yPos))
					//{
						player1.dir = "LEFT";
						player1.canTurn = false;
					//}
				//}
			}
			else if (e.getKeyCode() == KeyEvent.VK_RIGHT && player1.dir != "LEFT" && player1.canTurn) {
				//for(Player_1_Snake_Body pb: playerz[0].bodies)
				//{
					//if(!(pb == null) && !(pb.xPos == playerz[0].xPos + 16) && !(pb.yPos == playerz[0].yPos))
					//{
						player1.dir = "RIGHT";
						player1.canTurn = false;
					//}
				//}
				
			}
			else if (e.getKeyCode() == KeyEvent.VK_UP && player1.dir != "DOWN" && player1.canTurn) {
				//for(Player_1_Snake_Body pb: playerz[0].bodies)
				//{
					//if(!(pb == null) && !(pb.xPos == playerz[0].xPos) && !(pb.yPos == playerz[0].yPos - 16))
					//{
						player1.dir = "UP";
						player1.canTurn = false;
					//}
				//}
				
			}
			else if (e.getKeyCode() == KeyEvent.VK_DOWN && player1.dir != "UP" && player1.canTurn) {
				//for(Player_1_Snake_Body pb: playerz[0].bodies)
				//{
					//if(!(pb == null) && !(pb.xPos == playerz[0].xPos) && !(pb.yPos == playerz[0].yPos + 16))
					//{
						player1.dir = "DOWN";
						player1.canTurn = false;
					//}
				//}
			}
			if(e.getKeyCode() == KeyEvent.VK_SHIFT)
			{
				player1.specialAbility("TUNNEL");
			}
			if(e.getKeyCode() == KeyEvent.VK_SPACE)
			{
				player1.specialAbility("RUN");
			}
		}
		
		if(!(playerz[1] == null))
		{
			Player_Snake_Head_DEPRECATED player2 = playerz[1];
			if (e.getKeyCode() == KeyEvent.VK_A && player2.dir != "RIGHT" && player2.canTurn) {
				//for(Player_1_Snake_Body pb: playerz[0].bodies)
				//{
					//if(!(pb == null) && !(pb.xPos == playerz[0].xPos - 16) && !(pb.yPos == playerz[0].yPos))
					//{
						player2.dir = "LEFT";
						player2.canTurn = false;
					//}
				//}
			}
			else if (e.getKeyCode() == KeyEvent.VK_D && player2.dir != "LEFT" && player2.canTurn) {
				//for(Player_1_Snake_Body pb: playerz[0].bodies)
				//{
					//if(!(pb == null) && !(pb.xPos == playerz[0].xPos + 16) && !(pb.yPos == playerz[0].yPos))
					//{
						player2.dir = "RIGHT";
						player2.canTurn = false;
					//}
				//}
				
			}
			else if (e.getKeyCode() == KeyEvent.VK_W && player2.dir != "DOWN" && player2.canTurn) {
				//for(Player_1_Snake_Body pb: playerz[0].bodies)
				//{
					//if(!(pb == null) && !(pb.xPos == playerz[0].xPos) && !(pb.yPos == playerz[0].yPos - 16))
					//{
						player2.dir = "UP";
						player2.canTurn = false;
					//}
				//}
				
			}
			else if (e.getKeyCode() == KeyEvent.VK_S && player2.dir != "UP" && player2.canTurn) {			
				//for(Player_1_Snake_Body pb: playerz[0].bodies)
				//{
					//if(!(pb == null) && !(pb.xPos == playerz[0].xPos) && !(pb.yPos == playerz[0].yPos + 16))
					//{
						player2.dir = "DOWN";
						player2.canTurn = false;
					//}
				//}
			}
			if(e.getKeyCode() == KeyEvent.VK_F)
			{
				player2.specialAbility("TUNNEL");
			}
			if(e.getKeyCode() == KeyEvent.VK_G)
			{
				player2.specialAbility("RUN");
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
		{
			reset = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
		{
			reset = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		try {
			while (true) {
				Thread.currentThread().sleep(timeInterval);

				repaint();
			}
		} catch (Exception e) {
		}

	}
}
