import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Snake_Head {
	public int getPosX() {
		return posX;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	public int getPosY() {
		return posY;
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public Snake_Direction getDirection() {
		return direction;
	}
	public void setDirection(Snake_Direction direction) {
		this.direction = direction;
	}
	public int getNextMoveTimer() {
		return nextMoveTimer;
	}
	public void setNextMoveTimer(int nextMoveTimer) {
		this.nextMoveTimer = nextMoveTimer;
	}
	public int getMoveSpeedTimer() {
		return moveSpeedTimer;
	}
	public void setMoveSpeedTimer(int moveSpeedTimer) {
		this.moveSpeedTimer = moveSpeedTimer;
	}
	public int getBodyLength() {
		return bodyLength;
	}
	public void setBodyLength(int bodyLength) {
		this.bodyLength = bodyLength;
	}
	public int getSpeedMultiplier() {
		return speedMultiplier;
	}
	public void setSpeedMultiplier(int speedMultiplier) {
		this.speedMultiplier = speedMultiplier;
	}
	public Image getImg() {
		return img;
	}
	public void setImg(Image img) {
		this.img = img;
	}
	public ArrayList<Snake_Body> getBodies() {
		return bodies;
	}
	public void setBodies(ArrayList<Snake_Body> bodies) {
		this.bodies = bodies;
	}
	public boolean isCanUseAbility() {
		return canUseAbility;
	}
	public void setCanUseAbility(boolean canUseAbility) {
		this.canUseAbility = canUseAbility;
	}
	public Ability_State getAbility() {
		return ability;
	}
	public void setAbility(Ability_State ability) {
		this.ability = ability;
	}
	public int getAbilityTime() {
		return abilityTime;
	}
	public void setAbilityTime(int abilityTime) {
		this.abilityTime = abilityTime;
	}
	public int getAbilityTimeMax() {
		return abilityTimeMax;
	}
	public void setAbilityTimeMax(int abilityTimeMax) {
		this.abilityTimeMax = abilityTimeMax;
	}
	public String getImgBaseHead() {
		return imgBaseHead;
	}
	public void setImgBaseHead(String imgBase) {
		this.imgBaseHead = imgBase;
	}
	public String getImgBaseBody() {
		return imgBaseBody;
	}
	public void setImgBaseBody(String imgBase) {
		this.imgBaseBody = imgBase;
	}
	private int posX, posY, width, height;
	private enum Snake_Direction {
		RIGHT, UP, LEFT, DOWN
	}
	private Snake_Direction direction;
	private int nextMoveTimer, moveSpeedTimer, bodyLength = 2, speedMultiplier;
	private String imgBaseHead = "Player/Head/Player_1_Snake_Head";
	private String imgBaseBody = "Player/Head/Player_1_Snake_Body";
	private Image img;
	private ArrayList<Snake_Body> bodies;
	private boolean canUseAbility = true;
	private enum Ability_State {
		NORMAL, RUNNING, TUNNELLING
	}
	private Ability_State ability = Ability_State.NORMAL;
	private int abilityTime = 4500000, abilityTimeMax = 4500000;
	
	
	public void draw( Graphics window )
	{
	    Graphics2D g2 = (Graphics2D) window;
	    window.drawImage(img, posX, posY, width, height, null);  
	    g2.dispose();
	}
	public void updateImage() {
		try
		{
			String imgName = imgBaseHead + "_" + direction + "_" + ability + ".png";
			URL url = getClass().getResource(imgName);
			setImg(ImageIO.read(url));
		}
		catch(Exception e)
		{
			System.err.println(e);
		}
	}
	public void activateAbility(Ability_State a)
	{
		if(isCanUseAbility())
		{
			setAbility(a.equals(getAbility()) ? Ability_State.NORMAL : a);
		}
	}
	public void activateTunnelling() {
		setSpeedMultiplier(getSpeedMultiplier() * 3);
	}
	public void deactivateTunnelling() {
		setSpeedMultiplier(getSpeedMultiplier() / 3);
	}
	public void move(){
		
		if(getNextMoveTimer() <= 0)
		{
			setNextMoveTimer(getMoveSpeedTimer());
			if(!getAbility().equals(Ability_State.NORMAL)) {
				if(getAbilityTime() > 0) {
					setAbilityTime(getAbilityTime() - getMoveSpeedTimer());
				} else if(getAbilityTime() <= 0) {
					if(getAbility().equals(Ability_State.RUNNING))
					{
						setSpeedMultiplier(getSpeedMultiplier()/3);
					}
					setAbility(Ability_State.NORMAL);
					setCanUseAbility(false);
				}
			}
			else if(getAbility().equals(Ability_State.NORMAL))
			{
				if(getAbilityTime() >= getAbilityTimeMax())
				{
					setCanUseAbility(true);
					setAbilityTime(getAbilityTimeMax());
				}
				else
				{
					setAbilityTime(getAbilityTime() + getMoveSpeedTimer());
				}
			}
			
			bodies.remove(0);
			bodies.add(new Snake_Body(this, posX, posY, imgBaseBody, (!ability.name().equals("RUNNING")) ? ability.name() : "NORMAL"));
			
			updatePosition();
		}
	}
	public void updatePosition() {
		switch(direction) {
		case LEFT :
			setPosX(posX - width);
			break;
		case RIGHT :
			setPosX(posX + width);
			break;
		case UP :
			setPosY(posY - height);
			break;
		case DOWN :
			setPosY(posY + height);
			break;
		}
	}
}
