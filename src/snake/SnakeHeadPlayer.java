package snake;

public class SnakeHeadPlayer extends SnakeHead {
	public SnakeHeadPlayer(GameArea world) {
		super(world, SnakeSprites.player);
	}

	public void setDirection(Direction d) {
		direction = d;
	}
}
