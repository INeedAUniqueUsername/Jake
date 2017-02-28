package Deprecated;
/*import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameArea_Ruins_1 extends JPanel implements KeyListener, Runnable {

	private Player_1_Snake_Head[] playerz;
	private Enemy_Snake_Head[] enemiez;
	private Snake_Food[] foodz;
	String direction = "";
	private boolean[] keys;
	private int score = 0;
	private int randomWidth;
	private int randomHeight;
	private int timer = 0;
	int timeInterval = 200;
	private boolean title;
	private boolean reset;
	private boolean playerLost;
	Random random;
	Food[] foodzlist;

	public int nextInt(int range)
	{
		return (int)(Math.random()*range);
	}
	
	public int randomWidth()
	{
		return nextInt(120)*16;
	}
	public int randomHeight()
	{
		return nextInt(60)*16;
	}
	
	public void reset()
	{
		reset = false;
		
		keys = new boolean[5];
		
		playerz = new Player_1_Snake_Head[1];
		playerz[0] = new Player_1_Snake_Head(16, 16, "RIGHT", 2, 2, 200);
		enemiez = new Enemy_Snake_Head[1];
		enemiez[0] = new Enemy_Snake_Head(randomWidth(), randomHeight(), "LEFT", 1, 10, 200); //1600, 160
		foodz = new Snake_Food[1];
		foodz[0] = new Snake_Food(randomWidth(), randomHeight());
		
		title = true;
		
		this.addKeyListener(this);
		new Thread(this).start();

		setVisible(true);
	}
	
	public GameArea_Ruins_1(JFrame f) {
		reset = false;
		
		keys = new boolean[5];
		
		player = new Player_1_Snake_Head(16, 16, "RIGHT", 2, 2, 200);
		enemiez = new Enemy_Snake_Head[1];
		enemiez[0] = new Enemy_Snake_Head(randomWidth(), randomHeight(), "LEFT", 1, 10, 200); //1600, 160
		foodz = new Snake_Food[1];
		foodz[0] = new Snake_Food(randomWidth(), randomHeight());
		
		title = true;
		
		this.addKeyListener(this);
		new Thread(this).start();

		setVisible(true);
		
		reset();

	}
	
	public void update(Graphics window) {
		paint(window);
	}

	public void paint(Graphics window) {
		
		if(title)
		{
			window.setColor(Color.blue);
			window.fillRect(0, 0, 1920, 1080);
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
			welcome = new Font("Comic Sans MS", Font.PLAIN, 60);
			window.setFont(welcome);
			window.drawString("A minigame developed by Alex C.", 50, 325);
			window.drawString("Press RIGHT ARROW to start or you will DIE instantly!", 50, 500);
			welcome = new Font("Comic Sans MS", Font.PLAIN, 48);
			window.setFont(welcome);
			window.drawString("Don't click with the mouse because it won't work either!", 100, 600);
		}
		else if(reset)
		{
			reset();
			reset = false;
			
			keys = new boolean[5];
			
			//randomWidth = (int)(Math.random()*700);
			//randomHeight = (int)(Math.random()*500);
			
			
			
			player = new Player_1_Snake_Head(16, 16, "RIGHT", 2, 2, 200);
			enemiez = new Enemy_Snake_Head[1];
			enemiez[0] = new Enemy_Snake_Head(1600, 160, "LEFT", 1, 10, 200);
			
			title = true;
		}
		else
		{	
			window.setColor(Color.WHITE);
			window.fillRect(0, 0, 1920, 1080);
			
			timer+=1;
			System.out.println(timer);
			
			for(Snake_Food f: foodz)
			{
				f.draw(window);
			}
			
			//A Unified Algorithm
			for(Player_1_Snake_Head p: playerz)
			{
				p.nextMoveTimer = p.nextMoveTimer - (timeInterval*p.speedMultiplier);
				p.move();
				for(Player_1_Snake_Body b: p.bodies)
				{
					if(!(b == null))
					{
						b.draw(window);
					}
				}
				p.draw(window);
				
				for(Player_1_Snake_Body pb: p.bodies)
				{
					for(Enemy_Snake_Head e: enemiez)
					{
						if(!(e == null))
						{
							if(p.abilityMode.equals(""))
							{
								e.nextMoveTimer = e.nextMoveTimer - (timeInterval*e.speedMultiplier);
								e.posDiffs = new int[2];
								//posDiffs[0] is the horizontal distance between the approximate center of the enemy and the approximate center of the player
								e.posDiffs[0] = Math.abs((e.xPos + (e.width/2)) - (p.xPos + (p.width/2)));
								//posDiffs[1] is the vertical distance between the approximate center of the enemy and the approximate center of the player
								e.posDiffs[1] = Math.abs((e.yPos + (e.height/2)) - (p.yPos + (p.height/2)));
								e.abovePlayer = e.yPos + e.height < p.yPos;
								e.belowPlayer = e.yPos > p.yPos + p.height;
								e.rightToPlayer = e.xPos > p.xPos + p.width;
								e.leftToPlayer = e.xPos + e.width < p.xPos;
							}
							e.move();
							
							for(Enemy_Snake_Body b: e.bodies)
							{
								if(b == null)
								{
									//Do-Nothing Null Repellent
								}
								else
								{
									b.draw(window);
								}
							}
							e.draw(window);
						}
						for(Enemy_Snake_Body eb: e.bodies)
						{
							if(pb.xPos == e.xPos && pb.yPos == e.yPos)
							{
								System.out.println("Just now you lost...");
								playerLost = true;
								reset = true;
							}
						}
					
						for(Snake_Food f: foodz)
						{
							if(f.xPos == p.xPos && f.yPos == p.yPos)
							{
								f = new Snake_Food(randomWidth(), randomHeight());
								p.increaseLength();
							}
							else if(f.xPos == e.xPos && f.yPos == e.yPos)
							{
								f = new Snake_Food(randomWidth(), randomHeight());
								e.increaseLength();
							}
						}	
					
					}
				}
			}
			
			
			
			//draw score
			window.setColor(Color.black);;
			window.drawString("Score: " + score,25,50);
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
		
		if (e.getKeyCode() == KeyEvent.VK_LEFT && playerz[0].dir != "RIGHT") {
			playerz[0].dir = "LEFT";
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT && playerz[0].dir != "LEFT") {
			playerz[0].dir = "RIGHT";
		}
		if (e.getKeyCode() == KeyEvent.VK_UP && playerz[0].dir != "DOWN") {
			playerz[0].dir = "UP";
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN && playerz[0].dir != "UP") {
			playerz[0].dir = "DOWN";
		}
		if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
		{
			reset = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_SHIFT)
		{
			playerz[0].tunnelMode();
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
*/