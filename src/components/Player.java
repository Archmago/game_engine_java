package components;

import java.awt.Graphics;

public class Player extends Entity {
	
	public Player(int x, int y, int height, int width) {
		super(x, y, height, width);
	}
	
	@Override
	public void update() {
		this.setX(getX() + 1);
		this.setY(getY() + 1);
	}
	
	@Override
	public void render(Graphics g) {
		g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}
}
