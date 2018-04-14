package snake;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

import snake.SnakeSprites.BodySprites;
import snake.SnakeSprites.HeadSprites;

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
	protected Image[] head, body;
	public Image getHeadSprite(HeadSprites s) {
		return head[s.ordinal()];
	}
	public Image getBodySprite(BodySprites s) {
		return body[s.ordinal()];
	}
	public static Image load(String path) {
		Image i;
		try {
			i = ImageIO.read(ClassLoader.getSystemClassLoader().getResource(path));
		} catch (IOException e) {
			i = null;
		}
		return i;
	}
	public static final SnakeSprites sprites_player = new SnakeSprites() {{
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
		head = new Image[head_path.length];
		for(int i = 0; i < head.length; i++) { head[i] = load(head_path[i]); }
		
		String[] body_path = {
			"player_body.png",
			"player_body_tunnelling.png",
		};
		body = new Image[body_path.length];
		for(int i = 0; i < body.length; i++) { body[i] = load(body_path[i]); }
	}};
}