package snake;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SnakeSprites {
	enum HeadSprites {
		UP,
		DOWN,
		RIGHT,
		LEFT,
		UP_TUNNELLING,
		DOWN_TUNNELLING,
		RIGHT_TUNNELLING,
		LEFT_TUNNELLING,
	};
	enum BodySprites {
		BODY,
		BODY_TUNNELLING,
	};
	protected final Image[] head = new Image[HeadSprites.values().length], body = new Image[BodySprites.values().length];
	public Image getHeadSprite(HeadSprites s) {
		return head[s.ordinal()];
	}
	public Image getBodySprite(BodySprites s) {
		return body[s.ordinal()];
	}
	public static Image load(String path) {
		//System.out.println(f.getAbsolutePath());
		Image i;
		try {
			i = ImageIO.read(SnakeSprites.class.getClassLoader().getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
			i = null;
		}
		return i;
	}
	public static final String DIR = "sprites/";
	public static final SnakeSprites player = new SnakeSprites() {{
		String[] head_path = {
			"player_head_up.png",
			"player_head_down.png",
			"player_head_right.png",
			"player_head_left.png",
			"player_head_up_tunnelling.png",
			"player_head_down_tunnelling.png",
			"player_head_right_tunnelling.png",
			"player_head_left_tunnelling.png",
		};
		for(int i = 0; i < head_path.length; i++) { head[i] = load(DIR + head_path[i]); }
		
		String[] body_path = {
			"player_body.png",
			"player_body_tunnelling.png",
		};
		for(int i = 0; i < body_path.length; i++) { body[i] = load(DIR + body_path[i]); }
	}};
	public static final SnakeSprites enemy = new SnakeSprites() {{
		String[] head_path = {
			"enemy_head_up.png",
			"enemy_head_down.png",
			"enemy_head_right.png",
			"enemy_head_left.png",
		};
		for(int i = 0; i < head_path.length; i++) { head[i] = load(DIR + head_path[i]); }
		
		String[] body_path = {
			"enemy_body.png",
		};
		for(int i = 0; i < body_path.length; i++) { body[i] = load(DIR + body_path[i]); }
	}};
}