
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameArea extends JPanel implements KeyListener, Runnable {

	//ADD SOUNDS, RESTART/QUIT, AND ENEMIES
	//ADD SOUND FILES: lose.wav and eat.wav
	
	private int score = 0;
	private int timer = 0;
	int timeInterval = 25;
	private int gameWidth;
	private int gameHeight;
	ArrayList<GameObject> objects;
/*	Food[] foodzlist;*/

	public int nextInt(int max)
	{
		return (int)(Math.random()*max);
	}
	
	public int randomX()
	{
		return nextInt(gameWidth/16)*16;
	}
	public int randomY()
	{
		return nextInt(gameHeight/16)*16;
	}
	
	public void reset()
	{
		timer = 0;
		score = 0;
		this.addKeyListener(this);
	}
	public void setGameDimensions(int w, int h) {
		gameWidth = w;
		gameHeight = h;
	}
	public GameArea(int width, int height) {
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
		setGameDimensions(width, height);
		reset();
		setVisible(true);
		new Thread(this).start();

	}
	public void update(Graphics window) {
		updateCharacters();
		paint(window);
	}
	public void updateCharacters() {
		int i = 0;
		ArrayList<GameObject> dead = new ArrayList<GameObject>();
		Iterator<GameObject> o_i_1 = objects.iterator();
		while(o_i_1.hasNext()) {
			GameObject o_1 = o_i_1.next();
			i++;
			Iterator<GameObject> o_i_2 = objects.subList(i, objects.size()).iterator();
			while(o_i_2.hasNext()) {
				GameObject o_2 = o_i_2.next();
				o_1.onCollision(o_2);
				o_2.onCollision(o_1);
			}
			if(!o_1.getActive()) {
				dead.add(o_1);
			}
		}
		for(GameObject o : objects) {
			objects.remove(o);
		}
	}
	public void paint(Graphics window) {
		window.setColor(Color.WHITE);
		window.fillRect(0, 0, gameWidth, gameHeight);
		
		timer+=1;
		//draw score
		window.setColor(Color.black);;
		window.drawString("Score: " + score,25,50);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
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
