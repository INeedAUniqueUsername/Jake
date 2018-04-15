package snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JPanel;

import snake.SnakeHead.Ability;
import snake.SnakeHead.Direction;
import snake.SnakeSprites.BodySprites;

public class GameArea extends JPanel implements KeyListener, Runnable {
	private static final long serialVersionUID = 1L;
	private int score = 0;
	//private int timer = 0;
	int timeInterval = 25;
	private int gameWidth;
	private int gameHeight;
	private ArrayList<GameObject> objects;
	private ArrayList<GameObject> created;
	private SnakeHeadPlayer player;
	
	private final Font FONT;
	
/*	Food[] foodzlist;*/

	public static int nextInt(int max) {
		return (int)(Math.random()*max);
	}
	
	public int randomX() {
		return nextInt(gameWidth/16)*16;
	}
	public int randomY() {
		return nextInt(gameHeight/16)*16;
	}
	public int round(int n, int interval) {
		return interval * (n/interval);
	}
	
	public void reset() {
		//timer = 0;
		score = 0;
		player = new SnakeHeadPlayer(this);
		player.setPos(new Point(round(gameWidth/2, 16), round(gameHeight/2, 16)));
		objects = new ArrayList<>();
		objects.add(player);
		objects.add(createFood());
		objects.add(createEnemy());
		created = new ArrayList<>();
	}
	public SnakeFood createFood() {
		return new SnakeFood(randomX(), randomY());
	}
	public SnakeHeadEnemy createEnemy() {
		SnakeHeadEnemy e = new SnakeHeadEnemy(this);
		e.setPos(randomX(), randomY());
		return e;
	}
	public GameArea(int gameWidth, int gameHeight) {
		this.addKeyListener(this);
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
		Font f = null;
		try {
			InputStream input = getClass().getResourceAsStream("/Inconsolata-Regular.ttf");
			f = Font.createFont(Font.TRUETYPE_FONT, input).deriveFont(Font.BOLD, 16);
		} catch (FontFormatException | IOException e) {
			System.out.println("Using default font");
			f = new Font("Consolas", Font.PLAIN, 12);
			e.printStackTrace();
		}
		FONT = f;
		
		this.gameWidth = gameWidth;
		this.gameHeight = gameHeight;
		reset();
		setVisible(true);
		new Thread(this).start();

	}

	public void updateCharacters() {
		for(Iterator<GameObject> i = objects.iterator(); i.hasNext();) {
			GameObject o = i.next();
			/*
			while(o.getPosX() < 0) { o.incPosX(gameWidth); }
			while(o.getPosX() >= gameWidth) { o.incPosX(-gameWidth); }
			
			while(o.getPosY() < 0) { o.incPosY(gameHeight); }
			while(o.getPosY() >= gameHeight) { o.incPosY(-gameHeight); }
			*/
			o.update(timeInterval);
			if(o.getPosX() < 0 || o.getPosX() >= gameWidth || o.getPosY() < 0 || o.getPosY() >= gameHeight) {
				o.destroy();
				i.remove();
			}
		}
		
		/*
		for(Iterator<GameObject> o_i_1 = objects.iterator(); o_i_1.hasNext();) {
			GameObject o_1 = o_i_1.next();
			i++;
			for(Iterator<GameObject> o_i_2 = objects.subList(i, objects.size()).iterator(); o_i_2.hasNext();) {
				GameObject o_2 = o_i_2.next();
				o_1.onCollision(o_2);
				o_2.onCollision(o_1);
			}
			if(!o_1.getActive()) {
				dead.add(o_1);
			}
		}
		*/
		GameObjectList[][] grid = new GameObjectList[gameWidth/16+4][gameHeight/16+4];
		ArrayList<List<GameObject>> collisions = new ArrayList<List<GameObject>>();
		for(GameObject o : objects) {
			int x = 1+o.getPosX()/16, y = 1+o.getPosY()/16;
			if(grid[x][y] == null) {
				collisions.add(grid[x][y] = new GameObjectList());
			}
			grid[x][y].add(o);
		}
		for(List<GameObject> tile : collisions) {
			int n = 1;
			for(Iterator<GameObject> i1 = tile.iterator(); i1.hasNext(); n++) {
				GameObject o1 = i1.next();
				for(Iterator<GameObject> i2 = tile.subList(n, tile.size()).iterator(); i2.hasNext();) {
					GameObject o2 = i2.next();
					if(o1.getPos().equals(o2.getPos())) {
						o1.onCollision(o2);
						o2.onCollision(o1);
					}
				}
			}
		}
		/*
		for(List<GameObject>[] column : grid) {
			for(List<GameObject> tile : column) {
				if(tile != null && tile.size() > 1) {
					int n = 1;
					for(Iterator<GameObject> i1 = tile.iterator(); i1.hasNext(); n++) {
						GameObject o1 = i1.next();
						for(Iterator<GameObject> i2 = tile.subList(n, tile.size()).iterator(); i2.hasNext();) {
							GameObject o2 = i2.next();
							if(o1.getPos().equals(o2.getPos())) {
								o1.onCollision(o2);
								o2.onCollision(o1);
							}
						}
					}
				}
			}
		}
		*/
		ArrayList<GameObject> dead = new ArrayList<>();
		objects.removeIf(o -> {
			boolean result = !o.getActive();
			if(result) {
				dead.add(o);
			}
			return result;
		});
		
		for(GameObject o : created) {
			objects.add(o);
		}
		created.clear();
	}
	public void paint(Graphics g) {
		updateCharacters();
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, gameWidth, gameHeight);
		
		//timer += 1;
		//draw score
		g.setColor(Color.black);
		
		g.setFont(FONT);
		int x = 24;
		int y = 24;
		g.drawString("Press arrow keys to turn", x, y);	y += 24;
		g.drawString("Press Space to run", x, y);		y += 24;
		g.drawString("Press Shift to tunnel", x, y);	y += 24;
		g.drawString("Eat the yellow food", x, y);	y += 24;
		g.drawString("Score: " + score, x, y);
		y += 8;
		g.setColor(Color.BLACK);
		/*
		g.drawRect(x, y, 240, 15);
		int pieces = (15 * player.getAbilityTime() / player.getAbilityTimeMax());
		for(int i = 0; i < pieces; i++) {
			g.drawImage(SnakeSprites.sprites_player.getBodySprite(BodySprites.BODY), x + i*16, y, null);
		}
		*/
		
		int barLength = player.getAbilityTimeMax() / player.getMoveInterval();
		g.drawRect(x, y, 16*barLength, 15);
		int segments = barLength * player.getAbilityTime() / player.getAbilityTimeMax();
		for(int i = 0; i < segments; i++) {
			g.drawImage(SnakeSprites.player.getBodySprite(BodySprites.BODY), x + i*16, y, null);
		}
		
		Iterator<GameObject> o_i = objects.iterator();
		while(o_i.hasNext()) {
			GameObject o = o_i.next();
			o.draw(g);
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_UP:
			if(player.directionCheck(Direction.UP)) {
				player.setDirection(Direction.UP);
			}
			break;
		case KeyEvent.VK_DOWN:
			if(player.directionCheck(Direction.DOWN)) {
				player.setDirection(Direction.DOWN);
			}
			break;
		case KeyEvent.VK_RIGHT:
			if(player.directionCheck(Direction.RIGHT)) {
				player.setDirection(Direction.RIGHT);
			}
			break;
		case KeyEvent.VK_LEFT:
			if(player.directionCheck(Direction.LEFT)) {
				player.setDirection(Direction.LEFT);
			}
			break;
		case KeyEvent.VK_SPACE:
			player.activateAbility(Ability.RUNNING);
			break;
		case KeyEvent.VK_SHIFT:
			player.activateAbility(Ability.TUNNELLING);
			break;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}
	public void onFoodEaten(SnakeHead eater, SnakeFood eaten) {
		if(eater == player) {
			score++;
		}
		objects.add(createFood());
		if(Math.random() < 0.1 && objects.stream().filter(o -> o instanceof SnakeFood).count() < 10) {
			objects.add(createFood());
		}
		if(Math.random() < 0.1 || objects.stream().filter(o -> o instanceof SnakeHeadEnemy).count() < 1) {
			objects.add(createEnemy());
		}
	}
	public void create(GameObject o) {
		created.add(o);
	}
	public SnakeHeadPlayer getPlayer() {
		return player;
	}
	public ArrayList<GameObject> getObjects() {
		return objects;
	}
	@Override
	public void run() {
		try {
			while (true) {
				Thread.sleep(timeInterval);
				repaint();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
class GameObjectList extends ArrayList<GameObject> {
	private static final long serialVersionUID = 1L;
	
}