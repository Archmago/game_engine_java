package engine;

import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;

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
	
	// Setup window / game
	public Game() {
		this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		frame = new JFrame("Game Engine");
		frame.add(this);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	public synchronized void stop() {
		
	}
	
	public void update() {
		
	}
	public void render() {
		
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
	}
	
	// Start Game / thread
	public static void main(String args[]) {
		game = new Game();
		game.start();
	}
}
