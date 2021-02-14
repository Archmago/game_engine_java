package engine;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JFrame;
import components.Player;
import components.Entity;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	
	// Window
	public static JFrame frame;
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final int SCALE = 1;
	
	// Game
	public static Game game;
	private Thread thread;
	private boolean isRunning;
	private final double FPS_LIMIT = 60.0;
	private BufferStrategy bs;
	private Graphics g;
	private BufferedImage screen;
	
	// Game Components
	private Player player;
	private ArrayList<Entity> entities;
	
	public Game() {
		
		// Setup window
		this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		frame = new JFrame("Game Engine");
		frame.add(this);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		screen = new BufferedImage(WIDTH * SCALE, HEIGHT * SCALE, BufferedImage.TYPE_INT_RGB);
		
		// Setup Game
		player = new Player();
		entities = new ArrayList<Entity>();
		entities.add(player);
	}
	
	// Thread Control
	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	// Game elements update, render
	public void update() {
		for(int i = 0; i < entities.size(); i++) {
			entities.get(i).update();
		}
	}
	public void render() {
		bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		g = screen.getGraphics();
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);
		
		// All Entities, tiles, etc...
		for(int i = 0; i < entities.size(); i++) {
			entities.get(i).render();
		}
		
		g = bs.getDrawGraphics();
		g.drawImage(screen, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		bs.show();
	}
	
	// Game loop
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double amontOfUpdates = FPS_LIMIT;
		double ns = 1000000000 / amontOfUpdates;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		while(isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				update();
				render();
				frames++;
				delta--;
			}
			if(System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS: " + frames);
				frames = 0;
				timer += 1000;
			}
		}
		stop();
	}
	
	// Start Game / thread
	public static void main(String args[]) {
		game = new Game();
		game.start();
	}
}
