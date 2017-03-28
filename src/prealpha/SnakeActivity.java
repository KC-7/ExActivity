package prealpha;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.Set;

import javax.swing.ImageIcon;

import prealpha.Activity;

public class SnakeActivity extends Activity {

	private static final long serialVersionUID = 1L;
	
	private enum Direction {
		UP, DOWN, LEFT, RIGHT
	}
	
	private static final String PATH_IMG = "res/img/";

	private static final int WIDTH = 300;
	private static final int HEIGHT = 300;
	private static final int RATE = 100;
	private static final Color COLOR = Color.BLACK;
	
	private static final int DOT_COUNT = 900;
	private static final int x[] = new int[DOT_COUNT];
	private static final int y[] = new int[DOT_COUNT];	
	
	private final int DOT_SIZE = 10;
	
	private int dots, apple_x, apple_y;
	private Direction direction;
	private Image apple, head, dot;

	public SnakeActivity() {
		super(WIDTH, HEIGHT, RATE, COLOR);
	}

	@Override
	protected final void load() {
		apple = new ImageIcon(PATH_IMG + "apple.png").getImage();
		head = new ImageIcon(PATH_IMG + "head.png").getImage();
		dot = new ImageIcon(PATH_IMG + "dot.png").getImage();
	}

	@Override
	protected final void start() {
		
		relocateApple();
		dots = 4;
		direction = (Math.random() < 0.75) ? Direction.RIGHT : Direction.LEFT;

		for (int z = 0; z < dots; z++) {
			x[z] = (direction == Direction.RIGHT)  ? (WIDTH/2 - z * DOT_SIZE) : (WIDTH/2 + z * DOT_SIZE);
			y[z] = HEIGHT/2;
		}
		
	}

	@Override
	protected final void stop() {}

	@Override
	protected final void process() {
		checkCollision();
		checkApple();
		move();
	}
	
	@Override
	protected void draw(Graphics g) {
		
		// Draws each element of the snake as a head or dot
		for (int z = 0; z < dots; z++) {
			if (z == 0) {
				g.drawImage(head, x[z], y[z], this);
			} else {
				g.drawImage(dot, x[z], y[z], this);
			}
		}
		
		// Draws the apple and the score
		g.drawImage(apple, apple_x, apple_y, this);
		g.setColor(Color.CYAN);
		g.drawString("Score: " + dots, 5, 15);

		Toolkit.getDefaultToolkit().sync();
		
	}

	@Override
	protected void checkKeyList() {
		
		final Set<Integer> keyList = super.keyList;
		
		// Pauses/resumes the Activity
		if (keyList.contains(KeyEvent.VK_SPACE)) {
			if (isActive()) {
				super.pause();
			} else {
				super.resume();
			}
		}
		
		// Changes direction except for reversals
		if (keyList.contains(KeyEvent.VK_UP) && direction != Direction.DOWN) {
			direction = Direction.UP;
		} else if (keyList.contains(KeyEvent.VK_DOWN) && direction != Direction.UP) {
			direction = Direction.DOWN;
		} else if (keyList.contains(KeyEvent.VK_LEFT) && direction != Direction.RIGHT) {
			direction = Direction.LEFT;
		} else if (keyList.contains(KeyEvent.VK_RIGHT) && direction != Direction.LEFT) {
			direction = Direction.RIGHT;
		}
		
		// Utilizes debugging features
		if (keyList.contains(KeyEvent.VK_SHIFT)) {
			if (keyList.contains(KeyEvent.VK_A)) {
				dots++;
			}
			if (keyList.contains(KeyEvent.VK_S)) {
				relocateApple();
			}
		}
		
	}
	
	// Locates apples to random spot
	private void relocateApple() {
		apple_x = ((int)(Math.random() * WIDTH) / DOT_SIZE) * DOT_SIZE;		// Rounds down to nearest dot
		apple_y = ((int)(Math.random() * HEIGHT) / DOT_SIZE) * DOT_SIZE;	
	}

	// Checks snake collision with the apple to relocate the apple and increase dots
	private void checkApple() {
		if ((x[0] == apple_x) && (y[0] == apple_y)) {
			relocateApple();
			dots++;
		}
	}
	
	// Checks snake collision with self or borders to deactivate
	private void checkCollision() {

		// Checks for self-collision
		for (int z = dots; z > 0; z--) {
			if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
				deactivate();
			}
		}

		// Checks for border collision
		if (y[0] >= HEIGHT || y[0] < 0 || x[0] >= WIDTH || x[0] < 0) {
			deactivate();
		}
		
	}
	
	// Moves body and head
	private void move() {
		
		// Moves body
		for (int z = dots; z > 0; z--) {
			x[z] = x[z - 1];
			y[z] = y[z - 1];
		}
		
		
		// Moves head
		switch(direction) {
			case LEFT:
				x[0] -= DOT_SIZE;
				break;
			case RIGHT:
				x[0] += DOT_SIZE;
				break;
			case UP:
				y[0] -= DOT_SIZE;
				break;
			case DOWN:
				y[0] += DOT_SIZE;
				break;
		}
		
	}

}
