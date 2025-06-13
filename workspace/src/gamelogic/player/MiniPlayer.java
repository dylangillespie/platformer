package gamelogic.player;

import java.awt.Color;
import java.awt.Graphics;

import gameengine.PhysicsObject;
import gameengine.graphics.MyGraphics;
import gameengine.hitbox.RectHitbox;
import gameengine.maths.Vector2D;
import gamelogic.Main;
import gamelogic.level.Level;
import gamelogic.tiles.Tile;

public class MiniPlayer extends PhysicsObject{
	public float walkSpeed = 400;
	public float jumpPower = 1350;

	private boolean isJumping = false;

	public MiniPlayer(float x, float y, Level level) {
	
		super(x, y, level.getLevelData().getTileSize(), level.getLevelData().getTileSize(), level);
		int offset =(int)(level.getLevelData().getTileSize()*0.1); //hitbox is offset by 10% of the player size.
		this.hitbox = new RectHitbox(this, offset,offset, (width/2) -offset, (height/2) - offset);
	}

	public Vector2D getMovement(){
		return movementVector;
	}

	@Override
	public void update(float tslf) {
		super.update(tslf);
		
		movementVector.x = 0;
		if(PlayerInput.isLeftKeyDown()) {
			movementVector.x = -walkSpeed+100;
		}
		if(PlayerInput.isRightKeyDown()) {
			movementVector.x = +walkSpeed+100;
		}
		if(PlayerInput.isJumpKeyDown() && !isJumping) {
			movementVector.y = -jumpPower+100;
			isJumping = true;
		}
		
		isJumping = true;
		if(collisionMatrix[BOT] != null) isJumping = false;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.YELLOW);
		MyGraphics.fillRectWithOutline(g, (int)getX(), (int)getY(), width, height);
		
		if(Main.DEBUGGING) {
			for (int i = 0; i < closestMatrix.length; i++) {
				Tile t = closestMatrix[i];
				if(t != null) {
					g.setColor(Color.RED);
					g.drawRect((int)t.getX(), (int)t.getY(), t.getSize(), t.getSize());
				}
			}
		}
		
		hitbox.draw(g);
	}
}