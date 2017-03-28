package alpha;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Random;

import javax.swing.ImageIcon;

public class SnakeActivity extends Activity {
	
	private enum Direction {
		UP, DOWN, LEFT, RIGHT
	}
	
	public static final int RATE = 100;
	private static final String PATH_IMG = "res/img/";
	private static final int PIXEL_SIZE = 10;
	private static int WIDTH;
	private static int HEIGHT;
	
	private Point[] snakeLocations;
	private Direction snakeDirection;
	private Point appleLocation = new Point();
	private int snakeDots = 4;
	
	private Image apple = new ImageIcon(PATH_IMG + "apple.png").getImage();
	private Image head = new ImageIcon(PATH_IMG + "head.png").getImage();
	private Image dot = new ImageIcon(PATH_IMG + "dot.png").getImage();
	
	public SnakeActivity(Application application) {
		super(application);
		SnakeActivity.WIDTH = application.getDimension().width;
		SnakeActivity.HEIGHT = application.getDimension().height;
		snakeLocations = new Point[(WIDTH * HEIGHT)/(PIXEL_SIZE * PIXEL_SIZE)];
		super.setBackground(Color.BLACK);
	}
	
	@Override
	protected void start(HashMap<String, Object> bundle) {
		locateApple();
		snakeDirection = new Random().nextDouble() < 0.5 ? Direction.RIGHT : Direction.LEFT;
		for (int i = 0; i < snakeLocations.length; i++) {
			final int x = (snakeDirection == Direction.RIGHT)  ? (WIDTH/2 - i * PIXEL_SIZE) : (WIDTH/2 + i * PIXEL_SIZE);
			final int y = HEIGHT/2;
			snakeLocations[i] = i < snakeDots ?  new Point(x, y) : new Point();
		}
	}

	@Override
	protected void stop() {
	}

	@Override
	protected void pause() {
	}

	@Override
	protected void resume() {
	}

	@Override
	protected void update() {
		if (isCollidingWithSelf() || isCollidingWithWall()) {
			super.deactivate();
		}
		checkAppleCollision();
		moveSnake();
	}
	
	@Override
	protected void handlePressedKeys() {
		
		if (super.isPressedKey(KeyEvent.VK_SPACE | KeyEvent.VK_ENTER)) {
			if (super.isActive()) {
				super.suspend();
			} else {
				super.proceed();
			}
		}
		
		if (super.isPressedKey(KeyEvent.VK_UP) && snakeDirection != Direction.DOWN) {
			snakeDirection = Direction.UP;
		} else if (super.isPressedKey(KeyEvent.VK_DOWN) && snakeDirection != Direction.UP) {
			snakeDirection = Direction.DOWN;
		} else if (super.isPressedKey(KeyEvent.VK_LEFT) && snakeDirection != Direction.RIGHT) {
			snakeDirection = Direction.LEFT;
		} else if (super.isPressedKey(KeyEvent.VK_RIGHT) && snakeDirection != Direction.LEFT) {
			snakeDirection = Direction.RIGHT;
		}

	}
	
	@Override
	protected void render(Graphics g) {
		
		for (int i = 0; i < snakeDots; i++) {
			final Image img = (i == 0) ? head : dot;
			final Point p = snakeLocations[i];
			g.drawImage(img, p.x, p.y, this);
		}
		
		g.drawImage(apple, appleLocation.x, appleLocation.y, this);
		g.setColor(Color.CYAN);
		g.drawString("Score: " + snakeDots, 5, 15);

		Toolkit.getDefaultToolkit().sync();
	}
	
	private void locateApple() {
		appleLocation.setLocation(getRandomAndRound(WIDTH), getRandomAndRound(HEIGHT));
	}
	
	private int getRandomAndRound(int range) {
		return ((int)(Math.random() * range) / PIXEL_SIZE) * PIXEL_SIZE;
	}
	
	private boolean isCollidingWithSelf() {
		for (int i = snakeDots; i > 4; i--) {
		    if (snakeLocations[0].equals(snakeLocations[i])) {
		        return true;
		    }
		}
		return false;
	}
	
	private boolean isCollidingWithWall() {
		final int x = snakeLocations[0].x;
		final int y = snakeLocations[0].y;
		return (x < 0 || x >= SnakeActivity.WIDTH || y < 0 || y >= SnakeActivity.HEIGHT);
	}
	
	private void checkAppleCollision() {
		if (snakeLocations[0].equals(appleLocation)) {
			locateApple();
			snakeDots++;
		}
	}
	
	private void moveSnake() {
		
		for (int i = snakeDots; i > 0; i--) {
			snakeLocations[i].setLocation(snakeLocations[i-1]);
		}
		
		switch(snakeDirection) {
		case LEFT:
			snakeLocations[0].translate(-PIXEL_SIZE, 0);
			break;
		case RIGHT:
			snakeLocations[0].translate(PIXEL_SIZE, 0);
			break;
		case UP:
			snakeLocations[0].translate(0, -PIXEL_SIZE);
			break;
		case DOWN:
			snakeLocations[0].translate(0, PIXEL_SIZE);
			break;
		}
		
	}
	
}
